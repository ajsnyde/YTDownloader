package engine;

import java.util.HashMap;
import java.util.Vector;

public class TaskBuilder {

  public enum TASK {
    TASKDOWNLOADMETA, TASKDOWNLOADVIDEO, TASKGETALBUM, TASKTASK, TASKSPLITBYALBUM, MULTITASK
  }

  Task mainTask;
  HashMap<String, Object> mainParameters;
  HashMap<String, Object> lastParameters;

  public TaskBuilder() {

  }

  public TaskBuilder createSequentialTask() {
    mainParameters = new HashMap<String, Object>();
    mainParameters.put("tasks", new Vector<Task>());
    mainTask = new TaskTask(mainParameters);
    lastParameters = mainParameters;
    return this;
  }

  public TaskBuilder createMultiTask() {
    mainParameters = new HashMap<String, Object>();
    mainParameters.put("tasks", new Vector<Task>());
    mainTask = new MultiTask(mainParameters);
    lastParameters = mainParameters;
    return this;
  }

  // default to stale parameters
  public TaskBuilder addTask(TASK type) {
    return addTask(type, false);
  }

  public TaskBuilder addTask(TASK type, boolean fresh) {
    if (fresh)
      lastParameters = new HashMap<String, Object>();
    switch (type) {
    case TASKDOWNLOADMETA:
      ((Vector<Task>) mainParameters.get("tasks")).add(new TaskDownloadMeta(lastParameters));
      break;
    case TASKDOWNLOADVIDEO:
      ((Vector<Task>) mainParameters.get("tasks")).add(new TaskDownloadVideo(lastParameters));
      break;
    case TASKGETALBUM:
      ((Vector<Task>) mainParameters.get("tasks")).add(new TaskGetAlbum(lastParameters));
      break;
    case TASKTASK:
      ((Vector<Task>) mainParameters.get("tasks")).add(new TaskTask(lastParameters));
      break;
    case TASKSPLITBYALBUM:
      ((Vector<Task>) mainParameters.get("tasks")).add(new TaskSplitByAlbum(lastParameters));
      break;
    case MULTITASK:
      ((Vector<Task>) mainParameters.get("tasks")).add(new MultiTask(lastParameters));
      break;
    default:
      throw new IllegalArgumentException(String.format("Unsupported task type", mainTask));
    }
    return this;
  }

  public TaskBuilder addTask(Task task) {
    lastParameters = task.parameters;
    ((Vector<Task>) mainParameters.get("tasks")).add(task);
    return this;
  }

  public TaskBuilder put(String key, Object value) {
    lastParameters.put(key, value);
    return this;
  }

  public Object get(String key) {
    return lastParameters.get(key);
  }

  public Task build() {
    return mainTask;
  }
}
