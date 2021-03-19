package game;

import tools.Point2D;

// Treasure 1 is to recover the health of player
public class Treasure1 implements Treasure{
	
	// position 
	private Point2D position;
	
	// when timer reduce to 0, it is not alive
	private int timer;
	
	// 
	private boolean alive;
	
	// 
	public Treasure1() {
		// TODO Auto-generated constructor stub
		position = new Point2D();
		timer = 1;
		alive = true;
	}
	
	// 
	public Treasure1(Point2D p) {
		// TODO Auto-generated constructor stub
		position = new Point2D(p.getX(), p.getY());
		this.timer = 60;
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
		System.out.println("player recover health by 3, now it is " + player.getHealth());
		player.recover(3);
	}

}
