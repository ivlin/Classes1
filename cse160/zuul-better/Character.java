
/**
 * Write a description of class Monster here.
 * 
 * @author Ivan Lin
 * @version 2017.3.28
 */
public class Character {
    private Room currentRoom;
    private int health;

    /**
     *  Consturctor for character objects
     */
    public Character(Room r){
        this(10, r);
    }

    public Character(int health, Room r){
        setHealth(health);
        setRoom(r);
    }

    
    public Character(int health){
        setHealth(health);
    }

    protected void setHealth(int health){
        this.health = health;
    }

    protected int getHealth(){
        return health;
    }

    /**
     *setRoom change the room the character is in
     *@param newRoom the new room the character should be in
     */
    public void setRoom(Room newRoom){
        if (currentRoom!=null){
            currentRoom.removeOccupant(this);
        }
        currentRoom = newRoom;
        currentRoom.addOccupant(this);
    }

    /**
     * getRoom returns the current room
     */
    public Room getRoom(){
        return currentRoom;
    }
    
    public String toString(){
        return this.getClass().getName()+ " ("+getHealth()+" HP)";
    }
}
