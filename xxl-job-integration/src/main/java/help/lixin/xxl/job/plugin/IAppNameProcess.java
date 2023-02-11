package help.lixin.xxl.job.plugin;

/**
 * 对appName名称进行处理
 */
public interface IAppNameProcess {

    /**
     * @param oldAppName app名称
     * @return 新的app名称
     */
    String process(String oldAppName);
}
