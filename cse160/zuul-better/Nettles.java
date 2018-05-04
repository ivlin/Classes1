import java.util.Random;
import java.util.ArrayList;
/**
 * Write a description of class Nettles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Nettles extends Monster
{
    /**
     * Constructor for objects of class Nettles
     */
    public Nettles(Room r)
    {
        super(r);
    }
    
    /**
     *  Performs actions unique to poisonous nettle: paralysis
     */
    @Override
    public void enactAction(){
        Room r = getRoom();
        Random rand = new Random();
        ArrayList<Character> occupants = r.getOccupants();
        for (Character o: occupants){
            if (o instanceof Player){
                int isPoisoned = rand.nextInt(2);
                ((Player)o).paralyze(isPoisoned);
                System.out.println("Attacked by nettles and paralyzed for an additional "+isPoisoned+" turns");
            }
        }
    }
}
