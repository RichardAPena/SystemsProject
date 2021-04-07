import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("Waiting for players");
        Socket s1 = ss.accept();
        Player p1 = new Player(s1);
        System.out.println("Player 1 found");
        Socket s2 = ss.accept();
        Player p2 = new Player(s2);
        System.out.println("Player 2 found");
        Board board = new Board();
        board.initialize();
        System.out.println("Created board");
        System.out.println("Game done");
    }
}
