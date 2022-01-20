package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import java.util.List;
import java.util.Map;

public class AudioController 
{
	private ScheduledExecutorService cores;
	private Map<String, List<String>> audioMap;
	
	public AudioController(Map<String, List<String>> dirmap)
	{
		this.audioMap = dirmap;
		setScheduler(Executors.newScheduledThreadPool(3, new ThreadNamers()));
	}
	
	public void play(String key)
	{
		this.audioMap.get((String) key).size();
	}
	
	public void stop()
	{
		
	}

	public void pause()
	{
		
	}
	
	public void resume()
	{
		
	}
	
	void dumb() {}

	public ScheduledExecutorService getScheduler() { return cores; }
	public Map<String, List<String>> getAudioMap() { return this.audioMap; }
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