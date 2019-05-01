package com.fracturedscale.spectrar.dicemanager;

import android.content.Context;

import java.util.Random;

/**
 * 
 * @author Jordan Davis
 *
 * This class represents a die that has a certain number of sides and
 * an image corresponding to the viewable side (rollValue). The dice can
 * be rolled and the viewable side (rollValue) will change to a randomly 
 * selected value in the range [1, numberOfSides]. The image that corresponds
 * to the viewable side is a string value giving the location of the image 
 * file.
 * 
 */
public class Dice {
	private int rollValue;
	private int numberOfSides;
	private int image;
	private Random rand;
	private Context c;
	private String color;
	
	/**
	 * @param numberOfSides - Number of sides on the die.
	 *
	 */
	public Dice(int numberOfSides,Context c,String color) {
		super();
		this.numberOfSides = numberOfSides;
		rand = new Random();
		this.c=c;
		this.color = color;
		roll();
	}
	
	/**
	 * Chooses a random side of the die. Sets the rollValue and the image for the die.
	 */
	public void roll() {

		rollValue = rand.nextInt(numberOfSides) + 1; //Shift the return range from 1 to numberOfSides
		String diceName = "dice_"+numberOfSides+"_"+color+"_"+rollValue;
		//String diceNameTemp = "dice_"+numberOfSides+"_white_"+rollValue;
		setImage(c.getResources().getIdentifier(diceName, "drawable", c.getPackageName()));
	}//"ic_6_white_1edit" "ic_dice_12_white_1"
	
	/**
	 * Returns the number of sides on the die.
	 * 
	 * @return - the number of sides on the die
	 */
	public int getSides() {
		return numberOfSides;
	}

	/**
	 * Sets the number of sides on the die.
	 * @param sides - the number of sides on the die
	 */
	public void setSides(int sides) {
		this.numberOfSides = sides;
	}

	/**
	 * Returns the image of the die
	 * 
	 * @return - image of the die
	 */
	public int getImage() {
		return image;
	}

	/**
	 * Sets the image using the given filepath
	 *
	 * @param image - the file location
	 */
	public void setImage(int image) {
		this.image = image;
	}

	/**
	 * Returns the current rollValue.
	 * 
	 * @return - the current roll value of the die
	 */
	public int getRollValue() {
		return rollValue;
	}
	
}
