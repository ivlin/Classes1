
/**
 * Write a description of class FlippingBitsGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class FlippingBitsGame
{
    private BoardPanel finalView, boardView;
    private FlippingBitsBoard finalBoard, board;
    private JButton restart, resize;
    private JPanel content, buttons;
    private JFrame viewframe;
    private int size;
    private final int BOARD_WIDTH = 500;
    private final int BOARD_HEIGHT = 500;
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = BOARD_HEIGHT;

    /**
     * Constructor for objects of class FlippingBitsGame
     */
    public FlippingBitsGame(JFrame frame)
    {
        viewframe = frame;
        setupContent(5);

        //button listeners
    }

    public void setupContent(int boardSize){
        JPanel boards = setupBoards(boardSize);
        size = boardSize;
        //setting up east buttons
        buttons = new JPanel();

        restart = new JButton("New Game");
        restart.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));

        resize = new JButton("Change Size");
        resize.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));

        buttons.add(restart);
        buttons.add(resize);
        buttons.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));  

        JPanel content = new JPanel();

        content.setLayout(new BorderLayout());
        content.add(buttons,BorderLayout.WEST);

        content.add(boards,BorderLayout.CENTER);
        content.setPreferredSize(new Dimension(BUTTON_WIDTH+BOARD_WIDTH,BOARD_HEIGHT+BOARD_HEIGHT));

        ActionListener changeSizeListener =
            (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String input =
                            JOptionPane.showInputDialog("New size");
                        viewframe.remove(content);
                        viewframe.repaint(BUTTON_WIDTH, 0, (int)finalView.getSize().getWidth(), (int)(finalView.getSize().getHeight()+boardView.getSize().getHeight()));
                        setupContent(Integer.parseInt(input));
                    }
                });
        resize.addActionListener(changeSizeListener);
        ActionListener newGameListener = (
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        viewframe.remove(content);
                        viewframe.repaint(BUTTON_WIDTH, 0, (int)finalView.getSize().getWidth(), (int)(finalView.getSize().getHeight()+boardView.getSize().getHeight()));
                        setupContent(size);
                    }
                });
        restart.addActionListener(newGameListener);

        viewframe.getContentPane().add(content);
        viewframe.pack();
    }

    public JPanel setupBoards(int boardSize){
        ActionListener check = (new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if (board.equals(finalBoard)){
                            JOptionPane.showMessageDialog
                            (viewframe, "Success");
                        }
                    }
                });//create boards and associated views
        board = new FlippingBitsBoard(boardSize, true);
        boardView = new BoardPanel(board,true, check);

        finalBoard = new FlippingBitsBoard(boardSize, true);
        finalView = new BoardPanel(finalBoard,false, null);
        //setting up the boards
        boardView.setPreferredSize(new Dimension(BOARD_WIDTH,BOARD_HEIGHT));
        finalView.setPreferredSize(new Dimension(BOARD_WIDTH,BOARD_HEIGHT));
        JPanel boards = new JPanel();
        boards.setLayout(new BoxLayout(boards, BoxLayout.Y_AXIS));
        boards.add(new JLabel("Target Board", JLabel.CENTER));
        boards.add(finalView);
        boards.add(new JLabel("Current Board", JLabel.CENTER));
        boards.add(boardView);
        boards.setPreferredSize(new Dimension(
                (int)(boardView.getPreferredSize().getWidth()),
                (int)(boardView.getPreferredSize().getHeight()+finalView.getPreferredSize().getHeight())));

        return boards;
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("test");
        FlippingBitsGame g = new FlippingBitsGame(frame);
        frame.setVisible(true);
    }
}
