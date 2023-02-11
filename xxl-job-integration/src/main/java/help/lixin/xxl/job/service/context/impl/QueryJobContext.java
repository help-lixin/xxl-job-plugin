package help.lixin.xxl.job.service.context.impl;

import help.lixin.xxl.job.service.context.XxlJobContext;

public class QueryJobContext implements XxlJobContext {

    private final Integer jobGroup;          // 执行器主键id
    private final String jobDesc;         //执行器描述
    private final int triggerStatus;      //任务状态 0-停止，1-运行
    private final String scheduleType;            // 调度类型
    private final String scheduleConf;            // 调度配置，值含义取决于调度类型
    private final String executorHandler; //执行器名称

    private QueryJobContext(Builder builder) {
        jobGroup = builder.jobGroup;
        jobDesc = builder.jobDesc;
        triggerStatus = builder.triggerStatus;
        scheduleType = builder.scheduleType;
        scheduleConf = builder.scheduleConf;
        executorHandler = builder.executorHandler;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public Integer getJobGroup() {
        return jobGroup;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public int getTriggerStatus() {
        return triggerStatus;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public String getScheduleConf() {
        return scheduleConf;
    }

    public String getExecutorHandler() {
        return executorHandler;
    }

    public static final class Builder {
        private Integer jobGroup;
        private String jobDesc;
        private int triggerStatus;
        private String scheduleType;
        private String scheduleConf;
        private String executorHandler;

        private Builder() {
        }

        public Builder jobGroup(Integer val) {
            jobGroup = val;
            return this;
        }

        public Builder jobDesc(String val) {
            jobDesc = val;
            return this;
        }

        public Builder triggerStatus(int val) {
            triggerStatus = val;
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

        public Builder executorHandler(String val) {
            executorHandler = val;
            return this;
        }

        public QueryJobContext build() {
            return new QueryJobContext(this);
        }
    }
}
