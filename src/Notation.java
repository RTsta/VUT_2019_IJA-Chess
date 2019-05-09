import board.Board;
import figures.Figure;

public class Notation {
    private String notArr[];
    private int moveCnt;

    private int srcColWhite;
    private int srcRowWhite;
    private int desColWhite;
    private int desRowWhite;
    private int srcColBlack;
    private int srcRowBlack;
    private int desColBlack;
    private int desRowBlack;

    private char changeWhite;
    private char changeBlack;

    private boolean takeFigureWhite;
    private boolean takeFigureBlack;

    private boolean checkWhite;
    private boolean mateWhite;
    private boolean checkBlack;
    private boolean mateBlack;

    public Notation(String notation) {
        this.notArr = notation.trim().split("\n");
        this.moveCnt = 0;
        this.init();
    }

    private void init() {
        this.srcColWhite = 0;
        this.srcRowWhite = 0;
        this.desColWhite = 0;
        this.desRowWhite = 0;
        this.srcColBlack = 0;
        this.srcRowBlack = 0;
        this.desColBlack = 0;
        this.desRowBlack = 0;

        this.changeWhite = 'p';
        this.changeBlack = 'p';

        this.takeFigureWhite = false;
        this.takeFigureBlack = false;

        this.checkWhite = false;
        this.mateWhite = false;
        this.checkBlack = false;
        this.mateBlack = false;
    }

    public boolean getMove(Board board) {
        this.init();
        if (this.isEnd()) { return false; }
        System.out.println(this.notArr[moveCnt]);

        String notParts[] = this.notArr[this.moveCnt].split(" ");
        if (notParts.length != 3 || notParts[0].compareTo((this.moveCnt+1)+".") != 0) { return false; }
        if (!parseMove(notParts[1], board, true)) { return false; }
        if (!parseMove(notParts[2], board, false)) { return false; }

        this.moveCnt++;
        return true;
    }

