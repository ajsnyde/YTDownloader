package engine;

import java.util.Vector;

public interface ThreadTracker {

  Vector<Task> tasks = new Vector<Task>();

  public void decreaseThreadCount();

  public void increaseThreadCount();

  public int getThreadCount();

  public boolean canIncrease();
}
