package ru.vlsu.anttrail.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.event.EventListenerList;

import ru.vlsu.anttrail.event.TrailEvent;
import ru.vlsu.anttrail.event.TrailListener;

public class TrailData {

	private int act;
	private final ArrayList<Snapshot> snapshots = new ArrayList<Snapshot>();
	private final EventListenerList listOfListeners = new EventListenerList();

	public TrailData(){
		Scanner scanner = null;
		List<String> listOfRows = new ArrayList<String>();
		try {
			scanner = new Scanner(new File("snapshots.txt"));
			while (scanner.hasNextLine())
				listOfRows.add(scanner.nextLine());
		} catch (Exception e){
			e.printStackTrace();
		}
		Snapshot snapshot = new Snapshot();
		int xLocation = -1;
		int yLocation = -1;
		int dxOrientation = -1;
		int dyOrientation = -1;
	    for (String str : listOfRows){
	    	if (str.startsWith("XLocation")){
	    		xLocation = parseStringByEqually(str);
	    	} else if (str.startsWith("YLocation")){
		    	yLocation = parseStringByEqually(str);
	    	} else if (str.startsWith("DXOrientation")){
		    	dxOrientation = parseStringByEqually(str);
	    	} else if (str.startsWith("DYOrientation")){
		    	dyOrientation = parseStringByEqually(str);
	    	} else if (str.startsWith("Score")){
		    	int score = parseStringByEqually(str);
		    	snapshot.setScore(score);
	    	} else if (str.startsWith("=============")){
	    		Location location = new Location(xLocation, yLocation);
	    		Orientation orientation = new Orientation(dxOrientation, dyOrientation);
	    		snapshot.setLocation(location);
	    		snapshot.setOrientation(orientation);
	    		snapshots.add(snapshot);
	            xLocation = -1;
	            yLocation = -1;
	            dxOrientation = -1;
	            dyOrientation = -1;
	            snapshot = new Snapshot();
	    	}
	     }
	}
	
	private int parseStringByEqually(String str){
    	String[] masOfStr = str.split(Pattern.quote(" = "));
    	if (masOfStr.length > 1){
    		int digit = Integer.valueOf(masOfStr[1]);
    		return digit;
    	}
    	return -1;
    }
	
	public void addTrailDataListener(TrailListener listener){
		listOfListeners.add(TrailListener.class, listener);
	}
	
	public void fireTrailChanged(TrailEvent e){
      Object[] listeners = listOfListeners.getListenerList();
      for (int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i] == TrailListener.class) {
              ((TrailListener) listeners[i + 1]).modelChanged(e);
          }
      }
	}
	
	public void nextAct(){
		act++;
		fireTrailChanged(new TrailEvent(this));
	}
	
	public boolean isEnd(){
		return act == snapshots.size() - 1;
	}

	public int getAct() {
		return act;
	}
	
	public Snapshot getSnapshot(){
		return snapshots.get(act);
	}
}
