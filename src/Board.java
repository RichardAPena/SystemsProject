import java.util.Arrays;

public class Board {

    private String[][] board;

    public Board() {
        board = new String[8][8];
    }

    public static void main(String[] args) {
        Board gameBoard = new Board();
        System.out.println(gameBoard.toString());
        gameBoard.initialize();
        System.out.println(gameBoard.toString());
        gameBoard.makeMove("X", 0, 0);
        System.out.println(gameBoard.toString());
        gameBoard.makeMove("O", 7, 7);
        System.out.println(gameBoard.toString());
    }

    public void initialize() {
        // Use X for Black, O for Whites, . for empty space
        for(int x = 0; x < board.length; x++){
            for (int y = 0; y < board.length; y++){
                board[x][y] = ".";
            }
        }
        board[3][3] = "O";
        board[4][4] = "O";
        board[4][3] = "X";
        board[3][4] = "X";

    }

    // TODO: check if move at (x,y) from player is valid
    public boolean isValidMove(String player, int x, int y) {
        String opponent;
        if (player.equals("X")) opponent = "O";
        else opponent = "X";

        int tempX = x;
        int tempY = y;

        // Check RIGHT
        while (true) {
            tempX++;
            break;
        }
        // Check UP-RIGHT

        // Check UP

        // Check UP-LEFT

        // Check LEFT

        // Check DOWN-LEFT

        // Check DOWN

        // Check DOWN-RIGHT


        // Else return false
        return false;
    }

    public void makeMove(String player, int x, int y) {
        board[x][y] = player;
        update(player, x, y);
    }

    // TODO: update the board after a move is made, so that u can turn over a bunch of disks after u made a big move
    public void update(String player, int previousX, int previousY) {
        String opponent;
        if (player.equals("X")) opponent = "O";
        else opponent = "X";

        /*
        player: X or O, depending on which player just made their move
        previousX: x position where they just made their move (0-7)
        previousY: y position where they just made their move (0-7)

        > Check all 8 directions around the point (x, y)
        > If you run into an empty space right away, break
        > If you run into opposite pieces and "sandwich" them in between your own pieces, turn them all to player colour
         */

        int tempX = previousX;
        int tempY = previousY;

        // Check RIGHT towards our disk
        // IMPORTANT: What if we checked from the very right, then move towards our point?
        tempX = 7; //
        while (tempX > previousX) { // Keep checking towards the left as long as we
            if (board[tempX][previousY].equals(".")) { // Empty space, do nothing
                System.out.println("a");
            }
            if (board[tempX][previousY].equals(opponent)) { // If the disk is an enemy, stop checking cuz u can't sandwich
                break;
            }
            if (board[tempX][previousY].equals(player)) { // You sandwiched the opponent's disks, start flipping them
                // TODO: start flipping stuff
            }
            tempX--;
        }

        // Check UP-RIGHT towards our disk
        tempX = previousX;
        tempY = previousY;
        do {
            tempX++;
            tempY--;
        } while (tempX < 7 || tempY > 0);

        // Check UP
        tempY = 0; // Set Y to the top

        // Check UP-LEFT
        tempX = previousX;
        tempY = previousY;
        do {
            tempX--;
            tempY--;
        } while (tempX > 0 || tempY > 0);

        // Check LEFT
        tempX = 0; // Set X to the left

        // Check DOWN-LEFT
        tempX = previousX;
        tempY = previousY;
        do {
            tempX--;
            tempY++;
        } while (tempX > 0 || tempY < 7);
        // Check DOWN
        tempY = 7; // Set Y to the bottom
        // Start going up

        // Check DOWN-RIGHT
        tempX = previousX;
        tempY = previousY;
        do {
            tempX++;
            tempY++;
        } while (tempX < 7 || tempY < 7);

    }

    public String getValue(int x, int y) {
        return board[x][y];
    }

    public void setValue(String value, int x, int y) { board[x][y] = value; }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    // TODO
    public String toString() {
        String output = "";
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[0].length; j++) {
                //System.out.println("i: " + i + " j: " + j);
                output += board[i][j] + " ";
            }
            output += "\n";
        }
        return output;
    }
}