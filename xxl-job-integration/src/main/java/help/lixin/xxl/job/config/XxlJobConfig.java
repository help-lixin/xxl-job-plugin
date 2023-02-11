package help.lixin.xxl.job.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import help.lixin.xxl.job.autoregsiter.XxlJobAutoRegsiterService;
import help.lixin.xxl.job.event.IBootstrapFinishHandler;
import help.lixin.xxl.job.event.impl.BootstrapFinishHandler;
import help.lixin.xxl.job.executor.XxlJobSpringExecutor;
import help.lixin.xxl.job.mediator.XxlJobsMediator;
import help.lixin.xxl.job.plugin.IJobNameProcess;
import help.lixin.xxl.job.plugin.IJobInvokeService;
import help.lixin.xxl.job.properties.JobTasksListProperties;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class XxlJobConfig {
    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    public static final String IS_LAZY = "xxl.job.isLazy";

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = XxlJobConfig.IS_LAZY, havingValue = "true")
    public IBootstrapFinishHandler bootstrapFinishHandler() {
        return new BootstrapFinishHandler();
    }


    @Bean
    @ConditionalOnMissingBean
    public XxlJobExecutor xxlJobExecutor(Environment environment,
                                         //
                                         XxlJobProperties xxlJobProperties,
                                         //
                                         JobTasksListProperties jobTasksListProperties,
                                         //
                                         XxlJobsMediator xxlJobsMediator,
                                         //
                                         XxlJobAutoRegsiterService xxlJobAutoRegsiterService,
                                         //
                                         ILoginService loginService,
                                         //
                                         @Autowired(required = false) IJobNameProcess jobNameProcess,
                                         //
                                         @Autowired(required = false) IJobInvokeService jobInvokeService) {
        Boolean isLazy = environment.getProperty(IS_LAZY, Boolean.class, Boolean.FALSE);

        XxlJobExecutor xxlJobExecutor = new XxlJobSpringExecutor(xxlJobProperties, jobTasksListProperties, xxlJobsMediator, xxlJobAutoRegsiterService, loginService, jobNameProcess, jobInvokeService, isLazy);
        xxlJobExecutor.setAdminAddresses(xxlJobProperties.getAdminAddresses());
        xxlJobExecutor.setAppname(xxlJobProperties.getAppName());
        xxlJobExecutor.setAddress(xxlJobProperties.getAddress());
        xxlJobExecutor.setIp(xxlJobProperties.getIp());
        xxlJobExecutor.setPort(xxlJobProperties.getPort());
        xxlJobExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobExecutor.setLogPath(xxlJobProperties.getLogPath());
        xxlJobExecutor.setLogRetentionDays(xxlJobProperties.getLogRetentionDays());
        return xxlJobExecutor;
    }

}
