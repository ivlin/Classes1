
/**
 * Write a description of class FlipButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.JButton;

public class FlipButton extends JButton
{
    private int row, col;

    public FlipButton(int r, int c){
        row=r;
        col=c;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}
