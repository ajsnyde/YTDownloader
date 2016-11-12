package engine;

import java.util.HashMap;
import java.util.Vector;

public class Test {

	public static void main(String[] args) {

		// This code will create a series of tasks and subtasks. The two low-level tasks will download metadata and the video. These tasks are added to TaskTasks so as to run in succession. Each of
		// the TaskTasks are added to the MultiTask, so that many threads can be running at once while ensuring that specific task run in succession.
		// This is merely an example and isn't absolutely needed with those tasks. In the future, more tasks (ex. conversionTasks) WILL require completion of prior tasks before they can run.

		HashMap<String, Object> multiParameters = new HashMap<String, Object>();
		multiParameters.put("maxThreads", 3);
		Vector<Task> tasktasks = new Vector<Task>();

		for (int i = 0; i < 5; ++i) {

			HashMap<String, Object> tasktaskParameters = new HashMap<String, Object>();
			Vector<Task> downloadMetaAndVid = new Vector<Task>();

			HashMap<String, Object> metaParameters = new HashMap<String, Object>();
			metaParameters.put("downloadExeLocation", "resources/youtube-dl.exe");
			metaParameters.put("downloadExeArguments",
					"--write-info-json --skip-download -o \"Downloads/%(uploader)s/%(uploader)s - %(title)s.%(ext)s\" https://www.youtube.com/watch?v=c4jgr8xz25Q");

			HashMap<String, Object> vidParameters = new HashMap<String, Object>();
			vidParameters.put("downloadExeLocation", "resources/youtube-dl.exe");
			vidParameters.put("downloadExeArguments", "https://www.youtube.com/watch?v=c4jgr8xz25Q");

			downloadMetaAndVid.add(new TaskDownloadVideo(metaParameters));
			downloadMetaAndVid.add(new TaskDownloadVideo(vidParameters));

			tasktaskParameters.put("tasks", downloadMetaAndVid);

			TaskTask subTask = new TaskTask(tasktaskParameters);
			tasktasks.add(subTask);
		}

		multiParameters.put("tasks", tasktasks);

		TaskManager.getInstance().addTask(new MultiTask(multiParameters));
	}
}
