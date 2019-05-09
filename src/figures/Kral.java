package figures;

import board.Board;
import board.BoardField;
import board.Field;

    /*
    |  |  |  |  |  |  |  |  |
    |  |  |  |  |  |  |  |  |
    |  |  |  |  |  |  |  |  |
    |  |  |  |  |  |  |  |  |
    |  |  |  |  |  |  |  |  |
    |13|23|33|  |  |  |  |  |
    |12|::|32|  |  |  |  |  |
    |11|21|31|  |  |  |  |  |
     */
public class Kral extends AbstractFigure {

    public Kral(int col, int row, boolean isWhite){
        super(col, row, isWhite);
        shortcut = "K";
    }

    @Override
    public String getState() {
        return "K["+(this.isWhite ? "W" : "B")+"]"+Integer.toString(this.col)+":"+Integer.toString(this.row);
    }

    //TODO otestovat
    @Override
    public boolean move(Field field, Board board, boolean realMove) {
        /* pohyb na místo, na kterém se právě nachází */
        if(field.getCol() == this.col && field.getRow() == this.row)
            return false;

        /*do @endCol a endRow se uloží vždy to vyšší číslo*/
        int endCol = (((BoardField)field).getCol() > this.col ? ((BoardField)field).getCol() : this.col);
        int endRow = (((BoardField)field).getRow() > this.row ? ((BoardField)field).getRow() : this.row);

        /*do @endCol a endRow se uloží vždy to nižší číslo*/
        int startCol = (((BoardField)field).getCol() < this.col ? ((BoardField)field).getCol() : this.col);
        int startRow = (((BoardField)field).getRow() < this.row ? ((BoardField)field).getRow() : this.row);

        if (Math.abs(startRow-endRow) > 1 || Math.abs(startCol-endCol) > 1){
            return false;
        }

        if (realMove) {
            /*odstranění cizího hráče*/
            if (field.get() != null) {
                if (field.get().isWhite() != this.isWhite) {
                    field.remove();
                } else {
                    return false;
                }
            }

            this.reposition(field, board);
        }
        return true;


    }
}
