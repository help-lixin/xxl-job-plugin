package help.lixin.xxl.job.event.impl;


import com.xxl.job.core.executor.XxlJobExecutor;
import help.lixin.xxl.job.event.BootstrapFinishEvent;
import help.lixin.xxl.job.event.IBootstrapFinishHandler;
import help.lixin.xxl.job.executor.XxlJobSpringExecutor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;

public class BootstrapFinishHandler implements IBootstrapFinishHandler, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @EventListener
    public void handler(BootstrapFinishEvent event) {
        XxlJobExecutor xxlJobExecutor = applicationContext.getBean(XxlJobExecutor.class);
        if (xxlJobExecutor instanceof help.lixin.xxl.job.executor.XxlJobSpringExecutor) {
            XxlJobSpringExecutor xxlJobSpringExecutor = (XxlJobSpringExecutor) xxlJobExecutor;
            // 开启注册
            xxlJobSpringExecutor.startRegistry();
            // 自动向xxl-job注册
            xxlJobSpringExecutor.startAutoRegister();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
