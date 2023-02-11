package help.lixin.xxl.job.service.impl;

import com.alibaba.fastjson.JSONObject;
import help.lixin.xxl.job.mediator.CookieMediator;
import help.lixin.xxl.job.model.XxlJobServiceResponse;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.AbstractService;
import help.lixin.xxl.job.service.IRemoveJobService;
import help.lixin.xxl.job.service.context.XxlJobContext;
import help.lixin.xxl.job.service.context.impl.RemoveJobContext;
import help.lixin.xxl.job.utils.XxlJobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class RemoveJobServiceImpl extends AbstractService implements IRemoveJobService {
    public RemoveJobServiceImpl(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        super(cookieMediator);
        this.xxlJobProperties = xxlJobProperties;
    }

    private static final Logger logger = LoggerFactory.getLogger(RemoveJobServiceImpl.class);

    private static final String UPDATE_JOB_URL = "jobinfo/remove";
    private XxlJobProperties xxlJobProperties;

    @Override
    public void delete(XxlJobContext ctx) {
        try {
            RemoveJobContext removeJobContext = (RemoveJobContext) ctx;
            if (removeJobContext == null || removeJobContext.getJobId() == null) {
                logger.warn("xxl-job RemoveJobServiceImpl removeJob jobId null");
                return;
            }
            String url = xxlJobProperties.getAdminAddresses() + UPDATE_JOB_URL;
            Map<String, Object> param = new HashMap<>();
            param.put("id", removeJobContext.getJobId());
            String cookie = this.getCookieMediator().getCookie();
            XxlJobServiceResponse<JSONObject> data = XxlJobUtil.doPostByXForm(url, cookie, param);
            if (data == null || data.getCode() != 200) {
                logger.warn("xxl-job jobInfo RemoveJobServiceImpl fail:admin result not is success");
            }
            logger.info("xxl-job RemoveJobServiceImpl removeJob result:{}", data);
        } catch (Exception e) {
            logger.warn("Xxl-job jobInfo RemoveJobServiceImpl fail:{},param:{}", e, ctx);
        }
    }
}
