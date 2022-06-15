package client.socketthread;
import client.sprites.*;

public class MessageListener extends Thread implements Listener{
    public Board board;
    public MessageListener(Board board){
        this.board = board;
    };
    public void run(){
        this.board.checkMessages();
    }
}