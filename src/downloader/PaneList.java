package downloader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PaneList extends JPanel {

  private JPanel mainList;
  private static GridBagConstraints gbc = new GridBagConstraints();

  public PaneList() {

    setLayout(new BorderLayout());

    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.weightx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    mainList = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.weightx = 1;
    gbc.weighty = 1;
    mainList.add(new JPanel(), gbc);

    JScrollPane scrollPane = new JScrollPane(mainList);

    add(scrollPane);

    JButton adder = new JButton("Add/Paste");
    adder.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          String[] urls = ((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)).split("\n");

          for (String url : urls) {
            YtPane pane = addPane(url);

            Video vid = new Video(url, pane);
            Thread thread = new Thread(vid, "test");
            thread.start();

          }

        } catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
          e1.printStackTrace();
        }
      }
    });

    add(adder, BorderLayout.SOUTH);

    KeyboardFocusManager ky = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    ky.addKeyEventDispatcher(new KeyEventDispatcher() {

      @Override
      public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_RELEASED && (e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
          adder.doClick();
        }
        return true;
      }
    });

  }

  public YtPane addPane(String url) {
    YtPane pane = new YtPane(url);
    mainList.add(pane.panel, gbc, 0);
    validate();
    repaint();
    return pane;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(200, 200);
  }
}