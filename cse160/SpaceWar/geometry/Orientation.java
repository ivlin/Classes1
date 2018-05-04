package geometry;

/**
 * Represents an orientation in 2D space.
 */

public class Orientation {
    /**
     * The orientation represented as an angle from -180 to 180 degrees.
     * Zero degrees represents an orientation in the direction of the
     * x coordinate axis.
     */
    private double degrees;

    /** The orientation represented as a unit vector. */
    private Vector2D vector;

    /**
     * Construct an orientation given an angle in degrees.
     *
     * @param deg The angle in degrees, where 0 represents an orientation
     * in the direction of the x coordinate axis.
     */
    public Orientation(double deg) {
        this.degrees = deg;
        vector = (new Vector2D(1,0)).rotateBy(deg);
    }

    /**
     * Construct an orientation given a vector that points in the
     * direction of the orientation.
     *
     * @param vector A vector that points in the direction of the
     * orientation.  This vector must have nonzero length.
     */
    public Orientation(Vector2D vector) {
        this.vector = vector.scaleBy(1/vector.length());
        if(vector.getX() == 0) {
            if(vector.getY() > 0)
                degrees = 90;
            else
                degrees = -90;
        } else {
            double a = Math.atan(vector.getY()/vector.getX());
            degrees = a * (180/Math.PI);
        }
    }

    /**
     * Return the angle associated with this orientation.
     *
     * @return The angle associated with this orientation.
     */
    public double getAngle() {
        return(degrees);
    }

    /**
     * Return the unit vector associated with this orientation.
     *
     * @return The unit vector associated with this orientation.
     */
    public Vector2D getDirection() {
        return(vector);
    }

    /**
     * Rotate an orientation by a specified increment in degrees,
     * returning the new orientation.
     *
     * @param deg The number of degrees to rotate by.
     * @return a new orientation obtained by rotating this orientation
     * by the specified number of degrees.
     */
    public Orientation rotateBy(double deg) {
        return(new Orientation(this.degrees + deg));
    }

    /**
     * Create a string representation of this orientation.
     *
     * @return a string representation of the orientation.
     */
    public String toString() {
        return("Orientation(" + degrees + " deg");
    }
}
