package client.sprites;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Board extends JPanel {

    private Timer timer;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;
    private int lifes = 3;
    private int score = 0;
    private Vector<Ball> vBall = new Vector<Ball>();
    private String message = "Game Over";

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));

        gameInit();
    }

    private void gameInit() {

        bricks = new Brick[Commons.N_OF_BRICKS];
        vBall.add(new Ball());
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 8; i++) {
            String color="red";
            if(i<2)
                color = "green";
            if(i>=2&&i<4)
                color = "yellow";
            if(i>=4&&i<6)
                color = "orange";

            for (int j = 0; j < 13; j++) {

                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50,1,0,1,color); //hits, surprise y points son asignados desde el servidor
                k++;
            }
        }

        timer = new Timer(Commons.PERIOD, new GameCycle());
        timer.start();
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

    private void drawObjects(Graphics2D g2d) {

        var font = new Font("Monospaced", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);

        g2d.drawString("Lifes: "+String.valueOf(lifes),(Commons.WIDTH - fontMetrics.stringWidth(String.valueOf(lifes)))/ 12, Commons.WIDTH/16);
        
        g2d.drawString(String.valueOf("Score: " + score), (Commons.WIDTH - fontMetrics.stringWidth(String.valueOf("Points:      " + score))), Commons.WIDTH/16);

        for(int i =0;i<vBall.size();i++){
            g2d.drawImage(vBall.get(i).getImage(), vBall.get(i).getX(), vBall.get(i).getY(),
            vBall.get(i).getImageWidth(), vBall.get(i).getImageHeight(), this);
        }

        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getImageWidth(), paddle.getImageHeight(), this);

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if (!bricks[i].isDestroyed()) {

                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }
    }

    private void gameFinished(Graphics2D g2d) {

        var font = new Font("Monospaced", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(message,
                (Commons.WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.WIDTH / 4);
        g2d.drawString( "\nYour score: " + String.valueOf(score),
                (Commons.WIDTH - fontMetrics.stringWidth( "\nYour score: " + String.valueOf(score))) / 2,
                Commons.WIDTH / 3);
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

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {

        for(int i = 0; i<vBall.size();i++)
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

    private void checkCollision() {
        
        for(int i = 0; i<vBall.size();i++){
            
            if (vBall.get(i).getRect().getMaxY() > Commons.BOTTOM_EDGE) {

                lifes--;
                vBall.remove(i);
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
    
                int paddleLPos = (int) paddle.getRect().getMinX();
                int ballLPos = (int) vBall.get(i).getRect().getMinX();
    
                int first = paddleLPos + 8;
                int second = paddleLPos + 16;
                int third = paddleLPos + 24;
                int fourth = paddleLPos + 32;
    
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
    
            for (int k = 0; k < Commons.N_OF_BRICKS; k++) {
    
                if ((vBall.get(i).getRect()).intersects(bricks[k].getRect())) {
    
                    int ballLeft = (int) vBall.get(i).getRect().getMinX();
                    int ballHeight = (int) vBall.get(i).getRect().getHeight();
                    int ballWidth = (int) vBall.get(i).getRect().getWidth();
                    int ballTop = (int) vBall.get(i).getRect().getMinY();
    
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
                            score+=bricks[i].getPoints();
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
                                paddle.resize(2, bricks[k].getColor()+".png");
                                break;
                                //mitad de tamano de raqueta
                                case 6:
                                paddle.resize(1/2, bricks[k].getColor()+".png");
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int x = 0, j = 0; x < Commons.N_OF_BRICKS; x++) {

            if (bricks[x].isDestroyed()) {

                j++;
            }

            if (j == Commons.N_OF_BRICKS) {

                message = "Victory!";
                stopGame();
            }
        }
    }
}