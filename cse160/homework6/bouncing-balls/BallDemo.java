import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
/**
 * Class BallDemo - a short demonstration showing animation with the 
 * Canvas class. 
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class BallDemo   
{
    private Canvas myCanvas;
    int collisions=0;
    /**
     * Create a BallDemo object. Creates a fresh canvas and makes it visible.
     */
    public BallDemo()
    {
        myCanvas = new Canvas("Ball Demo", 600, 500);
    }

    /**
     * Simulate two bouncing balls
     */
    public void bounce(int numBalls)
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.drawLine(50, ground, 550, ground);

        Random rand = new Random();
        ArrayList<BouncingBall> balls = new ArrayList<>();

        // crate and show the balls
        for (int i=0;i<numBalls;i++){//line runs from 50-550
            balls.add(new BouncingBall(50+rand.nextInt(500), 40, rand.nextInt(40)+1, 
                    new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat()),
                    ground,myCanvas));
        }
        // make them bounce
        boolean finished =  false;
        while(!finished) {
            myCanvas.wait(50);           // small delay

            for (BouncingBall b:balls){
                b.move();
                finished = finished || b.getXPosition()>=550;
            }
        }
    }

    /**
     * simulate balls bouncing in a box
     * @param numBalls the number of balls to generate
     */
    public void boxBounce(int numBalls)
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);
        // draw the ground
        Random rand = new Random();
        ArrayList<BouncingBall> balls = new ArrayList<>();

        for (int i=0;i<numBalls;i++){//line runs from 50-550
            balls.add(new BouncingBall(70+rand.nextInt(460), 40, rand.nextInt(35)+5, 
                    new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat()),
                    ground, 50, 550, myCanvas));
        }
        boolean finished =  false;
        Color black = new Color(0,0,0);
        while(!finished) {
            myCanvas.wait(50);           // small delay
            myCanvas.setForegroundColor(black);
            myCanvas.drawLine(50,50,50,ground);
            myCanvas.drawLine(50,ground,550,ground);
            myCanvas.drawLine(550,ground,550,50);
            myCanvas.drawLine(550,50,50,50);
            for (int i=0; i<balls.size(); i++){
                for (int c=i+1; c<balls.size(); c++){
                    simulateCollision(balls.get(i),balls.get(c));
                }
                balls.get(i).moveHorizontalBounce();
            }
        }
    }

    //collision calculation
    private void simulateCollision(BouncingBall a, BouncingBall b){
        double diffX = a.getXPosition()-b.getXPosition();
        double diffY = a.getYPosition()-b.getYPosition();
        double radiusSq = Math.pow(a.getRadius()+b.getRadius(),2);
        if (diffX*diffX+diffY*diffY<radiusSq){
            double diffXVel = a.getXVelocity()-b.getXVelocity();
            double diffYVel = a.getYVelocity()-b.getYVelocity();
            double k = ((diffXVel)*(-1*diffX)+(diffYVel)*(-1*diffY))/radiusSq;
            collisions++;
            a.setVelocity(a.getXVelocity()-k*(-1*diffX), a.getYVelocity()-k*(-1*diffY));;
            b.setVelocity(b.getXVelocity()-k*diffX, b.getYVelocity()-k*diffY);
        }
    }
}

