public class Board {

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
                        ". . . . . . . .\n" +
                        ". . . . . . . .\n" +
                        ". . . . . . . .\n" +
                        ". . . O X . . .\n" +
                        ". . . X O . . .\n" +
                        ". . . . . . . .\n" +
                        ". . . . . . . .\n" +
                        ". . . . . . . .";

        System.out.println("String board: \n" + stringBoard);
        Board gameBoard = new Board();
        gameBoard.fromString(stringBoard);
        System.out.println("To string: \n" + gameBoard.toString());
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
        // TODO: check if move at (x,y) from player is valid

        if (!board[x][y].equals(".")) { // If the space is NOT empty, then its not a valid move
            return false;
        }
        String opponent;
        if (player.equals("X")) opponent = "O";
        else opponent = "X";

        int tempX = x;
        int tempY = y;

        // >>> Check RIGHT
        tempX = 7;
        while (tempX > x) {
            if (board[tempX][y].equals(player)) {
                return true;
            } else if (board[tempX][y].equals(opponent)) {
                break;
            }
            tempX--;
        }
        // >>> Check UP-RIGHT
        tempX = x;
        tempY = y;


        // >>> Check UP
        tempY = 0;
        while (tempY < y) {
            if (board[x][tempY].equals(player)) {
                return true;
            } else if (board[x][tempY].equals(opponent)) {
                break;
            }
            tempY++;
        }

        // >>> Check UP-LEFT
        tempX = x;
        tempY = y;

        // >>> Check LEFT
        tempX = 0;
        while (tempX < x) {
            if (board[tempX][y].equals(player)) {
                return true;
            } else if (board[tempX][y].equals(opponent)) {
                break;
            }
            tempX++;
        }

        // >>> Check DOWN-LEFT
        tempX = x;
        tempY = y;

        // >>> Check DOWN
        tempY = 7;
        while (tempY > y) {
            if (board[x][tempY].equals(player)) {
                return true;
            } else if (board[x][tempY].equals(opponent)) {
                break;
            }
            tempY--;
        }

        // Check DOWN-RIGHT
        tempX = x;
        tempY = y;

        // If none of the conditions are true, return false
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
            for (int j=0; j<board[0].length; i++) {
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
        update(player, x, y);
    }

