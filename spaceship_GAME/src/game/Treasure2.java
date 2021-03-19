package game;

import tools.Point2D;

// Treasure is to increase the fatality of the player's bullets
public class Treasure2 implements Treasure{
	
	// position 
	private Point2D position;
	
	// when timer reduce to 0, it is not alive
	private int timer;
	
	// 
	private boolean alive;
	
	// 
	public Treasure2() {
		// TODO Auto-generated constructor stub
		position = new Point2D();
		timer = 1;
		alive = true;
	}
	
	// 
	public Treasure2(Point2D p) {
		// TODO Auto-generated constructor stub
		position = new Point2D(p.getX(), p.getY());
		this.timer = 80;
		alive = true;
	}
	
	@Override
	public Point2D getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean getAlive() {
		// TODO Auto-generated method stub
		return alive;
	}

	@Override
	public void countDown() {
		// TODO Auto-generated method stub
		if(timer > 0) timer--;
		
		if(timer <= 0) {
			alive = false;
		}
	}

	@Override
	public void effect(Ship player) {
		// TODO Auto-generated method stub
		player.setIncreaseF(1);
	}

}
