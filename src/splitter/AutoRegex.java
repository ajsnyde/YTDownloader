package splitter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoRegex {

	public Album getAlbum(String description, int length) {

		description = preprocess(description);

		System.out.println(description);

		if (isDoubleTimestamped(description))
			return DoubleTimestampMethod(description);
		else
			return SingleTimestampMethod(description, length);
	}

	private Album SingleTimestampMethod(String description, int length) {
		Album album = new Album();

		int lineNum = 0;
		Song lastSong = new Song();
		for (String line : description.split("\n")) {
			Song song = new Song();
			String pattern = "([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(line);

			// set start and end times to song
			m.find();
			song.start = RegexHelper.parseSeconds(m.group(1));
			song.number = lineNum;

			if (lineNum > 1) {
				lastSong.end = RegexHelper.parseSeconds(m.group(1));
				album.songs.add(lastSong);
			}
			lastSong = song;

		}
		lastSong.end = length;
		album.songs.add(lastSong);

		// description2 will be description stripped of timestamps
		String description2 = "";
		for (String line : description.split("\n")) {
			description2 += removeTimestamps(line) + "\n";
		}

		// TODO: remove commonalities in resulting lines before setting
		// resulting lines as titles

		System.out.println(description2);

		for (Song song : album.songs)
			System.out.println(song.title + " " + song.number + " " + song.start + "-" + song.end);

		removeCommonalities(description2);

		return album;
	}

	private Album DoubleTimestampMethod(String description) {
		Album album = new Album();

		int lineNum = 0;
		for (String line : description.split("\n")) {
			Song song = new Song();
			try {
				String pattern = "([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(line);

				// set start and end times to song
				m.find();
				song.start = RegexHelper.parseSeconds(m.group(1));
				m.find();
				song.end = RegexHelper.parseSeconds(m.group(1));

				// TODO: add lines (without timestamps and other garbage)
				// together, find commonalities, and remove commonalities.
				line = removeTimestamps(line);

				song.number = lineNum++;
				song.title = line;

				System.out.println(line + " " + lineNum + " " + song.start + "-" + song.end);

				album.songs.add(song);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		removeCommonalities(description);
		return album;
	}

	private String removeTimestamps(String line) {

		// take larger portion of line minus the timestamps
		line = line.replaceAll("(\\s*+([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)\\s*+(.*[\\d]{0,2}:?[\\d]{1,3}:\\d\\d)?+)", "TIMESTAMP");
		int index = line.indexOf("TIMESTAMP");
		int lengthleft = index;
		int lengthright = line.length() - index - "TIMESTAMP".length();

		// System.out.println(lengthleft);
		// System.out.println(lengthright);
		line = lengthleft > lengthright ? line.substring(0, index) : line.substring(index + "TIMESTAMP".length());

		return line;

	}

	private String removeCommonalities(String in) {

		String[] lines = in.split("\n");

		ArrayList<Character> common = new ArrayList<Character>();
		ArrayList<Boolean> same = new ArrayList<Boolean>();

		// get minimum length, will act as condition for looking for
		// commonalities;
		int minLength = Integer.MAX_VALUE;
		for (String line : lines) {
			minLength = Integer.min(minLength, line.length());
			System.out.println(line);
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
				}
				System.out.println(linesCommon + "-" + compare + "=" + lines[j].charAt(i) + "?");
			}

			if (linesCommon && Character.isDigit(compare)) {
				regex += "\\d+";
			} else if (linesCommon && Character.isWhitespace(compare))
				regex += "\\s+";
			else if (linesCommon) {
				regex += compare;
			}
		}
		regex += ")";

		System.out.println(regex);

		return in;
	}

	// returns true if all lines have 2 timestamps
	private boolean isDoubleTimestamped(String description) {

		for (String line : description.split("\n"))
			if (!(containsTimestamps(line) == 2)) {
				return false;
			}
		return true;
	}

	private String preprocess(String description) {
		String pre = "";
		for (String line : description.split("\n"))
			if (containsTimestamps(line) > 0)
				pre += line + "\n";
		return pre.substring(0, pre.length() - 2); // gets rid of extra newline
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
			System.out.println(e.getMessage());
		}
		return numTimestamps;
	}
}
