package client.sprites;
import javax.swing.*;

/**
 * Extends sprite, modelsthe ball
 */
public class Ball extends Sprite {
    
    private java.lang.Integer xdir;
    private java.lang.Integer ydir;
    private java.lang.Integer speed;

    /**
     * Constructor
     */
    public Ball() {

        initBall();
    }

    /**
     * initiates the ball with its direction, speed, image and position
     */
    private void initBall() {

        xdir = 1;
        ydir = -1;
        speed = 1;
        loadImage();
        getImageDimensions();
        resetState();
    }


    /**
     * Loads the designated image into the ball
     */
    private void loadImage() {

        var ii = new ImageIcon("breakout/client/sprites/resources/ball.png");
        image = ii.getImage();
    }

    /**
     * Decreases or increase the ball's speed
     * @param ii is True if speed needs to increse, and False if speed needs to decrease
     */
    void speed(java.lang.Boolean ii){
        if(ii){
            if(speed>3){
                speed = 3;
            }
            else{
            speed+= speed;
            }
        }
        else{
            if(speed<2){
                speed = 1;
            }
            else{
                this.speed-= speed;
            }
        }
    }

    /**
     * Handles the balls position
     */
    void move() {

        x += xdir*speed;
        y += ydir*speed;

        if (x == 0) {

            setXDir(1);
        }

        if (x == Constants.WIDTH - imageWidth) {

            System.out.println(imageWidth);
            setXDir(-1);
        }

        if (y == 0) {

            setYDir(1);
        }
    }


    /**
     * Sets the ball into the default position
     */
    private void resetState() {

        x = Constants.INIT_BALL_X;
        y = Constants.INIT_BALL_Y;
    }


    /**
     * sets x direction
     * @param x
     */
    void setXDir(java.lang.Integer x) {

        xdir = x;
    }

    /**
     * sets y direction
     * @param y
     */
    void setYDir(java.lang.Integer y) {

        ydir = y;
    }

    /**
     * 
     * @return y direction
     */
    java.lang.Integer getYDir() {

        return ydir;
    }
}

