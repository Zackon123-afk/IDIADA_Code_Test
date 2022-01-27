package main;

import java.util.Scanner;

import classes.*;


public class Main {
	static Scanner keyboard = new Scanner(System.in);
	private int sizeX;
	private int sizeY;
	
	public static void main(String[] args) {
		String keyboardResponse;
		String[] checkStr;
		
		System.out.print("Size of the tableau: ");
		keyboardResponse = keyboard.nextLine();
		checkStr = keyboardResponse.split("\\s+");
		while (checkingSizeTableau(checkStr)==false) {
			cleanScreen();
			System.out.println("Please put correct values (number of x size, number of y size");
			System.out.print("Size of the tableau: ");
			keyboardResponse = keyboard.nextLine();
			checkStr = keyboardResponse.split("\\s+");
		}
		System.out.println(checkStr[0]+" "+checkStr[1]);
	}
	
	public static void cleanScreen() {
		for(int i=0; i<20; i++) System.out.println();
	}
	
	public static boolean checkingSizeTableau(String[] checkStr) {
		if (checkStr.length != 2) {
			return false;
		}
		if (checkNumeric(checkStr[0]) == false) {
			return false;
		}
		if (checkNumeric(checkStr[1]) == false) {
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
	
}
