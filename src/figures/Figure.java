package figures;

import board.Board;
import board.Field;

/**
 *Rozhraní reprezentuje jeden kámen (figuru). Kámen může nabývat dvou barev - bílá nebo černá.
 */
public interface Figure{
    String getState();
    boolean move(Field field, Board board);
    int getCol();
    int getRow();
    boolean isWhite();
    void setColRow(int col, int row);
}