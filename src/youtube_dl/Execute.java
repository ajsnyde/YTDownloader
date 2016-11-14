package youtube_dl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Execute implements Runnable {
  String arguments = "";
  public BufferedReader input;
  Process p;

  Execute(String arguments) {
    this.arguments = arguments;
  }

  @Override
  public void run() {
    System.out.println("Running Executable");
    try {

      synchronized (this) {
        p = Runtime.getRuntime().exec(Engine.exeLocation + " " + arguments);
        InputStreamReader re = new InputStreamReader(p.getInputStream());
        input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        notify();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
