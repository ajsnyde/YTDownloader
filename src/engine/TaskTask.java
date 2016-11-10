package engine;

import java.util.HashMap;
import java.util.Vector;
import tables.DownloadTableModel;

//This essentially runs multiple Tasks in sequential order using a single thread.

//TODO: MultiTask - similar to this class (maybe even extends it), but completes tasks using many threads. The combination of this class and the MultiTask class
//would allow for some flexibility and allowance for prerequisites. For example, grab all metadata using 100 threads, and start downloading all videos after every  metadata task is complete.
public class TaskTask extends Task {

	public TaskTask(HashMap<String, Object> parameters) {
		this.parameters = parameters;

		// TODO: set the model function in the abstract class
		if (parameters.get("model") != null)
			((DownloadTableModel) parameters.get("model")).addTask(this);
	}

	@Override
	public void run() {
		// TODO: Find nicer solution to thread count problem
		TaskManager.getInstance().increaseThreadCount();
		try {
			for (int i = 0; i < ((Vector<Task>) parameters.get("tasks")).size(); ++i) {
				((Vector<Task>) parameters.get("tasks")).get(i).run();
				updateProgress((int) (100 * ((double) (i + 1) / ((Vector<Task>) parameters.get("tasks")).size())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			TaskManager.getInstance().decreaseThreadCount();
		}
	}
}
