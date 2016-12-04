package tasks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.HashMap;
import java.util.Vector;

import engine.ThreadTracker;

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
  public Vector<ActionListener> onStart = new Vector<ActionListener>();
  public Vector<ActionListener> onCompletion = new Vector<ActionListener>();
  public Vector<ActionListener> onUpdate = new Vector<ActionListener>();

  void updateProgress() {
    updateProgress(progress);
  }

  void updateProgress(double in) {
    updateProgress((int) in);
  }

  void updateProgress(int in) {
    progress = (double) in;
    for (ActionListener listener : onUpdate)
      listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
    if (parameters.get("parent") != null)
      ((Task) parameters.get("parent")).updateProgress();
  }

  void decreaseParent() {
    if ((parameters.get("parent")) != null && parameters.get("parent") instanceof ThreadTracker)
      ((ThreadTracker) parameters.get("parent")).decreaseThreadCount();
    // else
    for (ActionListener listener : onCompletion)
      listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
    TaskManager.getInstance().decreaseThreadCount();
  }

  void increaseParent() {
    if ((parameters.get("parent")) != null && parameters.get("parent") instanceof ThreadTracker)
      ((ThreadTracker) parameters.get("parent")).increaseThreadCount();
    // else
    TaskManager.getInstance().increaseThreadCount();
  }

  public abstract void kill();
}