    private boolean parseMove(String move, Board board, boolean isWhite) {
        if (move.length() < 2) { return false; }
        if (move.substring(move.length()-1).compareTo("+") == 0) {
            if (isWhite) {
                this.checkWhite = true;
            } else {
                this.checkBlack = true;
            }
            move = move.substring(0,move.length()-1);
        } else if (move.substring(move.length()-1).compareTo("#") == 0) {
            if (isWhite) {
                this.mateWhite = true;
            } else {
                this.mateBlack = true;
            }
            move = move.substring(0,move.length()-1);
        }
        if (move.length() < 2) { return false; }
        char figure = typeOfFigure(move.charAt(0));
        if (figure != 'p') {
            move = move.substring(1);
        }
        char change = typeOfFigure(move.charAt(move.length()-1));
        if (change != 'p') {
            move = move.substring(0,move.length()-1);
        }
        if (move.length() < 2) { return false; }
        int row = Character.getNumericValue(move.charAt(move.length()-1));
        int col = move.charAt(move.length()-2)-'a'+1;
        if (row < 1 || row > 8 || col < 1 || col > 8) { return false; }
        if (isWhite) {
            this.desRowWhite = row;
            this.desColWhite = col;
        } else {
            this.desRowBlack = row;
            this.desColBlack = col;
        }
        move = move.substring(0,move.length()-2);
        if (move.length() > 1 && move.charAt(move.length()-1) == 'x') {
            if (isWhite) {
                this.takeFigureWhite = true;
            } else {
                this.takeFigureBlack = true;
            }
            move = move.substring(0,move.length()-1);
        }
        if (move.length() == 2) {
            row = Character.getNumericValue(move.charAt(move.length()-1));
            col = move.charAt(move.length()-2)-'a'+1;
            if (row < 1 || row > 8 || col < 1 || col > 8) { return false; }
            if (isWhite) {
                this.srcRowWhite = row;
                this.srcColWhite = col;
            } else {
                this.srcRowBlack = row;
                this.srcColBlack = col;
            }
        } else if (move.length() < 2) {
            int borderLeft = 1;
            int borderTop = 8;
            int borderRight = 8;
            int borderBottom = 1;
            switch (figure) {
                case 'K':
                    borderLeft = (col == 1) ? 1 : col-1;
                    borderTop = (row == 8) ? 8 : row+1;
                    borderRight = (col == 8) ? 8 : col+1;
                    borderBottom = (row == 1) ? 1 : row-1;
                    break;
                case 'J':
                    borderLeft = (col <= 3) ? 1 : col-2;
                    borderTop = (row >= 6) ? 8 : row+2;
                    borderRight = (col >= 6) ? 8 : col+2;
                    borderBottom = (row <= 3) ? 1 : row-2;
                    break;
                case 'p':
                    if (isWhite) {
                        borderLeft = col - (this.takeFigureWhite ? 1 : 0);
                        borderTop = row;
                        borderRight = col + (this.takeFigureWhite ? 1 : 0);
                        borderBottom = (row-2 < 1) ?  1 : row-2;
                    } else {
                        borderLeft = col - (this.takeFigureBlack ? 1 : 0);
                        borderTop = (row+2 > 8) ? 8: row+2;
                        borderRight = col + (this.takeFigureBlack ? 1 : 0);
                        borderBottom = row;
                    }
                    break;
                default:
                    break;
            }
            if (move.length() == 1) {
                if (move.charAt(0) >= 'a' && move.charAt(0) < 'h') {
                    borderLeft = move.charAt(0)-'a'+1;
                    borderRight = move.charAt(0)-'a'+1;

                } else if (move.charAt(0) >= '1' && move.charAt(0) < '8') {
                    borderTop = Character.getNumericValue(move.charAt(0));
                    borderBottom = Character.getNumericValue(move.charAt(0));
                } else {
                    return false;
                }
            }
            for (int i = borderBottom; i <= borderTop; i++) {
                for (int j = borderLeft; j <= borderRight; j++) {
                    Figure fig = board.getField(j,i).get();
                    if (fig == null) { continue; }
                    if (fig.getClass().getName().toLowerCase().compareTo("figures."+figureToString(figure)) == 0 && fig.isWhite() == isWhite) {
                        System.out.println(fig.move(board.getField(col, row), board, false));
                        if (fig.move(board.getField(col, row), board, false)) {
                            if (isWhite) {
                                this.srcColWhite = j;
                                this.srcRowWhite = i;
                            } else {
                                this.srcColBlack = j;
                                this.srcRowBlack = i;
                            }
                        }
                    }
                }
            }
        } else { return false; }
        if (change != 'p') {
            if (figure == 'p' && (isWhite ? this.desRowWhite : this.desRowBlack) == 8) {
                if (isWhite) {
                    this.changeWhite = change;
                } else {
                    this.changeBlack = change;
                }
            } else {
                return false;
            }
        }
        if (isWhite) {
            if (this.srcColWhite == 0 || this.srcRowWhite == 0 || this.desColWhite == 0 || this.desRowWhite == 0) {
                return false;
            }
        } else {
            if (this.srcColBlack == 0 || this.srcRowBlack == 0 || this.desColBlack == 0 || this.desRowBlack == 0) {
                return false;
            }
        }
        return true;
    }

    private String figureToString(char figure) {
        switch (figure) {
            case 'K':
                return "kral";
            case 'D':
                return "dama";
            case 'V':
                return "vez";
            case 'S':
                return "strelec";
            case 'J':
                return "kun";
            case 'p':
                return "pesak";
            default:
                return "";
        }
    }

    private char typeOfFigure(char f) {
        switch (f) {
            case 'K':
            case 'D':
            case 'V':
            case 'S':
            case 'J':
                return f;
            default:
                return 'p';
        }
    }

    public boolean isEnd() {
        return this.moveCnt >= this.notArr.length;
    }

    public int getSrcCol(boolean isWhite) {
        return isWhite ? this.srcColWhite : this.srcColBlack;
    }

    public int getSrcRow(boolean isWhite) {
        return isWhite ? this.srcRowWhite : this.srcRowBlack;
    }

    public int getDecCol(boolean isWhite) {
        return isWhite ? this.desColWhite : this.desColBlack;
    }

    public int getDesRow(boolean isWhite) {
        return isWhite ? this.desRowWhite : this.desRowBlack;
    }

    public char getChangeFigure(boolean isWhite) {
        return isWhite ? this.changeWhite : this.changeBlack;
    }

    public boolean isTakeFigure(boolean isWhite) {
        return isWhite ? this.takeFigureWhite : this.takeFigureBlack;
    }

    public boolean isCheck(boolean isWhite) {
        return isWhite ? this.checkWhite : this.checkBlack;
    }

    public boolean isMate(boolean isWhite) {
        return isWhite ? this.mateWhite : this.mateBlack;
    }
}
