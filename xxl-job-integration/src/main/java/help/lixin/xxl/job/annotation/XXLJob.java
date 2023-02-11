package help.lixin.xxl.job.annotation;

import help.lixin.xxl.job.model.scheduler.ExecutorBlockStrategyEnum;
import help.lixin.xxl.job.model.scheduler.ExecutorRouteStrategyEnum;
import help.lixin.xxl.job.model.scheduler.MisfireStrategyEnum;
import help.lixin.xxl.job.model.scheduler.ScheduleTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XXLJob {

    String name();

    /**
     * 任务状态
     */
    TaskStatus enabled() default TaskStatus.START;

    /**
     * 任务描述
     */
    String desc();

    /**
     * 调度类型
     */
    ScheduleTypeEnum scheduleType() default ScheduleTypeEnum.CRON;

    /**
     * 调度表达式
     */
    String scheduleValue();

    /**
     * 调度过期策略
     */
    MisfireStrategyEnum misfireStrategy() default MisfireStrategyEnum.DO_NOTHING;

    /**
     * 任务参数
     */
    String executorParam() default "";

    /**
     * 执行器路由策略
     */
    ExecutorRouteStrategyEnum routeStrategy() default ExecutorRouteStrategyEnum.FIRST;

    /**
     * 阻塞处理策略
     */
    ExecutorBlockStrategyEnum blockStrategy() default ExecutorBlockStrategyEnum.SERIAL_EXECUTION;

    /**
     * 任务执行超时时间 单位秒
     */
    int timeout() default 60;

    /**
     * 失败重试次数
     */
    int failRetryCount() default 3;

    /**
     * GLUE类型
     */
    String glueType() default "BEAN";

    /**
     * 负责人
     */
    String author() default "AURHOR_NAME";

    /**
     * 报警邮件
     */
    String email() default "";


    /**
     * 初始化执行方法
     */
    String init() default "";

    /**
     * 销毁方法
     */
    String destroy() default "";

    /**
     * xxl-job所属分组(配合将来使用)
     *
     * @return
     */
    String group() default "";
}
