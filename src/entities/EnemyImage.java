package entities;

import javax.swing.*;

public class EnemyImage extends JLabel {

    ImageIcon image = new ImageIcon("C:/Users/cammog/IdeaProjects/SpaceInvaders/src/images/alienMini.png");

    public EnemyImage() {
        this.setIcon(image);
    }
}
