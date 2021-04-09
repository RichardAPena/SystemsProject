import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Client

public class Main extends Application {

    //FIELDS
    final private int SCREEN_WIDTH = 1024;
    final private int SCREEN_HEIGHT = 768;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group group = new Group();
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        group.getChildren().add(canvas);

        // TODO: thread for draw
        // TODO: socket stuff

        primaryStage.setTitle("Othello");
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
        draw(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void draw(GraphicsContext gc) {
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
