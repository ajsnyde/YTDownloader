package downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Execute implements Runnable{
	String arguments = "";
	static String exeLocation = "resources/youtube-dl.exe";
	Process p;
	public BufferedReader input;
	
	Execute(String arguments){
		this.arguments = arguments;
	}

	@Override
	public void run() {
		System.out.println("Running Executable?");
		try {
			p = Runtime.getRuntime().exec(exeLocation + " " + arguments);
			InputStreamReader re = new InputStreamReader(p.getInputStream());
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			//while ((line = input.readLine()) != null)
			//GUI.editorPane.setText(GUI.editorPane.getText() + "\n" + line);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
