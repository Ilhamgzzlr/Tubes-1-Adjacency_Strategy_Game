import java.util.ArrayList;
import java.util.List;

public class State {
    private static final int STATE_BOARD_ROW = 8;
    private static final int STATE_BOARD_COL = 8;

    private String[][] stateBoard;

    private int[] move;

    private int value;

    private List<State> children;

    private State parent;

    public State() {
        this.stateBoard = new String[STATE_BOARD_ROW][STATE_BOARD_COL];
        this.value = 0;
        this.children = new ArrayList<>();
        this.move = new int[]{0, 0};

        for(int i = 0; i < STATE_BOARD_ROW; i++) {
            for(int j = 0; j < STATE_BOARD_COL; j++) {
                this.stateBoard[i][j] = "";
            }
        }
    }

    public State(State parent) {
        this.stateBoard = new String[STATE_BOARD_ROW][STATE_BOARD_COL];
        this.value = 0;
        this.children = new ArrayList<>();
        this.parent = parent;
        this.move = new int[]{0, 0};

        for(int i = 0; i < STATE_BOARD_ROW; i++) {
            for(int j = 0; j < STATE_BOARD_COL; j++) {
                this.stateBoard[i][j] = parent.getStateBoard()[i][j];
            }
        }
    }

    // Setter
    public void writeToStateBoard(int row, int col, String botMarker) {
        String opponentMarker = botMarker.equals("O") ? "X" : "O";

        if(row != 0 && this.stateBoard[row-1][col].equals(opponentMarker)) {
            this.stateBoard[row-1][col] = botMarker;
        }

        if(row != STATE_BOARD_ROW - 1 && this.stateBoard[row+1][col].equals(opponentMarker)) {
            this.stateBoard[row+1][col] = botMarker;
        }

        if(col != 0 && this.stateBoard[row][col-1].equals(opponentMarker)) {
            this.stateBoard[row][col-1] = botMarker;
        }

        if(col != STATE_BOARD_COL - 1 && this.stateBoard[row][col+1].equals(opponentMarker)) {
            this.stateBoard[row][col+1] = botMarker;
        }

        this.stateBoard[row][col] = botMarker;
    }

    public void setTerminalValue(String botMarker) {
        String opponentMarker = botMarker.equals("O") ? "X" : "O";
        for(int i  = 0; i < STATE_BOARD_ROW; i++) {
            for(int j = 0; j < STATE_BOARD_COL; j++) {
                if(this.stateBoard[i][j].equals(botMarker)) {
                    this.value++;
                }
                else if(this.stateBoard[i][j].equals(opponentMarker)) {
                    this.value--;
                }
            }
        }
    }

    public void addChild(State child) {
        this.children.add(child);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setMove(int row, int col) {
        this.move[0] = row;
        this.move[1] = col;
    }

    // Getter
    public List<State> getChildren() {
        return this.children;
    }

    public int getValue() {
        return this.value;
    }

    public String[][] getStateBoard() {
        return this.stateBoard;
    }

    public int[] getMove() {
        return this.move;
    }
}