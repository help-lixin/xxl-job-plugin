package help.lixin.xxl.job.config;


import help.lixin.xxl.job.autoregsiter.XxlJobAutoRegsiterService;
import help.lixin.xxl.job.mediator.CookieMediator;
import help.lixin.xxl.job.mediator.XxlJobsMediator;
import help.lixin.xxl.job.plugin.IJobNameProcess;
import help.lixin.xxl.job.plugin.IJobInvokeService;
import help.lixin.xxl.job.plugin.DefaultJobInvokeService;
import help.lixin.xxl.job.plugin.impl.DefaultJobNameProcess;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.*;
import help.lixin.xxl.job.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    private Logger logger = LoggerFactory.getLogger(CommonConfig.class);

    @Bean
    @ConditionalOnMissingBean
    public CookieMediator cookieMediator() {
        return new CookieMediator();
    }

    @Bean
    @ConditionalOnMissingBean
    public XxlJobsMediator xxlJobsMediator() {
        return new XxlJobsMediator();
    }

    @Bean
    @ConditionalOnMissingBean
    public ILoginService loginService(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        return new LoginServiceImpl(xxlJobProperties, cookieMediator);
    }

    @Bean
    @ConditionalOnMissingBean
    public IAddJobService addJobService(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        return new AddJobServerImpl(xxlJobProperties, cookieMediator);
    }

    @Bean
    @ConditionalOnMissingBean
    public ISaveExecutorService saveExecutorService(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        return new SaveExecutorServerImpl(xxlJobProperties, cookieMediator);
    }

    @Bean
    @ConditionalOnMissingBean
    public IQueryExecutorService queryExecutorService(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        return new QueryExecutorServerImpl(xxlJobProperties, cookieMediator);
    }

    @Bean
    @ConditionalOnMissingBean
    public IQueryJobService queryJobService(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        return new QueryJobServerImpl(xxlJobProperties, cookieMediator);
    }

    @Bean
    @ConditionalOnMissingBean
    public IUpdateJobService updateJobService(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        return new UpdateJobServiceImpl(xxlJobProperties, cookieMediator);
    }

    @Bean
    public IRuningAddJobService runAddJobService(
            IAddJobService iAddJobService,
            IQueryExecutorService queryExecutorService,
            XxlJobProperties xxlJobProperties) {
        return new RuningAddJobServerImpl(iAddJobService, queryExecutorService, xxlJobProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public IRemoveJobService removeJobService(XxlJobProperties xxlJobProperties, CookieMediator cookieMediator) {
        return new RemoveJobServiceImpl(xxlJobProperties, cookieMediator);
    }

    @Bean
    @ConditionalOnMissingBean
    public XxlJobAutoRegsiterService xxlJobRegsiterFacadeService() {
        return new XxlJobAutoRegsiterService();
    }


    @Bean
    @ConditionalOnMissingBean
    public IJobNameProcess jobNameProcess() {
        return new DefaultJobNameProcess();
    }

    @Bean
    @ConditionalOnMissingBean
    public IJobInvokeService jobInvokeService() {
        return new DefaultJobInvokeService();
    }
}
