import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(PORT);
        Board board = new Board();
        board.initialize();
        System.out.println("Waiting for players");
        Socket s1 = ss.accept();
        Player p1 = new Player(s1);
        System.out.println("Player 1 found");

        while(true){ // temporary, wanted to see if board would update based on client
//            sendBoardData(board,s1);
            ObjectOutputStream output = new ObjectOutputStream(s1.getOutputStream());
            DataInputStream in = new DataInputStream(s1.getInputStream());
//            DataOutputStream out = new DataOutputStream(s1.getOutputStream());
            int x = Integer.parseInt(in.readUTF().split(" ")[0]);
            int y = Integer.parseInt(in.readUTF().split(" ")[1]);
            System.out.println("x : " + x + " y : " + y);
            board.getBoard()[x][y] = "X";
            output.writeObject(board.getBoard());
            output.flush();
            System.out.println(board);

        }
//        Socket s2 = ss.accept();
//        Player p2 = new Player(s2);
//        System.out.println("Player 2 found");
//
//        sendBoardData(board,s2);
//        System.out.println("Created board");
//        System.out.println("Game done");
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
