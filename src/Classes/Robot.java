package classes;

public class Robot {
	
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
	
	public String toString() {
		return coordinates.getX()+","+coordinates.getY()+","+orientation;
	}
	
	public Robot clone() {
		return new Robot(coordinates.getX(),coordinates.getY(),orientation);
	}
	
}
