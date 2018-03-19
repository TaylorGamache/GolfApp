package model.CourseInfo;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 * The TempGolfCourse class represents a golf course that is not connected to a UserClubHouse. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class TempGolfCourse extends AbstractGolfCourse{
	
	private int PAR;
	private boolean useScore;
	private String clubHouseName;
	
	/**
	 * Used for constructing a TempGolfCourse.
	 * <p>
	 * A constructor that takes all the inputs and makes a TempGolfCourse.
	 * 
	 * @return TempGolfCourse 	    An instance of TempGolfCourse
	 */
	public TempGolfCourse(String clubName, String courName, int numHoles, int PAR, int slope, double rating){
		this.courseName = courName;
		this.setClubHouseName(clubName);
		this.size = numHoles;
		
		this.tInfo = new ArrayList<TeeInfo>();
		List<HoleInfo> hInfo = new ArrayList<HoleInfo>();
		for(int i = 0;i<this.size; i++){
			hInfo.add(new HoleInfo(i+1,0,0,0));
		}
		this.tInfo.add( new TeeInfo("Temporary Tee", Color.WHITE, slope, rating, hInfo) );
		this.PAR = PAR;
		if(PAR != 0 && slope != 0.0 && rating != 0.0){
			this.useScore = true;
		} else {
			this.useScore = false;
		}
	}
	
	/**
	 * Getters and Setters for the fields that are not already defined in AbstractGolfCourse.
	 */
	public int getPAR(String TeeLocation){
		return this.PAR;
	}
	
	public String getClubHouseName() {
		return clubHouseName;
	}

	public void setClubHouseName(String clubHouseName) {
		this.clubHouseName = clubHouseName;
	}
	
	/**
	 * Used for deciding whether or not to store the score where this course is used.
	 * <p>
	 * Returns a boolean stored in useScore.
	 * 
	 * @return boolean 	    Whether or not the score should be used
	 */
	public boolean useScore(){
		return this.useScore;
	}
}
