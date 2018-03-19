package model.UserInfo;

import java.util.List;

import model.GolfScore.GolfScore;
  
/**
 * The UserGolfer class represents a user that golfs. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class UserGolfer extends AbstractUser {
	private String email; //Primary key
	private String uName;
	private List<GolfClub> clubs;
	private double handicap;
	private double aveNineScore;
	private int numGames;
	private String pw;
	
	/**
	 * Used for constructing a UserGolfer.
	 * <p>
	 * A constructor that takes all the inputs and makes a UserGolfer.
	 * 
	 * @return UserGolfer 	    An instance of UserGolfer
	 */
	public UserGolfer(String uName,String pWord, String email, List<GolfClub> clubs) {
		this.uName = uName;
		this.pw = pWord;
		this.email=email;
		this.clubs=clubs;
		this.handicap = 0.0;
		this.aveNineScore = 0.0;
		this.numGames = 0;
	} 
	
	/**
	 * Used for setting the stats of the UserGolfer from the database.
	 * <p>
	 * Takes the inputs and stores them as the stats for the UserGolfer.
	 */
	public void setStats(double aveScor, int NOG, double handi){
		this.aveNineScore = aveScor;
		this.numGames = NOG;
		this.handicap = handi;
	}
	
	//May not use
	public void addAveNineScore(GolfScore score) {
		int sc = score.getScore(getUserName());
		this.aveNineScore = (this.aveNineScore*this.numGames + sc) / (this.numGames+1);
	}
	
	/**
	 * Used for adding the inputed GolfScore's handicap for this user into his handicap stat.
	 * <p>
	 * Takes the input and gets the handicap for this user from it. Than updates the users handicap 
	 * stat with it.
	 * 
	 * @param GolfScore  	golf game
	 */
	public void addHandicap(GolfScore score) {
		this.handicap = (this.handicap*((double)this.numGames) + score.calcHandicap(getUserName())) / ((double)this.numGames+1);
	}
	
	/**
	 * Used for checking if the inputed password matches the UserGolfers password.
	 * <p>
	 * Takes the input and if it is equal to the UserGolfers pw than returns true else false.
	 * 
	 * @param String  	submitted password
	 * @return boolean	if passwords match
	 */
	public boolean checkPW(String str){
		if(str.equals(this.pw)) return true;
		else return false;
	}
	
	/**
	 * Getters and Setters
	 */
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String nEmail) {
		this.email = nEmail;
	}
	public GolfClub getClub(String type, String model, String brand){
		for (int i = 0; i<this.clubs.size(); i++) {
			if(this.clubs.get(i).getBrand().equals(brand) &&
			   this.clubs.get(i).getType().equals(type) &&
			   this.clubs.get(i).getModel().equals(model)) {
				return this.clubs.get(i);
			}
		}
		return null;
	}
	
	public List<GolfClub> getClubs() {
		return clubs;
	}
	public void addClub(GolfClub club) {
		this.clubs.add(club);
	}
	public void removeClub(GolfClub club) {
		this.clubs.remove(this.clubs.indexOf(club));
	}

	public double getHandicap() {
		return handicap;
	}

	public double getAveNineScore() {
		return aveNineScore;
	}

	public int getNumGames() {
		return numGames;
	}

	public void setNumGames(int numGames) {
		this.numGames = numGames;
	}

	public void changePW(String newPW){
		this.pw = newPW;
	}
	public String getPWord(){
		return this.pw;
	}

	@Override
	public String getUserName() {
		return this.uName;
	}
	public void setUserName(String name){
		this.uName = name;
	}

}


