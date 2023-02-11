package help.lixin.xxl.job;

import help.lixin.xxl.job.annotation.XXLJob;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TestJob {
    @XXLJob(name = "${job.name}", scheduleValue = "0/15 * * * * ?", desc = "Ebay订单下载")
    public void test1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        System.out.println(simpleDateFormat.format(new Date()) + " test-1 hello world-----1!!!!");
    }
    
    public void test2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        System.out.println(simpleDateFormat.format(new Date()) + " test-2 hello world-----2!!!!");
    }

}
