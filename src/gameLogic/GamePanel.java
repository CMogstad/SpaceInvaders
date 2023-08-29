package gameLogic;

import entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {

    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;
    private static final Dimension SCREEN_DIMENSIONS = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    private CheckCollision checkCollision;
    private HandleCollision handleCollision;
    private MyActionListener myActionListener;
    private EntityCreation entityCreation;
    private Movement movement;
    private Spaceship spaceship;
    private ArrayList<SpaceshipBullet> spaceshipBullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<EnemyBullet> enemyBullets;

    private ArrayList<Wall> walls;
    private Timer timer;
    private Score score;
    private LifeCount lifeCount;
    private GamePlay gamePlay;
    private boolean running;
    long prevWhen = 0;
    private float timeUntilNextSpaceshipBullet = 0;
    private float delaySpaceshipBullets = 0.5f;
    float timeUntilNextEnemyBullet = 0;
    float timeUntilNextEnemyMovement = 0;
    float delayEnemyBullet = 1.5f;
    float delayEnemyMovement = 1f;

    public GamePanel() {
        checkCollision = new CheckCollision();
        handleCollision = new HandleCollision();
        entityCreation = new EntityCreation();
        myActionListener = new MyActionListener(this, entityCreation);
        this.setFocusable(true);
        this.setPreferredSize(SCREEN_DIMENSIONS);
        this.addKeyListener(myActionListener);
        startGame();

    }

    public void startGame() {
        running = true;
        spaceship = entityCreation.createSpaceship(SCREEN_WIDTH, SCREEN_HEIGHT);
        spaceshipBullets = new ArrayList<>();
        enemies = entityCreation.createEnemies();
        walls = entityCreation.createWalls(SCREEN_WIDTH, SCREEN_HEIGHT);
        enemyBullets = new ArrayList<>();
        //randomEnemy = new Random();
        lifeCount = new LifeCount();
        gamePlay = new GamePlay(SCREEN_WIDTH, SCREEN_HEIGHT);
        score = new Score(SCREEN_WIDTH, SCREEN_HEIGHT);
        movement = new Movement(spaceship, enemies, EntityCreation.getEnemyWidth(), SCREEN_WIDTH, myActionListener);
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

    public boolean isRunning() {
        return running;
    }

    private void handleSpaceshipHit() {
        boolean gameOver = lifeCount.looseLife();
        if (gameOver) {
            gameOver();
        }
    }

    private void gameOver() {
        running = false;
        timer.stop();
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

            if(timeUntilNextSpaceshipBullet >= delaySpaceshipBullets && myActionListener.isSpaceBarPressed()){
                entityCreation.shootSpaceshipBullet(spaceship, spaceshipBullets);
                timeUntilNextSpaceshipBullet = 0;
            }

            if (timeUntilNextEnemyBullet >= delayEnemyBullet) {
                entityCreation.shootEnemyBullet(enemies, enemyBullets);
                timeUntilNextEnemyBullet = 0;
            }

            if (timeUntilNextEnemyMovement >= delayEnemyMovement) {
                movement.moveEnemies();
                timeUntilNextEnemyMovement = 0;
            }

            for (SpaceshipBullet spaceshipBullet : spaceshipBullets) {
                spaceshipBullet.moveY();
            }

            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.moveY();
            }

            movement.moveSpaceship();

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
}
