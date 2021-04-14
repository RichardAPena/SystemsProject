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

    public Player(Socket player, Board board, String piece) throws IOException {
        in = new BufferedReader(new InputStreamReader(player.getInputStream()));
        out = new PrintWriter(player.getOutputStream());
        this.board = board;
        //this.opponnent = opponent;
        this.piece = piece;
        goNext = piece.equals("X");
    }

    public void run() {
        // TODO
        String request = "";
        // Listen to client requests
        while (true) {
            try {
                if (goNext) {
                    out.println("YOURTURN");
                    goNext = false;
                }
                request = in.readLine();
                if (request.startsWith("MAKEMOVE")) {

                } else if (request.startsWith("PASS")) {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
