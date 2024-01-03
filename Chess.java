package graficas;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Chess extends JFrame implements Runnable {

    private int times;
    private float scalation;
    private BufferedImage bufferImage;
    private Image buffer;

    private int bodyX, bodyY;
    int centerX = 355;
    int centerY = 433;
//        int radius = 35;
    int numPoints = 360;
    int[][] circlePoints = new int[numPoints][2];

    private Image fondo;
    private Graphics graPixel;
    private ArrayList<Location> locations;
    private Figures g;
    private Transformations transformations;
    private int translateX; // Traslación en X
    private int translateY; // Traslación en Y
    private float counter;
    private int size;
    private boolean endSize;
    private int radius;
    private int radius2;
    private boolean endClouds;
    private boolean endScene;
    private int miniroket;

    public Chess() {
        this.g = new Figures();
        setTitle("lll");
        setSize(650, 650);
        setLayout(null);
        setVisible(true);
        this.transformations = new Transformations();
        this.locations = new ArrayList<>();
        setLocationRelativeTo(null);
        counter = 1;
        size = 600;
        this.radius = 4;
        this.radius2 = 1;
        this.endSize = false;
        this.endClouds = false;
        endScene = false;
        this.bodyY = -10;
        this.times = 1;
        this.miniroket = 1;
        bufferImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void paint(Graphics graphics) {
        if (fondo == null) {
            fondo = createImage(getWidth(), getHeight());
            Graphics gfondo = fondo.getGraphics();
            gfondo.setClip(0, 0, getWidth(), getHeight());
        }
        update(graphics);
    }

    private void scene1() {
        if (!this.endScene) {
            this.backgroudScene1();
        }
//        this.sceneAnimation1();
    }

    private void backgroudScene1() {

        ArrayList<Location> location = new ArrayList<>();

        int[][] chessboard = {
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0}
        };
        drawChessboard(chessboard);
//        A2();
//        B2();
    }

    private void drawChessboard(int[][] chessboard) {
        int squareSize = 80;
        for (int i = 1; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                int x = j * squareSize;
                int y = i * squareSize;
                Color color = (chessboard[i][j] == 1) ? new Color(204, 153, 102) : new Color(255, 204, 153);
                int[][] square = {
                    {x, y},
                    {x + squareSize, y},
                    {x + squareSize, y + squareSize},
                    {x, y + squareSize}
                };
                this.fillFigure(square, color);
            }
        }
    }
    
    

    @Override
    public void update(Graphics graphics) {
        graphics.setClip(0, 0, getWidth(), getHeight());
        buffer = createImage(getWidth(), getHeight());
        graPixel = buffer.getGraphics();
        graPixel.setClip(0, 0, getWidth(), getHeight());
        this.scene1();
        graphics.drawImage(buffer, 0, 0, this);
    }

    private void pointsLocations(ArrayList<Location> locations, Color color) {
        int[][] trianglePoints = new int[locations.size()][2];
        for (int i = 0; i < locations.size(); i++) {
            trianglePoints[i][0] = locations.get(i).pointX;
            trianglePoints[i][1] = locations.get(i).pointY;
        }
        fillFigure(trianglePoints, color);
    }

    private void fillFigure(int[][] points, Color color) {
        int n = points.length;
        int startX = Integer.MAX_VALUE;
        int startY = Integer.MAX_VALUE;
        int endX = Integer.MIN_VALUE;
        int endY = Integer.MIN_VALUE;

        // Find the bounding box of the rotated figure
        for (int i = 0; i < n - 1; i++) {
            int x = points[i][0];
            int y = points[i][1];
            startX = Math.min(startX, x);
            startY = Math.min(startY, y);
            endX = Math.max(endX, x);
            endY = Math.max(endY, y);
        }

        int direction = 0; // 0: right, 1: down, 2: left, 3: up
        int x = startX;
        int y = startY;

        while (startX <= endX && startY <= endY) {
            // Paint pixels in the current direction
            while (x >= startX && x <= endX && y >= startY && y <= endY) {
                if (isInsidePolygon(x, y, points)) {
                    putPixel(x, y, color);
                }
                // Move to the next pixel in the current direction
                switch (direction) {
                    case 0:
                        x++;
                        break;
                    case 1:
                        y++;
                        break;
                    case 2:
                        x--;
                        break;
                    case 3:
                        y--;
                        break;
                }
            }

            switch (direction) {
                case 0:
                    startY++;
                    x--;
                    y++;
                    break;
                case 1:
                    endX--;
                    x--;
                    y--;
                    break;
                case 2:
                    endY--;
                    x++;
                    y--;
                    break;
                case 3:
                    startX++;
                    x++;
                    y++;
                    break;
            }

            // Change direction (right -> down -> left -> up)
            direction = (direction + 1) % 4;
        }
    }

    private boolean isInsidePolygon(int x, int y, int[][] points) {
        int n = points.length;
        boolean isInside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            int xi = points[i][0];
            int yi = points[i][1];
            int xj = points[j][0];
            int yj = points[j][1];

            if (((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
                isInside = !isInside;
            }
        }
        return isInside;
    }

    private void putPixel(int x, int y, Color color) {
        bufferImage.setRGB(0, 0, color.getRGB());
        graPixel.drawImage(bufferImage, x, y, this);
    }

    void drawPolygon(ArrayList<Location> points, Color fillColor) {
        ArrayList<Location> newPoints = new ArrayList<Location>();
        for (int i = 0; i < points.size(); i++) {
            ArrayList<Location> line = (i == points.size() - 1)
                    ? g.bresenham(points.get(i), points.get(0))
                    : g.bresenham(points.get(i), points.get(i + 1));
            newPoints.addAll(line);
        }

        for (int i = 0; i < newPoints.size(); i++) {
            putPixel(newPoints.get(i).pointX, newPoints.get(i).pointY, fillColor);
        }
    }

    public ArrayList<Location> generateEllipsePoints(int centerX, int centerY, int radiusX, int radiusY) {
        ArrayList<Location> ellipsePoints = new ArrayList<>();

        for (int i = 0; i < 360; i++) {
            double angle = 2 * Math.PI * i / 360;
            int x = (int) (centerX + radiusX * Math.cos(angle));
            int y = (int) (centerY + radiusY * Math.sin(angle));
            ellipsePoints.add(new Location(x, y));
        }

        return ellipsePoints;
    }

    public static void main(String[] args) {
        Chess animation2d = new Chess();
        Thread thread = new Thread(animation2d);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
//            if(this.miniroket>200){
//                break;
//            }
        }
    }

    public void infitive(int centerX, int centerY) {
        ArrayList<Location> locations = new ArrayList<>();
        int x, y;
        for (int t = 0; t <= 360; t++) {
            double radians = Math.PI / 180 * t;
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            x = (int) ((30 * sin) / (1 + (cos * cos)));
            y = (int) ((30 * sin * cos) / (1 + (cos * cos)));
            //System.out.println((x+450)+" , "+(y+450));
            this.putPixel(x + centerX, y + centerY + this.bodyY + 50, Color.blue);
            locations.add(new Location(x + centerX, y + centerY + this.bodyY + 50));
        }
        this.lines(locations);
    }

    private void lines(ArrayList<Location> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            ArrayList<Location> locations = g.bresenham(points.get(i), points.get(i + 1));
            drawPoints(locations, Color.BLUE);
        }
    }

    public void drawPoints(ArrayList<Location> locations, Color color) {
        for (Location point : locations) {
            putPixel(point.pointX, point.pointY, color);
        }
    }

    private void scanLine() {
        for (int y = 0; y < getHeight() - 1; y++) {
            for (int x = 0; x <= getWidth() - 1; x++) {
                putPixel(x, y, Color.BLACK);
            }
        }
    }
}
