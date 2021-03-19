package game;

import tools.Point2D;

public class Bullet {
	
	// the current position of the bullet
	private Point2D position;
	
	// the starting position of the bullet
	// according to the current and staring position to calculate how long the bullet goes
	// the distance should be limited, or the bullet is not alive
	private Point2D startPos;
	
	// the latest/previous position of the bullet
	// this info may be helpful to check whether is hit other ship
	private Point2D prePos;
	
	// how many points will be reduced from health when hitting the ship
	private int fatality;
	
	// how fast the bullet move
	// in this game, it is 1
	private int speed;
	
	// how much distance the bullet could run
	// the limited distance is 9
	// if exceeding that limit, then the bullet is not alive
	private int distance;
	
	// direction the bullet keeps running
	// up 1, down 2, left 3, right 4
	private int dir;
	
	// whether the bullet is alive
	private boolean alive;
	
	// if the bullet is shot by player's ship, 1
	// if by enemies, 2
	private int type;
	
	// 
	public Bullet() {
		
	}
	
	// initialized by position, direction and type
	// position is the current position of the ship that shoots the bullet
	// fd, which direction the bullet should go
	// tp, 1 means shot by player, 2 by enemy
	public Bullet(Point2D p, int fd, int tp) {
		startPos = new Point2D(p.getX(), p.getY());
		prePos = new Point2D(p.getX(), p.getY());
		position = new Point2D(p.getX(), p.getY());	
		fatality = 1;
		speed = 1;
		distance = 9;
		dir = fd;
		alive = true;
		type = tp;
	}
	
	// if the bullet keeps running, return the position it should reach in the next time-stamp
	public Point2D getNextPos() {
		
		// which direction it heads for.
		Point2D to = new Point2D();
		if(dir == 1) {
			to.setX(0);
			to.setY(-1 * speed);
		}else if(dir == 2) {
			to.setX(0);
			to.setY(1 * speed);
		}else if(dir == 3) {
			to.setX(-1 * speed);
			to.setY(0);
		}else if(dir == 4) {
			to.setX(1 * speed);
			to.setY(0);
		}
		
		// current position plus the heading direction
		// calculate the next position
		Point2D next_pos = new Point2D(position.getX() + to.getX(), position.getY() + to.getY());
		
		return next_pos;
	}
	
	// if bullet is alive, it runs every time-stamp
	public void running() {
		
		// get the next position it is heading to
		Point2D next_pos = getNextPos();
		
		// calculate the distance from starting position
		int dis = (next_pos.getX() - startPos.getX()) * (next_pos.getX() - startPos.getX())
				+ (next_pos.getY() - startPos.getY()) * (next_pos.getY() - startPos.getY());
		
		// if exceeding the limited-distance, bullet is not alive
		if(dis > distance * distance) {
			alive = false;
			return;
		} 
		
		//
		prePos = position;
		position = next_pos;
		
		// if the bullet exceeds the boundary of the map, set it as not alive
		if(position.getX() >= GAME_MAP.WIDTH || position.getX() < 0) {
			alive = false;
		}
		if(position.getY() >= GAME_MAP.HEIGHT || position.getY() < 0) {
			alive = false;
		}
		
	}
	
	// for player's ship, it could hit enemies' ships
	// while for enemies' ships, they could only hit player's ship, could not hit within each other
	// therefore, there are over-written methods with different parameters
	// this is used for enemies' ships
	public boolean hitShip(Ship ship) {
		
		if(ship.getHealth() <= 0) {
			return false;
		}
		
		// the position of the bullet
		int x = position.getX();
		int y = position.getY();
		
		// the position of the ship
		int sx = ship.getPosition().getX();
		int sy = ship.getPosition().getY();
		
		// if enter into the circle within the ship with the radius
		int dis = (x - sx) * (x - sx) + (y - sy) * (y - sy);
		if(dis <= Ship.radius) {
			return true;
		}
		
		// 
		// this checking is enough if speed is equal to 1
//		for (int i = 0; i < ship.getShape().size(); i++) {
//			int sx = ship.getShape().get(i).getX();
//			int sy = ship.getShape().get(i).getY();
//			if(x == sx && y == sy) {
//				return true;
//			}
//		}
		
		// further checking when speed is not less than 2
//		int px = prePos.getX();
//		int py = prePos.getY();
//		for (int i = 0; i < ship.getShape().size(); i++) {
//			int sx = ship.getShape().get(i).getX();
//			int sy = ship.getShape().get(i).getY();
//			
//			// if dir = 1 or 2, bullet is vertically running
//			if(dir == 1 || dir == 2) {
//				if(x == sx) {
//					if( (sy - py) * (sy - y) < 0 )
//						return true;
//				}
//			}
//			// if dir = 3 or 4, bullet is horizontally running
//			else if(dir == 3 || dir == 4) {
//				if(y == sy) {
//					if( (sx - px) * (sx - x) < 0 )
//						return true;
//				}
//			}
//		}
		
		return false;
	}
	
	// this is only used for player's ship
	// iterate all enemies
	public Ship hitShip(Ship[] ships) {
		// 
		if(type != 1) return null;
		
		for (int i = 0; i < ships.length; i++) {
			if(hitShip(ships[i])) {
				return ships[i];
			}
		}
		
		return null;
	}
	
	// return the current position
	public Point2D getPosition() {
		return position;
	}

	// set new position
	public void setPosition(Point2D position) {
		this.position = position;
	}

	// previous position
	public Point2D getPrePos() {
		return prePos;
	}

	// set previous position
	public void setPrePos(Point2D prePos) {
		this.prePos = prePos;
	}

	// get the fatality of the ship
	public int getFatality() {
		return fatality;
	}

	// set 
	public void setFatality(int fatality) {
		this.fatality = fatality;
	}

	// get the speed of the bullet
	public int getSpeed() {
		return speed;
	}

	// set 
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	// get the limit of the bullet that could run
	public int getDistance() {
		return distance;
	}

	// set
	public void setDistance(int distance) {
		this.distance = distance;
	}

	// get the direction of the bullet running
	public int getDir() {
		return dir;
	}

	// set 
	public void setDir(int dir) {
		this.dir = dir;
	}
	
	// whether it is alive
	public boolean getAlive() {
		return alive;
	}
	
	// set
	public void setAlive(boolean fg) {
		alive = fg;
	}
	
}
