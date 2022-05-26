package client.sprites;
import java.awt.event.*;
import javax.swing.*;
public class Paddle extends Sprite  {
    private int dx;

    public Paddle() {

        initPaddle();
    }

    private void initPaddle() {
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

        if (x >= Commons.WIDTH - imageWidth) {

            x = Commons.WIDTH - imageWidth;
        }
    }

    void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }
    }

    void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }

    private void resetState() {

        x = Commons.INIT_PADDLE_X;
        y = Commons.INIT_PADDLE_Y;
    }

    void resize(float size, String s)
    {
        var ii = new ImageIcon("breakout/client/sprites/resources/"+s);
        image = ii.getImage();
        if(size<1){
            image = image.getScaledInstance(imageWidth*(1/((int)size)), imageHeight, java.awt.Image.SCALE_DEFAULT);
        }
        else{
        image = image.getScaledInstance(imageWidth*((int) size), imageHeight, java.awt.Image.SCALE_DEFAULT);
        }
    }
}
