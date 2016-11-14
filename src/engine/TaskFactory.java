// experimental factory - not sure if it will be of any use or benefit

package engine;

import java.util.HashMap;

public class TaskFactory {

  public enum Type {
    MULTITASK, TASKTASK, TASKDOWNLOADVIDEO
  }

  private static TaskFactory instance = null;

  public static TaskFactory getInstance() {
    if (instance == null) {
      instance = new TaskFactory();
    }
    return instance;
  }

  protected TaskFactory() {
  }

  // Dunno how I should go about cloning the parameters. This factory will need to get pretty complicated to facilitate each type's options.
  public Task createTask(HashMap<String, Object> parameters, Type type) {
    switch (type) {
    case MULTITASK:
      return new MultiTask(parameters);
    case TASKTASK:
      return new TaskTask(parameters);
    case TASKDOWNLOADVIDEO:
      return new TaskDownloadVideo(parameters);
    default:
      return null;
    }

  }
}
