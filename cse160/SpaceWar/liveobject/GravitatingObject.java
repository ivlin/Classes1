package liveobject;

import geometry.Vector2D;
import animation.Animator;

/**
 * Write a description of class GravitatingObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GravitatingObject extends LiveObject

{   // The gravitational constant. 
    private double gravityStrength;
    private double susceptability;

    /** Acceleration on this object due to gravity. */
    private Vector2D gravity;

    /**
     * Constructor for objects of class GravitatingObject
     */
    public GravitatingObject(Animator a){
        super(a); 
    }

    /**
     * Strength of pull this object exerts
     *
     * @returns the strength of the pull
     */
    public double gravityStrength(){
        return 0.0;
    }

    /**
     * Magnitude of the effect of gravity on this object
     *
     * @returns the strength of the pull
     */
    public double gravitySusceptability(){
        return 0.0;
    }

    /**
     * Calculate interaction between this spaceship and another live object.
     * 
     * @param other  The object to interact with.
     */
    public void interactWith(LiveObject other) {
        if(other != this && other instanceof GravitatingObject) {
            // Calculate acceleration due to gravity of star at the origin.
            double d = Math.abs(other.position.length()-position.length());                    // Distance to center of star.
            Vector2D u = position.scaleBy(-1.0/d);           // Unit vector toward center of star.
            gravity = u.scaleBy(((GravitatingObject)other).gravityStrength()*gravitySusceptability()/(d*d));// Acceleration due to gravity
            // (inverse-square law).
        }
    }

    /**
     * Method for performing one step of the physical simulation
     * associated with this object.
     *
     * @param dt The amount of time covered by the current time step.
     */
    public void update(double dt) {
        setAcceleration(getAcceleration().addTo(gravity));
        super.update(dt);
    }
}
