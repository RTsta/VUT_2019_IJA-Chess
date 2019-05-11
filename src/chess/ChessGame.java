package chess;

import board.Board;
import board.Field;
import figures.*;
import java.util.ArrayList;
import java.util.List;

public class ChessGame implements Game {

    private Board board;
    private int numberOfAliveFigures;
    private boolean whitesTurn;
    private boolean whiteCheck; // bílý dává šach
    private boolean blackCheck; //černý dává šach
    private boolean whiteMate;
    private boolean blackMate;
    private List<Move> list;
    private int listPos;

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
    public ChessGame(Board board){
        this.whiteCheck = false;
        this.blackCheck = false;
        this.whiteMate = false;
        this.blackMate = false;
        this.board = board;
        this.list = new ArrayList<Move>();
        this.listPos = -1;
        this.whitesTurn = true;

        /* put bílých figurek*/
        board.getField(1,1).put(new Vez(1,1,true));
        board.getField(2,1).put(new Kun(2,1,true));
        board.getField(3,1).put(new Strelec(3,1,true));
        board.getField(4,1).put(new Dama(4,1,true));
        board.getField(5,1).put(new Kral(5,1,true));
        board.getField(6,1).put(new Strelec(6,1,true));
        board.getField(7,1).put(new Kun(7,1,true));
        board.getField(8,1).put(new Vez(8,1, true));

        for(int i = 1; i <= board.getSize(); i++) {
            board.getField(i, 2).put(new Pesak(i, 2, true));
        }

        /* put cernych figurek*/
        board.getField(1,8).put(new Vez(1,8,false));
        board.getField(2,8).put(new Kun(2,8,false));
        board.getField(3,8).put(new Strelec(3,8,false));
        board.getField(4,8).put(new Dama(4,8,false));
        board.getField(5,8).put(new Kral(5,8,false));
        board.getField(6,8).put(new Strelec(6,8,false));
        board.getField(7,8).put(new Kun(7,8,false));
        board.getField(8,8).put(new Vez(8,8, false));

        for(int i = 1; i <= board.getSize(); i++) {
            board.getField(i, 7).put(new Pesak(i, 7, false));
        }
        this.numberOfAliveFigures = 32;
    }

    public Board getBoard(){
        return this.board;
    }

    /**
     * Přesune figuru na zadané políčko, pokud je to možné. Pokud je operace úspěšná, lze provést její reverzní krok (tj. vrátit tah zpět) vyvoláním operace undo().
     * @param figure - Přesunovaná figura.
     * @param field - Cílové pole.
     * @return Úspěšnost operace.
     */
    @Override
    public boolean move(Figure figure, Field field){
        Field prevf = this.board.getField(figure.getCol(), figure.getRow());
        Figure f = field.get();
        boolean b = figure.move(field, this.board, true);
        if(b){
            if (f != null) {
                this.numberOfAliveFigures--;
            }
            while (this.listPos < this.list.size()-1) {
                this.list.remove(this.list.size()-1);
            }
            this.list.add(new Move(prevf, figure, f, field));
            this.listPos++;
            testCheck();
        }
        return b;
    }
    /**
     * Provede reverzní operaci (tah) nad hrou. Opakovaným voláním této operace lze vrátit všechny dosud provedené tahy až na začátek hry.
     */
    @Override
    public void undo(){
        if (this.list.size() > 0 && this.listPos >= 0){
            Move m = this.list.get(this.listPos);
            m.getToField().remove();
            m.getField().put(m.getFigure());
            if(m.getRemoved() != null){
                m.getToField().put(m.getRemoved());
                this.numberOfAliveFigures++;
            }
            this.listPos--;
            this.whitesTurn = !this.whitesTurn;
        }
        testCheck();
    }

    /**
     * Provede reverzní operaci (tah) k metodě Undo. Opakovaným voláním této operace lze vrátit všechny dosud provedené tahy až na konec hry.
     */
    @Override
    public void redo(){
        if (this.list.size() > 0 && this.listPos < this.list.size()-1){
            Move m = this.list.get(this.listPos+1);
            if (m.getRemoved() != null) {
                this.numberOfAliveFigures--;
            }
            m.getField().remove();
            m.getToField().remove();
            m.getToField().put(m.getFigure());
            this.listPos++;
            this.whitesTurn = !this.whitesTurn;
        }
        testCheck();
    }

