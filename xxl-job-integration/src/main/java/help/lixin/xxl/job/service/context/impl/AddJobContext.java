package help.lixin.xxl.job.service.context.impl;

import help.lixin.xxl.job.model.scheduler.ExecutorBlockStrategyEnum;
import help.lixin.xxl.job.model.scheduler.ExecutorRouteStrategyEnum;
import help.lixin.xxl.job.model.scheduler.ScheduleTypeEnum;
import help.lixin.xxl.job.model.scheduler.MisfireStrategyEnum;
import help.lixin.xxl.job.service.context.XxlJobContext;
import com.xxl.job.core.glue.GlueTypeEnum;

public class AddJobContext<T> implements XxlJobContext {
    private final static String AURHOR_NAME = "Admin";
    private Integer id;                        //任务主键id
    private String jobGroupName;                    // 执行器名称
    private Integer jobGroup;                    // 执行器主键ID
    private String jobDesc;                //任务描述
    private String beanName;                //任务类名  对应spring容器中的bean名称
    private String executorHandler;        // 执行器，任务Handler名称 对应方法名
    private String author;                    // 负责人
    private String alarmEmail;                // 报警邮件
    private String scheduleType;            // 调度类型
    private String scheduleConf;            // 调度配置，值含义取决于调度类型
    private String misfireStrategy;        // 调度过期策略
    private String executorRouteStrategy;    // 执行器路由策略
    private T executorParam;            // 执行器，回调任务参数
    private String executorBlockStrategy;    // 阻塞处理策略
    private int executorTimeout = 60;            // 任务执行超时时间，单位秒
    private int executorFailRetryCount = 3;    // 失败重试次数
    private String glueType;                // GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum
    private int triggerStatus = 1;        //任务状态 0-停止，1-运行
    private int tenantEnabled = 0;                            //0-关闭,1-运行 是否开启多租户模式，默认为关闭


    public void setId(int id) {
        this.id = id;
    }

