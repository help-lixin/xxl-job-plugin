package help.lixin.xxl.job.properties;

import help.lixin.xxl.job.model.scheduler.ExecutorBlockStrategyEnum;
import help.lixin.xxl.job.model.scheduler.ExecutorRouteStrategyEnum;
import help.lixin.xxl.job.model.scheduler.ScheduleTypeEnum;
import help.lixin.xxl.job.model.scheduler.MisfireStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;

public class JobTaskProperties {
    private final static String AURHOR_NAME = "Admin";

    /**
     * 任务类名  对应spring容器中的bean名称
     */
    private String beanName;
    /**
     * 执行器，任务Handler名称 对应方法名
     */
    private String executorMethod;
    /**
     * 任务中文名称
     */
    private String jobDesc;
    /**
     * 前置处理的方法名
     */
    private String initMethod = "";
    /**
     * 销毁的方法名
     */
    private String destroyMethod = "";
    /**
     * 调度类型  CRON 固定速度
     */
    private String scheduleType = ScheduleTypeEnum.CRON.getDesc();
    /**
     * 调度配置，值含义取决于调度类型  CRON表达式
     */
    private String scheduleValue;
    /**
     * 调度过期策略
     */
    private String misfireStrategy = MisfireStrategyEnum.DO_NOTHING.getDesc();
    /**
     * 执行器，任务参数
     */
    private String executorParam;
    /**
     * 执行器路由策略
     */
    private String executorRouteStrategy = ExecutorRouteStrategyEnum.FIRST.getDesc();
    /**
     * 阻塞处理策略
     */
    private String executorBlockStrategy = ExecutorBlockStrategyEnum.SERIAL_EXECUTION.getDesc();
    /**
     * 任务执行超时时间，单位秒
     */
    private int executorTimeout = 60;
    /**
     * 失败重试次数
     */
    private int executorFailRetryCount = 3;
    /**
     * GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum
     */
    private String glueType = GlueTypeEnum.BEAN.getDesc();
    /**
     * 负责人
     */
    private String author = AURHOR_NAME;

    /**
     * 报警邮件
     */
    private String alarmEmail;
    /**
     * 任务状态 0-停止，1-运行
     */
    private int taskEnabled = 1;

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlarmEmail() {
        return alarmEmail;
    }

    public void setAlarmEmail(String alarmEmail) {
        this.alarmEmail = alarmEmail;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getScheduleValue() {
        return scheduleValue;
    }

    public void setScheduleValue(String scheduleValue) {
        this.scheduleValue = scheduleValue;
    }

    public String getExecutorMethod() {
        return executorMethod;
    }

    public void setExecutorMethod(String jobName) {
        this.executorMethod = jobName;
    }

    public String getMisfireStrategy() {
        return misfireStrategy;
    }

    public void setMisfireStrategy(String misfireStrategy) {
        this.misfireStrategy = misfireStrategy;
    }

    public String getExecutorRouteStrategy() {
        return executorRouteStrategy;
    }

    public void setExecutorRouteStrategy(String executorRouteStrategy) {
        this.executorRouteStrategy = executorRouteStrategy;
    }

    public String getExecutorParam() {
        return executorParam;
    }

    public void setExecutorParam(String executorParam) {
        this.executorParam = executorParam;
    }

    public String getExecutorBlockStrategy() {
        return executorBlockStrategy;
    }

    public void setExecutorBlockStrategy(String executorBlockStrategy) {
        this.executorBlockStrategy = executorBlockStrategy;
    }

    public int getExecutorTimeout() {
        return executorTimeout;
    }

    public void setExecutorTimeout(int executorTimeout) {
        this.executorTimeout = executorTimeout;
    }

    public int getExecutorFailRetryCount() {
        return executorFailRetryCount;
    }

    public String getGlueType() {
        return glueType;
    }

    public void setGlueType(String glueType) {
        this.glueType = glueType;
    }

    public String getDestroyMethod() {
        return destroyMethod;
    }

    public void setDestroyMethod(String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }

    public void setExecutorFailRetryCount(int executorFailRetryCount) {
        this.executorFailRetryCount = executorFailRetryCount;
    }

    public int getTaskEnabled() {
        return taskEnabled;
    }

    public void setTaskEnabled(int triggerStatus) {
        this.taskEnabled = triggerStatus;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    @Override
    public String toString() {
        return "JobTaskProperties{" + "beanName='" + beanName + '\'' + ", executorMethod='" + executorMethod + '\'' + ", jobDesc='" + jobDesc + '\'' + ", initMethod='" + initMethod + '\'' + ", destroyMethod='" + destroyMethod + '\'' + ", scheduleType='" + scheduleType + '\'' + ", scheduleValue='" + scheduleValue + '\'' + ", misfireStrategy='" + misfireStrategy + '\'' + ", executorParam='" + executorParam + '\'' + ", executorRouteStrategy='" + executorRouteStrategy + '\'' + ", executorBlockStrategy='" + executorBlockStrategy + '\'' + ", executorTimeout=" + executorTimeout + ", executorFailRetryCount=" + executorFailRetryCount + ", glueType='" + glueType + '\'' + ", author='" + author + '\'' + ", alarmEmail='" + alarmEmail + '\'' + ", taskEnabled=" + taskEnabled + '}';
    }
}
