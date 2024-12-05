package com.leonteq.snakegame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private int width;
    private int height;
    private Position food;
    private Snake snake;

    private int score;

    public Board(int width, int height, Position food, Snake snake){
        this.width = width;
        this.height = height;
        this.food = food;
        this.snake = snake;
    }

    public static Board createDefaultBoard() {
        Snake snake = new Snake(new ArrayList<Position>(Arrays.asList(new Position(4, 4), new Position(3, 4), new Position(2, 4))));
        Board board = new Board(9,9, new Position(2,2), snake);
        board.print();
        return board;
    }

    public void print() {
        clearConsole();
        for(int h=0; h<height; h++) {
            System.out.println();
            for (int w = 0; w < width; w++) {
                Position position = new Position(w, h);

                System.out.print(" ");

                if (snake.getActualPositionList().contains(position)) {
                    //verify if it is head (snakeIndex == 0)
                    int snakeIndex = snake.getActualPositionList().indexOf(position);
                    System.out.print(snakeIndex == 0 ? "o" : "x");
                } else if (food.equals(position)) {
                    System.out.print("@");
                } else {
                    System.out.print(".");
                }

                System.out.print(" ");
            }
        }
    }

    private void clearConsole() {
        for(int h=0; h<height/2; h++) {
            System.out.println();
        }
    }

    public void moveSnake(StepsEnum step) {
        boolean needNewFood = snake.move(step, this);
        if(needNewFood) {
            score++;
            createNewFood();
        }
        print();
    }

    private void createNewFood() {
        while (snake.getActualPositionList().contains(food))
            food = new Position(new Random().nextInt(width), new Random().nextInt(height));
    }

    public Position getFood(){
        return food;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public Position getSnakeHeadPosition(){
        return snake.getActualPositionList().get(0);
    }

    public int getSnakeSize(){
        return snake.getActualPositionList().size();
    }

    public boolean snakeContains(Position newFood) {
        return snake.getActualPositionList().contains(newFood);
    }
}
