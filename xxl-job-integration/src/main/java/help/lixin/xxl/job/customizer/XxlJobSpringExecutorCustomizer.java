package help.lixin.xxl.job.customizer;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
public interface XxlJobSpringExecutorCustomizer {
    public void customize(XxlJobSpringExecutor xxlJobSpringExecutor);
}
