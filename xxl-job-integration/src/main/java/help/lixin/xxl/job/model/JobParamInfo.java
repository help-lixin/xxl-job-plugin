package help.lixin.xxl.job.model;

import java.io.Serializable;

public class JobParamInfo<T> implements Serializable {
    /**
     * 租户id
     */
    private String tenantCode;
    /**
     * 任务id
     */
    private String jobId;
    /**
     * 任务参数
     */
    private T data;

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder<T> {
        private String tenantCode;
        private String jobId;
        private T data;

        public Builder tenantCode(String tenantCode) {
            this.tenantCode = tenantCode;
            return this;
        }

        public Builder jobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public JobParamInfo build() {
            JobParamInfo jobParamInfo = new JobParamInfo();
            jobParamInfo.setTenantCode(this.tenantCode);
            jobParamInfo.setJobId(this.jobId);
            jobParamInfo.setData(this.data);
            return jobParamInfo;
        }
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JobParamInfo{" +
                "tenantCode='" + tenantCode + '\'' +
                ", jobId='" + jobId + '\'' +
                ", data=" + data +
                '}';
    }
}
