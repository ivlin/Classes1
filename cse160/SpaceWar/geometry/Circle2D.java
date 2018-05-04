package geometry;

/**
 * Defines a circle in 2D space by giving its center and radius.
 */
public class Circle2D {

    /** The center of the circle. */
    private Vector2D center;

    /** The radius of the circle. */
    private double radius;

    /**
     * Initialize a circle with a specified center and radius.
     *
     * @param cen The center of the circle.
     * @param rad The radius of the circle.
     */
    public Circle2D(Vector2D cen, double rad) {
        center = cen;
        radius = rad;
    }

    /**
     * Translate this circle by a specified vector, returning a new circle.
     *
     * @param vector The vector to translate by.
     * @return A new circle obtained by translating this circle by the
     * specified vector.
     */
    public Circle2D translateBy(Vector2D vector) {
        return(new Circle2D(center.addTo(vector), radius));
    }

    /**
     * Determine if this circle overlaps with a specified circle.
     *
     * @param circle The circle to be checked for overlap with this circle.
     * @return true if the argument circle overlaps with this circle,
     * otherwise false.
     */
    public boolean overlapsWith(Circle2D circle) {
        return((center.subtractFrom(circle.center)).length()
               < radius + circle.radius);
    }

    /**
     * Create a string representation of this circle.
     *
     * @return a string representation of the circle.
     */
    public String toString() {
        return("Circle(" + center + "," + radius + ")");
    }
}

