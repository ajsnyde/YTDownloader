package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Metadata {

	public String url;
	public String name;
	public String size;
	public File file;

	public Metadata(File file) {
		this.file = file;
		// TODO: create more variables and read file to set them
	}

	public String getMetaElement(String element) {
		JSONParser parser = new JSONParser();
		ArrayList<String> out = new ArrayList<String>();
		try {
			JSONObject jsonObject;
			if (file.exists())
				return ((JSONObject) parser.parse(readFile(file))).get(element).toString();
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

}
