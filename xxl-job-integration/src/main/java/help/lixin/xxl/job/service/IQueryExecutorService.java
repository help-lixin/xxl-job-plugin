package help.lixin.xxl.job.service;

import help.lixin.xxl.job.service.context.XxlJobContext;

public interface IQueryExecutorService {
    /**
     *
     * @param  XxlJobContext ctx
     * @return Integer
     * @throws Exception
     */
    Integer queryExecutorId(XxlJobContext ctx)throws Exception;
}
