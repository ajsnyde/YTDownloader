package downloader;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import java.awt.ScrollPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.Component;

public class GUI {

	private JFrame frmDownloader;

	public static JTextPane textPane;
	private static ScrollPane scrollPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI window = new GUI();
				window.frmDownloader.setVisible(true);
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
		frmDownloader = new JFrame();
		frmDownloader.setTitle("Downloader");
		frmDownloader.setBounds(100, 100, 654, 415);
		frmDownloader.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 0, 30, 0 };
		gridBagLayout.rowHeights = new int[] { 30, 62, 98, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		frmDownloader.getContentPane().setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		frmDownloader.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));

		panel.add(new PaneList());

		textPane = new JTextPane();
		scrollPane = new ScrollPane();
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;

		scrollPane.add(textPane);
		frmDownloader.getContentPane().add(scrollPane, gbc_scrollPane);
	}
	
	public static void addText(String line){
		textPane.setText(textPane.getText() + "\n" + line);
		scrollPane.setScrollPosition(0, Integer.MAX_VALUE);
	}
}
