package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

public class SoundPlayer 
{
	private Clip cl;
	private ScheduledExecutorService cores;
	
	public SoundPlayer()
	{
		cores = Executors.newScheduledThreadPool(3, new ThreadNamers());
		try
		{
			cl = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(this.getClass().getResource("/audio/test.wav"));
			cl.open(ais);
			
			Runnable R = new Runnable()
					{
						public void run()
						{
							cl.start();
						}
					};
					
			ScheduledFuture<?> sf = cores.scheduleWithFixedDelay(R, 1, 1, TimeUnit.SECONDS);

			Timer test = new Timer(5000, new ActionListener()
			{
				boolean stopped = false;
				public void actionPerformed(ActionEvent e)
				{
					if (!stopped)
					{
						sf.cancel(true);
						cl.stop();
						cl.close();
						stopped = true;
					}
				}
				
			});
			test.start();
		}
		
		catch (Exception e) { e.printStackTrace(); }
	}
	
	void dumb() {};
	
	public static void main(String[] args) { SoundPlayer s = new SoundPlayer(); s.dumb(); }
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