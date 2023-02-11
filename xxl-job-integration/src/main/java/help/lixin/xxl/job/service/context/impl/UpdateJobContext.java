package help.lixin.xxl.job.service.context.impl;

import help.lixin.xxl.job.service.context.XxlJobContext;

public class UpdateJobContext implements XxlJobContext {
    private final int id;                       //任务主键id
    private final int jobGroup;                    // 执行器主键ID
    private final String jobDesc;               // 任务描述
    private final String author;                // 负责人
    private final String alarmEmail;            // 报警邮件
    private final String scheduleType;            // 调度类型
    private final String scheduleConf;            // 调度配置，值含义取决于调度类型
    private final String misfireStrategy;            // 调度过期策略
    private final String executorRouteStrategy;    // 执行器路由策略
    private final String executorHandler;        // 执行器，任务Handler名称
    private final String executorParam;            // 执行器，任务参数
    private final String executorBlockStrategy;    // 阻塞处理策略
    private final int executorTimeout;            // 任务执行超时时间，单位秒
    private final int executorFailRetryCount;    // 失败重试次数
    private final String glueType;                // GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum
    private final int triggerStatus;            //任务状态 0-停止，1-运行

    public int getId() {
        return id;
    }

    public int getJobGroup() {
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

    public String getExecutorParam() {
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

    private UpdateJobContext(UpdateJobContext.Builder builder) {
        id = builder.id;
        jobGroup = builder.jobGroup;
        jobDesc = builder.jobDesc;
        author = builder.author;
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
    }

    public static UpdateJobContext.Builder newBuilder() {
        return new UpdateJobContext.Builder();
    }


    public static final class Builder {
        private int id;
        private int jobGroup;
        private String jobDesc;
        private String author;
        private String alarmEmail;
        private String scheduleType;
        private String scheduleConf;
        private String misfireStrategy;
        private String executorRouteStrategy;
        private String executorHandler;
        private String executorParam;
        private String executorBlockStrategy;
        private int executorTimeout;
        private int executorFailRetryCount;
        private String glueType;
        private int triggerStatus;

        private Builder() {
        }

        public UpdateJobContext.Builder id(int val) {
            id = val;
            return this;
        }

        public UpdateJobContext.Builder jobGroup(int val) {
            jobGroup = val;
            return this;
        }

        public UpdateJobContext.Builder jobDesc(String val) {
            jobDesc = val;
            return this;
        }

        public UpdateJobContext.Builder author(String val) {
            author = val;
            return this;
        }

        public UpdateJobContext.Builder alarmEmail(String val) {
            alarmEmail = val;
            return this;
        }

        public UpdateJobContext.Builder scheduleType(String val) {
            scheduleType = val;
            return this;
        }

        public UpdateJobContext.Builder scheduleConf(String val) {
            scheduleConf = val;
            return this;
        }

        public UpdateJobContext.Builder misfireStrategy(String val) {
            misfireStrategy = val;
            return this;
        }

        public UpdateJobContext.Builder executorRouteStrategy(String val) {
            executorRouteStrategy = val;
            return this;
        }

        public UpdateJobContext.Builder executorHandler(String val) {
            executorHandler = val;
            return this;
        }

        public UpdateJobContext.Builder executorParam(String val) {
            executorParam = val;
            return this;
        }

        public UpdateJobContext.Builder executorBlockStrategy(String val) {
            executorBlockStrategy = val;
            return this;
        }

        public UpdateJobContext.Builder executorTimeout(int val) {
            executorTimeout = val;
            return this;
        }

        public UpdateJobContext.Builder executorFailRetryCount(int val) {
            executorFailRetryCount = val;
            return this;
        }

        public UpdateJobContext.Builder glueType(String val) {
            glueType = val;
            return this;
        }

        public UpdateJobContext.Builder triggerStatus(int val) {
            triggerStatus = val;
            return this;
        }

        public UpdateJobContext build() {
            return new UpdateJobContext(this);
        }
    }

    @Override
    public String toString() {
        return "UpdateJobContext{" + "id=" + id + ", jobGroup=" + jobGroup + ", jobDesc='" + jobDesc + '\'' + ", author='" + author + '\'' + ", alarmEmail='" + alarmEmail + '\'' + ", scheduleType='" + scheduleType + '\'' + ", scheduleConf='" + scheduleConf + '\'' + ", misfireStrategy='" + misfireStrategy + '\'' + ", executorRouteStrategy='" + executorRouteStrategy + '\'' + ", executorHandler='" + executorHandler + '\'' + ", executorParam='" + executorParam + '\'' + ", executorBlockStrategy='" + executorBlockStrategy + '\'' + ", executorTimeout=" + executorTimeout + ", executorFailRetryCount=" + executorFailRetryCount + ", glueType='" + glueType + '\'' + ", triggerStatus=" + triggerStatus + '}';
    }
}
