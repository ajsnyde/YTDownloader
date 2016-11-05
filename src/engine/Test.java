package engine;

import java.util.HashMap;

public class Test {

	public static void main(String[] args) {

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("downloadExeLocation", "resources/youtube-dl.exe");
		parameters.put("downloadExeArguments", "https://www.youtube.com/watch?v=faXNL5FpWFg");

		TaskManager.getInstance().addTask(new TaskDownloadVideo(parameters));
		TaskManager.getInstance().addTask(new TaskDownloadVideo(parameters));
		TaskManager.getInstance().addTask(new TaskDownloadVideo(parameters));
		TaskManager.getInstance().addTask(new TaskDownloadVideo(parameters));
	}
}
