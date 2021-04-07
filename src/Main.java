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

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group group = new Group();
        Canvas canvas = new Canvas(1024, 768);
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
        // TODO
        gc.setFill(Color.BLACK);
        gc.fillRect(10, 10, 100, 100);
    }
}
