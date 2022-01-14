package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundFile
{
	private Clip clip;
	private AudioInputStream ais;
	
	public SoundFile(URL u)
	{
		try
		{
			this.clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(u);
		}
		
		catch (Exception e) { e.printStackTrace(); }
	}
}