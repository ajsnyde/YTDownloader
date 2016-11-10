package engine;

import java.time.Instant;
import java.util.HashMap;

import javax.swing.JProgressBar;

// A task is fundamentally a set of things to get done - it will be represented in the GUI as a main top-panel unit. It should have constant updates from subtasks so that it can update the GUI
// Subtasks may be included.
//TODO: design way to specify dependencies and orders on tasks. Ideally, multiple subtasks should be taken when possible while still following proper dependency order.
// ex. Download all videos from a channel and process them into mp3 songs. Many videos would be downloaded at once, but the conversion to MP3s would take place only after the download of the video; likewise with splitting...

public abstract class Task implements Runnable {
	public String name;
	public String size;
	public Double progress = 0.0;
	public String status;
	public String speed;
	public HashMap<String, Object> parameters; // contains parameters for Task to use
	public Instant timeCreated = Instant.now();
	public Instant timeStarted;
	public Instant timeCompleted;
	public String eta;
	public int priority;

	void updateProgress(int in) {
		if (parameters.get("progressBar") != null) {
			((JProgressBar) parameters.get("progressBar")).setIndeterminate(false);
			((JProgressBar) parameters.get("progressBar")).setValue(in);
		}
	}
}
