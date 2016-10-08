package downloader;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class YtPane{
	
	JLabel lblProgress = new JLabel("progress");
	JPanel panel = new JPanel();
	
	public YtPane(String url){
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{165, 119, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{14, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        JLabel lblThumbnail = new JLabel("Thumbnail");
        GridBagConstraints gbc_lblThumbnail = new GridBagConstraints();
        gbc_lblThumbnail.insets = new Insets(0, 0, 0, 5);
        gbc_lblThumbnail.gridx = 0;
        gbc_lblThumbnail.gridy = 0;
        try{
        ImageIcon icon = new ImageIcon("resources/american-alligator.jpg");
		Image icon2 = icon.getImage().getScaledInstance(168, 94, Image.SCALE_DEFAULT);
		lblThumbnail.setIcon(new ImageIcon(icon2));
        } catch(Exception e){
        	
        }
        panel.add(lblThumbnail, gbc_lblThumbnail);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        JLabel label = new JLabel("Song Title (+ more soon)");
        panel.add(label, gbc);
        
        GridBagConstraints gbc_lblProgress = new GridBagConstraints();
        gbc_lblProgress.insets = new Insets(0, 0, 0, 5);
        gbc_lblProgress.gridx = 2;
        gbc_lblProgress.gridy = 0;
        panel.add(lblProgress, gbc_lblProgress);
        
        JLabel lblUrl = new JLabel(url);
        GridBagConstraints gbc_lblUrl = new GridBagConstraints();
        gbc_lblUrl.gridx = 3;
        gbc_lblUrl.gridy = 0;
        panel.add(lblUrl, gbc_lblUrl);
	}
}