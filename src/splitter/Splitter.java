package splitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class Splitter {
	static String exeLocation = "resources/youtube-dl.exe";

	public String getDescription(String URL) {
		try {
			Process process = new ProcessBuilder(exeLocation.toString(), "https://www.youtube.com/watch?v=m9M31l0pkC4", "--write-description", "--skip-download").start();
			process.waitFor();

			return readFile("C:\\Users\\Dreadhawk177\\Sodom _ Agent Orange [Full Album]-m9M31l0pkC4.description");
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}
}
