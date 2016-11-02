package splitter;

public class Tester {

	public static void main(String[] args) {
		AutoRegex regex = new AutoRegex();
		regex.getAlbum(
				"\u25BA Download: https://www.psyshop.com/shop/CDs/tim/...\r\n\r\n01 In This World There Is Room For Everyone 0:00\r\n02 Dancing Elves 7:58\r\n03 Spirituality 16:06\r\n04 Cold Less 23:07\r\n05 Somewhere In Travel 32:07\r\n06 Enlightenment 40:02 \r\n07 Hypnotized 46:42\r\n08 Trance Asian Express 53:52\r\n09 Shamanic Dream 1:01:08\r\n10 Worlds In Collision 1:09:13\r\n\r\nFollow Timewarp Records / Geomagnetic.tv:\r\n\u25BA Bandcamp: https://timewarprecords.bandcamp.com/\r\n\u25BA Facebook: https://www.facebook.com/Timewarp.rec\r\n\u25BA Soundcloud: https://soundcloud.com/timewarp-records\r\n\u25BA Youtube: https://www.youtube.com/channel/UCd7y...",
				3886);
	}
}
