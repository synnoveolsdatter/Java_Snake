package com.synnoveolsdatter.snake;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Snake implements Iterable<Short[]> {
    public ArrayList<short[]> snake;
    public short[] dir;
    
    private short[] head;
    private int snakeLen;

    public Snake(short[] pos) {
    	snake = new ArrayList<short[]>();
        head = pos;
        dir = new short[] {0, 0};
        for (short i = 0; i < 3; i++) {
            snake.add(new short[] {head[0], head[1]});
        }
        snakeLen = snake.size() - 1;
    }

    public void update() {
        short[] newHead = snake.remove(snakeLen);
        newHead[0] = (short)(head[0] + dir[0]);
        newHead[1] = (short)(head[1] + dir[1]);
        snake.add(0, newHead);
        head = newHead;
    }

    public short[] headPos() {
        return head;
    }

    public void extendSnake() {
        snake.add(snake.get(snakeLen).clone());
        snakeLen++;
    }

    public boolean inSnake(short[] pos) {
    	for (short[] part : snake) {
    	    if (part[0] == pos[0] && part[1] == pos[1]) {
    	        return true;
    	    }
    	}
        return false;
    }
    
    public ArrayList<short[]> withoutHead() {
    	ArrayList<short[]> wo = (ArrayList<short[]>)snake.clone();
    	wo.remove(0);
    	return wo;
    }

    public Iterator<Short[]> iterator() {
        return new Iterator<Short[]>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < snake.size();
            }

            @Override
            public Short[] next() {
                Short[] r = new Short[] {(Short)snake.get(i)[0], (Short)snake.get(i)[1]};
                i++;
                return r;
            }
        };
    }
}
