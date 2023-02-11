package help.lixin.xxl.job.plugin.impl;

import help.lixin.xxl.job.plugin.IJobNameProcess;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class DefaultJobNameProcess implements IJobNameProcess, EnvironmentAware {

    private Environment environment;

    @Override
    public String process(String oldJobName) {
        String group = environment.getProperty("group", String.class, null);
        if (null != group) {
            return oldJobName + "_" + group;
        } else {
            return oldJobName;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
