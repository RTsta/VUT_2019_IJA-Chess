/**
 * Třída implementující rozhraní Field. Reprezentuje jedno políčko hrací desky.
 * @author  Petr Hemza
 * @author Arthur Nácar
 * @version 1.0
 * @since   2019-04-25
 */
package board;

import figures.Figure;
import java.util.Objects;

/*
    Třída reprezentující aktivní pole na hrací desce.
 */
public class BoardField implements Field {
    private int col;
    private int row;

    private Figure figure;

    public BoardField(int col, int row){
        this.col = col;
        this.row = row;
        figure  = null;
    }

    /**
     * Metoda k získání figurky, která se nachází na políčku
     * @return Figure Figurka na tomto políčku
     */
    public Figure get(){
        return this.figure;
    }

    /**
     * Metoda na zjištění, jestli je políčko prázdné
     * @return boolean true pokud je prázdné, false pokud není
     */
    public boolean isEmpty(){
        return !(this.figure != null);
    }

    /**
     * Metoda vkládá figurku na toto políčko
     * @param figure Figurka určena ke vložení
     * @return boolean true v případě úspěchu
     */
    public boolean put(Figure figure){
        if(!this.isEmpty()){
            return false;
        }
        else {
            this.figure = figure;
            this.figure.setColRow(this.col, this.row);
            return true;
        }
    }

    /**
     * Metoda, která odstraňuje figurku na tomto políčku
     * @return boolean pokud je odstranění úspěšné
     */
    public boolean remove(){
        this.figure = null;
        return true;
    }

    /**
     * Metoda, která vrací sloupec, ve kterém je toto políčko
     * @return int sloupec
     */
    public int getCol(){
        return this.col;
    }

    /**
     * Metoda, která vrací řádek, ve kterém je toto políčko
     * @return int řádek
     */
    public int getRow(){
        return this.row;
    }
}