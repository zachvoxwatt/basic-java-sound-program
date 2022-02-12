package main;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioNormalListener implements LineListener
{
	private SoundFile sound;
	
	public AudioNormalListener(SoundFile s)
	{
		this.sound = s;
	}
	
	@Override
	public void update(LineEvent event) 
	{
		LineEvent.Type let = event.getType();
		
		System.out.println("I am here! at Normal Lis");
		System.out.println(let.toString());
		
		if (this.sound.isPaused() || this.sound.isLooping() || this.sound.muteOnAct()) return;
		
		if (let.equals(LineEvent.Type.STOP))
		{
			System.out.println("Why u do dis");
			this.sound.closeAssets();
			this.sound.getSchedule().cancel(true);
			this.sound.getAudioController().getPanel().resetButtons();
			this.sound.getAudioController().purgeSound(this.sound);
		}
	}
}