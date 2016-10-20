package launcher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Launcher {

	private JFrame frmYoutubemusic;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher window = new Launcher();
					window.frmYoutubemusic.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Launcher() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {
		frmYoutubemusic = new JFrame();
		frmYoutubemusic.setTitle("Youtube2Music");
		frmYoutubemusic.setBounds(100, 100, 415, 266);
		frmYoutubemusic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 32, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 31, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frmYoutubemusic.getContentPane().setLayout(gridBagLayout);

		JButton btnListurls = new JButton("List2URLs");
		btnListurls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				url2list.GUI.main(null);
			}
		});

		JLabel lblNewLabel = new JLabel("Utilities:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		frmYoutubemusic.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_btnListurls = new GridBagConstraints();
		gbc_btnListurls.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnListurls.insets = new Insets(0, 0, 5, 5);
		gbc_btnListurls.gridx = 1;
		gbc_btnListurls.gridy = 1;
		frmYoutubemusic.getContentPane().add(btnListurls, gbc_btnListurls);

		JButton btnDownloader = new JButton("Downloader");
		btnDownloader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				downloader.GUI.main(null);
			}
		});
		GridBagConstraints gbc_btnDownloader = new GridBagConstraints();
		gbc_btnDownloader.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDownloader.insets = new Insets(0, 0, 5, 5);
		gbc_btnDownloader.gridx = 2;
		gbc_btnDownloader.gridy = 1;
		frmYoutubemusic.getContentPane().add(btnDownloader, gbc_btnDownloader);

		JButton btnSplitByTimestamps = new JButton("Split by Timestamps");
		btnSplitByTimestamps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				splitter.GUI.main(null);
			}
		});
		GridBagConstraints gbc_btnSplitByTimestamps = new GridBagConstraints();
		gbc_btnSplitByTimestamps.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSplitByTimestamps.insets = new Insets(0, 0, 5, 5);
		gbc_btnSplitByTimestamps.gridx = 3;
		gbc_btnSplitByTimestamps.gridy = 1;
		frmYoutubemusic.getContentPane().add(btnSplitByTimestamps, gbc_btnSplitByTimestamps);

		JLabel lblNewLabel_1 = new JLabel("What do you want to do?");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 3;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		frmYoutubemusic.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		JButton btnNewButton = new JButton("Convert a youtube video to a music album!");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 4;
		frmYoutubemusic.getContentPane().add(btnNewButton, gbc_btnNewButton);
	}
}
