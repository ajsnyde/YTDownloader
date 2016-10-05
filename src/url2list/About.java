package url2list;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Window.Type;

public class About extends JDialog {

	private static boolean enabled = false;
	private final JPanel contentPanel = new JPanel();

	public About() {
		setResizable(false); 
		setType(Type.POPUP);
		setTitle("About");
		if(enabled)
			return;
		else
			enabled = true;
		setBounds(100, 100, 234, 214);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblCreatedByAddison = new JLabel("\r\nCreated by Addison Snyder");
			lblCreatedByAddison.setVerticalTextPosition(SwingConstants.TOP);
			lblCreatedByAddison.setHorizontalAlignment(SwingConstants.CENTER);
			lblCreatedByAddison.setVerticalTextPosition(JLabel.BOTTOM);
			lblCreatedByAddison.setHorizontalTextPosition(JLabel.CENTER);
			ImageIcon icon = new ImageIcon(getClass().getResource("/american-alligator.jpg"));
			Image icon2 = icon.getImage().getScaledInstance(183, 132, Image.SCALE_DEFAULT);
			lblCreatedByAddison.setIcon(new ImageIcon(icon2));			
			contentPanel.add(lblCreatedByAddison);
		}
	    addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	enabled = false;
                e.getWindow().dispose();
            }
        });
		//this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
