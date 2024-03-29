package graficas;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Orthogonal extends JFrame {
    private Color color;
    private BufferedImage buffer;
    private Graphics graphics;
    private ArrayList<Location> pointsXY = new ArrayList<Location>();
    private ArrayList<Location3D> pointsXYZ = new ArrayList<Location3D>();
    private Location3D vector;
    private Figures g;
    public Orthogonal(){
        color = Color.red;
        setTitle("Proyección Ortogonal");
        setSize(450, 450);
        setLayout(null);
        setVisible(true);

        this.g = new Figures();
        //first square
        pointsXYZ.add(new Location3D(50, 150, 150));
        pointsXYZ.add(new Location3D(150, 150, 150));
        pointsXYZ.add(new Location3D(50, 250, 150));
        pointsXYZ.add(new Location3D(150, 250, 150));
        //second square
        pointsXYZ.add(new Location3D(50, 150, 250));
        pointsXYZ.add(new Location3D(150, 150, 250));
        pointsXYZ.add(new Location3D(50, 250, 250));
        pointsXYZ.add(new Location3D(150, 250, 250));

        vector = new Location3D(8,7,20);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) buffer.createGraphics();
    }
    private void putPixel(int x, int y){
        buffer.setRGB(0, 0, color.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    private void drawCube(){
        for (int i = 0; i < pointsXYZ.size(); i++){
            float u = 0;
            float y = pointsXYZ.get(i).pointY + (vector.pointY * u);
            float z = pointsXYZ.get(i).pointZ + (vector.pointZ * u);

            pointsXY.add(new Location((int) y, (int) z));
        }
        for (Location point: pointsXY){
            putPixel(point.pointX, point.pointY);
        }

        ArrayList<Location> pointsAB = g.bresenham(pointsXY.get(0), pointsXY.get(1));
        ArrayList<Location> pointsAC = g.bresenham(pointsXY.get(0), pointsXY.get(2));
        ArrayList<Location> pointsAE = g.bresenham(pointsXY.get(0), pointsXY.get(4));

        ArrayList<Location> pointsBD = g.bresenham(pointsXY.get(1), pointsXY.get(3));
        ArrayList<Location> pointsBF = g.bresenham(pointsXY.get(1), pointsXY.get(5));

        ArrayList<Location> pointsEG = g.bresenham(pointsXY.get(4), pointsXY.get(6));
        ArrayList<Location> pointsEF = g.bresenham(pointsXY.get(4), pointsXY.get(5));

        ArrayList<Location> pointsCG = g.bresenham(pointsXY.get(2), pointsXY.get(6));
        ArrayList<Location> pointsCD = g.bresenham(pointsXY.get(2), pointsXY.get(3));

        ArrayList<Location> pointsHG = g.bresenham(pointsXY.get(7), pointsXY.get(6));
        ArrayList<Location> pointsHF = g.bresenham(pointsXY.get(7), pointsXY.get(5));
        ArrayList<Location> pointsHD = g.bresenham(pointsXY.get(7), pointsXY.get(3));

        ArrayList<Location> points = pointsAB;
        points.addAll(pointsAC);
        points.addAll(pointsAC);
        points.addAll(pointsAE);
        points.addAll(pointsBD);
        points.addAll(pointsBF);
        points.addAll(pointsEG);
        points.addAll(pointsEF);
        points.addAll(pointsCG);
        points.addAll(pointsCD);
        points.addAll(pointsHG);
        points.addAll(pointsHF);
        points.addAll(pointsHD);

        for (Location point: points){
            putPixel(point.pointX, point.pointY);
        }

    }

    @Override
    public void paint(Graphics graphics){
        super.paint(graphics);
        drawCube();
    }

    public static void main(String[] args) {
        new Orthogonal();
    }
}
