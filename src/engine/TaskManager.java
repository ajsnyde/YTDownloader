package engine;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import logger.FileLogger;

public class TaskManager implements Runnable, ThreadTracker {
  Vector<Task> tasks = new Vector<Task>();
  public static AtomicInteger MAX_THREADS = new AtomicInteger(200);
  private static AtomicInteger CUR_THREADS = new AtomicInteger(0);
  private static TaskManager instance = null;

  public static TaskManager getInstance() {
    if (instance == null) {
      instance = new TaskManager();
    }
    return instance;
  }

  protected TaskManager() {
    new Thread(this).start();
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  @Override
  public void run() {
    for (;;) {
      try {
        maintainThreadCount();
        Thread.sleep(50);
      } catch (InterruptedException e) {
      }
    }
  }

  private void maintainThreadCount() {
    while (canIncrease() && tasks.size() > 0) {
      new Thread(tasks.remove(0)).start();
      FileLogger.logger().info("Threadcount at " + getThreadCount() + " out of " + MAX_THREADS.get());
    }
  }

  public void decreaseThreadCount() {
    CUR_THREADS.decrementAndGet();
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
