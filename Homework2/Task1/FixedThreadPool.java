import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class FixedThreadPool implements ThreadPool {
    private static final Deque<Runnable> queueTasks = new ArrayDeque<>();
    private static Deque<FixedThreadPoolWorker> queueThreads;
    private int numOfThreads;

    public FixedThreadPool(int numOfThreads){
        this.numOfThreads = numOfThreads;
        queueThreads = new ArrayDeque<FixedThreadPoolWorker>(numOfThreads);
    }
    @Override
    public void start() {
        for(FixedThreadPoolWorker thread : queueThreads){
            thread.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (queueTasks){
            queueTasks.addLast(runnable);
            notify();
        }
    }
    private class FixedThreadPoolWorker extends Thread{
        public void run(){
            while(true){
                synchronized (queueTasks){
                    while(queueTasks.isEmpty()){
                        try{
                            wait();
                        }
                        catch (InterruptedException ignored){
                        }
                    }
                    queueTasks.removeFirst().run();
                }
            }
        }
    }
}
