package youtubeObjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.persistence.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import logger.FileLogger;

// This class pertains to ONE video/mp3/album/metadata file
@Entity
public class Metadata {

  public String url;
  public String uploader;
  public String title;
  public String description;
  public int length;
  @Id
  public File metaDataFile;

  public Metadata() {
  }

  public Metadata(File file) {
    if (!file.exists())
      FileLogger.logger().log(Level.WARNING, "Metadata file does not exist: " + file.getAbsolutePath());
    this.metaDataFile = file;
    ArrayList<String> metas = getMetaElements("uploader", "title", "duration", "description", "webpage_url");
    uploader = metas.get(0);
    title = metas.get(1);
    try {
      length = Integer.parseInt(metas.get(2));
    } catch (NumberFormatException e) {
    }
    description = metas.get(3);
    url = metas.get(4);
  }

  public String getMetaElement(String element) {
    JSONParser parser = new JSONParser();
    try {
      if (metaDataFile.exists())
        return ((JSONObject) parser.parse(readFile(metaDataFile))).get(element).toString();
      else
        return null;
    } catch (Exception e) {
      FileLogger.logger().log(Level.WARNING, "Metadata read error: " + e.getMessage());
      return null;
    }
  }

  public ArrayList<String> getMetaElements(String... elements) {
    JSONParser parser = new JSONParser();
    ArrayList<String> out = new ArrayList<String>();
    for (String element : elements)
      try {
        if (metaDataFile.exists())
          out.add(((JSONObject) parser.parse(readFile(metaDataFile))).get(element).toString());
      } catch (Exception e) {
        out.add(null);
        FileLogger.logger().log(Level.WARNING, "Metadata read error: " + e.getMessage());
      }
    return out;
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
