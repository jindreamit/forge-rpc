package forge.rpc.interfaces;

public interface ForgeRpcReceiver<P,R> {
    R processRequest(P requestData);
}
