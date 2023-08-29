package gameLogic;

import entities.Enemy;
import entities.Spaceship;

import java.util.ArrayList;

public class Movement {

    private Spaceship spaceship;
    private ArrayList<Enemy> enemies;
    private int enemyWidth;
    private int screenWidth;
    private MyActionListener myActionListener;
    private String direction = "right";

    public Movement(Spaceship spaceship, ArrayList<Enemy> enemies, int enemyWidth, int screenWidth, MyActionListener myActionListener) {
        this.spaceship = spaceship;
        this.enemies = enemies;
        this.enemyWidth = enemyWidth;
        this.screenWidth = screenWidth;
        this.myActionListener = myActionListener;
    }

    public void moveEnemies() {
        switch (direction) {
            case "right" -> moveEnemiesRight();
            case "left" -> moveEnemiesLeft();
            case "down" -> moveEnemiesDown();
        }

        determineNextDirection();
    }

    public void moveEnemiesRight() {
        for (Enemy enemy : enemies) {
            enemy.x += 10;
        }
    }

    public void moveEnemiesLeft() {
        for (Enemy enemy : enemies) {
            enemy.x -= 10;
        }
    }

    public void moveEnemiesDown() {
        for (Enemy enemy : enemies) {
            enemy.y += 10;
        }
    }

    public void determineNextDirection() {
        int marginEdge = 20;

        for (Enemy enemy : enemies) {
            if (!direction.equals("down") && (enemy.x + enemyWidth + marginEdge >= screenWidth || enemy.x - marginEdge <= 0)) {
                direction = "down";
                break;
            } else if (enemy.x + enemyWidth + marginEdge >= screenWidth) {
                direction = "left";
                break;
            } else if (enemy.x - marginEdge <= 0) {
                direction = "right";
                break;
            }
        }
    }

    public void moveSpaceship() {
        int marginEdge = 20;
        if (myActionListener.isRightKeyPressed()) {
            spaceship.moveRight(marginEdge);
        } else if (myActionListener.isLeftKeyPressed()) {
            spaceship.moveLeft(marginEdge);
        }
    }
}
