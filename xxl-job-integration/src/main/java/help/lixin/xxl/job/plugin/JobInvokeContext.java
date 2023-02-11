package help.lixin.xxl.job.plugin;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class JobInvokeContext {
    private Object target;
    private Method method;
    private Map<String,Object> others = new HashMap<>();

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Map<String, Object> getOthers() {
        return others;
    }

    public void setOthers(Map<String, Object> others) {
        this.others = others;
    }
}
