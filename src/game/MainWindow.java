package game;

import javax.swing.*;

import static game.Game.*;
import static game.Snake.SNAKE_OVERHEAD;

public class MainWindow extends JFrame {

    private final int TOP_SHIFT = 25;

    private MainWindow() {
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(FIELD_SIZE + SNAKE_OVERHEAD, FIELD_SIZE + SNAKE_OVERHEAD + TOP_SHIFT);
        setLocation(ALL_DOTS, ALL_DOTS);
        add(new Game());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
