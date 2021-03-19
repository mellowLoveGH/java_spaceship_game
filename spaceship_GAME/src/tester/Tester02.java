package tester;

import java.util.List;
//import java.util.Scanner;

import game.GAME_MAP;
import game.Obstacle;
import game.Ship;
import game.Treasure;
import gui.GUIShow;
import tools.Point2D;

public class Tester02 {

	public static void main(String[] args) {
//		@SuppressWarnings("resource")
//		Scanner scan = new Scanner(System.in);

		// test GUI frame
		// initialize 2D-array
		GAME_MAP.init();
		
		// add in player's ship
		Ship myship = new Ship(1, new Point2D(10, 18));
		GAME_MAP.addShip(myship);
		
		// add in obstacles
		List<Obstacle> obs_list = GAME_MAP.addObstacles(10, 17);
		
		// add in treasures
		List<Treasure> tre_list = GAME_MAP.addTreasure(3);
		
		// add in enemies' ship
		// number, how many enemies' ships are added in
		// 2-6 is suggested
		int number = 3;
		Ship[] enemies = new Ship[number];
		for (int i = 0; i < enemies.length; i++) {
			Point2D pos = GAME_MAP.getFreePos(2, 0, -10);
			enemies[i] = new Ship(2, pos);
			GAME_MAP.addShip(enemies[i]);
		}
		// health info 
		GAME_MAP.healthInfo(myship, enemies);
		
		// GUI frame created
		GUIShow frame = new GUIShow(myship, enemies, obs_list, tre_list);
		// run the game
		frame.running();
		
//		int dir = scan.nextInt();
//		while (dir != -1) {
//
//			myship.moveShip(dir);
//
//			System.out.println("player fire or not?");
//			dir = scan.nextInt();
//			if (dir <= 4) {
//				myship.setFireDir(dir);
//				myship.fireBullet();
//			}
//
//			for (int i = 0; i < enemies.length; i++) {
//				enemies[i].robot();
//			}
//
//			GAME_MAP.updateBullets(myship, enemies);
//
//			GAME_MAP.printSnapshot();
//			
//			frame.refresh();
//			
//			dir = scan.nextInt();
//		}
	}

}
