package model.UserInfo;

/**
 * The GolfClub class represents all the info about a golf club. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class GolfClub {
	private int gcID;
	private String clubType;
	private String clubBrand;
	private String clubModel;
	private double maxDis;
	private double aveDis; 
	private int numHits;
	
	/**
	  * Used for constructing GolfClub that is not from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a GolfClub.
	 * 
	 * @return GolfClub 	    An instance of GolfClub
	 */
	public GolfClub(String type, String brand, String model){
		this.clubType=type;
		this.clubBrand=brand;
		this.clubModel=model;
		this.maxDis=0.0;
		this.aveDis=0.0;
		this.numHits=0;
	}
	/**
	  * Used for constructing GolfClub that is from the database.
	 * <p>
	 * A constructor that takes all the inputs and makes a GolfClub.
	 * 
	 * @return GolfClub 	    An instance of GolfClub
	 */
	public GolfClub(int id, String type, String brand, String model, double max, double ave, int numhits){
		this.gcID = id;
		this.clubType=type;
		this.clubBrand=brand;
		this.clubModel=model;
		this.maxDis=max;
		this.aveDis=ave;
		this.numHits=numhits;
	}
	
	/**
	  * Used for updating the distance stats and number of hits stat.
	 * <p>
	 * Takes the input of double is larger than maxDis then maxDis is now equal 
	 * to the input. Updates aveDis with input and adds 1 to numHits.
	 * 
	 * @param double 	   The distance with this club
	 */
	public void recordDis(double dis){
		if(getMaxD() < dis) {
			this.maxDis = dis;
		}
		this.aveDis = (getAveD()*this.numHits + dis) / (this.numHits + 1);
		this.numHits++;
	}
	/**
	 * Getters and Setters
	 */
	public String getType(){
		return this.clubType;
	}
	public String getBrand(){
		return this.clubBrand;
	}
	public String getModel(){
		return this.clubModel;
	}
	public double getMaxD(){
		return this.maxDis;
	}
	public double getAveD(){
		return this.aveDis;
	}
	public int getNumHits(){
		return this.numHits;
	}
	public int getID(){
		return this.gcID;
	}
	public void setID(int id){
		this.gcID = id;
	}

}
