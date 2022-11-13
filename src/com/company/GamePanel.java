package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE;
    static final int DELAY = 75;
    int[] x = new int[GAME_UNITS];
    int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        x[0] = UNIT_SIZE * 8;
        y[0] = UNIT_SIZE * 8;
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(final Graphics g) {
        //        System.out.println(running);
        if (running) {
            g.setColor(new Color(67, 32, 145, 255));
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyParts; i++) {
                if (0 == i) {
                    g.setColor(new Color(233, 253, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(166, 173, 51));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(new Color(114, 93, 159, 255));
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            final FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; 0 < i; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' -> y[0] -= UNIT_SIZE;
            case 'D' -> y[0] += UNIT_SIZE;
            case 'L' -> x[0] -= UNIT_SIZE;
            case 'R' -> x[0] += UNIT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        //checks if head collides with body
        for (int i = bodyParts; 0 < i; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                //         System.out.println("1");
                break;
            }
        }
        //check if head touches left border
        if (0 > x[0]) {
            running = false;

            //              System.out.println("2");
        }
        //check if head touches right border
        if (SCREEN_WIDTH < x[0]) {
            running = false;
            //System.out.println("3");
        }

        //check if head touches top border
        if (0 > y[0]) {
            running = false;
            // System.out.println("4");
        }

        //check if head touches bottom border
        if (SCREEN_HEIGHT <= y[0]) {
            running = false;
            //   System.out.println("5");
        }
        if (!running) {
            timer.stop();
        }

    }

    public void gameOver(final Graphics g) {
        //score
        g.setColor(new Color(114, 93, 159, 255));
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                g.getFont().getSize());


        //game over
        g.setColor(new Color(114, 93, 159, 255));
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }


    @Override
    public void actionPerformed(final ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();

        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(final KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if ('R' != direction) {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if ('L' != direction) {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if ('D' != direction) {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if ('U' != direction) {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    //                        System.out.println("Enter pressed");
                    if (!running) {
                        gameReset();
                    }
                    break;
            }
        }
    }

    private void gameReset() {
        direction = 'R';
        applesEaten = 0;
        bodyParts = 6;
        running = false;

        for (int i = bodyParts; 0 < i; i--) {
            x[i] = 6666;
            y[i] = 6666;
        }
        startGame();
    }
}



