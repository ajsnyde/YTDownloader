package engine;

import tasks.TaskBuilder;
import tasks.TaskManager;
import tasks.TaskBuilder.TASK;

public class Test {

  public static void main(String[] args) {
    /*
     * 
     * System.out.println("Starting setup at " + Instant.now()); // grab all Metadatas from a url HashMap<String, Object> metaParameters = new HashMap<String, Object>(); metaParameters.put("url",
     * "https://www.youtube.com/watch?v=pcGHKzhmPvk");
     * 
     * Vector<Task> tasktasks = new Vector<Task>();
     * 
     * new TaskDownloadMeta(metaParameters).run(); for (Metadata meta : (Vector<Metadata>) metaParameters.get("metaDatas")) { HashMap<String, Object> tasktaskParameters = new HashMap<String,
     * Object>(); Vector<Task> subtasks = new Vector<Task>();
     * 
     * HashMap<String, Object> subParameters = new HashMap<String, Object>(); subParameters.put("url", meta.url); subParameters.put("metadata", meta);
     * 
     * TaskDownloadVideo download = new TaskDownloadVideo(subParameters); TaskGetAlbum album = new TaskGetAlbum(subParameters); TaskSplitByAlbum split = new TaskSplitByAlbum(subParameters);
     * 
     * subtasks.add(download); subtasks.add(album); subtasks.add(split);
     * 
     * tasktaskParameters.put("tasks", subtasks);
     * 
     * tasktasks.add(new TaskTask(tasktaskParameters)); } HashMap<String, Object> multitaskParameters = new HashMap<String, Object>();
     * 
     * multitaskParameters.put("tasks", tasktasks); multitaskParameters.put("maxThreads", 200);
     * 
     * System.out.println("Done with setup at " + Instant.now());
     * 
     * TaskManager.getInstance().addTask(new MultiTask(multitaskParameters));
     */
    // Similar to the code above, this uses the builder and demonstrates how much easier it is to perform roughly the same goal - create a multitask made up of many sequential tasks.
    TaskBuilder builder = new TaskBuilder().createMultiTask().put("maxThreads", 25);
    for (int i = 0; i < 100; ++i)
      builder.addTask(new TaskBuilder().createSequentialTask().addTask(TASK.TASKDOWNLOADMETA, true).put("url", "https://www.youtube.com/watch?v=pcGHKzhmPvk").addTask(TASK.TASKDOWNLOADVIDEO)
          .addTask(TASK.TASKGETALBUM).addTask(TASK.TASKSPLITBYALBUM).build());
    TaskManager.getInstance().addTask(builder.build());
  }
}
