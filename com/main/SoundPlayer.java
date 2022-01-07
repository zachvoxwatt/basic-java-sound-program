package main;

import java.awt.EventQueue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer 
{
	private Clip cl;
	
	public SoundPlayer()
	{
		try
		{
			cl = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(this.getClass().getResource("/audio/test.wav"));
			cl.open(ais);
			cl.start();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) { SoundPlayer s = new SoundPlayer(); }
}