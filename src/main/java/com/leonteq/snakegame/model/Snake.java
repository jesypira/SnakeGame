package com.leonteq.snakegame.model;

import com.leonteq.snakegame.exception.GameOverException;

import java.util.List;

public class Snake {
    private List<Position> actualPositionList;

    private Position lastTail;

    public Snake(List<Position> actualPosition){
        this.actualPositionList = actualPosition;
    }

    protected List<Position> getActualPositionList() {
        return actualPositionList;
    }

    /**
     * Moves the snake according to {@link StepsEnum};
     * Eat the food, if it finds;
     * Return a boolean to create a new food pellet, if necessary;
     * @param step
     * @return
     */
    protected boolean move(StepsEnum step, Board board) {
        boolean needNewFood = false;
        Position lastPosition = new Position(0,0);

        //set last tail to be used to 'grow', in case of eating
        lastTail = new Position(actualPositionList.get(actualPositionList.size() - 1).getX(), actualPositionList.get(actualPositionList.size() - 1).getY());

        for (int i = 0; i < actualPositionList.size(); i++) {
            Position position = actualPositionList.get(i);

            //head
            if(i==0) {
                lastPosition = new Position(position.getX(), position.getY());
                moveHead(step, position, actualPositionList.get(1), board);

                //if collides with itself, game over
                if(actualPositionList.stream().filter(p->p.equals(position)).count()>1) {
                    throw new GameOverException("GAME OVER!");
                }

                //if ate the food, grows and create new food
                if (board.getFood().equals(position)) {
                    grow();
                    needNewFood = true;
                }
            } else {
                // the others will follow the previous position
                Position oldPosition = new Position(position.getX(), position.getY());
                position.setNewPosition(lastPosition.getX(), lastPosition.getY());
                lastPosition = oldPosition;
            }
        }
        return needNewFood;
    }

    /**
     * Identify the {@link StepsEnum} and move the head of the snake according to its position;
     * Its position it is defined by the second position of the snake;
     * <p>
     * Ex:. head = (3,4); second = (2,4);
     * Second position Y is equals to head position Y.
     * Then, we must compare if the head position X is bigger than second position X.
     * If true, the direction of the snake is for the right. Else, is for the left.
     *
     * @param step
     * @param position
     * @param secondPosition
     * @param board
     */
    private static void moveHead(StepsEnum step, Position position, Position secondPosition, Board board) {
        int moveX = 0;
        int moveY = 0;
        switch (step) {
            case FORWARD:
                if(isSameVerticalPosition(position, secondPosition)) {
                    moveX = isWrappedToZero(position.getX(), secondPosition.getX(), board.getWidth()) ?
                            1
                            : isWrappedToMax(position.getX(), secondPosition.getX(), board.getWidth()) ?
                            -1
                            : position.getX() - secondPosition.getX();
                }
                else if(isSameHorizontalPosition(position, secondPosition)) {
                    moveY = isWrappedToZero(position.getY(), secondPosition.getY(), board.getHeight()) ?
                            1
                            : isWrappedToMax(position.getY(), secondPosition.getY(), board.getHeight()) ?
                            -1
                            : position.getY() - secondPosition.getY();
                }
                break;
            case LEFT:
                if(isSameVerticalPosition(position, secondPosition)) {
                    moveY = isWrappedToZero(position.getX(), secondPosition.getX(), board.getWidth()) ?
                            -1
                            :isWrappedToMax(position.getX(), secondPosition.getX(), board.getWidth()) ?
                            1
                            : secondPosition.getX() - position.getX();
                }
                else if(isSameHorizontalPosition(position, secondPosition)) {
                    moveX = isWrappedToZero(position.getY(), secondPosition.getY(), board.getHeight()) ?
                            1
                            :isWrappedToMax(position.getY(), secondPosition.getY(), board.getHeight()) ?
                            -1
                            :position.getY() - secondPosition.getY();
                }
                break;
            case RIGHT:
                if(isSameVerticalPosition(position, secondPosition)) {
                    moveY = isWrappedToMax(position.getX(), secondPosition.getX(), board.getWidth()) ?
                         -1
                         : isWrappedToZero(position.getX(), secondPosition.getX(), board.getWidth()) ?
                         1
                         : position.getX() - secondPosition.getX();
                }
                else if(isSameHorizontalPosition(position, secondPosition)) {
                    moveX = isWrappedToZero(position.getY(),secondPosition.getY(),board.getHeight()) ?
                            -1
                            :isWrappedToMax(position.getY(), secondPosition.getY(), board.getHeight()) ?
                            1
                            :secondPosition.getY() - position.getY();
                }
        }

        int newPositionX = wrapAround(position.getX(), moveX, board.getHeight());
        int newPositionY = wrapAround(position.getY(), moveY, board.getWidth());

        position.setNewPosition(newPositionX, newPositionY);
    }

    private static boolean isSameVerticalPosition(Position position, Position secondPosition) {
        return position.getY() == secondPosition.getY();
    }

    private static boolean isSameHorizontalPosition(Position position, Position secondPosition) {
        return position.getX() == secondPosition.getX();
    }

    private static boolean isWrappedToZero(int head, int secondPosition, int max) {
        return head== 0 && secondPosition == max - 1;
    }

    private static boolean isWrappedToMax(int head, int secondPosition, int max) {
        return secondPosition == 0 && head == max - 1;
    }

    private static int wrapAround(int position, int move, int max) {
        int newPosition = position + move;
        if (newPosition > max - 1) {
            return 0; // wrap to the start
        } else if (newPosition < 0) {
            return max - 1; // wrap to the end
        }
        return newPosition;
    }

    /**
     * Grows the snake by putting another position in the 'last tail position'
     * before moving and eating the food pellet
     */
    private void grow() {
        actualPositionList.add(new Position(lastTail.getX(), lastTail.getY()));
    }
}
