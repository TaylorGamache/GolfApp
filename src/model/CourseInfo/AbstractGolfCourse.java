package model.CourseInfo;

import java.util.List;

/**
 * The AbstractGolfCourse class represents what all golf course classes should have, in terms of
 * methods and fields. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public abstract class AbstractGolfCourse {
	
	protected String courseName;
	protected int size;
	protected List<TeeInfo> tInfo;
	protected int courseID;
	
	/**
	 * Used for getting the Par for the course.
	 * <p>
	 * An abstract function that takes a string input of the tee name and returns an int.
	 * 
	 * @param input 	the tee name
	 * @return int 	    The par of the course
	 */
	public abstract int getPAR(String TeeName);
	
	/**
	 * Getters and Setters for all of this classes fields.
	 * 
	 */
	public String getCourseName(){
		return this.courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getNumHoles(){
		return this.size;
	}
	public void setNumHoles(int newSize){
		this.size = newSize;
	}
	public int getID(){
		return this.courseID;
	}
	public void setID(int id){
		this.courseID = id;
	}
	public List<TeeInfo> getTeeInfo(){
		return this.tInfo;
	}
	
	/**
	 * Used for getting TeeInfo from this course.
	 * <p>
	 * Takes a string input and looks for a matching tName in List<TeeInfo> tInfo.
	 * 
	 * @param input 	string to be tested
	 * @return TeeInfo 	If a match is found return the found TeeInfo
	 */
	public TeeInfo getTeeInfo(String tName){
		for(int i = 0; i<this.tInfo.size(); i++){
			if(this.tInfo.get(i).getTeeName().equals(tName)){
				return this.tInfo.get(i);
			}
		}
		return null;
	}

}
