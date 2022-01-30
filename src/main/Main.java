package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.*;




/**
 * {@summary Main executor of the problem}
 * @author Arnau Francesc Llaberia Declara
 * @version 1.0
 */
public class Main {
	
	//Error constants
	static final int NO_ERR = 0;
	static final int ERR_FORMAT = 1;
	static final int ERR_SINTAXIS = 2;
	static final int ERR_COLISION = 3;
	static final int ERR_BOUNDS = 4;
	static final int ERR_NULL = 5;
	
	//Menu constant
	static final int SIZE_MENU = 6;
	static final int INIT_POS_MENU = 7;
	static final int MOVEMENT_MENU = 8;
	
	static Scanner keyboard = new Scanner(System.in);
	static int sizeX;
	static int sizeY;
	static List<Robot> robotsPositions = new ArrayList<Robot>();
	/**
	 * Main of the code
	 * @param args
	 */
	public static void main(String[] args) {
		
		String keyboardResponse;
		String[] checkStr;
		String[] actions;
		int n_robots=0;
		int valueCheck;
		cleanScreen();
		showMenu(0,0,n_robots,null,SIZE_MENU);
		keyboardResponse = keyboard.nextLine();
		checkStr = keyboardResponse.split("\\s+");
		valueCheck = checkingSizeTableau(checkStr);
		
		//Checking correct values of plateau
		while (valueCheck != NO_ERR) {
			cleanScreen();
			switch(valueCheck) {
				case ERR_SINTAXIS:
					System.out.println("Please put correct values (number of x size, number of y size)");
					break;
				case ERR_BOUNDS:
					System.out.println("Please put a valid size of the plateau");
					break;
			}
			System.out.println();
			showMenu(0,0,n_robots,null,SIZE_MENU);
			keyboardResponse = keyboard.nextLine();
			checkStr = keyboardResponse.split("\\s+");
			valueCheck = checkingSizeTableau(checkStr);
		}
		
		sizeX = Integer.parseInt(checkStr[0]) + 1;
		sizeY = Integer.parseInt(checkStr[1]) + 1;
		
		//Starting the loop of robots
		while(! keyboardResponse.equals("q")) {

			Robot initialRobot;
			
			cleanScreen();
			showMenu(sizeX,sizeY,n_robots,null,INIT_POS_MENU);
			keyboardResponse = keyboard.nextLine();
			checkStr = keyboardResponse.split("\\s+");
			valueCheck = checkInitialPos(checkStr);
			
			//Checking the correct values of the initial position of the robot
			while(valueCheck != NO_ERR) {
				cleanScreen();
				switch(valueCheck) {
					case ERR_SINTAXIS:
						System.out.println("Please put the correct format: positon X ,position Y, orientation(N,E,W,S) ");
						break;
					case ERR_BOUNDS:
						System.out.println("Please put the coordenates inside the plateau");
						break;
					case ERR_COLISION:
						System.out.println("Please put the coordenates in a position that there is no robot there");
						break;
				}
				System.out.println();
				showMenu(sizeX,sizeY,n_robots,null,INIT_POS_MENU);
				keyboardResponse = keyboard.nextLine();
				checkStr = keyboardResponse.split("\\s+");
				valueCheck = checkInitialPos(checkStr);
			}

			initialRobot = new Robot(Integer.parseInt(checkStr[0]),Integer.parseInt(checkStr[1]),checkStr[2]);
			
			cleanScreen();
			showMenu(sizeX,sizeY,n_robots,initialRobot,MOVEMENT_MENU);
			keyboardResponse = keyboard.nextLine();
			checkStr = keyboardResponse.split("");
			valueCheck = checkCorrectValuesMovement(checkStr);
			
			//Check the correct format of values
			while (valueCheck != NO_ERR){
				cleanScreen();
				System.out.println("Please put the correct format values (R=right,L=left,M=Movement)");
				System.out.println();
				showMenu(sizeX,sizeY,n_robots,initialRobot,MOVEMENT_MENU);
				keyboardResponse = keyboard.nextLine();
				checkStr = keyboardResponse.split("");
				valueCheck = checkCorrectValuesMovement(checkStr);
			}
			
			actions = checkStr;
			int i =0;
			
			//Check the movement while doing it
			while (i < actions.length){
				action(initialRobot,actions[i]);
				if (actions[i].equals("M")) {
					valueCheck = checkAction(initialRobot.getCoordinates());
				}
				if (valueCheck == NO_ERR) {
					i++;
				}else {
					break;
				}
			}
			
			//Check the end of the robot's movement
			switch(valueCheck) {
				case NO_ERR:
					System.out.println("Final position of robot n"+(n_robots+1)+": "+initialRobot.toString());
					robotsPositions.add(initialRobot);
					break;
					
				case ERR_COLISION:
					System.out.println("Robot n"+(n_robots+1)+" has crashed with robot in position "+initialRobot.getCoordinates().toString()+ "!");
					break;
					
				case ERR_BOUNDS:
					System.out.println("Robot n"+(n_robots+1)+" has gone out of the plateau in position "+initialRobot.getCoordinates().toString()+"!");
			}
			
			n_robots++;
			System.out.println("Press Enter to send another robot, otherwise, press 'q' and Enter");
			keyboardResponse = keyboard.nextLine();
			
		}
		
	}

