package model.GolfScore;

import java.util.Calendar;
import java.util.List;

import model.CourseInfo.AbstractGolfCourse;
import model.CourseInfo.HoleInfo;
import model.CourseInfo.TeeInfo;

/**
 * The GolfScore9Hole class represents all the info need for an 9 hole game of golf. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class GolfScore9Hole extends GolfScore {
	private boolean front;
	/**
	  * Used for constructing GolfScore9Hole that is not from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a GolfScore9Hole.
	 * 
	 * @return GolfScore9Hole 	    An instance of GolfScore9Hole
	 */
	public GolfScore9Hole(AbstractGolfCourse gc, boolean f){
		this.course = gc;
		this.date = Calendar.getInstance();
		this.front = f;
	}
	//May not need
	public GolfScore9Hole(int scoreID, int courseID, String date, ScoreCard sc, boolean f) {
		this.setID(scoreID);
		this.course = this.getCourseInfo(courseID);
		String[] calen = date.split(",");
		//Stored as year,month,day
		this.date.set(toInt(calen[0]), toInt(calen[1]), toInt(calen[2]));
		this.setScoreCard(sc);
		this.front = f;
	}
	/**
	  * Used for constructing GolfScore9Hole that is from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a GolfScore9Hole.
	 * 
	 * @return GolfScore9Hole 	    An instance of GolfScore9Hole
	 */
	public GolfScore9Hole(int scoreID, AbstractGolfCourse gc, Calendar date, ScoreCard card, boolean f) {
		this.setID(scoreID);
		this.setCourse(gc);
		this.setDate(date);
		this.setScoreCard(card);
		this.setFront(f);
	}
	
	/**
	  * Used for calculating the handicap for a player that is in the score card.
	 * <p>
	 * Gets the courses info and than takes the string input of uName and finds the 
	 * player card with the matching uName. Than takes the courses info and that player 
	 * card and calculates the handicap.
	 * 
	 * @param  String		User name
	 * @return int 	   		Handicap
	 */
	@Override
	public int calcHandicap(String uName) {
		double handicap = 0.0;
		TeeInfo tRat = this.getCourse().getTeeInfo(this.card.getPlayerTeeName(uName));
		//Handicap Differential = (Score - Adjusted Course Rating) x (113 / Course Slope).
		if(this.getCourse().getNumHoles() == 18){
			handicap = ((double)this.getScore(uName)*2 - tRat.getRating())  * (113.0 / tRat.getSlope());
		} else {
			handicap = ((double)this.getScore(uName) - tRat.getRating())  * (113.0 / tRat.getSlope())*2;
		}
		return (int) Math.round(handicap);
	}

	/**
	  * Used for calculating the courses par value for a specific tee.
	 * <p>
	 * Gets the tee info from the course with a name matching string input of tName.
	 * Than adds all the par values from the tees list of HoleInfos. Than returns that sum. 
	 * 
	 * @param  String		Tee name
	 * @return int 	   		Course Par value
	 */
	@Override
	public int getPar(String tName) {
		int PAR = 0;
		List<HoleInfo> info = this.getCourse().getTeeInfo(this.card.getPlayerTeeName(tName)).getHoles();
		for (int i = 0; i<info.size();i++){
			if(front && info.get(i).getHoleNum()<10) {
				//front 9 of 18hole
				PAR = PAR + info.get(i).getPAR();
			} else {
				//if((!front) && info.get(i).getHoleNum()>9){
				//back 9 of 18hole
				PAR = PAR + info.get(i).getPAR();
			} 
		}
		return PAR;
	}
	
	/**
	  * Used for formating the score card initially.
	 * <p>
	 * Gets the user list from GolfScore and makes a PlayerCard for each and adds them to the ScoreCard.
	 */
	@Override
	public void initScoreCard(){
		for(int i = 0; i < uList.size(); i++){
			this.card.addPlayerCard(new PlayerCard(uList.get(i).getUserName(),new int[9]," "));
		}
	}

	/**
	 * Getters and Setters not already in GolfScore
	 */
	public boolean isFront() {
		return front;
	}

	public void setFront(boolean front) {
		this.front = front;
	}

}
