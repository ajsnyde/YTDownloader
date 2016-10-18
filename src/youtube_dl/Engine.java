package youtube_dl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Engine {

	// I have plans for you.. I promise!
	ArrayList<Thread> threads = new ArrayList<Thread>();
	static String exeLocation = "resources/youtube-dl.exe";
	static String METADIR = "Downloads/meta/";
	static boolean forceDownload = false;

	public Engine() {
	}

	// friggin varargs.. what an annoyingly messy workaround
	public static void exeWait(String... commands) {
		String[] exe = { exeLocation };
		try {
			commands = Stream.concat(Arrays.stream(exe), Arrays.stream(commands)).toArray(String[]::new);

			Process process = new ProcessBuilder(commands).start();

			BufferedReader input = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			try {
				String line;
				while ((line = input.readLine()) != null) {
					System.out.println(line);

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			process.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BufferedReader exe() {
		return exe("https://www.youtube.com/watch?v=4R-JGw3VTuY"); // example
	}

	// create a new thread that launches and monitors (or more accurately,
	// returns the buffer of) the yt-downloader.exe
	public static BufferedReader exe(String command) {
		Execute execute = new Execute(command);
		Thread thread = new Thread(execute, "test");
		thread.start();
		synchronized (execute) {
			try {
				execute.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return execute.input;
	}

	private static String downloadMeta(String url) {
		try {
			Process process = new ProcessBuilder(exeLocation.toString(), url, "-o" + getMetaLocation(url) + ".%(ext)s",
					"--skip-download", "--write-info-json").start();
			process.waitFor();
			return readFile(getMetaLocation(url) + ".info.json");
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public static String getMetaElement(String url, String element) {
		JSONParser parser = new JSONParser();
		ArrayList<String> out = new ArrayList<String>();
		try {
			JSONObject jsonObject;

			// if file doesn't exist, go through full download process, else
			// read and parse
			if (!new File(getMetaLocation(url)).exists() && !forceDownload)
				return ((JSONObject) parser.parse(downloadMeta(url))).get(element).toString();
			else
				return ((JSONObject) parser.parse(readFile(getMetaLocation(url)))).get(element).toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public static ArrayList<String> getMetaElements(String url, String... elements) {
		JSONParser parser = new JSONParser();
		ArrayList<String> out = new ArrayList<String>();

		for (String element : elements)
			out.add(getMetaElement(url, element));
		return out;
	}

	private static String getMetaLocation(String url) {
		return METADIR + url.replaceFirst("https://www.youtube.com/watch\\?", "");
	}

	private static String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}
}
