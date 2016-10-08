package downloader;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.ImageIcon;

// run method grabs video metadata
public class Video implements Runnable {

	public String url = "";
	public String title;
	public String description;
	ImageIcon icon = new ImageIcon("resources/american-alligator.jpg");

	Video(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		BufferedReader input = Engine.exe(url + "--skip-download --write-thumbnail");
		try {
			input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
