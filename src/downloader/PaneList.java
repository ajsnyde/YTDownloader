package downloader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

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

		add(new JScrollPane(mainList));

		JButton adder = new JButton("Add/Paste");
		adder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] urls = ((String) Toolkit.getDefaultToolkit().getSystemClipboard()
							.getData(DataFlavor.stringFlavor)).split("\n");
					
					for(String url: urls){
						addPane(url);
						
						Video vid = new Video(url);
						Thread thread = new Thread(vid, "test");
						thread.start();

					}
					
				} catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		add(adder, BorderLayout.SOUTH);
	}

	public void addPane(String url) {
		mainList.add(new YtPane(url).panel, gbc, 0);
		validate();
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}
}