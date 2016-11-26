package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;
import tables.DownloadTableModel;

//This essentially runs multiple Tasks in sequential order using a single thread.

//TODO: rename to SequentialTask or TaskSequential (and TaskMulti)
public class TaskTask extends Task {
  private Task currentTask;
  private Vector<Task> tasks;

  public TaskTask(HashMap<String, Object> parameters) {
    this.parameters = parameters;
    tasks = ((Vector<Task>) parameters.get("tasks"));
    // TODO: set the model function in the abstract class
    if (parameters.get("model") != null)
      ((DownloadTableModel) parameters.get("model")).addTask(this);
  }

  @Override
  public void run() {
    increaseParent();
    try {
      for (int i = 0; i < tasks.size(); ++i) {
        currentTask = tasks.get(i);
        currentTask.run();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      decreaseParent();
    }
  }

  @Override
  public void kill() {
    tasks.clear();
    currentTask.kill();
  }

  // Note: ignores input! Still should be accepting the int parameter for override because other methods carry over to this one.
  @Override
  void updateProgress(int in) {
    status = currentTask.status;
    progress = (((double) tasks.indexOf(currentTask)) / (double) tasks.size() * 100.0) + (currentTask.progress / tasks.size());
    // System.out.println(tasks.indexOf(currentTask) + "/" + tasks.size() + " * 100 + (" + currentTask.progress + "/" + tasks.size() + ")");
    for (ActionListener listener : onUpdate)
      listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
    if (parameters.get("parent") != null)
      ((Task) parameters.get("parent")).updateProgress();
  }
}
