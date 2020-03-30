import java.util.Deque;

public interface ExecutionManage {
    Context execute (Runnable callback, Deque<Runnable> queueTasks);
}
