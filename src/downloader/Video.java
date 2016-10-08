package downloader;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

// run method grabs video metadata
public class Video implements Runnable {

	public String url = "";
	public String title;
	public String description;
	public YtPane pane;
	ImageIcon icon = new ImageIcon("resources/american-alligator.jpg");

	Video(String url, YtPane pane) {
		this.url = url;
		this.pane = pane;
	}

	@Override
	public void run() {
		BufferedReader input = Engine.exe(url + "--skip-download --write-thumbnail");

		try {
			String line;
			while ((line = input.readLine()) != null) {
				parseLine(line);
				GUI.editorPane.setText(GUI.editorPane.getText() + "\n" + line);
				// pane.lblProgress.setText(line);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void parseLine(String line) {
		if (line.contains("Writing thumbnail to")) {
			
			//Magic offset for the start of the file name
			String file = line.substring(45);
	
			ImageIcon icon = new ImageIcon(file);
			Image icon2 = icon.getImage().getScaledInstance(168, 94, Image.SCALE_DEFAULT);
			pane.lblThumbnail.setIcon(new ImageIcon(icon2));

			pane.lblThumbnail.setText(file);
			
		}
	}
}
