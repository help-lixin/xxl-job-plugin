package help.lixin.xxl.job.service.context.impl;

import help.lixin.xxl.job.service.context.XxlJobContext;


public class RemoveJobContext implements XxlJobContext {
    private final Integer jobId;          // 执行器主键id

    private RemoveJobContext(Builder builder) {
        jobId = builder.jobId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public Integer getJobId() {
        return jobId;
    }


    public static final class Builder {
        private Integer jobId;


        private Builder() {
        }

        public Builder jobId(Integer val) {
            jobId = val;
            return this;
        }

        public RemoveJobContext build() {
            return new RemoveJobContext(this);
        }
    }
}
