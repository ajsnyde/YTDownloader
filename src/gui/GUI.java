package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.Task;
import engine.TaskManager;
import logger.FileLogger;
import tables.DownloadTableModel;
import tables.ProgressCellRenderer;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.FlowLayout;

public class GUI {

  private JFrame frmYetAnotherYoutube;
  private static DownloadTableModel model;

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          GUI window = new GUI();
          window.frmYetAnotherYoutube.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public GUI() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    initialize();
  }

  private void initialize() {
    frmYetAnotherYoutube = new JFrame();
    frmYetAnotherYoutube.setTitle("Yet Another Youtube Downloader");
    frmYetAnotherYoutube.setBounds(100, 100, 1600, 900);
    frmYetAnotherYoutube.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JMenuBar menuBar = new JMenuBar();
    frmYetAnotherYoutube.setJMenuBar(menuBar);

    JPanel buttonBar = new JPanel();
    frmYetAnotherYoutube.getContentPane().add(buttonBar, BorderLayout.NORTH);

    JPanel fullPanel = new JPanel();
    fullPanel.setBorder(null);

    frmYetAnotherYoutube.getContentPane().add(fullPanel, BorderLayout.CENTER);
    GridBagLayout gbl_fullPanel = new GridBagLayout();
    gbl_fullPanel.columnWidths = new int[] { 5, 0, 5, 0 };
    gbl_fullPanel.rowHeights = new int[] { 0, 5, 5, 0 };
    gbl_fullPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
    gbl_fullPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
    fullPanel.setLayout(gbl_fullPanel);

    JPanel panel = new JPanel();
    FlowLayout flowLayout = (FlowLayout) panel.getLayout();
    flowLayout.setAlignment(FlowLayout.LEFT);
    GridBagConstraints gbc_panel = new GridBagConstraints();
    gbc_panel.insets = new Insets(0, 0, 5, 5);
    gbc_panel.fill = GridBagConstraints.BOTH;
    gbc_panel.gridx = 1;
    gbc_panel.gridy = 0;
    fullPanel.add(panel, gbc_panel);

    JLabel lblMaxThreads = new JLabel("Max Threads:");
    panel.add(lblMaxThreads);

    JSpinner spinner = new JSpinner();
    spinner.setPreferredSize(new Dimension(40, 20));
    spinner.setModel(new SpinnerNumberModel(new Integer(4), new Integer(1), null, new Integer(1)));
    panel.add(spinner);
    spinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        TaskManager.MAX_THREADS.set((int) spinner.getValue());
        FileLogger.logger().log(Level.FINER, "Max Thread Changed to " + (int) spinner.getValue());
      }
    });

    JPanel splitPaneContainer = new JPanel();
    splitPaneContainer.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    GridBagConstraints gbc_splitPaneContainer = new GridBagConstraints();
    gbc_splitPaneContainer.insets = new Insets(0, 0, 5, 5);
    gbc_splitPaneContainer.fill = GridBagConstraints.BOTH;
    gbc_splitPaneContainer.gridx = 1;
    gbc_splitPaneContainer.gridy = 1;
    fullPanel.add(splitPaneContainer, gbc_splitPaneContainer);
    JPanel topPanel = new JPanel();
    topPanel.setMinimumSize(new Dimension(0, 0));
    JPanel bottomPanel = new JPanel();
    bottomPanel.setMinimumSize(new Dimension(0, 0));
    splitPaneContainer.setLayout(new BorderLayout(0, 0));

    ResizableSplitPane splitPane = new ResizableSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel, fullPanel);
    bottomPanel.setLayout(new BorderLayout(0, 0));

    JScrollPane bottomScrollPane = new JScrollPane();
    bottomScrollPane.setMinimumSize(new Dimension(0, 0));
    bottomPanel.add(bottomScrollPane, BorderLayout.NORTH);
    topPanel.setLayout(new BorderLayout(0, 0));

    JPopupMenu menu = new JPopupMenu("Popup");
    menu.add(new JLabel("Selected Item"));
    JPopupMenu menu2 = new JPopupMenu("Popup");
    menu2.add(new JLabel("Not Selected Item"));

    ArrayList<Task> tasks = new ArrayList<Task>();
    model = new DownloadTableModel(tasks);
    JTable downloadTable = new JTable(model);

    downloadTable.setDefaultRenderer(Double.class, new ProgressCellRenderer());
    downloadTable.setFillsViewportHeight(true);

    JScrollPane topScrollPane = new JScrollPane(downloadTable);
    topScrollPane.setMinimumSize(new Dimension(0, 0));
    topPanel.add(topScrollPane, BorderLayout.CENTER);
    splitPaneContainer.add(splitPane);

    downloadTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        int r = downloadTable.rowAtPoint(e.getPoint());
        if (r >= 0 && r < downloadTable.getRowCount()) {
          downloadTable.setRowSelectionInterval(r, r);
        } else {
          downloadTable.clearSelection();
        }

        int rowindex = downloadTable.getSelectedRow();
        if (e.isPopupTrigger() && rowindex < 0) {
          menu2.show(e.getComponent(), e.getX(), e.getY());
        } else if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
          menu.show(e.getComponent(), e.getX(), e.getY());
        }
      }
    });

    KeyboardFocusManager ky = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    ky.addKeyEventDispatcher(new KeyEventDispatcher() {

      @Override
      public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_RELEASED && (e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
          try {

            String[] urls = ((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)).split("\n");

            addTaskGUI(urls);

          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
        return true;
      }
    });
  }

  public static void addTask(Task task) {
    task.parameters.put("model", model);
    model.addTask(task);
    TaskManager.getInstance().addTask(task);
  }

  public void addTaskGUI(String[] urls) {
    new AddTask(urls).setVisible(true);
  }
}
