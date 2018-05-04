import java.util.Random;
/**
 * Write a description of class PokerDeck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PokerDeck{
    private Card[] cards;
    private int topIndex;
    
    public PokerDeck(){
        cards = new Card[52];
        topIndex=0;
        initDeck();
        shuffle();
    }
    
    //fills the deck with cards in ascending order
    private void initDeck(){
        for (int s=0; s<Card.SUITS.length; s++){
            for (int r=0; r<Card.RANKS.length; r++){
                cards[s*Card.RANKS.length+r]=new Card(Card.SUITS[s],Card.RANKS[r]);
            }
        }
    }
    
    //shuffle the deck randomly
    private void shuffle(){
        Random rand = new Random();
        Card tempCard;
        int tempIndex;
        for (int top=topIndex; top<cards.length; top++){
            tempIndex = top+rand.nextInt(cards.length-top);
            tempCard = cards[tempIndex];
            cards[tempIndex]=cards[top];
            cards[top]=tempCard;
        }
    }
    
    
    /**
     * hasMoreCards
     * 
     * @return true if there are any more cards in the deck, otherwise false
     */
    public boolean hasMoreCards(){
        return topIndex < 52;
    }
    
    /**
     * nextCard 
     * 
     * @return the next highest Card on the pile
     */
    public Card nextCard(){
    if (hasMoreCards()){
        topIndex++;
        return cards[topIndex-1];
    }
    return null;
    }
    
    /**
     * @return size of deck in a string
     */
    public String toString(){
        return "Poker Deck with "+(cards.length-topIndex)+" cards";
    }
}
