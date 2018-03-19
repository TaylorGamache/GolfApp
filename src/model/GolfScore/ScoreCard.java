package model.GolfScore;

import java.util.ArrayList;
import java.util.List;

/**
 * The ScoreCard class represents all the info need for a score card in a golf game. 
 * @author  Taylor Gamache
 * @version 2.0, March 2018
 */

public class ScoreCard {
	protected List<PlayerCard> playerCards = new ArrayList<PlayerCard>();
	
	/**
	  * Used for constructing ScoreCard that is not defined yet.
	 * <p>
	 * Makes an instance of ScoreCard with no PlayerCards added to field playerCards.
	 * 
	 * @return ScoreCard 	    An instance of ScoreCard
	 */
	public ScoreCard(){
		
	}
	/**
	 * Used for constructing ScoreCard that is from the database.
	 * <p>
	 * Makes an instance of ScoreCard with the inputed PlayerCards.
	 * 
	 * @return ScoreCard 	    An instance of ScoreCard
	 */
	public ScoreCard(List<PlayerCard> scores){
		this.playerCards = scores;
	}
	
	/**
	 * Getters and Setters for all the fields
	 */
	public boolean addPlayerCard(PlayerCard pc){
		if (this.playerCards.add(pc)) return true;
		else return false;
	}
	public boolean removePlayerCard(PlayerCard pc){
		if(this.playerCards.remove(pc))return true;
		else return false;
	}
	public List<PlayerCard> getPlayerCards() {
		return this.playerCards;
	}
	
	public String getPlayerTeeName(String uName){
		return getPlayerCard(uName).getTeeName();
	}
	public PlayerCard getPlayerCard(String uName){
		for (int i = 0; i < this.playerCards.size(); i++){
			if (this.playerCards.get(i).getUName().equals(uName)){
				return this.playerCards.get(i);
			}
		}
		
		return null;
	}
	public void editHoleScore(String uName,int holeNum, int score){
		getPlayerCard(uName).setHoleScore(score, holeNum);
	}
	public int getSize(){
		return this.playerCards.size();
	}

}
