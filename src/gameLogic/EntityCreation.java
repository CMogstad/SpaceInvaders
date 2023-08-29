package gameLogic;

import entities.*;

import java.util.ArrayList;
import java.util.Random;

public class EntityCreation {

    private static final int ENEMY_WIDTH = 40;
    private static final int ENEMY_HEIGHT = 40;
    private static final int SPACESHIP_BULLET_WIDTH = 10;
    private static final int SPACESHIP_BULLET_HEIGHT = 10;
    private static final int SPACESHIP_WIDTH = 60;
    private static final int SPACESHIP_HEIGHT = 50;
    private static final int ENEMY_BULLET_WIDTH = 10;
    private static final int ENEMY_BULLET_HEIGHT = 10;
    private Random enemyRandomizer = new Random();

    public Spaceship createSpaceship(int screenWidth, int screenHeight) {
        return new Spaceship((screenWidth / 2 - SPACESHIP_WIDTH), (screenHeight - 80), SPACESHIP_WIDTH, SPACESHIP_HEIGHT, screenWidth);
    }

    public static int getEnemyWidth() {
        return ENEMY_WIDTH;
    }

    public void shootSpaceshipBullet(Spaceship spaceship, ArrayList<SpaceshipBullet> spaceshipBullets) {
        int bulletX = (int) spaceship.getX() + SPACESHIP_WIDTH / 2 - SPACESHIP_BULLET_WIDTH / 2;
        int bulletY = (int) spaceship.getY();
        SpaceshipBullet spaceshipBullet = new SpaceshipBullet(bulletX, bulletY, SPACESHIP_BULLET_WIDTH, SPACESHIP_BULLET_HEIGHT);
        spaceshipBullets.add(spaceshipBullet);
    }

    public ArrayList<Enemy> createEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        int enemyX = 130;
        int enemyY = 100;
        for (int i = 0; i < 10; i++) {
            Enemy enemy = new Enemy(enemyX + 80 * i, enemyY, ENEMY_WIDTH, ENEMY_HEIGHT);
            enemies.add(enemy);
        }

        enemyY = enemyY + 80;
        for (int i = 0; i < 10; i++) {
            Enemy enemy = new Enemy(enemyX + 80 * i, enemyY, ENEMY_WIDTH, ENEMY_HEIGHT);
            enemies.add(enemy);
        }


        enemyY = enemyY + 80;
        for (int i = 0; i < 10; i++) {
            Enemy enemy = new Enemy(enemyX + 80 * i, enemyY, ENEMY_WIDTH, ENEMY_HEIGHT);
            enemies.add(enemy);
        }
        return enemies;
    }

    public ArrayList<Wall> createWalls(int screenWidth, int screenHeight) {
        ArrayList<Wall> walls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Wall wall = new Wall(screenWidth / 9 * (2 * i) + screenWidth / 9, screenHeight - 180, 80, 30);
            walls.add(wall);
        }
        return walls;
    }

    public void shootEnemyBullet(ArrayList<Enemy> enemies, ArrayList<EnemyBullet> enemyBullets) {
        if (!enemies.isEmpty()) {
            int randomEnemyIndex = enemyRandomizer.nextInt(enemies.size());
            int bulletX = enemies.get(randomEnemyIndex).x + ENEMY_WIDTH / 2 - ENEMY_BULLET_WIDTH / 2;
            int bulletY = enemies.get(randomEnemyIndex).y + ENEMY_HEIGHT;
            EnemyBullet enemyBullet = new EnemyBullet(bulletX, bulletY, ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT);
            enemyBullets.add(enemyBullet);
        }
    }
}
