package client.sprites;
import javax.swing.*;

public class Brick extends Sprite {

    private java.lang.Integer hits;
    private java.lang.Integer count;
    private java.lang.Integer surprise;
    private String color;
    private java.lang.Integer points;

    public Brick(java.lang.Integer x, java.lang.Integer y, java.lang.Integer hits, java.lang.Integer surprise,java.lang.Integer points, String color) {

        initBrick(x, y, hits, surprise, points, color);
    }

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

    private void loadImage() {

        var ii = new ImageIcon("breakout/client/sprites/resources/"+color+".png");
        image = ii.getImage();
    }

    boolean isDestroyed() {

        return count>=hits;
    }

    void hit(){
        count++;
    }
    java.lang.Integer getSurprise(){
        return surprise;
    }
    java.lang.Integer getPoints(){
        return points;
    }
    String getColor(){
        return color;
    }

    void set_Points(int n){
        this.points = n;
    }

    void set_hits(int n){
        this.hits = n;
    }

    void set_surpride(int n){
        this.surprise = n;
    }
}