    /**
     * Metoda testující jestli nastal šach ve hře a případně nastavuje příznak check
     *
     * <p>Nejprve se naleznou oba králové. Pokud nebyli nalezeni -> mat a konec hry.
     * Jinak se u všech figurek na šachovnici vyzkouší, zda mohou protivníkova krále vyhodit</p>
     * */
    private void testCheck() {
        this.whiteCheck = false;
        this.blackCheck = false;
        this.whiteMate = false;
        this.blackMate = false;
        Figure kingW = null;
        Figure kingB = null;
        for (int loopCol = 1; loopCol <= this.board.getSize(); loopCol++) {
            for (int loopRow = 1; loopRow <= this.board.getSize(); loopRow++) {
                Field tmpField = this.board.getField(loopCol, loopRow);
                if (tmpField != null) {
                    if (tmpField.get() != null) {
                        if (tmpField.get().getClass().getName().toLowerCase().compareTo("figures.kral") == 0) {
                            if (tmpField.get().isWhite()) {
                                kingW = tmpField.get();
                            } else {
                                kingB = tmpField.get();
                            }
                        }
                    }
                }
            }
        }
        if (kingW == null || kingB == null) {
            this.whiteMate = (kingW == null);
            this.blackMate = (kingB == null);
            return;
        }
        for (int loopCol = 1; loopCol <= this.board.getSize(); loopCol++) {
            for (int loopRow = 1; loopRow <= this.board.getSize(); loopRow++) {
                Field tmpField = this.board.getField(loopCol, loopRow);
                if (tmpField != null) {
                    Figure tmpFig = tmpField.get();
                    if (tmpFig != null) {
                        if (tmpFig.isWhite()) {
                            if (tmpFig.move(board.getField(kingB.getCol(),kingB.getRow()),board,false)) {
                                this.blackCheck = true;
                            }
                        } else {
                            if (tmpFig.move(board.getField(kingW.getCol(),kingW.getRow()),board,false)) {
                                this.whiteCheck = true;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Metoda sloužící pro zjištění šachu určeného hráče.
     * @param isWhite Barva hráče
     * @return boolean Je šach?
     */
    public boolean isCheck(boolean isWhite) {
        return isWhite ? this.whiteCheck : this.blackCheck;
    }

    /**
     * Metoda sloužící pro zjištění matu určeného hráče.
     * @param isWhite Barva hráče
     * @return boolean Je mat?
     */
    public boolean isMate(boolean isWhite){
        return isWhite ? this.whiteMate : this.blackMate;
    }

    /**
     * Metoda nastavuje, který hráč je na řadě
     * @param isWhite Barva hráče
     */
    public void setWhitesTurn(boolean isWhite){
        this.whitesTurn = isWhite;
    }

    /**
     * Zjištění, který hráč je na řadě
     * @return boolean True, pokud je na řadě bílý hráč
     */
    public boolean isWhitesTurn() {
        return this.whitesTurn;
    }

    /**
     * Metoda, která vrací pozici v listu
     * @return int pozice
     */
    public int getListPos() {
        return this.listPos;
    }

    /**
     * Metoda vrátí počet položek v listu
     * @return int počet položek
     */
    public int getSizeOfList() {
        return this.list.size();
    }

    /**
     * Metoda na výměnu pešáka za zvolenou figurku
     * @param field Políčko, na kterém je pěšák
     * @param type Typ figurky, za kterou se bude pěšák měnit
     */
    public void changePawn(Field field, char type) {
        boolean isWhite = field.get().isWhite();
        int col = field.get().getCol();
        int row = field.get().getRow();
        field.remove();
        switch (type) {
            case 'D':
                field.put(new Dama(col,row,isWhite));
                break;
            case 'J':
                field.put(new Kun(col,row,isWhite));
                break;
            case 'S':
                field.put(new Strelec(col,row,isWhite));
                break;
            case 'V':
                field.put(new Vez(col,row,isWhite));
                break;
            default:
                break;
        }
    }

    /**
     * Metoda, která vrátí počet figurek ve hře
     * @return int Počet figurek
     */
    public int getNumberOfAliveFigures() {
        return this.numberOfAliveFigures;
    }

    private class Move{

        private Field ffrom;
        private Figure what;
        private Figure prevF;
        private Field tof;

        public Move(Field ffrom, Figure what, Figure prevF, Field tof){
            this.ffrom = ffrom;
            this.what = what;
            this.prevF = prevF;
            this.tof = tof;
        }

        /**
         * Kterou figurkou se hýbalo.
         * @return Figure Figurka
         */
        public Figure getFigure(){
            return this.what;
        }

        /**
         * Odkud se figurka při tahu pohybovala
         * @return Field políčko
         */
        public Field getField(){
            return this.ffrom;
        }

        /**
         * Která figurka byla v tahu vyhozena
         * @return Figure Vyhozená figurka
         */
        public Figure getRemoved(){
            return this.prevF;
        }

        /**
         * Políčko, na které se uskutečnil v tahu přesun
         * @return Field políčko
         */
        public Field getToField(){
            return this.tof;
        }
    }
}


