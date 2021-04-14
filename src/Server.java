import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Server {

    private static final int PORT = 1234;
    private static final String hostName = "localhost";

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket();
        SocketAddress add = new InetSocketAddress(hostName, PORT);
        ss.bind(add);
        Board board = new Board();
        board.initialize();
        System.out.println("Waiting for players");
        Socket s1 = ss.accept();
        Player p1 = new Player(s1);
        System.out.println("Player 1 found");
        Socket s2 = ss.accept();
        Player p2 = new Player(s2);
        System.out.println("Player 2 found");

//        sendBoardData(board,s2);
        System.out.println("Created board");
//        System.out.println("Game done");
        while(true){ // temporary, wanted to see if board would update based on client
//            sendBoardData(board,s1);
            ObjectOutputStream outputPlayer1 = new ObjectOutputStream(s1.getOutputStream());
            DataInputStream inputPlayer1 = new DataInputStream(s1.getInputStream());
            ObjectOutputStream outputPlayer2 = new ObjectOutputStream(s2.getOutputStream());
            DataInputStream inputPlayer2 = new DataInputStream(s2.getInputStream());

            int xP1 = Integer.parseInt(inputPlayer1.readUTF().split(" ")[0]);
            int yP1 = Integer.parseInt(inputPlayer1.readUTF().split(" ")[1]);
            int xP2 = Integer.parseInt(inputPlayer2.readUTF().split(" ")[0]);
            int yP2 = Integer.parseInt(inputPlayer2.readUTF().split(" ")[1]);

            System.out.println("xP1 : " + xP1 + " yP1 : " + yP1);
            System.out.println("xP2 : " + xP2 + " yP2 : " + yP2);

            board.getBoard()[xP1][yP1] = "X";
            board.getBoard()[xP2][yP2] = "Y";

            outputPlayer1.writeObject(board.getBoard());
            outputPlayer1.flush();
            outputPlayer2.writeObject(board.getBoard());
            outputPlayer2.flush();
            System.out.println(board);

        }

    }
    public static void sendBoardData(Board board, Socket p){
        try{
            ObjectOutputStream output = new ObjectOutputStream(p.getOutputStream());
//            ObjectInputStream input = new ObjectInputStream(p.getInputStream());

            output.writeObject(board.getBoard());
//            output.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
