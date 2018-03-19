package model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import model.CourseInfo.GolfCourse;

/**
 * The ClubHouseInfo class represents all the info about a specific club house to be seen. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class ClubHouseInfo {
	private String phone;
	private String link;
	private String chName;
	private List<GolfCourse> gc = new ArrayList<GolfCourse>();
	
	/**
	  * Used for constructing ClubHouseInfo that is not from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a ClubHouseInfo.
	 * 
	 * @return ClubHouseInfo 	    An instance of ClubHouseInfo
	 */
	public ClubHouseInfo(String chName, String phone, String link){
		this.setName(chName);
		this.setPhone(phone);
		this.setLink(link);
	}
	
	/**
	  * Used for constructing ClubHouseInfo that is from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a ClubHouseInfo.
	 * 
	 * @return ClubHouseInfo 	    An instance of ClubHouseInfo
	 */
	public ClubHouseInfo(String chName, String phone, String link,List<GolfCourse> gc){
		this.setName(chName);
		this.setPhone(phone);
		this.setLink(link);
		this.gc=gc;
	}
	
	/**
	 * Getters and Setters
	 */
	public String getName(){
		return this.chName;
	}
	public void setName(String name){
		this.chName = name;
	}
	public List<GolfCourse> getCourses(){
		return this.gc;
	}
	public boolean addCourse(GolfCourse course){
		return this.gc.add(course);
	}
	public boolean removeCourse(GolfCourse course){
		return this.gc.remove(course);
	}
	public GolfCourse getCourse(String courseName){
		for(int i = 0; i < this.gc.size(); i++){
			if(this.gc.get(i).getCourseName().equals(courseName)) return this.gc.get(i);
		}
		return null;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
