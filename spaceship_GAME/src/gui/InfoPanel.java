package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class InfoPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfoPanel() {
		// TODO Auto-generated constructor stub
	}
	
	int sx1 = 30;
	int sy1 = 100;
	int sx2 = 30;
	int sy2 = 500;
	private int[] healthInfo;
	
	public InfoPanel(int[] hInfo) {
		this.healthInfo = hInfo;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		// draw health of ships
		drawHealth(g);
	}
	
	// draw 
	public void drawHealth(Graphics g) {
		if(healthInfo == null) return;
		
		int x = 0;
		int y = 0;
		int w = 100;
		int h = 20;
		
		// enemies
		for (int i = 1; i < healthInfo.length; i++) {
			x = sx1;
			y = sy1 + i * 30;
			g.setColor(Color.BLACK);
			g.drawRect(x, y, w, h);
			g.fillRect(x, y, 10 * healthInfo[i], h);
		}
		
		// player
		x = sx2;
		y = sy2;
		g.setColor(Color.BLACK);
		g.drawRect(x, y, w, h);
		g.setColor(Color.RED);
		g.fillRect(x, y, 10 * healthInfo[0], h);
	}
}
