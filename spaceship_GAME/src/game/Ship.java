package game;

import java.util.LinkedList;
import java.util.List;

import gui.MapPanel;
import tools.Point2D;
import tools.RandomNumber;

public class Ship {
	
	// points that form the shape of ship
	private List<Point2D> shape;
	
	// the current position of ship
	private Point2D position;
	
	// the health of ship
	// if the health reduce to 0, then the ship is no longer alive
	private int health;
	
	// multiple lives for ships
	// for enemy, it is only 1
	// while for player, it is 3 by default
	private int lives;
	
	// bullets that the ship fired and is running
	// if bullets are alive, it is in the list, or it will be removed from the list 
	private List<Bullet> bullets;
	
	// type of the ship, 1 means player, 2 means enemies
	private int type;
	
	// the direction of firing
	// up 1, down 2, left 3, right 4
	private int fireDir = 0;
	
	// only for player's ship
	// increase fatality when player collect treasure
	private int increseF = 0;
	
	// the radius that is the size of ship
	// if hostile bullets enter the circle with this radius of the ship
	// the ship will be taken as being hit
	public static int radius = 2;
	
	//
	public Ship() {
		// TODO Auto-generated constructor stub
	}
	
	// create a ship
	public Ship(int type, Point2D p) {
		this.type = type;
		position = p;
		shape = setShape(position, type);
		health = 10;
		bullets = new LinkedList<>();
		if(type == 1) {
			lives = 2;
		}else if(type == 2) {
			lives = 0;
		}
		// log
		System.out.println("ship type " + type + " is created");
	}
	
	// print out the basic information about the ship
	public void printInfo() {
		String str = "";
		str = str + "type " + type;
		str = str + "\nhealth " + health;
		str = str + "\nposition " + position;
		str = str + "\nshape ";
		for (int i = 0; i < shape.size(); i++) {
			str = str + shape.get(i) + " ";
		}
		str = str + "\n";
		System.out.println(str);
	}
	
	// return the health of the ship
	public int getHealth() {
		return health;
	}
	
	// return the current position of the ship
	public Point2D getPosition() {
		return position;
	}

	// return the shape of the ship, which is a list consisting of 2D-points
	public List<Point2D> getShape() {
		return shape;
	}
	
	// return the bullets of the ship, 
	// which is a list consisting of alive bullets fired by the ship
	public List<Bullet> getBullets() {
		return bullets;
	}

	// return the type of this ship
	// 1 means player, 2 means enemies or robot-ship
	public int getType() {
		return type;
	}
	
	public int getLives() {
		return lives;
	}

//	public void setType(int type) {
//		this.type = type;
//	}
//	
	// default shape
	// according to the center position to form a shape
	// the center position is usually the current position
	// the reason why set this method as 'static' is:
	// this method may be used in GAME_MAP class to check 
	// whether a generated place is bigger enough to harbor the ship
	// please check the method 'checkPos' in GAME_MAP class
	public static List<Point2D> setShape(Point2D p, int type) {
		
		// 2D-points stored in a list, forming a shape
		List<Point2D> ls = new LinkedList<>();
		
		Point2D sp1 = new Point2D(p.getX(), p.getY());
		Point2D sp2 = new Point2D(p.getX(), p.getY() - 1);
		Point2D sp3 = new Point2D(p.getX() - 1, p.getY());
		Point2D sp4 = new Point2D(p.getX() + 1, p.getY());
		ls.add(sp1);
		ls.add(sp2);
		ls.add(sp3);
		ls.add(sp4);
		
		// for player's ship, its shape is:
		//  *
		// ***
		if(type == 1) {
			
		}
		// while for enemies' ships, their shape are:
		//  *
		// ***
		//  *
		else if(type == 2) {
			Point2D sp5 = new Point2D(p.getX(), p.getY() + 1);
			ls.add(sp5);
		}
		
		return ls;
	}
//	
//	public void setBullets(List<Bullet> bullets) {
//		this.bullets = bullets;
//	}
	
	// move the ship to a new position
	public void moveShip(int mx, int my) {
		position.move(mx, my);
		// move the shape
		for (int i = 0; i < shape.size(); i++) {
			shape.get(i).move(mx, my);
		}
	}
	
	// obviously, there are 4 directions to potentially move
	// UP 1, DOWN 2, LEFT 3, RIGHT 4
	// while before moving, it should be checked that
	// whether the next position is movable or not
	// if the next position is occupied by other entity, it is not movable
	public boolean moveShip(int direction) {
		
		// dir means the direction to move
		Point2D dir = new Point2D();
		if(direction == 1) {
			dir.setX(0);
			dir.setY(-1);
		}else if(direction == 2) {
			dir.setX(0);
			dir.setY(1);
		}else if(direction == 3) {
			dir.setX(-1);
			dir.setY(0);
		}else if(direction == 4) {
			dir.setX(1);
			dir.setY(0);
		}
		
		// remove the place info of ship from the map
		GAME_MAP.removeShip(this);
		
		// calculate what the next position is
		
		Point2D next_pos = new Point2D(position.getX() + dir.getX(), position.getY() + dir.getY());
		
//		System.out.println(next_pos + "\t" + position);
		// and check whether the next position is movable or not
		if(! GAME_MAP.checkPos(next_pos, type)) {
			// 
//			System.out.println("can not move the direction of " + direction);
			
			// if not movable, set back the current place info of the ship
			GAME_MAP.updateShip(this, type);
			return false;
		}
		
		// for enemies, it should only move in the upper part of the map
		if(type == 2) {
			if(next_pos.getY() >= 10) {
				return false;
			}
		}
		
		// if movable, then move the ship
		moveShip(dir.getX(), dir.getY());
		
		// add the new place info of ship into the map
		GAME_MAP.updateShip(this, type);
		
		return true;
	}
	
