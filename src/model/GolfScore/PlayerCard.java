package model.GolfScore;

/**
 * The PlayerCard class represents one row from a score card. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class PlayerCard {
	protected String uName;
	protected int[] scores;
	protected String teeName;
	
	/**
	  * Used for constructing PlayerCard that is not from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a PlayerCard.
	 * 
	 * @return PlayerCard 	    An instance of PlayerCard
	 */
	public PlayerCard(String u,int[] score,String tName){
		this.uName = u;
		this.scores = score;
		this.teeName = tName;
	}
	/**
	  * Used for constructing PlayerCard that is from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a PlayerCard.
	 * Takes String score and converts it into int[].
	 * 
	 * @return PlayerCard 	    An instance of PlayerCard
	 */
	public PlayerCard(String u,String score,String tName){
		this.uName = u;
		this.teeName = tName;
		String[] scr = score.split(",");
		this.scores = new int[scr.length];
		for(int i = 0; i < scr.length; i++){
			this.scores[i] = Integer.valueOf(scr[i]);
		}
	}
	
	/**
	 * Used for converting the scores into a string in order to store the values into the database.
	 * <p>
	 * Takes the field scores and makes a string with a ',' between each value.
	 * 
	 * @return String 	    String of scores in form 1,2,3,4
	 */
	public String formatScores(){
		String str = "";
		for(int i = 0; i < this.scores.length ; i++){
			str += this.scores[i];
			if((i+1) < this.scores.length) str += ",";
		}
		return str;
	}
	
	/**
	 * Getters and Setters
	 */
	
	//To get the Name of the golfer
	public String getUName(){
		return this.uName;
	}
	
	//To get the int array of scores
	public int[] getScores(){
		return this.scores;
	}
	
	//To get individual hole score
	public int getHoleScore(int HoleNum){
		return this.scores[HoleNum-1];
	}
	
	//To edit a hole score on a specific hole
	public void setHoleScore(int Score,int HoleNum){
		this.scores[HoleNum-1] = Score;
	}
	
	//Get total Score
	public int getTotalScore(){
		int sum = 0;
		for(int i = 0;i<this.scores.length;i++){
			sum += getHoleScore(i+1);
		}
		return sum;
	}
	
	public String getTeeName(){
		return this.teeName;
	}
	public void setTeeName(String tName){
		this.teeName = tName;
	}
	
}
