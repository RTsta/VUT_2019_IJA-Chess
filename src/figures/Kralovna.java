package figures;

import board.Board;
import board.BoardField;
import board.Field;

public class Kralovna extends AbstractFigure {
    public Kralovna(int col, int row, boolean isWhite){
        super(col, row, isWhite);
    }

    @Override
    public String getState() {
        return "Q["+(this.isWhite ? "W" : "B")+"]"+Integer.toString(this.col)+":"+Integer.toString(this.row);
    }

    @Override
    public boolean move(Field field, Board board) {
        return false;
    }
}
