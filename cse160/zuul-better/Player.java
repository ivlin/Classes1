import java.util.Collection;
import java.util.ArrayList;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Character
{
    private int maxWeight;
    private ArrayList<Item> items;
    private int paralyzeTurns;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room r)
    {
        this(25, r);
    }

    public Player(int maxWeight, Room r){
        super(r);
        this.maxWeight=maxWeight;
        items = new ArrayList<Item>();
    }

    /**
     * Adds the specified item to the player's backpack
     * @param item item to be added
     */
    public void take(String itemName){
        if (paralyzeTurns > 0){
            System.out.println("Can't do it! You're paralyzed. Try again with better luck!");
            paralyzeTurns--;
            if (paralyzeTurns==0){
                paralyzeTurns = -1;
                System.out.println("You regain feeling in your feet. Run before you're paralyzed again!");
            }
        }
        else{
            Item item = getRoom().getItem(itemName);
            if (item != null && item.getWeight()+getWeight()<= maxWeight){
                addItem(getRoom().removeItem(itemName));
                System.out.println("Picked up "+itemName);
            }
            else{
                System.out.println("Failed to pick it up. Perhaps you should lose some weight or make sure what you're looking for is actually in the room.");
            }
        }
    }

    /**
     * Removes an item with the specified name from the player's pack
     * @param String itemName name of the item
     */
    public void drop(String itemName){
        if (paralyzeTurns > 0){
            System.out.println("Can't do it! You're paralyzed. Try again with better luck!");
            paralyzeTurns--;
            if (paralyzeTurns==0){
                paralyzeTurns = -1;
                System.out.println("You regain feeling in your feet. Run before you're paralyzed again!");
            }
        }else{
            Item dropped=null;
            for (int i=0; i<items.size() && dropped==null; i++){
                if (items.get(i).getName().equalsIgnoreCase(itemName)){
                    dropped = items.get(i);
                    items.remove(i);
                    getRoom().addItem(dropped);
                    System.out.println("Dropped "+itemName);
                }
            }
        }
    }

    /**
     * Adds item to item lsit
     * @param item item to be added
     */
    public void addItem(Item item){
        items.add(item);
    }

    /**
     * gets total weight of items in pack
     */
    public int getWeight(){
        int weight=0;
        for (Item i:items){
            weight+=i.getWeight();
        }
        return weight;
    }

    /**
     * prints out all items
     */
    public void listItems(){
        System.out.println("Your inventory:");
        for (Item i: items){
            System.out.println(i);
        }
    }

    /**
     * eats any cookies in the room
     */
    public void eat(String itemName){
        if (paralyzeTurns > 0){
            System.out.println("Can't do it! You're paralyzed. Try again with better luck!");
            paralyzeTurns--;
            if (paralyzeTurns==0){
                paralyzeTurns = -1;
                System.out.println("You regain feeling in your feet. Run before you're paralyzed again!");
            }
        }
        else{
            Item i = getRoom().removeItem(itemName);
            if (i.getName().equals("Cookie")){
                maxWeight += 5;
                System.out.println("More cookies more power. You can now carry a max weight of "+maxWeight);
            }
        }
    }

    /**
     * returns the iventory
     */
    public ArrayList<Item> getInventory(){
        return items;
    }

    /**
     * changes the paralysis status
     */
    public void paralyze(int turns){
        paralyzeTurns+=turns;
    }

    @Override
    public void setRoom(Room newRoom){
        if (paralyzeTurns > 0){
            System.out.println("Can't do it! You're paralyzed. Try again with better luck!");
            paralyzeTurns--;
            if (paralyzeTurns==0){
                paralyzeTurns = -1;
                System.out.println("You regain feeling in your feet. Run before you're paralyzed again!");
            }
        }
        else{
            super.setRoom(newRoom);
        }
    }

    @Override
    public String toString(){
        if (paralyzeTurns > 0){
            return super.toString()+"(PARALYZED)";
        }
        else{
            return super.toString();
        }
    }
}
