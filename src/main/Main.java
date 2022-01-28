package main;

import java.util.Scanner;

import classes.*;


public class Main {
	static Scanner keyboard = new Scanner(System.in);
	static int sizeX;
	static int sizeY;
	
	public static void main(String[] args) {
		String keyboardResponse;
		String[] checkStr;
		int n_robots=0;
		
		System.out.print("Size of the tableau: ");
		keyboardResponse = keyboard.nextLine();
		checkStr = keyboardResponse.split("\\s+");
		
		while (checkingSizeTableau(checkStr) == false) {
			cleanScreen();
			System.out.println("Please put correct values (number of x size, number of y size)");
			System.out.print("Size of the tableau: ");
			keyboardResponse = keyboard.nextLine();
			checkStr = keyboardResponse.split("\\s+");
		}
		
		sizeX = Integer.parseInt(checkStr[0]);
		sizeY = Integer.parseInt(checkStr[1]);
		
		while(keyboardResponse != "q") {
			
			int initialPosX;
			int initialPosY;
			String initialOr;
			
			cleanScreen();
			System.out.println("Size of tableau: ("+sizeX+","+sizeY+")");
			System.out.print("Initial position of the robot nº"+(n_robots+1)+":");
			keyboardResponse = keyboard.nextLine();
			checkStr = keyboardResponse.split("\\s+");
			
			while(checkInitialPos(checkStr) == false) {
				cleanScreen();
				System.out.println("Size of tableau: ("+sizeX+","+sizeY+")");
				System.out.println("Please put the correct values (X position, Y position, Orientation)");
				keyboardResponse = keyboard.nextLine();
				checkStr = keyboardResponse.split("\\s+");
			}
			
			initialPosX = Integer.parseInt(checkStr[0]);
			initialPosY = Integer.parseInt(checkStr[1]);
			initialOr = checkStr[2];
			
			cleanScreen();
			System.out.println("Size of tableau: ("+sizeX+","+sizeY+")");
			System.out.println("Initial position of the robot nº"+(n_robots+1)+":");
			
			while (checkMovement(checkStr) == false){
				
			}
			
			keyboardResponse = keyboard.nextLine();
		}
		
	}

	public static void cleanScreen() {
		for(int i=0; i<20; i++) System.out.println();
	}
	
	public static boolean checkingSizeTableau(String[] checkStr) {
		
		if (checkStr.length != 2) {
			return false;
		}
		
		if (checkNumeric(checkStr[0]) == false || checkNumeric(checkStr[1]) == false) {
			return false;
		}

		return true;
	}
	
	public static boolean checkNumeric(String value) {

		if (value == null) {
			return false;
		} else {
			try {
				Integer.parseInt(value);
			} catch(NumberFormatException err) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkOrientation(String value) {
		
		if (value == "N" || value == "E" || value == "W" || value == "S") {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static boolean checkInitialPos(String[] checkStr) {
		
		if (checkStr.length != 3) {
			return false;
		}
		
		if (checkNumeric(checkStr[0]) == false || checkNumeric(checkStr[1]) == false){
			return false;
		}
		
		if (checkOrientation(checkStr[2]) == false) {
			return false;
		}
		
		return true;
		
	}
	
	private static boolean checkMovement(String[] checkStr) {
		
		
	}
	
	public static boolean checkCorrectValuesMovement(String[] checkStr) {
		
		int i=0;
		
		while ( i < checkStr.length) {
			if (checkStr[i] == "L" || checkStr[i] == "R" || checkStr[i] == "M") {
				i++;
			} else {
				return false;
			}
		}
		
		return true;
	}
	
}
