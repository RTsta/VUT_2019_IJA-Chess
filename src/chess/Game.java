package chess;

import figures.Figure;
import board.Field;
/**
 *Rozhraní reprezentující hru, která pracuje s figurami Figure a poli Field.
 */
public interface Game {

    /**
     * @brief Přesune figuru na zadané políčko, pokud je to možné. Pokud je operace úspěšná, lze provést její reverzní krok (tj. vrátit tah zpět) vyvoláním operace undo().
     * @param figure - Přesunovaná figura.
     * @param field - Cílové pole.
     * @return Úspěšnost operace.
     */
    boolean move(Figure figure, Field field);

    /**
     * @brief Provede reverzní operaci (tah) nad hrou. Opakovaným voláním této operace lze vrátit všechny dosud provedené tahy až na začátek hry.
     */
    void undo();
}