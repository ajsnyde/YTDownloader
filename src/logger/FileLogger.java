package logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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

      // This block configure the logger with handler and formatter
      if (new File(String.format("%03d", 0) + "LogFile.log").exists())
        for (int i = 0; new File(String.format("%03d", i) + "LogFile.log").exists(); ++i) {
          fh = new FileHandler(String.format("%03d", i) + "LogFile.log");
        }
      else
        fh = new FileHandler(String.format("%03d", 0) + "LogFile.log");
      logger = Logger.getLogger("MyLog");
      logger.addHandler(fh);
      SimpleFormatter formatter = new SimpleFormatter();
      fh.setFormatter(formatter);
      logger.info("Logger operational");

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
