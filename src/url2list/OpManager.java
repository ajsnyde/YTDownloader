package url2list;

import java.util.concurrent.atomic.AtomicInteger;

public class OpManager {
	public static AtomicInteger MAX_THREADS = new AtomicInteger(5);
	public static AtomicInteger CUR_THREADS = new AtomicInteger(0);

	public static void main(String input) {
		String[] lines = input.split("\\r?\\n");
		int lineNum = 0;
		Runnable task;

		// iterate through each line, starting new thread for each
		for (int i = 0; lineNum < lines.length; ++i) {
			// System.out.println(Thread.activeCount());
			try {
				if (CUR_THREADS.get() < MAX_THREADS.get() && lineNum < lines.length) {
					task = new Fetcher(lines[lineNum]);
					new Thread(task).start();
					++lineNum;
				}
				Thread.sleep(0);
			} catch (InterruptedException e) {
				if (CUR_THREADS.get() < MAX_THREADS.get() && lineNum < lines.length) {
					task = new Fetcher(lines[lineNum]);
					new Thread(task).start();
					++lineNum;
				}
			}
		}
	}
}
