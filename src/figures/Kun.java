package figures;

import board.Board;
import board.BoardField;
import board.Field;

public class Kun extends AbstractFigure {

    public Kun(int col, int row, boolean isWhite){
        super(col, row, isWhite);
    }

    @Override
    public String getState() {
        return "K["+(this.isWhite ? "W" : "B")+"]"+Integer.toString(this.col)+":"+Integer.toString(this.row);
    }

    @Override
    public boolean move(Field field, Board board) {
        return false;
    }
}
