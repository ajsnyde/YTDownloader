package splitter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import youtube_dl.Engine;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class Settings extends JDialog {

  private final JPanel contentPanel = new JPanel();
  private JTextField regexTextBar;
  private JTextField textField;
  private JCheckBox keepVid;
  private JTextField txtMemory;
  private JSpinner spinnerTitle;
  private JSpinner spinnerTimestamp;

  public Settings() {
    setTitle("Settings");
    setBounds(100, 100, 521, 403);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    GridBagLayout gbl_contentPanel = new GridBagLayout();
    gbl_contentPanel.columnWidths = new int[] { 10, 140, 10, 0 };
    gbl_contentPanel.rowHeights = new int[] { 10, 73, 0 };
    gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
    gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
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
      gbl_panel.columnWidths = new int[] { 93, 286, 0 };
      gbl_panel.rowHeights = new int[] { 0, 20, 0, 70, 0, 0, 20, 0, 0 };
      gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
      gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
      panel.setLayout(gbl_panel);
      {
        JComboBox<RegexSetting> comboBox = new JComboBox<RegexSetting>();
        comboBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            regexTextBar.setText(comboBox.getSelectedItem().toString());
            spinnerTitle.setValue(((RegexSetting) comboBox.getSelectedItem()).titleGroup);
            spinnerTimestamp.setValue(((RegexSetting) comboBox.getSelectedItem()).timestampGroup);
            txtMemory.setText(((RegexSetting) comboBox.getSelectedItem()).exampleInput);
          }
        });
        comboBox.setModel(
            new DefaultComboBoxModel<RegexSetting>(new RegexSetting[] { new RegexSetting("title timestamp", 1, 2, "Title 0:00"), new RegexSetting("num. title timestamp", 1, 2, "1) Title 0:00"),
                new RegexSetting("num. title \\(timestamp\\)", 1, 2, "1) Title (0:00)"), new RegexSetting("num. timestamp title", 2, 1, "1) 0:00 Title"),
                new RegexSetting("num. \"title\" \\(timestamp\\)", 1, 2, "1) \"Title\" (00:00)"), new RegexSetting("timestamp title", 2, 1, "00:00 Title"),
                new RegexSetting("timestamp \"title\"", 2, 1, "00:00 \"Title\""), new RegexSetting("\\(timestamp\\) title", 2, 1, "(00:00) Title") }));

        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 0;
        panel.add(comboBox, gbc_comboBox);
      }
      {
        JLabel lblNewLabel = new JLabel("Regex:");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.gridheight = 3;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        panel.add(lblNewLabel, gbc_lblNewLabel);
      }
      regexTextBar = new JTextField();
      regexTextBar.setDragEnabled(true);
      {
        regexTextBar.setText(Splitter.regex);
        GridBagConstraints gbc_txtRawJavaRegex = new GridBagConstraints();
        gbc_txtRawJavaRegex.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtRawJavaRegex.anchor = GridBagConstraints.NORTH;
        gbc_txtRawJavaRegex.insets = new Insets(0, 0, 5, 0);
        gbc_txtRawJavaRegex.gridx = 1;
        gbc_txtRawJavaRegex.gridy = 1;
        panel.add(regexTextBar, gbc_txtRawJavaRegex);
        regexTextBar.setColumns(35);
      }
      {
        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(0, 0, 5, 0);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 2;
        panel.add(panel_1, gbc_panel_1);
        {
          JLabel lblTitleGroup = new JLabel("title group #:");
          panel_1.add(lblTitleGroup);
        }
        {
          spinnerTitle = new JSpinner();
          spinnerTitle.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
          panel_1.add(spinnerTitle);
        }
        {
          JLabel lblTimestampGroup = new JLabel("timestamp group #:");
          panel_1.add(lblTimestampGroup);
        }
        {
          spinnerTimestamp = new JSpinner();
          spinnerTimestamp.setModel(new SpinnerNumberModel(new Integer(2), new Integer(0), null, new Integer(1)));
          panel_1.add(spinnerTimestamp);
        }
      }
      {
        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 3;
        panel.add(scrollPane, gbc_scrollPane);
        {
          JTextPane txtpnHelp = new JTextPane();
          txtpnHelp.setText(
              "The regex above is assisted - that is, certain keywords will yield their regex counterparts, allowing those not familiar with regex to still have a good chance of parsing descriptions. The following tags will be processed accordingly:\r\ntimestamp - a timestamp in standard Youtube format ([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)\r\ntitle - a string which is trimmed of outer whitespace\r\nnum - a group of digits (?:[\\d]+)\r\n[ ] (space) - any space (\\s+)\r\n\r\nCertain characters (ex. parentheses, brackets, periods) will need to be 'escaped', or preceded by a backslash. For example, '\\(' equates to a literal '('.\r\n\r\nAdditionally, group numbers must also be addressed - the only information that needs to be explicitly grouped is that of the title and the timestamp. When entering custom regex, one must tell the program which groups contain which info. For most non-advanced regex, this is simply the order of the timestamp and title tag - i.e. \"\\[timestamp\\] num\\. title\" would mean that timestamp group number would be 1 and title would be 2 (num is not captured in a group, as it is useless information).");
          scrollPane.setViewportView(txtpnHelp);
          txtpnHelp.setCaretPosition(0);
        }
      }
      {
        JButton btnTestRegex = new JButton("Test Regex:");
        btnTestRegex.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {

            txtMemory.getText();
            Pattern pattern = Pattern.compile(RegexHelper.regexProcess(regexTextBar.getText()));
            Matcher matcher = pattern.matcher(txtMemory.getText());
            String output = "";
            while (matcher.find()) {
              output += "title: " + matcher.group((int) spinnerTitle.getValue()) + "\n";
              output += "timestamp: " + matcher.group((int) spinnerTimestamp.getValue()) + "\n";
            }

            JOptionPane.showMessageDialog(new Frame(), output);

          }
        });
        GridBagConstraints gbc_btnTestRegex = new GridBagConstraints();
        gbc_btnTestRegex.anchor = GridBagConstraints.WEST;
        gbc_btnTestRegex.insets = new Insets(0, 0, 5, 5);
        gbc_btnTestRegex.gridx = 0;
        gbc_btnTestRegex.gridy = 4;
        panel.add(btnTestRegex, gbc_btnTestRegex);
      }
      {
        txtMemory = new JTextField();
        txtMemory.setText("Memory 17:48");
        GridBagConstraints gbc_txtMemory = new GridBagConstraints();
        gbc_txtMemory.insets = new Insets(0, 0, 5, 0);
        gbc_txtMemory.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtMemory.gridx = 1;
        gbc_txtMemory.gridy = 4;
        panel.add(txtMemory, gbc_txtMemory);
        txtMemory.setColumns(10);
      }
      {
        JButton btnUpdateYoutubedl = new JButton("Update youtube-dl");
        btnUpdateYoutubedl.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            Engine.exe("-U");
          }
        });
        GridBagConstraints gbc_btnUpdateYoutubedl = new GridBagConstraints();
        gbc_btnUpdateYoutubedl.insets = new Insets(0, 0, 5, 5);
        gbc_btnUpdateYoutubedl.gridx = 0;
        gbc_btnUpdateYoutubedl.gridy = 5;
        panel.add(btnUpdateYoutubedl, gbc_btnUpdateYoutubedl);
      }
      {
        JTextPane txtpnWillUpdateThe = new JTextPane();
        txtpnWillUpdateThe.setText(
            "Will update the youtube-dl.exe file - will NOT backup the file before doing so. Updating the underlying downloader may resolve some bugs. For obvious reasons, do not update while the downloader is in use.");
        GridBagConstraints gbc_txtpnWillUpdateThe = new GridBagConstraints();
        gbc_txtpnWillUpdateThe.insets = new Insets(0, 0, 5, 0);
        gbc_txtpnWillUpdateThe.fill = GridBagConstraints.BOTH;
        gbc_txtpnWillUpdateThe.gridx = 1;
        gbc_txtpnWillUpdateThe.gridy = 5;
        panel.add(txtpnWillUpdateThe, gbc_txtpnWillUpdateThe);
      }
      {
        JLabel lblExeLocation = new JLabel("sox.exe Location:");
        GridBagConstraints gbc_lblExeLocation = new GridBagConstraints();
        gbc_lblExeLocation.anchor = GridBagConstraints.WEST;
        gbc_lblExeLocation.insets = new Insets(0, 0, 5, 5);
        gbc_lblExeLocation.gridx = 0;
        gbc_lblExeLocation.gridy = 6;
        panel.add(lblExeLocation, gbc_lblExeLocation);
      }
      {
        textField = new JTextField();
        textField.setText(Splitter.exeLocation);
        textField.setDragEnabled(true);
        textField.setColumns(35);
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.anchor = GridBagConstraints.NORTH;
        gbc_textField.gridx = 1;
        gbc_textField.gridy = 6;
        panel.add(textField, gbc_textField);
      }
      {
        JLabel lblKeepVideo = new JLabel("Keep Video:");
        GridBagConstraints gbc_lblKeepVideo = new GridBagConstraints();
        gbc_lblKeepVideo.anchor = GridBagConstraints.WEST;
        gbc_lblKeepVideo.insets = new Insets(0, 0, 0, 5);
        gbc_lblKeepVideo.gridx = 0;
        gbc_lblKeepVideo.gridy = 7;
        panel.add(lblKeepVideo, gbc_lblKeepVideo);
      }
      {
        keepVid = new JCheckBox("Yes");
        keepVid.setSelected(Splitter.keepVideo);
        GridBagConstraints gbc_chckbxYes = new GridBagConstraints();
        gbc_chckbxYes.anchor = GridBagConstraints.WEST;
        gbc_chckbxYes.gridx = 1;
        gbc_chckbxYes.gridy = 7;
        panel.add(keepVid, gbc_chckbxYes);
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
            Splitter.regex = RegexHelper.regexProcess(regexTextBar.getText());
            Splitter.titleGroup = (int) spinnerTitle.getValue();
            Splitter.timestampGroup = (int) spinnerTimestamp.getValue();
            Splitter.exeLocation = textField.getText();
            Splitter.keepVideo = keepVid.isSelected();
          }
        });
        {
          JButton btnRestoreDefaults = new JButton("Restore Defaults");
          btnRestoreDefaults.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Splitter.regex = Splitter.DEFAULT_REGEX;
              Splitter.titleGroup = Splitter.DEFAULT_TITLE_GROUP;
              spinnerTitle.setValue(Splitter.DEFAULT_TITLE_GROUP);
              Splitter.timestampGroup = Splitter.DEFAULT_TIMESTAMP_GROUP;
              spinnerTimestamp.setValue(Splitter.DEFAULT_TIMESTAMP_GROUP);
              regexTextBar.setText(Splitter.DEFAULT_REGEX);
              Splitter.exeLocation = Splitter.EXE_LOCATION_DEFAULT;
              textField.setText(Splitter.EXE_LOCATION_DEFAULT);
              keepVid.setSelected(false);
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
