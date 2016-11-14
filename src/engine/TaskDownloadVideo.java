package engine;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tables.DownloadTableModel;

//should accept ONE url video to download from
//adds video file location
public class TaskDownloadVideo extends Task {

	final Pattern downloadStats = Pattern.compile(
			"(\\d+\\.?\\d+)% of (\\d+\\.\\d+[MK]iB)\\s+at\\s+(\\d+\\.\\d+[MK]iB\\/s)\\s+ETA\\s+(\\d+:\\d+:?\\d*)");

	final Pattern quotation = Pattern.compile("\"([^\"]+)\"");

	final Pattern progressNumber = Pattern.compile("(\\d+) of (\\d+)");

	public TaskDownloadVideo(HashMap<String, Object> parameters) {
		this.parameters = parameters;
		if (parameters.get("model") != null)
			((DownloadTableModel) parameters.get("model")).addTask(this);
	}

	@Override
	public void run() {
		increaseParent();
		try {
			HashMap<String, Object> downloadParameters = new HashMap<String, Object>();
			downloadParameters.put("ExeLocation", "resources/youtube-dl.exe");
			downloadParameters.put("ExeArguments",
					"-x --audio-format mp3 -o \"Downloads/%(uploader)s/%(uploader)s - %(title)s.%(ext)s\" "
							+ parameters.get("url"));

			Execute execute = new Execute(downloadParameters);
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
			updateProgress(100);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			decreaseParent();
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
					if (parameters.get("model") != null)
						((DownloadTableModel) parameters.get("model")).fireTableDataChanged();
					updateProgress(progress.intValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Matcher m = progressNumber.matcher(line);
				if (m.find()) {
					progress = Integer.parseInt(m.group(1)) / (double) Integer.parseInt(m.group(2));
					updateProgress(progress.intValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (line.contains("[ffmpeg] Destination: ")) {
			System.out.println("placing file in audioLocation: " + line.substring(22));
			parameters.put("audioLocation", new File(line.substring(22)));
		}
	}
}
