package liveobject;

import geometry.Circle2D;
import geometry.Orientation;
import geometry.Segment2D;
import geometry.Vector2D;

import animation.Animator;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Base class that implements a drawable object having a position,
 * velocity, acceleration, orientation, and rotation in the universe.
 */
public class LiveObject {
    
    /** Position of the object. */
    protected Vector2D position = new Vector2D(0, 0);

    /** Velocity of the object. */
    protected Vector2D velocity = new Vector2D(0, 0);
    
    /** Acceleration of the object. */
    protected Vector2D acceleration = new Vector2D(0, 0);

    /** The current orientation of the object. */
    protected Orientation orientation = new Orientation(0);

    /** The current rotational speed of the object, in degrees per second. */
    protected double rotation = 0.0;

    /**
     *  "Extent" of the object.  If this is non-null, then it is a circle
     *  that defines when another object will collide with this object.
     *  Two objects collide if their extents, translated to their current
     *  positions, overlap.
     */
    private Circle2D extent;

    /**
     * The remaining lifetime of this object.
     */
    private double lifetime = Double.POSITIVE_INFINITY;
    
    /**
     * Whether this object has terminated.
     */
    private boolean terminated = false;
    
    /**
     * Whether this object is currently visible.
     */
    private boolean visible = true;

    /** The color in which to display the object. */
    private Color color = Color.black;

    /** The animator that manages our motion and redisplay. */
    protected final Animator animator;

    /**
     * Initialize a LiveObject.
     *
     * @param anim The animator to register with.
     */
    protected LiveObject(Animator anim) {
        animator = anim;
        animator.addLiveObject(this);
    }

    /**
     * Method for performing one step of the physical simulation
     * associated with this object.  Subclasses may override to
     * add additional calculations.
     *
     * @param dt The amount of time covered by the current time step.
     */
    public void update(double dt) {
        lifetime -= dt;
        if(lifetime < 0)
            setTerminated();
        velocity = velocity.addTo(acceleration.scaleBy(dt));
        position = position.addTo(velocity.scaleBy(dt));
        orientation = orientation.rotateBy(rotation * dt);
    }
    
    /**
     * Method for handling an interaction with another live object.
     * Subclasses that are capable of interaction will override this.
     * 
     * @param other  The other live object to interact with.
     */
    public void interactWith(LiveObject other) {
        // By default, no interaction.
    }

    /**
     * Method for determining whether another object can interact
     * with this object.  Subclasses may override this if there
     * are certain situations in which they do not interact with
     * other objects.
     *
     * @return true if this object can interact with other objects,
     * and false otherwise.
     */
    public boolean canInteract() {
        return true;
    }

    /**
     * Method for drawing the object onto the display window.
     * Subclasses will override this to draw themselves appropriately.
     *
     * @param g The graphics context to be used for drawing.
     */
    public void draw(Graphics g) {
        // By default, no drawing.
    }

    /**
     * Mutator to set the position vector for the object.
     *
     * @param pos The vector to set as the position.
     */
    public void setPosition(Vector2D pos) {
        position = pos;
    }

    /**
     * Mutator to set the velocity vector for the object.
     *
     * @param vel The vector to set as the velocity.
     */
    public void setVelocity(Vector2D vel) {
        velocity = vel;
    }

    /**
     * Mutator to set the acceleration vector for the object.
     *
     * @param vel The vector to set as the acceleration.
     */
    public void setAcceleration(Vector2D acc) {
        acceleration = acc;
    }

   /**
     * Mutator to set the orientation for the object.
     *
     * @param orient The orientation to set.
     */
    public void setOrientation(Orientation orient) {
        orientation = orient;
    }

    /**
     * Mutator to set the rotational speed for the object.
     *
     * @param rot The rotational speed to set, in degrees per second.
     */
    public void setRotation(double rot) {
        rotation = rot;
    }

    /**
     * Mutator to set the color for the object.
     *
     * @param col The color to be used when displaying the object.
     */
    public void setColor(Color col) {
        color = col;
    }

    /**
     * Mutator to set the lifetime for the object.
     *
     * @param t The remaining lifetime of the object.
     */
    public void setLifetime(double t) {
        lifetime = t;
    }
    
    /**
     * Mutator to set the visibility for the object.
     * 
     * @param b true if the object is to be visible, false if not.
     */
    public void setVisible(boolean b) {
        visible = b;
    }

    /**
     * Accessor for getting the current position of the object.
     *
     * @return The current position of the object.
     */
    public Vector2D getPosition() {
        return(position);
    }

    /**
     * Accessor for getting the current velocity of the object.
     *
     * @return The current velocity of the object.
     */
    public Vector2D getVelocity() {
        return(velocity);
    }

    /**
     * Accessor for getting the current acceleration of the object.
     *
     * @return The current acceleration of the object.
     */
    public Vector2D getAcceleration() {
        return(acceleration);
    }

    /**
     * Accessor for getting the current orientation of the object.
     *
     * @return The current orientation of the object.
     */
    public Orientation getOrientation() {
        return(orientation);
    }
    
    /**
     * Accessor for getting the current rotational speed of the object.
     * 
     * @return The current rotational speed of the object.
     */
    public double getRotation() {
        return rotation;
    }
    
    /**
     * Accessor for getting the current color of the object.
     * 
     * @return The current color of the object.
     */
    public Color getColor() {
        return(color);
    }

    /**
     * Accessor for getting the current lifetime of the object.
     *
     * @return The current lifetime of the object.
     */
    public double getLifetime() {
        return(lifetime);
    }
    
    /**
     * Accessor for determining if this object has terminated.
     * 
     * @return true if this object has terminated, false otherwise.
     */
    public boolean isTerminated() {
        return terminated;
    }
    
    /**
     * Accessor for determining if this object is visible.
     * 
     * @return true if this object is visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Flag this object as having terminated.
     * Subclasses should override this method if they need to take other
     * actions.
     */
    void setTerminated() {
        terminated = true;
    }

    /**
     * Utility method for drawing a collection of segments.
     * Most subclasses will use this to draw themselves.
     *
     * @param g Graphics context to use for drawing.
     * @param segments Enumeration to generate the segments to draw.
     */
    public void drawSegments(Graphics g, Segment2D[] segments) {
        for(int i = 0; i < segments.length; i++) {
            Segment2D seg = segments[i].rotateBy(orientation.getAngle());
            seg = seg.translateBy(position);
            animator.drawSegment(g, seg);
        }
    }
    
    /**
     * Mutator for setting the extent of this object.
     * 
     * @param extent  The extent this object will have if it is
     * currently located at the origin.
     */
    public void setExtent(Circle2D extent) {
        this.extent = extent;
    }
    
    /**
     * Accessor for getting the current extent of this object.
     *
     * @return  the current extent of this object, if this object
     * has an extent, otherwise null.
     */
    private Circle2D getCurrentExtent() {
        if(extent == null)
            return null;
        else
            return extent.translateBy(position);
    }

    /**
     * Method for checking whether this object overlaps another object.
     * That occurs when both objects have non-null extents, and the extent of
     * this object overlaps the extent of the other object.
     *
     * @param other The object to be checked for overlap with this object.
     * @return true if the argument object overlaps this object,
     * and false otherwise.
     */
    public boolean overlaps(LiveObject other) {
        Circle2D thisExtent = getCurrentExtent();
        Circle2D otherExtent = other.getCurrentExtent();
        return thisExtent != null && otherExtent != null
                && thisExtent.overlapsWith(otherExtent);
    }
}
