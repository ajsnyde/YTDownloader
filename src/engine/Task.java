package engine;

import java.time.Instant;
import java.util.HashMap;

import javax.swing.JProgressBar;

// A task is fundamentally a set of instructions to be completed.

public abstract class Task implements Runnable {
  public String name;
  public String size;
  public Double progress = 0.0;
  public String status;
  public String speed;
  public HashMap<String, Object> parameters; // contains parameters for Task to use
  public Instant timeCreated = Instant.now();
  public Instant timeStarted;
  public Instant timeCompleted;
  public String eta;
  public int priority;

  void updateProgress(int in) {
    if (parameters.get("progressBar") != null) {
      ((JProgressBar) parameters.get("progressBar")).setIndeterminate(false);
      ((JProgressBar) parameters.get("progressBar")).setValue(in);
    }
  }

  void decreaseParent() {
    if (((ThreadTracker) parameters.get("parent")) != null)
      ((ThreadTracker) parameters.get("parent")).decreaseThreadCount();
    // else
    TaskManager.getInstance().decreaseThreadCount();
  }

  void increaseParent() {
    if (((ThreadTracker) parameters.get("parent")) != null)
      ((ThreadTracker) parameters.get("parent")).increaseThreadCount();
    // else
    TaskManager.getInstance().increaseThreadCount();
  }
}
