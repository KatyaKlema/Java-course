package ru.sbt.mipt.oop.adapters;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

class NewExecutionManage implements ExecutionManage {
    private AtomicInteger finishedTasks = new AtomicInteger(0);
    private AtomicInteger faildTasks = new AtomicInteger(0);
    private AtomicInteger interruptedTasks = new AtomicInteger(0);

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

    public class NewContext implements Context {
        @Override
        public int getCompleteTaskCount() {
            return finishedTasks.get();
        }

        @Override
        public int getFailedTaskCount() {
            return faildTasks.get();
        }

        @Override
        public int getInterruptedTaskCount() {
            return interruptedTasks.get();
        }

        @Override
        public void interrupt() {
            isInterrupted = true;
        }

        @Override
        public boolean isFinished() {
            return isFinished;
        }
    }
}
