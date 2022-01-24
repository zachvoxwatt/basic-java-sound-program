package main;

import java.net.URL;
import java.util.concurrent.ScheduledFuture;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SoundFile implements LineListener
{
	private URL url;
	private String audioType = "";
	private Clip clip;
	private AudioInputStream ais;
	private AudioController aud;
	private Runnable player, timer;	
	private ScheduledFuture<?> scheF;
	
	public SoundFile(URL u, String type, AudioController ad)
	{
		this.url = u;
		this.audioType = type;
		this.aud = ad;
		
		try
		{
			this.clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(this.url);
			clip.open(ais);
		}
	
		catch (Exception e) { e.printStackTrace(); }
		
		player = new Runnable()
		{
			public void run()
			{
				if (!clip.isOpen()) clip.start();
				else
				{
					clip.setFramePosition(0);
					clip.start();
				}
			}
		};
		
		timer = new Runnable()
		{
			public void run()
			{
				
			}
		};
		
		this.clip.addLineListener(this);
	}
	
	private void closeAssets()
	{
		try
		{
			ais.close();
			clip.close();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	

	@Override
	public void update(LineEvent event) 
	{
		LineEvent.Type le = event.getType();
		
		if (le.equals(LineEvent.Type.STOP))
		{
			closeAssets();
			this.scheF.cancel(true);
		}
	}
	
	public void assignSchedule(ScheduledFuture<?> sf) { this.scheF = sf; }
	
	public AudioController getAudioController() { return this.aud; }
	public ScheduledFuture<?> getSchedule() { return this.scheF; }
	public String getAudioType() { return this.audioType; }
	public Runnable getPlayerJob() { return this.player; }
	public Runnable getTimerJob() { return this.timer; }
	public URL getSoundURL() { return url; }
}