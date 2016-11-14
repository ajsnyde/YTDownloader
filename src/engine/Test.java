package engine;

import java.time.Instant;
import java.util.HashMap;
import java.util.Vector;

public class Test {

	public static void main(String[] args) {
		System.out.println("Starting setup at " + Instant.now());
		// grab all Metadatas from a url
		HashMap<String, Object> metaParameters = new HashMap<String, Object>();
		metaParameters.put("url", "https://www.youtube.com/watch?v=rp6hJQMF18I");// "https://www.youtube.com/user/CosmicSoundwaves");

		Vector<Task> tasktasks = new Vector<Task>();

		new TaskDownloadMeta(metaParameters).run();
		for (Metadata meta : (Vector<Metadata>) metaParameters.get("metaDatas")) {
			HashMap<String, Object> tasktaskParameters = new HashMap<String, Object>();
			Vector<Task> subtasks = new Vector<Task>();

			HashMap<String, Object> subParameters = new HashMap<String, Object>();
			subParameters.put("url", meta.url);
			subParameters.put("metadata", meta);

			TaskDownloadVideo download = new TaskDownloadVideo(subParameters);
			TaskGetAlbum album = new TaskGetAlbum(subParameters);
			TaskSplitByAlbum split = new TaskSplitByAlbum(subParameters);

			subtasks.add(download);
			subtasks.add(album);
			subtasks.add(split);

			tasktaskParameters.put("tasks", subtasks);

			tasktasks.add(new TaskTask(tasktaskParameters));
		}
		HashMap<String, Object> multitaskParameters = new HashMap<String, Object>();

		multitaskParameters.put("tasks", tasktasks);
		multitaskParameters.put("maxThreads", 200);

		System.out.println("Done with setup at " + Instant.now());

		TaskManager.getInstance().addTask(new MultiTask(multitaskParameters));
	}
}
