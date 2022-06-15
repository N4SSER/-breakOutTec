package client.sprites;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;
import client.socketthread.*;

/**
 * Contains most of the logic of the gameplay
 * It is extended from JPanel to draw an image on the screen
 */
public class Board extends JPanel implements Constants {

    private Timer timer;
    private Paddle paddle;
    private Brick[] bricks;
    private java.lang.Boolean inGame = true;
    private java.lang.Integer lifes = 3;
    private java.lang.Integer score = 0;
    private Vector<Ball> vBall = new Vector<Ball>();
    private String message = "Game Over";
    private DataInputStream in_stream;

    /**
     * It instantiates the a Board with a datinputstream
     * @param in_stream to handle the commands from a server
     */
    public Board(DataInputStream in_stream) {
        this.in_stream = in_stream;
        initBoard();
    }


    /**
     * Initializes board
     */
    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));

        gameInit();

    }


    /**
     * Function that checks for commands from the server in a loop
     */
    public void checkMessages(){
        while(true){
            try {
                System.out.println(this.in_stream.readUTF());
                String message = this.in_stream.readUTF();
                java.lang.Integer action;
                java.lang.Integer number1;
                java.lang.Integer number2;

                StringTokenizer st = new StringTokenizer(message,",");
                action = Integer.parseInt(st.nextToken());
                System.out.println(action);
                number1 = Integer.parseInt(st.nextToken());
                System.out.println(number1);
                number2 = Integer.parseInt(st.nextToken());
                System.out.println(number2);

                if(action.intValue() == 1){
                    java.lang.Integer levelfactor;
                    
                    levelfactor = 26*(number1)-1;
                    System.out.println(" "+(levelfactor-25)+","+levelfactor);
                    /**
                     * looping through the bricks of the selected group to change the value of the points
                     */
                    for (java.lang.Integer i = (levelfactor-25);i<(levelfactor+1);i++){
                        bricks[i].set_Points(number2);
                        System.out.println("Brick "+i+" with "+number2+" points");
                    }
                    System.out.println("Nivel "+number1+" tiene "+number2+" puntos");
                } else {

                    /**
                     * Changing from coordinates to list indexs
                     */
                    java.lang.Integer index = (number1-1)*13+number2-1;
                    if(action.intValue() == 2){
                        //asigna vidas
                        bricks[index].set_surprise(4);
                        System.out.println("Asigna vidas a "+number1+","+number2);
                    } else if (action.intValue() == 3){
                        //asigna bola
                        bricks[index].set_surprise(1);
                        System.out.println("Asigna bola a "+number1+","+number2);
                    } else if (action.intValue() == 4){
                        //asigna raqueta doble
                        bricks[index].set_surprise(5);
                        System.out.println("Asigna raqueta doble a "+number1+","+number2);
                    } else if (action.intValue() == 5){
                        //asigna raqueta mitad
                        bricks[index].set_surprise(6);
                        System.out.println("Asigna raqueta mitad a "+number1+","+number2);
                    } else if(action.intValue() == 6){
                        //asigna velocidad mas
                        bricks[index].set_surprise(2);
                        System.out.println("Asigna velocidad mas a "+number1+","+number2);
                    } else if(action.intValue() == 7){
                        //asigna velocidad menos
                        bricks[index].set_surprise(3);
                        System.out.println("Asigna velocidad menos a "+number1+","+number2);
                    }
                }
            } catch (IOException e){
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    /**
     * Starts the game
     */
    private void gameInit() {

        bricks = new Brick[Constants.N_OF_BRICKS];
        vBall.add(new Ball());
        paddle = Paddle.getInstancPaddle();
        MessageListener msg_listener = new MessageListener(this);
        java.lang.Integer k = 0;

        for (java.lang.Integer i = 0; i < 8; i++) {
            String color="red";
            if(i<2)
                color = "green";
            if(i>=2&&i<4)
                color = "yellow";
            if(i>=4&&i<6)
                color = "orange";

            for (java.lang.Integer j = 0; j < 13; j++) {

                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50,1,0,1,color); //hits, surprise y points son asignados desde el servidor
                k++;
            }
        }

        timer = new Timer(Constants.PERIOD, new GameCycle());
        timer.start();
        msg_listener.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (inGame) {

            drawObjects(g2d);
        } else {

            gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Draws the objects on screen, checks if bricks are destroyed, so it doesn't draw them
     * 
     * @param g2d
     */
    private void drawObjects(Graphics2D g2d) {

        var font = new Font("Monospaced", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);

        g2d.drawString("Lifes: "+String.valueOf(lifes),(Constants.WIDTH - fontMetrics.stringWidth(String.valueOf(lifes)))/ 12, Constants.WIDTH/16);
        
        g2d.drawString(String.valueOf("Score: " + score), (Constants.WIDTH - fontMetrics.stringWidth(String.valueOf("Points:      " + score))), Constants.WIDTH/16);

        for(java.lang.Integer i =0;i<vBall.size();i++){
            g2d.drawImage(vBall.get(i).getImage(), vBall.get(i).getX(), vBall.get(i).getY(),
            vBall.get(i).getImageWidth(), vBall.get(i).getImageHeight(), this);
        }

        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getImageWidth(), paddle.getImageHeight(), this);

        for (java.lang.Integer i = 0; i < Constants.N_OF_BRICKS; i++) {

            if (!bricks[i].isDestroyed()) {

                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }
    }

    /**
     * Checks the end of the game
     * @param g2d
     */
    private void gameFinished(Graphics2D g2d) {

        var font = new Font("Monospaced", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(message,
                (Constants.WIDTH - fontMetrics.stringWidth(message)) / 2,
                Constants.WIDTH / 4);
        g2d.drawString( "\nYour score: " + String.valueOf(score),
                (Constants.WIDTH - fontMetrics.stringWidth( "\nYour score: " + String.valueOf(score))) / 2,
                Constants.WIDTH / 3);
        if(message.equals("Game Over")){
            var ii = new ImageIcon("breakout/client/sprites/resources/lose.png");
            g2d.drawImage(ii.getImage(), 370, 230, 150, 150, null);
        }
        else{
            var ii = new ImageIcon("breakout/client/sprites/resources/win.png");
            g2d.drawImage(ii.getImage(), 0, 140, 250, 250, null);
        }
        g2d.dispose();

               
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            paddle.keyPressed(e);
        }
    }

    /**
     * Handles the Events so the game cycles through the necessary processess
     */
    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {

        for(java.lang.Integer i = 0; i<vBall.size();i++)
        {
            vBall.get(i).move();
        }
        paddle.move();
        checkCollision();
        repaint();
    }

    private void stopGame() {

        inGame = false;
        timer.stop();
    }

    /**
     * Checks for collisions between the ball and the bricks, paddle and walls
     * Contains a lot of the logic of the gameplay, since it is collision based
     */
    private void checkCollision() {
        
        for(java.lang.Integer i = 0; i<vBall.size();i++){
            
            if (vBall.get(i).getRect().getMaxY() > Constants.BOTTOM_EDGE) {

                lifes--;
                vBall.removeElementAt(i);
                if(lifes<1){
                    stopGame();
                    break;
                }
                else
                {
                    if(vBall.size()<2){
                        vBall.add(new Ball());
                    }
                }
            }
    
            if ((vBall.get(i).getRect()).intersects(paddle.getRect())) {
                

                java.lang.Double tmp = paddle.getRect().getMinX();
                java.lang.Integer paddleLPos =  tmp.intValue();
                tmp = vBall.get(i).getRect().getMinX();
                java.lang.Integer ballLPos = tmp.intValue();
                java.lang.Integer first = paddleLPos + 8;
                java.lang.Integer second = paddleLPos + 16;
                java.lang.Integer third = paddleLPos + 24;
                java.lang.Integer fourth = paddleLPos + 32;
    
                if (ballLPos < first) {
    
                    vBall.get(i).setXDir(-1);
                    vBall.get(i).setYDir(-1);
                }
    
                if (ballLPos >= first && ballLPos < second) {
    
                    vBall.get(i).setXDir(-1);
                    vBall.get(i).setYDir(-1 * vBall.get(i).getYDir());
                }
    
                if (ballLPos >= second && ballLPos < third) {
    
                    vBall.get(i).setXDir(0);
                    vBall.get(i).setYDir(-1);
                }
    
                if (ballLPos >= third && ballLPos < fourth) {
    
                    vBall.get(i).setXDir(1);
                    vBall.get(i).setYDir(-1 * vBall.get(i).getYDir());
                }
    
                if (ballLPos > fourth) {
    
                    vBall.get(i).setXDir(1);
                    vBall.get(i).setYDir(-1);
                }
            }
    
            for (java.lang.Integer k = 0; k < Constants.N_OF_BRICKS; k++) {
    
                if ((vBall.get(i).getRect()).intersects(bricks[k].getRect())) {
                    java.lang.Double tmp = vBall.get(i).getRect().getMinX();
                    java.lang.Integer ballLeft = tmp.intValue();
                    tmp = vBall.get(i).getRect().getHeight();
                    java.lang.Integer ballHeight = tmp.intValue();
                    tmp =  vBall.get(i).getRect().getWidth();
                    java.lang.Integer ballWidth = tmp.intValue();
                    tmp = vBall.get(i).getRect().getMinY();
                    java.lang.Integer ballTop = tmp.intValue();
    
                    var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                    var pointLeft = new Point(ballLeft - 1, ballTop);
                    var pointTop = new Point(ballLeft, ballTop - 1);
                    var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);
    
                    if (!bricks[k].isDestroyed()) {
    
                        if (bricks[k].getRect().contains(pointRight)) {
    
                            vBall.get(i).setXDir(-1);
                        } else if (bricks[k].getRect().contains(pointLeft)) {
    
                            vBall.get(i).setXDir(1);
                        }
    
                        if (bricks[k].getRect().contains(pointTop)) {
    
                            vBall.get(i).setYDir(1);
                        } else if (bricks[k].getRect().contains(pointBottom)) {
    
                            vBall.get(i).setYDir(-1);
                        }
    
                        bricks[k].hit();
                        //If brick is destroyed ask the server
                        if(bricks[k].isDestroyed()){
                            score+=bricks[k].getPoints();
                            
                            paddle.resize(1, bricks[k].getColor()+".png");
                            switch(bricks[k].getSurprise())
                            {
                                //agrega nueva bola
                                case 1:
                                vBall.add(new Ball());
                                break;
                                //aumenta velocidad
                                case 2:
                                vBall.get(i).speed(true);
                                break;
                                //resta velocidad
                                case 3:
                                vBall.get(i).speed(false);
                                break;
                                //agrega vidas
                                case 4:
                                lifes++;
                                break;
                                //doble tamano de raqueta
                                case 5:
                                paddle.resize(1, bricks[k].getColor()+".png");
                                break;
                                //mitad de tamano de raqueta
                                case 6:
                                paddle.resize(2, bricks[k].getColor()+".png");
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (java.lang.Integer x = 0, j = 0; x < Constants.N_OF_BRICKS; x++) {

            if (bricks[x].isDestroyed()) {

                j++;
            }

            if (j == Constants.N_OF_BRICKS) {

                message = "Victory!";
                stopGame();
            }
        }
    }
}