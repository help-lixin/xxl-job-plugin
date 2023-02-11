package help.lixin.xxl.job.mediator;

import help.lixin.xxl.job.properties.JobTaskProperties;

import java.util.ArrayList;
import java.util.List;

public class XxlJobsMediator {
    public List<JobTaskProperties> jobs = new ArrayList<>();

    public List<JobTaskProperties> getJobs() {
        return jobs;
    }

    public void addJob(JobTaskProperties job) {
        if (null != job) {
            this.jobs.add(job);
        }
    }
}
