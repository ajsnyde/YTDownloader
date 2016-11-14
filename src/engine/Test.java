package engine;

import java.util.HashMap;
import java.util.Vector;

public class Test {

	public static void main(String[] args) {

		// grab all Metadatas from a url
		HashMap<String, Object> metaParameters = new HashMap<String, Object>();
		metaParameters.put("url", "https://www.youtube.com/watch?v=OJVoApMH-y4");
		new TaskDownloadMeta(metaParameters).run();

		for (Metadata meta : (Vector<Metadata>) metaParameters.get("metaDatas")) {

			HashMap<String, Object> subParameters = new HashMap<String, Object>();
			subParameters.put("url", meta.url);
			subParameters.put("metadata", meta);

			new TaskDownloadVideo(subParameters).run();
			System.out.println("DONE");
			new TaskGetAlbum(subParameters).run();
			System.out.println("DONE");
			new TaskSplitByAlbum(subParameters).run();
			System.out.println("DONE");

		}

		// HashMap<String, Object> tasktaskParameters = new HashMap<String, Object>();
		// Vector<Task> tasks = new Vector<task>();
		// TaskTask tasktask = new TaskTask();

	}
}
