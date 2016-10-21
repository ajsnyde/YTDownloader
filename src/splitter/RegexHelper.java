package splitter;

public class RegexHelper {
	static String regexProcess(String pre) {
		System.out.println("Converting " + pre);

		pre = pre.replace("num", "(?:[\\d]+)");
		pre = pre.replace(" ", "\\s+");
		pre = pre.replace("title", "(.+)");
		pre = pre.replace("timestamp", "([\\d]{0,2}:?[\\d]{1,3}:\\d\\d)");
		System.out.println("End: " + pre);
		return pre;
	}
}
