package help.lixin.xxl.job.service;

import help.lixin.xxl.job.service.context.XxlJobContext;

public interface IUpdateJobService {
    /**
     * @param UpdateJobContext ctx
     * @throws Exception
     */
    void updateJob(XxlJobContext ctx) throws Exception;
}
