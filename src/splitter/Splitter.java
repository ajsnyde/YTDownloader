package splitter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import youtube_dl.Engine;

public class Splitter {

	String description = "";
	String albumTitle = "";
	int duration = 0;
	static String exeLocation = "resources/sox-14-4-2/sox.exe";
	Pattern pattern = Pattern.compile("([\\d]*\\.?)[ \\t]*(.+?)[ \\t]*([\\d]{0,2}:?[\\d]{1,3}:\\d\\d).*\\n");

	public void splitByDescription(String url) {
		download(url);
		parseAndSplit(url);
	}

	void download(String url) {
		Engine.exeWait(url, "-oDownloads/%(title)s/%(title)s.%(ext)s", "-x", "--audio-format", "mp3");
		System.out.println("Done downloading mp3");
		description = Engine.getMetaElement(url, "description");
		duration = Integer.parseInt(Engine.getMetaElement(url, "duration"));
		albumTitle = Engine.getMetaElement(url, "title");
	}

	void parseAndSplit(String url) {
		Matcher matcher = pattern.matcher(description);
		String title = "TEST";
		String time = "00:00";
		while (matcher.find()) {
			
			if (time != "00:00") {
				split(url, title, parseSeconds(time), parseSeconds(matcher.group(3))-parseSeconds(time));
			}

			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
			System.out.println(matcher.group(3));
			
			title = matcher.group(2);
			time = matcher.group(3);
		}
	}

	// sox test.mp3 -c 2 -C 320 test2.mp3 trim 10
	void split(String url, String title, int timeStart, int timeEnd) {
		Process process;
		try {
			System.out.println(exeLocation.toString() + " Downloads/" + albumTitle + "/" + albumTitle + ".mp3"+
					" Downloads/" + albumTitle + "/" + title + ".mp3"+ " trim "+ timeStart + " "+ timeEnd + "");
			
			process = new ProcessBuilder(exeLocation.toString(), "Downloads/" + albumTitle + "/" + albumTitle + ".mp3",
					"Downloads/" + albumTitle + "/" + title + ".mp3", "trim", timeStart + "", timeEnd + "").start();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// parses hh:mm:ss and mm:ss formats to int of seconds
	int parseSeconds(String input) {
		String[] tokens = input.split(":");
		if (tokens.length == 3) {
			int hours = Integer.parseInt(tokens[0]);
			int minutes = Integer.parseInt(tokens[1]);
			int seconds = Integer.parseInt(tokens[2]);
			return (3600 * hours + 60 * minutes + seconds);
		} else if (tokens.length == 2) {
			int minutes = Integer.parseInt(tokens[0]);
			int seconds = Integer.parseInt(tokens[1]);
			return (60 * minutes + seconds);
		} else
			return 0;
	}
}
