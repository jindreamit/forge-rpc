package forge.rpc.impl.block;

import forge.rpc.BeanFactory;
import forge.rpc.interfaces.ForgeRpcResponse;

public class DefaultForgeRpcResponse implements ForgeRpcResponse {
    private final Object data;

    public DefaultForgeRpcResponse(Object data) {
        this.data = data;
    }

    public Class<?> getDataClass() {
        return data.getClass();
    }
}
