package downloader;

import java.io.BufferedReader;
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
			while ((line = input.readLine()) != null)
				pane.lblProgress.setText(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
