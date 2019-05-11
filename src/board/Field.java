package board;

import figures.Figure;

public interface Field {

    /**
     * Metoda k získání figurky, která se nachází na políčku
     * @return Figure Figurka na tomto políčku
     */
    Figure get();

    /**
     * Metoda na zjištění, jestli je políčko prázdné
     * @return boolean true pokud je prázdné, false pokud není
     */
    boolean isEmpty();

    /**
     * Metoda vkládá figurku na toto políčko
     * @param figure Figurka určena ke vložení
     * @return boolean true v případě úspěchu
     */
    boolean put(Figure figure);

    /**
     * Metoda, která vrací sloupec, ve kterém je toto políčko
     * @return int sloupec
     */
    int getCol();

    /**
     * Metoda, která vrací řádek, ve kterém je toto políčko
     * @return int řádek
     */
    int getRow();

    /**
     * Metoda, která odstraňuje figurku na tomto políčku
     * @return boolean pokud je odstranění úspěšné
     */
    boolean remove();
}