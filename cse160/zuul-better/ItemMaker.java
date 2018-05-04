import java.util.Random;
/**
 * Write a description of class ItemMaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ItemMaker
{
    private Item[] itemList= {
        new Item("Cookie",0,"Eat this to increase your carrying capacity"),
        new Item("Brick",7,"This is probably dead weight. Or is it?"),
        new Item("Papers",1,"A useful staple for any student."),
        new Item("Torch",2,"No smoking indoors."),
        new Item("Excalibur",10,"All hail king of England, owner of the famed sword."),
        new Item("Pen",1,"Sweet.")
    };
    
    public ItemMaker(){
    }
    
    public Item generateItem(){
        Random rand = new Random();
        Item i = itemList[rand.nextInt(itemList.length)];
        return new Item(i.getName(), i.getWeight(), i.getDescription());
    }
}
