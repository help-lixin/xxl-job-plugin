package help.lixin.xxl.job.service;

import help.lixin.xxl.job.service.context.XxlJobContext;


public interface ISaveExecutorService {
    /**
     * @param SaveExecuorContext ctx
     * @throws Exception
     */
    void save(XxlJobContext ctx) throws Exception;
}
