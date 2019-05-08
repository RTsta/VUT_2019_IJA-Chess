package figures;

import board.Board;
import board.BoardField;
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
    public boolean move(Field field, Board board) {

        /*kontrola, že pohyb není po přímce*/
        if( ((BoardField)field).getCol() == this.col || ((BoardField)field).getRow() == this.row){
            return false;
        }

        /* pohyb na místo, na kterém se právě nachází */
        if(field.getCol() == this.col && field.getRow() == this.row)
            return false;

        /*do @endCol a endRow se uloží vždy to vyšší číslo*/
        int endCol = (((BoardField)field).getCol() > this.col ? ((BoardField)field).getCol() : this.col);
        int endRow = (((BoardField)field).getRow() > this.row ? ((BoardField)field).getRow() : this.row);

        /*do @startCol a endRow se uloží vždy to nižší číslo*/
        int startCol = (((BoardField)field).getCol() < this.col ? ((BoardField)field).getCol() : this.col);
        int startRow = (((BoardField)field).getRow() < this.row ? ((BoardField)field).getRow() : this.row);

        while(!(startCol == endCol && startRow == endRow)){
            if(startRow != endRow){
                startRow++;
                startCol++;
            }
            if(startRow > endRow || startCol > endCol){
                return false;
            }

            if(board.getField(startCol, startRow).get() != null){
                return false;
            }
        }

        /*odstranění cizího hráče*/
        if (field.get() != null){
            if (field.get().isWhite() != this.isWhite){
                field.remove();
            } else {return false;}
        }

        this.reposition(field,board);
        return true;


    }
}
