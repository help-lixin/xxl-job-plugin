package help.lixin.xxl.job.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@RefreshScope
@ConfigurationProperties(prefix = "xxl")
public class JobTasksListProperties {
    private List<JobTaskProperties> tasks = new ArrayList<>();

    public List<JobTaskProperties> getTasks() {
        return tasks;
    }

    public void setTasks(List<JobTaskProperties> tasks) {
        if (null != tasks) {
            this.tasks = tasks;
        }
    }
}
