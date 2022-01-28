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
	
	public void setCoordinates(int x, int y) {
		coordinates.SetPoint(x, y);
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
	
}
