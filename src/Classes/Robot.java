package classes;

public class Robot {
	
	private Point coordinates;
	
	public Robot(int x, int y) {
		coordinates = new Point(x,y);
	}
	
	public Point getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(int x, int y) {
		coordinates.SetPoint(x, y);
	}
	
}
