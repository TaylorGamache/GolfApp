package model.UserInfo;

/**
 * The UserTemp class represents a user who is not in the database or will be stored
 * in the database. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class UserTemp extends AbstractUser{
	private String userName;
	
	/**
	  * Used for constructing a UserTemp.
	 * <p>
	 * A constructor that takes the input of String uName and makes a UserTemp
	 * with a userNAme of uName.
	 * 
	 * @return UserTemp 	    An instance of UserTemp
	 */
	public UserTemp(String uName){
		this.setUserName(uName);
	}

	/**
	 * Getters and Setters
	 */
	public void setUserName(String str){
		this.userName = str;
	}
	public String getUserName(){
		return this.userName;
	}

}
