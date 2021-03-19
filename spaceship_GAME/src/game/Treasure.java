package game;

import tools.Point2D;

public interface Treasure {
	
	// the position of the treasure
	public Point2D getPosition();
	
	// whether the treasure is alive or not
	public boolean getAlive();
	
	// there is a timer for treasure, every time-stamp, reduce the timer
	// if the timer reduce to 0, then the treasure is not alive
	public void countDown();
	
	// when the treasure is collected by player
	// player gets the benefit
	public void effect(Ship player);
}
