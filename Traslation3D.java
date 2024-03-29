package graficas;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Traslation3D extends JFrame implements Runnable {
    private Color color, disponible;
    private float incX,incY,incZ;
    private BufferedImage bufferImage;
    private Image buffer;
    private Graphics graphics;
    private Image fondo;
    private ArrayList<Location> pointsXY = new ArrayList<Location>();
    private ArrayList<Location3D> pointsXYZ = new ArrayList<Location3D>();
    private Location3D vector;
    private Figures g;
    private Graphics graPixel;
    private Transformations transformations;

    public Traslation3D() {
        color = Color.red;
        setTitle("Traslacion 3D");
        setSize(450, 450);
        setLayout(null);
        setVisible(true);

        this.g = new Figures();
        //first square
        pointsXYZ.add(new Location3D(50, 150, 150));//a 0
        pointsXYZ.add(new Location3D(150, 150, 150));//b 1
        pointsXYZ.add(new Location3D(50, 250, 150));//c 2
        pointsXYZ.add(new Location3D(150, 250, 150));//d 3
        //second square
        pointsXYZ.add(new Location3D(50, 150, 250));//e 4
        pointsXYZ.add(new Location3D(150, 150, 250));//f 5
        pointsXYZ.add(new Location3D(50, 250, 250));//g 6
        pointsXYZ.add(new Location3D(150, 250, 250));//h 7

        transformations = new Transformations();
        vector = new Location3D(8, 7, 20);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        bufferImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) bufferImage.createGraphics();
    }

    private void putPixel(int x, int y) {
        bufferImage.setRGB(0, 0, color.getRGB());
        graPixel.drawImage(bufferImage, x, y, this);
    }

    private void putPixel(int x, int y, Color color) {
        bufferImage.setRGB(0, 0, color.getRGB());
        graPixel.drawImage(bufferImage, x, y, this);
    }

    private void drawCube() {
        pointsXYZ = transformations.translation3D(incX,0,0,pointsXYZ);
        for (int i = 0; i < pointsXYZ.size(); i++) {
            float u = (float) (pointsXYZ.get(i).pointZ) / vector.pointZ;
            float x = pointsXYZ.get(i).pointX + (vector.pointX * u);
            float y = pointsXYZ.get(i).pointY + (vector.pointY * u);

            pointsXY.add(new Location((int) x, (int) y));
        }
        for (Location point : pointsXY) {
            putPixel(point.pointX, point.pointY);
        }


        int square[][] = {
                {pointsXY.get(0).pointX, pointsXY.get(0).pointY},
                {pointsXY.get(1).pointX, pointsXY.get(1).pointY},
                {pointsXY.get(5).pointX, pointsXY.get(5).pointY},
                {pointsXY.get(4).pointX, pointsXY.get(4).pointY}
        };
        fillFigure(square, Color.BLUE);
        int square2[][] = {
                {pointsXY.get(0).pointX, pointsXY.get(0).pointY},
                {pointsXY.get(4).pointX, pointsXY.get(4).pointY},
                {pointsXY.get(6).pointX, pointsXY.get(6).pointY},
                {pointsXY.get(2).pointX, pointsXY.get(2).pointY}

        };
        fillFigure(square2, Color.BLACK);
        int square3[][] = {
                {pointsXY.get(4).pointX, pointsXY.get(4).pointY},
                {pointsXY.get(5).pointX, pointsXY.get(5).pointY},
                {pointsXY.get(7).pointX, pointsXY.get(7).pointY},
                {pointsXY.get(6).pointX, pointsXY.get(6).pointY}

        };
        fillFigure(square3, Color.GRAY);
        pointsXY.clear();
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

            // Update the bounding box based on the current direction
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


    @Override
    public void paint(Graphics graphics) {
        if (fondo == null) {
            fondo = createImage(getWidth(), getHeight());
            Graphics gfondo = fondo.getGraphics();
            gfondo.setClip(0, 0, getWidth(), getHeight());
        }
        update(graphics);
    }

    public static void main(String[] args) {
        Traslation3D traslation3D = new Traslation3D();
        Thread thread = new Thread(traslation3D);
        thread.start();
    }

    @Override
    public void update(Graphics graphics) {
        graphics.setClip(0, 0, getWidth(), getHeight());
        buffer = createImage(getWidth(), getHeight());
        graPixel = buffer.getGraphics();
        graPixel.setClip(0, 0, getWidth(), getHeight());
        this.drawCube();
        System.out.println("x: "+incX+" y:"+incY);
        graphics.drawImage(buffer, 0, 0, this);
    }

    @Override
    public void run() {
        while (incX<50) {
            try {
                incX+=0.1;
                incY-=0.1;
                incZ=1;
                repaint();
                sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch (Exception e){
                System.out.println("algo salio mal");
            }
        }
    }
}