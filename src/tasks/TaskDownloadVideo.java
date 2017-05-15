package tasks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logger.FileLogger;
import tables.DownloadTableModel;
import youtubeObjects.Metadata;

//should accept ONE url video to download from
//adds video file location
public class TaskDownloadVideo extends Task {

  final Pattern downloadStats = Pattern.compile("(\\d+\\.?\\d+)% of (\\d+\\.\\d+[MK]iB)\\s+at\\s+(\\d+\\.\\d+[MK]iB\\/s)\\s+ETA\\s+(\\d+:\\d+:?\\d*)");

  final Pattern quotation = Pattern.compile("\"([^\"]+)\"");

  final Pattern progressNumber = Pattern.compile("(\\d+) of (\\d+)");
  Thread thread;
  Execute execute;

  public TaskDownloadVideo(HashMap<String, Object> parameters) {
    this.parameters = parameters;
    if (parameters.get("model") != null)
      ((DownloadTableModel) parameters.get("model")).addTask(this);
  }

  // requires combination of extractAudio, url, metadata
  // may add audioLocation
  @Override
  public void run() {
    increaseParent();
    try {
      if (parameters.get("downloadLocation") == null)
        parameters.put("downloadLocation", "Downloads\\");

      FileLogger.logger().log(Level.FINEST, "Downloading!");
      status = "Starting";
      // Check for existing mp3 using metadata file - if both exist, skip download
      if (parameters.get("metadata") == null || !new File(parameters.get("downloadLocation") + ((Metadata) parameters.get("metadata")).uploader + "\\"
          + ((Metadata) parameters.get("metadata")).uploader + " - " + ((Metadata) parameters.get("metadata")).title + ".mp3").exists()) {
        status = "Downloading video";
        HashMap<String, Object> downloadParameters = new HashMap<String, Object>();
        downloadParameters.put("ExeLocation", "resources/youtube-dl.exe");
        downloadParameters.put("ExeArguments", (parameters.get("extractAudio") == "true" ? "-x --audio-format mp3" : "") + " -o \"" + parameters.get("downloadLocation")
            + "%(uploader)s/%(uploader)s - %(title)s.%(ext)s\" " + parameters.get("url"));
        execute = new Execute(downloadParameters);
        thread = new Thread(execute, "test");
        thread.start();
        synchronized (execute) {
          execute.wait();
        }
        String line;
        while ((line = execute.input.readLine()) != null) {
          parseLine(line);
          FileLogger.logger().log(Level.FINEST, line);
        }

      } else {
        status = "Pre-existing audio file detected - skipping download!";
        parameters.put("audioLocation", new File(parameters.get("downloadLocation") + ((Metadata) parameters.get("metadata")).uploader + "\\" + ((Metadata) parameters.get("metadata")).uploader + " - "
            + ((Metadata) parameters.get("metadata")).title + ".mp3"));
      }
      updateProgress(100);
      status = "Download complete!";
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      decreaseParent();
    }
  }

  void parseLine(String line) {
    if (line.contains("[download]")) {
      try {
        Matcher m = downloadStats.matcher(line);
        if (m.find()) {
          progress = Double.parseDouble(m.group(1));
          size = m.group(2);
          speed = m.group(3);
          eta = m.group(4);
          if (parameters.get("model") != null)
            ((DownloadTableModel) parameters.get("model")).fireTableDataChanged();
          updateProgress(progress.intValue());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        Matcher m = progressNumber.matcher(line);
        if (m.find()) {
          progress = Integer.parseInt(m.group(1)) / (double) Integer.parseInt(m.group(2));
          updateProgress(progress.intValue());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (line.contains("[ffmpeg] Destination: ")) {
      status = "Converting to Audio";
      updateProgress(progress); // needed to update parent of status - might want to facilitate in future...
      FileLogger.logger().log(Level.FINEST, "placing file in audioLocation: " + line.substring(22));
      parameters.put("audioLocation", new File(line.substring(22)));
    }
  }

  @Override
  public void kill() {
    execute.kill();
    thread.interrupt();
  }
}
