import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Button;

public class LocalSearchBot implements Bot {

    private int ROW;
    private int COL;

    private Button[][] buttons;

    private String bot;
    private String opponent;

    private int botScore;

    public LocalSearchBot(int row, int col, Button[][] buttons, String bot) {
        this.ROW = row;
        this.COL = col;
        this.buttons = buttons;
        this.bot = bot;
        this.opponent = bot.equals("O") ? "X" : "O";
        System.out.println("Bot: " + bot);
        System.out.println("Opponent: " + opponent);
    }

    public void updateScore(int botScore){
        this.botScore = botScore;
    }
    
    public int[] move() {
        List<int[]> allNeighbours = findBestNeighbours();

        int[] current = allNeighbours.get(0);
        int globalOptimum = 0;
        int cost = utilityFunction(current);

        // System.out.println("Size of available moves: " + allNeighbours.size());
        for (int[] neighbour : allNeighbours) {
            int newCost = utilityFunction(neighbour);
            if (newCost < cost) {
                cost = newCost;
                current = neighbour;
            }
            if (cost == globalOptimum) {
                break;
            }
        }

        return current;
    }

    private List<int[]> findBestNeighbours() {
        List<int[]> availableMoves = new ArrayList<>();
        int bestScore = -1;

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    int score = countAdjacent(new int[]{i, j}, opponent);

                    if (score > bestScore) {
                        bestScore = score;
                        availableMoves.clear();
                    }

                    if (score == bestScore) {
                        availableMoves.add(new int[]{i, j});
                    }
                }
            }
        }

        return availableMoves;
    }

    private int utilityFunction(int[] move) {
        return countAdjacentAfter(move);
    }

    private int countAdjacent(int[] move, String symbol) {
        int score = 0;
        int row = move[0];
        int col = move[1];
    
        int[][] directions = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };
    
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
    
            if (isValidMove(newRow, newCol) && buttons[newRow][newCol].getText().equals(symbol)) {
                score++;
                if (isAdjacentFull(newRow, newCol)) {
                    score++;
                }
            }
        }
    
        return score;
    }
    
    private boolean isAdjacentFull(int row, int col) {
        int[][] directions = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };
    
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
    
            if (isValidMove(newRow, newCol) && buttons[newRow][newCol].getText().isEmpty()) {
                return false;
            }
        }
    
        return true;
    }
    

    private int countAdjacentAfter(int[] move) {
        int cost = 0;
        int row = move[0];
        int col = move[1];

        int[][] directions = {
            {0, -1}, {0, 1}, {-1, 0}, {1, 0},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (isValidMove(newRow, newCol) && buttons[newRow][newCol].getText().isEmpty()) {

                int newCost = countAdjacent(new int[]{newRow, newCol}, bot) + 1;

                if (newCost > cost) {
                    cost = newCost;
                }
            }
        }

        return cost;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < ROW && col >= 0 && col < COL;
    }
}
