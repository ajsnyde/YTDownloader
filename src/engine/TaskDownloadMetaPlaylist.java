package engine;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logger.FileLogger;

//should accept ONE url with parameters for downloading (meta, video, etc.)
public class TaskDownloadMetaPlaylist extends Task {

  final Pattern progressNumber = Pattern.compile("(\\d+) of (\\d+)");

  public TaskDownloadMetaPlaylist(HashMap<String, Object> parameters) {
    this.parameters = parameters;
  }

  @Override
  public void run() {
    increaseParent();
    try {
      Execute execute = new Execute(parameters);
      Thread thread = new Thread(execute, "test");
      thread.start();
      synchronized (execute) {
        try {
          execute.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      String line;
      while ((line = execute.input.readLine()) != null) {
        parseLine(line);
        FileLogger.logger().log(Level.FINEST, line);
      }
      updateProgress(100);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      decreaseParent();
    }
  }

  void parseLine(String line) {
    if (line.contains("[download]")) {
      try {
        Matcher m = progressNumber.matcher(line);
        if (m.find()) {
          progress = 100 * (Double.parseDouble(m.group(1)) / Double.parseDouble(m.group(2)));
          updateProgress(progress.intValue());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (line.contains("[info] Writing video description metadata as JSON to: ")) {
      if (parameters.get("metaDataFiles") == null)
        parameters.put("metaDataFiles", new Vector<File>());
      ((Vector<File>) (parameters.get("metaDataFiles"))).add(new File(line.substring(54)));
    }
  }
}
