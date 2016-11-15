package logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger {

  private static FileLogger instance = null;
  public static Logger logger;
  FileHandler fh;
  File logFile;

  public static FileLogger getInstance() {
    if (instance == null) {
      instance = new FileLogger();
    }
    return instance;
  }

  protected FileLogger() {
    try {
      int i = 0;
      // This block configure the logger with handler and formatter
      for (i = 0; new File(String.format("%03d", i) + "LogFile.log").exists() && i < 100; ++i) {

      }
      fh = new FileHandler(String.format("%03d", i) + "LogFile.log");
      logger = Logger.getLogger("MyLog");
      logger.setLevel(Level.ALL);
      logger.addHandler(fh);
      ConsoleHandler ch = new ConsoleHandler();
      ch.setLevel(Level.FINEST);
      logger.addHandler(ch);
      logger.log(Level.FINEST, "Logger operational");

    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // functional AND elegant
  public static Logger logger() {
    return getInstance().logger;
  }
}
