import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Arrays;

// Client

public class Main extends Application {

    //FIELDS
    final private int PORT = 1234;
    final private String HOST = "localhost";
    final private int SCREEN_WIDTH = 1024;
    final private int SCREEN_HEIGHT = 768;
    final private int cell_X = 128;
    final private int cell_Y = 96;
    private GridPane playerBoard = new GridPane();
    public String sendToServer;

    Socket s;
    Board board;
//    BufferedReader in;
//    PrintWriter out;
    DataInputStream in;
    DataOutputStream out;
    ObjectInputStream boardIn;
    String [][] currentBoard = new String[8][8];
    Scene game;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group group = new Group();
        //StackPane background = new StackPane();
        //Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        //GraphicsContext gc = canvas.getGraphicsContext2D();

        //keeping it here, if you prefer handling graphics just revert it
//        background.getChildren().add(canvas);
//        group.getChildren().add(background);
//        background.setStyle("-fx-background-color: green");



        // Grid Pane for Menu
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // UI Elements for Menu
        Label label = new Label("Othello"); // TODO
        Button btConnect = new Button("Connect");
        btConnect.setPrefWidth(200);
        Button btExit = new Button("Exit");
        btExit.setPrefWidth(200);

        grid.add(label, 0, 1);
        grid.add(btConnect, 0, 2);
        grid.add(btExit, 0,3);

        Scene menu = new Scene(grid, SCREEN_WIDTH, SCREEN_HEIGHT);
        // TODO: Should constantly read in requests from the server and handle them (like server sending a new board or telling you its your turn)
        Thread serverIn = new Thread(() -> {
            while (true) {
                try {
                    out = new DataOutputStream(s.getOutputStream());
                    in = new DataInputStream(s.getInputStream());
                    out.writeUTF(sendToServer);
//                    break;
//                    String request = "";
//                    request = in.readLine();
//                    System.out.println("REQUEST: " + request);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        });
        playerBoard.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() { // gets x and y of cell clicked
            @Override
            public void handle(MouseEvent e) {

                for( Node node: playerBoard.getChildren()) {

                    if( node instanceof Rectangle) {
                        if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                            sendToServer = GridPane.getRowIndex(node) + " " + GridPane.getColumnIndex(node);
                            System.out.println("x : " + GridPane.getRowIndex(node) + " y : " + GridPane.getColumnIndex(node));
                        }
                    }
                }
            }
        });
        btConnect.setOnAction(actionEvent -> {
            System.out.println("Connecting...");
            try {
                s = new Socket(HOST, PORT);
//                objOut = new ObjectOutputStream(s.getOutputStream());
                boardIn = new ObjectInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());
//                out = new PrintWriter(s.getOutputStream());
//                System.out.println(in.readLine());
//                currentBoard = ((Board) objIn.readObject());
//                serverIn.start();

                // CURRENTLY GETS BOARD WITHOUT THREAD, JUST NEEDS TO BE IN THREAD NOW, BUT GETS BOARD FROM SERVER
                currentBoard = (String[][]) boardIn.readObject();
                System.out.println(Arrays.deepToString(currentBoard));
                initPlayerBoard();
                group.getChildren().add(playerBoard);
                game = new Scene(group, SCREEN_WIDTH, SCREEN_HEIGHT);
//                System.out.println(currentBoard);
                primaryStage.setScene(game);
                serverIn.start();
            } catch(ConnectException ce) {
                System.out.println("Could not connect to the server.");
            } catch (IOException e) {
                e.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        btExit.setOnAction(actionEvent -> {
            System.out.println("Exiting...");
            Platform.exit();
        });

        // TODO: thread for draw
        // TODO: socket stuff
        Thread drawBoard = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000/60); // updates board 60 frames per second, prob only need to update when it changes
                    //System.out.println("test");
                }
            } catch (Exception e) { e.printStackTrace(); }
        });


        EventHandler<MouseEvent> mouseMove = mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            System.out.println(x + " " + y);
        };

        EventHandler<MouseEvent> mouseClick = mouseEvent -> {
                System.out.println("Clicked");
        };
        menu.addEventFilter(MouseEvent.MOUSE_MOVED, mouseMove);
        menu.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClick);

        drawBoard.start();
        primaryStage.setTitle("Othello");
        primaryStage.setScene(menu);
        primaryStage.setMinWidth(SCREEN_WIDTH);
        primaryStage.setMinHeight(SCREEN_HEIGHT);
        primaryStage.show();
//        drawInitialBoardState(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void drawInitialBoardState(GraphicsContext gc) {
        // Board drawn to initial state
        // Dimensions for 8x8 in our window is rect of length = 128 width = 96
        for(int x = 0 ; x < 8; x++){
            for (int y = 0 ; y < 8; y++){
                if((x == 3 && y == 3) || (x == 4 && y == 4)){
                    gc.setFill(Color.WHITE);
                    gc.fillRect(x * cell_X, y * cell_Y, cell_X,cell_Y);
                }
                if ((x == 3 && y == 4) || (x == 4 && y == 3)){
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x * cell_X, y * cell_Y, cell_X,cell_Y);
                }
                else{
                    gc.strokeRect(x * cell_X, y * cell_Y, cell_X, cell_Y);
                }

            }
        }
    }
    //TODO: Get server streams running to be able to update client boards, load the server board to the client board
    public void initPlayerBoard(){ // went with GridPane, makes onMouseClick easier to update grid
//        playerBoard.setPrefSize(8,8);
//        playerBoard.setLayoutX(8);
//        playerBoard.setLayoutY(8);
        playerBoard.getChildren().removeAll();
        playerBoard.setBackground(Background.EMPTY);
        for(int x = 0; x < 8; x++){
            for (int y = 0;y < 8; y++){
                Rectangle tile = new Rectangle(cell_X,cell_Y);
                if (currentBoard[x][y].equals("X")){
                    tile.setFill(Color.BLACK);
                }
                else if (currentBoard[x][y].equals("O")){
                    tile.setFill(Color.WHITE);
                }
                else{
                    tile.setStroke(Color.BLACK);
                    tile.setFill(Color.GREEN);
                }
                GridPane.setRowIndex(tile,x);
                GridPane.setColumnIndex(tile,y);
                playerBoard.getChildren().add(tile);
            }
        }
//        playerBoard.add(new StackPane(new Rectangle(cell_X,cell_Y,Color.WHITE)),3,3);
//        playerBoard.add(new StackPane(new Rectangle(cell_X,cell_Y,Color.WHITE)),4,4);
//        playerBoard.add(new StackPane(new Rectangle(cell_X,cell_Y,Color.BLACK)),3,4);
//        playerBoard.add(new StackPane(new Rectangle(cell_X,cell_Y,Color.BLACK)),4,3);

        //I tried adding text to output the score on the screen but since the board is null I keep getting errors
         /*
        Text scoreX = new Text("X's Score: " + board.getScorePlayerX());
        Text scoreO = new Text ("O's Score: " + board.getScorePlayerO());
        playerBoard.add(scoreX, 0,0);
        playerBoard.add(scoreO, 0,0);

         */
    }


    public void stop() {
        Platform.exit();
    }
}
