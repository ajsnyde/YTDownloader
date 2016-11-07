package engine;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tables.DownloadTableModel;

//should accept ONE url with parameters for downloading (meta, video, etc.)
public class TaskDownloadVideo extends Task {

	final Pattern downloadStats = Pattern.compile(
			"(\\d+\\.?\\d+)% of (\\d+\\.\\d+[MK]iB)\\s+at\\s+(\\d+\\.\\d+[MK]iB\\/s)\\s+ETA\\s+(\\d+:\\d+:?\\d*)");

	public TaskDownloadVideo(HashMap<String, Object> parameters) {
		this.parameters = parameters;
		((DownloadTableModel) parameters.get("model")).addTask(this);
	}

	@Override
	public void run() {
		TaskManager.getInstance().increaseThreadCount();
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
				Matcher m = downloadStats.matcher(line);
				if (m.find()) {
					progress = Double.parseDouble(m.group(1));
					size = m.group(2);
					speed = m.group(3);
					eta = m.group(4);
					((DownloadTableModel) parameters.get("model")).fireTableDataChanged();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
