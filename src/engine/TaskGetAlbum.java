package engine;

import java.util.HashMap;

import splitter.AutoRegex;
import tables.DownloadTableModel;
import splitter.Album;

public class TaskGetAlbum extends Task {

  public TaskGetAlbum(HashMap<String, Object> parameters) {
    this.parameters = parameters;
    if (parameters.get("model") != null)
      ((DownloadTableModel) parameters.get("model")).addTask(this);
  }

  // requires metadata
  // creates album
  @Override
  public void run() {
    increaseParent();
    try {
      updateProgress(0);
      parameters.put("album", new AutoRegex().getAlbum(((Metadata) parameters.get("metadata")).description, ((Metadata) parameters.get("metadata")).length));
      ((Album) (parameters.get("album"))).albumName = ((Metadata) (parameters.get("metadata"))).title;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      updateProgress(100);
      decreaseParent();
    }
  }

  // no easy way to cancel
  @Override
  public void kill() {
  }
}
