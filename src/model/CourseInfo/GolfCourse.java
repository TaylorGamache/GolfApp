package model.CourseInfo;

import java.util.List;

/**
 * The GolfCourse class represents a golf course that is connected to a UserClubHouse. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class GolfCourse extends AbstractGolfCourse {

	private String location;
	
	/**
	 * Used for constructing a GolfCourse that is not from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a GolfCourse.
	 * 
	 * @return GolfCourse 	    An instance of GolfCourse
	 */
	public GolfCourse(String courseName,String loc,int size,List<TeeInfo> f) {
		this.setCourseName(courseName);
		this.setNumHoles(size);
		this.setLocation(loc);
		this.tInfo = f;		
	}
	
	/**
	 * Used for constructing a GolfCourse from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a GolfCourse.
	 * 
	 * @return GolfCourse 	    An instance of GolfCourse
	 */
	public GolfCourse(int courseID, String courseName,String loc,int size,List<TeeInfo> f) {
		this.setCourseName(courseName);
		this.setNumHoles(size);
		this.setLocation(loc);
		this.tInfo = f;		
		this.setID(courseID);
	}

	/**
	 * Getters and Setters for the fields that are not already defined in AbstractGolfCourse.
	 */
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPAR(String TeeName){
		TeeInfo hInfo = getTeeInfo(TeeName);
		int sum = 0;
		for(int i = 0;i<hInfo.getHoles().size();i++){
			sum = sum + hInfo.getHole(i+1).getPAR();
		}
		return sum;
	}
	
}
	
