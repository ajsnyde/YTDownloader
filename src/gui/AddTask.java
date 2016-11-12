package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

import engine.Metadata;
import engine.Task;
import engine.TaskDownloadMetaPlaylist;
import engine.TaskDownloadVideo;
import engine.TaskManager;
import engine.TaskTask;
import tables.MetaTableModel;
import tables.ProgressCellRenderer;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;

public class AddTask extends JFrame {

	private JPanel contentPane;
	private ArrayList<String> pastFiles = new ArrayList<String>();
	JProgressBar progressBar = new JProgressBar();
	private MetaTableModel model;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String urls[] = { "https://www.youtube.com/watch?v=Wr70mWusOgA" };

					AddTask frame = new AddTask(urls);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AddTask(String[] urls) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle(urls[0]);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 781, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 30, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 38, 40, 10, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Download Location",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JComboBox comboBox = new JComboBox(pastFiles.toArray());
		panel_1.add(comboBox);

		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(fc);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					comboBox.addItem(file.getAbsolutePath());
					comboBox.setSelectedItem(file.getAbsolutePath());
				} else {
				}
			}
		});
		panel_1.add(btnBrowse, BorderLayout.EAST);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Metadata", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 2;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 16, 19, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JButton btnNewButton = new JButton("\u25BC");

		btnNewButton.setMargin(new Insets(2, 2, 2, 2));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel_2.add(btnNewButton, gbc_btnNewButton);

		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 0;
		panel_2.add(progressBar, gbc_progressBar);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(
				new TitledBorder(null, "Tasks and Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 3;
		panel.add(panel_3, gbc_panel_3);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Download", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.add(panel_4);

		JCheckBox chckbxDownloadVideo = new JCheckBox("Download Video?");
		chckbxDownloadVideo.setSelected(true);
		panel_4.add(chckbxDownloadVideo);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Conversion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.add(panel_5);

		JCheckBox chckbxConvert = new JCheckBox("Convert?");
		chckbxConvert.setSelected(true);
		panel_5.add(chckbxConvert);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "MP3" }));
		panel_5.add(comboBox_1);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "Splitter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.add(panel_6);

		JCheckBox chckbxSplitAudioBy = new JCheckBox("Split Audio by Timestamps?");
		chckbxSplitAudioBy.setSelected(true);
		panel_6.add(chckbxSplitAudioBy);

		JButton btnPreviewSplitFiles = new JButton("Preview Split Files..");
		panel_6.add(btnPreviewSplitFiles);

		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.anchor = GridBagConstraints.NORTHEAST;
		gbc_panel_7.insets = new Insets(0, 0, 0, 5);
		gbc_panel_7.gridx = 1;
		gbc_panel_7.gridy = 4;
		panel.add(panel_7, gbc_panel_7);

		JButton btnAdd = new JButton("Add");
		btnAdd.setEnabled(false);
		panel_7.add(btnAdd);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_7.add(btnCancel);

		ArrayList<Metadata> metas = new ArrayList<Metadata>();
		model = new MetaTableModel(metas);
		JTable downloadTable = new JTable(model);

		downloadTable.setDefaultRenderer(Double.class, new ProgressCellRenderer());
		downloadTable.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(downloadTable);
		scrollPane.setVisible(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_2.add(scrollPane, gbc_scrollPane);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnNewButton.getText() == "\u25B2") {
					scrollPane.setVisible(false);
					btnNewButton.setText("\u25BC");
					gbl_panel.rowHeights[2] = 38;
					setSize(800, 200);
				} else {
					scrollPane.setVisible(true);
					btnNewButton.setText("\u25B2");
					setSize(800, 600);
					gbl_panel.rowHeights[2] = 338;
				}
			}
		});

		grabMeta(urls);
	}

	void grabMeta(String[] urls) {

		if (urls.length == 1) {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("downloadExeLocation", "resources/youtube-dl.exe");
			parameters.put("downloadExeArguments",
					"--write-info-json --skip-download -o \"Downloads/metadata/%(uploader)s/%(uploader)s - %(title)s.%(ext)s\" "
							+ urls[0]);
			parameters.put("progressBar", progressBar);
			TaskManager.getInstance().addTask(new TaskDownloadMetaPlaylist(parameters));

		} else {

			Vector<Task> tasks = new Vector<Task>();

			for (String url : urls) {
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("downloadExeLocation", "resources/youtube-dl.exe");
				parameters.put("downloadExeArguments",
						"--write-info-json --skip-download -o \"Downloads/metadata/%(uploader)s/%(uploader)s - %(title)s.%(ext)s\" "
								+ url);
				tasks.add(new TaskDownloadVideo(parameters));
			}
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tasks", tasks);
			parameters.put("progressBar", progressBar);
			TaskManager.getInstance().addTask(new TaskTask(parameters));
		}

	}
}
