package com.jdahlkar.snake.model;

import java.util.Random;

/**
 * Created by Johan on 2015-03-25.
 */
public class Food {
    private int x;
    private int y;
    private int boardWidth;
    private int boardHeight;
    private Random rand = new Random();

    public Food(int boardWidth, int boardHeight) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        respawn();
    }
    public void respawn() {
        this.x = 1 + rand.nextInt(Integer.MAX_VALUE) % (boardWidth - 2);
        this.y = 1 + rand.nextInt(Integer.MAX_VALUE) % (boardHeight - 2);
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
