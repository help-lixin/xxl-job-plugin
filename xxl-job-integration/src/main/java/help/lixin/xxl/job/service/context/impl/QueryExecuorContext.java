package help.lixin.xxl.job.service.context.impl;

import help.lixin.xxl.job.service.context.XxlJobContext;

public class QueryExecuorContext implements XxlJobContext {

    private final String appName;        // 执行器AppName
    private final String title;         //执行器描述
    private final Integer start;        // 起始页
    private final Integer length;    // 页大小

    private QueryExecuorContext(Builder builder) {
        appName = builder.appName;
        title = builder.title;
        start = builder.start;
        length = builder.length;
    }

    public String getAppName() {
        return appName;
    }

    public String getTitle() {
        return title;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getLength() {
        return length;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String appName;
        private String title;
        private Integer start;
        private Integer length;

        private Builder() {
        }

        public Builder appName(String val) {
            appName = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder start(Integer val) {
            start = val;
            return this;
        }

        public Builder length(Integer val) {
            length = val;
            return this;
        }

        public QueryExecuorContext build() {
            return new QueryExecuorContext(this);
        }
    }
}
