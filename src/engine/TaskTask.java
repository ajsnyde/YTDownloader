package engine;

import java.util.HashMap;
import java.util.Vector;
import tables.DownloadTableModel;

//This essentially runs multiple Tasks in sequential order using a single thread.

//TODO: rename to SequentialTask or TaskSequential (and TaskMulti)
//TODO: add vector<Task> to ThreadTracker interface
//TODO: track down and kill() every thread. Every. Last. One.
public class TaskTask extends Task {

  public TaskTask(HashMap<String, Object> parameters) {
    this.parameters = parameters;

    // TODO: set the model function in the abstract class
    if (parameters.get("model") != null)
      ((DownloadTableModel) parameters.get("model")).addTask(this);
  }

  @Override
  public void run() {
    increaseParent();
    try {
      for (int i = 0; i < ((Vector<Task>) parameters.get("tasks")).size(); ++i) {
        ((Vector<Task>) parameters.get("tasks")).get(i).run();
        updateProgress((int) (100 * ((double) (i + 1) / ((Vector<Task>) parameters.get("tasks")).size())));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      decreaseParent();
    }
  }
}
