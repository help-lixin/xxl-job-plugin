package help.lixin.xxl.job.autoregsiter;

import help.lixin.xxl.job.model.XxlJobInfo;
import help.lixin.xxl.job.properties.JobTaskProperties;
import help.lixin.xxl.job.properties.XxlJobProperties;
import com.xxl.job.core.glue.GlueTypeEnum;
import help.lixin.xxl.job.service.*;
import help.lixin.xxl.job.service.context.impl.AddJobContext;
import help.lixin.xxl.job.service.context.impl.QueryExecuorContext;
import help.lixin.xxl.job.service.context.impl.QueryJobContext;
import help.lixin.xxl.job.service.context.impl.SaveExecuorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

public class XxlJobAutoRegsiterService {
    private final Logger logger = LoggerFactory.getLogger(XxlJobAutoRegsiterService.class);

    @Autowired
    private IAddJobService addJobService;

    @Autowired
    private IQueryExecutorService queryExecutorService;

    @Autowired
    private IQueryJobService queryJobService;

    @Autowired
    private ISaveExecutorService saveExecutorService;

    @Autowired
    private IUpdateJobService updateJobService;

    @Autowired
    private XxlJobProperties xxlJobProperties;

    public void autoregsiter(List<JobTaskProperties> jobHandlers) {
        if (jobHandlers == null || jobHandlers.isEmpty()) {
            return;
        }

        Integer executorId = null;
        try {
            String appName = xxlJobProperties.getAppName();
            executorId = queryExecutorService.queryExecutorId(QueryExecuorContext.newBuilder().appName(appName).build());
            if (executorId == null) {
                String title = xxlJobProperties.getAppDesc();
                if (StringUtils.isEmpty(title)) {
                    title = appName;
                }
                SaveExecuorContext saveExecuorContext = SaveExecuorContext.newBuilder()
                        //
                        .appname(appName)
                        //
                        .title(title)
                        //
                        .addressType(0)
                        //
                        .build();
                saveExecutorService.save(saveExecuorContext);
                executorId = queryExecutorService.queryExecutorId(QueryExecuorContext.newBuilder().appName(appName).build());
            }
        } catch (Exception e) {
            logger.warn("xxl-job getCookie  fail please check adminPlatform userName/passWord correctness  adminPlatformAddress:{},fail JobGropName:{} errorInfo:{}", this.xxlJobProperties.getAdminAddresses(), this.xxlJobProperties.getAppName(), e);
        }

        for (JobTaskProperties jobHandler : jobHandlers) {
            if (executorId == null) {
                return;
            }
            try {
                XxlJobInfo xxlJobInfo = queryJobService.queryJobByDesc(QueryJobContext.newBuilder()
                        //
                        .jobGroup(executorId)
                        //
                        .jobDesc(jobHandler.getJobDesc())
                        //
                        .executorHandler(jobHandler.getExecutorMethod())
                        //
                        .scheduleConf(jobHandler.getScheduleType())
                        //
                        .scheduleConf(jobHandler.getScheduleValue())
                        //
                        .build());
                AddJobContext addJobContext = AddJobContext.newBuilder()
                        //
                        .jobGroup(executorId)
                        //
                        .jobDesc(jobHandler.getJobDesc())
                        //
                        .alarmEmail(jobHandler.getAlarmEmail())
                        //
                        .author(jobHandler.getAuthor())
                        //
                        .scheduleType(jobHandler.getScheduleType())
                        //
                        .scheduleConf(jobHandler.getScheduleValue())
                        //
                        .executorHandler(jobHandler.getExecutorMethod())
                        //
                        .executorParam(jobHandler.getExecutorParam())
                        //
                        .executorBlockStrategy(jobHandler.getExecutorBlockStrategy())
                        //
                        .executorTimeout(jobHandler.getExecutorTimeout())
                        //
                        .executorFailRetryCount(jobHandler.getExecutorFailRetryCount())
                        //
                        .executorRouteStrategy(jobHandler.getExecutorRouteStrategy())
                        //
                        .misfireStrategy(jobHandler.getMisfireStrategy())
                        //
                        .glueType(GlueTypeEnum.BEAN.getDesc())
                        //
                        .triggerStatus(jobHandler.getTaskEnabled())
                        //
                        .build();
                if (xxlJobInfo == null) {
                    addJobService.add(addJobContext);
                } else {
                    addJobContext.setId(xxlJobInfo.getId());
                    updateJobService.updateJob(addJobContext);
                }
            } catch (Exception e) {
                logger.warn("xxl-job addAndStart JobInfo fail please go adminPlatform Manual registration adminPlatformAddress:{},fail JobInfoName:{} errorInfo:{}", this.xxlJobProperties.getAdminAddresses(), jobHandler.getExecutorMethod(), e);
            }
        }
    }
}
