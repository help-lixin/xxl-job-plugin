package help.lixin.xxl.job.service.context.impl;

import help.lixin.xxl.job.service.context.XxlJobContext;

public class SaveExecuorContext implements XxlJobContext {
    private final String appname;
    private final String title;
    private final Integer addressType;

    public String getAppname() {
        return appname;
    }

    public String getTitle() {
        return title;
    }

    public Integer getAddressType() {
        return addressType;
    }

    private SaveExecuorContext(Builder builder) {
        appname = builder.appname;
        title = builder.title;
        addressType = builder.addressType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private String appname;
        private String title;
        private Integer addressType;

        private Builder() {
        }

        public Builder appname(String val) {
            appname = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder addressType(Integer val) {
            addressType = val;
            return this;
        }

        public SaveExecuorContext build() {
            return new SaveExecuorContext(this);
        }
    }

    @Override
    public String toString() {
        return "SaveExecuorContext{" +
                "appname='" + appname + '\'' +
                ", title='" + title + '\'' +
                ", addressType=" + addressType +
                '}';
    }
}
