package url2list;

public class Singleton {
   private static StatsGUI instance = null;
   protected Singleton() {
      // Exists only to defeat instantiation.
   }
   public static StatsGUI getStats() {
      if(instance == null || !instance.isShowing()) {
         instance = new StatsGUI();
      }
      return instance; 
   }
}