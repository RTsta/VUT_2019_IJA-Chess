import board.Board;
import board.BoardField;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainWindow extends Application {
    final int SIZE = 8;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Creating Tab");

        // create a tabpane
        TabPane tabPane = new TabPane();

        tabPane.getTabs().add(new ChessTab("Chess 1"));


        primaryStage.setScene(new Scene(tabPane, 400, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