    public void setJobGroup(int jobGroup) {
        this.jobGroup = jobGroup;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAlarmEmail(String alarmEmail) {
        this.alarmEmail = alarmEmail;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public void setScheduleConf(String scheduleConf) {
        this.scheduleConf = scheduleConf;
    }

    public void setMisfireStrategy(String misfireStrategy) {
        this.misfireStrategy = misfireStrategy;
    }

    public void setExecutorRouteStrategy(String executorRouteStrategy) {
        this.executorRouteStrategy = executorRouteStrategy;
    }

    public void setExecutorHandler(String executorHandler) {
        this.executorHandler = executorHandler;
    }

    public void setExecutorParam(T executorParam) {
        this.executorParam = executorParam;
    }

    public void setExecutorBlockStrategy(String executorBlockStrategy) {
        this.executorBlockStrategy = executorBlockStrategy;
    }

    public void setExecutorTimeout(int executorTimeout) {
        this.executorTimeout = executorTimeout;
    }

    public void setExecutorFailRetryCount(int executorFailRetryCount) {
        this.executorFailRetryCount = executorFailRetryCount;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public void setGlueType(String glueType) {
        this.glueType = glueType;
    }

    public Integer getId() {
        return id;
    }

    public Integer getJobGroup() {
        return jobGroup;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public String getAuthor() {
        return author;
    }

    public String getAlarmEmail() {
        return alarmEmail;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public String getScheduleConf() {
        return scheduleConf;
    }

    public String getMisfireStrategy() {
        return misfireStrategy;
    }

    public String getExecutorRouteStrategy() {
        return executorRouteStrategy;
    }

    public String getExecutorHandler() {
        return executorHandler;
    }

    public T getExecutorParam() {
        return executorParam;
    }

    public String getExecutorBlockStrategy() {
        return executorBlockStrategy;
    }

    public int getExecutorTimeout() {
        return executorTimeout;
    }

    public int getExecutorFailRetryCount() {
        return executorFailRetryCount;
    }

    public String getGlueType() {
        return glueType;
    }

    public int getTriggerStatus() {
        return triggerStatus;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setJobGroup(Integer jobGroup) {
        this.jobGroup = jobGroup;
    }

    public void setTriggerStatus(int triggerStatus) {
        this.triggerStatus = triggerStatus;
    }

    public int getTenantEnabled() {
        return tenantEnabled;
    }

    public void setTenantEnabled(int tenantEnabled) {
        this.tenantEnabled = tenantEnabled;
    }

    private AddJobContext(Builder<T> builder) {
        id = builder.id;
        jobGroupName = builder.jobGroupName;
        jobGroup = builder.jobGroup;
        jobDesc = builder.jobDesc;
        author = builder.author;
        beanName = builder.beanName;
        alarmEmail = builder.alarmEmail;
        scheduleType = builder.scheduleType;
        scheduleConf = builder.scheduleConf;
        misfireStrategy = builder.misfireStrategy;
        executorRouteStrategy = builder.executorRouteStrategy;
        executorHandler = builder.executorHandler;
        executorParam = builder.executorParam;
        executorBlockStrategy = builder.executorBlockStrategy;
        executorTimeout = builder.executorTimeout;
        executorFailRetryCount = builder.executorFailRetryCount;
        glueType = builder.glueType;
        triggerStatus = builder.triggerStatus;
        tenantEnabled = builder.tenantEnabled;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder<T> {
        private Integer id;
        private Integer jobGroup;
        private String jobGroupName;
        private String beanName;
        private String jobDesc;
        private String author = AURHOR_NAME;
        private String alarmEmail;
        private String scheduleType = ScheduleTypeEnum.CRON.getDesc();
        private String scheduleConf;
        private String misfireStrategy = MisfireStrategyEnum.DO_NOTHING.getDesc();
        private String executorRouteStrategy = ExecutorRouteStrategyEnum.FIRST.getDesc();
        private String executorHandler;
        private T executorParam;
        private String executorBlockStrategy = ExecutorBlockStrategyEnum.SERIAL_EXECUTION.getDesc();
        private int executorTimeout = 60;
        private int executorFailRetryCount = 3;
        private String glueType = GlueTypeEnum.BEAN.getDesc();
        private int triggerStatus = 1;
        private int tenantEnabled = 0;


        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder jobGroupName(String val) {
            jobGroupName = val;
            return this;
        }

        public Builder jobGroup(Integer val) {
            jobGroup = val;
            return this;
        }

        public Builder beanName(String val) {
            beanName = val;
            return this;
        }

        public Builder jobDesc(String val) {
            jobDesc = val;
            return this;
        }

        public Builder author(String val) {
            author = val;
            return this;
        }

        public Builder alarmEmail(String val) {
            alarmEmail = val;
            return this;
        }

        public Builder scheduleType(String val) {
            scheduleType = val;
            return this;
        }

        public Builder scheduleConf(String val) {
            scheduleConf = val;
            return this;
        }

        public Builder misfireStrategy(String val) {
            misfireStrategy = val;
            return this;
        }

        public Builder executorRouteStrategy(String val) {
            executorRouteStrategy = val;
            return this;
        }

        public Builder executorHandler(String val) {
            executorHandler = val;
            return this;
        }

        public Builder executorParam(T val) {
            executorParam = val;
            return this;
        }

        public Builder executorBlockStrategy(String val) {
            executorBlockStrategy = val;
            return this;
        }

        public Builder executorTimeout(int val) {
            executorTimeout = val;
            return this;
        }

        public Builder executorFailRetryCount(int val) {
            executorFailRetryCount = val;
            return this;
        }

        public Builder glueType(String val) {
            glueType = val;
            return this;
        }

        public Builder triggerStatus(int val) {
            triggerStatus = val;
            return this;
        }

        public Builder tenantEnabled(int val) {
            tenantEnabled = val;
            return this;
        }

        public AddJobContext build() {
            return new AddJobContext(this);
        }
    }

    @Override
    public String toString() {
        return "AddJobContext{" + "id=" + id + ", jobGroup=" + jobGroup + ", jobDesc='" + jobDesc + '\'' + ", beanName='" + beanName + '\'' + ", author='" + author + '\'' + ", alarmEmail='" + alarmEmail + '\'' + ", scheduleType='" + scheduleType + '\'' + ", scheduleConf='" + scheduleConf + '\'' + ", misfireStrategy='" + misfireStrategy + '\'' + ", executorRouteStrategy='" + executorRouteStrategy + '\'' + ", executorHandler='" + executorHandler + '\'' + ", executorParam='" + executorParam + '\'' + ", executorBlockStrategy='" + executorBlockStrategy + '\'' + ", executorTimeout=" + executorTimeout + ", executorFailRetryCount=" + executorFailRetryCount + ", glueType='" + glueType + '\'' + ", triggerStatus=" + triggerStatus + ", tenantEnabled=" + tenantEnabled + '}';
    }
}
