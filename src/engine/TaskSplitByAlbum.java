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

			int i = 1;

			for (Song song : album.songs) {
				System.out.println("Song being processed");
				System.out.println(song.title + " " + song.start + " " + song.end);

				// NOTE: IT SEEMS THAT SOX CANNOT CREATE DIRECTORIES. ADDING meta.title BETWEEN meta.uploader AND song.title RESULTS IN FAIL!

				// This should alleviate that issue assuming proper permissions.
				File dir = new File("Downloads/" + meta.uploader + "/" + meta.title);
				boolean success = dir.mkdir();

				Process process = new ProcessBuilder("resources/sox-14-4-2/sox.exe", "Downloads/" + meta.uploader + "/" + meta.uploader + " - " + meta.title + ".mp3",
						"Downloads/" + meta.uploader + (success ? ("/" + meta.title) : "") + "/" + song.title + ".mp3", "trim", song.start + "", song.end + "").start();
				process.waitFor();

				process = new ProcessBuilder("resources/id3tool.exe", "-c", i + "", "Downloads/" + meta.uploader + (success ? ("/" + meta.title) : "") + "/" + song.title + ".mp3").start();
				process.waitFor();
				process = new ProcessBuilder("resources/id3tool.exe", "-a", meta.title, "Downloads/" + meta.uploader + (success ? ("/" + meta.title) : "") + song.title + ".mp3").start();
				process.waitFor();
				process = new ProcessBuilder("resources/id3tool.exe", "-t", song.title + "", "Downloads/" + meta.uploader + (success ? ("/" + meta.title) : "") + "/" + song.title + ".mp3").start();
				process.waitFor();
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			decreaseParent();
		}
	}
}