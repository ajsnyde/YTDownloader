package url2list;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fetcher implements Runnable { // REGEX FOR FIRST VIDEO: /<a aria-hidden="true" href="/watch/

	String input = "";

	Fetcher(String in) {
		OpManager.CUR_THREADS.incrementAndGet();
		input = in;
	}

	@Override
	public void run() {
		//System.out.println("New Thread!");
		fetch(input);
	}

	void fetch(String line) {
		
		String html = fetchHTML(line);
		String URL = fetchURL(html);
		//System.out.println(URL);
		GUI.output.add(URL);
	}

	String fetchHTML(String lookup) {
		long start = System.nanoTime();
		lookup = lookup.replaceAll("\\s", "+");
		lookup = lookup.replaceAll("-", "");
		lookup = lookup.replaceAll("�", "");
		String content = null;
		URLConnection connection = null;
		try {
			//System.out.println("https://www.youtube.com/results?search_query=" + lookup);
			connection = new URL("https://www.youtube.com/results?search_query=" + lookup).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		OpManager.CUR_THREADS.decrementAndGet();
		return content;
	}

	String fetchURL(String html) {
		long start = System.nanoTime();
		Pattern pattern = Pattern.compile("(?:item-section\\\")(?is).+?(href=\"/watch[^\\\"]+)");
		Matcher matcher = pattern.matcher(html);

		if (matcher.find()) {
			return "https://www.youtube.com" + matcher.group(1).substring(6, matcher.group(1).length());
		} else {
			return "NOT FOUND";
		}
	}
}
