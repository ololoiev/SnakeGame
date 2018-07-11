import javax.swing.*;
import java.awt.*;

public class Snake {

    public enum Sides implements HasOpposite{
        right {
            @Override
            public boolean isOpposite(Sides side) {
                return Sides.left.equals(side);
            }
        }, up {
            @Override
            public boolean isOpposite(Sides side) {
                return Sides.down.equals(side);
            }
        }, left {
            @Override
            public boolean isOpposite(Sides side) {
                return Sides.right.equals(side);
            }
        }, down {
            @Override
            public boolean isOpposite(Sides side) {
                return Sides.up.equals(side);
            }
        };
    }

    interface HasOpposite {
        boolean isOpposite(Sides side);
    }


    private JPanel field;

    private Image dot;

    private Sides side;

    private int[] x;
    private int[] y;

    private int dots;

    private boolean idle_right = true;
    private int idle_move = 0;

    public Snake(GameField field) {
        this.field = field;
        dots = 3;
        side = Sides.right;
        x = new int[GameField.ALL_DOTS];
        y = new int[GameField.ALL_DOTS];
        for (int i = 0; i < dots; i++) {
            x[i] = dots * GameField.DOT_SIZE - i * GameField.DOT_SIZE;
            y[i] = dots * GameField.DOT_SIZE;
        }
    }

    public boolean setSide(Sides side) {
        if (!side.isOpposite(this.side)) {
            this.side = side;
            return true;
        }
        return false;
    }

    public void move() {
        for (int i = dots - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (side) {
            case left: x[0] -= GameField.DOT_SIZE; break;
            case down: y[0] += GameField.DOT_SIZE; break;
            case right: x[0] += GameField.DOT_SIZE; break;
            case up:  y[0] -= GameField.DOT_SIZE; break;
            default:  System.exit(1);
                System.out.println("What problem with direction?");
        }
        if(x[0]>GameField.SIZE) x[0] %= (GameField.SIZE + GameField.DOT_SIZE);
        else if(x[0]< 0) x[0] += (GameField.SIZE + GameField.DOT_SIZE);
        if(y[0]>GameField.SIZE) y[0] %= (GameField.SIZE + GameField.DOT_SIZE);
        else if(y[0]< 0) y[0] += (GameField.SIZE + GameField.DOT_SIZE);
//        x[0] = Math.abs(x[0]);
//        y[0] = Math.abs(y[0]);

    }

    public void draw(Graphics g, Image head, Image dot) {
        for (int i = 0; i < dots; i++) {
            if(i == 0){
                g.drawImage(head, x[i], y[i], field);
            }else {
                g.drawImage(dot, x[i], y[i], field);
            }
        }
    }

    public boolean checkFieldCollisions() {
//        if (x[0] > GameField.SIZE) {
//            return true;
//        }else if (x[0] < 0) {
//            return true;
//        }else if (y[0] > GameField.SIZE) {
//            return true;
//        }else if (y[0] < 0) {
//            return true;
//        }else
            return false;
    }

    public boolean checkSelfCollision() {
        if(dots < 5) {
            return false;
        }
        for (int i = dots - 1; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean checkApple(int appleX, int appleY) {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            return true;
        }
        return false;
    }

    public int getDelay() {
        return 100 + (int) (450 / (1 +  Math.pow(2*Math.E, 0.25*dots)));
}

    public void idle(){
        if (idle_move < 19) {
            idle_move ++;
            if (idle_right) {
                setSide(Sides.right);
            } else setSide(Sides.left);
        }else {
            setSide(Sides.down);
            idle_move = 0;
            idle_right = !idle_right;
        }
    }

    public int getNextY() {
        return (y[0] + GameField.DOT_SIZE) % GameField.SIZE;
    }

    public boolean full() {
        return dots == x.length;
    }
}
