package downloader;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

import youtube_dl.Engine;

// run method grabs video metadata
public class Video implements Runnable {

	public String url = "";
	public String title;
	public String description;
	public YtPane pane;
	ImageIcon icon = new ImageIcon("resources/american-alligator.jpg");

	final Pattern percentage = Pattern.compile("(\\d+(\\.)?\\d+)%");

	Video(String url, YtPane pane) {
		this.url = url;
		this.pane = pane;
	}

	@Override
	public void run() {
		BufferedReader input = Engine.exe(url + " -o %(title)s.%(ext)s --write-thumbnail -x --audio-format mp3");

		try {
			String line;
			while ((line = input.readLine()) != null) {
				parseLine(line);
				GUI.addText(line);
				// pane.lblProgress.setText(line);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void parseLine(String line) {
		if (line.contains("Writing thumbnail to")) {

			// Magic offset for the start of the file name
			String file = line.substring(45);

			ImageIcon icon = new ImageIcon(file);
			Image icon2 = icon.getImage().getScaledInstance(168, 94, Image.SCALE_DEFAULT);
			pane.lblThumbnail.setIcon(new ImageIcon(icon2));
			// pane.lblThumbnail.setText(file);
		} else if (line.contains("[download]")) {
			try {
				Matcher m = percentage.matcher(line);
				if (m.find()) {
					pane.progressBar.setValue((int) Double.parseDouble(m.group(1)));
					pane.lblProgress.setText(m.group(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
