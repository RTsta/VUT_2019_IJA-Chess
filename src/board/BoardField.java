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


    public Figure get(){
        return this.figure;
    }

    public boolean isEmpty(){
        return !(this.figure != null);
    }

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

    public boolean remove(){
        this.figure = null;
        return true;
    }

    public int getCol(){
        return this.col;
    }

    public int getRow(){
        return this.row;
    }

    @Override
    public boolean equals(java.lang.Object obj){
        if (obj == this) {
            return true;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof BoardField)) {
            return false;
        }
        // typecast o to Complex so that we can compare data members
        BoardField tmp = (BoardField) obj;

        // Compare the data members and return accordingly
        return  Objects.equals(tmp.col,this.col) &&
                Objects.equals(tmp.row,this.row) &&
                Objects.equals(tmp.figure,this.figure);
    }

    @Override
    public int hashCode(){
        return Objects.hash(col, row, figure);
    }

}