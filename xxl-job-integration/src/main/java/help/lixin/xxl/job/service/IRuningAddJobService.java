package help.lixin.xxl.job.service;

import help.lixin.xxl.job.service.context.XxlJobContext;


public interface IRuningAddJobService {
    /**
     * @param AddJobContext ctx
     * @throws
     */
    Integer addAndRun(XxlJobContext ctx);
}
