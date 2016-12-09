package tasks;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import logger.FileLogger;
import tables.DownloadTableModel;
import youtubeObjects.Album;
import youtubeObjects.Metadata;
import youtubeObjects.Song;

public class TaskSplitByAlbum extends Task {

  public TaskSplitByAlbum(HashMap<String, Object> parameters) {
    this.parameters = parameters;
    if (parameters.get("model") != null)
      ((DownloadTableModel) parameters.get("model")).addTask(this);
  }

  // requires album with an mp3 that is stored in the directory schema below as well as metadata
  // Splits into song mp3s
  @Override
  public void run() {
    increaseParent();
    try {
      status = "Splitting by Album";
      updateProgress(0);

      Album album = (Album) parameters.get("album");
      // FileLogger.logger().log(Level.FINEST, ((File) parameters.get("audioLocation")).getAbsolutePath());
      // TODO: updateProgress
      FileLogger.logger().log(Level.FINEST, "Album being processed");
      FileLogger.logger().log(Level.FINEST, album.albumName);
      FileLogger.logger().log(Level.FINEST, album.length + "");
      Metadata meta = (Metadata) parameters.get("metadata");

      int i = 1;

      // NOTE: IT SEEMS THAT SOX CANNOT CREATE DIRECTORIES. ADDING meta.title BETWEEN meta.uploader AND song.title RESULTS IN FAIL!

      // This should alleviate that issue assuming proper permissions. Note that I moved it outside of the loop. Perhaps this will fix the !1st song being written outside of the dir.
      File dir = new File("Downloads/" + meta.uploader + "/" + meta.title);
      boolean success = dir.mkdir();
      status = "Splitting Audio by Song";

      for (Song song : album.songs) {
        FileLogger.logger().log(Level.FINEST, "Song being processed");
        FileLogger.logger().log(Level.FINEST, song.title + " " + song.startTime + " " + song.endTime);

        Process process = new ProcessBuilder("resources/sox-14-4-2/sox.exe", "Downloads/" + meta.uploader + "/" + meta.uploader + " - " + meta.title + ".mp3",
            "Downloads/" + meta.uploader + (success ? ("/" + meta.title) : "") + "/" + song.title + ".mp3", "trim", song.startTime + "", (song.endTime - song.startTime) + "").start();
        process.waitFor();

        process = new ProcessBuilder("resources/id3tool.exe", "-c", i + "", "Downloads/" + meta.uploader + (success ? ("/" + meta.title) : "") + "/" + song.title + ".mp3").start();
        process.waitFor();
        process = new ProcessBuilder("resources/id3tool.exe", "-a", meta.title, "Downloads/" + meta.uploader + (success ? ("/" + meta.title) : "") + "/" + song.title + ".mp3").start();
        process.waitFor();
        process = new ProcessBuilder("resources/id3tool.exe", "-t", song.title + "", "Downloads/" + meta.uploader + (success ? ("/" + meta.title) : "") + "/" + song.title + ".mp3").start();
        process.waitFor();
        updateProgress(((double) i / album.songs.size()) * 100.0);
        i++;
      }
      status = "Splitting by Audio - Complete";
      updateProgress(100);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      decreaseParent();
    }
  }

  // No easy way to simply cancel the operation of this Task
  public void kill() {
  }
}