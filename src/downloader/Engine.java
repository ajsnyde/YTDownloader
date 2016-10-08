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
	
	public static BufferedReader exe(String command){
		Execute execute = new Execute(command);
		System.out.println("Creating new thread!");
		Thread thread = new Thread(execute, "test");
		thread.start();
		
        synchronized(execute){
            try{
                System.out.println("Waiting for b to notify...");
                execute.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Notified!");
        }

		return execute.input;
	}
}
