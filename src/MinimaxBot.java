import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Button;

public class MinimaxBot implements Bot {
    private static final int INFINITY = 9999;

    private State state;

    private int ROW;
    private int COL;

    private Button[][] buttons;

    private String bot;
    private String opponent;

    private int roundsLeft;

    public MinimaxBot(int row, int col, Button[][] buttons, String bot) {
        this.ROW = row;
        this.COL = col;
        this.buttons = buttons;
        this.bot = bot;
        this.opponent = bot.equals("O") ? "X" : "O";
    }

    public void updateBot(int roundsLeft){
        this.roundsLeft = roundsLeft;
    }

    public int[] move() {
        this.state = new State();
        initStateBoard();

        minimax(this.state, 4, this.roundsLeft, -INFINITY, INFINITY, true);

        int[] bestMove = createValidMove(this.state);

        // Find next best move
        for (State child : state.getChildren()) {
            if(child.getValue() == this.state.getValue()) {
                bestMove = child.getMove();
            }
        }
        System.out.println(bestMove[0] + ", " + bestMove[1]);
        return bestMove;
    }

    private void minimax(State state, int depth, int roundsLeft, int alpha, int beta, boolean maxPlayer) {
        if(depth == 0 || roundsLeft == 0) {
            state.setTerminalValue(this.bot);
            return;
        }

        if(maxPlayer) {
            addPossibleMove(this.bot, state);

            if(state.getChildren().isEmpty()) {
                state.setTerminalValue(this.bot);
                return;
            }

            state.setValue(-INFINITY);
            for (State child : state.getChildren()) {
                minimax(child, depth-1, roundsLeft-1, alpha, beta, false);
                state.setValue(Math.max(child.getValue(), state.getValue()));
                alpha = Math.max(child.getValue(), alpha);
                if(beta <= alpha) break;
            }
        }

        else {
            addPossibleMove(this.opponent, state);

            if(state.getChildren().isEmpty()) {
                state.setTerminalValue(this.bot);
                return;
            }

            state.setValue(INFINITY);
            for (State child : state.getChildren()) {
                minimax(child, depth-1, roundsLeft-1, alpha, beta, true);
                state.setValue(Math.min(child.getValue(), state.getValue()));
                beta = Math.min(child.getValue(), beta);
                if(beta <= alpha) break;
            }
        }
    }

    private void initStateBoard() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (!buttons[i][j].getText().isEmpty()) {
                    this.state.writeToStateBoard(i, j, buttons[i][j].getText());
                }
            }
        }
    }

    private void addPossibleMove(String botMarker, State state) {
        String opponentMarker = botMarker.equals("O") ? "X" : "O";
        int[] move = createValidMove(state);

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (state.getStateBoard()[i][j].equals("")) {
                    move = new int[]{i, j};
                    State child = new State(state);
                    child.writeToStateBoard(move[0], move[1], this.bot);
                    child.setMove(move[0], move[1]);
                    state.addChild(child);
                }
            }
        }
    }

    public int[] createValidMove(State state) {
        for(int i = 0; i < ROW; i++) {
            for(int j = 0; j < COL; j++) {
                if(state.getStateBoard()[i][j].equals("")) {
                    return new int[]{i, j};
                }
            }
        }
        return  new int[]{0, 0};
    }
}