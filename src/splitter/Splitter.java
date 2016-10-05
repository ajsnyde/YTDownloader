package splitter;

import java.io.File;
import java.nio.file.Files;

public class Splitter {
	static File exeLocation = new File("C:\\Users\\Dreadhawk177\\workspace\\YTDownloader\\src\\youtube-dl.exe");
	public String split(String URL) {
		try {
			Process process = new ProcessBuilder(exeLocation.toString(),"https://www.youtube.com/watch?v=m9M31l0pkC4", "--write-description", "--skip-download").start();
			return String.valueOf(Files.readAllBytes((new File("C:\\Users\\Dreadhawk177\\Sodom _ Agent Orange [Full Album]-m9M31l0pkC4.description")).toPath()));

		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
