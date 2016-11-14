package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// This class pertains to ONE video/mp3/album/metadata file

public class Metadata {

	public String url;
	public String uploader;
	public String title;
	public String description;
	public int length;
	public File metaDataFile;

	public Metadata(File file) {
		this.metaDataFile = file;
		uploader = getMetaElement("uploader");
		title = getMetaElement("title");
		length = Integer.parseInt(getMetaElement("duration"));
		description = getMetaElement("description");
		url = getMetaElement("webpage_url");
	}

	public String getMetaElement(String element) {
		JSONParser parser = new JSONParser();
		ArrayList<String> out = new ArrayList<String>();
		try {
			JSONObject jsonObject;
			if (metaDataFile.exists())
				return ((JSONObject) parser.parse(readFile(metaDataFile))).get(element).toString();
			else
				return null;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private String readFile(File file) throws IOException {
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

	// Considering sanitizing filenames...
	String sanitize(String in) {
		return in.replaceAll("[^a-zA-Z0-9\\._]+", "_");
	}
}
