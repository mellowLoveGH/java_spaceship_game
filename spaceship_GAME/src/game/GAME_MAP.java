package game;

import java.util.LinkedList;
import java.util.List;

import tools.Point2D;
import tools.RandomNumber;

public class GAME_MAP {
	
	// the size of the game map
	public static int WIDTH = 20;
	public static int HEIGHT = 20;
	
	// the game map is 2D-array
	// there are 4 maps to store the info of the game
	// when visualizing the game, those maps should all be considered
	// please check the GUIShow and MyPanel class
	public static int[][] map; // used to store the positions of ships
	public static int[][] map1; // used to store the positions of bullets
	public static int[][] map2; // used to store the positions of obstacles
	public static int[][] map3; // used to store the positions of treasures
	public static int[] health; // used to store the health info of ships
	
	public static void init() {
		// initialize the 2D-array map
		// 0 means it is blank
		map = new int[HEIGHT][WIDTH];
		map1 = new int[HEIGHT][WIDTH];
		map2 = new int[HEIGHT][WIDTH];
		map3 = new int[HEIGHT][WIDTH];
		// set as 0 for free place
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = 0;
				map1[i][j] = 0;
				map2[i][j] = 0;
				map3[i][j] = 0;
			}
		}
		
	}
	
	// for ship, info stored into map 2D-array
	// add new ship into the map
	public static void addShip(Ship ship) {
		updateShip(ship, ship.getType());
	}
	
	// for ship, info stored into map 2D-array
	// remove the place info of ship from map
	public static void removeShip(Ship ship) {
		// set the place occupied by my ship back as 0
		for (int i = 0; i < ship.getShape().size(); i++) {
			Point2D p = ship.getShape().get(i);
			int x = p.getX();
			int y = p.getY();
			
			// corresponding to the swing-Frame coordinate system
			// y comes first rather than x
			map[y][x] = 0;
		}
	}
	
	// for ship, info stored into map 2D-array
	// add the place info of ship into the map
	public static void updateShip(Ship ship, int type) {
		// set the place occupied by my ship as 1, enemies ship as 2
		for (int i = 0; i < ship.getShape().size(); i++) {
			Point2D p = ship.getShape().get(i);
			int x = p.getX();
			int y = p.getY();
			
			// corresponding to the swing-Frame coordinate system
			map[y][x] = type;
		}
	}
	
	// for bullet, info stored into map1 2D-array
	// set the place of bullets back as 0
	public static void removeBullets(Ship myship, Ship[] enemies) {
		// for player's bullets
		for (int i = 0; i < myship.getBullets().size(); i++) {
			Point2D p = myship.getBullets().get(i).getPosition();
			int x = p.getX();
			int y = p.getY();
			
			// corresponding to the swing-Frame coordinate system
			map1[y][x] = 0;
		} 
		
		// for enemies' bullets
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].getBullets().size(); j++) {
				Point2D p = enemies[i].getBullets().get(j).getPosition();
				int x = p.getX();
				int y = p.getY();
				
				// corresponding to the swing-Frame coordinate system
				map1[y][x] = 0;
			} 
		}
	}
	
	// for bullet, info stored into map1 2D-array
	// set the place of bullets as 3
	public static void updateBullets(Ship myship, Ship[] enemies) {
		
		// remove the current info
		removeBullets(myship, enemies);
		
		// bullets run and update the info 
		myship.updateBullets(enemies);
		for (int i = 0; i < enemies.length; i++) {
			enemies[i].updateBullets(myship);
		}
		
		// for player
		for (int i = 0; i < myship.getBullets().size(); i++) {
			Point2D p = myship.getBullets().get(i).getPosition();
			int x = p.getX();
			int y = p.getY();
			
			// corresponding to the swing-Frame coordinate system
			map1[y][x] = 3;
		} 
		
		// for enemies
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[i].getBullets().size(); j++) {
				Point2D p = enemies[i].getBullets().get(j).getPosition();
				int x = p.getX();
				int y = p.getY();
				
				// corresponding to the swing-Frame coordinate system
				map1[y][x] = 4;
			} 
		}
	}
	
	// for obstacle, info stored into map2 2D-array
	// from which row to which row, add the obstacles
	// in this game, moving obstacles will exist across the lower half part of the map 
	// and every row, it has 3-8 obstacles
	// used for the initialization of obstacle list
	public static List<Obstacle> addObstacles(int from, int to) {
		
		List<Obstacle> obs_list = new LinkedList<Obstacle>();
		
		for (int i = from; i < to; i = i + 3) {
			int y = i;
			
			// every row, it has 3-8 obstacles
			int num = RandomNumber.getRandomNum(3, 9);
			
			for (int j = 0; j < num; j++) {
				int x = RandomNumber.getRandomNum(0, 20);
				
				// if the place is free
				if(map2[y][x] == 0) {
					map2[y][x] = 5;
					Obstacle obs = new Obstacle(new Point2D(x, y));
					obs_list.add(obs);
				}
			}
		}
		
		return obs_list;
	}
	
	// for obstacle, info stored into map2 2D-array
	// when game begins, obstacles will move 
	// when the existing obstacles running beyond boundary, new row of obstacles will appear
	// used during the playing of the game, add new elements to the obstacle list 
	public static void addObstacles(int from, List<Obstacle> obs_list) {

		int num = RandomNumber.getRandomNum(3, 9);
		int y = from;
		for (int j = 0; j < num; j++) {
			int x = RandomNumber.getRandomNum(0, 20);
			
			// if the place is free
			if(map2[y][x] == 0) {
				map2[y][x] = 5;
				Obstacle obs = new Obstacle(new Point2D(x, y));
				obs_list.add(obs);
			}
		}
	}
	
	// for obstacle, info stored into map2 2D-array
	// set the place of obstacles back as 0
	// because the obstacles are moving, every time-stamp their position should be updated
	// remove the obstacle info from the map2 
	public static void removeObstacles(List<Obstacle> obs_list) {
		if(obs_list == null || obs_list.size() == 0) return;
		
		// iterate the list
		for (int i = 0; i < obs_list.size(); i++) {
			
			Obstacle obs = obs_list.get(i);
			
			for (int j = 0; j < obs.getShape().size(); j++) {
				Point2D tmp = obs.getShape().get(j);
				int x = tmp.getX();
				int y = tmp.getY();
				
				if(obs.isValid()) {
					map2[y][x] = 0;
				}
			}
		}
	}
	
	// for obstacle, info stored into map2 2D-array
	// update the info of moving obstacles
	public static List<Obstacle> moveObstacles(List<Obstacle> obs_list) {
		
		List<Obstacle> tmp_list = new LinkedList<>(); 
		
		for (int i = 0; i < obs_list.size(); i++) {
			obs_list.get(i).move(0, 1);
//			System.out.print(obs_list.get(i));
			
			// if it is still valid
			if(obs_list.get(i).isValid()) {
				tmp_list.add(obs_list.get(i));
			}
		}
		
		return tmp_list;
	}
	
	// for obstacle, info stored into map2 2D-array
	// when the moving obstacle hits player's ship, reducing its health by 1
	public static boolean obstacleHitShip(List<Obstacle> obs_list) {
		
		for (int i = 0; i < obs_list.size(); i++) {
			Point2D tmp = obs_list.get(i).getPosition();
			
			// if the position of obstacle equals the position occupied by player's ship
			if(map[tmp.getY()][tmp.getX()] == 1) {
				System.out.println("obstacle hit player");
				return true;
			}
		}
		
		return false;
	}
	
	// for obstacle, info stored into map2 2D-array
	// set the place of obstacles as 5 
	// add the position info of obstacles in order to refresh the GUI frame
	public static void updateObstacles(List<Obstacle> obs_list) {

		// add new obstacles randomly
		if(RandomNumber.getRandomNum(0, 6) == 2) {
			int from = 10;
			addObstacles(from, obs_list);
		}
		
		// add the position info to the map2
		for (int i = 0; i < obs_list.size(); i++) {
			
			Obstacle obs = obs_list.get(i);
			
			for (int j = 0; j < obs.getShape().size(); j++) {
				Point2D tmp = obs.getShape().get(j);
				int x = tmp.getX();
				int y = tmp.getY();
				
				map2[y][x] = 5;
			}
		}
	}
	
	// for treasure, info stored into map3 2D-array
	// there are two kinds of treasure
	// treasure-1 is to recover health, treasure-2 is to increase the fatality of bullets
	// set treasure-1 as 6
	// set treasure-2 as 7
	public static List<Treasure> addTreasure(int number) {
		List<Treasure> tre_list = new LinkedList<>();
		
		for (int i = 0; i < number; i++) {
			int x = RandomNumber.getRandomNum(1, 18); // 1-17
			int y = RandomNumber.getRandomNum(1, 18); // 1-17
			
			Treasure tre = null;
			// generate different kinds of treasure randomly
			if(RandomNumber.getRandomNum(0, 3) == 1) {
				map3[y][x] = 6;
				tre = new Treasure1(new Point2D(x, y));
			}else {
				map3[y][x] = 7;
				tre = new Treasure2(new Point2D(x, y));
			}
			
			tre_list.add(tre);
		}
		
		return tre_list;
	}
	
	// for treasure, info stored into map3 2D-array
	// update the info about treasure, such as the timer
	// check whether the player's ship collect the treasure
	// if the position of player's ship overlap with that of treasure
	public static List<Treasure> updateTreasure(List<Treasure> tre_list, Ship myship) {
		
		List<Treasure> tmp = new LinkedList<>();
		
		for (int i = 0; i < tre_list.size(); i++) {
			Treasure tre = tre_list.get(i); 
			// update the timer of those treasures
			tre.countDown();
			Point2D p = tre_list.get(i).getPosition();
			
			// whether it is still alive
			if(! tre.getAlive()) {
				map3[p.getY()][p.getX()] = 0;
			}
			// whether it is collected by player
			else if(treasureCollect(tre)){
				tre.effect(myship);
				map3[p.getY()][p.getX()] = 0;
			}
			// 
			else {
				tmp.add(tre);
			}
		}
		
		
		return tmp;
	}
	
	// for treasure, info stored into map3 2D-array
	// check whether the player's ship collect the treasure
	// if the position of player's ship overlap with that of treasure
	public static boolean treasureCollect(Treasure tre) {
		Point2D p = tre.getPosition();
		if(map[p.getY()][p.getX()] == 1) {
			return true;
		}
		return false;
	}
	
	// update every time-stamp
	public static void healthInfo(Ship myship, Ship[] enemies) {
		
		if(health == null) {
			// player's ship + enemies' ship
			health = new int[1 + enemies.length];
		}
		
		health[0] = myship.getHealth();
		for (int i = 0; i < enemies.length; i++) {
			health[i + 1] = enemies[i].getHealth();
		}
		
	}
	
	// used to get a copy of map
	public static int[][] getMAPcopy(int[][] m){
		int[][] copy = new int[HEIGHT][WIDTH]; 
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[0].length; j++) {
				copy[i][j] = m[i][j];
			}
		}
		
		return copy;
	}
	
	// get a free position for enemies ship
	// the position should be bigger enough to harbor its size
	public static Point2D getFreePos(int type) {
		return getFreePos(type, 0, 0);
	}
	
	// the randomly generated place is (0, 0) - (17, 17)
	// the offset value is used to limit the position to certain range
	public static Point2D getFreePos(int type, int offset_x, int offset_y) {
		Point2D fp = new Point2D();
		boolean flag = true;
		
		while(flag) {
			flag = false;
			int x = RandomNumber.getRandomNum(0, 18) + offset_x;
			int y = RandomNumber.getRandomNum(0, 18) + offset_y;
			fp.setX(x);
			fp.setY(y);
			
			// check the randomly generated position
			// if it is legal, then go out of the loop
			if(!checkPos(fp, type)) {
				flag = true;
			}
		}
		
		return fp;
	}
	
	// if the position is legal for the ship, return true
	// fp, is the center point of the ship
	public static boolean checkPos(Point2D fp, int type) {
		
		// get all the shape info
		// check all the points within the shape-list
		List<Point2D> ls = Ship.setShape(fp, type); 
		for (int i = 0; i < ls.size(); i++) {
			Point2D tmp = ls.get(i);
			int x = tmp.getX();
			int y = tmp.getY();
			
			// out of boundary
			if(x >= WIDTH || x < 0) {
				return false;
			}
			if(y >= HEIGHT || y < 0) {
				return false;
			}
			
			// occupied by other ships or obstacles
			if(map[tmp.getY()][tmp.getX()] != 0 || map2[tmp.getY()][tmp.getX()] != 0) {
				return false;
			}
		}
		return true;
	}
	
	// print the current map info
	// this method is used for testing
	public static void printSnapshot() {
		System.out.println("the current snapshot-------------------");
		for (int i = 0; i < map.length; i++) {
			
			for (int j = 0; j < map[0].length; j++) {
				
//				System.out.print(map1[i][j] + " ");
				if(map1[i][j] != 0) {
					System.out.print(map1[i][j] + " ");
				}else if(map[i][j] != 0) {
					System.out.print(map[i][j] + " ");
				}else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	
}
