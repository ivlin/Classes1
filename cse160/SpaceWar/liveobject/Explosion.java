package liveobject;
import geometry.Segment2D;
import geometry.Vector2D;

import java.awt.Graphics;
import java.awt.Color;


import animation.Animator;

/**
 * An extension of LiveObject that implements an explosion.
 */

public class Explosion extends LiveObject {
    /**
     * Line segments making up the graphical representation of this object,
     * relative to its center at (0, 0).
     */
    private Segment2D[] segments = {
        new Segment2D(new Vector2D(0, 0), new Vector2D(0, 0.05)),
        new Segment2D(new Vector2D(0, 0), new Vector2D(0.05, 0)),
        new Segment2D(new Vector2D(0, 0), new Vector2D(0, -0.05)),
        new Segment2D(new Vector2D(0, 0), new Vector2D(-0.05, 0)),
        new Segment2D(new Vector2D(0, 0), new Vector2D(0.035355, 0.035355)),
        new Segment2D(new Vector2D(0, 0), new Vector2D(0.035355, -0.035355)),
        new Segment2D(new Vector2D(0, 0), new Vector2D(-0.035355, 0.035355)),
        new Segment2D(new Vector2D(0, 0), new Vector2D(-0.035355, -0.035355))
    };

    /**
     * Initialize an Explosion.
     *
     * @param a The animator that manages the update and display.
     * @param p The position of the explosion.
     */
    public Explosion(Animator a, Vector2D p) {
        super(a);
        position = p;
        setColor(Color.red);
        setLifetime(0.5);
    }

    /**
     * Method for drawing the explosion on the screen.
     *
     * @param g The graphics object to be used for drawing.
     */
    public void draw(Graphics g) {
        if(isVisible()) {
            g.setColor(getColor());
            drawSegments(g, segments);
        }
    }
}
