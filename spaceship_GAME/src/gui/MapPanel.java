package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MapPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public MapPanel() {
		
	}
	
	// the left-upper corner of the map (0, 50)
	int sx = 0;
	int sy = 50;
	
	// info used to locate info of entities and draw them 
	private int[][] shipInfo;
	private int[][] bulletInfo;
	private int[][] obstacleInfo;
	private int[][] treasureInfo;
	
	// when player's ship is hit
	// hurtSignal will be set as 3
	// used to change the color of player's ship for a few seconds to alarm
	public static int hurtSignal = 0;
	
	// 
	public MapPanel(int[][] spInfo) {
		this.shipInfo = spInfo;
	}
	
	public MapPanel(int[][] spInfo, int[][] bltInfo) {
		this.shipInfo = spInfo;
		this.bulletInfo = bltInfo;
	}
	
	public MapPanel(int[][] spInfo, int[][] bltInfo, int[][] obsInfo) {
		this.shipInfo = spInfo;
		this.bulletInfo = bltInfo;
		this.obstacleInfo = obsInfo;
	}
	
	public MapPanel(int[][] spInfo, int[][] bltInfo, int[][] obsInfo, int[][] treInfo) {
		this.shipInfo = spInfo;
		this.bulletInfo = bltInfo;
		this.obstacleInfo = obsInfo;
		this.treasureInfo = treInfo;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//
		drawMAP(g);
		
		//
	}
	
	public void drawMAP(Graphics g) {
		
		// draw the basic boundary lines
		drawLines(g);
		
		// draw ships 
		drawShip(g);
		
		// draw bullets
		drawBullet(g);
		
		// draw obstacles
		drawObstacle(g);
		
		// draw treasures 
		drawTreasure(g);
	}
	
	public void drawTreasure(Graphics g) {
		if(treasureInfo == null) return;
		
		for (int i = 0; i < treasureInfo.length; i++) {
			for (int j = 0; j < treasureInfo[0].length; j++) {
				int x = i * 30 + 1;
				int y = j * 30 + 1;
				
				if(treasureInfo[i][j] == 6) {
					g.setColor(Color.magenta);
					
					// up-triangle form
					int r = 28;
					int xpoints[] = new int[3];
					xpoints[0] = sy + x;
					xpoints[1] = sy + x + r;
					xpoints[2] = sy + x + r/2;
					
				    int ypoints[] = new int[3];
				    ypoints[0] = sx + y;
					ypoints[1] = sx + y;
					ypoints[2] = sx + y + r;

				    int npoints = 3;
					g.fillPolygon(ypoints, xpoints, npoints);
					
				}else if(treasureInfo[i][j] == 7) {
					g.setColor(Color.orange);
					
					// down-triangle form
					int r = 28;
					int xpoints[] = new int[3];
					xpoints[0] = sy + x + r/2;
					xpoints[1] = sy + x;
					xpoints[2] = sy + x + r;
					
				    int ypoints[] = new int[3];
				    ypoints[0] = sx + y;
					ypoints[1] = sx + y + r;
					ypoints[2] = sx + y + r;

				    int npoints = 3;
					g.fillPolygon(ypoints, xpoints, npoints);
				}
			}
		}
	}
	
	public void drawObstacle(Graphics g) {
		if(obstacleInfo == null) return;
		
		g.setColor(new Color(220,223,227));
		
		int w = 28;
		int h = 28;
		
		for (int i = 0; i < obstacleInfo.length; i++) {
			for (int j = 0; j < obstacleInfo[0].length; j++) {
				if(obstacleInfo[i][j] != 0) {
					
					int x = i * 30 + 1;
					int y = j * 30 + 1;
					g.fillRect(sx + y, sy + x, w, h);
				}
			}
		}
		
	}
	
	public void drawBullet(Graphics g) {
		if(bulletInfo == null) return;
		
		int r = 15;
		for (int i = 0; i < bulletInfo.length; i++) {
			for (int j = 0; j < bulletInfo[0].length; j++) {
				if(bulletInfo[i][j] != 0) {
					
					if(bulletInfo[i][j] == 3) {
						// 240,255,255
						g.setColor(new Color(87,250,255));
					}else {
						g.setColor(Color.YELLOW);
					}
					
					int x = i * 30 + 7;
					int y = j * 30 + 7;
					// circle/ball
					g.fillOval(sx + y, sy + x, r, r);
				}
			}
		}
	}
	
	// 
	public void drawShip(Graphics g) {
		if(hurtSignal > 0) {
			drawShip(g, Color.RED, Color.YELLOW);
			hurtSignal--;
		}else {
			drawShip(g, new Color(87,250,255), Color.YELLOW);
		}
	}
	
	public void drawShip(Graphics g, Color player, Color enemies) {
		if(shipInfo == null) return;
		
		g.setColor(Color.YELLOW);
		
//		int w = 28;
//		int h = 28;
		
		for (int i = 0; i < shipInfo.length; i++) {
			for (int j = 0; j < shipInfo[0].length; j++) {
				if(shipInfo[i][j] != 0) {
					
					if(shipInfo[i][j] == 1) {
						// 240,255,255
						
						g.setColor(player);
					}else {
						g.setColor(enemies);
					}
					
//					int x = i * 30 + 1;
//					int y = j * 30 + 1;
//					g.fillRect(sx + y, sy + x, w, h);
					
					// prism form
					int x = i * 30 + 15;
					int y = j * 30 + 15;
					int r = 14;
					int xpoints[] = new int[4];
					xpoints[0] = sy + x - r;
					xpoints[1] = sy + x;
					xpoints[2] = sy + x + r;
					xpoints[3] = sy + x;
				    int ypoints[] = new int[4];
				    ypoints[0] = sx + y;
					ypoints[1] = sx + y - r;
					ypoints[2] = sx + y;
					ypoints[3] = sx + y + r;
				    int npoints = 4;
					g.fillPolygon(ypoints, xpoints, npoints);
				}
			}
		}
	}
	
	// 
	public void drawLines(Graphics g) {
		g.setColor(Color.RED);
		
		// boundary
		g.drawLine(sx, sy, sx + 600, sy);
		g.drawLine(sx, sy + 600, sx + 600, sy + 600);
		g.drawLine(sx, sy, sx, sy + 600);
		g.drawLine(sx + 600, sy, sx + 600, sy + 600);
		
		// grid
//		int row = 20;
//		int col = 20;
		// horizon 
//		for (int i = 0; i < row + 1; i++) {
//            g.drawLine(sx, sy + i * 30, sx + 600, sy + i * 30);
//        }
//         
//		// vertical
//        for (int i = 0; i < col + 1; i++) {
//            g.drawLine(sx + i * 30, sy, sx + i * 30, sy + 600);
//        }
	}
	
}
