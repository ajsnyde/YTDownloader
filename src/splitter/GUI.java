package splitter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GUI {

	private JFrame frame;
	public JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 544, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 45, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblUrl = new JLabel("URL:");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.anchor = GridBagConstraints.EAST;
		gbc_lblUrl.gridx = 1;
		gbc_lblUrl.gridy = 1;
		frame.getContentPane().add(lblUrl, gbc_lblUrl);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnShowMeThe = new JButton("Download + Convert to MP3 + Split by Description");
		btnShowMeThe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Splitter splitter = new Splitter();
				splitter.splitByDescription(textField.getText());
				// textPane.setText(new
				// Engine().getMetaElement(textField.getText(), "description"));
			}
		});
		GridBagConstraints gbc_btnShowMeThe = new GridBagConstraints();
		gbc_btnShowMeThe.insets = new Insets(0, 0, 5, 5);
		gbc_btnShowMeThe.gridx = 2;
		gbc_btnShowMeThe.gridy = 2;
		frame.getContentPane().add(btnShowMeThe, gbc_btnShowMeThe);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenuItem mntmOptions = new JMenuItem("Settings..");
		mntmOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Settings();
			}
		});
		menuBar.add(mntmOptions);
	}

}
