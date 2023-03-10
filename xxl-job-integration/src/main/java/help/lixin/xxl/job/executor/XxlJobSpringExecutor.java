package help.lixin.xxl.job.executor;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.glue.GlueFactory;
import com.xxl.job.core.thread.ExecutorRegistryThread;
import com.xxl.job.core.util.IpUtil;
import com.xxl.job.core.util.NetUtil;
import help.lixin.xxl.job.annotation.TaskStatus;
import help.lixin.xxl.job.annotation.XXLJob;
import help.lixin.xxl.job.autoregsiter.XxlJobAutoRegsiterService;
import help.lixin.xxl.job.mediator.XxlJobsMediator;
import help.lixin.xxl.job.plugin.IJobInvokeService;
import help.lixin.xxl.job.properties.JobTaskProperties;
import help.lixin.xxl.job.properties.JobTasksListProperties;
import help.lixin.xxl.job.properties.XxlJobProperties;
import help.lixin.xxl.job.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XxlJobSpringExecutor extends com.xxl.job.core.executor.impl.XxlJobSpringExecutor {

    private Logger logger = LoggerFactory.getLogger(help.lixin.xxl.job.executor.XxlJobSpringExecutor.class);

    private boolean isLazy = Boolean.FALSE;

    private JobTasksListProperties jobTasksListProperties;

    private XxlJobAutoRegsiterService xxlJobAutoRegsiterService;

    private XxlJobProperties xxlJobProperties;

    private IJobInvokeService jobInvokeService;

    private XxlJobsMediator xxlJobsMediator;

    private ILoginService loginService;


    public XxlJobSpringExecutor(XxlJobProperties xxlJobProperties,
                                //
                                JobTasksListProperties jobTasksListProperties,
                                //
                                XxlJobsMediator xxlJobsMediator,
                                //
                                XxlJobAutoRegsiterService xxlJobAutoRegsiterService,
                                //
                                ILoginService loginService,
                                //
                                IJobInvokeService jobInvokeService,
                                //
                                boolean isLazy) {
        this.loginService = loginService;
        this.xxlJobProperties = xxlJobProperties;
        this.jobTasksListProperties = jobTasksListProperties;
        this.xxlJobsMediator = xxlJobsMediator;
        this.xxlJobAutoRegsiterService = xxlJobAutoRegsiterService;
        if (null != jobInvokeService) {
            this.jobInvokeService = jobInvokeService;
        }
        this.isLazy = isLazy;
    }

    @Override
    public void afterSingletonsInstantiated() {
        initJobHandlerMethodRepositoryForProperties(getApplicationContext());

        initJobHandlerMethodRepositoryForAnnotation(getApplicationContext());

        // refresh GlueFactory
        GlueFactory.refreshInstance(1);

        // super start
        try {
            super.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // ??????super.start(),???????????????:EmbedServer,????????????:xxl-job-admin????????????,??????????????????????????????????????????????????????,??????,????????????:stopRegistry
        if (isLazy) { // ????????????
            ExecutorRegistryThread.getInstance().toStop();
            logger.warn("????????????????????????:XxlJobSpringExecutor,??????,??????????????????xxl-job-admin????????????");
        } else { // ???????????????
            startAutoRegister();
        }
    }

    public void addTask(JobTaskProperties task) {
        if (null == jobTasksListProperties) {
            jobTasksListProperties = new JobTasksListProperties();
        } else {
            jobTasksListProperties.getTasks().add(task);
        }
    }

    public void startAutoRegister() {
        // ??????
        loginService.login(xxlJobProperties.getUsername(), xxlJobProperties.getPassword());

        // ???????????????
        List<JobTaskProperties> taskList = new ArrayList<>();

        if (null != jobTasksListProperties && null != jobTasksListProperties.getTasks()) {
            taskList.addAll(jobTasksListProperties.getTasks());
        }

        if (null != xxlJobsMediator.getJobs()) {
            taskList.addAll(xxlJobsMediator.getJobs());
        }

        // ????????????
        xxlJobAutoRegsiterService.autoregsiter(taskList);
    }

    public void startRegistry() {
        try {
            Field appnameField = XxlJobExecutor.class.getDeclaredField("appname");
            appnameField.setAccessible(true);
            String appname = (String) appnameField.get(this);
            String addresses = getAddress();

            // ????????????????????????
            ExecutorRegistryThread executorRegistryThread = ExecutorRegistryThread.getInstance();
            Field toStopField = ExecutorRegistryThread.class.getDeclaredField("toStop");
            toStopField.setAccessible(Boolean.TRUE);
            toStopField.set(executorRegistryThread, Boolean.FALSE);

            executorRegistryThread.start(appname, addresses);
            logger.warn("?????????xxl-job-admin??????...");
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
            logger.error("startRegistry fail,message:[{}] ", ignore);
        }
    }

    private void initJobHandlerMethodRepositoryForProperties(ApplicationContext applicationContext) {
        if (getApplicationContext() == null) {
            return;
        }
        if (jobTasksListProperties == null || null == jobTasksListProperties.getTasks() || jobTasksListProperties.getTasks().size() == 0) {
            return;
        }
        for (JobTaskProperties task : jobTasksListProperties.getTasks()) {
            registerTask(task);
        }
    }


    public void registerTask(JobTaskProperties task) {
        String beanName = task.getBeanName();
        Object bean = null;
        Map<Method, JobTaskProperties> annotatedMethods = null;   // referred to ???org.springframework.context.event.EventListenerMethodProcessor.processBean
        try {
            bean = getApplicationContext().getBean(beanName);
            String jobName = task.getExecutorMethod();
            Method[] m = bean.getClass().getDeclaredMethods();
            if (m == null) {
                logger.error("xxl-job method-jobhandler  definition null job [" + jobName + "].");
            }
            annotatedMethods = new HashMap<>();
            for (int i = 0; i < m.length; i++) {
                String methodName = m[i].getName();
                if (methodName.equals(jobName)) {
                    annotatedMethods.put(m[i], task);
                }
            }
        } catch (NoSuchBeanDefinitionException e) {
            logger.error("xxl-job bean-jobhandler resolve error for bean[" + beanName + "].", e);

        } catch (Throwable ex) {
            logger.error("erp-xxl-job method-jobhandler resolve error for bean[" + task + "].", ex);
        }
        if (annotatedMethods == null || bean == null || annotatedMethods.isEmpty()) {
            return;
        }

        for (Map.Entry<Method, JobTaskProperties> methodXxlJobEntry : annotatedMethods.entrySet()) {
            Method executeMethod = methodXxlJobEntry.getKey();
            JobTaskProperties xxlJob = methodXxlJobEntry.getValue();
            if (xxlJob == null) {
                return;
            }
            String oldJobName = xxlJob.getExecutorMethod();
            Class<?> clazz = bean.getClass();
            String methodName = executeMethod.getName();
            if (oldJobName == null || oldJobName.trim().length() == 0) {
                throw new RuntimeException("erp-xxl-job method-jobhandler name invalid, for[" + clazz + "#" + methodName + "] .");
            }

            // ?????????????????????
            oldJobName = getApplicationContext().getEnvironment().resolvePlaceholders(oldJobName);
            String name = oldJobName;
            if (loadJobHandler(name) != null) {
                logger.info("erp-xxl-job jobhandler[" + name + "] naming conflicts.");
                return;
            }
            // ???beanName????????????,??????,??????xxl-job-admin?????????,?????????????????????
            xxlJob.setExecutorMethod(name);

            executeMethod.setAccessible(true);

            // init and destroy
            Method initMethod = null;
            Method destroyMethod = null;

            if (xxlJob.getInitMethod().trim().length() > 0) {
                try {
                    initMethod = clazz.getDeclaredMethod(xxlJob.getInitMethod());
                    initMethod.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("erp-xxl-job method-jobhandler initMethod invalid, for[" + clazz + "#" + methodName + "] .");
                }
            }

            if (xxlJob.getDestroyMethod().trim().length() > 0) {
                try {
                    destroyMethod = clazz.getDeclaredMethod(xxlJob.getDestroyMethod());
                    destroyMethod.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("erp-xxl-job method-jobhandler destroyMethod invalid, for[" + clazz + "#" + methodName + "] .");
                }
            }

            if (null != jobInvokeService) {
                // registry jobhandler
                registJobHandler(name, new help.lixin.xxl.job.handler.MethodJobHandler(jobInvokeService, bean, executeMethod, initMethod, destroyMethod));
            } else {
                registJobHandler(name, new com.xxl.job.core.handler.impl.MethodJobHandler(bean, executeMethod, initMethod, destroyMethod));
            }
        }
    }


    protected void initJobHandlerMethodRepositoryForAnnotation(ApplicationContext applicationContext) {
        if (getApplicationContext() == null) {
            return;
        }

        // init job handler from method
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            Map<Method, XXLJob> annotatedMethods = null;   // referred to ???org.springframework.context.event.EventListenerMethodProcessor.processBean
            try {
                annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(), new MethodIntrospector.MetadataLookup<XXLJob>() {
                    @Override
                    public XXLJob inspect(Method method) {
                        return AnnotatedElementUtils.findMergedAnnotation(method, XXLJob.class);
                    }
                });
            } catch (Throwable ex) {
                logger.error("xxl-job method-jobhandler resolve error for bean[" + beanDefinitionName + "].", ex);
            }
            if (annotatedMethods == null || annotatedMethods.isEmpty()) {
                continue;
            }

            for (Map.Entry<Method, XXLJob> methodXxlJobEntry : annotatedMethods.entrySet()) {

                // ????????????????????????
                Method executeMethod = methodXxlJobEntry.getKey();
                XXLJob xxlJob = methodXxlJobEntry.getValue();
                if (xxlJob == null) {
                    continue;
                }

                String oldJobName = xxlJob.name();
                if (oldJobName.trim().length() == 0) {
                    throw new RuntimeException("xxl-job method-jobhandler name invalid, for[" + bean.getClass() + "#" + executeMethod.getName() + "] .");
                }

                // ?????????????????????
                oldJobName = applicationContext.getEnvironment().resolvePlaceholders(oldJobName);
                String name = oldJobName;
                // ??????????????????,?????????
                if (loadJobHandler(name) != null) {
                    throw new RuntimeException("xxl-job jobhandler[" + name + "] naming conflicts.");
                }

                // ??????execute??????
                executeMethod.setAccessible(true);

                // init and destory
                Method initMethod = null;
                Method destroyMethod = null;

                // ???init??????????????????
                if (xxlJob.init().trim().length() > 0) {
                    try {
                        initMethod = bean.getClass().getDeclaredMethod(xxlJob.init());
                        initMethod.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("xxl-job method-jobhandler initMethod invalid, for[" + bean.getClass() + "#" + executeMethod.getName() + "] .");
                    }
                }

                // ???destroy??????????????????
                if (xxlJob.destroy().trim().length() > 0) {
                    try {
                        destroyMethod = bean.getClass().getDeclaredMethod(xxlJob.destroy());
                        destroyMethod.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("xxl-job method-jobhandler destroyMethod invalid, for[" + bean.getClass() + "#" + executeMethod.getName() + "] .");
                    }
                }

                // ???????????????????????????Job??????????????????
                addToXxlJobsMediator(executeMethod, beanDefinitionName, name);

                if (null != jobInvokeService) {
                    // registry jobhandler
                    registJobHandler(name, new help.lixin.xxl.job.handler.MethodJobHandler(jobInvokeService, bean, executeMethod, initMethod, destroyMethod));
                } else {
                    registJobHandler(name, new com.xxl.job.core.handler.impl.MethodJobHandler(bean, executeMethod, initMethod, destroyMethod));
                }
            }
        }
    }// end


    protected void addToXxlJobsMediator(Method executeMethod, String beanName, String name) {
        JobTaskProperties jobTask = new JobTaskProperties();
        jobTask.setBeanName(beanName);
        jobTask.setExecutorMethod(name);
        XXLJob xxlJob = executeMethod.getAnnotation(XXLJob.class);
        if (TaskStatus.START.equals(xxlJob.enabled())) {
            jobTask.setTaskEnabled(1);
        } else {
            jobTask.setTaskEnabled(0);
        }
        jobTask.setJobDesc(xxlJob.desc());
        jobTask.setInitMethod(xxlJob.init());
        jobTask.setDestroyMethod(xxlJob.destroy());
        jobTask.setScheduleType(xxlJob.scheduleType().getDesc());

        String scheduleValue = xxlJob.scheduleValue();
        if (null != scheduleValue && null != getApplicationContext()) {
            scheduleValue = getApplicationContext().getEnvironment().resolvePlaceholders(scheduleValue);
        }
        jobTask.setScheduleValue(scheduleValue);
        jobTask.setMisfireStrategy(xxlJob.misfireStrategy().getDesc());
        jobTask.setExecutorParam(xxlJob.executorParam());
        jobTask.setExecutorRouteStrategy(xxlJob.routeStrategy().getDesc());
        jobTask.setExecutorBlockStrategy(xxlJob.blockStrategy().getDesc());
        jobTask.setExecutorTimeout(xxlJob.timeout());
        jobTask.setExecutorFailRetryCount(xxlJob.failRetryCount());
        jobTask.setGlueType(xxlJob.glueType());
        jobTask.setAuthor(xxlJob.author());
        jobTask.setAlarmEmail(xxlJob.email());
        xxlJobsMediator.addJob(jobTask);
    }

    protected String getAddress() throws NoSuchFieldException, IllegalAccessException {
        Field ipField = XxlJobExecutor.class.getDeclaredField("ip");
        ipField.setAccessible(true);
        String ip = (String) ipField.get(this);

        Field portField = XxlJobExecutor.class.getDeclaredField("port");
        portField.setAccessible(true);
        int port = (Integer) portField.get(this);


        Field addressField = XxlJobExecutor.class.getDeclaredField("address");
        addressField.setAccessible(true);
        String address = (String) addressField.get(this);

        // fill ip port
        port = port > 0 ? port : NetUtil.findAvailablePort(9999);
        ip = (ip != null && ip.trim().length() > 0) ? ip : IpUtil.getIp();

        // generate address
        if (address == null || address.trim().length() == 0) {
            String ip_port_address = IpUtil.getIpPort(ip, port);   // registry-address???default use address to registry , otherwise use ip:port if address is null
            address = "http://{ip_port}/".replace("{ip_port}", ip_port_address);
        }
        return address;
    }
}
