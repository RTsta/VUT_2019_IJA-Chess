package chess;

import board.Board;
import board.Field;
import figures.*;

import java.util.Stack;

public class ChessGame implements Game {

    private Board board;
    //private int numberOfAliveFigures;
    private boolean whiteCheck; // bílý dává šach
    private boolean blackCheck; //černý dává šach
    private Stack<Move> stack;

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
        this.board = board;
        this.stack = new Stack<Move>();

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
        //numberOfAliveFigures = 32;
    }

    public Board getBoard(){
        return this.board;
    }

    @Override
    public boolean move(Figure figure, Field field){
        Field prevf = this.board.getField(figure.getCol(), figure.getRow());
        Figure f = field.get();
        boolean b = figure.move(field, this.board, true);
        if(b){
            this.stack.push(new Move(prevf, figure, f, field));
            testCheck();
        }
        return b;
    }

    @Override
    public void undo(){
        if(!this.stack.empty()){
            Move m = stack.pop();
            m.getToField().remove();
            m.getField().put(m.getFigure());
            if(m.getRemoved() != null){
                m.getToField().put(m.getRemoved());
            }

        }
        testCheck();
    }

    /*
    * @brief metoda testující jestli nastal šach ve hře a případně nastavuje příznak check
    *
    * - procházím postupně ceslou šachovnici a z každého poláčka si vyberu tmpFigure
    * dalším cyklem postupně testuji všechny místa šachovnice, jestli na ně může figurka jít
    * v případě úspěchu, že figurka na to místo může jít, tak testuji, jeestli náhodu na tom místě není král a pokud je, tak se nastavuje příznak check na true
    * */

    private void testCheck(){

    }

    private boolean isMate(){
        int numberOfKings= 0;
        for (int loopCol = 1; loopCol < this.board.getSize()+1;loopCol++){
            for (int loopRow = 1; loopRow < this.board.getSize()+1;loopRow++) {
                if (this.board.getField(loopRow, loopRow).get().getClass().getName().equals("figures.kral")){
                    numberOfKings++;
                }
            }
        }
        if (numberOfKings < 2){
            return true;
        } else {
            return false;
        }
    }

    public boolean isWhiteCheck() {return this.whiteCheck;}
    public boolean isBlackCheck(){return this.blackCheck;}

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

        public Figure getFigure(){
            return this.what;
        }

        public Field getField(){
            return this.ffrom;
        }

        public Figure getRemoved(){
            return this.prevF;
        }

        public Field getToField(){
            return this.tof;
        }

    }
}


