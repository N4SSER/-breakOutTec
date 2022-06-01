package client.sprites;
import javax.swing.*;

public class Brick extends Sprite {

    private int hits;
    private int count;
    private int surprise;
    private String color;
    private int points;

    public Brick(int x, int y, int hits, int surprise,int points, String color) {

        initBrick(x, y, hits, surprise, points, color);
    }

    private void initBrick(int x, int y, int hits, int surprise, int points ,String color) {
        
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
    int getSurprise(){
        return surprise;
    }
    int getPoints(){
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
