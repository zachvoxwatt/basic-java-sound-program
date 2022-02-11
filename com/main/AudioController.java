package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import ui.Panel;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AudioController 
{
	private Random rand;
	private Panel pane;
	private ScheduledExecutorService schedulerCore;
	private Map<String, List<String>> audioKeyURLMap;
	private Map<String, List<SoundFile>> sfxMap;
	private List<SoundFile> activeQueue;
	
	public AudioController(Map<String, List<String>> dirmap)
	{
		this.rand = new Random();
		this.audioKeyURLMap = dirmap;
		this.sfxMap = new HashMap<>();
		this.activeQueue = new LinkedList<>();
		
		//initialize the all of the sounds
		for (Map.Entry<String, List<String>> itor: this.audioKeyURLMap.entrySet())
		{
			String key = itor.getKey();
			List<SoundFile> sfxs = new ArrayList<>();
			
			if (itor.getValue().size() <= 2)
			{
				String type = itor.getValue().get(0);
				String u = itor.getValue().get(1);
				URL url = this.getClass().getResource(u);
				
				SoundFile f = new SoundFile(url, type, this, false);
				sfxs.add(f);
			}
			
			else
			{
				String type = itor.getValue().get(0);
				Iterator<String> itr = itor.getValue().iterator();
				int i = 0;
				
				while (itr.hasNext())
				{
					if (i == 0)
					{
						itr.next();
						i++;
						continue;
					}
					
					String u = itr.next();
					URL url = this.getClass().getResource(u);
					
					SoundFile f = new SoundFile(url, type, this, false);
					sfxs.add(f);
					i++;
				}
			}
			
			this.sfxMap.put(key, sfxs);
		}
		
		setScheduler(Executors.newScheduledThreadPool(3, new ThreadNamers()));
	}
	
	public SoundFile play(String key)
	{
		int sizeof = this.sfxMap.get(key).size();
		SoundFile f = this.sfxMap.get(key).get(rand.nextInt(sizeof)).clone();
			f.play();
			f.assignSchedule(this.schedulerCore.scheduleWithFixedDelay(f.getTimerJob(), 0, 1, TimeUnit.SECONDS));
			this.activeQueue.add(f);
		return f;
	}
	
	public void stop(SoundFile sf)
	{
		sf.getSchedule().cancel(true);
		sf.stop();
		this.activeQueue.remove(sf);
		
		sf = null;
		System.gc();
	}
	
	public void purgeSound(SoundFile sf)
	{
		this.activeQueue.remove(sf);
		sf = null;
		System.gc();
	}
	
	public void toggleMute(SoundFile sf) { sf.toggleMute(); }
	public void toggleLoop(SoundFile sf) { sf.toggleLoop(); }
	public void pause(SoundFile sf) { sf.pause(); }
	public void resume(SoundFile sf) { sf.resume(); }
	
	void dumb() {}

	public void assignPanel(Panel p) { this.pane = p; }
	
	public Panel getPanel() { return this.pane; }
	public ScheduledExecutorService getScheduler() { return schedulerCore; }
	public Map<String, List<String>> getAudioMap() { return this.audioKeyURLMap; }
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