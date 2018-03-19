package model.CourseInfo;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The TeeInfo class represents all the info about a specific tee location for a GolfCourse. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class TeeInfo {
	private String tName;
	private Color tColor;
	private int slope;
	private double rating;
	private List<HoleInfo> holes = new ArrayList<HoleInfo>();
	private boolean hideTee;
	private int teeID;
	
	/**
	  * Used for constructing TeeInfo that is not from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a TeeInfo.
	 * 
	 * @return TeeInfo 	    An instance of TeeInfo
	 */
	public TeeInfo(String name, Color col, int slope, double rating, List<HoleInfo> hInfo){
		setTeeName(name);
		setTeeColor(col);
		setRating(rating);
		setSlope(slope);
		this.holes = hInfo;
		this.hideTee = false;
	}
	/**
	 * Used for constructing TeeInfo from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a TeeInfo.
	 * 
	 * @return TeeInfo 	    An instance of TeeInfo
	 */
	public TeeInfo(int tID, String name, Color col, int slope, double rating, List<HoleInfo> hInfo, boolean hide){
		setID(tID);
		setTeeName(name);
		setTeeColor(col);
		setRating(rating);
		setSlope(slope);
		this.holes = hInfo;
		this.hideTee = hide;
	}
	
	/**
	 * All the getters and setters for the classes fields.
	 */
	//Gets the Tee name
	public String getTeeName() {
		return tName;
	}
	//Sets the Tee name
	public void setTeeName(String tName) {
		this.tName = tName;
	}
	//Gets the Tee color
	public Color getTeeColor() {
		return tColor;
	}
	//Sets the Tee color
	public void setTeeColor(Color tColor) {
		this.tColor = tColor;
	}
	//Gets the slope
	public int getSlope(){
		return this.slope;
	}
	//Sets the slope
	public void setSlope(int slope){
		this.slope = slope;
	}
	//Gets the rating 
	public double getRating(){
		return this.rating;
	}
	//Sets the Rating
	public void setRating(double rating){
		this.rating = rating;
	}
	//Gets the list of holes of this tee name
	public List<HoleInfo> getHoles(){
		return this.holes;
	}
	//Gets a Specific hole
	public HoleInfo getHole(int holeNum){
		for(int i = 0; i < this.holes.size(); i ++){
			if(holeNum == this.holes.get(i).getHoleNum()){
				return this.holes.get(i);
			}
		}
		return null;
	}
	//Adds a HoleInfo to this.holes
	public void addHole(HoleInfo hi){
		this.holes.add(hi);
	}
	//Toggles the Hide Tee field
	public void toggleHideTee(){
		this.hideTee = !(this.hideTee);
	}
	//returns a boolean for whether or not to show this tee location
	public boolean showTee(){
		return this.hideTee;
	}
	public int getID(){
		return this.teeID;
	}
	public void setID(int id){
		this.teeID = id;
	}
}
