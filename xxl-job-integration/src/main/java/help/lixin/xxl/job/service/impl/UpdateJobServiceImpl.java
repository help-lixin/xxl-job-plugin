package help.lixin.xxl.job.service.impl;

import help.lixin.xxl.job.mediator.CookieMediator;
import help.lixin.xxl.job.model.XxlJobServiceResponse;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.AbstractService;
import help.lixin.xxl.job.service.IUpdateJobService;
import help.lixin.xxl.job.service.context.XxlJobContext;
import help.lixin.xxl.job.utils.XxlJobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class UpdateJobServiceImpl extends AbstractService implements IUpdateJobService {
    public UpdateJobServiceImpl(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        super(cookieMediator);
        this.xxlJobProperties = xxlJobProperties;
    }

    private static final Logger logger = LoggerFactory.getLogger(UpdateJobServiceImpl.class);

    private static final String UPDATE_JOB_URL = "jobinfo/update";
    private XxlJobProperties xxlJobProperties;

    @Override
    public void updateJob(XxlJobContext ctx) throws Exception {
        String url = xxlJobProperties.getAdminAddresses() + UPDATE_JOB_URL;
        String cookie = this.getCookieMediator().getCookie();
        Map<String, Object> map = XxlJobUtil.assembleJobInfoMap(ctx);
        XxlJobServiceResponse result = XxlJobUtil.doPostByXForm(url, cookie, map);
        if (result == null || result.getCode() != 200) {
            throw new RuntimeException("GErp_Xxl-job jobInfo updateJob fail");
        }
        logger.info("xxl-job AddJobServerImpl updateJob result:{}", result);
    }
}
