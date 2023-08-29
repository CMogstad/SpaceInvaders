package gameLogic;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyActionListener extends KeyAdapter {

    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;

    private GamePanel gamePanel;

    public MyActionListener(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gamePanel.isRunning()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> rightKeyPressed = true;
                case KeyEvent.VK_LEFT -> leftKeyPressed = true;
                case KeyEvent.VK_SPACE -> gamePanel.createBullet();
            }
        }

        if (!gamePanel.isRunning()) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
               gamePanel.startGame();
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

    public boolean isLeftKeyPressed() {
        return leftKeyPressed;
    }

    public boolean isRightKeyPressed() {
        return rightKeyPressed;
    }
}
