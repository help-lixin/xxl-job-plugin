package help.lixin.xxl.job.service;

import help.lixin.xxl.job.service.context.XxlJobContext;

public interface IAddJobService{
     /**
      *
      * @param  AddJobContext ctx
      * @throws Exception
      */
     Integer add(XxlJobContext ctx)throws Exception;
}
