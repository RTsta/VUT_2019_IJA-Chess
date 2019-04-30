package board;

import figures.Figure;

public interface Field {

    Figure get();

    boolean isEmpty();

    boolean put(Figure disk);

    int getCol();

    int getRow();

    boolean remove();
}