package db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import logger.FileLogger;
import youtubeObjects.Album;
import youtubeObjects.Metadata;

// If I were going for best practice, I'd have a parent class, allowing one to change the DB platform without a bunch of code changes in unrelated classes. But given the scope of the project, I'm not too worried.

public class DerbyDB {
  EntityManagerFactory factory;
  static EntityManager em;
  Connection c;
  Query q;

  public static void main(String[] args) {
    new DerbyDB();
  }

  DerbyDB() {
    try {
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("derby-eclipselink");
      em = factory.createEntityManager();
    } catch (Exception e) {
      FileLogger.logger().log(Level.SEVERE, e.getMessage());
    }
  }

  public void addMeta(File file) {
    try {
      FileLogger.logger().log(Level.FINER, "Adding meta from file: " + file);
      persist(new Metadata(file));
    } catch (Exception e) {
      FileLogger.logger().log(Level.WARNING, "Adding meta from file failed: " + file);
    }
  }

  public static void persist(Object obj) {
    try {
      if (em == null)
        new DerbyDB();

      em.getTransaction().begin();
      em.persist(obj);
      em.getTransaction().commit();
    } catch (Exception e) {
      FileLogger.logger().log(Level.FINEST, "Persisting object failed: " + e.toString());
    }
  }

  public void numAlbums() {
    q = em.createQuery("SELECT u FROM Album u");
    List<Album> albums = q.getResultList();
    System.out.println("first album: " + albums.get(0).albumName);
    System.out.println("Songs of first album: " + albums.get(0).songs.size());
  }

  public void initMeta() {
    try {
      Files.find(Paths.get("Downloads"), 999, (p, bfa) -> bfa.isRegularFile() && p.toFile().getAbsolutePath().endsWith("json")).forEach(path -> addMeta(path.toFile()));
    } catch (IOException e) {
      FileLogger.logger().log(Level.WARNING, e.toString());
    }
  }

  public void close() {
    em.close();
    factory.close();
  }
}
