import board.Board;
import board.Field;
import javafx.collections.ObservableList;
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

    private ChessGame chessGame;
    private GridPane chessGrid;
    private int SIZE = 8;
    private String COLORB = "#7D7D7D";
    private String COLORW = "white";
    private Boolean evenClick;
    private String srcClick;
    private String destClick;

    private Boolean whitesTurn;


    public ChessTab(String name){
        super();

        Board b = new Board(SIZE);
        this.chessGame = new ChessGame(b);
        this.chessGrid = createNewChessBoard();
        super.setContent(chessGrid);
        super.setText(name);
        displayGame();
        evenClick = true;

        srcClick = "";
        destClick = "";

        whitesTurn = true;
    }

    GridPane createNewChessBoard(){

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
                        gameMove(square.getId());
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

    public void displayGame() {
        Board b = this.chessGame.getBoard();

        if (chessGrid.getChildren().size() > 64) {
            for (int i = chessGrid.getChildren().size()-1; i > 63; i--){
                chessGrid.getChildren().remove(i);
            }
        }

        for (int row = 0; row < SIZE; row++) {
            int c_id = SIZE-row;
            int r_id = 1;

            for (int col = 0; col < SIZE; col ++) {
                String curFieldID = (r_id)*10+(col+c_id)+"";

                Field f = b.getField(r_id, (col+c_id));
                ImageView iv1;
                if (f.get() == null){
                    iv1 = new ImageView(new Image("/GUI/figureImages/empty.png"));
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
                    iv1.setId((r_id*10+(c_id+col)) + "");
                    chessGrid.add(iv1, col, row);
                    iv1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            gameMove(iv1.getId());
                        }
                    });

                }
                r_id++;
                c_id--;
            }

        }

    }

    private void gameMove(String id){
        if (evenClick) {
            if (whitesTurn == chessGame.getBoard().getField(parseColFromID(id), parseRowFromID(id)).get().isWhite()) {
                srcClick = id;
                evenClick = false;
            }
        } else {
            destClick = id;
            if (srcClick != destClick){
                System.out.println("Click:   "+evenClick);
                System.out.println("src:     "+srcClick);
                System.out.println("dest:    "+destClick);
                System.out.println("===========================================");

                Boolean a = chessGame.move(chessGame.getBoard().getField(parseColFromID(srcClick), parseRowFromID(srcClick)).get(), chessGame.getBoard().getField(parseColFromID(destClick),parseRowFromID(destClick)));
                if (a) {
                    whitesTurn = !whitesTurn;
                    displayGame();
                }
            }

            srcClick = "";
            destClick = "";
            evenClick = true;
        }
    }


    private int parseColFromID(String id){
        return Integer.parseInt(id)/10;
    }

    private int parseRowFromID(String id){
        return Integer.parseInt(id)%10;
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

}