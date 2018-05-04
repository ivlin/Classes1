import java.util.ArrayList;
/**
 * Write a description of class Ogre here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ogre extends Monster
{

    /**
     * Constructor for objects of class Ogre
     */
    public Ogre(Room r)
    {
        super(r);
    }
    
    /**
     * 
     */
    public void enactAction(){
        super.enactAction();
        attack(getRoom());
    }
    
    /**
     * saps health of all players in the room
     */
    public void attack(Room r){
        ArrayList<Character> occupants = r.getOccupants();
        for (Character c: occupants){
            if (c instanceof Player){
                c.setHealth(c.getHealth()-1);
                System.out.println("Player takes damage!");
            }
        }
    }
}
