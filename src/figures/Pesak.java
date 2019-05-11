package figures;

import board.Board;
import board.Field;

//TODO pokud pěšec dojde na konec šachovnice, může si vybrat v co se změní

public class Pesak extends AbstractFigure{

    private Boolean beenMoved;

    public Pesak(int col, int row, boolean isWhite){

        super(col, row, isWhite);
        shortcut = "";
        beenMoved = false;
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
            return true;
        } else if (beenMoved == false && field.getCol() == this.col && field.getRow() - this.row == (isWhite ? 2 : -2) && field.get() == null){
            if (realMove) {
                this.reposition(field, board);
                beenMoved = true;
            }
            return true;
        }
        return false;
    }
}
