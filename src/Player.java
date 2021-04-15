import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private PrintWriter opponentOut;
    private Board board;
    Player opponnent;
    private String piece;
    private Socket playerSocket;
    private Socket opponentSocket;

    public Player(Socket player, Board board, String piece) throws IOException {
        playerSocket = player;
        in = new BufferedReader(new InputStreamReader(player.getInputStream()));
        out = new PrintWriter(player.getOutputStream());
        this.board = board;
        this.piece = piece;
    }
    public void setOpponent(Socket p) throws IOException { // set the opponent
        this.opponentSocket = p;
        System.out.println(getOpponentSocket().isConnected());
        opponentOut = new PrintWriter(getOpponentSocket().getOutputStream());
    }
    public Socket getOpponentSocket(){
        return this.opponentSocket;
    }
    public void run() {
        // TODO
        sendMessage(piece);
        // Listen to client requests
        while (true) {
            String request = "";
            try {
                request = in.readLine();
                System.out.println(piece + ": " + request);
                if (request.startsWith("MAKEMOVE")) {
                    int x  = Integer.parseInt(request.split(" ")[2]);
                    int y  = Integer.parseInt(request.split(" ")[3]);
                    System.out.println(x + " " + y);
                    board.getBoard()[x][y] = piece;
                    try{
                        opponentOut.println("MAKEMOVE" +" "+ piece + " " + x + " " + y + " " + "YOURTURN");
                        opponentOut.flush();
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                } else if (request.startsWith("PASS")) {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        System.out.println("Sending '" + message + "' to " + piece);
        out.println(message);
        out.flush();
    }
}
