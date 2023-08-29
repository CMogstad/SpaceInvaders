package gameLogic;

import entities.*;

import java.awt.*;
import java.util.ArrayList;

public class CheckCollision {

    public boolean checkCollisionBetweenSpaceshipBulletAndEnemy(ArrayList<SpaceshipBullet> spaceshipBullets, ArrayList<Enemy> enemies) {
        for (int i = 0; i < spaceshipBullets.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (spaceshipBullets.get(i).intersects(enemies.get(j))) {
                    enemies.remove(enemies.get(j));
                    spaceshipBullets.remove(spaceshipBullets.get(i));
                    return true;
                }
            }
        }
        return false;
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

    public boolean checkCollisionBetweenWallsAndBullets(ArrayList<Wall> walls, ArrayList<? extends Bullet> bullets) {
        for (int i = 0; i < walls.size(); i++) {

            for (Bullet bullet : bullets) {
                if (walls.get(i).intersects((Rectangle) bullet)) {
                    walls.get(i).hit();
                    bullets.remove(bullet);
                    if (walls.get(i).getRemainingHits() == 0) {
                        walls.remove(walls.get(i));
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
