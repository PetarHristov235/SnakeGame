package com.company;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame(){
        add(new GamePanel());
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
