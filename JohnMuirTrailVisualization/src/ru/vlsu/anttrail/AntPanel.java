package ru.vlsu.anttrail;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ru.vlsu.anttrail.model.TrailData;

public class AntPanel extends JPanel {
	
	public AntPanel(TrailData trailData){
		super(new BorderLayout());
		add(new AntTrailComponent(trailData));
		add(new RunPanel(trailData), BorderLayout.SOUTH);
		setBorder(new EmptyBorder(3, 3, 3, 3));
	}
}
