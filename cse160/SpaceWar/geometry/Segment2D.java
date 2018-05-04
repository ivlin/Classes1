package geometry;

/**
 * Defines a line segment in 2D space by giving its starting and ending
 * positions.
 */
public class Segment2D {
    /** The starting position of the segment. */
    private Vector2D from;

    /** The ending position of the segment. */
    private Vector2D to;

    /**
     * Initialize a segment given it starting and ending positions.
     *
     * @param f The starting position.
     * @param t The ending position.
     */
    public Segment2D(Vector2D f, Vector2D t) {
        from = f;
        to = t;
    }

    /**
     * Accessor for getting the starting position of this segment.
     *
     * @return The starting position of this segment.
     */
    public Vector2D getFrom() {
        return(from);
    }
    
    /**
     * Accessor for getting the ending position of this segment.
     *
     * @return The ending position of this segment.
     */
    public Vector2D getTo() {
        return(to);
    }

    /**
     * Translate this segment by a specified vector, returning a new segment.
     *
     * @param vector The vector to translate by.
     * @return A new segment obtained by translating this segment by the
     * specified vector.
     */
    public Segment2D translateBy(Vector2D vector) {
        return(new Segment2D(from.addTo(vector), to.addTo(vector)));
    }

    /**
     * Rotate this segment by a specified angle, returning a new segment.
     *
     * @param angle The angle by which to rotate (positive is
     * counterclockwise, negative is clockwise).
     * @return A new segment obtained by rotating this segment by the
     * specified angle.
     */
    public Segment2D rotateBy(double angle) {
        return(new Segment2D(from.rotateBy(angle), to.rotateBy(angle)));
    }
}
