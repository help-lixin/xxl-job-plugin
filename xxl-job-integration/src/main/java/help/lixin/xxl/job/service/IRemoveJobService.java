package help.lixin.xxl.job.service;

import help.lixin.xxl.job.service.context.XxlJobContext;

public interface IRemoveJobService {
     /**
      *
      * @param  RemoveJobContext ctx
      * @throws Exception
      */
     void delete(XxlJobContext ctx)throws Exception;
}
