import java.util.concurrent.Callable;

public class Task<T> {
    private T currentResult = null;
    private Callable<? extends T> callable;
    public Task(Callable<? extends T> callable){
        this.callable = callable;
    }
    public T get() throws Exception {
        if(currentResult != null){
            return currentResult;
        }
        synchronized (callable){
            try {
                currentResult = callable.call();
            }
            catch(RuntimeException runtimeException){
                throw new CallException();
            }
            return currentResult;
        }
    }
}
