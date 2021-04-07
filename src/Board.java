public class Board {

    private String[][] board;

    public Board() {
        board = new String[8][8];
    }

    public void initialize() {
        // TODO: set the board to its initial state
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
