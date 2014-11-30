package ru.vlsu.anttrail.model;

public class Orientation {
	
    public static final String RIGHT_SIDE = "RIGHT";
    private static final int RIGHT_DX = 1;
    private static final int RIGHT_DY = 0;
    
    public static final String DOWN_SIDE = "DOWN";
    private static final int DOWN_DX = 0;
    private static final int DOWN_DY = 1;
    
    public static final String LEFT_SIDE = "LEFT";
    private static final int LEFT_DX = -1;
    private static final int LEFT_DY = 0;
    
    public static final String UP_SIDE = "UP";
    private static final int UP_DX = 0;
    private static final int UP_DY = -1;
   
    private int dx;
    private int dy;
    private String sideTitle = "";
    
    public Orientation(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
		if (dx == RIGHT_DX && dy == RIGHT_DY)
			sideTitle = RIGHT_SIDE;
		else if (dx == DOWN_DX && dy == DOWN_DY)
			sideTitle = DOWN_SIDE;
		else if (dx == LEFT_DX && dy == LEFT_DY)
			sideTitle = LEFT_SIDE;
		else if (dx == UP_DX && dy == UP_DY)
			sideTitle = UP_SIDE;
    }

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public String getSideTitle() {
		return sideTitle;
	}

}





