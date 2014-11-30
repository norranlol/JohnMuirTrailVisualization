package ru.vlsu.anttrail;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import ru.vlsu.anttrail.event.TrailEvent;
import ru.vlsu.anttrail.event.TrailListener;
import ru.vlsu.anttrail.model.Orientation;
import ru.vlsu.anttrail.model.Snapshot;
import ru.vlsu.anttrail.model.TrailData;
import ru.vlsu.ga.data.AntParameters;

public class AntTrailComponent extends JComponent implements TrailListener {
	
	private final TrailData trailData;
	
	private final static ImageIcon FOOD_IMGS = getIcon("food.png");
	private final static ImageIcon ANT_RIGHT = getIcon("antRight.png");
	private final static ImageIcon ANT_DOWN = getIcon("antDown.png");
	private final static ImageIcon ANT_LEFT = getIcon("antLeft.png");
	private final static ImageIcon ANT_UP = getIcon("antUp.png");

    private static ImageIcon getIcon(String s) {
        return new ImageIcon(AntTrailComponent.class.getResource("/" + s));
    }
    
    public AntTrailComponent(TrailData trailData)
    {
    	this.trailData = trailData;
    	trailData.addTrailDataListener(this);
    }
    
    public void paintComponent(Graphics g){
    	if (trailData == null)
    		return;
    	Graphics2D g2d = (Graphics2D) g;
    	Snapshot currentSnapshot = trailData.getSnapshot();
    	
    	final double dx = 1.0 * this.getWidth() / AntParameters.DIMENSION_N;
    	final double dy = 1.0 * this.getHeight() / AntParameters.DIMENSION_M;
    	
    	//Прорисовываем бэкграунд
    	g2d.setColor(Color.GRAY);
    	g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    	
    	int x, y;
    	
    	g2d.setColor(Color.LIGHT_GRAY);
    	
    	for (int i = 0; i < AntParameters.DIMENSION_N; i++){
          y = (int) (dy * i + dy);
          g2d.drawLine(0, y, this.getWidth(), y);
    	}
    	
    	for (int i = 0; i < AntParameters.DIMENSION_M; i++) {
    		x = (int) (dx * i + dx);
    		g2d.drawLine(x, 0, x, this.getHeight());
    	}
    	
        x = (int) (dx * currentSnapshot.getLocation().getX());
        y = (int) (dy * currentSnapshot.getLocation().getY());
        ImageIcon resultIcon = null;
        if (currentSnapshot.getOrientation().getSideTitle().equals(Orientation.RIGHT_SIDE))
        	resultIcon = ANT_RIGHT;
        else if (currentSnapshot.getOrientation().getSideTitle().equals(Orientation.LEFT_SIDE))
        	resultIcon = ANT_LEFT;
        else if (currentSnapshot.getOrientation().getSideTitle().equals(Orientation.DOWN_SIDE))
        	resultIcon = ANT_DOWN;
        else if (currentSnapshot.getOrientation().getSideTitle().equals(Orientation.UP_SIDE))
        	resultIcon = ANT_UP;
        g2d.drawImage(resultIcon.getImage(), x, y, (int) dx, (int) dy, null);
        drawScore(g2d); 	
    }
    
    private void drawScore(Graphics2D g2d) {
        int score = trailData.getSnapshot().getScore();
        int act = trailData.getAct();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font(g2d.getFont().getName(), 0, 40);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        String s = (new StringBuilder()).append(score).append(" / ").append(act).toString();
        int width = fm.stringWidth(s);
        int height = fm.getAscent();
        g2d.setColor(Color.BLUE);
        g2d.setComposite(AlphaComposite.getInstance(3, 0.5f));
        g2d.drawString(s, getWidth() / 2 - width / 2, getHeight() / 2 + height / 2);
        g2d.setComposite(AlphaComposite.getInstance(3, 1.0f));
    }

	@Override
	public void modelChanged(TrailEvent event) {
        repaint();
	}
}
