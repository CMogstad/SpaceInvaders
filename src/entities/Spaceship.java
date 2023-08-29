package entities;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Spaceship extends Rectangle {

    private int screenWidth;


    public Spaceship(int x, int y, int width, int height, int screenWidth) {
        super(x, y, width, height);
        this.screenWidth = screenWidth;
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
    }

    public void moveRight(int marginEdge){
        if (x + width + marginEdge <= screenWidth) {
            x = x + 10;
        }
    }

    public void moveLeft(int marginEdge){
        if (x - marginEdge >= 0) {
            x = x - 10;
        }
    }
}
