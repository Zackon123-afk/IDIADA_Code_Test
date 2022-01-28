package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.*;





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
			System.out.println("Please put correct values (number of x size, number of y size)");
			showMenu(0,0,n_robots,null,SIZE_MENU);
			keyboardResponse = keyboard.nextLine();
			checkStr = keyboardResponse.split("\\s+");
			valueCheck = checkingSizeTableau(checkStr);
		}
		
		sizeX = Integer.parseInt(checkStr[0]);
		sizeY = Integer.parseInt(checkStr[1]);
		
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
				initialRobot = action(initialRobot,actions[i]);
				if (actions[i].equals("M")) {
					valueCheck = checkAction(initialRobot.getCoordinates());
				}
				if (valueCheck == NO_ERR) {
					i++;
				}else {
					break;
				}
			}
			
			//Check the end of the robot
			switch(valueCheck) {
				case NO_ERR:
					System.out.println("Final position of robot nº"+(n_robots+1)+": "+initialRobot.toString());
					robotsPositions.add(initialRobot);
					break;
					
				case ERR_COLISION:
					System.out.println("Robot nº"+(n_robots+1)+" has crashed with robot in position "+initialRobot.getCoordinates().toString()+ "!");
					break;
					
				case ERR_BOUNDS:
					System.out.println("Robot nº"+(n_robots+1)+" has gone out of the plateau in position"+initialRobot.getCoordinates().toString()+"!");
			}
			
			n_robots++;
			System.out.println("Press Enter to send another robot, otherwise, press 'q' and Enter");
			keyboardResponse = keyboard.nextLine();
			
		}
		
	}

	
	public static void showMenu(int sizeX, int sizeY,int n_robots ,Robot initialRobot, int typeOfMenu) {
		switch(typeOfMenu) {
			case SIZE_MENU:
				System.out.print("Size of the tableau: ");
				break;
			case INIT_POS_MENU:
				System.out.println("Size of the tableau: ("+sizeX+","+sizeY+")");
				System.out.print("Initial position of the robot nº"+(n_robots+1)+": ");
				break;
			case MOVEMENT_MENU:
				System.out.println("Size of the tableau: ("+sizeX+","+sizeY+")");
				System.out.println("Initial position of the robot nº"+(n_robots+1)+": "+initialRobot.getCoordinates().getX()+" "+
						initialRobot.getCoordinates().getY()+" "+initialRobot.getOrientation());
				System.out.print("Movement is going to do the robot: ");
				break;
		}
	}
	
	public static void cleanScreen() {
		for(int i=0; i<20; i++) System.out.println();
	}
	
	public static Robot action(Robot robotToMove, String mov) {
		
		if (mov.equals("M")) {
			robotToMove.setCoordinates(movement(robotToMove.getCoordinates(),robotToMove.getOrientation()));
		} else {
			robotToMove.setOrientation(rotate(robotToMove.getOrientation(),mov));
		}
		
		return robotToMove;
	}
	
	public static Point movement(Point actualPoint, String ori) {
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
		return actualPoint;
	}
	
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

	public static int checkingSizeTableau(String[] checkStr) {
		
		if (checkStr.length != 2) {
			return ERR_SINTAXIS;
		}
		
		if (checkNumeric(checkStr[0]) != NO_ERR || checkNumeric(checkStr[1]) != NO_ERR) {
			return ERR_SINTAXIS;
		}

		return NO_ERR;
	}
	
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

	public static int checkOrientation(String value) {
		
		if (value.equals("N") || value.equals("E") || value.equals("W") || value.equals("S") ) {
			return NO_ERR;
		} else {
			return ERR_SINTAXIS;
		}
		
	}
	
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
		
		if (checkColision(testPoint) != NO_ERR) {
			return ERR_COLISION;
		}
		
		return NO_ERR;
		
	}
	
	private static int checkAction(Point checkPt) {
		
		if (checkBounds(checkPt) != NO_ERR) {
			return ERR_BOUNDS;
		}
		
		if (checkColision(checkPt) != NO_ERR) {
			return ERR_COLISION;
		}
		
		return NO_ERR;
		
	}

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
	
	private static int checkBounds(Point checkPt) {
		if (checkPt.getX() > sizeX || checkPt.getY() > sizeY ) {
			return ERR_BOUNDS;
		}
		
		return NO_ERR;
	}
	
	private static int checkColision(Point checkPt) {
		
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
