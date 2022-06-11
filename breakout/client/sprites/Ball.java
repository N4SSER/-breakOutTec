package client.sprites;
import javax.swing.*;

public class Ball extends Sprite {

    private java.lang.Integer xdir;
    private java.lang.Integer ydir;
    private java.lang.Integer speed;
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

    private void resetState() {

        x = Constants.INIT_BALL_X;
        y = Constants.INIT_BALL_Y;
    }

    void setXDir(java.lang.Integer x) {

        xdir = x;
    }

    void setYDir(java.lang.Integer y) {

        ydir = y;
    }

    java.lang.Integer getYDir() {

        return ydir;
    }
}

