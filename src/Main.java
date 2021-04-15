import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Main extends Application {

    //FIELDS
    final private int PORT = 1234;
    final private String HOST = "localhost";
    final private int SCREEN_WIDTH = 1024;
    final private int SCREEN_HEIGHT = 768;

    Socket s;
    Board board;
    BufferedReader in;
    PrintWriter out;

    boolean yourTurn;
    String piece;

    @Override
    public void start(Stage primaryStage) {
        board = new Board();
        board.initialize();

        // Grid Pane for Menu
        GridPane grid = new GridPane();
        grid.setManaged(false);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(5);

        // Aesthetics
        Text emptySpace = new Text("");
        Rectangle rectangle = new Rectangle(0,0,5000,5000);
        rectangle.setManaged(false);
        rectangle.setFill(Color.FORESTGREEN);
        Circle circle1 = new Circle(437, 230, 50);
        circle1.setManaged(false);
        circle1.setFill(Color.WHITE);
        Circle circle2 = new Circle(567, 230, 50);
        circle2.setManaged(false);
        circle2.setFill(Color.BLACK);
        Circle circle3 = new Circle(437, 110, 50);
        circle3.setManaged(false);
        Circle circle4 = new Circle(567, 110, 50);
        circle4.setManaged(false);
        circle4.setFill(Color.WHITE);

        // UI Elements for Menu
        Text text = new Text("Othello");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        Text creditsl1 = new Text("Created by: Richard Pena, David Watt,");
        Text creditsl2 = new Text ("Saffana Ahammed and Nick Gaudet");
        Text connection = new Text ("Waiting for input...");
        connection.setWrappingWidth(225);
        text.setLineSpacing(5.0);
        creditsl1.setLineSpacing(2.0);
        creditsl2.setLineSpacing(2.0);
        connection.setLineSpacing(20.0);
        connection.setTextAlignment(TextAlignment.CENTER);
        connection.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        creditsl1.setTextAlignment(TextAlignment.CENTER);
        creditsl2.setTextAlignment(TextAlignment.CENTER);
        creditsl1.setWrappingWidth(225);
        creditsl2.setWrappingWidth(225);
        creditsl1.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        creditsl2.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        Button btConnect = new Button("Connect");
        btConnect.setPrefWidth(200);
        btConnect.setStyle("-fx-background-color: #ebe834;");
        Button btExit = new Button("Exit");
        btExit.setStyle("-fx-background-color: #00ffff;");
        btExit.setPrefWidth(200);
        grid.getChildren().add(0,rectangle);
        grid.add (emptySpace,0,11);
        grid.add(text, 0, 12);
        grid.add(btConnect, 0, 13);
        grid.add(btExit, 0,14);
        grid.add(creditsl1,0,15);
        grid.add(creditsl2,0,16);
        grid.getChildren().add(1,circle1);
        grid.getChildren().add(2,circle2);
        grid.getChildren().add(1,circle3);
        grid.getChildren().add(2,circle4);
        grid.add(connection,0,17);

        Scene menu = new Scene(grid, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Initialize Canvas for game UI
        Group group = new Group();
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        group.getChildren().add(canvas);
        Scene game = new Scene(group, SCREEN_WIDTH, SCREEN_HEIGHT);

        // TODO: Should constantly read in requests from the server and handle them (like server sending a new board or telling you its your turn)
        Thread serverIn = new Thread(() -> {
            System.out.println("Started serverIn thread");
            while (true) {
                String request = "";
                try {
                    request = in.readLine();
                    System.out.println(request);
                    if (request.equals("GAMEOVER")) {
                        break;
                    } else if (request.equals("YOURTURN")) {
                        yourTurn = true;
                    } else if (request.contains("MAKEMOVE")) { // Syntax: MAKEMOVE X 2 3
                        String[] requestArr = request.split(" ");
                        board.makeMove(requestArr[1], Integer.parseInt(requestArr[2]), Integer.parseInt(requestArr[3]));
                    } else if (request.equals("X")) {
                        piece = "X";
                    } else if (request.equals("O")) {
                        piece = "O";
                    } else if (request.contains("GAMEOVER")) {
                        System.out.println("Game over!");
                        break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Draws board 60 times per second
        Thread drawBoard = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000/60);
                    draw(gc);
                    if (yourTurn && board.numValidMoves(piece) == 0) {
                        out.println("PASS");
                        yourTurn = false;
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        });
        // Connect to server button
        btConnect.setOnAction(actionEvent -> {
            System.out.println("Connecting...");
            try {
                s = new Socket(HOST, PORT);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new PrintWriter(s.getOutputStream());

//                System.out.println(in.readLine()); // TODO
                primaryStage.setScene(game);
                serverIn.start();
                drawBoard.start();
            } catch(ConnectException ce) {
                connection.setText("Could not connect to the server");
                System.out.println("Could not connect to the server.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Exit button
        btExit.setOnAction(actionEvent -> {
            connection.setText("Exiting");
            System.out.println("Exiting...");
            Platform.exit();
        });



        // Handle moves and server communication
        EventHandler<MouseEvent> mouseClick = mouseEvent -> {
            double mouseX = mouseEvent.getX();
            double mouseY = mouseEvent.getY();
            System.out.println("Clicked: " + mouseX + " " + mouseY);
            System.out.println();
            double boardX = -1;
            double boardY = -1;
            if (mouseX >= 100 && mouseX <= gc.getCanvas().getWidth()-100 && mouseY >= 100 && mouseY <= 700) {
                boardX = (mouseX - 100) / (gc.getCanvas().getWidth()-200) * 8;
                boardY = (mouseY - 100) / 600 * 8;
                System.out.println((int) boardX + " " + (int) boardY);
            }

            // TODO: send server message if yourTurn == true and set yourTurn to false
            System.out.println("MAKEMOVE" + " " + piece + " " + (int) boardX + " " + (int) boardY);
            if (board.isValidMove(piece, (int) boardX, (int) boardY) && yourTurn) {
            out.println("MAKEMOVE" + " " + piece + " " + (int) boardX + " " + (int) boardY);
            out.flush();
            board.makeMove(piece, (int) boardX, (int) boardY);
            yourTurn = false;
            }
        };

        game.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClick);


        primaryStage.setTitle("Othello");
        primaryStage.setScene(menu);
        primaryStage.setMinWidth(SCREEN_WIDTH);
        primaryStage.setMinHeight(SCREEN_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void draw(GraphicsContext gc) {

        double gridX = 100;
        double gridY = 100;
        double gridWidth = gc.getCanvas().getWidth()-200;
        double gridHeight = 600;

        // Draw background
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        gc.setFill(Color.BLACK);
        gc.setLineWidth(10);
        gc.fillText("Othello", 100, 20);
        String turn = "Opponent's turn";
        if (yourTurn)
            turn = "Your turn";

        gc.fillText(turn, 100, 50);

        gc.fillText("Black score: " + board.getScore("X"), 300, 25);
        gc.fillText("White score: " + board.getScore("O"), 300, 50);

        // Draw board background
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(gridX, gridY, gridWidth, gridHeight);

        // Draw grid lines
        for (int i=0; i<=board.getBoard().length; i++) {
            gc.strokeLine(gridX+gridWidth/8*i, gridY, gridX+gridWidth/8*i, gridY+gridHeight); // Vertical
            gc.strokeLine(gridX, gridY+gridHeight/8*i, gridX+gridWidth, gridY+gridHeight/8*i); // Horizontal
        }

        // Draw pieces
        for (int i=0; i<board.getBoard().length; i++) {
            for (int j=0; j<board.getBoard()[0].length; j++) {
                if (board.getValue(j, i).equals("X")) {
                    gc.setFill(Color.BLACK);
                    gc.fillOval(120+j*gridWidth/8, 110+i*gridHeight/8, 50, 50);
                } else if (board.getValue(j, i).equals("O")) {
                    gc.setFill(Color.WHITE);
                    gc.fillOval(120+j*gridWidth/8, 110+i*gridHeight/8, 50, 50);
                }
            }
        }
    }

    public void stop() {
        System.exit(0);
    }
}
