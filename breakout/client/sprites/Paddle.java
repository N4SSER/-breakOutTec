package client.sprites;
import java.awt.event.*;
import javax.swing.*;
public class Paddle extends Sprite  {

    private java.lang.Integer dx;
    private static Paddle paddle = null;

    private Paddle() {

        initPaddle();
    }
    public static Paddle getInstancPaddle()
    {
        if(paddle == null)
            paddle = new Paddle();

        return paddle;
    }
    private void initPaddle() {
        dx = 0;
        loadImage();
        getImageDimensions();

        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("breakout/client/sprites/resources/paddle.png");
        image = ii.getImage();
        
    }
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

    private void resetState() {

        x = Constants.INIT_PADDLE_X;
        y = Constants.INIT_PADDLE_Y;
    }

    void resize(java.lang.Integer size, String s)
    {
        var ii = new ImageIcon("breakout/client/sprites/resources/"+s);
        image = ii.getImage();
        if(size<1){
            image = image.getScaledInstance(imageWidth*(1/size), imageHeight, java.awt.Image.SCALE_DEFAULT);
        }
        else{
        image = image.getScaledInstance(imageWidth*(size), imageHeight, java.awt.Image.SCALE_DEFAULT);
        }
    }
}
