import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.net.Socket;

// Client

public class Main extends Application {

    //FIELDS
    final private int PORT = 1234;
    final private int SCREEN_WIDTH = 1024;
    final private int SCREEN_HEIGHT = 768;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group group = new Group();
        Pane pane  = new Pane();
        StackPane background = new StackPane();
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        background.getChildren().add(canvas);
        group.getChildren().add(background);
        background.setStyle("-fx-background-color: green");
        // TODO: thread for draw
        // TODO: socket stuff

        primaryStage.setTitle("Othello");
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
        drawInitialBoardState(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void drawInitialBoardState(GraphicsContext gc) {
        // Board drawn to initial state

        gc.setFill(Color.BLACK);
        for(int x = 0 ; x <= SCREEN_WIDTH; x++){
            for (int y = 0 ; y <= SCREEN_HEIGHT; y++){
                gc.strokeRect(x, y, 128, 96);
                y += 96;
            }
            x += 128;
        }
    }
}
