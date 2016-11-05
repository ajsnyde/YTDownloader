package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.border.BevelBorder;

import engine.Task;
import engine.TaskDownloadVideo;
import tables.DownloadTableModel;
import tables.ProgressCellRenderer;

import java.awt.Dimension;

public class GUI {

	private JFrame frmYetAnotherYoutube;

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

		ResizableSplitPane splitPane = new ResizableSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel,
				fullPanel);
		bottomPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane bottomScrollPane = new JScrollPane();
		bottomScrollPane.setMinimumSize(new Dimension(0, 0));
		bottomPanel.add(bottomScrollPane, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));

		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(new TaskDownloadVideo(new HashMap<String, Object>()));
		tasks.add(new TaskDownloadVideo(new HashMap<String, Object>()));
		JTable downloadTable = new JTable(new DownloadTableModel(tasks));
		downloadTable.setDefaultRenderer(Double.class, new ProgressCellRenderer());
		downloadTable.setFillsViewportHeight(true);

		JScrollPane topScrollPane = new JScrollPane(downloadTable);
		topScrollPane.setMinimumSize(new Dimension(0, 0));
		topPanel.add(topScrollPane, BorderLayout.CENTER);
		splitPaneContainer.add(splitPane);

	}

}
