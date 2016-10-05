package downloader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

public class PaneList extends JPanel {

    private JPanel mainList;
    private static GridBagConstraints gbc = new GridBagConstraints();
    public PaneList() {
        setLayout(new BorderLayout());

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);

        add(new JScrollPane(mainList));

        JButton add = new JButton("New Video");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	addPane();
            }
        });

        add(add, BorderLayout.SOUTH);
    }

    public void addPane(){
        mainList.add(new YtPane().panel, gbc, 0);
        validate();
        repaint();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
}