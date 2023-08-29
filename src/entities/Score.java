package entities;

import java.awt.*;

public class Score extends Rectangle {

    private int score;

    public Score(int width, int height) {
        super(width, height);
        score = 0;
    }

    public void increaseScore() {
        score += 10;
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        String scoreStr = "Score: " + score;
        int widthStr = g.getFontMetrics().stringWidth(scoreStr);

        g.drawString(scoreStr, width - 30 - widthStr, 60);
    }
}
