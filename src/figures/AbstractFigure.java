package figures;
import board.Board;
import board.BoardField;
import board.Field;

public abstract class AbstractFigure implements Figure{
    protected boolean isWhite;
    protected int col;
    protected int row;
    protected String shortcut;

    public AbstractFigure(int col, int row, boolean isWhite) {
        this.col = col;
        this.row = row;
        this.isWhite = isWhite;
    }


    public int getCol(){
        return this.col;
    }

    public int getRow(){
        return this.row;
    }

    public boolean isWhite(){
        return this.isWhite;
    }

    public String getShortcut(){
        return this.shortcut;
    }

    public void setColRow(int col, int row){
        this.col = col;
        this.row = row;
    }

    /*přesun políčka na požadovanou pozici*/
    protected void reposition(Field field, Board board){
        board.getField(this.col, this.row).remove();
        this.col = field.getCol();
        this.row = field.getRow();
        ((BoardField)field).put(this);
    }

}
