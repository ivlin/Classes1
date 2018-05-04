/**
 * Write a description of class BoardPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
public class BoardPanel extends JPanel
{
    private FlippingBitsBoard board;
    private boolean isConfigurable;
    private ArrayList<Component> tiles;

    /**
     * Constructor for objects of class BoardPanel
     */
    public BoardPanel(FlippingBitsBoard board, boolean configurable, ActionListener listener)
    {
        super();
        this.board=board;
        this.setLayout(new GridLayout(board.getSize()+2, board.getSize()+2, 0, 0));
        isConfigurable = configurable;
        tiles = new ArrayList<>();
        loadLabels(listener);
    }

    public void loadLabels(ActionListener listener){
        ActionListener flip = (new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        FlipButton b = (FlipButton)e.getSource();
                        if (b.getRow() == 0 || b.getRow() == board.getSize()+1){
                            board.flipCol(b.getCol()-1);
                        }
                        else{
                            board.flipRow(b.getRow()-1);
                        }
                        for (int ri=1; ri<(board.getSize()+1); ri++){
                            for (int ci=1; ci<(board.getSize()+1); ci++){
                                ((JLabel)(tiles.get(ri*(2+board.getSize())+ci))).setText(""+board.getValue(ri-1,ci-1));
                            }
                        }
                    }
                });
        for (int r=0; r<board.getSize()+2; r++){
            for (int c=0; c<board.getSize()+2; c++){
                if (r==0||c==0||r>board.getSize()||c>board.getSize()){
                    if (isConfigurable && r!=c && r+c!=board.getSize()+1){
                        FlipButton b = new FlipButton(r,c);
                        b.addActionListener(listener);
                        b.addActionListener(flip);
                        this.add(b);
                        tiles.add(b);
                    }
                    else{
                        JLabel label = new JLabel();
                        tiles.add(label);
                        this.add(new JLabel());
                    }
                }
                else{
                    JLabel label = new JLabel(""+board.getValue(r-1,c-1), JLabel.CENTER);
                    tiles.add(label);
                    this.add(label);
                }
            }
        }
    }

}