package db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.sqlite.SQLiteConfig;

import youtubeObjects.Metadata;

public class SqliteDB {

  public File dbLocation = new File("db/data.db");
  public String connectionString = "jdbc:sqlite:" + dbLocation.getAbsolutePath();
  EntityManager em;
  Connection c;

  public static void main(String[] args) {
    SQLiteConfig config = new SQLiteConfig();
  }

  SqliteDB() {
    // connect();
    testEntity();
  }

  public boolean connect() {
    try {
      Class.forName("org.sqlite.JDBC");
      new File("db").mkdir();

      c = DriverManager.getConnection(connectionString);
      logger.FileLogger.logger().log(Level.FINE, "Opened database successfully: " + dbLocation.getAbsolutePath());
      return true;
    } catch (Exception e) {
      logger.FileLogger.logger().log(Level.SEVERE, "Could not open database: " + connectionString + "\n" + e.getClass().getName() + ": " + e.getMessage());
      return false;
    }
  }

  public void testEntity() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("org.sqlite.SQLiteConnection");
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

    em.close();
    factory.close();
  }

  public void addMeta(File file) {
    em.getTransaction().begin();
    Metadata user = new Metadata(file);
    em.persist(user);
    em.getTransaction().commit();
  }
}
