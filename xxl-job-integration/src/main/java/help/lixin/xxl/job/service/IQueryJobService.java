package help.lixin.xxl.job.service;

import help.lixin.xxl.job.model.XxlJobInfo;
import help.lixin.xxl.job.service.context.XxlJobContext;

public interface IQueryJobService {
     /**
      *
      * @param  QueryJobContext ctx
      * @return XxlJobInfo
      * @throws Exception
      */
     XxlJobInfo queryJobByDesc(XxlJobContext ctx)throws Exception;

}
