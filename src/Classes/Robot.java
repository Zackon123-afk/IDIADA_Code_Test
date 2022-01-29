package classes;
/**
 * {@summary Class for saving the information of the robot}
 * @author Arnau Francesc Llaberia Declara
 * @version 1.0
 */
public class Robot {
	/**
	 * coordinates: Point where is located the Robot
	 * orientation: in which of the 4 cardinal positions is the robot watching
	 */
	private Point coordinates;
	private String orientation;
	
	public Robot(int x, int y, String orientation) {
		coordinates = new Point(x,y);
		this.orientation = orientation;
	}
	
	public Point getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Point point) {
		coordinates = point;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	/**
	 * toString: Returns an information string about the object
	 */
	public String toString() {
		return coordinates.toString()+","+orientation;
	}
	/**
	 * clone: Returns a new Robot with the same attributes as this one
	 */
	public Robot clone() {
		return new Robot(coordinates.getX(),coordinates.getY(),orientation);
	}
	
}
