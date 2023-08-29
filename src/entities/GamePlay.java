package entities;

import java.awt.*;

public class GamePlay extends Rectangle {

    public GamePlay(int width, int height) {
        super(width, height);
    }

    public void draw(Graphics g, boolean win) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 50));
        String message;
        int messageWidth;

        if (win) {
            message = "GAME OVER";
            messageWidth = g.getFontMetrics().stringWidth(message);
        } else {
            message = "YOU WIN!";
            messageWidth = g.getFontMetrics().stringWidth(message);
        }

        g.drawString(message, width /2 - messageWidth / 2, 200);

        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        message = "Push space to start a new game";
        messageWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, width /2 - messageWidth / 2, 300);
    }
}
