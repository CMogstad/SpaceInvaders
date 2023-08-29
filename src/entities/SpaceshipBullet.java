package entities;

import java.awt.*;

public class SpaceshipBullet extends Rectangle {

    public SpaceshipBullet(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }

    public void moveY(){
        y -= 10;
    }


}
