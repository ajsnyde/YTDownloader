package engine;

import java.util.HashMap;
import java.util.Vector;
import tables.DownloadTableModel;

//This essentially runs multiple Tasks in sequential order using a single thread.
public class TaskTask extends Task {

	public TaskTask(HashMap<String, Object> parameters) {
		this.parameters = parameters;

		// TODO: set the model function in the abstract class
		((DownloadTableModel) parameters.get("model")).addTask(this);
	}

	@Override
	public void run() {
		// TODO: Find nicer solution to thread count problem
		TaskManager.getInstance().increaseThreadCount();
		try {
			for (int i = 0; i < ((Vector<Task>) parameters.get("tasks")).size(); ++i) {
				((Vector<Task>) parameters.get("tasks")).get(i).run();
				updateProgress((int) (100 * ((double) i / ((Vector<Task>) parameters.get("tasks")).size())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			TaskManager.getInstance().decreaseThreadCount();
		}
	}
}
