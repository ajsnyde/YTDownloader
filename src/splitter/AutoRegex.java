package splitter;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logger.FileLogger;
import youtubeObjects.Album;
import youtubeObjects.Song;

/*
 * DOCUMENTATION:
 * This class turns raw descriptions (implicitly from Youtube videos) into Album objects. The description
 * MUST contain some lines with exactly one or exactly two timestamps (ex. 3:34:13)
 * To do this, the class removes all lines from the input that don't contain timestamps
 * It then runs through the timestamps (different process based on number of timestamps) and creates song
 * times and numbers. The timestamps are then removed, along with the SMALLER side of the string as split
 * by the timestamp. For example, "asdfasdfasdf3:31jkl;" would be read and returned as "asdfasdfasdf"
 * Finally, all the lines are compared and common characters are removed via classification and regex.
 * This occurs both on the left side moving inward and on the right side moving inward
 * 
 * Ex.
 * 
 * 1.) asdf (test)
 * 20.) jkl; (test)
 * 
 * the numbers are detected and removed with '\d+' regex
 * 
 * " (test)" is removed based on identical characters and whitespace (essentially " \(test\)" regex)
 * 
 * Pass 2:
 * Because the ".) " didn't line up correctly to trigger on the first pass (due to the variable # digits),
 * multiple passes are often necessary. In the second left pass, these characters are detected as identical
 * and are removed.
 * 
 * ...
 * End passes
 * 
 * After the process, the resulting lines (hopefully containing unique and minimally formatted titles) are
 * used to populate the titles in the songs that were made earlier.
 */

public class AutoRegex {

  Album album = new Album();

  public int numPasses = 4;

  public Album getAlbum(String description, int length) {

    description = preprocess(description);

    FileLogger.logger().log(Level.FINEST, description);

    if (isDoubleTimestamped(description))
      DoubleTimestampMethod(description);
    else if (isSingleTimestamped(description))
      SingleTimestampMethod(description, length);
    else
      return new Album();
    album.length = length;

    // Required for database to have relationships correctly recorded
    for (Song song : album.songs)
      song.album = album;

    return album;
  }

  private void SingleTimestampMethod(String description, int length) {
    int lineNum = 0;
    Song lastSong = new Song();
    for (String line : description.split("\n")) {
      Song song = new Song();
      lineNum++;
      String pattern = "([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)";
      Pattern r = Pattern.compile(pattern);
      Matcher m = r.matcher(line);

      // set start and end times to song
      m.find();
      song.startTime = RegexHelper.parseSeconds(m.group(1));
      song.number = lineNum;

      if (lineNum > 1) {
        lastSong.endTime = RegexHelper.parseSeconds(m.group(1));
        album.songs.add(lastSong);
      }
      lastSong = song;
    }
    lastSong.endTime = length;
    album.songs.add(lastSong);

    // description2 will be description stripped of timestamps
    String description2 = "";
    for (String line : description.split("\n")) {
      description2 += removeTimestamps(line) + "\n";
    }

    // for (Song song : album.songs)
    // System.out.println(song.title + " " + song.number + " " + song.start + "-" + song.end);

    description2 = removeCommonalities(description2);

    String[] lines = description2.split("\n");
    for (int i = 0; i < album.songs.size() && i < lines.length; ++i) {
      album.songs.get(i).title = lines[i];
    }
  }

