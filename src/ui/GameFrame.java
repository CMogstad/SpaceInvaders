package ui;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    GamePanel gamePanel;
    public GameFrame(){
        gamePanel = new GamePanel();
        this.add(gamePanel);
        this.setTitle("Space Invaders");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
