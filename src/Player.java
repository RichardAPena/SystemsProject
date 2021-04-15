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
                    String xo = request.split(" ")[1];
                    int x = Integer.parseInt(request.split(" ")[2]);
                    int y = Integer.parseInt(request.split(" ")[3]);
                    System.out.println(x + " " + y);
                    board.makeMove(xo, x, y);
                    System.out.println(board);

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
