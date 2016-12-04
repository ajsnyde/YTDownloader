package engine;

import java.util.Vector;

import tasks.Task;

public interface ThreadTracker {

  Vector<Task> tasks = new Vector<Task>();

  public void decreaseThreadCount();

  public void increaseThreadCount();

  public int getThreadCount();

  public boolean canIncrease();
}
