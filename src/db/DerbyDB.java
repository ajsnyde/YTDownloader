package db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import youtubeObjects.Album;
import youtubeObjects.Metadata;
import youtubeObjects.Song;

public class DerbyDB {

  public File dbLocation = new File("db/data.db");
  public String connectionString = "jdbc:derby:" + dbLocation.getAbsolutePath();
  EntityManager em;
  Connection c;

  public static void main(String[] args) {
    new DerbyDB();
  }

  DerbyDB() {
    testEntity();
  }

  public void testEntity() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("derby-eclipselink");
    em = factory.createEntityManager();

    // Read the existing entries and write to console
    Query q = em.createQuery("SELECT u FROM Metadata u");
    List<Metadata> userList = q.getResultList();
    System.out.println("Size: " + userList.size());

    try {
      Files.find(Paths.get("Downloads"), 999, (p, bfa) -> bfa.isRegularFile() && p.toFile().getAbsolutePath().endsWith("json")).forEach(path -> addMeta(path.toFile()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    q = em.createQuery("SELECT u FROM Metadata u");
    userList = q.getResultList();
    System.out.println("Size: " + userList.size());

    addAlbum();

    q = em.createQuery("SELECT u FROM Album u");
    List<Album> albums = q.getResultList();
    System.out.println("first album: " + albums.get(0).albumName);
    System.out.println("Songs of first album: " + albums.get(0).songs.size());

    em.close();
    factory.close();
  }

  public void addMeta(File file) {
    try {
      em.getTransaction().begin();
      Metadata user = new Metadata(file);
      em.persist(user);
      em.getTransaction().commit();
    } catch (Exception e) {

    }
  }

  public void addAlbum() {
    try {
      em.getTransaction().begin();
      Album album = new Album();
      album.albumName = "TEST";
      Song song = new Song();
      song.album = album;
      song.title = "TESTSONG";
      album.songs.add(song);
      song = new Song();
      song.album = album;
      song.title = "TESTSONG2";
      album.songs.add(song);
      em.persist(album);
      em.getTransaction().commit();
    } catch (Exception e) {

    }
    Query q = em.createQuery("SELECT u FROM Album u");
    List<Album> albums = q.getResultList();
    System.out.println("first album: " + albums.get(0).albumName);
    System.out.println("Songs of first album: " + albums.get(0).songs.size());
  }
}
