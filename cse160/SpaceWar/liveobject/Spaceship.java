package liveobject;
import animation.Keyboard;
import geometry.Circle2D;
import geometry.Orientation;
import geometry.Segment2D;
import geometry.Vector2D;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;

import animation.Animator;
import animation.KeyHandler;

/**
 * An extension of LiveObject that implements a spaceship with
 * rotation, thrusting, and torpedo-shooting capabilities.
 */

public class Spaceship extends GravitatingObject implements KeyHandler {

    /**
     * The rate of rotation, in degrees per second, when the rotate keys
     * are pressed.
     */
    private final double ROTATION_RATE = 120;

    /**
     * The strength of the thruster.
     */
    private final double THRUSTER_STRENGTH = 0.2;

    /** The speed of a torpedo, relative to the spaceship. */
    private final double TORPEDO_SPEED = 0.1;

    /** Distance in front of the ship center that a torpedo starts. */
    private final double TORPEDO_START = 0.1;

    /** How long it takes before a terminated spaceship is reincarnated. */
    private final double PURGATORY_TIME = 5.0;

    /** The source of keyboard input for control. */
    private Keyboard keyboard;

    /** Scoreboard to receive score if this spaceship is destroyed. */
    private Scoreboard scoreboard;

    /** The magnitude of the current thrust, in AU/sec*sec. */
    private double thrust = 0;

    /** Whether the spaceship has been destroyed */
    private boolean destroyed = false;

    /** Amount of time left until destroyed spaceship is reincarnated. */
    private double purgatory;

    /**
     * Line segments making up the graphical representation of this spaceship,
     * relative to its center at (0, 0).
     */
    private final Segment2D[] SEGMENTS = {
            new Segment2D(new Vector2D(-0.05, 0.05), new Vector2D(0.05, 0)),
            new Segment2D(new Vector2D(0.05, 0), new Vector2D(-0.05, -0.05)),
            new Segment2D(new Vector2D(-0.05, -0.05), new Vector2D(-0.05, 0.05))
        };

    private Segment2D[] segments = SEGMENTS;

    /**
     * Line segments making up the graphical representation of the
     * thrust when the thruster is active.
     */
    private Segment2D[] thruster = {
            new Segment2D(new Vector2D(-0.05, 0), new Vector2D(-0.1, 0))
        };

    /** Extent for a Spaceship */
    private final Circle2D EXTENT = new Circle2D(new Vector2D(0, 0), Math.sqrt(2*0.05*0.05));

    /** The initial position of the spaceship when it was created. */
    private Vector2D initialPosition;

    /** The initial velocity of the spaceship when it was created. */
    private Vector2D initialVelocity;

    /** The initial orientation of the spaceship when it was created. */
    private Orientation initialOrientation;

    /**
     * Initialize a Spaceship.
     *
     * @param a The animator that manages the update and display.
     * @param k The source of keyboard input for control.
     * @param cs Which set of keys are we taking control from?
     * (0 = ASDF, 1 = KL;').
     * @param sb Scoreboard to receive score for this spaceship.
     * @param p The initial position of the spaceship.
     * @param v The initial velocity of the spaceship.
     * @param o The initial orientation of the spaceship.
     */
    public Spaceship(Animator a, Keyboard k, int cs, Scoreboard sb,
    Vector2D p, Vector2D v, Orientation o) {
        super(a);
        keyboard = k;
        scoreboard = sb;
        position = initialPosition = p;
        velocity = initialVelocity = v;
        orientation = initialOrientation = o;
        switch(cs) {
            case 0:
            keyboard.addHandler(KeyEvent.VK_A, this);
            keyboard.addHandler(KeyEvent.VK_S, this);
            keyboard.addHandler(KeyEvent.VK_D, this);
            keyboard.addHandler(KeyEvent.VK_F, this);
            break;
            case 1:
            keyboard.addHandler(KeyEvent.VK_K, this);
            keyboard.addHandler(KeyEvent.VK_L, this);
            keyboard.addHandler(KeyEvent.VK_SEMICOLON, this);
            keyboard.addHandler(KeyEvent.VK_QUOTE, this);
            break;
        }
        setExtent(EXTENT);
    }

