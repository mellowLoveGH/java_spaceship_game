package tester;

import java.util.Scanner;

import game.GAME_MAP;
import game.Ship;
import tools.Point2D;

public class Tester01 {

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		// test ship
		GAME_MAP.init();
		
		Ship myship = new Ship(1, new Point2D(10, 10));
		GAME_MAP.addShip(myship);
		
		int number = 2;
		Ship[] enemies = new Ship[number];
		for (int i = 0; i < enemies.length; i++) {
			Point2D pos = GAME_MAP.getFreePos(1);
			enemies[i] = new Ship(2, pos);
			GAME_MAP.addShip(enemies[i]);
		}
		
		GAME_MAP.printSnapshot();
		
		int dir = scan.nextInt();
		while(dir != -1) {
			
			myship.moveShip(dir);
			
			System.out.println("player fire or not?");
			dir = scan.nextInt();
			if(dir <= 4) {
				myship.setFireDir(dir);
				myship.fireBullet();
			}
			
			for (int i = 0; i < enemies.length; i++) {
				enemies[i].robot();
			}
			
			
			GAME_MAP.updateBullets(myship, enemies);
			
			
			GAME_MAP.printSnapshot();
			dir = scan.nextInt();
		}
		
	}

}
