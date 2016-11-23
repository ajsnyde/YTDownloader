//http://www.programcreek.com/java-api-examples/index.php?source_dir=JavaTorrent-master/torrent/frame/table/TorrentTableModel.java
package tables;

import java.io.File;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import engine.Metadata;

public class MetaTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;

  private static final int COL_NAME = 0;
  private static final int COL_URL = 1;
  private static final int COL_UPLOADER = 2;
  private static final int COL_DESCRIPTION = 3;

  private static String[] headers = new String[] { "Title", "Url", "Uploader", "Description" };

  public List<Metadata> metas;

  public MetaTableModel(List<Metadata> metas) {
    this.metas = metas;
  }

  @Override
  public int getRowCount() {
    return metas.size();
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
    Metadata meta = metas.get(rowIndex);

    switch (columnIndex) {
    case COL_NAME:
      return meta.title;
    case COL_URL:
      return meta.url;
    case COL_UPLOADER:
      return meta.uploader;
    case COL_DESCRIPTION:
      return meta.description;
    default:
      throw new IllegalArgumentException(String.format("Column %d is outside of the column range", columnIndex));
    }
  }

  public void addMeta(File metaFile) {
    this.fireTableDataChanged();
  }

  public void addMeta(List<File> metaFiles) {
    for (File meta : metaFiles)
      metas.add(new Metadata(meta));
    this.fireTableDataChanged();
  }

  // TODO: Update in accordance to columns
  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return super.getColumnClass(columnIndex);
  }

}