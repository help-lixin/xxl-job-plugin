package help.lixin.xxl.job.service.impl;

import help.lixin.xxl.job.mediator.CookieMediator;
import help.lixin.xxl.job.model.XxlJobServiceResponse;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.AbstractService;
import help.lixin.xxl.job.service.ILoginService;
import help.lixin.xxl.job.utils.XxlJobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class LoginServiceImpl extends AbstractService implements ILoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    static final String LOGIN_URL = "/login";
    private XxlJobProperties xxlJobProperties;

    public LoginServiceImpl(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        super(cookieMediator);
        this.xxlJobProperties = xxlJobProperties;
    }

    @Override
    public void login(String userName, String password) {
        String cookie = null;
        try {
            String url = xxlJobProperties.getAdminAddresses() + LOGIN_URL;
            XxlJobServiceResponse<String> response = XxlJobUtil.getCookie(url, userName, password);
            if (response == null || response.getCode() != 200 || StringUtils.isEmpty(response.getData())) {
                throw new RuntimeException("xxl-job login getcookie fail");
            }
            cookie = response.getData();
            this.getCookieMediator().setCookie(cookie);
            logger.info("xxl-job LoginServiceImpl  login result:{}", cookie);
        } catch (Exception e) {
            logger.error("xxl-job LoginServiceImpl  login fail info:{}", e);
        }
    }
}
