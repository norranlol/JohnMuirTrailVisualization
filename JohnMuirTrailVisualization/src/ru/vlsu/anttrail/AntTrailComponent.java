package ru.vlsu.anttrail;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import ru.vlsu.anttrail.event.TrailEvent;
import ru.vlsu.anttrail.event.TrailListener;
import ru.vlsu.anttrail.model.Snapshot;
import ru.vlsu.anttrail.model.TrailData;
import vlsu.ga.data.AntParameters;
import vlsu.ga.data.MapReader;
import vlsu.ga.model.Orientation;

public class AntTrailComponent extends JComponent implements TrailListener {
	
	private final TrailData trailData;
	
	private final static ImageIcon FOOD_IMGS = getIcon("food.jpg");
	private final static ImageIcon ANT_RIGHT = getIcon("antRight.jpg");
	private final static ImageIcon ANT_DOWN = getIcon("antDown.jpg");
	private final static ImageIcon ANT_LEFT = getIcon("antLeft.jpg");
	private final static ImageIcon ANT_UP = getIcon("antUp.jpg");
	private MapReader mapReader = new MapReader();
	ArrayList<Point> foodList;

    private static ImageIcon getIcon(String s) {
        return new ImageIcon(AntTrailComponent.class.getResource("/resources/" + s));
    }
    
    public AntTrailComponent(TrailData trailData)
    {
    	this.trailData = trailData;
    	trailData.addTrailDataListener(this);
    	foodList = mapReader.getListOfFoods();
    }
    
    public void paintComponent(Graphics g){
    	if (trailData == null)
    		return;
    	Graphics2D g2d = (Graphics2D) g;
    	Snapshot currentSnapshot = trailData.getSnapshot();
    	
    	final double dx = 1.0 * this.getWidth() / AntParameters.DIMENSION_M;
    	final double dy = 1.0 * this.getHeight() / AntParameters.DIMENSION_N;
    	
    	//Прорисовываем бэкграунд
    	g2d.setColor(Color.GREEN);
    	g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    	
    	int x, y;
    	
    	g2d.setColor(Color.BLACK);
    	
    	for (int i = 0; i < AntParameters.DIMENSION_M; i++) {
    		x = (int) (dx * i + dx);
    		g2d.drawLine(x, 0, x, this.getHeight());
    	}
    	
    	for (int i = 0; i < AntParameters.DIMENSION_N; i++){
            y = (int) (dy * i + dy);
            g2d.drawLine(0, y, this.getWidth(), y);
      	}
    	
    	for (Point point : foodList){
    		x = (int) (point.getX() * dx);
    		y = (int) (point.getY() * dy);
    		g.drawImage(FOOD_IMGS.getImage(), x, y, (int) dx, (int) dy, null);
    	}
    	
        x = (int) (dx * currentSnapshot.getLocation().getX());
        y = (int) (dy * currentSnapshot.getLocation().getY());
        
        removeFoodLocation(new Point(currentSnapshot.getLocation().getX(),
        		currentSnapshot.getLocation().getY()));
        
        ImageIcon resultIcon = null;
        if (currentSnapshot.getOrientation() == Orientation.EAST)
        	resultIcon = ANT_RIGHT;
        else if (currentSnapshot.getOrientation() == Orientation.WEST)
        	resultIcon = ANT_LEFT;
        else if (currentSnapshot.getOrientation() == Orientation.SOUTH)
        	resultIcon = ANT_DOWN;
        else if (currentSnapshot.getOrientation() == Orientation.NORTH)
        	resultIcon = ANT_UP;
        g2d.drawImage(resultIcon.getImage(), x, y, (int) dx, (int) dy, null);	
    }

	@Override
	public void modelChanged(TrailEvent event) {
        repaint();
	}
	
	public void removeFoodLocation(final Point food){
		foodList.remove(food);
	}
}
