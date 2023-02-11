package help.lixin.xxl.job.plugin.impl;

import help.lixin.xxl.job.plugin.IAppNameProcess;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class DefaultAppNameProcess implements IAppNameProcess, EnvironmentAware {

    private Environment environment;

    @Override
    public String process(String oldAppName) {
        String group = environment.getProperty("group", String.class, null);
        if (null != group) {
            return oldAppName + "_" + group;
        } else {
            return oldAppName;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
