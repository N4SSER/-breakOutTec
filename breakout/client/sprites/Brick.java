package client.sprites;
import javax.swing.*;

/**
 * Brick extends the super class Sprite
 * It modelesthe bricks displayed in the game
 */
public class Brick extends Sprite {
    
    private java.lang.Integer hits;
    private java.lang.Integer count;
    private java.lang.Integer surprise;
    private String color;
    private java.lang.Integer points;
    /**
     * Brick Constructor
     * @param x
     * @param y
     * @param hits
     * @param surprise
     * @param points
     * @param color
     */
    public Brick(java.lang.Integer x, java.lang.Integer y, java.lang.Integer hits, java.lang.Integer surprise,java.lang.Integer points, String color) {

        initBrick(x, y, hits, surprise, points, color);
    }

    /**
     * Initializes the brick in position (x,y), with hits hits,
     * points numbe of points, a surprise represented by a number
     * and a color
     * @param x
     * @param y
     * @param hits
     * @param surprise
     * @param points
     * @param color
     */
    private void initBrick(java.lang.Integer x, java.lang.Integer y, java.lang.Integer hits, java.lang.Integer surprise, java.lang.Integer points ,String color) {
        
        this.x = x;
        this.y = y;
        this.hits = hits;
        this.points = points;
        this.surprise = surprise;
        this.color = color;
        count = 0;

        loadImage();
        getImageDimensions();
    }

    /**
     * Loads an Image into the Brick
     */
    private void loadImage() {

        var ii = new ImageIcon("breakout/client/sprites/resources/"+color+".png");
        image = ii.getImage();
    }

    /**
     * Checks if the brick is destroyed
     * 
     * @return True if the brick has been hit enough times to be destroyed
     */
    boolean isDestroyed() {

        return count>=hits;
    }

    /**
     * increases the amount of hits taken by the brick
     */
    void hit(){
        count++;
    }

    /**
     * 
     * @return integer representing a surprise
     */
    java.lang.Integer getSurprise(){
        return surprise;
    }

    /**
     * 
     * @return integer representing the ammount of points that the brick gives if destroyed
     */
    java.lang.Integer getPoints(){
        return points;
    }

    /**
     * 
     * @return a string with the brick's color
     */
    String getColor(){
        return color;
    }

    /**
     * sets points to n
     * @param n
     */
    void set_Points(int n){
        this.points = n;
    }

    /**
     * sets hits to n
     * @param n
     */
    void set_hits(int n){
        this.hits = n;
    }

    /**
     * sets surprise to n
     * @param n
     */
    void set_surprise(int n){
        this.surprise = n;
    }
}
