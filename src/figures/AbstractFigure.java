/**
 * Abstraktní třída, která zapouzdřuje metody společné pro všechny figurky. Implementuje interface Figure.
 * @author  Petr Hemza
 * @author Arthur Nácar
 * @version 1.0
 * @since   2019-04-25
 */
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

    /**
     * Metoda ke zjištění sloupce, ve kterém se nachází figurka
     * @return int číslo sloupce
     */
    public int getCol(){
        return this.col;
    }

    /**
     * Metoda ke zjištění řádku, ve kterém se nachází figurka
     * @return int číslo řádku
     */
    public int getRow(){
        return this.row;
    }
    /**
     * Metoda ke zjištění barvy figurky
     * @return boolean True - figurka je bílá
     */
    public boolean isWhite(){
        return this.isWhite;
    }

    /**
     * Metoda ke zjištění zkratky figurky
     * @return String Zkratka figurky
     */
    public String getShortcut(){
        return this.shortcut;
    }

    /**
     * Metoda nastavuje pozici figurky
     * @param col sloupec pozice
     * @param row řádek pozice
     */
    public void setColRow(int col, int row){
        this.col = col;
        this.row = row;
    }

    /**
     * Metoda která přesune figurku na nové políčko, v případě, že je někdo jiný na políčku, tak jej odstraní.
     * Před voláním metody by mělo být volána metoda move, ke zjištění validity pohybu
     * @param field Cílové políčko, na které má dojít k přesunu
     * @param board Hrací deska, kde se pohybovaná figurka nachází
     * @return True - v případě úspěchu, False v případě, že tah není možný
     */
    protected boolean finalMove(Field field, Board board) {
        /*odstranění cizího hráče*/
        if (field.get() != null) {
            if (field.get().isWhite() != this.isWhite) {
                field.remove();
            } else {
                return false;
            }
        }
        this.reposition(field, board);
        return true;
    }

    /**
     * Přesun políčka na požadovanou pozici
     * @param field Cílové políčko, na které má dojít k přesunu
     * @param board Hrací deska, kde se pohybovaná figurka nachází
     */
    protected void reposition(Field field, Board board){
        board.getField(this.col, this.row).remove();
        this.col = field.getCol();
        this.row = field.getRow();
        ((BoardField)field).put(this);
    }
}
