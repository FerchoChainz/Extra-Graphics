package graficas;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.Thread.sleep;

public class Animation2d extends JFrame implements Runnable {

    private int times;
    private float scalation;
    private BufferedImage bufferImage;
    private Image buffer;
    private int bodyX, bodyY;

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

    public Animation2d() {
        this.g = new Figures();
        setTitle("A New Hope & Empire Strikes Back");
        setSize(700, 650);
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
        this.sceneAnimation1();
    }

    private void backgroudScene1() {
        Mountains();
        Sky();
        antenna();

        int min = 0;
        int max1 = 400;
        int max = 700;
        for (int i = 0; i < 50; i++) {
            putPixel((int) (Math.random() * (max - min + 1)) + min, (int) (Math.random() * (max1 - min + 1)) + min, Color.white);
        }

        int centerX = 430;
        int centerY = 305;
        int radius = 25;
        int numPoints = 360;
        int[][] circlePoints = new int[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            circlePoints[i] = new int[]{x, y};
        }
        fillFigure(circlePoints, new Color(255, 255, 255));

        centerX = 522;
        centerY = 350;
        radius = 25;
        numPoints = 360;

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            circlePoints[i] = new int[]{x, y};
        }
        fillFigure(circlePoints, new Color(223, 8, 8));

        int[][] squarePoints2 = {
            {0, 420},//izq arriba
            {700, 420},//der arriba
            {700, 650},//der abajo
            {0, 650} //izq abajo
        };
        this.fillFigure(squarePoints2, new Color(0, 0, 0));

        //sun
        this.sunScene1();

