package ui;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.BorderLayout;

import main.JSONDataParser;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 409023406294929767L;

	public Frame()
	{
		super();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("Advanced Sound Engine");
			setLayout(new BorderLayout());
			
			add(new Panel(new JSONDataParser()));
			
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
	}
	
	public static void main(String[] args) { EventQueue.invokeLater(Frame::new); }
}