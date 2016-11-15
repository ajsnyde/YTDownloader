package splitter;

import java.util.logging.Level;

import logger.FileLogger;

public class RegexHelper {
  static String regexProcess(String pre) {
    FileLogger.logger().log(Level.FINEST, "Converting " + pre);

    pre = pre.replace("num", "(?:[\\d]+)");
    pre = pre.replace(" ", "\\s+");
    pre = pre.replace("title", "(.+)");
    pre = pre.replace("timestamp", "([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)");
    System.out.println("End: " + pre);
    return pre;
  }

  // parses hh:mm:ss and mm:ss formats to int of seconds
  public static int parseSeconds(String input) {
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
