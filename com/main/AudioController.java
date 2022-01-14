package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class AudioController 
{
	private ScheduledExecutorService cores;
	
	public AudioController()
	{
		setScheduler(Executors.newScheduledThreadPool(3, new ThreadNamers()));
	}
	
	void dumb() {}

	public ScheduledExecutorService getScheduler() { return cores; }
	public void setScheduler(ScheduledExecutorService cores) { this.cores = cores; };
}

class ThreadNamers implements ThreadFactory
{
	private int counter = 0;
	private String t_name = "Sound Core #";
	
	@Override
	public Thread newThread(Runnable r) 
	{
		// TODO Auto-generated method stub
		return new Thread(r, t_name + ++counter);
	}
	
}