	/**
	 * Function to show the menu depending on the information we have to provide
	 * @param sizeX: X size of the tableau
	 * @param sizeY: Y size of the tableau
	 * @param n_robots: number of robots in the plateau
	 * @param initialRobot: robot of the actual loop
	 * @param typeOfMenu: type of the menu is it going to show
	 */
	public static void showMenu(int sizeX, int sizeY,int n_robots ,Robot initialRobot, int typeOfMenu) {
		switch(typeOfMenu) {
			case SIZE_MENU:
				System.out.print("Upper-right coordinates of the plateau: ");
				break;
			case INIT_POS_MENU:
				System.out.println("Upper-right coordinates of the plateau:: ("+(sizeX-1)+","+(sizeY-1)+")");
				System.out.print("Initial position of the robot n"+(n_robots+1)+": ");
				break;
			case MOVEMENT_MENU:
				System.out.println("Upper-right coordinates of the plateau:: ("+(sizeX-1)+","+(sizeY-1)+")");
				System.out.println("Initial position of the robot n"+(n_robots+1)+": "+initialRobot.getCoordinates().getX()+" "+
						initialRobot.getCoordinates().getY()+" "+initialRobot.getOrientation());
				System.out.print("Movement is going to do the robot: ");
				break;
		}
	}
	/**
	 * Function to clean the screen
	 */
	public static void cleanScreen() {
		for(int i=0; i<20; i++) System.out.println();
	}
	/**
	 * Function to do the action of the robot
	 * @param robotToMove: Robot is going to do the action
	 * @param mov: Action is going to do, M = Movement, R/L= Rotation
	 */
	public static void action(Robot robotToMove, String mov) {
		
		if (mov.equals("M")) {
			movement(robotToMove.getCoordinates(),robotToMove.getOrientation());
		} else {
			robotToMove.setOrientation(rotate(robotToMove.getOrientation(),mov));
		}
		
	}
	/**
	 * Function to do the action 'movement' of the robot
	 * @param actualPoint: Actual point of the robot
	 * @param ori: Orientation of the robot
	 */
	public static void movement(Point actualPoint, String ori) {
		switch(ori) {
			case "N":
				actualPoint.setY(actualPoint.getY()+1);
				break;
			case "W":
				actualPoint.setX(actualPoint.getX()-1);
				break;
			case "E":
				actualPoint.setX(actualPoint.getX()+1);
				break;
			case "S":
				actualPoint.setY(actualPoint.getY()-1);
		}
	}
	/**
	 * Function to rotate the orientation of the robot
	 * @param actualOri: Actual orientation of the robot
	 * @param rotation: Direction which has the robot to rotate
	 * @return New orientation of the robot
	 */
	public static String rotate(String actualOri, String rotation) {
		switch(actualOri) {
			case "N":
				if (rotation.equals("R")) {
					return "E";
				} else {
					return "W";
				}
				
			case "E":
				if (rotation.equals("R")) {
					return "S";
				} else {
					return "N";
				}
				
			case "W":
				if (rotation.equals("R")) {
					return "N";
				} else {
					return "S";
				}
				
			case "S":
				if (rotation.equals("R")) {
					return "W";
				} else {
					return "E";
				}
			default:
				return actualOri;
		}
	}
	/**
	 * Function to check if the user has choose a correct format size of the tableau
	 * @param checkStr: String of the values to check
	 * @return NO_ERR: No error / ERR_SINTAXIS: Error writing the format of the tableau / ERR_BOUNS: Can not exist a tableau with negative or 0 size
	 */
	public static int checkingSizeTableau(String[] checkStr) {
		
		if (checkStr.length != 2) {
			return ERR_SINTAXIS;
		}
		
		if (checkNumeric(checkStr[0]) != NO_ERR || checkNumeric(checkStr[1]) != NO_ERR) {
			return ERR_SINTAXIS;
		}
		
		if (Integer.parseInt(checkStr[0]) <=0 || Integer.parseInt(checkStr[1]) <=0) {
			return ERR_BOUNDS;
		}

		return NO_ERR;
	}
	/**
	 * Function to check if the pass value is an Integer
	 * @param value: Value to check
	 * @return NO_ERR: No error / ERR_NULL: The value is null / ERR_FORMAT: Is not an integer
	 */
	public static int checkNumeric(String value) {

		if (value == null) {
			return ERR_NULL;
		} else {
			try {
				Integer.parseInt(value);
			} catch(NumberFormatException err) {
				return ERR_FORMAT;
			}
		}
		return NO_ERR;
	}
	/**
	 * Function to check the correct format of the orientation
	 * @param value: Value to check
	 * @return NO_ERR: No error / ERR_SINTAXIS:Error writing the correct value
	 */
	public static int checkOrientation(String value) {
		
		if (value.equals("N") || value.equals("E") || value.equals("W") || value.equals("S") ) {
			return NO_ERR;
		} else {
			return ERR_SINTAXIS;
		}
		
	}
	/**
	 * Function to check if the user has choose a correct format initial position
	 * @param checkStr: String of the values to check
	 * @return NO_ERR: No error / ERR_SINTAXIS: Error writing the format of the tableau / ERR_BOUNDS: Error of the bounds of the tableau / ERR_COLISION: Error for choosing an occupied point
	 */
	public static int checkInitialPos(String[] checkStr) {
		
		if (checkStr.length != 3) {
			return ERR_SINTAXIS;
		}
		
		if (checkNumeric(checkStr[0]) != NO_ERR || checkNumeric(checkStr[1]) != NO_ERR){
			return ERR_SINTAXIS;
		}
		
		if (checkOrientation(checkStr[2]) != NO_ERR) {
			return ERR_SINTAXIS;
		}
		
		Point testPoint = new Point(Integer.parseInt(checkStr[0]),
				Integer.parseInt(checkStr[1]));
		
		if (checkBounds(testPoint) != NO_ERR) {
			return ERR_BOUNDS;
		}
		
		if (checkCollision(testPoint) != NO_ERR) {
			return ERR_COLISION;
		}
		
		return NO_ERR;
		
	}
	/**
	 * Function to check if the action is correct
	 * @param checkPt: Point to check
	 * @return NO_ERR: No error / ERR_BOUNDS: Error of the bounds of the tableau / ERR_COLISION: Error for choosing an occupied point
	 */
	private static int checkAction(Point checkPt) {
		
		if (checkBounds(checkPt) != NO_ERR) {
			return ERR_BOUNDS;
		}
		
		if (checkCollision(checkPt) != NO_ERR) {
			return ERR_COLISION;
		}
		
		return NO_ERR;
		
	}
	/**
	 * Function to check values of the movement
	 * @param checkStr: String to check
	 * @return NO_ERR: No error / ERR_SINTAXIS: Error writing the movement
	 */
	public static int checkCorrectValuesMovement(String[] checkStr) {
		
		int i=0;
		
		while ( i < checkStr.length) {
			if (checkStr[i].equals("L") || checkStr[i].equals("R") || checkStr[i].equals("M")) {
				i++;
			} else {
				return ERR_SINTAXIS;
			}
		}
		
		return NO_ERR;
	}
	/**
	 * Function to check the bounds of the tableau
	 * @param checkPt: Point to check
	 * @return NO_ERR: No error / ERR_BOUNDS: Error of the bounds of the tableau
	 */
	private static int checkBounds(Point checkPt) {
		if (checkPt.getX() >= sizeX || checkPt.getX() < 0 || checkPt.getY() >= sizeY || checkPt.getY() < 0 ) {
			return ERR_BOUNDS;
		}
		
		return NO_ERR;
	}
	/**
	 * Function to check if there is collision
	 * @param checkPt: Point to check
	 * @return NO_ERR: No error / ERR_COLISION: Error for choosing an occupied point
	 */
	private static int checkCollision(Point checkPt) {
		
		int i =0;
		
		while (i < robotsPositions.size()) {
			if (robotsPositions.get(i).getCoordinates().getX() == checkPt.getX() && 
					robotsPositions.get(i).getCoordinates().getY() == checkPt.getY()) {
				return ERR_COLISION;
			}else {
				i++;
			}
		}
		
		return NO_ERR;
	}
	
	
	
	
}
