package figures;

import board.Board;
import board.Field;

/**
 *Rozhraní reprezentuje jeden kámen (figuru). Kámen může nabývat dvou barev - bílá nebo černá.
 */
public interface Figure{
    /**
     * Tah figurkou
     * @param field Cílové políčko, na které má dojít k přesunu
     * @param board Hrací deska, kde se pohybovaná figurka nachází
     * @param realMove Booleanovská hodnota, jestli se má tah opravdu provést, nebo zdali jde jen o zjištění providitelnosti tahu
     * @return True - v případě úspěchu, False v případě, že tah není možný
     */
    boolean move(Field field, Board board, boolean realMove);
    /**
     * Metoda ke zjištění sloupce, ve kterém se nachází figurka
     * @return int číslo sloupce
     */
    int getCol();
    /**
     * Metoda ke zjištění řádku, ve kterém se nachází figurka
     * @return int číslo řádku
     */
    int getRow();
    /**
     * Metoda ke zjištění zkratky figurky
     * @return String Zkratka figurky
     */
    String getShortcut();
    /**
     * Metoda ke zjištění barvy figurky
     * @return boolean True - figurka je bílá
     */
    boolean isWhite();
    /**
     * Metoda nastavuje pozici figurky
     * @param col sloupec pozice
     * @param row řádek pozice
     */
    void setColRow(int col, int row);
}