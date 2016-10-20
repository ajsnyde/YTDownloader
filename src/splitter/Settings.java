package splitter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Settings extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRawJavaRegex;
	private JTextField textField;
	private JCheckBox chckbxYes;

	public Settings() {
		setTitle("Settings");
		setBounds(100, 100, 450, 300); 
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{20, 140, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 73, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 0, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.anchor = GridBagConstraints.NORTH;
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{35, 286, 0};
			gbl_panel.rowHeights = new int[]{20, 20, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblNewLabel = new JLabel("Regex:");
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel.gridx = 0;
				gbc_lblNewLabel.gridy = 0;
				panel.add(lblNewLabel, gbc_lblNewLabel);
			}
			txtRawJavaRegex = new JTextField();
			txtRawJavaRegex.setDragEnabled(true);
			{
				txtRawJavaRegex.setText(Splitter.regex);
				GridBagConstraints gbc_txtRawJavaRegex = new GridBagConstraints();
				gbc_txtRawJavaRegex.anchor = GridBagConstraints.NORTHWEST;
				gbc_txtRawJavaRegex.insets = new Insets(0, 0, 5, 0);
				gbc_txtRawJavaRegex.gridx = 1;
				gbc_txtRawJavaRegex.gridy = 0;
				panel.add(txtRawJavaRegex, gbc_txtRawJavaRegex);
				txtRawJavaRegex.setColumns(35);
			}
			{
				JLabel lblExeLocation = new JLabel("sox.exe Location:");
				GridBagConstraints gbc_lblExeLocation = new GridBagConstraints();
				gbc_lblExeLocation.anchor = GridBagConstraints.WEST;
				gbc_lblExeLocation.insets = new Insets(0, 0, 5, 5);
				gbc_lblExeLocation.gridx = 0;
				gbc_lblExeLocation.gridy = 1;
				panel.add(lblExeLocation, gbc_lblExeLocation);
			}
			{
				textField = new JTextField();
				textField.setText(Splitter.exeLocation);
				textField.setDragEnabled(true);
				textField.setColumns(35);
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.insets = new Insets(0, 0, 5, 0);
				gbc_textField.anchor = GridBagConstraints.NORTHWEST;
				gbc_textField.gridx = 1;
				gbc_textField.gridy = 1;
				panel.add(textField, gbc_textField);
			}
			{
				JLabel lblKeepVideo = new JLabel("Keep Video:");
				GridBagConstraints gbc_lblKeepVideo = new GridBagConstraints();
				gbc_lblKeepVideo.insets = new Insets(0, 0, 0, 5);
				gbc_lblKeepVideo.gridx = 0;
				gbc_lblKeepVideo.gridy = 2;
				panel.add(lblKeepVideo, gbc_lblKeepVideo);
			}
			{
				chckbxYes = new JCheckBox("Yes");
				chckbxYes.setSelected(Splitter.keepVideo);
				GridBagConstraints gbc_chckbxYes = new GridBagConstraints();
				gbc_chckbxYes.gridx = 1;
				gbc_chckbxYes.gridy = 2;
				panel.add(chckbxYes, gbc_chckbxYes);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton btnApply = new JButton("Apply");
				btnApply.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Splitter.regex = txtRawJavaRegex.getText();
						Splitter.exeLocation = textField.getText();
						Splitter.keepVideo = chckbxYes.isSelected();
					}
				});
				{
					JButton btnRestoreDefaults = new JButton("Restore Defaults");
					btnRestoreDefaults.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Splitter.regex = Splitter.DEFAULT_REGEX;
							txtRawJavaRegex.setText(Splitter.DEFAULT_REGEX);
							Splitter.exeLocation = Splitter.EXE_LOCATION_DEFAULT;
							textField.setText(Splitter.EXE_LOCATION_DEFAULT);
							chckbxYes.setSelected(false);
						}
					});
					btnRestoreDefaults.setActionCommand("OK");
					buttonPane.add(btnRestoreDefaults);
				}
				btnApply.setActionCommand("OK");
				buttonPane.add(btnApply);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
