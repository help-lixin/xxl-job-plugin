package help.lixin.xxl.job.plugin;

/**
 * 对job名称进行处理
 */
public interface IJobNameProcess {

    /**
     * @param oldJobName job名称
     * @return 新的job名称
     */
    String process(String oldJobName);

}
