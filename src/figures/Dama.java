/**
 * Třída rozšiřující třídu AbstractFigure a implementueje metody spojené s konkrétní figurkou
 * @author  Petr Hemza
 * @author Arthur Nácar
 * @version 1.0
 * @since   2019-04-25
 */
package figures;

import board.Board;
import board.Field;

public class Dama extends AbstractFigure {
    public Dama(int col, int row, boolean isWhite) {
        super(col, row, isWhite);
        shortcut = "D";
    }

    /*
    |  |24|  |44|
    |13|23|33|  |
    |12|::|32|42|
    |11|21|31|  |
     */
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
        if (field.getCol() == this.col && field.getRow() == this.row)
            return false;
        int startCol = this.col;
        int startRow = this.row;
        int endCol = field.getCol();
        int endRow = field.getRow();
        boolean rook = (startCol == endCol || startRow == endRow);
        while (!(startCol == endCol && startRow == endRow)) {
            if (rook) {
                if (startCol != endCol) {
                    startCol += startCol < endCol ? 1 : -1;
                }
                if (startRow != endRow) {
                    startRow += startRow < endRow ? 1 : -1;
                }
            } else {
                startCol += startCol < endCol ? 1 : -1;
                startRow += startRow < endRow ? 1 : -1;
            }
            if (board.getField(startCol, startRow) == null) { return false; }
            if(board.getField(startCol, startRow).get() != null && !(startCol == endCol && startRow == endRow)){ return false; }
        }
        return realMove ? finalMove(field, board): true;
    }
}
