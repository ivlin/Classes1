
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;
    private int weight;
    private String description;
    
    public Item(String name, int weight, String description)
    {
        this.name=name;
        this.weight=weight;
        this.description=description;
    }
    
    //setters
    public String getName(){
        return name;
    }
    
    public int getWeight(){
        return weight;
    }
    
    public String getDescription(){
        return description;
    }
    
    public String toString(){
        return getName() + "\n\tWeight:" + getWeight() + "\n\tDescription:"+getDescription();
    }
}
