import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainWindow extends Application {
    final int SIZE = 8;

    /**
     * Vstupní metoda programu
     * @param args argumenty
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Vstupní metoda JavaFx
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Terrible chess");

        AnchorPane mainPane = new AnchorPane();
        TabPane tabPane = new TabPane();

        // create a TabPane
        tabPane.getTabs().add(new ChessTab("chess "));

        Button addBtn = new Button();
        addBtn.setText("+");
        addBtn.setOnAction((ActionEvent event) -> {
            tabPane.getTabs().add(new ChessTab("chess"));
        });
        mainPane.getChildren().addAll(tabPane, addBtn);

        AnchorPane.setLeftAnchor(tabPane,1.);
        AnchorPane.setTopAnchor(tabPane,1.);
        AnchorPane.setBottomAnchor(tabPane,1.);
        AnchorPane.setRightAnchor(tabPane,1.);

        AnchorPane.setRightAnchor(addBtn,1.);
        AnchorPane.setTopAnchor(addBtn,1.);

        primaryStage.setScene(new Scene(mainPane, 620, 450));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
