package ru.vlsu.anttrail;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ru.vlsu.anttrail.model.TrailData;
import vlsu.ga.data.AntParameters;

public class RunPanel extends JPanel {

    private final TrailData trailData;
	private final JButton nextButton;
	private final JButton runButton;
	private final JButton pauseButton;
	private final JLabel labelOfEaten;
	private Runnable run;
	private final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> task;
    
	private static ImageIcon getIcon(String s) {
        return new ImageIcon(RunPanel.class.getResource("/resources/" + s));
    }
	
	private void setEnabledNextButton(){
		final boolean canNext = !trailData.isEnd();
		nextButton.setEnabled(canNext);
	}
	
	private void play(){
		run = new Runnable() {
			@Override
			public void run() {
				if (!trailData.isEnd())
					trailData.nextAct();
				setStringOfEatenToLabel();
			}
		};
		start();
	}
	
	private void pause(){
		task.cancel(true);
	}
	
//    private void pause() {
//        played = false;
//        task.cancel(true);
//        setEnabled();
//    }
	
    private void start() {
        task = exec.scheduleWithFixedDelay(run, 200, 200, TimeUnit.MILLISECONDS);
    }
	
	public RunPanel(final TrailData trailData){
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout());
		this.trailData = trailData;

		nextButton = new JButton(getIcon("player_next.png"));
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trailData.nextAct();
				setStringOfEatenToLabel();
				setEnabledNextButton();
			}
		});
		panel.add(nextButton);
		
		runButton = new JButton(getIcon("run_icon.png"));
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				play();
				runButton.setEnabled(false);
				nextButton.setEnabled(false);
				pauseButton.setEnabled(true);
			}
		});
		panel.add(runButton);
		
		pauseButton = new JButton(getIcon("pause_icon.png"));
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runButton.setEnabled(true);
				nextButton.setEnabled(true);
				pauseButton.setEnabled(false);
				pause();
			}
		});
		pauseButton.setEnabled(false);
		panel.add(pauseButton);
		labelOfEaten = new JLabel("0 / 0");
		labelOfEaten.setSize(getMaximumSize());
		labelOfEaten.setFont(new Font("Garamond", Font.BOLD , 30));
		panel.add(labelOfEaten);
				
	    setLayout(new BorderLayout());
	    add(panel);
	}	
	
	private void setStringOfEatenToLabel(){
		int score = trailData.getSnapshot().getScore();
		int act = trailData.getAct();
		String str = (new StringBuilder()).append(score).append(" / ").append(act).toString();
		labelOfEaten.setText(str);
	}
}
