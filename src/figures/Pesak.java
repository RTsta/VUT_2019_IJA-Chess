/**
 * Třída rozšiřující třídu AbstractFigure a implementueje metody spojené s konkrétní figurkou
 * @author  Petr Hemza
 * @author Arthur Nácar
 * @version 1.0
 * @since   2019-04-25
 */
package figures;

import board.Board;
import board.Field;

public class Pesak extends AbstractFigure{

    private boolean beenMoved;

    private boolean canTake;

    public Pesak(int col, int row, boolean isWhite){

        super(col, row, isWhite);
        this.shortcut = "";
        this.beenMoved = false;
        this.canTake = false;
    }

    /*
    |  |  |  |  |
    |  |  |  |  |
    |  |22|  |  |
    |  |::|  |  |
     */
    /**
     * Tah figurkou
     * @param field Cílové políčko, na které má dojít k přesunu
     * @param board Hrací deska, kde se pohybovaná figurka nachází
     * @param realMove Booleanovská hodnota, jestli se má tah opravdu provést, nebo zdali jde jen o zjištění providitelnosti tahu
     * @return True - v případě úspěchu, False v případě, že tah není možný
     */
    @Override
    public boolean move(Field field, Board board, boolean realMove){
        this.canTake = false;
        if(field.getCol() == this.col && field.getRow() - this.row == (isWhite ? 1 : -1) && field.get() == null){
            if (realMove) {
                board.getField(this.col, this.row).remove();
                this.reposition(field, board);
                beenMoved = true;
            }
            return true;
        }else if(Math.abs(field.getCol() - this.col) == 1 && field.getRow() - this.row == (isWhite ? 1 : -1) && field.get() != null && field.get().isWhite() != this.isWhite){
            if (realMove) {
                board.getField(field.getCol(), field.getRow()).remove();
                this.reposition(field, board);
                beenMoved = true;
            }
            this.canTake = true;
            return true;
        } else if (this.beenMoved == false && field.getCol() == this.col && field.getRow() - this.row == (isWhite ? 2 : -2) && field.get() == null){
            if (realMove) {
                this.reposition(field, board);
                beenMoved = true;
            }
            return true;
        }
        return false;
    }

    public boolean isCanTake() {
        return this.canTake;
    }
}
