/**
 *
 * @author  Petr Hemza
 * @author Arthur Nácar
 * @version 1.0
 * @since   2019-04-25
 */
import board.Board;
import board.Field;
import chess.ChessGame;
import chess.Notation;
import figures.Figure;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChessTab extends Tab {

    private ChessGame chessGame;
    private GridPane chessGrid;
    private ListView sideListView;
    private VBox leftSideListBox;
    private TextArea sideImportTextArea;
    private HBox topNavigationButtons;
    private Button btn1;
    private Button rldBtn;
    private Button playBtn;
    private Button pauseBtn;
    private Button exportBtn;

    private Label checkLabel;
    private Label turnsLabel;


    private int SIZE = 8;
    private String COLORB = "#7D7D7D";
    private String COLORW = "white";
    private Boolean evenClick;
    private String srcClick;
    private String destClick;
    private String lastWhitesMove;
    private boolean gameTouched;

    private int lapsCounter;

    private boolean playPause;

    private char changeOfPawn;


    public ChessTab(String name){
        super();

        Board b = new Board(SIZE);
        this.chessGame = new ChessGame(b);

        AnchorPane anchor = new AnchorPane();
            this.leftSideListBox = new VBox();//----------------------------------
                sideListView = new ListView();
                sideListView.setCellFactory(TextFieldListCell.forListView());
                sideListView.setEditable(false);
                sideListView.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });
                this.sideImportTextArea = new TextArea();

                this.topNavigationButtons = new HBox();
                    this.btn1 = new Button();
                    btn1.setText("Importovat tahy");

                    Button btn2 = new Button();
                    btn2.setText("Undo");

                    Button btn3 = new Button();
                    btn3.setText("Redo");

                    this.rldBtn = new Button();
                    rldBtn.setText("RLD");

                    this.playBtn = new Button();
                    playBtn.setText(">");

                    this.pauseBtn = new Button();
                    pauseBtn.setText("||");

                    topNavigationButtons.getChildren().addAll(btn2, btn3, btn1);
                    topNavigationButtons.setAlignment(Pos.BOTTOM_LEFT);

                    this.exportBtn = new Button();
                    exportBtn.setText("Exportovat tahy");
                    exportBtn.setAlignment(Pos.CENTER);
                    exportBtn.setOnAction((ActionEvent event) -> {
                        String a = sideListView.itemsProperty().getValue().toString();
                        a = a.replace(", ","\n");
                        a = a.substring(1,a.length()-1);

                        Stage exportWindow = new Stage();
                        exportWindow.setTitle("Export");

                        FileChooser fileChooser = new FileChooser();

                        //Set extension filter for text files
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                        fileChooser.getExtensionFilters().add(extFilter);

                        //Show save file dialog

                        File file = fileChooser.showSaveDialog(exportWindow);

                        if (file != null) {
                            saveTextToFile(a, file);
                        }

                    });

                leftSideListBox.getChildren().addAll(topNavigationButtons, sideListView, sideImportTextArea);

                    btn1.setOnAction((ActionEvent event) -> {
                        leftSideListBox.getChildren().remove(sideImportTextArea);
                        topNavigationButtons.getChildren().remove(btn1);
                        topNavigationButtons.getChildren().addAll(rldBtn, playBtn,pauseBtn);
                        leftSideListBox.getChildren().add(exportBtn);
                        gameTouched = true;
                        if (!importMoves(sideImportTextArea.getText())) {
                            Stage errorWindow = new Stage();
                            errorWindow.setTitle("Chyba");
                            AnchorPane errorPane = new AnchorPane();
                            Label errorMessage = new Label("Neplatný formát notace");
                            Button okBtn = new Button("OK");
                            okBtn.setOnAction((ActionEvent _event) -> {
                                errorWindow.close();
                            });
                            errorPane.getChildren().addAll(errorMessage,okBtn);
                            AnchorPane.setBottomAnchor(okBtn,10.);
                            AnchorPane.setRightAnchor(okBtn,10.);
                            AnchorPane.setTopAnchor(errorMessage, 20.);
                            errorMessage.setAlignment(Pos.CENTER);
                            okBtn.setAlignment(Pos.BOTTOM_RIGHT);
                            Scene errorScene = new Scene(errorPane,300,150);
                            errorWindow.setScene(errorScene);
                            errorWindow.setResizable(false);
                            errorWindow.show();
                        }
                    });
                    btn2.setOnAction((ActionEvent) -> {
                        chessGame.undo();
                        sideListView.getSelectionModel().select(chessGame.getListPos()/2);
                        displayGame();
                    });
                    btn3.setOnAction((ActionEvent) -> {
                        chessGame.redo();
                        sideListView.getSelectionModel().select(chessGame.getListPos()/2);
                        displayGame();
                    });
                    this.rldBtn.setOnAction((ActionEvent) ->{
                        this.reload();
                        displayGame();
                    });
                    this.playBtn.setOnAction((ActionEvent) ->{
                        this.playPause = true;
                        while (this.playPause && chessGame.getListPos() < chessGame.getSizeOfList()-1) {
                            chessGame.redo();
                            //try {
                            //    TimeUnit.SECONDS.sleep(2);
                            //} catch (InterruptedException e) {}
                            displayGame();
                        }
                    });

                    this.pauseBtn.setOnAction((ActionEvent) ->{
                        this.playPause = false;

                    });
                                                                                    //----------------------------------
            VBox rightChessBox = new VBox();
                HBox chessWithNumber = new HBox();//----------------------------------
                    GridPane rowNumbers = new GridPane();
                    for (int i = 0; i < chessGame.getBoard().getSize();i++){
                        StackPane square = new StackPane();
                        Label number = new Label((8-i)+"");
                        number.setAlignment(Pos.CENTER);
                        square.setMinSize(20,45);
                        square.getChildren().add(number);
                        rowNumbers.add(square,0,i);
                    }
                    rowNumbers.setPadding(new Insets(25, 0, 0, 10));
                    chessWithNumber.getChildren().add(rowNumbers);
                    this.chessGrid = createNewChessGrid();
                    chessWithNumber.getChildren().add(this.chessGrid);
                    //spodní panel s písmeny

                HBox colAlphabet = new HBox();
                    GridPane colNumbers = new GridPane();
                    for (int i = 0; i < chessGame.getBoard().getSize();i++){
                        StackPane square = new StackPane();
                        int a ='A'+i;
                        char labelChar = (char) a;
                        Label number = new Label(labelChar+"");
                        number.setAlignment(Pos.CENTER);
                        square.getChildren().add(number);
                        square.setMinSize(45,20);
                        number.setAlignment(Pos.CENTER);
                        colNumbers.add(square,i,0);

                    }
                    colNumbers.setPadding(new Insets(0,0,0,30));
                    colAlphabet.getChildren().add(colNumbers);

                HBox chessStatusBox = new HBox();

                    this.checkLabel = new Label();
                    this.checkLabel.setText("");

                    this.turnsLabel = new Label();
                    this.turnsLabel.setText("Táhne bílý");

                    this.turnsLabel.setAlignment(Pos.BOTTOM_LEFT);
                    checkLabel.setAlignment(Pos.BOTTOM_RIGHT);
                    turnsLabel.setPadding(new Insets(0,100,0,10));
                    checkLabel.setPadding(new Insets(0,10,0,100));
                    StackPane text1box = new StackPane(turnsLabel);
                    StackPane text2box = new StackPane(checkLabel);
                chessStatusBox.getChildren().addAll(text1box,text2box);
                text1box.setAlignment(Pos.CENTER_LEFT);
                text2box.setAlignment(Pos.CENTER_RIGHT);
                chessStatusBox.setMinHeight(40);

                rightChessBox.getChildren().addAll(chessWithNumber,colAlphabet,chessStatusBox);

        // Anchor the controls
            anchor.getChildren().addAll(rightChessBox, leftSideListBox);

            AnchorPane.setLeftAnchor(rightChessBox,1.);
            AnchorPane.setTopAnchor(rightChessBox,1.);
            AnchorPane.setBottomAnchor(rightChessBox,1.);

            AnchorPane.setBottomAnchor(leftSideListBox,1.);
            AnchorPane.setRightAnchor(leftSideListBox,1.);
            AnchorPane.setTopAnchor(leftSideListBox,1.);
            AnchorPane.setLeftAnchor(leftSideListBox, 400.);

        super.setContent(anchor);
        //super.setContent(chessGrid);
        super.setText(name);
        displayGame();
        evenClick = true;

        srcClick = "";
        destClick = "";
        lastWhitesMove = "";
        gameTouched = false;

        lapsCounter = 1;

        playPause = false;

        changeOfPawn = '-';
    }

    /**
     * Metoda k vytvoření GUI šachového pole
     * @return GridPane Vytvořená šachová deska
     */
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

        chessGrid.setPadding(new Insets(25, 25, 0, 0));

        return chessGrid;
    }

    /**
     * Metoda která překreslí celou hru podle aktuálního stavu Boardu
     * <p> Mění pozici figurek na GUI hrací desky
     * Aktualizuje Labels</p>
     */
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

        if (chessGame.isWhitesTurn()){ turnsLabel.setText("Táhne - bílý");}
        else {turnsLabel.setText("Táhne - černý");}

        checkLabel.setText("ŠACH\n");
        if (chessGame.isCheck(true)){ checkLabel.setText(checkLabel.getText()+"- bílý\n"); }
        if (chessGame.isCheck(false)){ checkLabel.setText(checkLabel.getText()+"- cerny\n"); }

        if (!(chessGame.isCheck(true) || chessGame.isCheck(false))){checkLabel.setText("");}
    }

    /**
     * Metoda provádí zpracovávající pohyby na GUI hraci desky po kliknutí na jednotlivá políčka
     * @param id Políčka, na které bylo kliknuto ( id = "col" + "row")
     */
    private void gameMove(String id){
        if (chessGame.isMate(true) || chessGame.isMate(false)){ return; }

        while ((chessGame.getListPos()+1)/2 < sideListView.getItems().size()) {
            if (!chessGame.isWhitesTurn()) {
                String list = sideListView.itemsProperty().getValue().toString();
                list = list.replace(", ","\n");
                list = list.substring(1,list.length()-1);
                String items[] = list.split("\n");
                lastWhitesMove = items[items.length-1].split(" ")[1];
            }
            sideListView.getItems().remove(sideListView.getItems().size()-1);
            lapsCounter--;
        }

        if (gameTouched == false){
            leftSideListBox.getChildren().remove(sideImportTextArea);
            topNavigationButtons.getChildren().remove(btn1);
            topNavigationButtons.getChildren().addAll(rldBtn, playBtn,pauseBtn);
            leftSideListBox.getChildren().add(exportBtn);
            gameTouched = true;
        }
        if (evenClick) {
            if (chessGame.getBoard().getField(parseColFromID(id), parseRowFromID(id)).get()!= null && chessGame.isWhitesTurn() == chessGame.getBoard().getField(parseColFromID(id), parseRowFromID(id)).get().isWhite()) {
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
                int pocetPostavicekVeHrePredTahem = chessGame.getNumberOfAliveFigures();
                Boolean a = chessGame.move(chessGame.getBoard().getField(s_col, s_row).get(), chessGame.getBoard().getField(d_col,d_row));
                if (a) {
                    int pocetPostavicekVeHrePoTahu = chessGame.getNumberOfAliveFigures();
                    changeOfPawn = '-';
                    Figure tmpFig = chessGame.getBoard().getField(d_col,d_row).get();
                    if ((!chessGame.isMate(true) && !chessGame.isMate(false)) && tmpFig != null && tmpFig.getClass().getName().toLowerCase().compareTo("figures.pesak") == 0) {
                        if ((tmpFig.isWhite() && tmpFig.getRow() == 8) || (!tmpFig.isWhite() && tmpFig.getRow() == 1)){
                            Stage winWindow = new Stage();
                            winWindow.setTitle("Vyber postavičku");
                            VBox vbox = new VBox();
                            HBox buttonPane = new HBox();
                            Label winMessage = new Label("Vyber si figurku");
                            vbox.getChildren().addAll(winMessage, buttonPane);
                            //strelec, jezdec, vez,
                            Button damaBtn = new Button("Dáma");
                            damaBtn.setOnAction((ActionEvent _event) -> {
                                chessGame.changePawn(chessGame.getBoard().getField(d_col,d_row),'D');
                                winWindow.close();
                                changeOfPawn = 'D';
                                displayGame();
                            });
                            Button strelecBtn = new Button("Střelec");
                            strelecBtn.setOnAction((ActionEvent _event) -> {
                                chessGame.changePawn(chessGame.getBoard().getField(d_col,d_row),'S');
                                winWindow.close();
                                changeOfPawn = 'S';
                                displayGame();
                            });
                            Button jezdecBtn = new Button("Jezdec");
                            jezdecBtn.setOnAction((ActionEvent _event) -> {
                                chessGame.changePawn(chessGame.getBoard().getField(d_col,d_row),'J');
                                winWindow.close();
                                changeOfPawn = 'J';
                                displayGame();
                            });
                            Button vezBtn = new Button("Věž");
                            vezBtn.setOnAction((ActionEvent _event) -> {
                                chessGame.changePawn(chessGame.getBoard().getField(d_col,d_row),'V');
                                winWindow.close();
                                changeOfPawn = 'V';
                                displayGame();
                            });

                            buttonPane.getChildren().addAll(damaBtn,strelecBtn,jezdecBtn,vezBtn);

                            winMessage.setAlignment(Pos.CENTER);
                            buttonPane.setAlignment(Pos.CENTER);
                            Scene errorScene = new Scene(vbox,300,150);
                            winWindow.setScene(errorScene);
                            winWindow.setResizable(false);
                            winWindow.show();
                        }
                    }
                    while (chessGame.getListPos()/2 < sideListView.getItems().size()) {
                        sideListView.getItems().remove(sideListView.getItems().size()-1);
                    }
                    if (chessGame.isWhitesTurn()){
                        String q1 = (chessGame.isMate(false) ? "#" : (chessGame.isCheck(false) ? "+" : ""));
                        lastWhitesMove = chessGame.getBoard().getField(d_col, d_row).get().getShortcut() + ((char) ((s_col) + 'a' - 1)) + s_row +(pocetPostavicekVeHrePredTahem <= pocetPostavicekVeHrePoTahu? "":"x") + ((char) (d_col + 'a' - 1)) + d_row + (changeOfPawn == '-' ? "" : changeOfPawn)+q1;
                    }
                    else {
                        String q1 = (chessGame.isMate(true) ? "#" : (chessGame.isCheck(true) ? "+" : ""));
                        sideListView.getItems().add(lapsCounter + ". " + lastWhitesMove +" "+chessGame.getBoard().getField(d_col, d_row).get().getShortcut() + ((char) ((s_col) + 'a' - 1)) + s_row +(pocetPostavicekVeHrePredTahem <= pocetPostavicekVeHrePoTahu? "":"x")+ ((char) (d_col + 'a' - 1)) + d_row + (changeOfPawn == '-' ? "" : changeOfPawn) + q1);
                        lastWhitesMove = "";
                        lapsCounter++;
                    }
                    chessGame.setWhitesTurn(!chessGame.isWhitesTurn());
                    displayGame();
                }
            }
            srcClick = "";
            destClick = "";
            evenClick = true;
        }

        if (chessGame.isMate(true) || chessGame.isMate(false)){
            Stage winWindow = new Stage();
            winWindow.setTitle("Vyhrál jsi!");
            AnchorPane winPane = new AnchorPane();
            Label winMessage = new Label("Vyhrál hráč na "+ (chessGame.isMate(true) ? "bílé": "černé"));
            Button okBtn = new Button("OK");
            okBtn.setOnAction((ActionEvent _event) -> {
                winWindow.close();
            });
            winPane.getChildren().addAll(winMessage,okBtn);
            AnchorPane.setBottomAnchor(okBtn,10.);
            AnchorPane.setRightAnchor(okBtn,10.);
            AnchorPane.setTopAnchor(winMessage, 20.);
            winMessage.setAlignment(Pos.CENTER);
            okBtn.setAlignment(Pos.BOTTOM_RIGHT);
            Scene errorScene = new Scene(winPane,300,150);
            winWindow.setScene(errorScene);
            winWindow.setResizable(false);
            winWindow.show();
        }
    }

    /**
     * Pomocná metoda k získání sloupce z id políčka
     * @param id Políčka, na které bylo kliknuto ( id = "col" + "row")
     * @return int číslo sloupce
     */
    private int parseColFromID(String id){
        return Integer.parseInt(id)/10;
    }

    /**
     * Pomocná metoda k získání řádku z id políčka
     * @param id Políčka, na které bylo kliknuto ( id = "col" + "row")
     * @return int číslo řádku
     */
    private int parseRowFromID(String id){
        return Integer.parseInt(id)%10;
    }

    /**
     * Metoda k načtení vstupní notace
     * @param moves textový řetězec s notací
     * @return boolean True - pokud byla notace ve správném formátu
     */
    private boolean importMoves(String moves) {
        Notation notation = new Notation(moves);
        while (!notation.isEnd()) {
            if (notation.getMove(chessGame.getBoard())) {
                if (!notationMove(notation,true)) {
                    return false;
                }
                if (!notationMove(notation,false)) {
                    return false;
                }
                sideListView.getItems().add(notation.getCurrentNotation());
                lapsCounter++;
            } else { return false; }
        }
        this.reload();
        displayGame();
        return true;
    }

    private boolean isEndOfGame(){return false;}

    /**
     * Metoda provádějící pohyb dle zadané notace
     * @param notation Notace, podle které má být proveden tah
     * @param isWhite Barva hráče
     * @return boolean True - vše proběhlo bez problému
     */
    private boolean notationMove(Notation notation, boolean isWhite) {
        int numFig = chessGame.getNumberOfAliveFigures();
        if (!chessGame.move(chessGame.getBoard().getField(notation.getSrcCol(isWhite), notation.getSrcRow(isWhite)).get(),chessGame.getBoard().getField(notation.getDecCol(isWhite),notation.getDesRow(isWhite)))) {
            return false;
        }
        if (notation.isTakeFigure(isWhite) && numFig-1 != chessGame.getNumberOfAliveFigures()) {
            return false;
        }
        /*
        if (chessGame.isCheck(isWhite) != notation.isCheck(isWhite)) {
            return false;
        }*/
        if (chessGame.isMate(isWhite) != notation.isMate(isWhite)) {
            return false;
        }
        return true;
    }
    /**
     * Metoda, která přetočí hru na začátek
     */
    private void reload() {
        while (chessGame.getListPos() >= 0) {
            chessGame.undo();
        }
    }

    /**
     * Metoda, která ukládá text do souboru
     * @param content Text určený k uložení
     * @param file Cílový soubor, kam má být text zapsán
     */
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(ChessTab.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}