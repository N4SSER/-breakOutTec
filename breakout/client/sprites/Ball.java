package client.sprites;
import javax.swing.*;

public class Ball extends Sprite {

    private float xdir;
    private float ydir;
    private float speed;
    public Ball() {

        initBall();
    }

    private void initBall() {

        xdir = 1;
        ydir = -1;
        speed = 1;
        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("breakout/client/sprites/resources/ball.png");
        image = ii.getImage();
    }
    void speed(boolean ii){
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
    void move() {

        x += xdir*speed;
        y += ydir*speed;

        if (x == 0) {

            setXDir(1);
        }

        if (x == Commons.WIDTH - imageWidth) {

            System.out.println(imageWidth);
            setXDir(-1);
        }

        if (y == 0) {

            setYDir(1);
        }
    }

    private void resetState() {

        x = Commons.INIT_BALL_X;
        y = Commons.INIT_BALL_Y;
    }

    void setXDir(float x) {

        xdir = x;
    }

    void setYDir(float y) {

        ydir = y;
    }

    float getYDir() {

        return ydir;
    }
}

