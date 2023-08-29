package entities;

import java.awt.*;

public class EnemyBullet extends Rectangle implements Bullet{

    public EnemyBullet(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics g){
        g.setColor(Color.yellow);
        g.fillOval(x,y,width,height);
    }

    public void moveY(){
        y += 5;
    }
}
