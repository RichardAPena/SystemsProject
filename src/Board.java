import java.io.Serializable;

public class Board implements Serializable { // needs to be serializable to pass from server to client via ObjectStream

    private String[][] board;

    public Board() {
        board = new String[8][8];
    }

    public static void main(String[] args) {
//        Board gameBoard = new Board();
//        System.out.println(gameBoard.toString());
//        gameBoard.initialize();
//        System.out.println(gameBoard.toString());
//        gameBoard.makeMove("X", 0, 0);
//        System.out.println(gameBoard.toString());
//        gameBoard.makeMove("O", 7, 7);
//        System.out.println(gameBoard.toString());
//        gameBoard.makeMove("X", 3, 6);
//        System.out.println(gameBoard.toString());
        String stringBoard =
                       //0 1 2 3 4 5 6 7
                   /*0*/". . . . . . . .\n" +
                   /*1*/". . . . . . . .\n" +
                   /*2*/". . . . . . . .\n" +
                   /*3*/". . . O X . . .\n" +
                   /*4*/". . . X O . . .\n" +
                   /*5*/". . . . . . . .\n" +
                   /*6*/". . . . . . . .\n" +
                   /*7*/". . . . . . . .";

        System.out.println("String board: \n" + stringBoard);
        Board gameBoard = new Board();
        gameBoard.fromString(stringBoard);
        System.out.println("To string: \n" + gameBoard.toString());
        System.out.println(gameBoard.numValidMoves("X"));
        System.out.println(gameBoard.numValidMoves("O"));
        // Remember X goes first
        gameBoard.makeMove("X", 3, 2);
        System.out.println("To string: \n" + gameBoard.toString());
        System.out.println(gameBoard.numValidMoves("X"));
        System.out.println(gameBoard.numValidMoves("O"));
        gameBoard.makeMove("O", 4, 2);
        System.out.println("To string: \n" + gameBoard.toString());
        System.out.println(gameBoard.numValidMoves("X"));
        System.out.println(gameBoard.numValidMoves("O"));
    }

    /**
     * Initializes the board to its default state (2 X's and 2 O's placed diagonally from each other)
     */
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

    /**
     * Checks if a move made by a player at coordinate (x, y) is valid, and returns a boolean with the result
     * @param player player indicator, can be either X or O
     * @param x x-coordinate to check
     * @param y y-coordinate to check
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(String player, int x, int y) {

        if (!board[x][y].equals(".")) { // If the space is NOT empty, then its not a valid move
            return false;
        }

        String opponent;
        if (player.equals("X")) opponent = "O";
        else opponent = "X";

        // Check left
        if (checkFlip(x-1, y, -1, 0, player, opponent))
            return true;

        // Check right
        if (checkFlip(x+1, y, 1, 0, player, opponent))
            return true;

        // Check up
        if (checkFlip(x, y-1, 0, -1, player, opponent))
            return true;

        // Check down
        if (checkFlip(x, y+1, 0, 1, player, opponent))
            return true;

        // Check up-left
        if (checkFlip(x-1, y-1, -1, -1, player, opponent))
            return true;

        // Check up-right
        if (checkFlip(x+1, y-1, 1, -1, player, opponent))
            return true;

        // Check down-left
        if (checkFlip(x-1, y+1, -1, 1, player, opponent))
            return true;

        // Check down-right
        if (checkFlip(x+1, y+1, 1, 1, player, opponent))
            return true;

        // If no valid flips are found, return false
        return false;
    }

    /**
     * Checks if opposing pieces can be flipped in a specified direction
     * @param x starting x point
     * @param y starting y point
     * @param dx horizontal direction to check (-1 for left, 1 for right)
     * @param dy vertical direction to check (-1 for up, 1 for down)
     * @param player player to check
     * @param opponent opponent to check
     * @return true if a flip can be made in the chosen direction, false otherwise
     */
    private boolean checkFlip(int x, int y, int dx, int dy, String player, String opponent) {
        if (x < 0 || x > 7 || y < 0 || y > 7) return false; // Out of bounds
        if (dx == 0 && dy == 0 ) return false; // Infinite loop prevention
        if (board[x][y].equals(opponent)) {
            while (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
                x += dx;
                y += dy;
                if (board[x][y].equals("."))
                    return false;
                if (board[x][y].equals(player))
                    return true;
            }
        }
        return false;
    }

