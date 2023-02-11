package help.lixin.xxl.job.service.impl;

import com.xxl.job.core.executor.XxlJobExecutor;
import help.lixin.xxl.job.executor.XxlJobSpringExecutor;
import help.lixin.xxl.job.plugin.IAppNameProcess;
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

    private IAddJobService addJobService;

    private ApplicationContext applicationContext;

    private IQueryExecutorService queryExecutorService;

    private XxlJobProperties xxlJobProperties;

    private IAppNameProcess appNameProcess;

    public RuningAddJobServerImpl(IAddJobService addJobService,
                                  //
                                  IAppNameProcess appNameProcess,
                                  //
                                  IQueryExecutorService queryExecutorService,
                                  //
                                  XxlJobProperties xxlJobProperties) {
        this.addJobService = addJobService;
        this.queryExecutorService = queryExecutorService;
        this.xxlJobProperties = xxlJobProperties;
        if (null != appNameProcess) {
            this.appNameProcess = appNameProcess;
        }
    }

    @Override
    public Integer addAndRun(XxlJobContext ctx) {
        Integer jobId = null;
        try {
            AddJobContext addJobContext = (AddJobContext) ctx;
            jobId = addJobContext.getId();
            if (addJobContext.getId() == null) {
                String appName = xxlJobProperties.getAppName();
                if (!StringUtils.isEmpty(addJobContext.getJobGroupName())) {
                    appName = addJobContext.getJobGroupName();
                }

                if (null != appName && null != appNameProcess) {
                    appName = appNameProcess.process(appName);
                }

                Integer executorId = queryExecutorService.queryExecutorId(QueryExecuorContext.newBuilder()
                        //
                        .appName(appName).build());
                if (executorId == null) {
                    logger.warn("xxl-job RuningAddJobServerImpl addJob executorId is null. configure effective appName JobParam:{},appName:{}", ctx, xxlJobProperties.getAppName());
                    return null;
                }
                addJobContext.setJobGroup(executorId);
                jobId = addJobService.add(addJobContext);
            }

            JobTaskProperties task = XxlJobUtil.assembleRunJobTaskProperties(ctx);

            XxlJobExecutor xxlJobExecutor = applicationContext.getBean(XxlJobExecutor.class);
            if (xxlJobExecutor instanceof XxlJobSpringExecutor) {
                XxlJobSpringExecutor xxlJobSpringExecutor = (XxlJobSpringExecutor) xxlJobExecutor;
                // 添加注任务
                xxlJobSpringExecutor.addTask(task);
                // 注册任务
                xxlJobSpringExecutor.registerTask(task);
                // 向xxl-job-admin重新注册所有的任务.
                xxlJobSpringExecutor.startAutoRegister();
            }
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
