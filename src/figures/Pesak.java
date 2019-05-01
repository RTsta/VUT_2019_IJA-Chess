package figures;

import board.Board;
import board.BoardField;
import board.Field;

public class Pesak extends AbstractFigure{

    public Pesak(int col, int row, boolean isWhite){
        super(col, row, isWhite);
    }

    @Override
    public String getState(){
        return "P["+(this.isWhite ? "W" : "B")+"]"+Integer.toString(this.col)+":"+Integer.toString(this.row);
    }

    /*
    |  |  |  |  |
    |  |  |  |  |
    |  |22|  |  |
    |  |::|  |  |
     */

    @Override
    public boolean move(Field field, Board board){
        if(field.getCol() == this.col && field.getRow() - this.row == (isWhite ? 1 : -1) && field.get() == null){
            board.getField(this.col, this.row).remove();

            this.reposition(field, board);
            return true;
        }else if(Math.abs(field.getCol() - this.col) == 1 && field.getRow() - this.row == (isWhite ? 1 : -1) && field.get() != null && field.get().isWhite() != this.isWhite){
            this.reposition(field, board);
            return true;
        }
        return false;
    }
}
