package figures;

import board.Board;
import board.BoardField;
import board.Field;

public class Dama extends AbstractFigure {
    public Dama(int col, int row, boolean isWhite){
        super(col, row, isWhite);
    }

    @Override
    public String getState() {
        return "Q["+(this.isWhite ? "W" : "B")+"]"+Integer.toString(this.col)+":"+Integer.toString(this.row);
    }

    /*
    |  |24|  |44|
    |13|23|33|  |
    |12|::|32|42|
    |11|21|31|  |
     */
    //TODOotestovat
    @Override
    public boolean move(Field field, Board board) {





        /* pohyb na místo, na kterém se právě nachází */
        if(field.getCol() == this.col && field.getRow() == this.row)
            return false;

        /*do @endCol a endRow se uloží vždy to vyšší číslo*/
        int endCol = (((BoardField)field).getCol() > this.col ? ((BoardField)field).getCol() : this.col);
        int endRow = (((BoardField)field).getRow() > this.row ? ((BoardField)field).getRow() : this.row);

        /*do @endCol a endRow se uloží vždy to nižší číslo*/
        int startCol = (((BoardField)field).getCol() < this.col ? ((BoardField)field).getCol() : this.col);
        int startRow = (((BoardField)field).getRow() < this.row ? ((BoardField)field).getRow() : this.row);

        /*prochýzení vždy z prava do leva a zespodu nahoru jestli něco nestojí v cestě */
        while(!(startCol == endCol && startRow == endRow)){
            if(startRow != endRow && startCol == endCol){
                startRow++;
            }
            else if (startCol != endCol && startRow == endRow){
                startCol++;
            }
            else {
                startRow++;
                startCol++;
            }
            if (startRow > endRow || startCol > endCol){
                return false;
            }

            if(board.getField(startCol, startRow).get() != null){
                return false;
            }
        }


        /*odstranění cizího hráče, pokud to není moje figurka*/
        if(field.get() != null && field.get().isWhite() != this.isWhite){
            field.remove();
        }else{return false;}

        this.reposition(field,board);
        return true;

    }
}
