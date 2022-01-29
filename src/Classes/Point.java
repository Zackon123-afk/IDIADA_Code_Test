package classes;
/**
 * {@summary Class for saving the position into the plateau}
 * @author Arnau Francesc Llaberia Declara
 * @version 1.0
 */
public class Point {
	/**
	 * x: X position of the plateau
	 * y: Y position of the plateau
	 */
	int x;
	int y;
	/**
	 * Constructor: Create a new instance Point
	 * @param x: X value
	 * @param y: Y value 
	 */
	public Point(int x,int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void SetPoint(int x, int y) {
		this.x=x;
		this.y=y;
	}
	/**
	 * toString: Returns an information string about the object
	 */
	public String toString() {
		return x+","+y;
	}
	
}
