package client.sprites;
import java.awt.event.*;
import javax.swing.*;

/**
 * Paddle extends the class Sprite
 * It modeles the paddle that the player controls during gamplay
 */
public class Paddle extends Sprite  {

    private java.lang.Integer dx;
    private static Paddle paddle = null;

    /**
     * Constructor
     */
    private Paddle() {

        initPaddle();
    }

    /**
     * singleton instantiations
     * @return 
     */
    public static Paddle getInstancPaddle()
    {
        if(paddle == null)
            paddle = new Paddle();

        return paddle;
    }

    /**
     * Ititializes the paddle
     */
    private void initPaddle() {
        dx = 0;
        loadImage();
        getImageDimensions();

        resetState();
    }

    /**
     * Loads the image to the paddle
     */
    private void loadImage() {

        var ii = new ImageIcon("breakout/client/sprites/resources/paddle.png");
        image = ii.getImage();
        
    }

    /**
     * handles the events of key strokes to change the padddle's postiion
     */
    void move() {

        x += dx;

        if (x <= 0) {

            x = 0;
        }

        if (x >= Constants.WIDTH - imageWidth) {

            x = Constants.WIDTH - imageWidth;
        }
    }

    void keyPressed(KeyEvent e) {

        java.lang.Integer key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }
    }

    void keyReleased(KeyEvent e) {

        java.lang.Integer key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }

    /**
     * makes the paddle return to default position
     */
    private void resetState() {

        x = Constants.INIT_PADDLE_X;
        y = Constants.INIT_PADDLE_Y;
    }

    /**
     * Changes the size of the paddle
     * @param size
     * @param s
     */
    void resize(java.lang.Integer size, String s)
    {
        var ii = new ImageIcon("breakout/client/sprites/resources/"+s);
        image = ii.getImage();
        if(size<2){
            this.image = this.image.getScaledInstance(imageWidth/2, imageHeight, java.awt.Image.SCALE_DEFAULT);
        }
        else{
        this.image = this.image.getScaledInstance(imageWidth*2, imageHeight, java.awt.Image.SCALE_DEFAULT);
        }
    }
}
