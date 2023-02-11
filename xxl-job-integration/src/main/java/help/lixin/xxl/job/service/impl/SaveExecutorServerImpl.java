package help.lixin.xxl.job.service.impl;

import com.alibaba.fastjson.JSONObject;
import help.lixin.xxl.job.mediator.CookieMediator;
import help.lixin.xxl.job.model.XxlJobServiceResponse;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.AbstractService;
import help.lixin.xxl.job.service.ISaveExecutorService;
import help.lixin.xxl.job.service.context.XxlJobContext;
import help.lixin.xxl.job.service.context.impl.SaveExecuorContext;
import help.lixin.xxl.job.utils.XxlJobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class SaveExecutorServerImpl extends AbstractService implements ISaveExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(SaveExecutorServerImpl.class);

    private XxlJobProperties xxlJobProperties;

    public SaveExecutorServerImpl(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        super(cookieMediator);
        this.xxlJobProperties = xxlJobProperties;
    }

    private static final String ADD_GROUP_URL = "jobgroup/save";


    @Override
    public void save(XxlJobContext ctx) throws Exception {
        SaveExecuorContext queryExecuorContext = (SaveExecuorContext) ctx;
        String url = xxlJobProperties.getAdminAddresses() + ADD_GROUP_URL;
        Map<String, Object> param = new HashMap<>();
        param.put("appname", queryExecuorContext.getAppname());
        param.put("title", queryExecuorContext.getTitle());
        param.put("addressType", "0");
        XxlJobServiceResponse<JSONObject> result = XxlJobUtil.doPostByXForm(url, this.getCookieMediator().cookie, param);
        if (result == null || result.getCode() != 200) {
            throw new RuntimeException("Xxl-job executor saveExecutor fail");
        }
        if (result.getCode() == 200 && result.getData().containsKey("msg") && null != result.getData().get("msg")) {
            String error = String.format("register executor:[%s]  fail: [%s]", ctx, (String) result.getData().get("msg"));
            throw new RuntimeException(error);
        }
        logger.info("xxl-job SaveExecutorServerImpl  save result:{}", result);
    }
}
