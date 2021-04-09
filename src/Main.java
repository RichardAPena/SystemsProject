import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.net.Socket;

// Client

public class Main extends Application {

    //FIELDS
    final private int PORT = 1234;
    final private int SCREEN_WIDTH = 1024;
    final private int SCREEN_HEIGHT = 768;
    final private int cell_X = 128;
    final private int cell_Y = 96;
    private GridPane playerBoard = new GridPane();


    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group group = new Group();
        StackPane background = new StackPane();
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //keeping it here, if you prefer handling graphics just revert it
//        background.getChildren().add(canvas);
//        group.getChildren().add(background);
//        background.setStyle("-fx-background-color: green");
        // TODO: thread for draw
        // TODO: socket stuff

        initPlayerBoard();
        group.getChildren().add(playerBoard);
        primaryStage.setTitle("Othello");
        primaryStage.setScene(new Scene(group));
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
        playerBoard.setPrefSize(8,8);
        playerBoard.setBackground(Background.EMPTY);
        for(int x = 0; x < 8; x++){
            for (int y = 0;y < 8; y++){
                Rectangle tile = new Rectangle(cell_X,cell_Y);
                tile.setStroke(Color.BLACK);
                tile.setFill(Color.GREEN);
                playerBoard.add(new StackPane(tile),x,y);
            }
        }
        playerBoard.add(new StackPane(new Rectangle(cell_X,cell_Y,Color.WHITE)),3,3);
        playerBoard.add(new StackPane(new Rectangle(cell_X,cell_Y,Color.WHITE)),4,4);
        playerBoard.add(new StackPane(new Rectangle(cell_X,cell_Y,Color.BLACK)),3,4);
        playerBoard.add(new StackPane(new Rectangle(cell_X,cell_Y,Color.BLACK)),4,3);
    }
}
