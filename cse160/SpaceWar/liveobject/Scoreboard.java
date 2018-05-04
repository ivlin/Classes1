package liveobject;
import geometry.Segment2D;
import geometry.Vector2D;

import java.awt.Graphics;
import java.awt.Color;


import animation.Animator;

/**
 * Objects of this class implement a numeric scoreboard display.
 */

public class Scoreboard extends LiveObject {
    
    /** The current value of the score */
    private int score = 0;
    
    /*
     * Line segments making up the graphical representation of this object,
     * relative to its center at (0, 0).
     */
    private Segment2D[] segments;

    /**
     * Initialize a Scoreboard
     *
     * @param a The animator that manages the update and display.
     * @param p The position of the score.
     */
    public Scoreboard(Animator a, Vector2D p) {
        super(a);
        setPosition(p);
        setColor(Color.black);
        segments = SEGMENTS[0];
    }

    /**
     * Method for drawing the scoreboard on the screen.
     *
     * @param g The graphics object to be used for drawing.
     */
    public void draw(Graphics g) {
        if(isVisible()) {
            g.setColor(getColor());
            drawSegments(g, segments);
        }
    }
    
    /**
     * Increment the score on this scoreboard.
     */
    public void increment() {
        score++;
        if(score >= 9) {
            animator.setDone();
        } else {
            segments = SEGMENTS[score];
        }
    }
    
    /**
     * "7-segment" numerals for score display.
     */
    private final Segment2D[][] SEGMENTS = {
        /* The numeral "0" */
        {
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(0.05,0.1)),
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(-0.05,0)),
            new Segment2D(new Vector2D(0.05,0.1), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(-0.05,-0.1)),
            new Segment2D(new Vector2D(0.05,0), new Vector2D(0.05,-0.1)),
            new Segment2D(new Vector2D(-0.05,-0.1), new Vector2D(0.05,-0.1)),
        },
        /* The numeral "1" */
        {
            new Segment2D(new Vector2D(0.05,0.1), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(0.05,0), new Vector2D(0.05,-0.1)),
        },
        /* The numeral "2" */
        {
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(0.05,0.1)),
            new Segment2D(new Vector2D(0.05,0.1), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(-0.05,-0.1)),
            new Segment2D(new Vector2D(-0.05,-0.1), new Vector2D(0.05,-0.1)),
        },
        /* The numeral "3" */
        {
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(0.05,0.1)),
            new Segment2D(new Vector2D(0.05,0.1), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(0.05,0), new Vector2D(0.05,-0.1)),
            new Segment2D(new Vector2D(-0.05,-0.1), new Vector2D(0.05,-0.1)),
        },
        /* The numeral "4" */
        {
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(-0.05,0)),
            new Segment2D(new Vector2D(0.05,0.1), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(0.05,0), new Vector2D(0.05,-0.1)),
        },
        /* The numeral "5" */
        {
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(0.05,0.1)),
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(-0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(0.05,0), new Vector2D(0.05,-0.1)),
            new Segment2D(new Vector2D(-0.05,-0.1), new Vector2D(0.05,-0.1)),
        },
        /* The numeral "6" */
        {
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(-0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(-0.05,-0.1)),
            new Segment2D(new Vector2D(0.05,0), new Vector2D(0.05,-0.1)),
            new Segment2D(new Vector2D(-0.05,-0.1), new Vector2D(0.05,-0.1)),
        },
        /* The numeral "7" */
        {
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(0.05,0.1)),
            new Segment2D(new Vector2D(0.05,0.1), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(0.05,0), new Vector2D(0.05,-0.1)),
        },
        /* The numeral "8" */
        {
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(0.05,0.1)),
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(-0.05,0)),
            new Segment2D(new Vector2D(0.05,0.1), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(-0.05,-0.1)),
            new Segment2D(new Vector2D(0.05,0), new Vector2D(0.05,-0.1)),
            new Segment2D(new Vector2D(-0.05,-0.1), new Vector2D(0.05,-0.1)),
        },
        /* The numeral "9" */
        {
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(0.05,0.1)),
            new Segment2D(new Vector2D(-0.05,0.1), new Vector2D(-0.05,0)),
            new Segment2D(new Vector2D(0.05,0.1), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(-0.05,0), new Vector2D(0.05,0)),
            new Segment2D(new Vector2D(0.05,0), new Vector2D(0.05,-0.1)),
            new Segment2D(new Vector2D(-0.05,-0.1), new Vector2D(0.05,-0.1)),
        }
    };
}
