import java.util.ArrayDeque;
import java.util.Deque;

public class ScalableThreadPool implements ThreadPool {
    private final Deque<Runnable> queueTasks = new ArrayDeque<>();
    private Deque<ScalableThreadPoolWorker> queueThreads;
    int minNumOfThreads;
    int maxNumOfThreads;

    public ScalableThreadPool(int minNumOfThreads, int maxNumOfThreads){
        this.minNumOfThreads = minNumOfThreads;
        this.maxNumOfThreads = maxNumOfThreads;
        this.queueThreads = new ArrayDeque<ScalableThreadPoolWorker>(this.minNumOfThreads);
    }

    @Override
    public void start() {
        for(ScalableThreadPoolWorker thread : this.queueThreads){
            thread.start();
        }
    }

    private boolean isGreaterThanMax(){
        return this.queueThreads.size() > this.maxNumOfThreads;
    }
    private boolean isLesserThanMin(){
        return this.queueThreads.size() < this.minNumOfThreads;
    }
    @Override
    public void execute(Runnable runnable) {
        synchronized(this.queueThreads){
            queueTasks.addLast(runnable);
            if(!queueTasks.isEmpty()&& !isGreaterThanMax()){
                ScalableThreadPoolWorker tempWorker = new ScalableThreadPoolWorker();
                this.queueThreads.addLast(tempWorker);
                tempWorker.start();
            }
            else{
                notify();
            }
        }
    }
    private class ScalableThreadPoolWorker extends Thread{
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
                synchronized (this.queueThreads){
                    if(!isLesserThanMin() && queueTasks.isEmpty()){
                        this.queueThreads.removeFirst();
                    }
                }
            }
        }
    }
}
