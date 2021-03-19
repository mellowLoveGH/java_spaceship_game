package game;

import java.util.LinkedList;
import java.util.List;

import tools.Point2D;

public class Obstacle {
	
	// because obstacle is moving
	// when obstacle exceeds the boundary of the map, it will be not valid 
	private boolean valid;
	
	// the position of the obstacle
	private Point2D position;
	
	// use 2D-points to form the shape of the obstacle
	// here by default, just the shape of obstacle only consists one 2D-point
	private List<Point2D> shape;
	
	
	//
	public Obstacle() {
		// TODO Auto-generated constructor stub
		position = new Point2D();
		shape = new LinkedList<>();
		setShape();
		valid = true;
	}
	
	public Obstacle(Point2D p) {
		position = new Point2D(p.getX(), p.getY());
		shape = new LinkedList<>();
		setShape();
		valid = true;
	}
	
	// by default, just the shape of obstacle only consists one 2D-point
	public void setShape() {
		Point2D p1 = new Point2D(position.getX(), position.getY());
		shape.add(p1);
	}
	
	// move the obstacle
	// during the game, the obstacle will move downwards
	public void move(int x, int y) {
		position.move(x, y);
		for (int i = 0; i < shape.size(); i++) {
			shape.get(i).move(x, y);
		}
	}
	
	// get position
	public Point2D getPosition() {
		return position;
	}
	
	// get shape
	public List<Point2D> getShape(){
		return shape;
	}
	
	// check whether it is still valid
	public boolean isValid() {
		
		for (int i = 0; i < shape.size(); i++) {
			int y = shape.get(i).getY();
			if(y >= 20) valid = false;
		}
		
		return valid;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return position.toString();
	}
	
}
