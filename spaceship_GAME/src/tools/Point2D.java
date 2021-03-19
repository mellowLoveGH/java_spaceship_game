package tools;

public class Point2D {
	
	// (x, y) position
	private int x;
	private int y;
	
	// default values
	public Point2D() {
		this.x = 0;
		this.y = 0;
	}
	
	// initialize
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// move 
	public void move(int mx, int my) {
		this.x += mx;
		this.y += my;
	}
	
	// 
	public int getX() {
		return x;
	}

	// 
	public void setX(int x) {
		this.x = x;
	}

	// 
	public int getY() {
		return y;
	}

	// 
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
	
}
