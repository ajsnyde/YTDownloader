package engine;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import logger.FileLogger;

public class MultiTask extends TaskTask implements ThreadTracker {

  Vector<Task> tasks = new Vector<Task>();
  Vector<Task> tasksRunning = new Vector<Task>();
  public AtomicInteger MAX_THREADS = new AtomicInteger(5);
  private AtomicInteger CUR_THREADS = new AtomicInteger(0);
  private int sleepTime = 50;
  int taskCount;
  int taskComplete = 0;

  public MultiTask(HashMap<String, Object> parameters) {
    super(parameters);
    tasks = (Vector<Task>) parameters.get("tasks");
    if (parameters.get("maxThreads") != null)
      MAX_THREADS.set((int) parameters.get("maxThreads"));
    if (parameters.get("sleepTime") != null)
      sleepTime = (int) parameters.get("sleepTime");
  }

  @Override
  public void run() {
    increaseParent();
    taskCount = tasks.size();
    while (tasks.size() > 0) {
      try {
        maintainThreadCount();
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
      }
    }
    decreaseParent();
  }

  @Override
  public void kill() {
    tasks.clear();
    // TODO: fix 'tasksRunning' memory leak - remove finished Tasks
    for (Task task : tasksRunning)
      if (task != null)
        task.kill();
    decreaseParent();
  }

  private void maintainThreadCount() {
    while (TaskManager.getInstance().canIncrease() && canIncrease() && tasks.size() > 0) {
      tasks.get(0).parameters.put("parent", this);
      tasksRunning.add(tasks.firstElement());
      new Thread(tasks.remove(0)).start();
      FileLogger.logger().log(Level.FINEST, CUR_THREADS.get() + "");
    }
  }

  public void decreaseThreadCount() {
    CUR_THREADS.decrementAndGet();
    taskComplete++;
    updateProgress((int) (100.0 * taskComplete / taskCount));
  }

  public void increaseThreadCount() {
    CUR_THREADS.incrementAndGet();
  }

  public int getThreadCount() {
    return CUR_THREADS.get();
  }

  public boolean canIncrease() {
    return CUR_THREADS.get() < MAX_THREADS.get();
  }
}
