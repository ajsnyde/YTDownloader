package url2list;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;

import javax.swing.event.ChangeEvent;

public class GUI {
  public static List<String> output = Collections.synchronizedList(new ArrayList<String>());
  public final static JTextArea outputTxt = new JTextArea();
  private JFrame frmBatchUrlRetriever;
  OpManager mgr = new OpManager();

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          GUI window = new GUI();
          window.frmBatchUrlRetriever.setVisible(true);
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
    frmBatchUrlRetriever = new JFrame();
    frmBatchUrlRetriever.setTitle("Batch URL Retriever - Youtube");
    frmBatchUrlRetriever.setBounds(100, 100, 591, 448);
    frmBatchUrlRetriever.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel topPanel = new JPanel();
    frmBatchUrlRetriever.getContentPane().add(topPanel, BorderLayout.NORTH);
    topPanel.setLayout(new BorderLayout(0, 0));

    JButton fetchBtn = new JButton("Fetch!");
    fetchBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));

    topPanel.add(fetchBtn);

    JPanel panel = new JPanel();
    frmBatchUrlRetriever.getContentPane().add(panel, BorderLayout.CENTER);
    panel.setLayout(new BorderLayout(0, 0));

    JSplitPane splitPane = new JSplitPane();
    panel.add(splitPane, BorderLayout.CENTER);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(new Dimension(300, 300));
    splitPane.setLeftComponent(scrollPane);

    final JTextArea inputTxt = new JTextArea();
    inputTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
    scrollPane.setViewportView(inputTxt);
    inputTxt.setText(
        "Careless Whisper - Wham! featuring George Michael\nLike a Virgin - Madonna\nWake Me Up Before You Go-Go - Wham!\nI Want To Know What Love Is - Foreigner\nI Feel For You - Chaka Khan\nOut of Touch - Daryl Hall & John Oates\nEverybody Wants to Rule the World - Tears for Fears\nMoney for Nothing - Dire Straits\nCrazy for You - Madonna\nTake on Me - a-ha");
    inputTxt.setMinimumSize(new Dimension(300, 0));

    JScrollPane scrollPane_1 = new JScrollPane();
    splitPane.setRightComponent(scrollPane_1);
    outputTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));

    scrollPane_1.setViewportView(outputTxt);
    outputTxt.setText("output");
    outputTxt.setMinimumSize(new Dimension(100, 0));

    JMenuBar menuBar = new JMenuBar();
    frmBatchUrlRetriever.setJMenuBar(menuBar);

    JMenuItem mntmSettings = new JMenuItem("Settings");
    mntmSettings.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        Settings settings = new Settings();
      }
    });
    menuBar.add(mntmSettings);

    JLabel lblThreads = new JLabel("Threads:");
    menuBar.add(lblThreads);

    final JSpinner spinner = new JSpinner();
    spinner.setValue(1);
    spinner.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent arg0) {
        mgr.MAX_THREADS.set((int) spinner.getValue());
      }
    });
    spinner.setPreferredSize(new Dimension(0, 20));
    menuBar.add(spinner);
    spinner.setValue(OpManager.MAX_THREADS.get());

    JButton btnRefresh = new JButton("Refresh");
    btnRefresh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        refresh();
      }
    });
    menuBar.add(btnRefresh);

    fetchBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        inputTxt.setEnabled(false);
        inputTxt.setEditable(false);
        OpManager.main(inputTxt.getText());
        refresh();
        inputTxt.setEditable(true);
        inputTxt.setEnabled(true);
      }
    });
  }

  public static void refresh() {
    String outputStr = "";
    for (String s : output)
      outputStr += s + "\n";
    outputTxt.setText(outputStr);
  }
}