    /**
     * Called right after a move is made, updates the board so that values are changed and opposing disks are "captured"
     * @param player player which just made the move, can be either X or O
     * @param previousX x-coordinate in the board where the move was made
     * @param previousY y-coordinate in the board where the move was made
     */
    public void update(String player, int previousX, int previousY) {
        String opponent;
        if (player.equals("X")) opponent = "O";
        else opponent = "X";
        // TODO: update the board after a move is made, so that u can turn over a bunch of disks after u made a big move

        /*
        player: X or O, depending on which player just made their move
        previousX: x position where they just made their move (0-7)
        previousY: y position where they just made their move (0-7)

        > Check all 8 directions around the point (x, y)
        > If you run into an empty space right away, break
        > If you run into opposite pieces and "sandwich" them in between your own pieces, turn them all to player colour
         */

        // IMPORTANT: What if we checked from the very right, then move towards our point?
        int tempX;
        int tempY;

        // >>> Check RIGHT towards our disk <<<
        tempX = 7;
        // Start going left
        while (tempX > previousX) { // Keep checking towards the left as long as we
            if (board[tempX][previousY].equals(".")) { // Empty space, do nothing
                System.out.println("a");
            } else if (board[tempX][previousY].equals(opponent)) { // If the disk is an enemy, stop checking cuz u can't sandwich
                break;
            } else if (board[tempX][previousY].equals(player)) { // If a player disk is reached, flip disks in between
                // TODO: start flipping stuff
            }
            tempX--;
        }

        // >>> Check UP-RIGHT towards our disk <<<
        tempX = previousX;
        tempY = previousY;
        do {
            tempX++;
            tempY--;
        } while (tempX < 7 || tempY > 0); // Set X, Y to a corner in the top-right
        // Start going down-left
        while (tempX > previousX || tempY < previousY) {
            if (board[tempX][tempY].equals(".")) { // Empty space, do nothing
                System.out.println("d");
            } else if (board[tempX][tempY].equals(opponent)) { // Stop if you reach an enemy disk
                break;
            } else if (board[tempX][tempY].equals(player)) { // If a player disk is reached, flip disks in between
                // TODO: start flipping stuff
            }
            tempX--;
            tempY++;
        }

        // >>> Check UP towards our disk <<<
        tempY = 0; // Set Y to the top
        // Start going down
        while (tempY < previousY) {
            if (board[previousX][tempY].equals(".")) { // Empty space, do nothing
                System.out.println("b");
            } else if (board[previousX][tempY].equals(opponent)) { // Stop if you reach an enemy disk
                break;
            } else if (board[previousX][tempY].equals(player)) { // If a player disk is reached, flip disks in between
                // TODO: start flipping stuff
            }
            tempY++;
        }

        // >>> Check UP-LEFT towards our disk
        tempX = previousX;
        tempY = previousY;
        do {
            tempX--;
            tempY--;
        } while (tempX > 0 || tempY > 0); // Set X, Y to a corner in the top-left
        // Start going down-right
        while (tempX < previousX || tempY < previousY) {
            if (board[tempX][tempY].equals(".")) { // Empty space, do nothing
                System.out.println("d");
            } else if (board[tempX][tempY].equals(opponent)) { // Stop if you reach an enemy disk
                break;
            } else if (board[tempX][tempY].equals(player)) { // If a player disk is reached, flip disks in between
                // TODO: start flipping stuff
            }
            tempX++;
            tempY++;
        }

        // >>> Check LEFT
        tempX = 0; // Set X to the left
        // Start going right
        while (tempX > previousX) {
            if (board[tempX][previousY].equals(".")) { // Empty space, do nothing
                System.out.println("c");
            } else if (board[tempX][previousY].equals(opponent)) { // Stop if you reach an enemy disk
                break;
            } else if (board[tempX][previousY].equals(player)) { // If a player disk is reached, flip disks in between
                // TODO: start flipping stuff
            }
            tempX++;
        }

        // >>> Check DOWN-LEFT
        tempX = previousX;
        tempY = previousY;
        do {
            tempX--;
            tempY++;
        } while (tempX > 0 || tempY < 7); // Set X, Y to a corner in the bottom-left
        // Start going up-right
        while (tempX > previousX || tempY > previousY) {
            if (board[tempX][tempY].equals(".")) { // Empty space, do nothing
                System.out.println("g");
            } else if (board[tempX][tempY].equals(opponent)) { // Stop if you reach an enemy disk
                break;
            } else if (board[tempX][tempY].equals(player)) { // If a player disk is reached, flip disks in between
                // TODO: start flipping stuff
            }
            tempX++;
            tempY--;
        }

        // >>> Check DOWN
        tempY = 7; // Set Y to the bottom
        // Start going up
        while (tempY > previousY) {
            if (board[previousX][tempY].equals(".")) { // Empty space, do nothing
                System.out.println("d");
            } else if (board[previousX][tempY].equals(opponent)) { // Stop if you reach an enemy disk
                break;
            } else if (board[previousX][tempY].equals(player)) { // If a player disk is reached, flip disks in between
                // TODO: start flipping stuff
            }
            tempY--;
        }

        // >>> Check DOWN-RIGHT
        tempX = previousX;
        tempY = previousY;
        do {
            tempX++;
            tempY++;
        } while (tempX < 7 || tempY < 7); // Set X, Y to a corner in the bottom-right
        // Start going up-left
        while (tempX > previousX || tempY > previousY) {
            if (board[tempX][tempY].equals(".")) { // Empty space, do nothing
                System.out.println("h");
            } else if (board[tempX][tempY].equals(opponent)) { // Stop if you reach an enemy disk
                break;
            } else if (board[tempX][tempY].equals(player)) { // If a player disk is reached, flip disks in between
                // TODO: start flipping stuff
            }
            tempX--;
            tempY--;
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
        int oPlayer = 0;
        int xPlayer = 0;
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[0].length; j++) {
                if (board[j][i].equals("O")) oPlayer++;
                if (board[j][i].equals("X")) xPlayer++;
            }
        }

        if (oPlayer > xPlayer) {
            return "O";
        } else if (xPlayer > oPlayer) {
            return "X";
        }
        return "Tie";
    }

    public int getScorePlayerO() {
        int oPlayer = 0;
        int xPlayer = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[j][i].equals("O")) oPlayer++;
            }
        }
    return oPlayer;
    }

    public int getScorePlayerX() {
        int oPlayer = 0;
        int xPlayer = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[j][i].equals("X")) xPlayer++;
            }
        }
        return oPlayer;
    }



}