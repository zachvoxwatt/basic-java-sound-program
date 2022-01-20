package ui;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.BorderLayout;

import main.AudioController;
import main.JSONDataParser;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 409023406294929767L;
	private AudioController aud;
	private JSONDataParser jdp;
	
	public Frame()
	{
		super();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("Advanced Sound Engine");
			setLayout(new BorderLayout());
		
		jdp = new JSONDataParser();
		aud = new AudioController(jdp.getDirectoryMap());
			add(new Panel(jdp, aud));
			
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
	}
	
	public static void main(String[] args) { EventQueue.invokeLater(Frame::new); }
}