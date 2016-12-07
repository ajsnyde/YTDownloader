package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import youtubeObjects.Metadata;

public class SqliteDB {

  public File dbLocation = new File("db/data.db");
  public String connectionString = "jdbc:sqlite:" + dbLocation.getAbsolutePath();
  Connection c;

  public static void main(String[] args) {
    SqliteDB db = new SqliteDB();
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

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("Metadata");
    EntityManager em = factory.createEntityManager();

    // Read the existing entries and write to console
    Query q = em.createQuery("SELECT u FROM Metadata u");
    List<Metadata> userList = q.getResultList();
    System.out.println("Size: " + userList.size());

    // Create new user
    em.getTransaction().begin();
    Metadata user = new Metadata(new File(""));
    em.persist(user);
    em.getTransaction().commit();

    em.close();
  }
}
