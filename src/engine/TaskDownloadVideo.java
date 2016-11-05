package engine;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tables.DownloadTableModel;

//should accept ONE url with parameters for downloading (meta, video, etc.)
public class TaskDownloadVideo extends Task {

	final Pattern percentage = Pattern.compile("(\\d+(\\.)?\\d+)%");

	public TaskDownloadVideo(HashMap<String, Object> parameters) {
		this.parameters = parameters;

		((DownloadTableModel) parameters.get("model")).addTask(this);
	}

	@Override
	public void run() {
		try {
			Execute execute = new Execute(parameters);
			Thread thread = new Thread(execute, "test");
			thread.start();
			synchronized (execute) {
				try {
					execute.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String line;
			while ((line = execute.input.readLine()) != null) {
				// TODO: Parse line and update appropriate GUI components, as passed in through parameters
				parseLine(line);
				System.out.println(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			TaskManager.getInstance().decreaseThreadCount();
		}
	}

	void parseLine(String line) {
		if (line.contains("[download]")) {
			try {
				Matcher m = percentage.matcher(line);
				if (m.find()) {

					progress = Double.parseDouble(m.group(1));
					System.out.println(progress);
					((DownloadTableModel) parameters.get("model")).fireTableDataChanged();

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
