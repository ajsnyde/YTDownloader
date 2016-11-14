package url2list;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fetcher implements Runnable {

  String input = "";
  final static String DEFAULT_REGEX = "(?:item-section\\\")(?is).+?(href=\"/watch[^\\\"]+)";
  static String regex = "(?:item-section\\\")(?is).+?(href=\"/watch[^\\\"]+)";

  Fetcher(String in) {
    OpManager.CUR_THREADS.incrementAndGet();
    input = in;
  }

  @Override
  public void run() {
    fetch(input);
  }

  void fetch(String line) {

    String html = fetchHTML(line);
    String URL = fetchURL(html);
    GUI.output.add(URL);
  }

  String fetchHTML(String lookup) {
    long start = System.nanoTime();
    lookup = lookup.replaceAll("\\s", "+");
    lookup = lookup.replaceAll("-", "");
    lookup = lookup.replaceAll("–", "");
    String content = null;
    URLConnection connection = null;
    try {
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
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(html);

    if (matcher.find()) {
      return "https://www.youtube.com" + matcher.group(1).substring(6, matcher.group(1).length());
    } else {
      return "NOT FOUND";
    }
  }
}
