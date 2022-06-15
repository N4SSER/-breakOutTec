package client.sprites;
import java.awt.*;

/**
 * Class from wich all of the elements on screen are subclasses of
 */
public class Sprite {

    java.lang.Integer x;
    java.lang.Integer y;
    java.lang.Integer imageWidth;
    java.lang.Integer imageHeight;
    Image image;

    protected void setX(java.lang.Integer x) {

        this.x = x;
    }

    java.lang.Integer getX() {

        return x;
    }

    
    protected void setY(java.lang.Integer y) {

        this.y = y;
    }

    java.lang.Integer getY() {

        return y;
    }

    java.lang.Integer getImageWidth() {

        return imageWidth;
    }

    java.lang.Integer getImageHeight() {

        return imageHeight;
    }

    Image getImage() {

        return image;
    }

    Rectangle getRect() {

        return new Rectangle(x, y,
                image.getWidth(null), image.getHeight(null));
    }

    void getImageDimensions() {

        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
}
