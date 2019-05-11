import chess.ChessGame;
import chess.Notation;
import board.Board;
import board.Field;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class ChessTab extends Tab {

    private ChessGame chessGame;
    private GridPane chessGrid;
    private ListView sideListView;


    private int SIZE = 8;
    private String COLORB = "#7D7D7D";
    private String COLORW = "white";
    private Boolean evenClick;
    private String srcClick;
    private String destClick;
    private String lastWhitesMove;

    private Boolean whitesTurn;
    private int lapsCounter;


    public ChessTab(String name){
        super();

        Board b = new Board(SIZE);
        this.chessGame = new ChessGame(b);
        this.chessGrid = createNewChessGrid();

            sideListView = new ListView();
            sideListView.setCellFactory(TextFieldListCell.forListView());
            sideListView.setEditable(false);

            TextArea sideImportTextArea = new TextArea();

            Button btn1 = new Button();
            btn1.setText("Importovat tahy");
            btn1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!importMoves(sideImportTextArea.getText())) {
                        // TODO error okno
                    }
                }
            });
            Button btn2 = new Button();
            btn2.setText("Undo");


            Button btn3 = new Button();
            btn3.setText("Redo");

            HBox bottomNavigationButtons = new HBox(btn1, btn2, btn3);
            bottomNavigationButtons.setAlignment(Pos.BOTTOM_RIGHT);
            VBox sideListBox = new VBox(sideListView, sideImportTextArea,bottomNavigationButtons);

            btn1.setOnAction((ActionEvent event) -> {
                sideListBox.getChildren().remove(sideImportTextArea);
                bottomNavigationButtons.getChildren().remove(btn1);
            });

            btn2.setOnAction((ActionEvent) -> {
                chessGame.undo();
                //TODO vymazat poslední záznam v sideListView
                displayGame();
            });


        // Anchor the controls
        AnchorPane anchor = new AnchorPane();
        anchor.getChildren().addAll(chessGrid, sideListBox);

        AnchorPane.setLeftAnchor(chessGrid,1.);
        AnchorPane.setTopAnchor(chessGrid,1.);
        AnchorPane.setBottomAnchor(chessGrid,1.);

        AnchorPane.setBottomAnchor(sideListBox,1.);
        AnchorPane.setRightAnchor(sideListBox,1.);
        AnchorPane.setTopAnchor(sideListBox,1.);
        AnchorPane.setLeftAnchor(sideListBox, 400.);

        super.setContent(anchor);
        //super.setContent(chessGrid);
        super.setText(name);
        displayGame();
        evenClick = true;

        srcClick = "";
        destClick = "";
        lastWhitesMove = "";

        whitesTurn = true;
        lapsCounter = 1;
    }

   private GridPane createNewChessGrid(){

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
            if (chessGame.getBoard().getField(parseColFromID(id), parseRowFromID(id)).get()!= null && whitesTurn == chessGame.getBoard().getField(parseColFromID(id), parseRowFromID(id)).get().isWhite()) {
                srcClick = id;
                evenClick = false;
            }
        } else {
            destClick = id;
            if (srcClick != destClick){
                int s_col = parseColFromID(srcClick);
                int s_row = parseRowFromID(srcClick);
                int d_col = parseColFromID(destClick);
                int d_row = parseRowFromID(destClick);
                Boolean a = chessGame.move(chessGame.getBoard().getField(s_col, s_row).get(), chessGame.getBoard().getField(d_col,d_row));
                if (a) {
                    if (whitesTurn){
                        lastWhitesMove = chessGame.getBoard().getField(d_col, d_row).get().getShortcut() + ((char) ((s_col) + 'a' - 1)) + s_row + ((char) (d_col + 'a' - 1)) + d_row;
                    }
                    else {
                        sideListView.getItems().add(lapsCounter + ". " + lastWhitesMove +" "+chessGame.getBoard().getField(d_col, d_row).get().getShortcut() + ((char) ((s_col) + 'a' - 1)) + s_row + ((char) (d_col + 'a' - 1)) + d_row);
                        lastWhitesMove = "";
                        lapsCounter++;
                    }
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

    private boolean importMoves(String moves) {
        Notation notation = new Notation(moves);
        while (!notation.isEnd()) {
            if (notation.getMove(chessGame.getBoard())) {
                notationMove(notation,true);
                notationMove(notation,false);
                sideListView.getItems().add(notation.getCurrentNotation());
                lapsCounter++;
            } else { return false; }
        }
        return true;
    }

    private void notationMove(Notation notation, boolean isWhite) {
        chessGame.move(chessGame.getBoard().getField(notation.getSrcCol(isWhite), notation.getSrcRow(isWhite)).get(),chessGame.getBoard().getField(notation.getDecCol(isWhite),notation.getDesRow(isWhite)));
        //if (notation.isTakeFigure(true)) {}
        if (notation.isCheck(isWhite)) {
            //System.out.println("Sach");
            //TODO sach bila/cerna
        }
        if (notation.isMate(isWhite)) {
            //System.out.println("Mat");
            //TODO mat bila/cerna
        }
        displayGame();
    }
}