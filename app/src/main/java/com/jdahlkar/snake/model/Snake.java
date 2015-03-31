package com.jdahlkar.snake.model;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Johan on 2015-03-25.
 */
public class Snake {
    private static final int GO_UP = 0;
    private static final int GO_DOWN = 1;
    private static final int GO_LEFT = 2;
    private static final int GO_RIGHT = 3;
    private static final int moveX[] = {0, 0, -1, 1};
    private static final int moveY[] = {-1, 1, 0, 0};
    private int length = 1;
    private int direction;
    private LinkedList<SnakeSegment> body = new LinkedList<SnakeSegment>();


    public Snake(int x, int y){
        body.add(new SnakeSegment(x, y));
        body.add(new SnakeSegment(x, y+1));
        body.add(new SnakeSegment(x, y+2));
        direction = GO_UP;
    }
    public void goUp() {
        direction = GO_UP;
    }
    public void goDown() {
        direction = GO_DOWN;
    }
    public void goLeft() {
        direction = GO_LEFT;
    }
    public void goRight() {
        direction = GO_RIGHT;
    }
    public void addSegment(SnakeSegment seg) {
        body.add(seg);
    }
    public void move() {
        SnakeSegment head = body.peek();
        int headX = head.getX();
        int headY = head.getY();
        int x = headX + moveX[direction];
        int y = headY + moveY[direction];
        body.push(new SnakeSegment(x, y));
    }
    public boolean checkForSelfCollision() {
        SnakeSegment head = body.poll();
        int headX = head.getX();
        int headY = head.getY();
        Iterator<SnakeSegment> it = body.iterator();
        while(it.hasNext()) {
            SnakeSegment current = it.next();
            if(current.getX() == headX && current.getY() == headY) {
                body.push(head);
                return true;
            }
        }
        body.push(head);
        return false;
    }
    public SnakeSegment getHead() {
        return body.peek();
    }
    public Iterator<SnakeSegment> getBody() {
        return body.iterator();
    }
    public void dropLast() {
        body.removeLast();
    }
}
