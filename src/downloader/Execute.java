package downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Execute extends Thread {
	String arguments = "";
	static String exeLocation = "resources/youtube-dl.exe";
	Process p;
	public BufferedReader input;

	Execute(String arguments) {
		this.arguments = arguments;
	}

	@Override
	public void run() {
		System.out.println("Running Executable");
		try {
			
			synchronized (this) {
				p = Runtime.getRuntime().exec(exeLocation + " " + arguments);
				InputStreamReader re = new InputStreamReader(p.getInputStream());
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				notify();
			}

			//String line;
			//while ((line = input.readLine()) != null)
			//	GUI.editorPane.setText(GUI.editorPane.getText() + "\n" + line);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
