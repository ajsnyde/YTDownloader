package engine;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logger.FileLogger;

//should accept ONE url and add a number of Metadatas to parameters
public class TaskDownloadMeta extends Task {

  final Pattern progressNumber = Pattern.compile("(\\d+) of (\\d+)");

  public TaskDownloadMeta(HashMap<String, Object> parameters) {
    this.parameters = parameters;
  }

  // requires url
  // adds metaDataFiles to parameters
  @Override
  public void run() {
    increaseParent();
    try {

      if (parameters.get("metaDataFiles") == null)
        parameters.put("metaDataFiles", new Vector<File>());
      if (parameters.get("metaDatas") == null)
        parameters.put("metaDatas", new Vector<File>());

      HashMap<String, Object> metaParameters = new HashMap<String, Object>();
      metaParameters.put("ExeLocation", "resources/youtube-dl.exe");
      metaParameters.put("ExeArguments", "--write-info-json --skip-download -i -o \"Downloads/%(uploader)s/%(uploader)s - %(title)s.%(ext)s\" " + parameters.get("url"));

      Execute execute = new Execute(metaParameters);
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

      // After grabbing metadata files, process into Metadata objects and add to parameters
      // TODO: add progress increase
      for (File metaFile : (Vector<File>) parameters.get("metaDataFiles")) {
        ((Vector<Metadata>) (parameters.get("metaDatas"))).addElement(new Metadata(metaFile));
        FileLogger.logger().log(Level.FINEST, "Creating new Metadata: " + metaFile);
      }
      // if only a single metadata file is created, assume that this is a one-video job:
      if (((Vector<File>) parameters.get("metaDataFiles")).size() == 1)
        parameters.put("metadata", new Metadata(((Vector<File>) parameters.get("metaDataFiles")).get(0)));
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
      ((Vector<File>) (parameters.get("metaDataFiles"))).add(new File(line.substring(54)));
      FileLogger.logger().log(Level.FINEST, "New metadata file detected");
    }
  }
}
