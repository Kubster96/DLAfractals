import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Random;

public class Board extends JComponent implements ComponentListener {
    private Point[][] points;
    private int size = 5;

    Point runner;
    boolean running = false;
    int length;
    int height;
    int centerX;
    int centerY;


    public Board(int length, int height) {
        addComponentListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    // single iteration
    public void iteration() {
        points[runner.x][runner.y] = new Point(0, runner.x, runner.y, centerX, centerY);
        runner.move();
        checkObstacle();
        this.repaint();

        points[runner.x][runner.y] = new Point(0, runner.x, runner.y, centerX, centerY);
        runner.useGravity();
        checkObstacle();

        this.repaint();
    }

    public void checkObstacle(){
        if (outOfBounds(runner)) {
            newRunner();
        }else if(haveNeighbours(runner)){
            points[runner.x][runner.y] = new Point(1, runner.x, runner.y, centerX, centerY);
            newRunner();
        } else {
            points[runner.x][runner.y] = runner;
        }
    }

    public void reinitialize() {
        for (Point[] point : points)
            for (Point value : point) {
                value.setState(0);
            }
        int length = (this.getWidth() / size) + 1;
        int height = (this.getHeight() / size) + 1;
        initialize(length, height);
        this.repaint();
    }

    private void initialize(int length, int height) {
        this.length = length;
        this.height = height;
        this.centerX = length/2;
        this.centerY = height/2;

        Random random = new Random();

        points = new Point[length][height];

        for (int x = 0; x < length; ++x)
            for (int y = 0; y < height; ++y)
                points[x][y] = new Point(0, x, y, centerX, centerY);

        points[length/2][height/2] = new Point(1, length/2, height/2, centerX, centerY);

        int a;
        int b;

        do {
            a = random.nextInt(length);
            b = random.nextInt(height);
        } while(points[a][b].getType()!=0);

        running = true;
        runner = new Point(2, a, b, centerX, centerY);
        points[a][b] = runner;
    }

    void newRunner(){
        Random random = new Random();

        int a;
        int b;

        do {
            a = random.nextInt(length);
            b = random.nextInt(height);
        } while(points[a][b].getType()!=0);

        runner = new Point(2, a, b, centerX, centerY);
        points[a][b] = runner;

    }

    boolean outOfBounds(Point point){
        return point.x < 2 || point.y < 2 || point.x > length-2 || point.y > height-2;
    }

    boolean haveNeighbours(Point point){
        return (points[point.x - 1][point.y].getType() == 1
                || points[point.x + 1][point.y].getType() == 1
                || points[point.x][point.y + 1].getType() == 1
                || points[point.x][point.y - 1].getType() == 1
                || points[point.x - 1][point.y - 1].getType() == 1
                || points[point.x - 1][point.y + 1].getType() == 1
                || points[point.x + 1][point.y + 1].getType() == 1
                || points[point.x + 1][point.y - 1].getType() == 1);


    }

    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                switch (points[x][y].getType()) {
                    case 0:
                        g.setColor(new Color(0xffffff));
                        break;
                    case 1:
                        g.setColor(new Color(0x0100FF));
                        break;
                    case 2:
                        g.setColor(new Color(0xFF0003));
                        break;
                }
                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));

            }
        }
    }

    public void componentResized(ComponentEvent e) {
        int length = (this.getWidth() / size) + 1;
        int height = (this.getHeight() / size) + 1;
        initialize(length, height);
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }
}