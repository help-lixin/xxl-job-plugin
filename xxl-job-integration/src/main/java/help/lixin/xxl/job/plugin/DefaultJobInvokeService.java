package help.lixin.xxl.job.plugin;

import java.lang.reflect.Method;

public class DefaultJobInvokeService implements IJobInvokeService {

    @Override
    public void execute(JobInvokeContext ctx) throws Exception {
        Object target = ctx.getTarget();
        Method method = ctx.getMethod();
        // 自己写一个实现,扔给Spring容器就好了,可以是一个多线程的处理,或者其它的东西之类的.
        exec(target, method);
    }

    protected void exec(Object target, Method method) throws Exception {
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length > 0) {
            method.invoke(target, new Object[paramTypes.length]);       // method-param can not be primitive-types
        } else {
            method.invoke(target);
        }
    }
}
