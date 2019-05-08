import board.Board;
import board.BoardField;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
        primaryStage.setTitle("Terrible chess");

        AnchorPane mainPane = new AnchorPane();
        TabPane tabPane = new TabPane();

        // create a TabPane
        tabPane.getTabs().add(new ChessTab("Chess "));

        Button addBtn = new Button();
        addBtn.setText("+");
        addBtn.setOnAction((ActionEvent event) -> {
            tabPane.getTabs().add(new ChessTab("Chess"));
        });
        mainPane.getChildren().addAll(tabPane, addBtn);

        AnchorPane.setLeftAnchor(tabPane,1.);
        AnchorPane.setTopAnchor(tabPane,1.);
        AnchorPane.setBottomAnchor(tabPane,1.);
        AnchorPane.setRightAnchor(tabPane,1.);

        AnchorPane.setRightAnchor(addBtn,1.);
        AnchorPane.setTopAnchor(addBtn,1.);

        primaryStage.setScene(new Scene(mainPane, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
