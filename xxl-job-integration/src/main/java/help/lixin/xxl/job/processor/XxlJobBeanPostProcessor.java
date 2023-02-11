package help.lixin.xxl.job.processor;

import help.lixin.xxl.job.annotation.TaskStatus;
import help.lixin.xxl.job.annotation.XXLJob;
import help.lixin.xxl.job.mediator.XxlJobsMediator;
import help.lixin.xxl.job.properties.JobTaskProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

//@Component
@Deprecated
public class XxlJobBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private XxlJobsMediator xxlJobsMediator;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetCls = bean.getClass();
        Method[] methods = targetCls.getMethods();
        if (methods == null || methods.length < 1) {
            return bean;
        }

        for (Method method : methods) {
            if (method.isAnnotationPresent(XXLJob.class)) {
                JobTaskProperties jobTaskProperties = new JobTaskProperties();

                String methodName = method.getName();
                jobTaskProperties.setExecutorMethod(methodName);
                jobTaskProperties.setBeanName(beanName);
                XXLJob gerpXxlJob = method.getAnnotation(XXLJob.class);
                TaskStatus isEnabled = gerpXxlJob.enabled();
                if (TaskStatus.START.equals(isEnabled.name())) {
                    jobTaskProperties.setTaskEnabled(1);
                } else {
                    jobTaskProperties.setTaskEnabled(0);
                }

                jobTaskProperties.setJobDesc(gerpXxlJob.desc());
                jobTaskProperties.setInitMethod(gerpXxlJob.init());
                jobTaskProperties.setDestroyMethod(gerpXxlJob.destroy());
                jobTaskProperties.setScheduleType(gerpXxlJob.scheduleType().name());
                jobTaskProperties.setScheduleValue(gerpXxlJob.scheduleValue());
                jobTaskProperties.setMisfireStrategy(gerpXxlJob.misfireStrategy().name());
                jobTaskProperties.setExecutorParam(gerpXxlJob.executorParam());
                jobTaskProperties.setExecutorRouteStrategy(gerpXxlJob.routeStrategy().name());
                jobTaskProperties.setExecutorBlockStrategy(gerpXxlJob.blockStrategy().name());
                jobTaskProperties.setExecutorTimeout(gerpXxlJob.timeout());
                jobTaskProperties.setExecutorFailRetryCount(gerpXxlJob.failRetryCount());
                jobTaskProperties.setGlueType(gerpXxlJob.glueType());
                jobTaskProperties.setAuthor(gerpXxlJob.author());
                jobTaskProperties.setAlarmEmail(gerpXxlJob.email());
                xxlJobsMediator.addJob(jobTaskProperties);
            }
        }
        return bean;
    }


}
