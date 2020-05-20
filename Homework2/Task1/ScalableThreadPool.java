import java.util.ArrayDeque;
import java.util.Deque;

public class ScalableThreadPool implements ThreadPool {
    private static final Deque<Runnable> queueTasks = new ArrayDeque<>();
    private static Deque<ScalableThreadPoolWorker> queueThreads;
    int minNumOfThreads;
    int maxNumOfThreads;

    public ScalableThreadPool(int minNumOfThreads, int maxNumOfThreads){
        this.minNumOfThreads = minNumOfThreads;
        this.maxNumOfThreads = maxNumOfThreads;
        queueThreads = new ArrayDeque<ScalableThreadPoolWorker>(this.minNumOfThreads);
    }

    @Override
    public void start() {
        for(ScalableThreadPoolWorker thread : queueThreads){
            thread.start();
        }
    }

    private boolean isGreaterThanMax(){
        return queueThreads.size() > this.maxNumOfThreads;
    }
    private boolean isLesserThanMin(){
        return queueThreads.size() < this.minNumOfThreads;
    }
    @Override
    public void execute(Runnable runnable) {
        synchronized(queueThreads){
            queueTasks.addLast(runnable);
            if(!queueTasks.isEmpty()&& !isGreaterThanMax()){
                ScalableThreadPoolWorker tempWorker = new ScalableThreadPoolWorker();
                queueThreads.addLast(tempWorker);
                tempWorker.start();
            }
            else if(isGreaterThanMax()){
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
                synchronized (queueThreads){
                    if(!isLesserThanMin() && queueTasks.isEmpty()){
                        queueThreads.removeFirst();
                    }
                }
            }
        }
    }
}
