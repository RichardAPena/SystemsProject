import java.util.Arrays;

public class Board {

    private String[][] board;

    public Board() {
        board = new String[8][8];
    }

    public void initialize() {
        // TODO: set the board to its initial state
        // Use X for Black, O for Whites, . for empty space
        for(int x = 0; x < getBoard().length; x++){
            for (int y = 0; y < getBoard().length; y++){
                getBoard()[x][y] = ".";
            }
        }
        getBoard()[3][3] = "O";
        getBoard()[4][4] = "O";
        getBoard()[4][3] = "X";
        getBoard()[3][4] = "X";
        for(int x = 0; x < getBoard().length; x++){
            for (int y = 0; y < getBoard().length; y++){
                System.out.print(getBoard()[x][y]);
            }
            System.out.println("");

        }
    }

    public String getValue(int x, int y) {
        return board[x][y];
    }

    public void setValue(String value, int x, int y) {
        board[x][y] = value;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }
}