  private void DoubleTimestampMethod(String description) {

    int lineNum = 0;
    String description2 = "";
    for (String line : description.split("\n")) {
      Song song = new Song();
      try {
        String pattern = "([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);

        // set start and end times to song
        m.find();
        song.startTime = RegexHelper.parseSeconds(m.group(1));
        m.find();
        song.endTime = RegexHelper.parseSeconds(m.group(1));

        line = removeTimestamps(line);
        // System.out.println(line);
        song.number = lineNum++;
        description2 += line + "\n";
        // System.out.println(line + " " + lineNum + " " + song.start + "-" + song.end);

        album.songs.add(song);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    description2 = removeCommonalities(description2);

    String[] lines = description2.split("\n");
    for (int i = 0; i < album.songs.size() && i < lines.length; ++i) {
      album.songs.get(i).title = lines[i];
    }
  }

  // Finds and deletes timestamps, returning the resulting left or right side, whichever is larger
  private String removeTimestamps(String line) {

    // take larger portion of line minus the timestamps
    line = line.replaceFirst("(\\s*+([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)\\s*+(.*[\\d]{0,2}:?[\\d]{1,3}:\\d\\d)?+)", "TIMESTAMP");
    int index = line.indexOf("TIMESTAMP");
    int lengthleft = index;
    int lengthright = line.length() - index - "TIMESTAMP".length();

    // System.out.println(lengthleft);
    // System.out.println(lengthright);
    line = lengthleft > lengthright ? line.substring(0, index) : line.substring(index + "TIMESTAMP".length());

    return line;
  }

  // removes left and right commonalities over a number of passes
  private String removeCommonalities(String in) {
    for (int i = 0; i < numPasses; ++i) {
      in = removeLeftCommonalities(in);
      in = removeRightCommonalities(in);
      in = trimLines(in);
    }
    FileLogger.logger().log(Level.FINEST, in);
    return in;
  }

  private String trimLines(String in) {
    String out = "";
    for (String line : in.split("\n"))
      out += line.trim() + "\n";
    return out;
  }

  // reverses the string, revmoves left commounalities, reverses again and returns
  private String removeRightCommonalities(String in) {
    String reverse = "";
    for (String line : in.split("\n")) {
      reverse += new StringBuilder(line).reverse() + "\n";
    }
    reverse = removeLeftCommonalities(reverse);
    String output = "";
    for (String line : in.split("\n")) {
      output += new StringBuilder(line).reverse() + "\n";
    }
    return output;
  }

  private String removeLeftCommonalities(String in) {

    String[] lines = in.split("\n");

    ArrayList<Character> common = new ArrayList<Character>();
    ArrayList<Boolean> same = new ArrayList<Boolean>();

    // get minimum length, will act as condition for looking for
    // commonalities;
    int minLength = Integer.MAX_VALUE;
    for (String line : lines) {
      minLength = Integer.min(minLength, line.length());
      // System.out.println(line);
    }

    String regex = "(";

    // start from one side, go to other until common strings end
    for (int i = 0; i < minLength; ++i) {
      char compare = '\f'; // placeholder, since chars can't be null;
      boolean linesCommon = true;

      for (int j = 0; j < lines.length - 1; ++j) {
        if (compare == '\f')
          compare = lines[j].charAt(i);
        else if (Character.isDigit(compare) && Character.isDigit(lines[j].charAt(i))) {
        } else if (Character.isWhitespace(compare) && Character.isWhitespace(lines[j].charAt(i))) {
        } else if (compare != lines[j].charAt(i)) {
          linesCommon = false;
          break;
        }
        // System.out.println(linesCommon + "-" + compare + "=" + lines[j].charAt(i) + "?");
      }

      if (linesCommon && Character.isDigit(compare)) {
        regex += "\\d+";
      } else if (linesCommon && Character.isWhitespace(compare))
        regex += "\\s+";
      else if (linesCommon) {
        if (compare == '(' || compare == ')' || compare == '.' || compare == '[' || compare == ']')
          regex += "\\";
        regex += compare;
      }
    }
    regex += ")";
    FileLogger.logger().log(Level.FINEST, regex);
    String output = "";
    for (String line : lines) {
      output += line.replaceFirst(regex, "") + "\n";
    }
    output = output.substring(0, output.length() - 1);

    return output;
  }

  // returns true if all lines have 2 timestamps
  private boolean isDoubleTimestamped(String description) {

    for (String line : description.split("\n"))
      if (!(containsTimestamps(line) == 2)) {
        return false;
      }
    return true;
  }

  private boolean isSingleTimestamped(String description) {

    for (String line : description.split("\n"))
      if (!(containsTimestamps(line) == 1)) {
        return false;
      }
    return true;
  }

  private String preprocess(String description) {
    String pre = "";
    for (String line : description.split("\n"))
      if (containsTimestamps(line) > 0)
        pre += line.trim() + "\n";
    if (pre.length() != 0)
      return pre.substring(0, pre.length() - 1); // gets rid of extra newline
    else
      return pre;
  }

  private int containsTimestamps(String line) {
    int numTimestamps = 0;

    try {
      String pattern = "([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)";
      Pattern r = Pattern.compile(pattern);
      Matcher m = r.matcher(line);

      while (m.find())
        numTimestamps++;
    } catch (Exception e) {
      FileLogger.logger().log(Level.WARNING, "Failure when trying to locate a timestamp in a line of text");
    }
    return numTimestamps;
  }
}
