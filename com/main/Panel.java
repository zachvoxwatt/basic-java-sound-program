package main;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import java.util.Map;
import java.util.List;

public class Panel extends JPanel
{
	private static final long serialVersionUID = 3350710760250147620L;
	private JSONDataParser jdp;
	
	public Panel(JSONDataParser j)
	{
		super();
			this.jdp = j;
			setPreferredSize(new Dimension(350, 350));
			setLayout(new FlowLayout());
			setBackground(new Color(0, 0, 0, 255));
			
		JComboBox<String> jcb = new JComboBox<>();
			jcb.setPreferredSize(new Dimension(345, 30));
			
			for (Map.Entry<String, List<String>> map: jdp.getDirectoryMap().entrySet()) 
				jcb.addItem(map.getKey());
			
			jcb.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.out.printf("Item '%s' is selected at index %d\n",jcb.getSelectedItem(), jcb.getSelectedIndex());
					}
				}
			);
			
			jcb.setSelectedIndex(1);
			add(jcb);
			
		JButton play = new JButton("Play");
			play.setPreferredSize(new Dimension(165, 150));
			add(play);
			
		JButton stop = new JButton("Stop");
			stop.setPreferredSize(new Dimension(165, 150));
			add(stop);
			
		JButton paus = new JButton("Pause");
			paus.setPreferredSize(new Dimension(165, 150));
			add(paus);
			
		JButton res = new JButton("Resume");
			res.setPreferredSize(new Dimension(165, 150));
			add(res);
	}
}