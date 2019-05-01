//Assignment 2450-DiceManager
//Program Presets
//Programmer: Rachel Reichmann
//Date: Mar 26, 2019
package com.fracturedscale.spectrar.dicemanager;

public class Presets 
{
	private String presetName;
	private String background;
	private String diceColor;
	private int numFourSided;
	private int numSixSided;
	private int numEightSided;
	private int numTenSided;
	private int numTwelveSided;
	private int numTwentySided;

	/**
	 *
	 * @return total number of dice
	 */
	public int getNumAllDice(){
		return numFourSided+numSixSided+numEightSided+numTenSided+numTwelveSided+numTwentySided;
	}

	/**
	 * @return the presetName
	 */
	public String getPresetName() 
	{
		return presetName;
	}
	
	/**
	 * @param presetName the presetName to set
	 */
	public void setPresetName(String presetName)
	{
		this.presetName = presetName;
	}
	
	/**
	 * @return the background
	 */
	public String getBackground() 
	{
		return background;
	}
	
	/**
	 * @param background the background to set
	 */
	public void setBackground(String background) 
	{
		this.background = background;
	}
	
	/**
	 * @return the diceColor
	 */
	public String getDiceColor() 
	{
		return diceColor;
	}
	
	/**
	 * @param diceColor the diceColor to set
	 */
	public void setDiceColor(String diceColor) 
	{
		this.diceColor = diceColor;
	}
	
	/**
	 * @return the numFourSided
	 */
	public int getNumFourSided() 
	{
		return numFourSided;
	}
	
	/**
	 * @param numFourSided the numFourSided to set
	 */
	public void setNumFourSided(int numFourSided) 
	{
		this.numFourSided = numFourSided;
	}
	
	/**
	 * @return the numSixSided
	 */
	public int getNumSixSided()
	{
		return numSixSided;
	}
	
	/**
	 * @param numSixSided the numSixSided to set
	 */
	public void setNumSixSided(int numSixSided)
	{
		this.numSixSided = numSixSided;
	}
	
	/**
	 * @return the numEightSided
	 */
	public int getNumEightSided() 
	{
		return numEightSided;
	}
	
	/**
	 * @param numEightSided the numEightSided to set
	 */
	public void setNumEightSided(int numEightSided) 
	{
		this.numEightSided = numEightSided;
	}
	
	/**
	 * @return the numTenSided
	 */
	public int getNumTenSided() 
	{
		return numTenSided;
	}
	
	/**
	 * @param numTenSided the numTenSided to set
	 */
	public void setNumTenSided(int numTenSided)
	{
		this.numTenSided = numTenSided;
	}
	
	/**
	 * @return the numTwelveSided
	 */
	public int getNumTwelveSided()
	{
		return numTwelveSided;
	}
	
	/**
	 * @param numTwelveSided the numTwelveSided to set
	 */
	public void setNumTwelveSided(int numTwelveSided) 
	{
		this.numTwelveSided = numTwelveSided;
	}
	
	/**
	 * @return the numTwentySided
	 */
	public int getNumTwentySided() 
	{
		return numTwentySided;
	}
	
	/**
	 * @param numTwentySided the numTwentySided to set
	 */
	public void setNumTwentySided(int numTwentySided) 
	{
		this.numTwentySided = numTwentySided;
	}
}
