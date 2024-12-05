package com.leonteq.snakegame;

import com.leonteq.snakegame.exception.GameOverException;
import com.leonteq.snakegame.model.Board;
import com.leonteq.snakegame.model.Position;
import com.leonteq.snakegame.model.StepsEnum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeGameTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = Board.createDefaultBoard();
    }

    @Test
    void whenGivenSteps_thenEatsTheFoodPellet(){
        Position oldFood = board.getFood();
        int snakeOldSize = board.getSnakeSize();

        board.moveSnake(StepsEnum.LEFT);
        board.moveSnake(StepsEnum.LEFT);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.RIGHT);

        assertEquals(oldFood, board.getSnakeHeadPosition());
        assertEquals(snakeOldSize+1, board.getSnakeSize());
        assertNotEquals(oldFood, board.getFood());
    }

    @Test
    void whenFoodEaten_thenNewFoodPositionIsValid() {
        board.moveSnake(StepsEnum.LEFT);
        board.moveSnake(StepsEnum.LEFT);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.RIGHT);

        Position newFood = board.getFood();
        assertFalse(board.snakeContains(newFood));
    }

    @Test
    void whenGivenSteps_thenGameOver(){
        board.moveSnake(StepsEnum.LEFT);
        board.moveSnake(StepsEnum.LEFT);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.RIGHT);
        board.moveSnake(StepsEnum.RIGHT);

        assertThrows(GameOverException.class, () ->  board.moveSnake(StepsEnum.RIGHT));
    }

    @Test
    void whenGivenSteps_thenWrapsAround(){
        //wraps around X
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.FORWARD);

        assertEquals(0, board.getSnakeHeadPosition().getX());

        //wraps around Y
        board.moveSnake(StepsEnum.LEFT);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.FORWARD);
        board.moveSnake(StepsEnum.FORWARD);

        assertEquals(8, board.getSnakeHeadPosition().getY());

        //wraps around X
        board.moveSnake(StepsEnum.LEFT);

        assertEquals(8, board.getSnakeHeadPosition().getX());

        //wraps around y
        board.moveSnake(StepsEnum.LEFT);

        assertEquals(0, board.getSnakeHeadPosition().getY());
    }

}