        centerX = 65;
        centerY = 420;
        radius = 55;
        numPoints = 360;

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            circlePoints[i] = new int[]{x, y};
        }
        fillFigure(circlePoints, new Color(0, 0, 0));

        this.locations.clear();
        this.locations.add(new Location(115, 400));
        this.locations.add(new Location(130, 400));
        this.locations.add(new Location(130, 440));
        this.locations.add(new Location(115, 440));
        int[][] square = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square, new Color(0, 0, 0));

    }

    public void antenna() {
        ArrayList<Location> location = new ArrayList<>();
        location = g.triangle(new Location(620, 420), new Location(630, 100),
                new Location(640, 420));
        this.pointsLocations(location, new Color(81, 60, 44));

        location = g.triangle(new Location(580, 420), new Location(590, 390),
                new Location(600, 420));
        this.pointsLocations(location, new Color(81, 60, 44));

    }

    public void Sky() {
        int[][] squarePoints1 = {
            {0, 0},//izq arriba
            {700, 0},//der arriba
            {700, 50},//der abajo
            {0, 50} //izq abajo
        };
        this.fillFigure(squarePoints1, new Color(226, 40, 59));

        int[][] squarePoints3 = {
            {0, 50},//izq arriba
            {700, 50},//der arriba
            {700, 100},//der abajo
            {0, 100} //izq abajo
        };
        this.fillFigure(squarePoints3, new Color(228, 53, 52));

        int[][] squarePoints4 = {
            {0, 100},//izq arriba
            {700, 100},//der arriba
            {700, 150},//der abajo
            {0, 150} //izq abajo
        };
        this.fillFigure(squarePoints4, new Color(231, 77, 46));

        int[][] squarePoints5 = {
            {0, 150},//izq arriba
            {700, 150},//der arriba
            {700, 200},//der abajo
            {0, 200} //izq abajo
        };
        this.fillFigure(squarePoints5, new Color(229, 99, 49));

        int[][] squarePoints6 = {
            {0, 200},//izq arriba
            {700, 200},//der arriba
            {700, 250},//der abajo
            {0, 250} //izq abajo
        };
        this.fillFigure(squarePoints6, new Color(229, 113, 56));

        int[][] squarePoints7 = {
            {0, 250},//izq arriba
            {700, 250},//der arriba
            {700, 300},//der abajo
            {0, 300} //izq abajo
        };
        this.fillFigure(squarePoints7, new Color(221, 133, 56));

        int[][] squarePoints8 = {
            {0, 300},//izq arriba
            {700, 300},//der arriba
            {700, 350},//der abajo
            {0, 350} //izq abajo
        };
        this.fillFigure(squarePoints8, new Color(221, 150, 58));

        int[][] squarePoints9 = {
            {0, 350},//izq arriba
            {700, 350},//der arriba
            {700, 420},//der abajo
            {0, 420} //izq abajo
        };
        this.fillFigure(squarePoints9, new Color(222, 192, 64));

    }

    public void Mountains() {
        //mountains
        ArrayList<Location> location = new ArrayList<>();

        location = g.triangle(new Location(0, 420), new Location(190, 100),
                new Location(370, 420));
        this.pointsLocations(location, new Color(81, 60, 44));
        location = g.triangle(new Location(371, 420), new Location(540, 100),
                new Location(700, 420));
        this.pointsLocations(location, new Color(81, 60, 44));

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

    private void putPixel(int x, int y, Color color) {
        bufferImage.setRGB(0, 0, color.getRGB());
        graPixel.drawImage(bufferImage, x, y, this);
    }

    private void sunScene1() {
        int centerX = 608;
        int centerY = 90;
        int radius = 50;
        int numPoints = 360;
        int[][] circlePoints = new int[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            circlePoints[i] = new int[]{x, y};
        }
        fillFigure(circlePoints, new Color(149, 150, 154));
        
        int newCenterX = centerX; 
        int newCenterY = centerY; 
        int newRadius = 15;
        int[][] newCirclePoints = new int[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            int x = (int) (newCenterX + newRadius * Math.cos(angle));
            int y = (int) (newCenterY + newRadius * Math.sin(angle));
            newCirclePoints[i] = new int[]{x, y};
        }
        fillFigure(newCirclePoints, new Color(242, 243, 243)); 

    }

    private void TieFighterBody() {
        this.locations.clear();
        this.locations.add(new Location(540, 410));
        this.locations.add(new Location(560, 410));
        this.locations.add(new Location(560, 450));
        this.locations.add(new Location(540, 450));
        this.locations = transformations.translation(0, bodyY, locations);
        int[][] square3 = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square3, new Color(148, 149, 154));

        this.locations.clear();
        this.locations.add(new Location(160, 410));
        this.locations.add(new Location(180, 410));
        this.locations.add(new Location(180, 450));
        this.locations.add(new Location(160, 450));
        this.locations = transformations.translation(0, bodyY, locations);
        int[][] square4 = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square4, new Color(148, 149, 154));

        this.locations.clear();
        this.locations.add(new Location(155, 420));
        this.locations.add(new Location(175, 420));
        this.locations.add(new Location(175, 440));
        this.locations.add(new Location(155, 440));
        this.locations = transformations.translation(0, bodyY, locations);
        int[][] square5 = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square5, new Color(148, 149, 154));

        this.locations.clear();
        this.locations.add(new Location(545, 420));
        this.locations.add(new Location(565, 420));
        this.locations.add(new Location(565, 440));
        this.locations.add(new Location(545, 440));
        this.locations = transformations.translation(0, bodyY, locations);
        int[][] square6 = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square6, new Color(148, 149, 154));
        window();
    }

    private void window() {
        int centerX = 355;
        int centerY = 433;
        int radius = 85;
        int numPoints = 360;
        int[][] circlePoints = new int[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            circlePoints[i] = new int[]{x, y};
        }

        // Aplica la traslación a los puntos del círculo
        for (int i = 0; i < numPoints; i++) {
            circlePoints[i][1] += bodyY;
        }

        fillFigure(circlePoints, new Color(148, 149, 154));

        centerX = 355;
        centerY = 433;
        radius = 35;
        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            circlePoints[i] = new int[]{x, y};
        }
        for (int i = 0; i < numPoints; i++) {
            circlePoints[i][1] += bodyY;
        }
        fillFigure(circlePoints, new Color(255, 255, 255));

        this.infitive(centerX, centerY);
    }

    private void TieFighterGuns() {
        this.locations.clear();
        Location location1 = new Location(550, 430);
        Location location2 = new Location(435, 390);
        Location location3 = new Location(435, 480);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.locations = transformations.translation(0, bodyY, locations);
        this.pointsLocations(locations, new Color(148, 149, 154));

        this.locations.clear();
        location1 = new Location(160, 430);
        location2 = new Location(275, 390);
        location3 = new Location(275, 480);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.locations = transformations.translation(0, bodyY, locations);
        this.pointsLocations(this.locations, new Color(148, 149, 154));

        //ship guns
        this.locations.clear();
        location1 = new Location(170, 280);
        location2 = new Location(160, 430);
        location3 = new Location(180, 430);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.locations = transformations.translation(0, bodyY, locations);
        this.pointsLocations(this.locations, new Color(148, 149, 154));

        this.locations.clear();
        location1 = new Location(170, 580);
        location2 = new Location(160, 430);
        location3 = new Location(180, 430);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.locations = transformations.translation(0, bodyY, locations);
        this.pointsLocations(this.locations, new Color(148, 149, 154));

        this.locations.clear();
        location1 = new Location(550, 280);
        location2 = new Location(540, 430);
        location3 = new Location(560, 430);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.locations = transformations.translation(0, bodyY, locations);
        this.pointsLocations(this.locations, new Color(148, 149, 154));

        this.locations.clear();
        location1 = new Location(550, 580);
        location2 = new Location(540, 430);
        location3 = new Location(560, 430);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.locations = transformations.translation(0, bodyY, locations);
        this.pointsLocations(this.locations, new Color(148, 149, 154));

    }

    private void sceneAnimation1() {
        bodyX += 10;
        System.out.println(this.endScene);
        if (this.times <= 0) {
            bodyY -= 10;
            translateY -= 10;
        }
        scalation += 1;
        if (bodyY > -530) {
            this.StormTrooper();
            this.TieFighterBody();
            this.TieFighterGuns();
//            this.MiniTieFighter();
        } else {
            this.endScene1();
        }
        if (this.endScene) {
            this.scene2();
        }

    }

    private void StormTrooper() {
        if (!this.endClouds) {
            translateX += 10;
            ArrayList<Location> circle1Points;
            this.locations.clear();
            this.locations = generateEllipsePoints(100, 420, radius, radius2);
            circle1Points = new Transformations().translation(translateX, 1, this.locations);
            pointsLocations(circle1Points, Color.white);

            if (radius <= 34 && radius2 <= 31) {
                radius += 3;
                radius2 += 3;
            }
            if (translateX >= 150) {
                translateX = 1;
                this.times -= 1;
            }
            if (this.times <= 0) {
                this.endClouds = true;
            }
        }
    }

    private void endScene1() {
        if (this.endSize) {
            if (counter < 150) {
                ArrayList<Location> newPoint = new ArrayList<Location>();
                newPoint.add(new Location(5, getHeight()));
                counter += 5;
                newPoint = transformations.Escalation(counter, 1, newPoint);
                ArrayList<Location> result = new ArrayList<Location>();
                result.add(new Location(5, 5));
                result.add(new Location(5, getHeight()));
                result.add(newPoint.get(0));
                result.add(new Location(newPoint.get(0).pointX, 0));
                System.out.println(this.counter);
                drawPolygon(result, Color.BLACK);
                this.pointsLocations(result, Color.BLACK);
            } else {
                this.endScene = true;
            }
        } else {
            this.sizes();
        }
    }
    //------------------------------------------------------------------------------------

    private void Hoth() {
        this.planet();
        this.ATAT();
        this.miniroket++;
        try {
            sleep(35);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void planet() {

        int[][] squarePoints = {
            {0, 0},//izq arriba
            {700, 0},//der arriba
            {700, 450},//der abajo
            {0, 450} //izq abajo
        };
        this.fillFigure(squarePoints, new Color(247, 252, 255));

        int centerX = 580;
        int centerY = 280;
        int radius = 80;
        int numPoints = 360;
        int[][] circlePoints = new int[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            circlePoints[i] = new int[]{x, y};
        }
        fillFigure(circlePoints, new Color(240, 247, 253));

        centerX = 50;
        centerY = 460;
        radius = 100;
        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            circlePoints[i] = new int[]{x, y};
        }

        fillFigure(circlePoints, new Color(231, 248, 255));

        this.locations.clear();
        Location location1 = new Location(580, 360);
        Location location2 = new Location(510, 440);
        Location location3 = new Location(700, 440);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.pointsLocations(this.locations, new Color(240, 247, 253));

        this.locations.clear();
        location1 = new Location(750, 360);
        location2 = new Location(560, 440);
        location3 = new Location(790, 440);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.pointsLocations(this.locations, new Color(240, 247, 253));

        this.locations.clear();
        location1 = new Location(0, 455);
        location2 = new Location(701, 440);
        location3 = new Location(701, 500);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.pointsLocations(this.locations, new Color(144, 218, 247));

        this.locations.clear();
        location1 = new Location(701, 455);
        location2 = new Location(0, 440);
        location3 = new Location(0, 500);
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.pointsLocations(this.locations, new Color(144, 218, 247));

        int[][] squarePoints2 = {
            {0, 450},//izq arriba
            {700, 450},//der arriba
            {700, 650},//der abajo
            {0, 650} //izq abajo
        };
        this.fillFigure(squarePoints2, new Color(144, 218, 247));
    }

    private void sizes() {
        if (size > 480) {
            size -= 10;
            System.out.println(size);
            setSize(getWidth(), size);
        } else {
            this.endSize = true;
        }
    }

    private void scene2() {
        this.scanLine();
        this.Hoth();
    }

    private void ATAT() {
        this.locations.clear();
        this.locations = g.triangle(new Location(580, 420), new Location(590, 390),
                new Location(600, 420));
        this.locations = transformations.CenterRotation(translateY, locations, new Location(514, 350));
        this.pointsLocations(this.locations, new Color(208, 33, 33));

        //primero back
        this.locations.clear();
        this.locations.add(new Location(110, 275));
        this.locations.add(new Location(185, 275));
        this.locations.add(new Location(185, 340));
        this.locations.add(new Location(110, 340));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] back1 = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(back1, new Color(149, 150, 155));

        Location location1 = new Location(100, 290);//punta
        Location location2 = new Location(110, 275);//abajo izq
        Location location3 = new Location(110, 340);//abajo der
        this.locations.clear();
        this.locations.add(location1);
        this.locations.add(location2);
        this.locations.add(location3);
        this.locations = g.triangle(new Location(locations.get(0).pointX, locations.get(0).pointY),
                new Location(locations.get(1).pointX, locations.get(1).pointY),
                new Location(locations.get(2).pointX, locations.get(2).pointY));
        this.locations = transformations.translation(miniroket, 0, locations);
        this.pointsLocations(this.locations, new Color(149, 150, 155));

        this.locations.clear();
        this.locations.add(new Location(110, 235));
        this.locations.add(new Location(310, 235));
        this.locations.add(new Location(310, 340));
        this.locations.add(new Location(110, 340));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] backCenter = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(backCenter, new Color(149, 150, 155));

        this.locations.clear();
        this.locations.add(new Location(145, 335));
        this.locations.add(new Location(250, 335));
        this.locations.add(new Location(250, 370));
        this.locations.add(new Location(145, 370));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] backEngine = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(backEngine, new Color(149, 150, 155));

        this.locations.clear();
        this.locations.add(new Location(270, 315));
        this.locations.add(new Location(375, 315));
        this.locations.add(new Location(375, 370));
        this.locations.add(new Location(270, 370));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] backEngineRight = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(backEngineRight, new Color(149, 150, 155));

        //leftFrontLegTop
        this.locations.clear();
        this.locations.add(new Location(120, 370));
        this.locations.add(new Location(165, 375));
        this.locations.add(new Location(154, 490));
        this.locations.add(new Location(105, 485));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] leftFrontLeg = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(leftFrontLeg, new Color(170, 170, 178));

        //leftBackLegTop
        this.locations.clear();
        this.locations.add(new Location(175, 370));
        this.locations.add(new Location(220, 370));
        this.locations.add(new Location(220, 480));
        this.locations.add(new Location(175, 480));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] leftBackLeg = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(leftBackLeg, new Color(170, 170, 178));

        //------------------------------------------
        //rightFrontLegTop
        this.locations.clear();
        this.locations.add(new Location(339, 370));
        this.locations.add(new Location(385, 375));
        this.locations.add(new Location(374, 490));
        this.locations.add(new Location(305, 485));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] rightFrontLeg = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(rightFrontLeg, new Color(170, 170, 178));

        this.locations.clear();
        this.locations.add(new Location(290, 370));
        this.locations.add(new Location(335, 370));
        this.locations.add(new Location(335, 480));
        this.locations.add(new Location(290, 480));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] rightBackLeg = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(rightBackLeg, new Color(170, 170, 178));

        //LeftEngine
        radius = 30;
        radius2 = 30;
        translateX += 0;
        ArrayList<Location> circle1Points;
        this.locations.clear();
        this.locations = generateEllipsePoints(145, 355, radius, radius2);
        circle1Points = new Transformations().translation(translateX, 0, this.locations);
        pointsLocations(circle1Points, new Color(147, 148, 153));

        radius = 25;
        radius2 = 25;
        translateX += 1; //mover aqui a 1 
        this.locations.clear();
        this.locations = generateEllipsePoints(145, 355, radius, radius2);
        circle1Points = new Transformations().translation(translateX, 0, this.locations);
        pointsLocations(circle1Points, new Color(205, 205, 213));

        //RightEngine
        radius = 30;
        radius2 = 30;
        translateX += 0;
        this.locations.clear();
        this.locations = generateEllipsePoints(365, 355, radius, radius2);
        circle1Points = new Transformations().translation(translateX, 0, this.locations);
        pointsLocations(circle1Points, new Color(147, 148, 153));

        radius = 25;
        radius2 = 25;
        translateX += 0;
        this.locations.clear();
        this.locations = generateEllipsePoints(365, 355, radius, radius2);
        circle1Points = new Transformations().translation(translateX, 0, this.locations);
        pointsLocations(circle1Points, new Color(205, 205, 213));

        //primero
        this.locations.clear();
        this.locations.add(new Location(110, 230));
        this.locations.add(new Location(200, 230));
        this.locations.add(new Location(200, 320));
        this.locations.add(new Location(110, 320));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] square = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square, new Color(169, 169, 177));

        //central 
        this.locations.clear();
        this.locations.add(new Location(205, 225));
        this.locations.add(new Location(300, 225));
        this.locations.add(new Location(300, 340));
        this.locations.add(new Location(205, 340));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] square2 = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square2, new Color(169, 169, 177));

        //tercero
        this.locations.clear();
        this.locations.add(new Location(305, 230));
        this.locations.add(new Location(395, 230));
        this.locations.add(new Location(395, 320));
        this.locations.add(new Location(305, 320));
        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] square3 = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square3, new Color(169, 169, 177));

        //cuello
        this.locations.clear();
        this.locations.add(new Location(395, 260));
        this.locations.add(new Location(415, 260));
        this.locations.add(new Location(415, 290));
        this.locations.add(new Location(395, 290));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] square4 = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square4, new Color(148, 149, 154));

        //cara
        this.locations.clear();
        this.locations.add(new Location(415, 257));
        this.locations.add(new Location(500, 257));
        this.locations.add(new Location(500, 300));
        this.locations.add(new Location(415, 300));

        this.locations = transformations.translation(miniroket, 1, locations);
        int[][] square5 = {
            {this.locations.get(0).pointX, this.locations.get(0).pointY},//izq arriba
            {this.locations.get(1).pointX, this.locations.get(1).pointY},
            {this.locations.get(2).pointX, this.locations.get(2).pointY},
            {this.locations.get(3).pointX, this.locations.get(3).pointY}
        };
        fillFigure(square5, new Color(169, 169, 177));
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
            this.putPixel(x + centerX, y + centerY + this.bodyY + 50, Color.black);
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

    public static void main(String[] args) {
        Animation2d animation2d = new Animation2d();
        Thread thread = new Thread(animation2d);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            if (this.miniroket > 200) {
                break;
            }
        }
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

}
