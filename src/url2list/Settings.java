package url2list;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Settings extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRawJavaRegex;

	public Settings() {
		setTitle("Settings");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 20, 140, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 34, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 0, 5);
			gbc_panel.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel.anchor = GridBagConstraints.NORTH;
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			txtRawJavaRegex = new JTextField();
			txtRawJavaRegex.setDragEnabled(true);
			{
				JLabel lblNewLabel = new JLabel("Regex:");
				panel.add(lblNewLabel);
			}
			{
				txtRawJavaRegex.setText(Fetcher.regex);
				panel.add(txtRawJavaRegex);
				txtRawJavaRegex.setColumns(35);
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
						Fetcher.regex = txtRawJavaRegex.getText();
					}
				});
				{
					JButton btnRestoreDefaults = new JButton("Restore Defaults");
					btnRestoreDefaults.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Fetcher.regex = Fetcher.DEFAULT_REGEX;
							txtRawJavaRegex.setText(Fetcher.DEFAULT_REGEX);
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
