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
        if(currentResult != null){
            return currentResult;
        }
        if(isCalled){
            synchronized (this){
                while(currentResult == null){
                    try{
                        this.wait();
                    }
                    catch(RuntimeException runtimeException){
                        throw new CallException();
                    }
                }
            }
        }
        else{
            isCalled = true;
            try {
                currentResult = callable.call();
                synchronized(this){
                    this.notifyAll();   
                }
                return currentResult; 
            }
            catch(RuntimeException runtimeException){
                throw new CallException();
            }
        }
    }
}
