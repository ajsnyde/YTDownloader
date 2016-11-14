//http://www.programcreek.com/java-api-examples/index.php?source_dir=JavaTorrent-master/torrent/frame/table/TorrentTableModel.java
package tables;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import engine.Task;

public class DownloadTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;

  private static final int COL_NAME = 0;
  private static final int COL_PROGRESS = 1;
  private static final int COL_STATUS = 2;
  private static final int COL_SIZE = 3;
  private static final int COL_ETA = 4;
  private static final int COL_SPEED = 5;

  private static String[] headers = new String[] { "Name", "Progress", "Status", "Size", "ETA", "Speed" };

  private List<Task> tasks;

  public DownloadTableModel(List<Task> tasks) {
    this.tasks = tasks;
  }

  @Override
  public int getRowCount() {
    return tasks.size();
  }

  @Override
  public int getColumnCount() {
    return headers.length;
  }

  @Override
  public String getColumnName(int column) {
    return headers[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Task task = tasks.get(rowIndex);

    switch (columnIndex) {
    case COL_NAME:
      return task.name;
    case COL_PROGRESS:
      return task.progress;
    case COL_STATUS:
      return task.status;
    case COL_SIZE:
      return task.size;
    case COL_ETA:
      return task.eta;
    case COL_SPEED:
      return task.speed;
    default:
      throw new IllegalArgumentException(String.format("Column %d is outside of the column range", columnIndex));
    }
  }

  public void addTask(Task task) {
    tasks.add(task);
    this.fireTableDataChanged();
  }

  // TODO: Update in accordance to columns
  @Override
  public Class<?> getColumnClass(int columnIndex) {
    if (columnIndex == COL_PROGRESS) {
      return Double.class;
    }

    return super.getColumnClass(columnIndex);
  }

}