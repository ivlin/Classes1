import geometry.Orientation;
import geometry.Vector2D;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.Timer;

import liveobject.LiveObject;
import liveobject.Scoreboard;
import liveobject.Spaceship;
import liveobject.Star;

import animation.Animator;
import animation.Keyboard;

/**
 * SpaceWar game: Main class.
 */

public class Main {
    /** The animator that manages the display. */
    private Animator animator;

    /** Default width of the display area. */
    private final int WIDTH = 500;

    /** Default height of the display area. */
    private final int HEIGHT = 500;

    /** The main frame. */
    private JFrame frame;
    
    /** Timer to drive the animation. */
    private Timer timer;

    /**
     * Entry point for the SpaceWar program.
     * Initialize the animator and main frame, then start the animation timer.
     *
     * @param args Command line arguments.  If the first
     * argument is present, it is interpreted as an integer frame rate in Hz.
     */
    public static void main(String[] args) {
        int frameRate = 15;
        if(args.length > 0) {
            try {
                frameRate = Integer.parseInt(args[0]);
            } catch(NumberFormatException x) {
                System.out.println
                    ("Argument must be an integer frame rate in Hz");
                System.exit(1);
            }
        }
        new Main(frameRate);
    }

    /**
     * Initialize the main frame.
     *
     * @param frameRate The frame rate in Hz.
     */
    private Main(int frameRate) {
        frame = new JFrame();
        frame.setTitle("Space War!");
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        animator = new Animator(frameRate);
        animator.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.getContentPane().add(animator);

        Scoreboard scoreboard1 = new Scoreboard(animator, new Vector2D(-0.8, 0.8));
        scoreboard1.setColor(Color.blue);
        Scoreboard scoreboard2 = new Scoreboard(animator, new Vector2D(0.8, 0.8));
        scoreboard2.setColor(Color.green);
        Keyboard keyboard = new Keyboard();
        frame.addKeyListener(keyboard);
        LiveObject ship1 = new Spaceship(animator, keyboard, 0, scoreboard2,
                                         new Vector2D(-0.5, 0),
                                         new Vector2D(0, 0.4),
                                         new Orientation(90));
        ship1.setColor(Color.blue);
        LiveObject ship2 = new Spaceship(animator, keyboard, 1, scoreboard1,
                                         new Vector2D(0.5, 0),
                                         new Vector2D(0, -0.4),
                                         new Orientation(-90));
        ship2.setColor(Color.green);
        LiveObject star = new Star(animator);
        star.setColor(Color.yellow);
        frame.pack();
        frame.setVisible(true);
        timer = new Timer((int)(1000.0/frameRate), animator);
        timer.start();
    }
}
