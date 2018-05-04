package geometry;
/**
 * Objects of this class represent two-dimensional mathematical vectors.
 */

public class Vector2D {
    /** The x-coordinate of the vector. */
    private double x;

    /** The y-coordinate of the vector. */
    private double y;

    /**
     * Construct a vector given an x and y coordinate.
     *
     * @param x The x coordinate of the vector.
     * @param y The y coordinate of the vector.
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x coordinate of a vector.
     *
     * @return The x coordinate of the vector.
     */
    public double getX() {
        return(x);
    }

    /**
     * Get the y coordinate of a vector.
     *
     * @return The y coordinate of the vector.
     */
    public double getY() {
        return(y);
    }

    /**
     * Obtain the length of a vector.
     *
     * @return The length of the vector.
    */
    public double length() {
        return(Math.sqrt(x*x+y*y));
    }

    /**
     * Scale a vector by a scalar.
     *
     * @param scale The scaling factor to apply to the vector.
     * @return A new vector obtained by scaling this vector by
     * the given factor.
     */
    public Vector2D scaleBy(double scale) {
        return(new Vector2D(scale*x, scale*y));
    }

    /**
     * Add another vector to this vector.
     *
     * @param v The vector to add to this vector.
     * @return A new vector obtained by adding the vector v to this vector.
     */
    public Vector2D addTo(Vector2D v) {
        return(new Vector2D(x+v.getX(), y+v.getY()));
    }

    /**
     * Subtract this vector from another vector.
     *
     * @param v The vector from which to subtract this vector.
     * @return A new vector obtained by subtracting this vector from v.
     */
    public Vector2D subtractFrom(Vector2D v) {
        return(new Vector2D(v.getX()-x, v.getY()-y));
    }

    /**
     * Rotate this vector by a specified angle, returning a new vector.
     *
     * @param angle The angle by which to rotate (positive is
     * counterclockwise, negative is clockwise).
     * @return A new vector obtained by rotating this vector by the
     * specified angle.
     */
    public Vector2D rotateBy(double angle) {
        double a = angle * (Math.PI/180);
        double sina = Math.sin(a);
        double cosa = Math.cos(a);
        return(new Vector2D(x*cosa - y*sina, x*sina + y*cosa));
    }

    /**
     * Create a string representation of this vector.
     *
     * @return a string representation of the vector.
     */
    public String toString() {
        return("Vector(" + x + "," + y + ")");
    }
}
