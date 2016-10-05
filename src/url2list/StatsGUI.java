package url2list;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class StatsGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public StatsGUI() {
		setTitle("Stats - Newest Search");
		setBounds(100, 100, 309, 301);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(4, 2, 5, 5));
		{
			JLabel lblTotalTime = new JLabel("Total Time (seconds): ");
			contentPanel.add(lblTotalTime);
		}
		{
			textField = new JTextField();
			textField.setEnabled(false);
			textField.setEditable(false);
			
			//textField.setText(Fetcher.totalTime/1000000000.0 + "");
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JLabel lblAverageTimeseconds = new JLabel("Average Time: ");
			contentPanel.add(lblAverageTimeseconds);
		}
		{
			textField_3 = new JTextField();
			//textField_3.setText(((double)Fetcher.totalTime/Fetcher.totalTrials)/1000000000.0 + "");
			textField_3.setEnabled(false);
			textField_3.setEditable(false);
			textField_3.setColumns(10);
			contentPanel.add(textField_3);
		}
		{
			JLabel lblFetchTimeseconds = new JLabel("HTML Fetch Time: ");
			contentPanel.add(lblFetchTimeseconds);
		}
		{
			textField_1 = new JTextField();
			//textField_1.setText(Fetcher.htmlGrabTime/1000000000.0 + "");
			textField_1.setEnabled(false);
			textField_1.setEditable(false);
			textField_1.setColumns(10);
			contentPanel.add(textField_1);
		}
		{
			JLabel lblRegexSearchprocessTime = new JLabel("Regex Processing Time: ");
			contentPanel.add(lblRegexSearchprocessTime);
		}
		{
			textField_2 = new JTextField();
			//textField_2.setText(Fetcher.regexTime/1000000000.0 + "");
			textField_2.setEnabled(false);
			textField_2.setEditable(false);
			textField_2.setColumns(10);
			contentPanel.add(textField_2);
		}
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}
