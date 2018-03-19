package model.CourseInfo;

/**
 * The HoleInfo class represents a specific holes info for a specific tee. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class HoleInfo {
	private int holeNum;
	private int distance;
	private int handicap;
	private int par;
	private double greenLoc;
	private boolean hide;
	
	/**
	 * Used for constructing a HoleInfo that is not from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a HoleInfo.
	 * 
	 * @return HoleInfo 	    An instance of HoleInfo
	 */
	public HoleInfo(int holeNum,int dis, int handi, int par){
		this.holeNum = holeNum;
		this.distance = dis;
		this.handicap = handi;
		this.par = par;
		this.setGreenLoc(0.0);
		this.hide = false;
	}
	/**
	 * Used for constructing a HoleInfo that is from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a HoleInfo.
	 * 
	 * @return HoleInfo 	    An instance of HoleInfo
	 */
	public HoleInfo(int holeNum, int dis, int handi, int par, double greenLoc, boolean hide){
		this.holeNum = holeNum;
		this.distance = dis;
		this.handicap = handi;
		this.par = par;
		this.setGreenLoc(greenLoc);
		this.hide = hide;
	}

	
	/**
	 * Getters and Setters for all of HoleInfos fields.
	 */
	//Returns the hole number
	public int getHoleNum() {
		return holeNum;
	}
	//sets the hole number
	public void setHoleNum(int holeNum) {
		this.holeNum = holeNum;
	}
	//Returns the hole's distance
	public int getDistance() {
		return this.distance;
	}
	//sets the hole's distance
	public void setDistance(int dis) {
		this.distance = dis;
	}
	//Returns the hole's handicap
	public int getHandicap() {
		return this.handicap;
	}
	//sets the hole's handicap
	public void setHandicap(int handi) {
		this.handicap = handi;
	}
	//Returns the hole's par
	public int getPAR() {
		return this.par;
	}
	//sets the hole's par
	public void setPAR(int par) {
		this.par = par;
	}
	//Returns the greens Location
	public double getGreenLoc() {
		return greenLoc;
	}
	//sets the greens location
	public void setGreenLoc(double greenLoc) {
		this.greenLoc = greenLoc;
	}
	// Returns the value of Hide
	public boolean getHide() {
		return this.hide;
	}

	/**
	 * Used for toggling the value of hide.
	 * <p>
	 * Takes the boolean value of hide and swaps its value.
	 */
	public void toggleHide(){
		this.hide = !(this.hide);
	}
	
}
