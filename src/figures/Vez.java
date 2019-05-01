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
    }


    @Override
    public String getState(){
        return "V["+(this.isWhite ? "W" : "B")+"]"+Integer.toString(this.col)+":"+Integer.toString(this.row);
    }

    @Override
    public boolean move(Field field, Board board){

        /*test jestli tah není do úhlopříčky vždy musí alespoň jedna souřadnice být stejná*/
        if( ((BoardField)field).getCol() != this.col && ((BoardField)field).getRow() != this.row){
            return false;
        }
        /* pohyb na místo, na kterém se právě nachází */
        if(field.getCol() == this.col && field.getRow() == this.row)
            return false;

        /*do @endCol a endRow se uloží vždy to vyšší číslo*/
        int endCol = (((BoardField)field).getCol() > this.col ? ((BoardField)field).getCol() : this.col);
        int endRow = (((BoardField)field).getRow() > this.row ? ((BoardField)field).getRow() : this.row);

        /*do @endCol a endRow se uloží vždy to nižší číslo*/
        int startCol = (((BoardField)field).getCol() < this.col ? ((BoardField)field).getCol() : this.col);
        int startRow = (((BoardField)field).getRow() < this.row ? ((BoardField)field).getRow() : this.row);

        /*
        if(startCol == endCol){
        	if((isWhite && this.row > field.getRow()) || (!isWhite && this.row < field.getRow()))
        		return false;
        }
        */

        /*prochýzení vždy z prava do leva a zespodu nahoru jestli něco nestojí v cestě */
        while(!(startCol == endCol && startRow == endRow)){
            if(startRow != endRow){
                startRow++;
            }
            else{
                startCol++;
            }
            if(board.getField(startCol, startRow).get() != null){
                return false;
            }
        }
        /*odstranění cizího hráče*/
        if(field.get() != null && field.get().isWhite() != this.isWhite){
            field.remove();
        } else {return false;}

        this.reposition(field,board);
        return true;
    }

}
