### 1. 项目背景
xxl-job是比较流的定时任务管理器,但是,也有一些烦锁的事情,就是需要人工在后台(xxl-job-admin)添加执行器和任务,能否在项目启动时候自动注册呢?该组件主要解决这个功能,解决烦锁的人工干预过程.

### 2. 如何集成?
1) 添加依赖

```
<dependency>
    <groupId>help.lixin.xxl.job</groupId>
    <artifactId>xxl-job-integration</artifactId>
    <version>${project.version}</version>
</dependency>
```

2) 配置任务

```
# 方式一: 通过注解配置
help.lixin.xxl.job.annotation.@XXLJob(name = "${job.name}", scheduleValue = "0/30 * * * * ?", desc = "Ebay订单下载")
public void test1() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    System.out.println(simpleDateFormat.format(new Date()) + " test-1 hello world-----1!!!!");
}


# 方式二: 通过配置文件配置
xxl:
  tasks:
#    - beanName: testJob
#      executorMethod: test1
#      jobDesc: Ebay订单下载
#      scheduleValue: 0/30 * * * * ?
    - beanName: testJob
      executorMethod: test2
      jobDesc: Shopify订单下载
      scheduleValue: 0/40 * * * * ?
```

3) appName定制

> 当xxl-job是Global级,而,微服务可能是划分不同的环境(比如:order-service微服务,在环境:jd/taobao都有部署一套,而xxl-job只有一套),这时候,要对定时任务进行分组处理.  

```
public interface IAppNameProcess {

    /**
     * @param oldAppName app名称
     * @return 新的app名称
     */
    String process(String oldAppName);
}
```

4) MethodJobHandler定制
> xxl-job-admin会回调你注册的任务,如果你对任务有一些特殊处理,比较,要用多线程去处理,又或者,每一个多线程要配置租户信息到线程上下文,你可以实现:IJobInvokeService接口,并把它交给Spring容器即可. 

```
public class JobInvokeContext {
    private Object target;
    private Method method;
    private Map<String,Object> others = new HashMap<>();
} // end JobInvokeContext

public interface IJobInvokeService {
    void execute(JobInvokeContext ctx) throws Exception;
} // end IJobInvokeService
```

### 3. 延迟加载机制
```
xxl.job.isLazy=true

# 在某些场景下,我们的微服务,可能还没有来得及全部初始化(比如:数据源),可是,进程启动时,已经向xxl-job-admin进行了注册,正好xxl-job-admin也开始派发了任务.
# 这时候,可能会出现错误,你可以配置上面这个配置项,这个配置项的目的是等待一个事件(BootstrapFinishEvent)触发后,才真正的向xxl-job-admin进行注册.     
```
### 4. 代码贡献者
感谢[清源](https://github.com/caiqingyuan95),对xxl-job-plugin部份代码的贡献.  
