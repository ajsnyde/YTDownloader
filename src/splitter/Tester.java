package splitter;

public class Tester {

	public static void main(String[] args) {
		AutoRegex regex = new AutoRegex();
		regex.getAlbum(
				"Since I was unable to find a good mix of all the chilled out songs of Joel's that I love, I decided to make my own.\r\n\r\nFor those asking, yes this mix is on soundcloud - https://soundcloud.com/music-to-work-to\r\n\r\nTrack List:\r\n00:00 - All I have \r\n7:11 - Jaded \r\n14:45 - Alone with you \r\n22:12 - I Remember \r\n31:20 - Faxing Berlin \r\n35:36 - Contact \r\n41:35 - HR 8938 Cephei/Raise your weapon \r\n51:05 - Slip \r\n58:06 - October \r\n1:05:00 - Phantoms Can't Hang \r\n1:13:43 - Arguru \r\n1:19:43 - Brazil 2nd edit \r\n1:24:50 - Pets \r\n1:32:05 - Bleed \r\n1:35:10 - Drop the Poptart/Somewhere Up Here \r\n1:52:15 - Strobe\r\n\r\nThanks to Jeka Designs for times. \r\n",
				3886);

		regex.getAlbum(
				"1. Invention 0:00 58:45 Gar\r\n2. Ungooma 8:32 58:45 Gar\r\n3. Air A.M. 16:40 58:45 Gar\r\n4. Fludentri 24:35 58:45 Gar\r\n5. Orbital Highway 31:31 58:45 Gar\r\n6. The Incredible Adventures Of A Microbe A 40:46 58:45 Gar\r\n7. Unite 48:35 58:45 Gar\r\n8. Glow Effect 51:57 58:45 Gar\r\n9. Fuse 58:45 Gar 58:45 Gar\r\n",
				3886);

		regex.getAlbum(
				"\r\nTracklisting:\r\n1. Past, Present & Future (Intro) 00:00\r\n2. Dreamcatcher 01:00\r\n3. Arrow 05:13\r\n4. Nana 09:41\r\n5. Footsteps 13:05\r\n6. All In My Mind 16:42\r\n7. Golden 19:55\r\n8. Summer 24:32\r\n9. Whispering 29:12\r\n10. Origin 33:00\r\n11. Traveller 37:33\r\n12. Swept Away 42:34\r\n13. Sunrise 46:33\r\n14. Fuji 50:40\r\n15. Rainy Day 56:16\r\n16. Gigi 1:00:11\r\n17. That Dream Again 01:04:01",
				3886);

		regex.getAlbum(
				"Spotify: http://spoti.fi/1TIfJkT\r\n\r\nTrack listings\r\n1. Resurrection 00:00\r\n2. Occult 02:24\r\n3. Black Magic 07:56\r\n4. Blood Club 12:10\r\n5. Naturom Demonto 17:08\r\n6. Demons 21:50\r\n\r\nArtwork by the talented Ariel ZB",
				3886);

		// I was VERY pleasantly surprised to see this description work flawlessly. I now realize that the timestamp process cut out the optional '*' at the end of some lines
		regex.getAlbum(
				"1. Old Friends [0:00 - 3:21]\r\n2. Stained Glass [3:22 - 5:32]\r\n3. Forecast [5:32 - 8:12]\r\n4. The Spine [8:13 - 11:26] *\r\n5. Coasting [11:27 - 14:56]\r\n6. Vanishing Point [14:57 - 18:36]\r\n7. Traces [18:37 - 20:39]\r\n8. Water Wall [20:40 - 24:36]\r\n9. Cut Apart [24:37 - 25:28]\r\n10. In Circles [25:29 - 28:55] *\r\n11. Gold Leaf [28:56 - 31:37]\r\n12. Heightmap [31:38 - 36:01]\r\n13. Dormant [36:02 - 38:52]\r\n14. Apex Beat [38:53 - 42:03]\r\n15. Gateless [42:04 - 46:23]\r\n16. Sandbox [46:24 - 49:52]\r\n17. We All Become [49:53 - 52:22] *\r\n18. Interlace [52:23 - 56:28]\r\n19. Tangent [56:29 - 59:52]\r\n20. Signals [59:53 - 1:02:47] *\r\n21. Impossible [1:02:48 - 1:07:27]\r\n22. Blank Canvas [1:07:28 - 1:08:20]\r\n23. Paper Boats [1:08:21 - 1:12:51] *",
				3886);

	}
}
