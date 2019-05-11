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

    @Override
    public String getState() {
        return "S["+(this.isWhite ? "W" : "B")+"]"+Integer.toString(this.col)+":"+Integer.toString(this.row);
    }

    //TODO otestovat
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
        return finalMove(field, board, realMove);
    }
}
