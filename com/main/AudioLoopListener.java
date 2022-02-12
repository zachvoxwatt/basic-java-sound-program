package main;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioLoopListener implements LineListener
{
	private SoundFile sound;
	
	public AudioLoopListener(SoundFile s)
	{
		this.sound = s;
	}
	
	@Override
	public void update(LineEvent event) 
	{
		LineEvent.Type let = event.getType();
		
		System.out.println("I am here! at Loop Lis");
		System.out.println(let.toString());
		
		if (let.equals(LineEvent.Type.STOP) && this.sound.isLooping())
		{
			this.sound.closeAssets();
			this.sound.reopenLine();
			this.sound.replay();
		}
	}
}