    /**
     * Method for drawing the spaceship on the screen.
     *
     * @param g The graphics object to be used for drawing.
     */
    public void draw(Graphics g) {
        if(isVisible()) {
            // Draw main body of spaceship
            g.setColor(getColor());
            drawSegments(g, segments);

            // If thruster is on, draw a thrust indication.
            if(thrust > 0) {
                g.setColor(Color.red);
                drawSegments(g, thruster);
            }
        }
    }

    /**
     * Method for performing one step of the physical simulation
     * associated with this object.
     *
     * @param dt The amount of time covered by the current time step.
     */
    public void update(double dt) {
        if(destroyed) {
            // A destroyed spaceship remains invisible and ephemeral
            // for a certain amount of time before it is reincarnated
            // in its initial state.
            purgatory -= dt;
            if(purgatory <= PURGATORY_TIME)
                setExtent(null);          // Make it ephemeral.
            if(purgatory <= 0.0) {        // Reincarnation!
                destroyed = false;
                position = initialPosition;
                velocity = initialVelocity;
                orientation = initialOrientation;
                segments = SEGMENTS;
                setExtent(EXTENT);
                setVisible(true);
            }
        } else {
            // Call the update method of the super class to update
            // the position and orientation and acceleration.
            Vector2D a = orientation.getDirection().scaleBy(thrust);
            setAcceleration(a);
            super.update(dt);
        }
    }

    /**
     * Calculate interaction between this spaceship and another live object.
     * 
     * @param other  The object to interact with.
     */
    public void interactWith(LiveObject other) {
        super.interactWith(other);
        if (position.length()>2.0){
            setDestroyed();
        }
        if(other instanceof Star
        || other instanceof Spaceship
        || other instanceof Torpedo) {
            // Check for collisions
            if(other != this && other.overlaps(this))
                setDestroyed();
        }
    }

    /**
     * Method for determining whether another object can interact
     * with this spaceship.  If this spaceship is destroyed, other
     * objects should not be able to interact with it.
     *
     * @return true if this object can interact with other objects,
     * and false otherwise.
     */
    public boolean canInteract() {
        return !isDestroyed();
    }

    /**
     * Determine whether this spaceship is destroyed.
     * A destroyed spaceship should not interact with other objects.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Cause this spaceship to be destroyed.
     * When a spaceship is destroyed, it disappears for a certain amount
     * of time and is then reincarnated in an initial state.
     */
    private void setDestroyed() {
        if(!destroyed) {
            destroyed = true;
            setVisible(false);
            purgatory = PURGATORY_TIME;
            scoreboard.increment();
        }
    }

    /**
     * Handle keyPressed event.
     *
     * @param keyCode The code for the key that was pressed.
     */
    public void keyPressed(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_K:
            /* Rotate counter-clockwise */
            rotation += ROTATION_RATE;
            break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_L:
            /* Rotate clockwise */
            rotation -= ROTATION_RATE;
            break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_SEMICOLON:
            /* Thrust */
            thrust += THRUSTER_STRENGTH;
            break;
            case KeyEvent.VK_F:
            case KeyEvent.VK_QUOTE:
            /* Fire torpedo */
            Vector2D d = orientation.getDirection();
            Torpedo t = new Torpedo(animator);
            t.setColor(getColor());
            t.setPosition(position.addTo(d.scaleBy(TORPEDO_START)));
            t.setOrientation(orientation);
            t.setVelocity(velocity.addTo(d.scaleBy(TORPEDO_SPEED)));
            break;
        }
    }

    /**
     * Handle keyReleased event.
     *
     * @param keyCode The code for the key that was released.
     */
    public void keyReleased(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_K:
            /* Stop counter-clockwise rotation */
            rotation -= ROTATION_RATE;
            break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_L:
            /* Stop clockwise rotation */
            rotation += ROTATION_RATE;
            break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_SEMICOLON:
            /* Thrust */
            thrust -= THRUSTER_STRENGTH;
            break;
            case KeyEvent.VK_F:
            case KeyEvent.VK_QUOTE:
            /* Fire torpedo */
            // No need to do anything, torpedo is already fired.
            break;
        }
    }

    /**
     * Magnitude of the effect of gravity on this object
     *
     * @returns the strength of the pull
     */
    @Override
    public double gravitySusceptability(){
        return 0.1;
    }
}
