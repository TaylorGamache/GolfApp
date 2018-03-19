package model.GolfScore;

import java.util.Calendar;
import java.util.List;

import model.CourseInfo.AbstractGolfCourse;
import model.CourseInfo.HoleInfo;
import model.CourseInfo.TeeInfo;

/**
 * The GolfScore18Hole class represents all the info need for an 18 hole game of golf. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class GolfScore18Hole extends GolfScore {	
	/**
	  * Used for constructing GolfScore18Hole that is not from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a GolfScore18Hole.
	 * 
	 * @return GolfScore18Hole 	    An instance of GolfScore18Hole
	 */
	public GolfScore18Hole(AbstractGolfCourse gc){
		this.course = gc;
		this.date = Calendar.getInstance();
	}
	//May not need
	public GolfScore18Hole(int scoreID, int courseID, String date, ScoreCard sc) {
		this.setID(scoreID);
		this.course = this.getCourseInfo(courseID);
		String[] calen = date.split(",");
		//Stored as year,month,day
		this.date.set(toInt(calen[0]), toInt(calen[1]), toInt(calen[2]));
		this.setScoreCard(sc);
	}
	/**
	  * Used for constructing GolfScore18Hole that is from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a GolfScore18Hole.
	 * 
	 * @return GolfScore18Hole 	    An instance of GolfScore18Hole
	 */
	public GolfScore18Hole(int scoreID, AbstractGolfCourse gc, Calendar date, ScoreCard sc){
		this.setID(scoreID);
		this.setCourse(gc);
		this.setDate(date);
		this.setScoreCard(sc);
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
		TeeInfo tRat = this.getCourse().getTeeInfo(this.getTeeName(uName));
		//Handicap Differential = (Score - Adjusted Course Rating) x (113 / Course Slope).
		if(this.getCourse().getNumHoles() == 18){
			handicap = ((double)this.getScore(uName) - tRat.getRating())  * (113.0 / tRat.getSlope());
		} else {
			handicap = ((double)this.getScore(uName) - tRat.getRating()*2)  * (113.0 / tRat.getSlope());
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
		List<HoleInfo> info = this.getCourse().getTeeInfo(this.getTeeName(tName)).getHoles();
		for (int i = 0; i<info.size();i++){
			PAR = PAR + info.get(i).getPAR();
		}
		//if 9hole course played 18 on
		if (info.size()==9) PAR = PAR*2;
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
			this.card.addPlayerCard(new PlayerCard(uList.get(i).getUserName(),new int[18]," "));
		}
	}

}
