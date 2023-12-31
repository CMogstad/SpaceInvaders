package entities;

import java.awt.*;

public class LifeCount {
    private int lives = 3;

    public void looseLife() {
        lives--;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        //String livesStr = String.valueOf(lives);

        g.drawString("Lives: ", 30, 60);

        for (int i = 0; i < lives; i++) {
            g.fillOval(150 + 50 * i, 35, 25, 25);
        }
    }

    public int getLives() {
        return lives;
    }
}
