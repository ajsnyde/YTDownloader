package downloader;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Engine {
	
	ArrayList<Thread> threads = new ArrayList<Thread>();
	
	Engine(){
	}
	
	public static BufferedReader exe(){
		return exe("https://www.youtube.com/watch?v=4R-JGw3VTuY");
	}
	
	// create a new thread that launches and monitors (or more accurately, returns the buffer of) the yt-downloader.exe
	public static BufferedReader exe(String command){
		Execute execute = new Execute(command);
		Thread thread = new Thread(execute, "test");
		thread.start();
        synchronized(execute){
            try{
                execute.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
		return execute.input;
	}
}
