import java.util.Random;
import java.util.ArrayList;
/**
 * Write a description of class Dwarf here.
 * 
 * @author Ivan Lin
 * @version 2017.3.28
 */
public class Dwarf extends Monster
{
    /**
     * Constructor for objects of class Dwarf
     */
    public Dwarf(Room r)
    {
        super(r);
    }

    /**
     * performs all automatic actions
     */
    public void enactAction(){
        super.enactAction();
        steal();
    }

    /**
     * steals from any players in the room 
     */
    public void steal(){
        Random rand = new Random();
        ArrayList<Character> occupants = getRoom().getOccupants();
        Player c=null;
        for (Character occupant: occupants){
            if (occupant instanceof Player){
                c = (Player)occupant;
            }
        }
        if (c instanceof Player){
            ArrayList<Item> inventory = ((Player)c).getInventory();
            if (inventory.size()>0){
                Item i = inventory.remove(rand.nextInt(inventory.size()));
                System.out.println("Dwarf steals "+i.getName()+" from player and flees!");
                moveRandom();
            }
            else{
                System.out.println("Dwarf comes, but you've nothing for it to steal.");
            }
        }
    }
}
