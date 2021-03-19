package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.GAME_MAP;
import game.Obstacle;
import game.Ship;
import game.Treasure;
import tools.RandomNumber;

public class GUIShow {
	
	// size of the frame
	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	
	// the frame as the basic container
	// there are two panels added on the frame
	// one is for map, and other is for info
	private JFrame frame;
	private JPanel panel_map;
	private JPanel panel_info;
	
	// info used in the GUI frame, there are four kinds of info
	// ships
	// bullets that could be got from related ships
	// obstacles
	// treasures
	private Ship myship;
	private Ship[] enemies;
	private List<Obstacle> obs_list;
	private List<Treasure> tre_list;
	
	// parameters past into and show the game by GUI frame
	public GUIShow(Ship myship, Ship[] enemies, List<Obstacle> obs_list, List<Treasure> tre_list) {
		
		this.myship = myship;
		this.enemies = enemies;
		this.obs_list = obs_list;
		this.tre_list = tre_list;
		
		// basic info for the frame
		// title, size, and how to close
		frame = new JFrame("CE218 GAME");
		frame.setBounds(0,0,WIDTH,HEIGHT); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		// set the layout as Border
		// two panels on the frame actually
		// one is the left side for map, and the other is right side for info
		frame.setLayout(new BorderLayout());
		
		// the map-panel
		// show the game where there are moving ships, moving obstacles and constant treasures
		panel_map = new MapPanel(GAME_MAP.map, GAME_MAP.map1, GAME_MAP.map2, GAME_MAP.map3);
		panel_map.setPreferredSize(new Dimension(600, 800)); // size
		panel_map.setBackground(new Color(0, 0, 0)); // background color
		
		// the info-panel
		// show the game info: the health of the ships
		// the upper part is for enemies' ships
		// the lower part is for player's ship
		panel_info = new InfoPanel(GAME_MAP.health);
		panel_info.setPreferredSize(new Dimension(180, 800)); // size 
		panel_info.setBackground(new Color(255, 255, 255)); // background color
		
		// add in the two panels
		frame.add(panel_map, BorderLayout.WEST);
		frame.add(panel_info, BorderLayout.EAST);
		
		// show the frame
		frame.setVisible(true);
	}
	
	public void refresh() {
		// refresh/re-paint the frame when info updates
		frame.repaint();		
		
		// here the log info could also be printed in the console
//		GAME_MAP.printSnapshot();
	}
	
	
	// player's ship, moving direction and firing direction
	int dir = 0;
	int fireDir = 5;
	
	// run the game
	public void running() {
		
		// when player's ship runs out of health, game over, lose
		// when all the enemies' ships run out of health, game over, win
		// when game over, flag is false
		boolean flag = true;
		int timer = 1;
		
		while (flag) {
			
			// add key listener
			frame.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					switch(e.getKeyCode()) {
					case KeyEvent.VK_UP:
						dir = 1;
						break;
					case KeyEvent.VK_DOWN:
						dir = 2;
						break;
					case KeyEvent.VK_LEFT:
						dir = 3;
						break;
					case KeyEvent.VK_RIGHT:
						dir = 4;
						break;
					}
					
					switch(e.getKeyCode()) {
					case KeyEvent.VK_W:
						fireDir = 1;
						break;
					case KeyEvent.VK_S:
						fireDir = 2;
						break;
					case KeyEvent.VK_A:
						fireDir = 3;
						break;
					case KeyEvent.VK_D:
						fireDir = 4;
						break;
					}
				}
			});
			
			// map-panel part
			// my ship
			// if there is keyboard event 
			// according to keyboard, the player's ship move or fire
			myship.moveShip(dir);
			if(fireDir < 5) {
				myship.setFireDir(fireDir);
				myship.fireBullet();
			}
			refresh();
			// set the directions back
			dir = 0;
			fireDir = 5;
			
			// enemies' ships
			// count the number of alive enemies
			int enemies_num = 0;
			for (int i = 0; i < enemies.length; i++) {
				// if enemies are all dead, then you win
				if(enemies[i].getHealth() > 0) {
					enemies[i].robot();
					enemies_num++;
				}
			}
			// if there is no alive enemies
			if(enemies_num == 0) {
				System.out.println("player win!");
				flag = false;
			}
			refresh();
			
			// bullets
			// update the bullet info both for player's ship and enemies' ships
			GAME_MAP.updateBullets(myship, enemies);
			refresh();
			
			
			// obstacles
			// set a timer for obstacle to move
			if(timer % 3 == 0) {
				// remove the current place info of obstacles
				GAME_MAP.removeObstacles(obs_list);
//				System.out.print(obs_list.size() + " -------------- ");
				// update moving-obstacle info
				obs_list = GAME_MAP.moveObstacles(obs_list);
//				System.out.println(obs_list.size());
				// add in the place info of obstacles
				GAME_MAP.updateObstacles(obs_list);
				// whether the obstacles hit player's ship
				if(GAME_MAP.obstacleHitShip(obs_list)) {
					myship.hurt(1); // reduce its health by 1
				}
				refresh();
			}
			
			// treasure
			// update the place info of treasure
			// randomly add in new treasures
			tre_list = GAME_MAP.updateTreasure(tre_list, myship);
			if(tre_list.size() == 0 && RandomNumber.getRandomNum(0, 20) == 1) {
				tre_list = GAME_MAP.addTreasure(3);
			}
			refresh();
			
			
			// info-panel part
			// health of ships
			GAME_MAP.healthInfo(myship, enemies);
			refresh();
			
			
			// game over 
			if(myship.getHealth() <= 0 && myship.getLives() == 0) {
				flag = false;
			}
			
			timer++;
			timeStamp(2);
		}
		
	}
	
	public void timeStamp(int number) {
		try {
			Thread.sleep(number * 100);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
//		new GUIShow();
	}
}















