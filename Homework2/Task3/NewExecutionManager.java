import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class NewExecutionManage implements ExecutionManage {
    private AtomicInteger finishedTasks;
    private AtomicInteger faildTasks;
    private AtomicInteger interruptedTasks;

    private boolean isFinished;
    private boolean isInterrupted;
    private static Deque<Thread> queueThreads;

    public NewExecutionManage(){
        isFinished = false;
        isInterrupted = false;
        queueThreads = new ArrayDeque<Thread>();
    }

    @Override
    public Context execute(Runnable callback, Deque<Runnable> queueTasks) {
        Thread main = new Thread(() -> {
            for(Runnable task : queueTasks){
                if(!isInterrupted){
                    try {
                        Thread tempThread = new Thread(task);
                        queueThreads.addLast(tempThread);
                        tempThread.start();
                    }
                    catch (RuntimeException runtimeException){
                        faildTasks.incrementAndGet();
                    }
                }
                else{
                    interruptedTasks.incrementAndGet();
                }
            }

            for (Thread thread : queueThreads){
                try {
                    thread.join();
                    finishedTasks.incrementAndGet();
                }
                catch(InterruptedException interruptedException) {
                    interruptedTasks.incrementAndGet();
                }
            }

            callback.run();
            isFinished = true;
        });

        main.start();
        return new NewContext();
    }
}
