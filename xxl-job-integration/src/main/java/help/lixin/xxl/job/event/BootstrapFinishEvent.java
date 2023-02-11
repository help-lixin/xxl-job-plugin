package help.lixin.xxl.job.event;

import org.springframework.context.ApplicationEvent;

/**
 * 引导完成事件
 */
public class BootstrapFinishEvent extends ApplicationEvent {
    public BootstrapFinishEvent(Object source) {
        super(source);
    }
}
