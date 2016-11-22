package engine;

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
        updateProgress((int) (100 * ((double) (i + 1) / tasks.size())));
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
}
