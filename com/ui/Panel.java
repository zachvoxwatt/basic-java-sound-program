package ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import java.util.Map;
import java.util.Objects;
import java.util.List;

import main.AudioController;
import main.JSONDataParser;
import main.SoundFile;

public class Panel extends JPanel
{
	private static final long serialVersionUID = 3350710760250147620L;
	private JSONDataParser jdp;
	private AudioController audctrl;
	private SoundFile activeSound;
	private JButton play, paus, stop, res;
	
	public Panel(JSONDataParser j, AudioController aud)
	{
		super();
			this.activeSound = null;
			this.jdp = j;
			this.audctrl = aud;
			setPreferredSize(new Dimension(350, 350));
			setLayout(new FlowLayout());
			setBackground(new Color(0, 0, 0, 255));
			
		JComboBox<String> jcb = new JComboBox<>();
			jcb.setPreferredSize(new Dimension(345, 30));
			jcb.addItem("None");
			jcb.setSelectedIndex(0);
			
			jcb.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (Objects.isNull(activeSound)) return;
						else if (activeSound.isPlaying())
							audctrl.stop(activeSound);
					}
				}
			);
			
			for (Map.Entry<String, List<String>> map: jdp.getDirectoryMap().entrySet()) 
				jcb.addItem(map.getKey());
				
			add(jcb);
			
		play = new JButton("Play");
			play.setPreferredSize(new Dimension(165, 150));
			play.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (jcb.getSelectedIndex() == 0) return;
				//		if (!activeSound.equals(null)) aud.stop(activeSound);
						activeSound = audctrl.play(jcb.getSelectedItem().toString());
						
						play.setEnabled(false);
						stop.setEnabled(true);
						paus.setEnabled(true);
					}
				}
			);
			add(play);
			
		stop = new JButton("Stop");
			stop.setPreferredSize(new Dimension(165, 150));
			stop.setEnabled(false);
			stop.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (jcb.getSelectedIndex() == 0) return;
						audctrl.stop(activeSound);
						
						play.setEnabled(true);
						res.setEnabled(false);
						paus.setEnabled(false);
						stop.setEnabled(false);
					}
				}
			);
			add(stop);
			
		paus = new JButton("Pause");
			paus.setPreferredSize(new Dimension(165, 150));
			paus.setEnabled(false);
			paus.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (jcb.getSelectedIndex() == 0) return;
						
						audctrl.pause(activeSound);
						
						paus.setEnabled(false);
						play.setEnabled(true);
						stop.setEnabled(true);
						res.setEnabled(true);
					}
				}
			);
			add(paus);
			
		res = new JButton("Resume");
			res.setPreferredSize(new Dimension(165, 150));
			res.setEnabled(false);
			res.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (jcb.getSelectedIndex() == 0) return;
						
						audctrl.resume(activeSound);
						
						res.setEnabled(false);
						play.setEnabled(false);
						stop.setEnabled(true);
						paus.setEnabled(true);
					}
				}
			);
			add(res);
	}
	
	public AudioController getAudioController() { return this.audctrl; }
}