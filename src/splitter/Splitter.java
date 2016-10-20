package splitter;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import youtube_dl.Engine;

public class Splitter {

	static final String EXE_LOCATION_DEFAULT = "resources/sox-14-4-2/sox.exe";
	static String exeLocation = EXE_LOCATION_DEFAULT;
	
	static final String DEFAULT_REGEX ="([\\d]*\\.?)[ \\t]*(.+?)[ \\t]*([\\d]{0,2}:?[\\d]{1,3}:\\d\\d).*\\n";
	static String regex = DEFAULT_REGEX;
	
	static boolean keepVideo = false;
	
	String description = "";
	String albumTitle = "";
	int duration = 0;
	
	public void splitByDescription(String url) {
		download(url);
		parseAndSplit(url);
	}

	void download(String url) {
		if(!new File("Downloads/"+Engine.getMetaElement(url, "title") + "/" +Engine.getMetaElement(url, "title") + ".mp3").exists())
			Engine.exeWait(url, "-oDownloads/%(title)s/%(title)s.%(ext)s", "-x", "--audio-format", "mp3");//, keepVideo ? "-k" : "");
		System.out.println("Done downloading mp3");
		description = Engine.getMetaElement(url, "description");
		duration = Integer.parseInt(Engine.getMetaElement(url, "duration"));
		albumTitle = Engine.getMetaElement(url, "title");
	}

	void parseAndSplit(String url) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(description);
		String title = "TEST";
		String time = "00:00";
		int tracknum = 0;
		while (matcher.find()) {
			
			if (time != "00:00") 
				split(url, title, tracknum, parseSeconds(time), parseSeconds(matcher.group(1))-parseSeconds(time));
			
			title = matcher.group(2);
			time = matcher.group(1);
			
			tracknum++;
		}
	}

	void split(String url, String title, int tracknum, int timeStart, int timeEnd) {
		Process process;
		try {
			System.out.println(exeLocation.toString() + " Downloads/" + albumTitle + "/" + albumTitle + ".mp3"+
					" Downloads/" + albumTitle + "/" + title + ".mp3"+ " trim "+ timeStart + " "+ timeEnd + "");
			
			process = new ProcessBuilder(exeLocation.toString(), "Downloads/" + albumTitle + "/" + albumTitle + ".mp3",
					"Downloads/" + albumTitle + "/" + title + ".mp3", "trim", timeStart + "", timeEnd + "").start();
			process.waitFor();
			
			String song = "Downloads/" + albumTitle + "/" + title + ".mp3";
			
			process = new ProcessBuilder("resources/id3tool.exe", "-c", tracknum + "", song).start();
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
