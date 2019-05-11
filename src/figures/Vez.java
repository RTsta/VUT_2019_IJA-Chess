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


    @Override
    public String getState(){
        return "V["+(this.isWhite ? "W" : "B")+"]"+Integer.toString(this.col)+":"+Integer.toString(this.row);
    }

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
            if(board.getField(startCol, startRow).get() != null){ return false; }
        }

        return finalMove(field, board, realMove);
    }

}
