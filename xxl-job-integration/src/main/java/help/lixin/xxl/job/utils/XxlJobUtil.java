package help.lixin.xxl.job.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import help.lixin.xxl.job.model.JobParamInfo;
import help.lixin.xxl.job.model.XxlJobInfo;
import help.lixin.xxl.job.model.XxlJobServiceResponse;
import help.lixin.xxl.job.properties.JobTaskProperties;
import help.lixin.xxl.job.service.context.XxlJobContext;
import help.lixin.xxl.job.service.context.impl.AddJobContext;
import kong.unirest.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class XxlJobUtil {


    public static XxlJobServiceResponse doPostByXForm(String url, String cookieString, Map<String, Object> param) throws Exception {

        Cookie cookie = new Cookie(cookieString);
        HttpResponse<String> httpResponse = Unirest.post(url).header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8").fields(param).cookie(cookie).asString();
        if (httpResponse == null) {
            throw new RuntimeException("XxlJobUtil.doPostByXForm.httpResponse is null");
        }
        String body = httpResponse.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        return XxlJobServiceResponse.newBuilder().code(httpResponse.getStatus()).data(jsonObject).build();

    }

    public static XxlJobServiceResponse getCookie(String url, String userName, String password) throws Exception {
        XxlJobServiceResponse response = null;
        HttpResponse httpResponse = Unirest.post(url).field("userName", userName).field("password", password).asEmpty();
        if (httpResponse == null) {
            throw new RuntimeException("XxlJobUtil.getCookie.httpResponse is null");
        }
        if (httpResponse.getStatus() == 200) {
            StringBuilder stringBuilder = new StringBuilder();
            Cookies cookies = httpResponse.getCookies();
            for (Cookie cookie1 : cookies) {
                stringBuilder.append(cookie1.toString());
            }
            String cookie = stringBuilder.toString();
            Integer indix = cookie.indexOf(";");
            String newCookie = cookie.substring(1, indix);
            response = XxlJobServiceResponse.newBuilder().code(httpResponse.getStatus()).data(newCookie).build();
        } else {
            XxlJobServiceResponse.newBuilder().code(httpResponse.getStatus()).build();
        }
        return response;
    }

    public static Map<String, Object> assembleJobInfoMap(XxlJobContext cxt) throws Exception {
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        AddJobContext addJobContext = (AddJobContext) cxt;
        xxlJobInfo.setId(addJobContext.getId());
        xxlJobInfo.setJobGroup(addJobContext.getJobGroup());
        xxlJobInfo.setJobDesc(addJobContext.getJobDesc());
        xxlJobInfo.setAuthor(addJobContext.getAuthor());
        xxlJobInfo.setAlarmEmail(addJobContext.getAlarmEmail());
        xxlJobInfo.setScheduleType(addJobContext.getScheduleType());
        xxlJobInfo.setScheduleConf(addJobContext.getScheduleConf());
        xxlJobInfo.setGlueType(addJobContext.getGlueType());
        xxlJobInfo.setExecutorHandler(addJobContext.getExecutorHandler());
        //重新组装参数,生成参数实体类
        assembleJobParamInfo(xxlJobInfo, addJobContext);
        xxlJobInfo.setExecutorRouteStrategy(addJobContext.getExecutorRouteStrategy());
        xxlJobInfo.setMisfireStrategy(addJobContext.getMisfireStrategy());
        xxlJobInfo.setExecutorBlockStrategy(addJobContext.getExecutorBlockStrategy());
        xxlJobInfo.setExecutorTimeout(addJobContext.getExecutorTimeout());
        xxlJobInfo.setExecutorFailRetryCount(addJobContext.getExecutorFailRetryCount());
        xxlJobInfo.setTriggerStatus(addJobContext.getTriggerStatus());
        return objectToMap(xxlJobInfo);
    }

    private static void assembleJobParamInfo(XxlJobInfo xxlJobInfo, AddJobContext addJobContext) {
        Object executorParam = addJobContext.getExecutorParam();
        if (executorParam != null && executorParam instanceof String) {
            String executorParamString = (String) executorParam;
            if (isJSON(executorParamString)) {
                //如果任务参数是一个json字符串，则转化为map存储,防止出现转义符和多余的冒号
                JobParamInfo jobParamInfo = JobParamInfo.newBuilder()
                        //
                        .data(JSONObject.parseObject((String) executorParam))
                        //
                        .build();
                xxlJobInfo.setExecutorParam(JSON.toJSONString(jobParamInfo));
                return;
            }
        }
        JobParamInfo jobParamInfo = JobParamInfo.newBuilder()
                //
                .data(addJobContext.getExecutorParam()).build();
        xxlJobInfo.setExecutorParam(JSON.toJSONString(jobParamInfo));
    }

    public static boolean isJSON(String str) {
        boolean result = false;
        if (StringUtils.isNotBlank(str)) {
            str = str.trim();
            if (str.startsWith("{") && str.endsWith("}")) {
                result = true;
            } else if (str.startsWith("[") && str.endsWith("]")) {
                result = true;
            }
        }
        return result;
    }

    public static JobTaskProperties assembleRunJobTaskProperties(XxlJobContext cxt) throws Exception {
        JobTaskProperties jobTaskProperties = new JobTaskProperties();
        AddJobContext addJobContext = (AddJobContext) cxt;
        jobTaskProperties.setJobDesc(addJobContext.getJobDesc());
        jobTaskProperties.setExecutorMethod(addJobContext.getExecutorHandler());
        jobTaskProperties.setBeanName(addJobContext.getBeanName());
        jobTaskProperties.setAuthor(addJobContext.getAuthor());
        jobTaskProperties.setAlarmEmail(addJobContext.getAlarmEmail());
        jobTaskProperties.setScheduleType(addJobContext.getScheduleType());
        jobTaskProperties.setScheduleValue(addJobContext.getScheduleConf());
        jobTaskProperties.setGlueType(addJobContext.getGlueType());
        jobTaskProperties.setExecutorRouteStrategy(addJobContext.getExecutorRouteStrategy());
        jobTaskProperties.setMisfireStrategy(addJobContext.getMisfireStrategy());
        jobTaskProperties.setExecutorBlockStrategy(addJobContext.getExecutorBlockStrategy());
        jobTaskProperties.setExecutorTimeout(addJobContext.getExecutorTimeout());
        jobTaskProperties.setExecutorFailRetryCount(addJobContext.getExecutorFailRetryCount());
        jobTaskProperties.setTaskEnabled(addJobContext.getTriggerStatus());
        return jobTaskProperties;
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value != null) {
                map.put(fieldName, value);
            }
        }
        return map;
    }
}