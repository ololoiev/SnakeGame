import javax.swing.*;

public class MainWindow extends JFrame {

    private final int TOP_SHIFT = 22;

    public MainWindow() {
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(GameField.SIZE+32, GameField.SIZE + TOP_SHIFT+32);
        setLocation(GameField.ALL_DOTS, GameField.ALL_DOTS);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
