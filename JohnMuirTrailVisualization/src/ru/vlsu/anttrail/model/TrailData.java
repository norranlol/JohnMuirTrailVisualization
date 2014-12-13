package ru.vlsu.anttrail.model;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.event.EventListenerList;

import ru.vlsu.anttrail.event.TrailEvent;
import ru.vlsu.anttrail.event.TrailListener;
import vlsu.ga.data.AntParameters;
import vlsu.ga.data.MapReader;
import vlsu.ga.model.Action;
import vlsu.ga.model.Ant;
import vlsu.ga.model.AntLandscape;
import vlsu.ga.model.Orientation;
import vlsu.ga.model.StateMachine;
import vlsu.ga.model.Transition;
import vlsu.ga.utils.BitStringUtils;

public class TrailData {

	private int act;
	private final ArrayList<Snapshot> snapshots = new ArrayList<Snapshot>();
	private final EventListenerList listOfListeners = new EventListenerList();
	private ArrayList<Point> foodList;

	public TrailData(String automataString){
		MapReader mapReader = new MapReader();
    	foodList = mapReader.getListOfFoods();
		automataString = BitStringUtils.convertBitVectorIndividualToBitString(automataString);
		StateMachine stateMachine = BitStringUtils.createStateMachineFromBitString(automataString);
		AntLandscape antLandscape = new AntLandscape(foodList);
		Ant ant = new Ant(stateMachine, antLandscape);
		
		int initialState = stateMachine.getInitialState();
		int currentState = -1;
		
		while (ant.getTimeSteps() < ant.getMaxTimeSteps()){
			Transition[] transitions = ant.getTransitionArray();
			if (ant.getTimeSteps() == 0){
				currentState = initialState;
				Snapshot snap = new Snapshot(new Location(ant.getxLocation(), ant.getyLocation()),
						Orientation.EAST, ant.getFoodEaten());
				snapshots.add(snap);
			}
			
			Transition transition = ant.findTransitionWithThisCurrentState(transitions, currentState);
			
			String action = transition.getAction().getCurrentAction();
			
			if (action.equals(Action.ACTION_MOVE)){
				ant.move();
			} else if (action.equals(Action.ACTION_TURN_LEFT)){
				ant.turnLeft();
			} else if (action.equals(Action.ACTION_TURN_RIGHT)){
				ant.turnRight();
			} else if (action.equals(Action.ACTION_NOP)){
				ant.skip();
			}
			currentState = transition.getToStateNumber();
			
			Snapshot snapshot = new Snapshot(new Location(ant.getxLocation(), ant.getyLocation()),
					ant.getOrientation(), ant.getFoodEaten());
			snapshots.add(snapshot);
		}
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
