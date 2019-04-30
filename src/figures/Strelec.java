package figures;

import board.Board;
import board.BoardField;
import board.Field;

public class Strelec extends AbstractFigure{
    public Strelec(int col, int row, boolean isWhite){
        super(col, row, isWhite);
    }

    @Override
    public String getState() {
        return "S["+(this.isWhite ? "W" : "B")+"]"+Integer.toString(this.col)+":"+Integer.toString(this.row);
    }

    @Override
    public boolean move(Field field, Board board) {
        return false;
    }
}
