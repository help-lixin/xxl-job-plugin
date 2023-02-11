package help.lixin.xxl.job.service.impl;

import help.lixin.xxl.job.properties.JobTaskProperties;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.IAddJobService;
import help.lixin.xxl.job.service.IQueryExecutorService;
import help.lixin.xxl.job.service.IRuningAddJobService;
import help.lixin.xxl.job.service.context.XxlJobContext;
import help.lixin.xxl.job.service.context.impl.AddJobContext;
import help.lixin.xxl.job.service.context.impl.QueryExecuorContext;
import help.lixin.xxl.job.utils.XxlJobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

public class RuningAddJobServerImpl implements IRuningAddJobService, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(RuningAddJobServerImpl.class);

    private static final String XXL_JOB_EXECUTOR_CLASS_NAME = "xxlJobSpringExecutor";

    private IAddJobService addJobService;

    private ApplicationContext applicationContext;

    private IQueryExecutorService queryExecutorService;

    private XxlJobProperties xxlJobProperties;

    public RuningAddJobServerImpl(IAddJobService addJobService, IQueryExecutorService queryExecutorService, XxlJobProperties xxlJobProperties) {
        this.addJobService = addJobService;
        this.queryExecutorService = queryExecutorService;
        this.xxlJobProperties = xxlJobProperties;
    }

    @Override
    public Integer add(XxlJobContext ctx) {
        Integer jobId = null;
        try {
            AddJobContext addJobContext = (AddJobContext) ctx;
            jobId = addJobContext.getId();
            if (addJobContext.getId() == null) {
                String appName = xxlJobProperties.getAppName();
                if (!StringUtils.isEmpty(addJobContext.getJobGroupName())) {
                    appName = addJobContext.getJobGroupName();
                }
                Integer executorId = queryExecutorService.queryExecutorId(QueryExecuorContext.newBuilder().appName(appName).build());
                if (executorId == null) {
                    logger.warn("xxl-job RuningAddJobServerImpl addJob executorId is null. configure effective appName JobParam:{},appName:{}", ctx, xxlJobProperties.getAppName());
                    return null;
                }
                addJobContext.setJobGroup(executorId);
                jobId = addJobService.add(addJobContext);
            }
            JobTaskProperties jobTaskProperties = XxlJobUtil.assembleRunJobTaskProperties(ctx);
            ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
            // XxlJobSpringExecutorExt xxlJobSpringExecutor = (XxlJobSpringExecutorExt) configurableApplicationContext.getBean(XXL_JOB_EXECUTOR_CLASS_NAME);
            // xxlJobSpringExecutor.runJobHandlerMethodRepository(jobTaskProperties);
            logger.info("xxl-job RuningAddJobServerImpl add result:{},param:{}", jobId, ctx);
        } catch (Exception e) {
            logger.error("xxl-job RuningAddJobServerImpl add fail:{},param:{}", e, ctx);
        }
        return jobId;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
