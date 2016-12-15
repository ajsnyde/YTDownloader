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

public class DerbyDB {
  EntityManagerFactory factory;
  EntityManager em;
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
      FileLogger.logger().log(Level.FINER, e.getMessage());
    }

    try {
      Files.find(Paths.get("Downloads"), 999, (p, bfa) -> bfa.isRegularFile() && p.toFile().getAbsolutePath().endsWith("json")).forEach(path -> addMeta(path.toFile()));
    } catch (IOException e) {
      FileLogger.logger().log(Level.FINER, e.getMessage());
    }
  }

  public void addMeta(File file) {
    try {
      FileLogger.logger().log(Level.FINER, "Adding meta: " + file);
      em.getTransaction().begin();
      Metadata user = new Metadata(file);
      em.persist(user);
      em.getTransaction().commit();
    } catch (Exception e) {

    }
  }

  public void persist(Object obj) {
    em.getTransaction().begin();
    em.persist(obj);
    em.getTransaction().commit();
  }

  public void numAlbums() {
    q = em.createQuery("SELECT u FROM Album u");
    List<Album> albums = q.getResultList();
    System.out.println("first album: " + albums.get(0).albumName);
    System.out.println("Songs of first album: " + albums.get(0).songs.size());
  }

  public void close() {
    em.close();
    factory.close();
  }
}
