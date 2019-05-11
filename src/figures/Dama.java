package figures;

import board.Board;
import board.Field;

public class Dama extends AbstractFigure {
    public Dama(int col, int row, boolean isWhite) {
        super(col, row, isWhite);
        shortcut = "D";
    }

    @Override
    public String getState() {
        return "Q[" + (this.isWhite ? "W" : "B") + "]" + Integer.toString(this.col) + ":" + Integer.toString(this.row);
    }

    /*
    |  |24|  |44|
    |13|23|33|  |
    |12|::|32|42|
    |11|21|31|  |
     */
    //TODOotestovat
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
            }
        }
            return finalMove(field, board, realMove);
    }
}
