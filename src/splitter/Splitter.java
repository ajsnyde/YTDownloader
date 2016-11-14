package splitter;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import youtube_dl.Engine;

public class Splitter {

  static final String EXE_LOCATION_DEFAULT = "resources/sox-14-4-2/sox.exe";
  static String exeLocation = EXE_LOCATION_DEFAULT;

  static final String DEFAULT_REGEX = "title timestamp";
  static final int DEFAULT_TITLE_GROUP = 1;
  static final int DEFAULT_TIMESTAMP_GROUP = 2;

  static String regex = DEFAULT_REGEX;
  static int titleGroup = 1;
  static int timestampGroup = 2;

  static boolean keepVideo = false;

  String description = "";
  String albumTitle = "";
  int duration = 0;

  public void splitByDescription(String url) {
    download(url);
    parseAndSplit(url);
  }

  void download(String url) {
    if (!new File("Downloads/" + Engine.getMetaElement(url, "title") + "/" + Engine.getMetaElement(url, "title") + ".mp3").exists())
      Engine.exeWait(url, "-oDownloads/%(title)s/%(title)s.%(ext)s", "-x", "--audio-format", "mp3");
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
        split(url, title, tracknum, RegexHelper.parseSeconds(time), RegexHelper.parseSeconds(matcher.group(timestampGroup)) - RegexHelper.parseSeconds(time));

      title = matcher.group(titleGroup);
      time = matcher.group(timestampGroup);

      tracknum++;
    }
  }

  void split(String url, String title, int tracknum, int timeStart, int timeEnd) {
    Process process;
    try {
      System.out
          .println(exeLocation.toString() + " Downloads/" + albumTitle + "/" + albumTitle + ".mp3" + " Downloads/" + albumTitle + "/" + title + ".mp3" + " trim " + timeStart + " " + timeEnd + "");

      process = new ProcessBuilder(exeLocation.toString(), "Downloads/" + albumTitle + "/" + albumTitle + ".mp3", "Downloads/" + albumTitle + "/" + title + ".mp3", "trim", timeStart + "",
          timeEnd + "").start();
      process.waitFor();

      String song = "Downloads/" + albumTitle + "/" + title + ".mp3";

      process = new ProcessBuilder("resources/id3tool.exe", "-c", tracknum + "", song).start();
      process.waitFor();
      process = new ProcessBuilder("resources/id3tool.exe", "-a", albumTitle, song).start();
      process.waitFor();
      process = new ProcessBuilder("resources/id3tool.exe", "-t", title + "", song).start();
      process.waitFor();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
