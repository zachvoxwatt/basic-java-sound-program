package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class AudioController 
{
	private ScheduledExecutorService schedulerCore;
	private Map<String, List<String>> audioMap;
	
	public AudioController(Map<String, List<String>> dirmap)
	{
		this.audioMap = dirmap;
		setScheduler(Executors.newScheduledThreadPool(3, new ThreadNamers()));
	}
	
	public void testKey(String key)
	{
		String replyString = "Key '" + key + "' has ";
		
		replyString += (this.audioMap.get((String) key).size() <= 2) ? "only 1 variant" : this.audioMap.get((String) key).size() - 1 + " variants";
		System.out.println(replyString);
	}
	
	public SoundFile play(String key)
	{
		String type = this.audioMap.get(key).get(0);
		String url = this.audioMap.get(key).get(1);
		URL u = this.getClass().getResource(url);
		
		SoundFile f = new SoundFile(u, type, this);
			f.assignSchedule(this.schedulerCore.schedule(f.getPlayerJob(), 10, TimeUnit.MICROSECONDS));
		return f;
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

	public ScheduledExecutorService getScheduler() { return schedulerCore; }
	public Map<String, List<String>> getAudioMap() { return this.audioMap; }
	public void setScheduler(ScheduledExecutorService cores) { this.schedulerCore = cores; };
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