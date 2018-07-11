import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    public static final int DOT_SIZE = 16;
    public  static final int SIZE = DOT_SIZE * (20 - 1);
    public static final int ALL_DOTS = 20 * 20;
    private Image dot;
    private Image head;
    private Image apple;
    private int appleX;
    private int appleY;
    private Timer timer;
    private boolean inGame = true;
    private boolean pause = false;
    private final String GAME_OVER = "GAME OVER";
    private boolean availableMove = false;
    private boolean idle = false;
    private boolean win = false;

    private Snake snake;



    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        createSnake();
        createApple();
        initTimer();
    }

    public void initTimer() {
        timer = new Timer(250, this);
        timer.start();
    }

    public void createSnake() {
        snake = new Snake(this);
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = idle ? snake.getNextY() : new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("snake.png");
        dot = iid.getImage();
        ImageIcon iih = new ImageIcon("snake_head.png");
        head = iih.getImage();
    }

    public void move() {
        snake.move();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (win) {
            g.setColor(Color.WHITE);
            g.drawString("WIN!", 125, SIZE/2);
        } else {
            if (inGame) {
                g.drawImage(apple, appleX, appleY, this);
                snake.draw(g, head, dot);
            } else {

                //Font f = new Font("Arial", 14, Font.BOLD);
                g.setColor(Color.WHITE);
                //g.setFont(f);
                g.drawString(GAME_OVER, 125, SIZE / 2);
            }
        }
    }

    private void win() {
        win = true;
        inGame = false;
    }

    public void checkApple() {
        if (snake.checkApple(appleX, appleY)) {
            createApple();
        }
    }

    public void checkCollisions() {
        if (snake.checkSelfCollision() || snake.checkFieldCollisions()) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!pause) {
            if (inGame) {
                if (snake.full()) {
                    win();
                    return;
                }
                availableMove = true;
                checkCollisions();
                checkApple();
                if (idle) {
                    availableMove = false;
                    snake.idle();
                    setTimer(10);
                }else {
                    setTimer(snake.getDelay());
                }
                move();
            }
            repaint();
        }
    }

    private void setTimer(int delay) {
        timer.stop();
        timer.setInitialDelay(delay);
        timer.restart();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if(availableMove) snake.setSide(Snake.Sides.left);
                    availableMove = false;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if(availableMove) snake.setSide(Snake.Sides.right);
                    availableMove = false;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if(availableMove) snake.setSide(Snake.Sides.up);
                    availableMove = false;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if(availableMove) snake.setSide(Snake.Sides.down);
                    availableMove = false;
                    break;
                case KeyEvent.VK_SPACE:
                    if (!inGame) {
                        inGame = true;
                        pause = false;
                        win = false;
                        createSnake();
                    }else {
                        pause = !pause;
                    }
                    break;
                case KeyEvent.VK_I:
                    idle = !idle;
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
            }
        }
    }

}
