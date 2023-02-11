package help.lixin.xxl.job;

import help.lixin.xxl.job.event.BootstrapFinishEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);

        TimeUnit.SECONDS.sleep(30);
        // 配合懒加载机制,做测试,在30秒之后,才让xxl-job去向服务器进行注册.
        BootstrapFinishEvent bootstrapFinishEvent = new BootstrapFinishEvent("");
        ctx.publishEvent(bootstrapFinishEvent);
    }
}
