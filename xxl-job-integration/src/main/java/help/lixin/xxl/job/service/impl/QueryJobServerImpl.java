package help.lixin.xxl.job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import help.lixin.xxl.job.mediator.CookieMediator;
import help.lixin.xxl.job.model.XxlJobInfo;
import help.lixin.xxl.job.model.XxlJobServiceResponse;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.AbstractService;
import help.lixin.xxl.job.service.IQueryJobService;
import help.lixin.xxl.job.service.context.XxlJobContext;
import help.lixin.xxl.job.service.context.impl.QueryJobContext;
import help.lixin.xxl.job.utils.XxlJobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class QueryJobServerImpl extends AbstractService implements IQueryJobService {
    private static final Logger logger = LoggerFactory.getLogger(QueryJobServerImpl.class);
    private static final String GET_JOB = "jobinfo/pageList";

    private static final String JOB_DESC = "jobDesc";
    private static final String JOB_GROUP = "jobGroup";
    private static final String TRIGGER_STATUS = "triggerStatus";
    private static final String EXECUTOR_HANDLER = "executorHandler";

    private XxlJobProperties xxlJobProperties;

    public QueryJobServerImpl(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        super(cookieMediator);
        this.xxlJobProperties = xxlJobProperties;
    }

    @Override
    public XxlJobInfo queryJobByDesc(XxlJobContext ctx) throws Exception {
        QueryJobContext queryJobContext = (QueryJobContext) ctx;
        XxlJobInfo xxlJobInfo = null;
        String url = xxlJobProperties.getAdminAddresses() + GET_JOB;
        Map<String, Object> param = new HashMap<>();
        param.put(JOB_DESC, queryJobContext.getJobDesc());
        param.put(JOB_GROUP, queryJobContext.getJobGroup());
        param.put(TRIGGER_STATUS, -1);
        param.put(EXECUTOR_HANDLER, queryJobContext.getExecutorHandler());
        XxlJobServiceResponse<JSONObject> data = XxlJobUtil.doPostByXForm(url, this.getCookieMediator().getCookie(), param);
        if (data == null || data.getCode() != 200) {
            throw new RuntimeException("GErp_Xxl-job job queryJobByDesc fail");
        }
        JSONArray jsonArray = data.getData().getJSONArray("data");
        if (jsonArray != null && jsonArray.size() > 0) {
            xxlJobInfo = jsonArray.toJavaList(XxlJobInfo.class).get(0);
        }
        logger.info("GErp-xxl-job QueryJobServerImpl queryJobByDesc result:{}", data);
        return xxlJobInfo;
    }
}
