package splitter;

public class Tester {

	public static void main(String[] args) {
		AutoRegex regex = new AutoRegex();
		regex.getAlbum(
				"1.\"Unknown Legend\" 0:00\r\n2.\"From Hank to Hendrix\" 4:33 \r\n3.\"You and Me\" 9:50\r\n4.\"Harvest Moon\" 13:36\r\n5.\"War of Man\" 18:41\r\n6.\"One of These Days\" 24:25\r\n7.\"Such a Woman\" 29:23\r\n8.\"Old King\" 34:01\r\n9.\"Dreamin' Man\" 36:58\r\n10.\"Natural Beauty\" 41:353",
				3886);
	}
}
