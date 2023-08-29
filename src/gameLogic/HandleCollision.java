package gameLogic;

import entities.*;

import java.util.ArrayList;

public class HandleCollision {

    public void collideWallsAndBullets(ArrayList<Wall> walls, ArrayList<? extends Bullet> bullets, IndexPair indexWallSpaceshipBullet) {
        walls.get(indexWallSpaceshipBullet.getFirst()).hit();
        bullets.remove(bullets.get(indexWallSpaceshipBullet.getSecond()));
        if (walls.get(indexWallSpaceshipBullet.getFirst()).getRemainingHits() == 0) {
            walls.remove(walls.get(indexWallSpaceshipBullet.getFirst()));
        }
    }

    public void collideSpaceshipBulletAndEnemy(ArrayList<SpaceshipBullet> spaceshipBullets, ArrayList<Enemy> enemies, IndexPair indexSpaceshipBulletEnemy) {
        //score.increaseScore();
        spaceshipBullets.remove(spaceshipBullets.get(indexSpaceshipBulletEnemy.getFirst()));
        enemies.remove(enemies.get(indexSpaceshipBulletEnemy.getSecond()));
    }
}
