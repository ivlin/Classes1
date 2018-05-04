/**
 * Demonstration of how to open a window, place a menu item
 * and a button, and handle events from these user-interface objects.
 * 
 * @author E. Stark
 * @version Created for CSE 219 using AWT May 2001, updated to
 * Swing May 2002, updated for CSE 160 April 2005.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleWindow {
    private JPanel panel;

    /**
     * Given a JFrame, populate the frame with various components.
     * @param frame The frame to populate.
     */
    private void setup(final JFrame frame) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel);

        JMenuBar mb = new JMenuBar();
        frame.setJMenuBar(mb);

        JMenuItem quit = new JMenuItem("Quit");
        ActionListener quitListener =
            (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
        quit.addActionListener(quitListener);
        
        JMenuItem popup = new JMenuItem("Popup");
        ActionListener popupListener =
            (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String input =
                            JOptionPane.showInputDialog("Type something!");
                            JOptionPane.showMessageDialog
                                (frame, "You typed: '" + input + "'");
                    }
                });
        popup.addActionListener(popupListener);

        JMenu m = new JMenu("File");
        mb.add(m);
        m.add(quit);
        m.add(popup);

        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel, BorderLayout.NORTH);
        buttonPanel.setLayout(new GridLayout(1,2));

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(quitListener);
        buttonPanel.add(quitButton);
        
        JButton popupButton = new JButton("Popup");
        popupButton.addActionListener(popupListener);
        buttonPanel.add(popupButton);

        Picture picture = new Picture();
        panel.add(picture);

        frame.pack();
    }

    /**
     * Panel into which stuff can be drawn.
     */
    class Picture extends JPanel {
        public Picture() {
            super();
            setPreferredSize(new Dimension(500, 400));
        }

        /*
         * We override the default paintComponent method with a version
         * that draws what we want.
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);  // clear background
            g.setPaintMode();
            g.setColor(Color.green);
            g.drawLine(30, 40, 250, 110);
        }
    }

    /**
     * Entry point for the demo.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String [] args) {
        SimpleWindow win = new SimpleWindow();
        JFrame frame = new JFrame("Simple Window");
        win.setup(frame);
        frame.setVisible(true);
    }
}

