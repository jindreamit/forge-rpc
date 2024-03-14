package forge.rpc.interfaces;

public interface ForgeRpcCodec<T> {
    T deserialize(byte[] data);
    byte[] serialize(T obj);
}
