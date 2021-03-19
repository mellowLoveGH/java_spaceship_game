package tools;

// in this project
// matrix knowledge seems not very necessary
// 2D scalar instead of vector is applied 

public class Vector2D {
	// constructor for vector with given coordinates
	public Vector2D(double x, double y) {}

	// constructor that copies the argument vector 
	public Vector2D(Vector2D v) {}

	// set coordinates
	public Vector2D set(double x, double y) {return null;}

	// set coordinates based on argument vector 
	public Vector2D set(Vector2D v) {return null;}

	// compare for equality (note Object type argument) 
	public boolean equals(Object o) {return true;} 

	// String for displaying vector as text 
	public String toString() {return "";}
			
	//  magnitude (= "length") of this vector 
	public double mag() {return 0;}

	// angle between vector and horizontal axis in radians in range [-PI,PI] 
	// can be calculated using Math.atan2 
	public double angle() {return 0;}

	// angle between this vector and another vector in range [-PI,PI] 
	public double angle(Vector2D other) {return 0;}

	// add argument vector 
	public Vector2D add(Vector2D v) {return null;}

	// add values to coordinates 
	public Vector2D add(double x, double y) {return null;}

	// weighted add - surprisingly useful
	public Vector2D addScaled(Vector2D v, double fac) {return null;}

	// subtract argument vector 
	public Vector2D subtract(Vector2D v) {return null;}

	// subtract values from coordinates 
	public Vector2D subtract(double x, double y) {return null;}

	// multiply with factor 
	public Vector2D mult(double fac) {return null;}

	// rotate by angle given in radians 
	public Vector2D rotate(double angle) {return null;}

	// "dot product" ("scalar product") with argument vector 
	public double dot(Vector2D v) {return 0;}

	// distance to argument vector 
	public double dist(Vector2D v) {return 0;}

	// normalize vector so that magnitude becomes 1 
	public Vector2D normalise() {return null;}

	// wrap-around operation, assumes w> 0 and h>0
	// remember to manage negative values of the coordinates
	public Vector2D wrap(double w, double h) {return null;}

	// construct vector with given polar coordinates  
	public static Vector2D polar(double angle, double mag) {return null;}

}
