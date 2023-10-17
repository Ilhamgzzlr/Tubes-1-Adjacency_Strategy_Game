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

    private Random random = new Random();

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
        List<int[]> availableMoves = findAvailableMoves(); 

        if (availableMoves.isEmpty()) {
            return new int[]{0, 0};
        }

        // int randomIndex = random.nextInt(availableMoves.size());
        // int[] move = availableMoves.get(randomIndex);

        int[] current = availableMoves.get(0);
        int globalOptimum = 4;
        int maxScore = 0;

        System.out.println(availableMoves.size());
        for (int[] neighbour : availableMoves) {
            int score = utilityFunction(neighbour);
            if (score > 0){
                System.out.println("Move: " + neighbour[0] + " " + neighbour[1]);
                System.out.println("Score: " + score);
            }
            if (score > maxScore) {
                maxScore = score;
                current = neighbour;
            }
            if(maxScore == globalOptimum){
                break;
            }
        }
      
        return current;
    }

    private List<int[]> findAvailableMoves() {
        List<int[]> availableMoves = new ArrayList<>();

        // Iterate through the game board and find empty cells where the bot can make a move.
        // Add the coordinates of these empty cells to the availableMoves list.

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    availableMoves.add(new int[]{i, j});
                }
            }
        }

        return availableMoves;
    }

    private int utilityFunction(int[] move) {
        return checkAdjacent(move);
    }

    private int checkAdjacent(int[] move){
        // check if adjacent move is opponent symbol
        // if it is score++

        int score = 0;
        int row = move[0];
        int col = move[1];

        // check left
        if (col > 0) {
            if (buttons[row][col - 1].getText().equals(opponent)) {
                System.out.println(buttons[row][col - 1].getText());
                System.out.println("Left");
                score++;
            }
        }

        // check right
        if (col < COL - 1) {
            if (buttons[row][col + 1].getText().equals(opponent)) {
                System.out.println(buttons[row][col + 1].getText());
                System.out.println("Right");
                score++;
            }
        }

        // check up
        if (row > 0) {
            if (buttons[row - 1][col].getText().equals(opponent)) {
                System.out.println(buttons[row - 1][col].getText());
                System.out.println("Up");
                score++;
            }
        }

        // check down
        if (row < ROW - 1) {
            if (buttons[row + 1][col].getText().equals(opponent)) {
                System.out.println(buttons[row + 1][col].getText());
                System.out.println("Down");
                score++;
            }
        }
        
        return score;
    }
}