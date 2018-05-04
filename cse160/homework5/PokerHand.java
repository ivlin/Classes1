import java.util.List;
/**
 * Write a description of class PokerHand here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PokerHand
{

    private List<Card> cards;

    /**
     * Constructor for objects of class PokerHand
     */
    public PokerHand(List<Card> cards){
        this.cards = cards;
    }

    /**
     * @return a string with the type of hand it is
     */
    public String getType(){
        //an associative array could probably be cleaner and more readable
        //but not sure if we're allowed to use them
        //maybe a custom linked list too
        int[] ranks = new int[Card.RANKS.length];
        int[] suits = new int[Card.SUITS.length];
        String rank, suit;
        for (Card c:cards){
            rank = c.getRank();
            suit = c.getSuit();
            for (int i=0; i<Card.RANKS.length; i++){
                if (rank.equals(Card.RANKS[i])){
                    ranks[i]+=1;
                }
            }
            for (int i=0; i<Card.SUITS.length; i++){
                if (suit.equals(Card.SUITS[i])){
                    suits[i]+=1;
                }
            }
        }
        int maxConsecutive=0;
        int consecutive=0;
        int maxRankCount=0;
        for (int i=0; i<ranks.length+4; i++){
            if (ranks[i%ranks.length]==0){
                if (consecutive>maxConsecutive){
                    maxConsecutive=consecutive;
                }
                consecutive=0;
            }
            else{
                consecutive++;
            }
            if (ranks[i%ranks.length]>maxRankCount){
                maxRankCount=ranks[i%ranks.length];
            }
        }
        int maxSuitCount=0;
        for (int i=0; i<suits.length; i++){
            if (suits[i]>maxSuitCount){
                maxSuitCount=suits[i];
            }
        }
        String type="";
        if (maxConsecutive==5 && maxSuitCount==5){
            type="Straight flush";
        }
        else if (maxConsecutive==5){
            type="Straight";
        }
        else if (maxSuitCount==5){
            type="Flush";
        }
        else if (maxRankCount==4){
            type="Four of a kind";
        }
        else if (maxRankCount==3){
            boolean pairExists=false;
            for (int i=0;i<ranks.length;i++){
                pairExists=pairExists||ranks[i]==2;
            }
            if (pairExists){
                type="Full House";
            }
            else{
                type="Three of a kind";
            }
        }
        else if (maxRankCount==2){
            int pairCount=0;
            for (int i=0;i<ranks.length;i++){
                if (ranks[i]==2){
                    pairCount++;
                }
            }
            if (pairCount>=2){
                type="Two pair";
            }
            else{
                type="Pair";
            }
        }
        else {
            type="High Card";
        }
        return type;
    }

    /**
     * @return the cards
     */
    public String toString(){
        String str="";
        for (Card card: cards){
            str+=card;
            str+=",";
        }
        return str;
    }
}
