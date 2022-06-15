package client;
import javax.swing.JFrame;

import client.sprites.Board;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.net.Socket;


public class Breakout extends JFrame {

    public Breakout() {

        initUI();
    }

    private void initUI() {
        try {
            Socket socket = new Socket("localhost", 35557);
            DataInputStream in_stream = new DataInputStream(socket.getInputStream());
            add(new Board(in_stream));
            setTitle("BreakoutTEC");

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            pack();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var game = new Breakout();
            game.setVisible(true);
        });
    }
}