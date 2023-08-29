package gameLogic;

import entities.*;

import java.awt.*;
import java.util.ArrayList;

public class CheckCollision {



    public IndexPair getCollisionIndexesSpaceshipBulletAndEnemy(ArrayList<SpaceshipBullet> spaceshipBullets, ArrayList<Enemy> enemies) {
        for (int i = 0; i < spaceshipBullets.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (spaceshipBullets.get(i).intersects(enemies.get(j))) {
                    return new IndexPair(i, j);
                }
            }
        }
        return null;
    }

    public boolean checkCollisionBetweenEnemyBulletsAndSpaceship(ArrayList<EnemyBullet> enemyBullets, Spaceship spaceship) {
        for (EnemyBullet enemyBullet : enemyBullets) {
            if (enemyBullet.intersects(spaceship)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCollisionBetweenSpaceShipAndEnemies(Spaceship spaceship, ArrayList<Enemy> enemies) {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).intersects(spaceship)) {
                enemies.remove(enemies.get(i));
                return true;
            }
        }
        return false;
    }

    public IndexPair checkCollisionIndexesWallsAndBullets(ArrayList<Wall> walls, ArrayList<? extends Bullet> bullets) {
        for (int i = 0; i < walls.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {
                if (walls.get(i).intersects((Rectangle) bullets.get(j))) {
                    return new IndexPair(i, j);
                }
            }
        }
        return null;
    }
}
