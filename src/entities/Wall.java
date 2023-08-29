package entities;

import java.awt.*;

public class Wall extends Rectangle {

    private int remainingHits;

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        remainingHits = 10;
    }

    public void hit(){
        remainingHits--;
    }

    public int getRemainingHits() {
        return remainingHits;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);

        String remainingHitsStr = String.valueOf(remainingHits);
        int widthStr = g.getFontMetrics().stringWidth(remainingHitsStr);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Consolas", Font.PLAIN, 20));
        g.drawString(remainingHitsStr, x + width / 2 - widthStr / 2, y + height * 3 / 4);
    }

}
