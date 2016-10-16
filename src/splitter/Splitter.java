package splitter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import downloader.Engine;

public class Splitter {
	
	String description = "";
	static String exeLocation = "resources/sox-14-4-2/sox.exe";
	Pattern pattern = Pattern.compile("([\\d]*\\.?)[ \\t]*(.+?)[ \\t]*([\\d]{0,2}:?[\\d]{1,3}:\\d\\d).*\\n");
	
	
	public void splitByDescription(String url){
		download(url);
		parse(url);
	}
	
	void download(String url){
		Engine.exeWait(url, "-oDownloads/%(title)s/%(title)s.%(ext)s", "-x", "--audio-format", "mp3");
		System.out.println("Done downloading mp3");
		description = Engine.getMetaElement(url, "description");
	}
	
	
	void parse(String url){
		Matcher matcher = pattern.matcher(description);
		String title = "TEST";
		String time = "00:00";
		while(matcher.find()){
			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
			System.out.println(matcher.group(3));
			
			time = matcher.group(2);
			title = matcher.group(3);
		}
	}
	
	//sox test.mp3 -c 2 -C 320 test2.mp3 trim 10
	void split(String url, String title, String timeStart, String timeEnd){
		
	}
	
}
