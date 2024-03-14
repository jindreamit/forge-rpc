package forge.rpc.impl.block;

import forge.rpc.interfaces.*;

import java.lang.reflect.Method;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;


public class BlockForgeRpcImpl implements ForgeRpcInvokeFunction, ForgeRpcReceiver<DefaultForgeRpcRequest, DefaultForgeRpcResponse> {
    private final ForgeRpcCodec<DefaultForgeRpcRequest> requestCodec;
    private final ForgeRpcCodec<DefaultForgeRpcResponse> responseCodec;
    private final BiFunction<Class<?>, Method, Object> methodConsumeFunction;
    private final Function<byte[], Future<DefaultForgeRpcResponse>> packetConsumer;

    public BlockForgeRpcImpl(ForgeRpcCodec<DefaultForgeRpcRequest> requestCodec, ForgeRpcCodec<DefaultForgeRpcResponse> responseCodec, BiFunction<Class<?>, Method, Object> methodConsumeFunction, Consumer<byte[]> packetConsumer) {
        this.requestCodec = requestCodec;
        this.responseCodec = responseCodec;
        this.methodConsumeFunction = methodConsumeFunction;
        this.packetConsumer = packetConsumer;
    }

    private boolean checkRequest(ForgeRpcRequest request) {
        return request instanceof DefaultForgeRpcRequest;
    }

    @Override
    public ForgeRpcResponse invoke(ForgeRpcRequest request) {
        if (!checkRequest(request)) {
            throw new IllegalArgumentException(request.getClass().getSimpleName());
        }

        DefaultForgeRpcRequest defaultForgeRpcRequest = (DefaultForgeRpcRequest) request;

        return doInvoke(defaultForgeRpcRequest);
    }

    private DefaultForgeRpcResponse doInvoke(DefaultForgeRpcRequest request) {
        final byte[] requestData = this.requestCodec.serialize(request);
        final Future<DefaultForgeRpcResponse> future = this.packetConsumer.apply(requestData);
        DefaultForgeRpcResponse r = null;
        while (!future.isDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        try {
            return future.get();
        } catch (Exception e) {
            System.err.println(e.getCause());
        }
        return null;
    }

    @Override
    public DefaultForgeRpcResponse processRequest(DefaultForgeRpcRequest requestData) {
        final Class<?> declaringClass = requestData.getMethod().getDeclaringClass();
        final Method method = requestData.getMethod();
        Object result = this.methodConsumeFunction.apply(declaringClass, method);
        //TODO 发包返回结果
        return null;
    }
}
