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

public class ChessTab extends Tab {

    ChessGame chessGame;
    GridPane chessGrid;
    int SIZE = 8;
    String COLORB = "#7D7D7D";
    String COLORW = "white";
    Boolean evenClick;

    public ChessTab(String name){
        super();

        Board b = new Board(SIZE);
        this.chessGame = new ChessGame(b);
        this.chessGrid = createNewChessGame();
        super.setContent(chessGrid);
        super.setText(name);
        displayGame();
        evenClick = true;
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
                    color = COLORW;
                } else {
                    color = COLORB;
                }


                square.setId(squareID);
                r_id++;
                square.setStyle("-fx-background-color: "+color+";");
                square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (evenClick) {
                            System.out.println("Clicked at "+square.getId());
                            evenClick = false;
                        } else {
                            System.out.println("Clicked at "+square.getId());
                            evenClick = true;
                        }
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
        //Rychlejší alternativa
        //chessGrid.lookup((row)*10+(col)+"");

        return null;
    }

    public void displayGame() {

        Board b = chessGame.getBoard();

        for (int row = 0; row < SIZE; row++) {
            int c_id = SIZE-row;
            int r_id = 1;

            for (int col = 0; col < SIZE; col ++) {
                System.out.println("Figurka: row-"+(c_id+col)+",col-"+r_id+" | Policko: "+ (col*10+row));

                //String squareID = (r_id)*10+(col+c_id)+"";
                Field f = b.getField(r_id, (col+c_id));
                ImageView iv1;
                if (f.get() == null){
                    iv1 = new ImageView(new Image("/GUI/figureImages/empty.png"));
                    String color ;
                    if ((row + col) % 2 == 0) {
                        color = COLORW;
                    } else {
                        color = COLORB;
                    }
                    iv1.setStyle("-fx-background-color: "+color+";");
                } else {
                    switch (f.get().getClass().getName().toLowerCase()) {
                        case "figures.kral":
                            if (f.get().isWhite()) {
                                iv1 = new ImageView(new Image("/GUI/figureImages/kral_w.png"));
                                //bila figurka
                            } else {
                                iv1 = new ImageView(new Image("/GUI/figureImages/kral_b.png"));
                                //cerna figurka
                            }
                            break;
                        case "figures.dama":
                            if (f.get().isWhite()) {
                                iv1 = new ImageView(new Image("/GUI/figureImages/dama_w.png"));

                                //bila figurka
                            } else {
                                iv1 = new ImageView(new Image("/GUI/figureImages/dama_b.png"));
                                //cerna figurka
                            }
                            break;
                        case "figures.kun":
                            if (f.get().isWhite()) {
                                iv1 = new ImageView(new Image("/GUI/figureImages/kun_w.png"));
                                //bila figurka
                            } else {
                                iv1 = new ImageView(new Image("/GUI/figureImages/kun_b.png"));
                                //cerna figurka
                            }
                            break;
                        case "figures.strelec":
                            if (f.get().isWhite()) {
                                iv1 = new ImageView(new Image("/GUI/figureImages/strelec_w.png"));
                                //bila figurka
                            } else {
                                iv1 = new ImageView(new Image("/GUI/figureImages/strelec_b.png"));
                                //cerna figurka
                            }
                            break;
                        case "figures.vez":
                            if (f.get().isWhite()) {
                                iv1 = new ImageView(new Image("/GUI/figureImages/vez_w.png"));
                                //bila figurka
                            } else {
                                iv1 = new ImageView(new Image("/GUI/figureImages/vez_b.png"));
                                //cerna figurka
                            }
                            break;
                        case "figures.pesak":
                            if (f.get().isWhite()) {
                                iv1 = new ImageView(new Image("/GUI/figureImages/pesak_w.png"));
                                //bila figurka
                            } else {
                                iv1 = new ImageView(new Image("/GUI/figureImages/pesak_b.png"));
                                //cerna figurka
                            }
                            break;
                        default:
                            iv1 = null;
                            break;
                    }
                }
                if (iv1 != null) {
                    iv1.setId((col * 10 + row) + "");
                    chessGrid.add(iv1, col, row);
                    iv1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (evenClick) {
                                System.out.println("Clicked at "+iv1.getId());
                                evenClick = false;
                            } else {
                                System.out.println("Clicked at "+iv1.getId());
                                evenClick = true;
                            }
                        }
                    });

                }
                r_id++;
                c_id--;
            }

        }

    }

}