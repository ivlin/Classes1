package animation;

import geometry.Segment2D;
import geometry.Vector2D;

import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import liveobject.LiveObject;

/**
 * An animator is responsible for driving the physical simulation of the
 * motion of a collection of live objects, and for keeping the screen display
 * up-to-date.
 */

public class Animator extends JPanel implements ActionListener {

    /** Table of live objects currently being managed and displayed. */
    private ArrayList<LiveObject> liveObjects = new ArrayList<LiveObject>();

    /**
     * Objects that have been added since the last frame.
     * These are added before processing the next frame, so as
     * to avoid modifying the list of all live objects while we are
     * iterating over them.
     */
    private ArrayList<LiveObject> objectsToAdd = new ArrayList<LiveObject>();
    
    /**
     * Objects that have been deleted since the last frame.
     * The purpose is the same as for objectsToAdd.
     */
    private ArrayList<LiveObject> objectsToDelete = new ArrayList<LiveObject>();

    /**
     * Interval at which the display is updated, in Hz.
     */
    private double frameRate;

    /**
     * Number of steps of the physical simulation executed between
     * each frame.
     */
    private int updatesPerFrame;

    /**
     * Current screen resolution, in pixels per AU.
     */
    private double resolution = 250;

    /** Flag indicating whether the game is over or not. */
    private boolean done = false;
    
    /** Stroke object to control line thickness in drawing. */
    private final BasicStroke stroke = new BasicStroke(3);

    /**
     * Initialize an Animator.
     *
     * @param hz The frame rate in Hz.
     */
    public Animator(double hz) {
        frameRate = hz;
        updatesPerFrame = (int)(100/frameRate);
    }

    /**
     * Accessor for getting the frame rate.
     *
     * @return the frame rate.
     */
    public double getFrameRate() {
        return(frameRate);
    }
    
    /**
     * Accessor for getting the time per frame.
     * 
     * @return the time per frame, in seconds.
     */
    public double getFrameTime() {
        return(1.0/getFrameRate());
    }

    /**
     * Mutator for setting the update rate for the physical calculations.
     *
     * @param n The number of steps of the physical simulation per frame.
     */
    public void setUpdatesPerFrame(int n) {
        updatesPerFrame = n;
    }

    /**
     * Mutator for setting the number of updates per frame.
     *
     * @param hz The number of frames per second.
     */
    public void setFrameRate(double hz) {
        frameRate = hz;
    }

    /**
     * Register an object to be managed and displayed by the animator.
     *
     * @param object The object to be registered.
     */
    public void addLiveObject(LiveObject object) {
        objectsToAdd.add(object);
    }

    /**
     * Routine called back by the animation timer to let us know that
     * it is time to update the simulation.
     */
    public void actionPerformed(ActionEvent e) {
        doFrame();
    }

    /**
     * Method for redrawing the display.
     *
     * @param g The graphics context used               dispose(true);
 for drawing.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the window first.
        for(LiveObject object : liveObjects) {
            object.draw(g);
        }
    }

    /**
     * Iterator for enumerating all live objects currently being managed.
     * This is useful, for example, so that an object can check whether
     * it has collided with another object.
     *
     * @return An enumeration object to generate all the live objects.
     */
    public Iterator<LiveObject> allLiveObjects() {
        return(liveObjects.iterator());
    }

    /**
     * Obtain the current screen resolution.
     *
     * @return the current resolution in pixels per AU.
     */
    public double getResolution() {
        return(resolution);
    }

    /**
     * Method used by the lower levels to notify us that the game
     * has terminated.
     */
    public void setDone() {
        done = true;
    }
    
    /**
     * Utility method for converting model to pixel coordinates,
     * according to the current display panel size and resolution.
     *
     * @param vec 2D vector to be converted to pixel coordinates.
     * @return a 2D vector giving the corresponding pixel coordinates.
     */
    public Vector2D toPixels(Vector2D vec) {
        double res = getResolution();
        int width = getWidth();
        int height = getHeight();
        return(new Vector2D(res*vec.getX() + width/2,
                            -res*vec.getY() + height/2));
    }

    /**
     * Utility method for converting model to pixel coordinates,
     * according to the current display panel size and resolution.
     *
     * @param seg 2D segment to be converted to pixel coordinates.
     * @return a 2D segment giving the corresponding pixel coordinates.
     */
    public Segment2D toPixels(Segment2D seg) {
        return(new Segment2D(toPixels(seg.getFrom()),
                             toPixels(seg.getTo())));
    }
    
    /**
     * Utility method for drawing a line segment according to the
     * current screen size and resolution.
     * 
     * @param g Graphics context in which to draw.
     * @param seg 2D segment to be drawn.
     */
    public void drawSegment(Graphics g, Segment2D seg) {
        if(g instanceof Graphics2D)
            ((Graphics2D)g).setStroke(stroke);
        seg = toPixels(seg);
        g.drawLine((int)seg.getFrom().getX(), (int)seg.getFrom().getY(),
                   (int)seg.getTo().getX(), (int)seg.getTo().getY());
    }
    
    /**
     * Utility method for drawing a circular arc according to the current
     * screen size and resolution.
     * 
     * @param g Graphics context in which to draw.
     * @param position  Position of the center of the arc.
     * @param radius  Radius of the arc.
     * @param startAngle  Starting angle in degrees.
     * @param stopAngle  Stopping angle in degrees.
     */
    public void drawArc(Graphics g, Vector2D position, double radius,
                        double startAngle, double stopAngle) {
        double res = getResolution();
        Vector2D home = toPixels((new Vector2D(-radius, radius)).addTo(position));
        Vector2D size = (new Vector2D(2*radius, 2*radius)).scaleBy(res);
        g.drawArc((int)home.getX(), (int)home.getY(),
                  (int)size.getX(), (int)size.getY(), (int)startAngle, (int)stopAngle);
    }
    
    /**
     * Main animation routine.  Called to periodically perform physical
     * simulation calculations and arrange for the display to be updated.
     */
    private void doFrame() {
        if(!done) {
            // We have to do updatesPerFrame steps of the simulation.
            for(int i = 0; i < updatesPerFrame; i++) {
                // Take care of live objects added or deleted since the last frame.
                liveObjects.addAll(objectsToAdd);
                objectsToAdd.clear();
                liveObjects.removeAll(objectsToDelete);
                objectsToDelete.clear();
                
                // Carry out interactions between objects.
                for(LiveObject object : liveObjects) {
                    for(LiveObject other: liveObjects) {
                        if(other.canInteract())
                            object.interactWith(other);
                    }
                }
            
                // Now update each live object.
                for(LiveObject object : liveObjects) {
                    object.update(1/(frameRate*updatesPerFrame));
                    if(object.isTerminated())
                        objectsToDelete.add(object);
                }
            }
            repaint();
        }
    }
}
