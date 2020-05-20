import java.util.concurrent.Callable;

public class Task<T> {
    private T currentResult = null;
    private Callable<? extends T> callable;
    private Boolean isCalled;
    public Task(Callable<? extends T> callable){
        this.callable = callable;
        this.isCalled = false;
    }
    public T get() throws Exception {
        try{
            if(!isCalled){
                synchronized(this){
                    if(isCalled){
                        return currentResult;   
                    }
                    currentResult = callable.call();
                    isCalled = true;
                }
            }
            return currentResult;   
        }
        catch(RuntimeException runtimeException){
            throw new CallException();
        }
    }
}
