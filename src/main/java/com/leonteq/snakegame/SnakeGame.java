package com.leonteq.snakegame;

import com.leonteq.snakegame.exception.GameOverException;
import com.leonteq.snakegame.model.Board;
import com.leonteq.snakegame.model.StepsEnum;

import java.util.Scanner;

public class SnakeGame {

    public static void main(String[] args) {
        Board board = Board.createDefaultBoard();
        Scanner scanner = new Scanner(System.in);

        try {
            //Remove comment to simulate to eat the first food pellet
            //eatFirstFoodPellet(board);

            //Remove comment to play the game
            //playGame(board, scanner);
        }catch (GameOverException e){
            System.out.println("\n\n #### GAME OVER!!! ####");
        } finally {
            scanner.close();
        }
    }

    private static void playGame(Board board, Scanner scanner) {
        while (true) {
            System.out.println("\n SCORE: "+ board.getScore());
            System.out.print(" Move (W = FORWARD; A = LEFT; D = RIGHT): ");
            String input = scanner.nextLine().trim().toUpperCase();
            StepsEnum step;
            switch (input) {
                case "W" -> step = StepsEnum.FORWARD;
                case "A" -> step = StepsEnum.LEFT;
                case "D" -> step = StepsEnum.RIGHT;
                default -> {
                    System.out.println("Invalid input! Use W (forward), A (left), D (right).");
                    continue;
                }
            }
            board.moveSnake(step);
        }
    }

    private static void eatFirstFoodPellet(Board board) {
        board.moveSnake(StepsEnum.LEFT);
        board.moveSnake(StepsEnum.LEFT);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.RIGHT);
    }
}