    /**
     * Use to check how many valid moves are available to a specified player (if none, pass)
     * @param player the player to check
     * @return number of valid moves available to player
     */
    public int numValidMoves(String player) {
        int validMoves = 0;
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[0].length; j++) {
                if (isValidMove(player, j, i)) validMoves++;
            }
        }
        return validMoves;
    }

    /**
     * Makes a move at a specified coordinate in the board, changing its value to a parameter
     * @param player player indicator, can be either X or O
     * @param x x-coordinate in the board
     * @param y y-coordinate in the board
     */
    public void makeMove(String player, int x, int y) {
        board[x][y] = player;

        String opponent;
        if (player.equals("X")) opponent = "O";
        else opponent = "X";

        // Check left
        if (checkFlip(x-1, y, -1, 0, player, opponent))
            flipPieces(x-1, y, -1, 0, player, opponent);

        // Check right
        if (checkFlip(x+1, y, 1, 0, player, opponent))
            flipPieces(x+1, y, 1, 0, player, opponent);

        // Check up
        if (checkFlip(x, y-1, 0, -1, player, opponent))
            flipPieces(x, y-1, 0, -1, player, opponent);

        // Check down
        if (checkFlip(x, y+1, 0, 1, player, opponent))
            flipPieces(x, y+1, 0, 1, player, opponent);

        // Check up-left
        if (checkFlip(x-1, y-1, -1, -1, player, opponent))
            flipPieces(x-1, y-1, -1, -1, player, opponent);

        // Check up-right
        if (checkFlip(x+1, y-1, 1, -1, player, opponent))
            flipPieces(x+1, y-1, 1, -1, player, opponent);

        // Check down-left
        if (checkFlip(x-1, y+1, -1, 1, player, opponent))
            flipPieces(x-1, y+1, -1, 1, player, opponent);

        // Check down-right
        if (checkFlip(x+1, y+1, 1, 1, player, opponent))
            flipPieces(x+1, y+1, 1, 1, player, opponent);

        //update(player, x, y);
    }

    /**
     * Flips all opposing pieces to the player's colour and stops once a player piece is found
     * @param x starting x point
     * @param y starting y point
     * @param dx horizontal direction to flip (-1 for left, 1 for right, 0 for neither)
     * @param dy vertical direction to flip (-1 for up, 1 for down, 0 for neither)
     * @param player player to check
     * @param opponent opponent to check
     */
    private void flipPieces(int x, int y, int dx, int dy, String player, String opponent) {
        if (dx == 0 && dy == 0 ) return; // Infinite loop prevention
        while (board[x][y].equals(opponent)) {
            board[x][y] = player;
            x += dx;
            y += dy;
        }
    }

    /**
     * Returns the player value at a given x and y value
     * @param x x value on the board
     * @param y y value on the board
     */
    public String getValue(int x, int y) {
        return board[x][y];
    }

    /**
     * Sets the value at a given x and y value
     * @param x x value on the board
     * @param y y value on the board
     * @param value which player owns that spot on the board
     */
    public void setValue(String value, int x, int y) { board[x][y] = value; }

    /**
     * Returns a board object
     */
    public String[][] getBoard() {
        return board;
    }
    /**
     * Sets the value at a given x and y value
     * @param board sets a board object
     */
    public void setBoard(String[][] board) {
        this.board = board;
    }

    /**
     * Returns an a String version of the board
     */
    public String toString() {
        String output = "";
        for (int i=0; i<board.length; i++) { // Columns
            for (int j=0; j<board[0].length; j++) { // Rows
                output += board[j][i];
                if (j<board[0].length-1) output += " ";
            }
            if (i<board.length-1) output += "\n";
        }
        return output;
    }

    /**
     * Parses a String board into the String[][] board
     * @param stringBoard board in String form
     */
    public void fromString(String stringBoard) {
        /*
        EXAMPLE:
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . O X . . .
        . . . X O . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
         */

        String[] rows = stringBoard.split("\n");
        for (int i=0; i<rows.length; i++) {
            String[] values = rows[i].split(" ");
            for (int j=0; j<values.length; j++) {
                board[j][i] = values[j];
            }
        }
    }

    /**
     * Checks to see which player has the most disks on the board, and returns the winner of the game
     * @return the corresponding player indicator with the most points, or Tie if both players have equal points
     */
    public String getWinner() {
        if (getScore("O") > getScore("X")) {
            return "O";
        } else if (getScore("X") > getScore("O")) {
            return "X";
        } else {
            return "Tie";
        }
    }

    /**
     * Counts the number of pieces a player has and returns the value
     * @param player player to check
     * @return the score of player x
     */
    public int getScore(String player) {
        int score = 0;
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[0].length; j++) {
                if (board[j][i].equals(player)) score++;
            }
        }
        return score;
    }

}