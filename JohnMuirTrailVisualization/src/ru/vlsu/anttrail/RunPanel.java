package ru.vlsu.anttrail;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ru.vlsu.anttrail.model.TrailData;

public class RunPanel extends JPanel {

    private final TrailData trailData;
	private final JButton nextButton;
	private Runnable run;
    
	private static ImageIcon getIcon(String s) {
        return new ImageIcon(RunPanel.class.getResource("/" + s));
    }
	
	private void setEnabled(){
		final boolean canNext = !trailData.isEnd();
		nextButton.setEnabled(canNext);
	}
	
	public RunPanel(final TrailData trailData){
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout());
		this.trailData = trailData;

		nextButton = new JButton(getIcon("player_next.png"));
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trailData.nextAct();
				setEnabled();
			}
		});
		panel.add(nextButton);
		
	    setEnabled();
	    setLayout(new BorderLayout());
	    add(panel);
	}	
}
