import java.util.Random;
/**
 * Write a description of class Monster here.
 * 
 * @author Ivan Lin 
 * @version 2017.3.28
 */
public class Monster extends Character
{
    /**
     * Constructor for objects of class Monster
     */
    public Monster(Room r)
    {
        super(r);
    }

    /**
     * performs all automated actions - moving between rooms
     */
    public void enactAction(){
        moveRandom();
    }
    
    /**
     * Randomly moves the monster to a room adjacent to the current one
     */
    public void moveRandom(){
        Random rand = new Random();
        Object[] exits = getRoom().getExits().toArray();
        setRoom((Room)exits[rand.nextInt(exits.length)]);
    }
}