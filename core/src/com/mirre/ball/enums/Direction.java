package com.mirre.ball.enums;

public enum Direction {
	
	LEFT(-1),
	RIGHT(1),
	UP(1),
	CLIMBDOWN(-1),
	CLIMBUP(1),
	STILL(0);
	
	private int dir;
	
	Direction(int i){
		setDir(i);
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	
	public Direction getReverse(){
		if(this == LEFT)
			return Direction.RIGHT;
		else if(this == RIGHT)
			return Direction.LEFT;
		else
			return this;
	}
}
