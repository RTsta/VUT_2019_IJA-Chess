import board.*;

public abstract class GameFactory {
    public static Game createChessGame(Board board){
        return new ChessGame(board);
    }

}