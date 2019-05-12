/**
 * Třída rozšiřující třídu AbstractFigure a implementueje metody spojené s konkrétní figurkou
 * @author  Petr Hemza
 * @author Arthur Nácar
 * @version 1.0
 * @since   2019-04-25
 */
package figures;

import board.Board;
import board.BoardField;
import board.Field;
/*
|  |  |  |  |  |  |  |  |
|  |  |  |  |  |  |  |  |
|  |  |  |  |  |  |  |  |
|  |  |35|  |55|  |  |  |
|  |24|  |  |  |64|  |  |
|  |  |  |::|  |  |  |  |
|  |22|  |  |  |24|  |  |
|  |  |31|  |51|  |  |  |
*/
public class Kun extends AbstractFigure {

    public Kun(int col, int row, boolean isWhite){
        super(col, row, isWhite);
        shortcut = "J";
    }

    /* vždy skáču takto -> |startRow - endrow| = 2 && |startCol-endCol| = 1 nebo
    *                      |startCol - endCol| = 2 && |startRow-endRow| = 1
    *
    * */
    /**
     * Tah figurkou
     * @param field Cílové políčko, na které má dojít k přesunu
     * @param board Hrací deska, kde se pohybovaná figurka nachází
     * @param realMove Booleanovská hodnota, jestli se má tah opravdu provést, nebo zdali jde jen o zjištění providitelnosti tahu
     * @return True - v případě úspěchu, False v případě, že tah není možný
     */
    @Override
    public boolean move(Field field, Board board, boolean realMove) {

        /* pohyb na místo, na kterém se právě nachází */
        if(field.getCol() == this.col && field.getRow() == this.row) {
            return false;
        }
        /*do @endCol a endRow se uloží vždy to vyšší číslo*/
        int endCol = (((BoardField)field).getCol() > this.col ? ((BoardField)field).getCol() : this.col);
        int endRow = (((BoardField)field).getRow() > this.row ? ((BoardField)field).getRow() : this.row);

        /*do @endCol a endRow se uloží vždy to nižší číslo*/
        int startCol = (((BoardField)field).getCol() < this.col ? ((BoardField)field).getCol() : this.col);
        int startRow = (((BoardField)field).getRow() < this.row ? ((BoardField)field).getRow() : this.row);


        if ( ( Math.abs(startRow-endRow) == 2 ) && ( Math.abs(startCol-endCol) == 1 ) ||
             ( Math.abs(startCol-endCol) == 2 ) && ( Math.abs(startRow-endRow) == 1 ) ){
            return realMove ? finalMove(field, board): true;
        }
        else{
            return false;
        }
    }
}
