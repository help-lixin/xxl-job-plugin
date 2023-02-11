package help.lixin.xxl.job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import help.lixin.xxl.job.mediator.CookieMediator;
import help.lixin.xxl.job.model.XxlJobServiceResponse;
import help.lixin.xxl.job.plugin.IAppNameProcess;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.AbstractService;
import help.lixin.xxl.job.service.IQueryExecutorService;
import help.lixin.xxl.job.service.context.XxlJobContext;
import help.lixin.xxl.job.service.context.impl.QueryExecuorContext;
import help.lixin.xxl.job.utils.XxlJobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class QueryExecutorServerImpl extends AbstractService implements IQueryExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(QueryExecutorServerImpl.class);
    private static final String GET_GROUP_ID = "jobgroup/pageList";

    private static final String APPNAME = "appname";
    private XxlJobProperties xxlJobProperties;

    private IAppNameProcess appNameProcess;

    public QueryExecutorServerImpl(XxlJobProperties xxlJobProperties, IAppNameProcess appNameProcess, CookieMediator cookieMediator) {
        super(cookieMediator);
        this.xxlJobProperties = xxlJobProperties;
        if (null != appNameProcess) {
            this.appNameProcess = appNameProcess;
        }
    }

    @Override
    public Integer queryExecutorId(XxlJobContext ctx) throws Exception {
        QueryExecuorContext queryExecuorContext = (QueryExecuorContext) ctx;
        String appName = queryExecuorContext.getAppName();
        if (null != appName && null != appNameProcess) {
            appName = appNameProcess.process(appName);
        }
        Integer groupId = null;
        String url = xxlJobProperties.getAdminAddresses() + GET_GROUP_ID;
        Map<String, Object> param = new HashMap<>();
        param.put(APPNAME, appName);
        XxlJobServiceResponse<JSONObject> data = XxlJobUtil.doPostByXForm(url, this.getCookieMediator().getCookie(), param);
        if (data == null || data.getCode() != 200) {
            throw new RuntimeException("xxl-job executor queryExecutorId fail");
        }

        JSONArray jsonArray = data.getData().getJSONArray("data");
        if (jsonArray != null && jsonArray.size() > 0) {
            JSONObject dataString = jsonArray.getJSONObject(0);
            Integer id = dataString.getInteger("id");
            if (id != null) {
                groupId = id;
            }
        }
        logger.info("xxl-job QueryExecutorServerImpl queryExecutorId result:{}", data);
        return groupId;
    }
}
