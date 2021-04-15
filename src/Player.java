import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private Board board;
    Player opponnent;
    private boolean goNext;
    private String piece;
    private Socket playerSocket;
    private Socket opponentSocket;

    public Player(Socket player, Board board, String piece) throws IOException {
        in = new BufferedReader(new InputStreamReader(player.getInputStream()));
        out = new PrintWriter(player.getOutputStream());
        this.board = board;

        this.piece = piece;
        goNext = piece.equals("X");
    }
    public void setOpponent(Player p){
        this.opponnent = p;
    }
    public Socket getPlayerSocket(){
        return this.playerSocket;
    }
    public Socket getOpponentSocket(){
        return this.opponnent.getPlayerSocket();
    }
    public void run() {
        // TODO
        sendMessage(piece);
        // Listen to client requests
        while (true) {
            String request = "";
            try {
//                if (goNext) {
//                    out.println("YOURTURN");
//                    goNext = false;
//                }
                request = in.readLine();
                System.out.println(piece + ": " + request);
                if (request.startsWith("MAKEMOVE")) {
                    int x  = Integer.parseInt(request.split(" ")[2]);
                    int y  = Integer.parseInt(request.split(" ")[3]);
                    System.out.println(x + " " + y);
                    board.getBoard()[x][y] = piece;
                    try{
                        PrintWriter opponentOut = new PrintWriter(getOpponentSocket().getOutputStream());
                        opponentOut.println("MAKEMOVE" +" "+ piece + " " + x + " " + y);
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
