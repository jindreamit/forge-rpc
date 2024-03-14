package forge.rpc;

public class BeanFactory {
    public static<T> T getInstance(Class<T> tClass){
        try {
            return tClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
        }
        throw new RuntimeException();
    }
}
