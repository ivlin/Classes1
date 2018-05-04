package liveobject;

import geometry.Circle2D;
import geometry.Vector2D;
import geometry.Segment2D;

import animation.Animator;

import java.awt.Graphics;
import java.util.ArrayList;
/**
 * An extension of LiveObject that implements a star.
 */

public class Star extends GravitatingObject {

    /** Radius of a Star */
    private final double RADIUS = 0.1;
    private final double RAY_SIZE = 0.15;
    private final double NUM_RAYS = 12;
    
    /** Extent for a Star */
    private final Circle2D EXTENT = new Circle2D(new Vector2D(0, 0), RADIUS);
    
    /**
     * Initialize a Star.
     *
     * @param a The animator that manages the update and display.
     */
    public Star(Animator a) {
        super(a);
        setExtent(EXTENT);
    }

    /**
     * Method for drawing the star on the screen.
     *
     * @param g The graphics object to be used for drawing.
     */
    public void draw(Graphics g) {
        if(isVisible()) {
            g.setColor(getColor());
            animator.drawArc(g, position, RADIUS, 0, 360);
            Segment2D ray;
            for (int rot=0; rot<360; rot+=360/NUM_RAYS){
                ray = new Segment2D(new Vector2D(0,RADIUS),new Vector2D(0,RADIUS+RAY_SIZE+Math.random()/10));
                animator.drawSegment(g, ray.rotateBy(rot));
            }
        }
    }
    
    
    /**
     * Strength of pull this object exerts
     *
     * @returns the strength of the pull
     */
    public double gravityStrength(){
        return 1.0;
    }
}
