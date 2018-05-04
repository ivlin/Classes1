import java.util.List;
/**
 * Write a description of class Hunter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hunter extends Being    
{
    private int SHOTS_PER_ROUND = 5;

    /**
     * Constructor for objects of class Hunter
     */
    public Hunter(Field field, Location location)
    {
        super(field, location);
    }

    public void act(List<Being> beings) {
        if (isAlive()){
            teleport();
            fire();
        }
    }

    public void fire(){
        Location l;
        for (int i=0; i<SHOTS_PER_ROUND; i++){
            l=getField().getRandomLocation();
            if (getField().getObjectAt(l)!=null &&getField().getObjectAt(l)!=this){
                ((Being)(getField().getObjectAt(l))).setDead();
            }
        }
    }

    public void teleport(){
        Location l = getField().getRandomLocation();
        while (getField().getObjectAt(l)!=null){
            l = getField().getRandomLocation();
        }
        setLocation(l);
    }
}
