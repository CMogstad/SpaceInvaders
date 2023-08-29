package gameLogic;

import javax.swing.*;

public class GameLifecycle {

    private boolean running;
    private Timer timer;

    private GamePanel gamePanel;

    public GameLifecycle(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void startGame() {
        running = true;
        gamePanel.instantiateAllUIObjects();
        timer = new Timer(10, gamePanel);
        timer.start();
    }

    public boolean isRunning() {
        return running;
    }

    public void gameOver() {
        running = false;
        timer.stop();
    }

}
