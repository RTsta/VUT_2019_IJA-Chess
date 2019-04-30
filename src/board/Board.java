package board;


/**
 * @brief Třída reprezentující hrací desku. Deska má velikost (rozměr) N a je tvořena maticí N x N polí.
 * Pole jsou umístěna na řádcích a sloupcích, které jsou číslovány od 1 do N. Pole jsou identifikována dvojici (c, r),
 * kde c je číslo sloupce a r je číslo řádku. Pozice políčka (1,1) je vlevo dole, pozice políčka (8,1) je vpravo dole.
 * Na pole lze vkládat a odebírat kameny (figury), viz Disk.
 */

public class Board {
    private BoardField[][] playboard;
    private int size;
    /*
    |14|24|34|44|
    |13|23|33|43|
    |12|22|32|42|
    |11|21|31|41|
     */
    public Board(int size){
        this.size = size;
        playboard = new BoardField[size+1][size+1];

        for (int row = size; row > 0;row--){
            for (int col = 1;col <= size;col++){
                playboard[col][row] = new BoardField(col , row);
            }
        }
    }

    /**
     *
     * @param col - číslo sloupce
     * @param row - číslo řádku
     * @return Pole na pozici (col, row).
     */
    public Field getField(int col, int row)  {
        if(col > size || row > size || row < 1 || col < 1){
            return null;
        } else {
            return this.playboard[col][row];
        }
    }

    public int getSize(){
        return this.size;
    }

}