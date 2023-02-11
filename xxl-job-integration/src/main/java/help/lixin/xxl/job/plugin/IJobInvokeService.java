package help.lixin.xxl.job.plugin;

public interface IJobInvokeService {

    void execute(JobInvokeContext ctx) throws Exception;
}
