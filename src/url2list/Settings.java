package url2list;
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

public class Settings extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRawJavaRegex;

	public Settings() {
		setTitle("Settings");
		setBounds(100, 100, 450, 300); 
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			contentPanel.add(panel);
			txtRawJavaRegex = new JTextField();
			{
				JLabel lblNewLabel = new JLabel("Regex:");
				panel.add(lblNewLabel);
			}
			{
				final JCheckBox chckbxDefault = new JCheckBox("Default");
				panel.add(chckbxDefault);
				chckbxDefault.setSelected(true);
				chckbxDefault.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						txtRawJavaRegex.setEnabled(!chckbxDefault.isSelected());
						txtRawJavaRegex.setEditable(!chckbxDefault.isSelected());
					}
				});
			}
			{
				JLabel lblCustomString = new JLabel("Custom String:");
				panel.add(lblCustomString);
			}
			{
				txtRawJavaRegex.setText("raw java regex");
				panel.add(txtRawJavaRegex);
				txtRawJavaRegex.setEnabled(false);
				txtRawJavaRegex.setEditable(false);
				txtRawJavaRegex.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton btnApply = new JButton("Apply");
				btnApply.setActionCommand("OK");
				buttonPane.add(btnApply);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
