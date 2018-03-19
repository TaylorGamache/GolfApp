package model.UserInfo;

/**
 * The UserClubHouse class represents a user that owns golf courses and doesn't golf. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class UserClubHouse extends AbstractUser {
	private String pw;
	private String email;
	private ClubHouseInfo chInfo;

	/**
	 * Used for constructing a UserClubHouse.
	 * <p>
	 * A constructor that takes all the inputs and makes a UserClubHouse.
	 * 
	 * @return UserClubHouse 	    An instance of UserClubHouse
	 */
	public UserClubHouse(String nEmail, String pw, ClubHouseInfo chInfo) {
		this.setChInfo(chInfo);
		this.setEmail(nEmail);
		this.setPW(pw);
	}
	
	//TODO constructor from DB
	
	/**
	 * Getters and Setters
	 */
	public boolean checkPW(String pw){
		if(pw.equals(this.pw)) return true;
		else return false;
	}
	public String getPWord(){
		return this.pw;
	}
	public void setPW(String pw){
		this.pw = pw;
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String nEmail){
		this.email = nEmail;
	}

	public ClubHouseInfo getChInfo() {
		return chInfo;
	}

	public void setChInfo(ClubHouseInfo chInfo) {
		this.chInfo = chInfo;
	}

	@Override
	public String getUserName() {
		return getChInfo().getName();
	}

}
