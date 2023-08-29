package gameLogic;

import entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;
    private static final Dimension SCREEN_DIMENSIONS = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);

    private CheckCollision checkCollision;
    private HandleCollision handleCollision;
    private Spaceship spaceship;
    private static final int SPACESHIP_WIDTH = 60;
    private static final int SPACESHIP_HEIGHT = 50;
    private ArrayList<SpaceshipBullet> spaceshipBullets;
    private static final int SPACESHIP_BULLET_WIDTH = 10;
    private static final int SPACESHIP_BULLET_HEIGHT = 10;
    private ArrayList<Enemy> enemies;
    private static final int ENEMY_WIDTH = 40;
    private static final int ENEMY_HEIGHT = 40;
    private ArrayList<EnemyBullet> enemyBullets;
    private static final int ENEMY_BULLET_WIDTH = 10;
    private static final int ENEMY_BULLET_HEIGHT = 10;

    private ArrayList<Wall> walls;
    private Timer timer;
    private Random randomEnemy;
    private Score score;
    private LifeCount lifeCount;
    private GamePlay gamePlay;
    private boolean running;
    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;
    private String direction = "right";
    long prevWhen = 0;
    float timeUntilNextSpaceshipBullet = 0;
    float timeUntilNextEnemyBullet = 0;
    float timeUntilNextEnemyMovement = 0;
    float delaySpaceshipBullets = 0.5f;
    float delayEnemyBullet = 1.5f;
    float delayEnemyMovement = 1f;

    public GamePanel() {
        checkCollision = new CheckCollision();
        handleCollision = new HandleCollision();
        this.setFocusable(true);
        this.setPreferredSize(SCREEN_DIMENSIONS);
        this.addKeyListener(new MyActionsListener());
        startGame();
    }

    public void startGame() {
        running = true;
        createSpaceship();
        spaceshipBullets = new ArrayList<>();
        createEnemies();
        createWalls();
        enemyBullets = new ArrayList<>();
        randomEnemy = new Random();
        lifeCount = new LifeCount();
        gamePlay = new GamePlay(SCREEN_WIDTH, SCREEN_HEIGHT);
        score = new Score(SCREEN_WIDTH, SCREEN_HEIGHT);
        timer = new Timer(10, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        Image image = createImage(getWidth(), getHeight()); //getwidth and height is the size of the current component.
        Graphics graphics = image.getGraphics();
        draw(graphics);

        g.drawImage(image, 0, 0, this);
    }

    private void draw(Graphics g) {
        score.draw(g);
        lifeCount.draw(g);
        spaceship.draw(g);
        for (SpaceshipBullet spaceshipBullet : spaceshipBullets) {
            spaceshipBullet.draw(g);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        for (EnemyBullet enemyBullet : enemyBullets) {
            enemyBullet.draw(g);
        }

        for (Wall wall : walls) {
            wall.draw(g);
        }

        if (!running) {
            gamePlay.draw(g, lifeCount.getLives() == 0);
        }
    }

    public void createSpaceship() {
        spaceship = new Spaceship((SCREEN_WIDTH / 2 - SPACESHIP_WIDTH), (SCREEN_HEIGHT - 80), SPACESHIP_WIDTH, SPACESHIP_HEIGHT, SCREEN_WIDTH);
    }

    public void createBullet() {
        if (timeUntilNextSpaceshipBullet >= delaySpaceshipBullets) {
            int bulletX = (int) spaceship.getX() + SPACESHIP_WIDTH / 2 - SPACESHIP_BULLET_WIDTH / 2;
            int bulletY = (int) spaceship.getY();
            SpaceshipBullet spaceshipBullet = new SpaceshipBullet(bulletX, bulletY, SPACESHIP_BULLET_WIDTH, SPACESHIP_BULLET_HEIGHT);
            spaceshipBullets.add(spaceshipBullet);
            timeUntilNextSpaceshipBullet = 0;
        }
    }

    public void createEnemies() {
        enemies = new ArrayList<>();
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
    }

    public void createWalls() {
        walls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Wall wall = new Wall(SCREEN_WIDTH / 9 * (2 * i) + SCREEN_WIDTH / 9, SCREEN_HEIGHT - 180, 80, 30);
            walls.add(wall);
        }
    }

    public void shootEnemyBullet() {
        if (!enemies.isEmpty()) {
            int randomEnemyIndex = randomEnemy.nextInt(enemies.size());
            int bulletX = enemies.get(randomEnemyIndex).x + ENEMY_WIDTH / 2 - ENEMY_BULLET_WIDTH / 2;
            int bulletY = enemies.get(randomEnemyIndex).y + ENEMY_HEIGHT;
            EnemyBullet enemyBullet = new EnemyBullet(bulletX, bulletY, ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT);
            enemyBullets.add(enemyBullet);
        }
    }

    private void handleSpaceshipHit() {
        boolean gameOver = lifeCount.looseLife();
        if (gameOver) {
            gameOver();
        } else {
            createSpaceship();
        }
    }

    private void gameOver() {
        running = false;
        timer.stop();
    }

    private void moveEnemies() {
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
            if (!direction.equals("down") && (enemy.x + ENEMY_WIDTH + marginEdge >= SCREEN_WIDTH || enemy.x - marginEdge <= 0)) {
                direction = "down";
                break;
            } else if (enemy.x + ENEMY_WIDTH + marginEdge >= SCREEN_WIDTH) {
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
        if (rightKeyPressed) {
            spaceship.moveRight(marginEdge);
        } else if (leftKeyPressed) {
            spaceship.moveLeft(marginEdge);

        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        long when = e.getWhen();
        long deltaMs = when - prevWhen;
        float deltaSeconds = deltaMs / 1000f;
        prevWhen = when;

        if (running) {
            timeUntilNextSpaceshipBullet += deltaSeconds;
            timeUntilNextEnemyBullet += deltaSeconds;
            timeUntilNextEnemyMovement += deltaSeconds;

            if (timeUntilNextEnemyBullet >= delayEnemyBullet) {
                shootEnemyBullet();
                timeUntilNextEnemyBullet = 0;
            }

            if (timeUntilNextEnemyMovement >= delayEnemyMovement) {
                moveEnemies();
                timeUntilNextEnemyMovement = 0;
            }

            for (SpaceshipBullet spaceshipBullet : spaceshipBullets) {
                spaceshipBullet.moveY();
            }

            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.moveY();
            }

            moveSpaceship();

            IndexPair indexSpaceshipBulletEnemy = checkCollision.getCollisionIndexesSpaceshipBulletAndEnemy(spaceshipBullets, enemies);
            {
                if (indexSpaceshipBulletEnemy != null) {
                    score.increaseScore(); //TODO: Keep this here and not in HandleCollision?
                    handleCollision.collideSpaceshipBulletAndEnemy(spaceshipBullets, enemies, indexSpaceshipBulletEnemy);
                    if (enemies.isEmpty()) {
                        gameOver();
                    }
                }
            }

            if (checkCollision.checkCollisionBetweenEnemyBulletsAndSpaceship(enemyBullets, spaceship)) {
                enemyBullets.clear();
                handleSpaceshipHit();
            }

            if (checkCollision.checkCollisionBetweenSpaceShipAndEnemies(spaceship, enemies)) {
                handleSpaceshipHit();
            }

            IndexPair indexWallEnemyBullet = checkCollision.checkCollisionIndexesWallsAndBullets(walls, enemyBullets);
            if (indexWallEnemyBullet != null) {
                handleCollision.collideWallsAndBullets(walls, enemyBullets, indexWallEnemyBullet);
            }

            IndexPair indexWallSpaceshipBullet = checkCollision.checkCollisionIndexesWallsAndBullets(walls, spaceshipBullets);
            if (indexWallSpaceshipBullet != null) {
                handleCollision.collideWallsAndBullets(walls, spaceshipBullets, indexWallSpaceshipBullet);
            }
        }
        repaint();
    }

    public class MyActionsListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (running) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT -> rightKeyPressed = true;
                    case KeyEvent.VK_LEFT -> leftKeyPressed = true;
                    case KeyEvent.VK_SPACE -> createBullet();
                }
            }

            if (!running) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    startGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> rightKeyPressed = false;
                case KeyEvent.VK_LEFT -> leftKeyPressed = false;
            }
        }
    }
}
