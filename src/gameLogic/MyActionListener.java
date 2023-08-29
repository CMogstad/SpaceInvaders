package gameLogic;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyActionListener extends KeyAdapter {

    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;
    private boolean spaceBarPressed = false;

    private GameLifecycle gameLifecycle;

    public MyActionListener(GameLifecycle gameLifecycle) {
        this.gameLifecycle = gameLifecycle;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameLifecycle.isRunning()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> rightKeyPressed = true;
                case KeyEvent.VK_LEFT -> leftKeyPressed = true;
                case KeyEvent.VK_SPACE -> spaceBarPressed = true;
            }
        }
        if (!gameLifecycle.isRunning()) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameLifecycle.startGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT -> rightKeyPressed = false;
            case KeyEvent.VK_LEFT -> leftKeyPressed = false;
            case KeyEvent.VK_SPACE -> spaceBarPressed = false;
        }
    }

    public boolean isLeftKeyPressed() {
        return leftKeyPressed;
    }

    public boolean isRightKeyPressed() {
        return rightKeyPressed;
    }

    public boolean isSpaceBarPressed() {
        return spaceBarPressed;
    }
}