	// for enemies ship, they are robot-controlled
	// here I would not use AI, which will be much difficult
	// it just randomly and stupidly move and fire with different direction
	public void robot() {
		if(type != 2) return; // only for enemies's ship
		
		// four directions to move and stay constantly, it is 5 options
		// 0 means stay constantly
		int d1 = RandomNumber.getRandomNum(0, 5);
		boolean flag = moveShip(d1);
		if(!flag) {
			System.out.println("robot can not move");
		}
		
		// 
		int d2 = RandomNumber.getRandomNum(0, 5);
		if(d2 == 1) {
			// fire
			fireDir = RandomNumber.getRandomNum(1, 5);
//			System.out.println("robot fire bullet!" + fireDir);
			fireBullet();
		}
	}
	
	// ship fires
	// add the new-created bullet into the list
	public void fireBullet() {
		Bullet blt = new Bullet(position, fireDir, type);
		bullets.add(blt);
	}
	
	// only for player, type 1
	// because enemies could not hit each other, they could only hit player
	// check whether the bullets of player's ship hit enemies or not
	public void updateBullets(Ship[] enemies) {
		// iterate all bullets of player's ship
		for (int i = 0; i < bullets.size(); i++) {
			if(bullets.get(i).getAlive()) {
				bullets.get(i).running();
				
				// if hit enemies, return that enemy's ship
				Ship tmp = bullets.get(i).hitShip(enemies);
				if(tmp != null) {
//					System.out.println("player hit enemy!");
					// set the bullet as not alive
					bullets.get(i).setAlive(false);
					
					// hurt the enemy's ship that is hit
					tmp.hurt(bullets.get(i).getFatality() + increseF);
					
//					System.out.println(tmp.getHealth() + " remains");
				}
			}
		}
		
		// remove those not alive bullets from the list
		removeBullets();
	}
	
	// only for enemies, type 2
	// because enemies could not hit each other, they could only hit player
	public void updateBullets(Ship player) {
		for (int i = 0; i < bullets.size(); i++) {
			if(bullets.get(i).getAlive()) {
				bullets.get(i).running();
								
				// if hit player
				boolean tmp = bullets.get(i).hitShip(player);
				if(tmp) {
//					System.out.println("enemy hit player!");
					// set the bullet as not alive
					bullets.get(i).setAlive(false);
					
					// hurt player's ship
					player.hurt(bullets.get(i).getFatality());
					
//					System.out.println("player " + player.getHealth() + " remains");
				}
			}
		}
		
		// remove those not alive bullets from the list
		removeBullets();
	}
	
	// remove those not alive bullets from the list
	public void removeBullets() {
		LinkedList<Bullet> ls = new LinkedList<>();
		for (int i = 0; i < bullets.size(); i++) {
			if(bullets.get(i).getAlive()) {
				ls.add(bullets.get(i));
			}
		}
		bullets = ls;
	}
	
	// set firing direction
	public void setFireDir(int fd) {
		fireDir = fd;
	}
	
	// only for player, when it collect treasure
	// type 1
	public void setIncreaseF(int inf) {
		increseF =+ inf;
	}
	
	// hurt the ship
	// for type 1 (player), if health reduce to 0, game over, 
	// obstacle and bullet both could hurt player's ship
	// for type 2 (enemy), if health reduce to 0, just remove it from map
	// only bullets from player could hurt enemies
	// because there are several enemies, and only one player
	public void hurt(int h) {
		if(health >= h) health -= h;
		else health = 0;
		
		// if player's ship get hurt, show the signal
		if(type == 1) {
			MapPanel.hurtSignal = 3;
		}
		
		// for enemies, if not alive, remove it from MAP
		if(type == 2 && health == 0) {
			dead();
		}
		
		// another life 
		if(type == 1 && health == 0) {
			if(lives > 0) {
				lives--;
				recover(10);
			}
		}
		
	}
	
	// if player's ship collect treasure, it recover health
	public void recover(int h) {
		health += h;
		if(health > 10) health = 10;
	}
	
	// when enemies' health reduce to 0
	public void dead() {
		if(health > 0) return;
		
		// remove it from map
		GAME_MAP.removeShip(this);
		
		// log
		System.out.println("one enemy is hit to death");
	}
}
