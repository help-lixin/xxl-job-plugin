package help.lixin.xxl.job.handler;

import help.lixin.xxl.job.plugin.IJobInvokeService;
import help.lixin.xxl.job.plugin.JobInvokeContext;

import java.lang.reflect.Method;

/**
 * 自定义handler
 */
public class MethodJobHandler extends com.xxl.job.core.handler.IJobHandler {
    private final Object target;
    private final Method method;
    private Method initMethod;
    private Method destroyMethod;

    private IJobInvokeService jobInvokeService;

    public MethodJobHandler(IJobInvokeService jobInvokeService, Object target, Method method, Method initMethod, Method destroyMethod) {
        this.target = target;
        this.method = method;
        this.initMethod = initMethod;
        this.destroyMethod = destroyMethod;
        this.jobInvokeService = jobInvokeService;
    }

    @Override
    public void execute() throws Exception {
        JobInvokeContext ctx = new JobInvokeContext();
        ctx.setMethod(method);
        ctx.setTarget(target);
        jobInvokeService.execute(ctx);
    }


    @Override
    public void init() throws Exception {
        if (initMethod != null) {
            initMethod.invoke(target);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (destroyMethod != null) {
            destroyMethod.invoke(target);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "[" + target.getClass() + "#" + method.getName() + "]";
    }
}
