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
    /**
     * Tah figurkou
     * @param field Cílové políčko, na které má dojít k přesunu
     * @param board Hrací deska, kde se pohybovaná figurka nachází
     * @param realMove Booleanovská hodnota, jestli se má tah opravdu provést, nebo zdali jde jen o zjištění providitelnosti tahu
     * @return True - v případě úspěchu, False v případě, že tah není možný
     */
    @Override
    public boolean move(Field field, Board board, boolean realMove) {
        /*kontrola, že pohyb není po přímce*/
        if( field.getCol() == this.col || field.getRow() == this.row){
            return false;
        }
        int startCol = this.col;
        int startRow = this.row;
        int endCol = field.getCol();
        int endRow = field.getRow();
        String dir = "";

        if ((startCol < endCol)) {
            dir += "R";
        } else {
            dir += "L";
        }

        if ((startRow < endRow)) {
            dir += "U";
        } else {
            dir += "D";
        }

        while (!(startCol == endCol && startRow == endRow)) {
            switch (dir) {
                case "RU":
                    startCol++;
                    startRow++;
                    break;
                case "RD":
                    startCol++;
                    startRow--;
                    break;
                case "LU":
                    startCol--;
                    startRow++;
                    break;
                case "LD":
                    startCol--;
                    startRow--;
                    break;
                default:
                    break;
            }
            if (board.getField(startCol, startRow) == null) { return false; }
            if(board.getField(startCol, startRow).get() != null && !(startCol == endCol && startRow == endRow)){ return false; }
        }
        return realMove ? finalMove(field, board): true;
    }
}
