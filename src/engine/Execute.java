package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Level;

import logger.FileLogger;

public class Execute implements Runnable {
  HashMap<String, Object> parameters;
  public BufferedReader input;
  Process p;

  Execute(HashMap<String, Object> parameters) {
    this.parameters = parameters;
  }

  @Override
  public void run() {
    FileLogger.logger().log(Level.FINEST, "Running Executable");
    try {
      synchronized (this) {
        p = Runtime.getRuntime().exec(parameters.get("ExeLocation") + " " + parameters.get("ExeArguments"));
        input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        notify();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void kill() {
    System.out.println("DESTROYING EXE");
    p.destroyForcibly();
    try {
      input.close();
      input = null;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
