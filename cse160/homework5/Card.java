
/**
 * Write a description of class Card here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Card
{
    public static final String[] SUITS = {"Spades","Hearts","Clubs","Diamonds"};
	public static final String[] RANKS = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

	private String suit;
	private String rank;

	/**
	 * Constructor for objects of class Card
	 * 
	 * @param suit - string of suit
	 * @param rank - string of rank
	 */
	public Card(String suit, String rank){
		this.suit = suit;
		this.rank = rank;
	}
	
	//Accessors
	/**
	 * getSuit 
	 * 
	 * @return string with suit
	 */
	public String getSuit(){
		return suit;
	}
	
	/**
	 * getRank
	 * 
	 * @return string with rank
	 */
	public String getRank(){
		return rank;
	}
	
	/**
	 * toString
	 * 
	 * @returns string formatted as 'rank' of 'suit'
	 */
	public String toString(){
		return getRank()+" of "+getSuit();
	}
}
