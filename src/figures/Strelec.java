package figures;

import board.Board;
import board.Field;

    /*
    |18|  |  |  |  |  |  |  |
    |  |27|  |  |  |  |  |87|
    |  |  |36|  |  |  |76|  |
    |  |  |  |45|  |65|  |  |
    |  |  |  |  |::|  |  |  |
    |  |  |  |43|  |63|  |  |
    |  |  |32|  |  |  |72|  |
    |  |21|  |  |  |  |  |81|

     */
public class Strelec extends AbstractFigure{
    public Strelec(int col, int row, boolean isWhite){
        super(col, row, isWhite);
        shortcut = "S";
    }
    /**
     * Tah figurkou
     * @param field Cílové políčko, na které má dojít k přesunu
     * @param board Hrací deska, kde se pohybovaná figurka nachází
     * @param realMove Booleanovská hodnota, jestli se má tah opravdu provést, nebo zdali jde jen o zjištění providitelnosti tahu
     * @return True - v případě úspěchu, False v případě, že tah není možný
     */
    @Override
    public boolean move(Field field, Board board, boolean realMove) {
        /*kontrola, že pohyb není po přímce*/
        if( field.getCol() == this.col || field.getRow() == this.row){
            return false;
        }
        int startCol = this.col;
        int startRow = this.row;
        int endCol = field.getCol();
        int endRow = field.getRow();
        while (!(startCol == endCol && startRow == endRow)) {
            startCol += startCol < endCol ? 1 : -1;
            startRow += startRow < endRow ? 1 : -1;
            if (board.getField(startCol, startRow) == null) { return false; }
            if(board.getField(startCol, startRow).get() != null && !(startCol == endCol && startRow == endRow)){ return false; }
        }
        return realMove ? finalMove(field, board): true;
    }
}
