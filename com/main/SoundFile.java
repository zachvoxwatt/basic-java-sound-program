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
	private int pauseMark = 0;
	private boolean playing = false, paused = false;
	
	private URL url;
	private String audioType = "";
	private Clip clip;
	private AudioInputStream ais;
	private AudioController aud;
	private Runnable timer;
	private ScheduledFuture<?> scheF;
	
	public SoundFile(URL u, String type, AudioController ad, boolean preOpenLine)
	{
		this.url = u;
		this.audioType = type;
		this.aud = ad;
		
		try
		{
			this.clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(this.url);
		}
	
		catch (Exception e) { e.printStackTrace(); }
		
		timer = new Runnable()
			{
				public void run()
				{
					for (int i = 0; i < 15; i++) System.out.println();
					System.out.println("Clip length: " + clip.getFrameLength());
					System.out.println("Clip curpos: " + clip.getFramePosition());
				}
		};
		
		this.clip.addLineListener(this);
		
		if (preOpenLine) try { clip.open(ais); } catch (Exception e) { e.printStackTrace(); }
	}
	
	public void play()
	{
		if (!playing) 
		{
			clip.start();
			playing = true;
			return;
		}
		
		else
		{
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void resume()
	{
		if (playing && paused)
		{
			clip.setFramePosition(pauseMark);
			clip.start();
			paused = false;
		}
	}
	
	public void pause() 
	{
		this.paused = true;
		this.clip.stop();
		this.pauseMark = this.clip.getFramePosition();
	}
	
	public void stop() { this.clip.stop(); this.playing = false; }
	
	public void closeAssets()
	{
		try
		{
			ais.close();
			clip.close();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	void reopenLine()
	{
		try 
		{ 
			this.clip = AudioSystem.getClip();
			this.clip.open(this.ais); 
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	

	@Override
	public void update(LineEvent event) 
	{
		LineEvent.Type le = event.getType();
		
		if (paused) return;
		
		if (le.equals(LineEvent.Type.STOP))
		{
			closeAssets();
			this.scheF.cancel(true);
			aud.purgeSound(this);
			aud.getPanel().resetButtons();
		}
	}
	
	public void assignSchedule(ScheduledFuture<?> sf) { this.scheF = sf; }
	
	public long getPauseMark() { return this.pauseMark; }
	public boolean isPaused() { return this.paused; }
	public boolean isPlaying() { return this.playing; }
	
	public AudioController getAudioController() { return this.aud; }
	public ScheduledFuture<?> getSchedule() { return this.scheF; }
	public String getAudioType() { return this.audioType; }
	public Runnable getTimerJob() { return this.timer; }
	public URL getSoundURL() { return url; }
	public SoundFile clone() { return new SoundFile(this.url, this.audioType, this.aud, true); } 
}