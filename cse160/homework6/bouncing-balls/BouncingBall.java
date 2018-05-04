import java.awt.*;
import java.awt.geom.*;

/**
 * Class BouncingBall - a graphical ball that observes the effect of gravity. The ball
 * has the ability to move. Details of movement are determined by the ball itself. It
 * will fall downwards, accelerating with time due to the effect of gravity, and bounce
 * upward again when hitting the ground.
 *
 * This movement can be initiated by repeated calls to the "move" method.
 * 
 * @author Michael Kölling (mik)
 * @author David J. Barnes
 * @author Bruce Quig
 *
 * @version 2011.07.31
 */

public class BouncingBall
{
    private static final int GRAVITY = 3;  // effect of gravity

    private int ballDegradation = 2;
    private Ellipse2D.Double circle;
    private Color color;
    private int diameter;
    private double xPosition;
    private double yPosition;
    private final int groundPosition;      // y position of ground
    private final int leftWallPosition;
    private final int rightWallPosition;
    private Canvas canvas;
    private double ySpeed = 1;                // initial downward speed
    private double xSpeed = 2;

    /**
     * Constructor for objects of class BouncingBall
     *
     * @param xPos  the horizontal coordinate of the ball
     * @param yPos  the vertical coordinate of the ball
     * @param ballDiameter  the diameter (in pixels) of the ball
     * @param ballColor  the color of the ball
     * @param groundPos  the position of the ground (where the wall will bounce)
     * @param drawingCanvas  the canvas to draw this ball on
     */
    public BouncingBall(double xPos, double yPos, int ballDiameter, Color ballColor,
                        int groundPos, Canvas drawingCanvas)
    {
        this(xPos, yPos, ballDiameter, ballColor, groundPos, 0, (int)drawingCanvas.getSize().getWidth(), drawingCanvas);
    }
    
    public BouncingBall(double xPos, double yPos, int ballDiameter, Color ballColor,
                        int groundPos, int leftWall, int rightWall, Canvas drawingCanvas)
    {
        xPosition = xPos;
        yPosition = yPos;
        color = ballColor;
        diameter = ballDiameter;
        groundPosition = groundPos;
        leftWallPosition = leftWall;
        rightWallPosition = rightWall;
        canvas = drawingCanvas;
    }

    /**
     * Draw this ball at its current position onto the canvas.
     **/
    public void draw()
    {
        canvas.setForegroundColor(color);
        canvas.fillCircle((int)xPosition, (int)yPosition, diameter);
    }

    /**
     * Erase this ball at its current position.
     **/
    public void erase()
    {
        canvas.eraseCircle((int)xPosition, (int)yPosition, diameter);
    }    

    /**
     * Move this ball according to its position and speed and redraw.
     **/
    public void move()
    {
        // remove from canvas at the current position
        erase();
            
        // compute new position
        ySpeed += GRAVITY;
        yPosition += ySpeed;
        xPosition +=2;

        // check if it has hit the ground
        if(yPosition >= (groundPosition - diameter) && ySpeed > 0) {
            yPosition = (int)(groundPosition - diameter);
            ySpeed = -ySpeed + ballDegradation; 
        }
        // draw again at new position
        draw();
    }
    
    /**
     * Move this ball according to its position and speed and redraw. now calculates walls
     **/
    public void moveHorizontalBounce()
    {
        // remove from canvas at the current position
        erase();
        // compute new position
        ySpeed += GRAVITY;
        yPosition += ySpeed;
        xPosition += xSpeed;
        // check if it has hit the ground
        if(yPosition >= (groundPosition - diameter) && ySpeed > 0) {
            yPosition = (int)(groundPosition - diameter);
            ySpeed = -ySpeed + ballDegradation; 
        }
        if (xPosition <= (int)(leftWallPosition) || xPosition >= (int)(rightWallPosition-diameter)){
            xSpeed*=-1;
        }
        // draw again at new position
        draw();
    }    

    /**
     * return the horizontal position of this ball
     */
    public double getXPosition()
    {
        return xPosition+getRadius();
    }

    /**
     * return the vertical position of this ball
     */
    public double getYPosition()
    {
        return yPosition+getRadius();
    }
    
    /**
     *  return the horizontal velocity of this ball
     */
    public double getXVelocity(){
        return xSpeed;
    }
    
    /**
     *  return the vertical velocity of this ball
     */
    public double getYVelocity(){
        return ySpeed;
    }
    
    /**
     * return radius of ball
     */
    public double getRadius(){
        return (double)diameter/2;
    }
    
    /**
     * sets the velocity of the ball
     */
    public void setVelocity(double x, double y){
        xSpeed=x;
        ySpeed=y;
    }
}
