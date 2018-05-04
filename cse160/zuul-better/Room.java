import java.util.Set;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;
    private ArrayList<Character> occupants;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        ItemMaker i = new ItemMaker();
        items.add(i.generateItem());
        occupants=new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String desc = "You are " + description + ".\n";
        desc+="Items Here:";
        for (Item i: items){
            desc += "\n" + i;
        }
        desc +="\n"+ getExitString();
        return desc;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        returnString+="\n";
        for(Character c : occupants){
            returnString+=c.toString()+"\n";
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Adds an item to a room
     * @para item the item to be added
     */
    public void addItem(Item item){
        items.add(item);
    }

    /**
     * returns an item of the given name if it is in the room
     */
    public Item getItem(String itemName){

        for (Item i:items){
            if (i.getName().equalsIgnoreCase(itemName)){
                return i;
            }
        }
        return null;
    }

    /**
     * removes and returns the item
     * @param itemName name of the item
     */
    public Item removeItem(String itemName){
        Item dropped=null;

        for (int i=0; i<items.size() && dropped==null; i++){
            if (items.get(i).getName().equalsIgnoreCase(itemName)){
                dropped = items.get(i);
                items.remove(i);
            }
        }
        return dropped;
    }

    /**
     * gets a list of all the exits
     */
    public Collection<Room> getExits(){
        return exits.values();
    }

    /**
     * returns the room's occupants
     */
    public ArrayList<Character> getOccupants(){
        return occupants;
    }

    /**
     * adds occupant to the room
     * @param newOccupant the new occupant to be added
     */
    public void addOccupant(Character newOccupant){
        occupants.add(newOccupant);
    }

    /**
     * removes the specified occupant fromt he room
     * @param occupant the occupant to be removed
     */
    public void removeOccupant(Character occupant){
        occupants.remove(occupant);
    }
}
