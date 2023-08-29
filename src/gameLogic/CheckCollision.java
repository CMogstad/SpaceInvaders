package gameLogic;

import entities.*;

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
        for (int i = 0; i < enemyBullets.size(); i++) {
            if (enemyBullets.get(i).intersects(spaceship)) {
                enemyBullets.clear();
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

    public boolean checkCollisionBetweenWallsAndEnemyBullet(ArrayList<Wall> walls, ArrayList<EnemyBullet> enemyBullets) {
        for (int i = 0; i < walls.size(); i++) {

            for (EnemyBullet enemyBullet : enemyBullets) {
                if (walls.get(i).intersects(enemyBullet)) {
                    walls.get(i).hit();
                    enemyBullets.remove(enemyBullet);
                    if (walls.get(i).getRemainingHits() == 0) {
                        walls.remove(walls.get(i));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCollisionBetweenWallsAndSpaceshipBullets(ArrayList<Wall> walls, ArrayList<SpaceshipBullet> spaceshipBullets) {
        for (int i = 0; i < walls.size(); i++) {

            for (SpaceshipBullet spaceshipBullet : spaceshipBullets) {
                if (walls.get(i).intersects(spaceshipBullet)) {
                    walls.get(i).hit();
                    spaceshipBullets.remove(spaceshipBullet);
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
