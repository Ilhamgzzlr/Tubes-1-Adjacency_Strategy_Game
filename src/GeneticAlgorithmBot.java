import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Button;

public class GeneticAlgorithmBot implements Bot {

    private int ROW;
    private int COL;

    private Button[][] buttons;

    private String bot;
    private String opponent;

    private int botScore;

    public GeneticAlgorithmBot(int row, int col, Button[][] buttons, String bot) {
        this.ROW = row;
        this.COL = col;
        this.buttons = buttons;
        this.bot = bot;
        this.opponent = bot.equals("O") ? "X" : "O";
        System.out.println("Bot: " + bot);
        System.out.println("Opponent: " + opponent);
    }

    public void updateScore(int botScore) {
        this.botScore = botScore;
    }

    public int[] move() {
        List<int[]> population = generateInitialPopulation(100);
        int generationLimit = 1000;
        int currentGeneration = 0;

        while (currentGeneration < generationLimit) {
            List<int[]> nextGeneration = new ArrayList<>();

            for (int[] parent : population) {
                int[] child = mutate(parent);
                nextGeneration.add(child);
            }
            population = nextGeneration;
            currentGeneration++;
        }
        int[] bestMove = findBestMove(population);
        return bestMove;
    }
    private List<int[]> generateInitialPopulation(int populationSize) {
        List<int[]> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            int[] move = generateRandomMove();
            population.add(move);
        }

        return population;
    }
    private int[] generateRandomMove() {
        int bestScore = -1;
        int[] bestMove = new int[2];

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    int[] move = new int[]{i, j};
                    int score = evaluateMove(move);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = move;
                    }
                }
            }
        }

        return bestMove;
    }
    private int[] mutate(int[] move) {
        Random random = new Random();

        int row = move[0];
        int col = move[1];

        int newRow, newCol;
        do {
            newRow = row + random.nextInt(3) - 1;
            newCol = col + random.nextInt(3) - 1;
        } while (!isValidMove(newRow, newCol));

        return new int[]{newRow, newCol};
    }
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < ROW && col >= 0 && col < COL && buttons[row][col].getText().isEmpty();
    }
    private int[] findBestMove(List<int[]> population) {
        int[] bestMove = population.get(0);
        int bestScore = evaluateMove(bestMove);

        for (int[] move : population) {
            int score = evaluateMove(move);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int evaluateMove(int[] move) {
        int row = move[0];
        int col = move[1];
        return countAdjacent(move, opponent) + countAdjacentAfter(move, bot);
    }

    private int countAdjacent(int[] move, String symbol) {
        int score = 0;
        int row = move[0];
        int col = move[1];

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

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
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (!isValidMove(newRow, newCol)) {
                continue;
            }
            if (buttons[newRow][newCol].getText().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private int countAdjacentAfter(int[] move, String symbol) {
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

                int newCost = countAdjacent(new int[]{newRow, newCol}, symbol) + 1;

                if (newCost > cost) {
                    cost = newCost;
                }
            }
        }

        return cost;
    }
}