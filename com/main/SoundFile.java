package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundFile
{
	private URL url;
	private String audioType = "";
	private Clip clip;
	private AudioInputStream ais;
	private Runnable player, timer;	
	
	public SoundFile(URL u, String type)
	{
		this.url = u;
		this.audioType = type;
		try
		{
			this.clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(u);
		}
	
		catch (Exception e) { e.printStackTrace(); }
		
		player = new Runnable()
		{
			public void run()
			{
				if (!clip.isOpen())
				{
					try
					{
						clip.open(ais);
						clip.start();
					}
					catch (Exception e) { e.printStackTrace(); }
				}
				
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
	}
	
	public String getAudioType() { return this.audioType; }
	public Runnable getPlayerJob() { return this.player; }
	public Runnable getTimerJob() { return this.timer; }
	public URL getSoundURL() { return url; }
}