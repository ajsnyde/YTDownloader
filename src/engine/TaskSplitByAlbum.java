package engine;

import java.io.File;
import java.util.HashMap;

import splitter.Album;
import splitter.Song;
import tables.DownloadTableModel;

public class TaskSplitByAlbum extends Task {

	public TaskSplitByAlbum(HashMap<String, Object> parameters) {
		this.parameters = parameters;
		if (parameters.get("model") != null)
			((DownloadTableModel) parameters.get("model")).addTask(this);
	}

	// requires album with an mp3 that is stored in the directory schema below as well as metadata
	// Splits into song mp3s
	@Override
	public void run() {
		increaseParent();
		try {
			Album album = (Album) parameters.get("album");
			System.out.println(((File) parameters.get("audioLocation")).getAbsolutePath());
			// TODO: updateProgress
			System.out.println("Album being processed");
			System.out.println(album.albumName);
			System.out.println(album.length);
			Metadata meta = (Metadata) parameters.get("metadata");

			for (Song song : album.songs) {
				System.out.println("Song being processed");
				System.out.println(song.title + " " + song.start + " " + song.end);

				Process process = new ProcessBuilder("resources/sox-14-4-2/sox.exe",
						"Downloads/" + meta.uploader + "/" + meta.uploader + " - " + meta.title + ".mp3",
						"Downloads/" + meta.uploader + "/" + song.title + ".mp3", "trim", song.start + "",
						song.end + "").start();
				process.waitFor();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			decreaseParent();
		}
	}
}