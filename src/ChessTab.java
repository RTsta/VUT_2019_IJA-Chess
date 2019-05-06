import board.Board;
import board.Field;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;

public class ChessTab extends Tab {

    ChessGame chessGame;
    GridPane chessGrid;
    int SIZE = 8;

    public ChessTab(String name){
        super();

        Board b = new Board(SIZE);
        this.chessGame = new ChessGame(b);
        this.chessGrid = createNewChessGame();
        super.setContent(chessGrid);
        super.setText(name);
    }

    GridPane createNewChessGame(){

            /*
        |VC|KC|SC|KC|DC|SC|KC|VC|  *  |18|28|38|48|58|68|78|88|
        |PC|PC|PC|PC|PC|PC|PC|PC|  *  |17|27|37|47|57|67|77|87|
        |  |  |  |  |  |  |  |  |  *  |16|26|36|46|56|66|76|86|
        |  |  |  |  |  |  |  |  |  *  |15|25|35|45|55|65|75|85|
        |  |  |  |  |  |  |  |  |  *  |14|24|34|44|54|64|74|84|
        |  |  |  |  |  |  |  |  |  *  |13|23|33|43|53|63|73|83|
        |PB|PB|PB|PB|PB|PB|PB|PB|  *  |12|22|32|42|52|62|72|82|
        |VB|KB|SB|DB|KB|SB|KB|VB|  *  |11|21|31|41|51|61|71|81|
     */

        GridPane chessGrid = new GridPane();
        for (int row = 0; row < SIZE; row++) {
            int c_id = SIZE-row;
            int r_id = 1;
            for (int col = 0; col < SIZE; col ++) {
                String squareID = (r_id)*10+(col+c_id)+"";

                StackPane square = new StackPane();
                String color ;

                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "black";
                }


                square.setId(squareID);
                r_id++;
                square.setStyle("-fx-background-color: "+color+";");
                square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        displayGame();

                    }
                });

                c_id--;
                chessGrid.add(square, col, row);
            }

        }
        for (int i = 0; i < SIZE; i++) {
            chessGrid.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            chessGrid.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        chessGrid.setPadding(new Insets(25, 25, 25, 25));

        return chessGrid;
    }

    private Node getChessTileAtCords(int row, int col){
        for (Node child : chessGrid.getChildren()){
            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col){
                return child;
            }
        }
        return null;
    }

    public void displayGame() {

        for (int row = 0; row < SIZE; row++) {
            int c_id = SIZE-row;
            int r_id = 1;
            for (int col = 0; col < SIZE; col ++) {
                String squareID = (r_id)*10+(col+c_id)+"";
                Board b = chessGame.getBoard();
                Field f = b.getField((col+c_id), r_id);
                Node tile = getChessTileAtCords(row, col);

                switch (f.get().getClass().getName()){
                    case "Kral":
                        if (f.get().isWhite()){
                            ImageView iv1 = new ImageView(new Image("/GUI/figureImages/icons8-bishop-50.png");
                            //@todo přidat obrázek do StackPane
                            //bila figurka
                        } else {
                            //cerna figurka
                        }
                        break;
                    case "Dama":
                        break;
                    case "Kun":
                        break;
                    case "Strelec":
                        break;
                    case "Vez":
                        break;
                    case "Pesak":
                        break;
                    default:
                        break;

                }

                r_id++;
            }
            c_id--;
        }

    }

}