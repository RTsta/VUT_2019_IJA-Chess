import board.Board;
import board.Field;
import figures.Vez;
import figures.Pesak;
import figures.Figure;
import java.util.Stack;

public class ChessGame implements Game{

    private Board board;
    private Stack<Move> stack;

    public ChessGame(Board board){
        this.board = board;
        this.stack = new Stack<Move>();

        for(int i = 1; i <= board.getSize(); i++){
            board.getField(i, 2).put(new Pesak(i, 2, true));
            board.getField(i, board.getSize()-1).put(new Pesak(i, board.getSize()-1, false));
        }

        board.getField(1, 1).put(new Vez(1, 1, true));
        board.getField(board.getSize(), 1).put(new Vez(board.getSize(), 1, true));
        board.getField(1, board.getSize()).put(new Vez(1, board.getSize(), false));
        board.getField(board.getSize(), board.getSize()).put(new Vez(board.getSize(), board.getSize(), false));
    }

    @Override
    public boolean move(Figure figure, Field field){
        Field prevf = this.board.getField(figure.getCol(), figure.getRow());
        Figure f = field.get();
        boolean b = figure.move(field, this.board);
        if(b){
            this.stack.push(new Move(prevf, figure, f, field));
        }
        return b;
    }

    @Override
    public void undo(){
        if(!this.stack.empty()){
            Move m = stack.pop();
            m.getField().remove();
            m.getField().put(m.getFigure());
            if(m.getRemoved() != null){
                m.getToField().remove();
                m.getToField().put(m.getRemoved());
            }

        }
    }


    private class Move{

        private Field ffrom;
        private Figure what;
        private Figure prevF;
        private Field tof;

        public Move(Field ffrom, Figure what, Figure prevF, Field tof){
            this.ffrom = ffrom;
            this.what = what;
            this.prevF = prevF;
            this.tof = tof;
        }

        public Figure getFigure(){
            return this.what;
        }

        public Field getField(){
            return this.ffrom;
        }

        public Figure getRemoved(){
            return this.prevF;
        }

        public Field getToField(){
            return this.tof;
        }
    }
}


