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
    |  |  |  |48|  |  |  |  |
    |  |  |  |47|  |  |  |  |
    |  |  |  |46|  |  |  |  |
    |  |  |  |45|  |  |  |  |
    |14|24|34|::|54|64|74|84|
    |  |  |  |43|  |  |  |  |
    |  |  |  |42|  |  |  |  |
    |  |  |  |41|  |  |  |  |
     */
public class Vez extends AbstractFigure {
    public Vez(int col, int row, boolean isWhite){
        super(col, row, isWhite);
        shortcut = "V";
    }
    /**
     * Tah figurkou
     * @param field Cílové políčko, na které má dojít k přesunu
     * @param board Hrací deska, kde se pohybovaná figurka nachází
     * @param realMove Booleanovská hodnota, jestli se má tah opravdu provést, nebo zdali jde jen o zjištění providitelnosti tahu
     * @return True - v případě úspěchu, False v případě, že tah není možný
     */
    @Override
    public boolean move(Field field, Board board, boolean realMove){

        /*test jestli tah není do úhlopříčky vždy musí alespoň jedna souřadnice být stejná*/
        if( ((BoardField)field).getCol() != this.col && ((BoardField)field).getRow() != this.row){
            return false;
        }
        /* pohyb na místo, na kterém se právě nachází */
        if(field.getCol() == this.col && field.getRow() == this.row)
            return false;

        int startCol = this.col;
        int startRow = this.row;
        int endCol = field.getCol();
        int endRow = field.getRow();
        while (!(startCol == endCol && startRow == endRow)) {
            if (startCol != endCol) {
                startCol += startCol < endCol ? 1 : -1;
            }
            if (startRow != endRow) {
                startRow += startRow < endRow ? 1 : -1;
            }
            if (board.getField(startCol, startRow) == null) { return false; }
            if(board.getField(startCol, startRow).get() != null && !(startCol == endCol && startRow == endRow)){ return false; }
        }

        return realMove ? finalMove(field, board): true;
    }

}
