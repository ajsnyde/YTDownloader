import java.util.HashMap;

//should accept ONE url with parameters for downloading (meta, video, etc.)
public class TaskDownloadVideo extends Task {

	public TaskDownloadVideo(HashMap<String, Object> parameters) {
		this.parameters = parameters;
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
				System.out.println(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
