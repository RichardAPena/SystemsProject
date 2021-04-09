import java.util.Arrays;

public class Board {

    private String[][] board;

    public Board() {
        board = new String[8][8];
    }

    public void initialize() {
        // TODO: set the board to its initial state
        // Use X for Black, O for Whites, . for empty space
        for(int x = 0; x < board.length; x++){
            for (int y = 0; y < board.length; y++){
                getBoard()[x][y] = ".";
            }
        }
        board[3][3] = "O";
        board[4][4] = "O";
        board[4][3] = "X";
        board[3][4] = "X";
        for(int x = 0; x < board.length; x++){
            for (int y = 0; y < board.length; y++){
                System.out.print(board[x][y]);
            }
            System.out.println("");

        }
    }

    // TODO: check if move at (x,y) from player is valid
    public boolean isValidMove(String player, int x, int y) {
        return true;
    }

    // TODO: make move at (x,y)
    public void makeMove(String player, int x, int y) {
        board[x][y] = player;
    }

    // TODO: update the board after a move is made, like turning over a bunch of
    public void update(String player, int previousX, int previousY) {
        /*
        player: X or O, depending on which player just made their move
        previousX: x position where they just made their move (0-7)
        previousY: y position where they just made their move (0-7)

        > Check all 8 directions around the point (x, y)
        > If you run into an empty space right away, break
        > If you run into opposite pieces and "sandwich" them in between your own pieces, turn them all to player colour
         */
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
}