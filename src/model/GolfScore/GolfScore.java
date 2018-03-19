package model.GolfScore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.CourseInfo.AbstractGolfCourse;
import model.CourseInfo.GolfCourse;
import model.UserInfo.AbstractUser;
import model.db.DBgolfcourse;

//ID course Temp starts or ends with:1 real:2
//jan is 0 in calendar
/**
 * The GolfScore class represents all the info for both a 18 and 9 hole game of golf. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */
public abstract class GolfScore {
	protected AbstractGolfCourse course;
	protected Calendar date;
	protected ScoreCard card = new ScoreCard();
	protected List<AbstractUser> uList = new ArrayList<AbstractUser>();
	protected int ID;
	
	/**
	 * Gets the inputed user names score from the score card.
	 * <p>
	 * Gets the ScoreCard and gets the inputed strings PlayerCard from ScoreCard.
	 * Than returns that PlayerCard's total score from the game.
	 * 
	 * @return int 	    The players end score
	 */
	public int getScore(String UserName){
		return getScoreCard().getPlayerCard(UserName).getTotalScore();
	}
	
	/**
	  * Used for Testing which Calendar date is newer.
	 * <p>
	 * Returns false if d2 is newer and true if it is not.
	 * 
	 * @return boolean 	    If d1 is more recent than d2
	 */
	public static boolean newerGame(Calendar d1, Calendar d2){
		if(d1.get(Calendar.YEAR)<d2.get(Calendar.YEAR)) return false; 
		else if(d1.get(Calendar.YEAR)>d2.get(Calendar.YEAR)) return true;
		
		else if(d1.get(Calendar.MONTH)<d2.get(Calendar.MONTH)) return false;
		else if(d1.get(Calendar.MONTH)>d2.get(Calendar.MONTH)) return true;
		
		else if(d1.get(Calendar.DATE)<d2.get(Calendar.DATE)) return false;
		else if(d1.get(Calendar.DATE)>d2.get(Calendar.DATE)) return true;
		else return true;
		
	}
	
	/**
	  * Used for putting a list of golf scores in chronological order.
	 * <p>
	 * Takes the input of list of golf scores in puts them in order from most recent to oldest.
	 * 
	 * @param  List<GolfScore>		List of unorganized golf scores
	 * @return List<GolfScore> 	    List of organized golf scores
	 */
	public static List<GolfScore> organizeGames(List<GolfScore> scores){
		List<GolfScore> cronOrder = new ArrayList<GolfScore>();
		while(!scores.isEmpty()){
			GolfScore curScore = scores.get(0);
			for(int i = 0; i<scores.size();i++){
				if(curScore.getDate().after(scores.get(i).getDate())){
					curScore = scores.get(i);
				}
			}
			cronOrder.add(curScore);
			scores.remove(curScore);
		}
		return cronOrder;
	}
	//May not need
	public GolfCourse getCourseInfo(int courseID) {
		return DBgolfcourse.getGolfCourse(courseID);
	}
	//May not need
	protected int toInt(String str){
		return (int) Integer.valueOf(str);
	}
	
	/**
	  *Abstract functions to be defined
	 */
	//returns par for the holes played
	public abstract int getPar(String uName);
	//returns handicap calculation for 18 holes
	public abstract int calcHandicap(String uName);
	//Initialize the ScoreCard
	public abstract void initScoreCard();
	
	/**
	  * Used for making an easy to read date.
	 * <p>
	 * Takes the value of date and returns a date in string form eg. 1/12/2018.
	 * 
	 * @return String 	    String version of the date
	 */
	public String getReadableDate(){
		return String.valueOf(1+getDate().get(Calendar.MONTH))+"/"+getDate().get(Calendar.DATE)+"/"+getDate().get(Calendar.YEAR);
	}
	/**
	  * Used for making an easy to read date without the year.
	 * <p>
	 * Takes the value of date and returns a date in string form eg. 1/12.
	 * 
	 * @return String 	    String version of the date
	 */
	public String getReadableDateWOyear(){
		return String.valueOf(1+getDate().get(Calendar.MONTH))+"/"+getDate().get(Calendar.DATE);
	}
	/**
	  * Used for making a Calendar date from a string.
	 * <p>
	 * Takes the value of str(eg. 1/12/2018) and splits it by /. Than takes the number values
	 * of the split strings and converts them into Integers and gets the value of Calendar they 
	 * represent.
	 * 
	 * @param  String		String version of the date
	 * @return Calendar 	Calendar version of the string date    
	 */
	public Calendar strToCalendar(String str){
		String[] strP = str.split("/");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(strP[2]), Integer.valueOf(strP[0]), Integer.valueOf(strP[1]));
		return cal;
	}
	
	/**
	  *Getters and Setters for the fields of this class
	 */
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String getTeeName(String uName) {
		return getScoreCard().getPlayerTeeName(uName);
	}

	public int[] getHoleScores(String uName) {
		return this.card.getPlayerCard(uName).getScores();
	}

	public void editHoleScores(String uName, int score, int holeNum) {
		this.card.getPlayerCard(uName).setHoleScore(holeNum, score);
	}
	public int getHoleScore(String uName, int holeNum){
		return this.card.getPlayerCard(uName).getHoleScore(holeNum);
	}

	public AbstractGolfCourse getCourse() {
		return course;
	}

	public void setCourse(AbstractGolfCourse course) {
		this.course = course;
	}
	
	public int getNumHoles(){
		//Always should be at least 1 player
		return this.card.getPlayerCards().get(0).getScores().length;
	}
	public ScoreCard getScoreCard(){
		return this.card;
	}
	public void setScoreCard(ScoreCard card){
		this.card = card;
	}

	public void addUsers(AbstractUser u){
		this.uList.add(u);
	}
	public void removeUsers(AbstractUser u){
		this.uList.remove(u);
	}
	public List<AbstractUser> getUsers(){
		return this.uList;
	}
	public int getID(){
		return this.ID;
	}
	public void setID(int nID){
		this.ID = nID;
	}

}
