package ru.vlsu.anttrail.model;

public class Snapshot {
	
    private Location location;
    private Orientation orientation;
    private int score;
    
    public Snapshot(){}
    
    public Snapshot(Location location, Orientation orientation, int score){
    	this.location = location;
    	this.orientation = orientation;
    	this.score = score;
    }

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
