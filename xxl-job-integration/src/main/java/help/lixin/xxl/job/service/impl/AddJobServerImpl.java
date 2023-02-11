package help.lixin.xxl.job.service.impl;

import com.alibaba.fastjson.JSONObject;
import help.lixin.xxl.job.mediator.CookieMediator;
import help.lixin.xxl.job.model.XxlJobServiceResponse;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.AbstractService;
import help.lixin.xxl.job.service.IAddJobService;
import help.lixin.xxl.job.service.context.XxlJobContext;
import help.lixin.xxl.job.utils.XxlJobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Map;

public class AddJobServerImpl extends AbstractService implements IAddJobService {
    private static final Logger logger = LoggerFactory.getLogger(AddJobServerImpl.class);

    private static final String ADD_URL = "jobinfo/add";
    private XxlJobProperties xxlJobProperties;

    public AddJobServerImpl(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        super(cookieMediator);
        this.xxlJobProperties = xxlJobProperties;
    }

    @Override
    public Integer add(XxlJobContext ctx) throws Exception {
        Integer jobId = null;
        String url = xxlJobProperties.getAdminAddresses() + ADD_URL;
        String cookie = this.getCookieMediator().getCookie();
        Map<String, Object> map = XxlJobUtil.assembleJobInfoMap(ctx);
        XxlJobServiceResponse result = XxlJobUtil.doPostByXForm(url, cookie, map);
        logger.info("xxl-job Admin AddJobResponse:{}", result);
        JSONObject adminResponse = (JSONObject) result.getData();
        String jobIdString = adminResponse.getString("content");
        if (!StringUtils.isEmpty(jobIdString)) {
            jobId = new Integer(jobIdString);
        }
        if (result == null || result.getCode() != 200) {
            logger.error("xxl-job jobInfo add fail");
        }
        if (null == jobId) {
            logger.error("xxl-job jobInfo add fail :[{}]", jobIdString);
        }
        return jobId;
    }
}
