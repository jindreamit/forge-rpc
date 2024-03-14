package forge.rpc.impl.block;

import forge.rpc.interfaces.ForgeRpcRequest;

import java.lang.reflect.Method;

public class DefaultForgeRpcRequest implements ForgeRpcRequest {
    private final Method method;
    private final Object[] args;

    public DefaultForgeRpcRequest(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

    public Method getMethod() {
        return method;
    }
}
