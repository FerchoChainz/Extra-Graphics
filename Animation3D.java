package graficas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Thread.sleep;
import javax.swing.JFrame;

public class Animation3D extends JFrame implements Runnable {

    private ArrayList<Location3D> location3D, location3DStickLeft, location3DStickRight;
    private BufferedImage buffer, animacion, temp, temp2;
    private Thread hilo;
    private Color color, disponible;
    private ArrayList<Location> pointsXY = new ArrayList<Location>();
    int x, y, z;
    private float incX, incY, incZ;
    Double a = -25.0, b = 15.0;
    boolean r1, r2, r3, r4, r5, r6, r7, r8, r9, r10,
            r11, r12, r13, r14, r15, r16, r17, r18, r19, r20,
            r21, r22, r23;

    boolean l1, l2, l3, l4, l5, l6, l7, l8, l9, l10,
            l11, l12, l13;

    boolean e1, e2, e3, e4, e5, e6, e7, e8, e9, e10,
            e11, e12, e13, e14;

    Double[][] coordenadas = {{a, a, a, 1.0}, //A0
    {a, a, b, 1.0}, //B1
    {a, b, a, 1.0}, //C2
    {a, b, b, 1.0}, //D3
    {b, a, a, 1.0}, //E4
    {b, a, b, 1.0}, //F5
    {b, b, a, 1.0}, //G6
    {b, b, b, 1.0}, //H7
};
    private int contador, Rcontador, Lcontador;
    int espacios[][] = {{0, 0, 0, 0}, {0, 0, 0}, {0, 0}, {0}};
    Double ae = -10.0, be = 5.0;
    Double[][] coordenadasEsc = {{ae, ae, ae, 1.0}, //A0
    {ae, ae, be, 1.0}, //B1
    {ae, be, ae, 1.0}, //C2
    {ae, be, be, 1.0}, //D3
    {be, ae, ae, 1.0}, //E4
    {be, ae, be, 1.0}, //F5
    {be, be, ae, 1.0}, //G6
    {be, be, be, 1.0}, //H7
};

    Double at = -10.0, bt = 10.0;
    Double[][] coordenadasT = {{at, at, at, 1.0}, //A0
    {at, at, bt, 1.0}, //B1
    {at, bt, at, 1.0}, //C2
    {at, bt, bt, 1.0}, //D3
    {bt, at, at, 1.0}, //E4
    {bt, at, bt, 1.0}, //F5
    {bt, bt, at, 1.0}, //G6
    {bt, bt, bt, 1.0}, //H7
};

    /*         Estructura cubos     */
    Double a1 = -30.0, b1 = 30.0;
    Double[][] coorCubos = {
        {a1, a1, a1, 1.0}, //A0
        {a1, a1, b1, 1.0}, //B1
        {a1, b1, a1, 1.0}, //C2
        {a1, b1, b1, 1.0}, //D3
        {b1, a1, a1, 1.0}, //E4
        {b1, a1, b1, 1.0}, //F5
        {b1, b1, a1, 1.0}, //G6
        {b1, b1, b1, 1.0}, //H7
    };
    boolean colorido;

    /*         End Estructura cubos     */
    Double[][] d = {{200.0, 300.0, 450.0},
    {200.0, 200.0, -500.0},
    {-200.0, -200.0, 500.0}
    };
    Double Xa = 250.0, Ya = 275.0;
    int indexZ;
    private boolean animarCubo = false;
    Double[][] auxCoordenadas = new Double[8][4];
    Double[][] centros, centrosP, auxCentros = new Double[6][4];

    Double[][] auxCoordenadasEsc = new Double[8][4];
    Double[][] centrosEsc, centrosPEsc, auxCentrosEsc = new Double[6][4];

    Double[][] auxCoordenadasT = new Double[8][4];
    Double[][] centrosT, centrosPT, auxCentrosT = new Double[6][4];
    private Location3D vector;
    private Location3D vectorSticks;
    private Figures g;
    private boolean loading;
    private Transformations transformations;
    private ArrayList<Location3D> pointsXYZ = new ArrayList<Location3D>();
    private int time;
    private float cubertoIncX = 0, cubertoIncY = 0, cubertoIncZ = 0;
    private float stickIncX = 0, stickIncY = 0, stickIncZ = 0;
    private boolean finScena;

    public Animation3D() {
        finScena = false;
        colorido = false;
        this.cubertoIncX = 1;
        this.cubertoIncY = 1;
        this.cubertoIncZ = 1;

        this.stickIncX = 1;
        this.stickIncY = 1;
        this.stickIncZ = 1;
        setTitle("Pong Game");
        setSize(1350, 800);
        setLayout(null);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        animacion = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        disponible = new Color(0, 0, 0);

        location3D = new ArrayList<>();
        location3DStickLeft = new ArrayList<>();
        location3DStickRight = new ArrayList<>();
        centros = new Double[6][4];
        determinarCentros();
        auxCoordenadas = coordenadas.clone();
        auxCentros = centros.clone();
        r1 = true;
        r2 = r3 = r4 = r6 = r7 = r8 = r9 = r10 = r11 = r12 = false;
        l1 = true;
        e1 = true;
        centrosEsc = new Double[6][4];
        determinarCentrosEsc();
        auxCoordenadasEsc = coordenadasEsc.clone();
        auxCentrosEsc = centrosEsc.clone();
        this.time = 100;
        centrosT = new Double[6][4];
        determinarCentrosT();
        auxCoordenadasT = coordenadasT.clone();
        auxCentrosT = centrosT.clone();
        transformations = new Transformations();
        Rotacion(coorCubos, 22, 'y');
        //primera cara
        location3D.add(new Location3D(10, 10, 2));//a 0
        location3D.add(new Location3D(40, 10, 2));//b 1
        location3D.add(new Location3D(10, 40, 2));//c 2
        location3D.add(new Location3D(40, 40, 2));//d 3
        //second square
        location3D.add(new Location3D(10, 10, 10));//e 4
        location3D.add(new Location3D(40, 10, 10));//f 5
        location3D.add(new Location3D(10, 40, 10));//g 6
        location3D.add(new Location3D(40, 40, 10));//h 7

        location3DStickLeft.add(new Location3D(10, 10, 2));//a 0
        location3DStickLeft.add(new Location3D(40, 10, 2));//b 1
        location3DStickLeft.add(new Location3D(10, 40, 2));//c 2
        location3DStickLeft.add(new Location3D(40, 40, 2));//d 3
        //second square
        location3DStickLeft.add(new Location3D(10, 10, 10));//e 4
        location3DStickLeft.add(new Location3D(40, 10, 10));//f 5
        location3DStickLeft.add(new Location3D(10, 40, 10));//g 6
        location3DStickLeft.add(new Location3D(40, 40, 10));//h 7

        location3DStickRight.add(new Location3D(10, 10, 2));//a 0
        location3DStickRight.add(new Location3D(40, 10, 2));//b 1
        location3DStickRight.add(new Location3D(10, 40, 2));//c 2
        location3DStickRight.add(new Location3D(40, 40, 2));//d 3
        //second square
        location3DStickRight.add(new Location3D(10, 10, 10));//e 4
        location3DStickRight.add(new Location3D(40, 10, 10));//f 5
        location3DStickRight.add(new Location3D(10, 40, 10));//g 6
        location3DStickRight.add(new Location3D(40, 40, 10));//h 7
        hilo = new Thread(this);
        hilo.start();

//first square
        pointsXYZ.add(new Location3D(50, 150, 50));//a 0
        pointsXYZ.add(new Location3D(150, 150, 50));//b 1
        pointsXYZ.add(new Location3D(50, 250, 50));//c 2
        pointsXYZ.add(new Location3D(150, 250, 50));//d 3
        //second square
        pointsXYZ.add(new Location3D(50, 150, 150));//e 4
        pointsXYZ.add(new Location3D(150, 150, 150));//f 5
        pointsXYZ.add(new Location3D(50, 250, 150));//g 6
        pointsXYZ.add(new Location3D(150, 250, 150));//h 7
        incX = incY = 1;
        transformations = new Transformations();
        vector = new Location3D(200.0, 300.0, 450.0);
        vectorSticks = new Location3D(00.0, 800.0, 200.0);
        loading = false;
        this.g = new Figures();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contador = 0;
        Rcontador = 0;
        Lcontador = 0;
    }

    public void paint(Graphics g) {
        animacion = new BufferedImage(this.getWidth(), 800, BufferedImage.TYPE_INT_RGB);

        /*Animaciones*/
        qbert(g);
        this.getGraphics().drawImage(this.animacion, 0, 0, this);
        g.dispose();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        animacion.getGraphics().drawImage(buffer, x, y, this);
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

    public void lineaDDA(int x1, int y1, int x2, int y2, Color c) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float steps;
        if (abs(dx) >= abs(dy)) {
            steps = abs(dx);
        } else {
            steps = abs(dy);
        }
        float xinc = dx / steps;
        float yinc = dy / steps;

        float x = x1, y = y1;
        for (int i = 0; i <= steps; i++) {
            putPixel(round(x), round(y), c);
            x += xinc;
            y += yinc;
        }
    }

    public void paralelaCubo(Double[][] coordenadas, Double Xa, Double Ya) {

        Double[][] fig2d = new Double[8][2];
        Double t;
        Double iz = 1000.0;

        for (int i = 0; i < 8; i++) {
            t = -coordenadas[i][2] / d[0][2];
            fig2d[i][0] = coordenadas[i][0] + (t * d[0][0]);
            fig2d[i][1] = coordenadas[i][1] + (t * d[0][1]);
        }

        for (int i = 0; i < 8; i++) {
            if (coordenadas[i][2] < iz) {
                iz = coordenadas[i][2];
                indexZ = i;
            }
        }
        //Cubo completo
        if (indexZ != 0 && indexZ != 1) {
            lineaDDA((int) (Xa - fig2d[0][0]), (int) (Ya - fig2d[0][1]), (int) (Xa - fig2d[1][0]), (int) (Ya - fig2d[1][1]), Color.WHITE);
        } //A->B
        if (indexZ != 1 && indexZ != 3) {
            lineaDDA((int) (Xa - fig2d[1][0]), (int) (Ya - fig2d[1][1]), (int) (Xa - fig2d[3][0]), (int) (Ya - fig2d[3][1]), Color.WHITE);
        } //B->D
        if (indexZ != 2 && indexZ != 3) {
            lineaDDA((int) (Xa - fig2d[2][0]), (int) (Ya - fig2d[2][1]), (int) (Xa - fig2d[3][0]), (int) (Ya - fig2d[3][1]), Color.WHITE);
        } //C->D
        if (indexZ != 2 && indexZ != 0) {
            lineaDDA((int) (Xa - fig2d[2][0]), (int) (Ya - fig2d[2][1]), (int) (Xa - fig2d[0][0]), (int) (Ya - fig2d[0][1]), Color.WHITE);
        } //C->A
        if (indexZ != 2 && indexZ != 6) {
            lineaDDA((int) (Xa - fig2d[2][0]), (int) (Ya - fig2d[2][1]), (int) (Xa - fig2d[6][0]), (int) (Ya - fig2d[6][1]), Color.WHITE);
        } //C->G
        if (indexZ != 3 && indexZ != 7) {
            lineaDDA((int) (Xa - fig2d[3][0]), (int) (Ya - fig2d[3][1]), (int) (Xa - fig2d[7][0]), (int) (Ya - fig2d[7][1]), Color.WHITE);
        } //D->H
        if (indexZ != 0 && indexZ != 4) {
            lineaDDA((int) (Xa - fig2d[0][0]), (int) (Ya - fig2d[0][1]), (int) (Xa - fig2d[4][0]), (int) (Ya - fig2d[4][1]), Color.WHITE);
        } //A->E
        if (indexZ != 1 && indexZ != 5) {
            lineaDDA((int) (Xa - fig2d[1][0]), (int) (Ya - fig2d[1][1]), (int) (Xa - fig2d[5][0]), (int) (Ya - fig2d[5][1]), Color.WHITE);
        } //B->F
        if (indexZ != 7 && indexZ != 6) {
            lineaDDA((int) (Xa - fig2d[7][0]), (int) (Ya - fig2d[7][1]), (int) (Xa - fig2d[6][0]), (int) (Ya - fig2d[6][1]), Color.WHITE);
        } //H->G
        if (indexZ != 7 && indexZ != 5) {
            lineaDDA((int) (Xa - fig2d[7][0]), (int) (Ya - fig2d[7][1]), (int) (Xa - fig2d[5][0]), (int) (Ya - fig2d[5][1]), Color.WHITE);
        } //H->F
        if (indexZ != 5 && indexZ != 4) {
            lineaDDA((int) (Xa - fig2d[5][0]), (int) (Ya - fig2d[5][1]), (int) (Xa - fig2d[4][0]), (int) (Ya - fig2d[4][1]), Color.WHITE);
        } //F->E
        if (indexZ != 4 && indexZ != 6) {
            lineaDDA((int) (Xa - fig2d[4][0]), (int) (Ya - fig2d[4][1]), (int) (Xa - fig2d[6][0]), (int) (Ya - fig2d[6][1]), Color.WHITE);
        } //E->G
    }

    public void determinarCentros() {

        Double c = a + (b - a) / 2;

        centros[0][0] = a;
        centros[0][1] = c;
        centros[0][2] = c;

        centros[1][0] = c;
        centros[1][1] = c;
        centros[1][2] = b;

        centros[2][0] = b;
        centros[2][1] = c;
        centros[2][2] = c;

        centros[3][0] = c;
        centros[3][1] = c;
        centros[3][2] = a;

        centros[4][0] = c;
        centros[4][1] = b;
        centros[4][2] = c;

        centros[5][0] = c;
        centros[5][1] = a;
        centros[5][2] = c;

        centros[0][3] = 1.0;
        centros[1][3] = 1.0;
        centros[2][3] = 1.0;
        centros[3][3] = 1.0;
        centros[4][3] = 1.0;
        centros[5][3] = 1.0;
    }

    public void paraleloCentros() {

        centrosP = new Double[6][2];
        Double t;

        for (int i = 0; i < centros.length; i++) {

            t = -centros[i][2] / d[0][2];
            centrosP[i][0] = centros[i][0] + (t * d[0][0]);
            centrosP[i][1] = centros[i][1] + (t * d[0][1]);

        }
    }

    public void Rotacion(Double[][] coordenadas, int a, char e) {

        Double r[] = {0.0, 0.0, 0.0, 0.0};

        Double[][] T = new Double[4][4];

        Double[][] Tx = {
            {1.0, 0.0, 0.0, 0.0},
            {0.0, Math.cos(Math.toRadians(a)), Math.sin(Math.toRadians(a)), 0.0},
            {0.0, -Math.sin(Math.toRadians(a)), Math.cos(Math.toRadians(a)), 0.0},
            {0.0, 0.0, 0.0, 1.0}
        };
        Double[][] Ty = {
            {Math.cos(Math.toRadians(a)), 0.0, -Math.sin(Math.toRadians(a)), 0.0},
            {0.0, 1.0, 0.0, 0.0},
            {Math.sin(Math.toRadians(a)), 0.0, Math.cos(Math.toRadians(a)), 0.0},
            {0.0, 0.0, 0.0, 1.0}
        };
        Double[][] Tz = {
            {Math.cos(Math.toRadians(a)), Math.sin(Math.toRadians(a)), 0.0, 0.0},
            {-Math.sin(Math.toRadians(a)), Math.cos(Math.toRadians(a)), 0.0, 0.0},
            {0.0, 0.0, 1.0, 0.0},
            {0.0, 0.0, 0.0, 1.0}
        };

        switch (e) {
            case 'x':
                T = Tx;
                break;
            case 'y':
                T = Ty;
                break;
            case 'z':
                T = Tz;
                break;
        }

        for (int m = 0; m < coordenadas.length; m++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    r[i] += coordenadas[m][j] * T[i][j];
                }
            }

            coordenadas[m][0] = r[0];
            coordenadas[m][1] = r[1];
            coordenadas[m][2] = r[2];
            r[0] = 0.0;
            r[1] = 0.0;
            r[2] = 0.0;
        }

    }

    public void determinarCentrosEsc() {

        Double c = ae + (be - ae) / 2;

        centrosEsc[0][0] = ae;
        centrosEsc[0][1] = c;
        centrosEsc[0][2] = c;

        centrosEsc[1][0] = c;
        centrosEsc[1][1] = c;
        centrosEsc[1][2] = be;

        centrosEsc[2][0] = be;
        centrosEsc[2][1] = c;
        centrosEsc[2][2] = c;

        centrosEsc[3][0] = c;
        centrosEsc[3][1] = c;
        centrosEsc[3][2] = ae;

        centrosEsc[4][0] = c;
        centrosEsc[4][1] = be;
        centrosEsc[4][2] = c;

        centrosEsc[5][0] = c;
        centrosEsc[5][1] = ae;
        centrosEsc[5][2] = c;

        centrosEsc[0][3] = 1.0;
        centrosEsc[1][3] = 1.0;
        centrosEsc[2][3] = 1.0;
        centrosEsc[3][3] = 1.0;
        centrosEsc[4][3] = 1.0;
        centrosEsc[5][3] = 1.0;
    }

    public void determinarCentrosT() {

        Double c = at + (bt - at) / 2;

        centrosT[0][0] = at;
        centrosT[0][1] = c;
        centrosT[0][2] = c;

        centrosT[1][0] = c;
        centrosT[1][1] = c;
        centrosT[1][2] = bt;

        centrosT[2][0] = bt;
        centrosT[2][1] = c;
        centrosT[2][2] = c;

        centrosT[3][0] = c;
        centrosT[3][1] = c;
        centrosT[3][2] = at;

        centrosT[4][0] = c;
        centrosT[4][1] = bt;
        centrosT[4][2] = c;

        centrosT[5][0] = c;
        centrosT[5][1] = at;
        centrosT[5][2] = c;

        centrosT[0][3] = 1.0;
        centrosT[1][3] = 1.0;
        centrosT[2][3] = 1.0;
        centrosT[3][3] = 1.0;
        centrosT[4][3] = 1.0;
        centrosT[5][3] = 1.0;
    }

    public void inundacion2(int x, int y, Color c, Color ob) {
        if ((x < this.getWidth() && y < 800) && (x > 0 && y > 0)) {
            color = ReadPixel(x, y);
            if (color.equals(ob)) {
                putPixel(x, y, c);
                inundacion2(x, y + 1, c, ob);
                inundacion2(x + 1, y, c, ob);
                inundacion2(x, y - 1, c, ob);
                inundacion2(x - 1, y, c, ob);
            }
        }
    }

    public void inundacion(int x, int y, Color c) {
        if ((x < this.getWidth() && y < 800) && (x > 0 && y > 0)) {
            color = ReadPixel(x, y);
            if (color.equals(disponible)) {
                putPixel(x, y, c);
                inundacion(x, y + 1, c);
                inundacion(x + 1, y, c);
                inundacion(x, y - 1, c);
                inundacion(x - 1, y, c);
            }
        }
    }

    public Color ReadPixel(int x, int y) {
        return new Color(animacion.getRGB(x, y));
    }

    public void RellenarCubo(Double Xa, Double Ya) {
        if (indexZ != 0 && indexZ != 1 && indexZ != 2 && indexZ != 3) {
            inundacion((int) (Xa - centrosP[0][0]), (int) (Ya - centrosP[0][1]), Color.CYAN);
        }
        if (indexZ != 1 && indexZ != 3 && indexZ != 5 && indexZ != 7) {
            inundacion((int) (Xa - centrosP[1][0]), (int) (Ya - centrosP[1][1]), Color.GRAY);
        }
        if (indexZ != 4 && indexZ != 5 && indexZ != 6 && indexZ != 7) {
            inundacion((int) (Xa - centrosP[2][0]), (int) (Ya - centrosP[2][1]), Color.CYAN);
        }
        if (indexZ != 0 && indexZ != 2 && indexZ != 6 && indexZ != 7) {
            inundacion((int) (Xa - centrosP[3][0]), (int) (Ya - centrosP[3][1]), Color.GRAY);
        }
        if (indexZ != 2 && indexZ != 3 && indexZ != 6 && indexZ != 7) {
            inundacion((int) (Xa - centrosP[4][0]), (int) (Ya - centrosP[4][1]), Color.YELLOW);
        }
        if (indexZ != 0 && indexZ != 1 && indexZ != 4 && indexZ != 5) {
            inundacion((int) (Xa - centrosP[5][0]), (int) (Ya - centrosP[5][1]), Color.YELLOW);
        }
    }

    /*Metodos Qbert*/
    public void qbert(Graphics g) {
        animacionQbert(g);
    }

    public void Pong() {
        //p
        dibujarEdificio(400.0, 400.0, 120.0, Color.BLUE, Color.BLACK, Color.BLACK);
        dibujarEdificioAlargadoEnX(450.0, 285.0, 3.5, Color.BLUE, Color.BLUE, Color.YELLOW);
        dibujarEdificio(500.0, 336.0, 55.0, Color.BLUE, Color.YELLOW, Color.YELLOW);
        dibujarEdificioAlargadoEnX(450.0, 337.0, 3.5, Color.YELLOW, Color.YELLOW, Color.YELLOW);

        //i
        dibujarEdificio(570.0, 400.0, 120.0, Color.BLUE, Color.YELLOW, Color.YELLOW);
        dibujarEdificio(570.0, 250.0, 10.0, Color.YELLOW, new Color(0, 0, 1), Color.YELLOW);

        //N
        dibujarEdificio(640.0, 400.0, 120.0, Color.BLUE, Color.YELLOW, Color.YELLOW);
        dibujarEdificio(698.0, 318.0, 10.0, Color.BLUE, Color.YELLOW, Color.YELLOW);
        dibujarEdificio(730.0, 375.0, 10.0, Color.BLUE, Color.YELLOW, Color.YELLOW);
        dibujarEdificio(790.0, 400.0, 120.0, Color.BLUE, Color.YELLOW, Color.YELLOW);

        //G
        dibujarEdificioAlargadoEnX(930.0, 280.0, 4.5, Color.BLUE, Color.YELLOW, Color.YELLOW);
        dibujarEdificio(860.0, 375.0, 100.0, Color.BLUE, Color.YELLOW, Color.YELLOW);
        dibujarEdificioAlargadoEnX(930.0, 400.0, 4.5, Color.BLUE, Color.YELLOW, Color.YELLOW);
        dibujarEdificio(1000.0, 375.0, 32.0, Color.BLUE, Color.YELLOW, Color.YELLOW);
        dibujarEdificioAlargadoEnX(970.0, 348.0, 2.5, Color.BLUE, new Color(0, 0, 0), Color.YELLOW);

    }

    public void animacionQbert(Graphics g) {

        BufferedImage temp = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        temp.setData(animacion.getRaster());

        for (int i = 1; i < 2; i++) {

            animacion = new BufferedImage(1350, 800, BufferedImage.TYPE_INT_RGB);
            animacion.setData(temp.getRaster());

            if (incX < 1.0600) {
                Pong();

                cubes();
                cubes1();
                seamlessPattern();
                System.out.println("i = " + incX);
                this.time += 1000 / 60;
            } else {
                Area();
                LeftSticks();
                RightStick();
                Rotacion(this.coordenadas, 10, 'y');
                Rotacion(this.centros, 10, 'y');
                paraleloCentros();
                paralelaCubo(this.coordenadas, 670.0, 290.0);
                RellenarCubo(670.0, 290.0);
                cuberto();

            }

            this.getGraphics().drawImage(this.animacion, 0, 0, this);
        }
    }

    public void dibujarEdificio(Double CordinateX, Double CordinateY, Double c, Color c1,
            Color c2, Color c3) {
        Double a = -20.0, b = 20.0; // Modifica las dimensiones según sea necesario

        Double[][] coorEdificio = {
            {a, a, a, 1.0}, // A0
            {a, a, b, 1.0}, // B1
            {a, c, a, 1.0}, // C2
            {a, c, b, 1.0}, // D3
            {b, a, a, 1.0}, // E4
            {b, a, b, 1.0}, // F5
            {b, c, a, 1.0}, // G6
            {b, c, b, 1.0} // H7
        };

        // Ajusta otras llamadas de métodos según sea necesario
        paraleloCentros();
        paralelaCubo(coorEdificio, CordinateX, CordinateY);
        int x = (int) (CordinateX + 1);
        int y1 = (int) (CordinateY - 5);
        int y2 = (int) (CordinateY + 5);
        inundacion(x, y1, c1);
        inundacion(x, y2, c2);
        inundacion(x - 5, y2, c3);
    }

    public void dibujarEdificioAlargadoEnX(Double coordenadaX, Double coordenadaY, Double factorDeAlargamientoX,
            Color c1, Color c2, Color c3) {
        Double a = -20.0, b = 20.0, c = 5.0; // Modifica las dimensiones según sea necesario
        Double[][] coorEdificio = {
            {a, a, a, 1.0}, // A0
            {a, a, b, 1.0}, // B1
            {a, c, a, 1.0}, // C2
            {a, c, b, 1.0}, // D3
            {b, a, a, 1.0}, // E4
            {b, a, b, 1.0}, // F5
            {b, c, a, 1.0}, // G6
            {b, c, b, 1.0} // H7
        };

        // Aplica el alargamiento solo a las coordenadas X
        for (int i = 0; i < coorEdificio.length; i++) {
            coorEdificio[i][0] *= factorDeAlargamientoX;
        }

        // Ajusta otras llamadas de métodos según sea necesario
        paraleloCentros();
        paralelaCubo(coorEdificio, coordenadaX, coordenadaY);
        int x = (int) (coordenadaX + 1);
        int y1 = (int) (coordenadaY - 5);
        int y2 = (int) (coordenadaY + 5);
        inundacion(x, y1, c1);
        inundacion(x, y2, c2);
        inundacion(x - 5, y2, c3);
    }

    private void rutina() {
        if (this.r1) {
            cubertoIncZ = 0;
            cubertoIncX = 0;
            if (cubertoIncY < 6.900000) {
                cubertoIncY += .3;
                cubertoIncX -= 20;
                location3D = transformations.translation3D(cubertoIncX, cubertoIncY, cubertoIncZ, location3D);
                System.out.println("this = " + cubertoIncY);

            } else {
                this.r1 = false;
                this.r2 = true;
                cubertoIncZ = 0;
                cubertoIncX = 0;
                cubertoIncY = 0;
                try {
                    sleep(120);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //firstMove end

        } else if (r2 && contador < 430) {
            location3D = transformations.translation3D(10, 5, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 430) {
                r3 = true;
                r2 = false;
                contador = 0;
            }

        } else if (r3 && contador < 160) {
            location3D = transformations.translation3D(10, -9, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 160) {
                r4 = true;
                r3 = false;
                contador = 0;
            }

        } else if (r4 && contador < 310) {
            location3D = transformations.translation3D(-10, -5, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 310) {
                r5 = true;
                r4 = false;
                contador = 0;
            }
            //thirdMove end
        } else if (r5 && contador < 280) {
            location3D = transformations.translation3D(-10, 4, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 280) {
                r6 = true;
                r5 = false;
                contador = 0;
            }
//            fourthMove end
        } else if (r6 && contador < 590) {
            location3D = transformations.translation3D(10, 0, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 590) {
                r7 = true;
                r6 = false;
                contador = 0;
            }
            //fifthMove end
        } else if (r7 && contador < 590) {
            location3D = transformations.translation3D(-10, 2, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 590) {
                r8 = true;
                r7 = false;
                contador = 0;
            }
//            //sixthMove end 
        } else if (r8 && contador < 65) {
            location3D = transformations.translation3D(10, 10, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 65) {
                r8 = false;
                r9 = true;
                contador = 0;
            }
//            // eighthMove end
        } else if (r9 && contador < 300) {
            location3D = transformations.translation3D(10, -10, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 300) {
                r9 = false;
                r10 = true;
                contador = 0;
            }
//            // ninthMove end
        } else if (r10 && contador < 220) {
            location3D = transformations.translation3D(10, 2, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 220) {
                r10 = false;
                r11 = true;
                contador = 0;
            }
//            // tenthMove end
        } else if (r11 && contador < 320) {
            location3D = transformations.translation3D(-10, 2, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 320) {
                r11 = false;
                r12 = true;
                contador = 0;
            }
////            // eleventhMove end
        } else if (r12 && contador < 65) {
            location3D = transformations.translation3D(-40, 2, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 65) {
                r12 = false;
                r13 = true;
                contador = 0;
            }
//////            // twelfthMove end
        } else if (r13 && contador < 430) {
            location3D = transformations.translation3D(10, 4, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 430) {
                r13 = false;
                r14 = true;
                contador = 0;
            }
            // thirteenthMove end
        } else if (r14 && contador < 150) {
            location3D = transformations.translation3D(10, -6, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 150) {
                r14 = false;
                r15 = true;
                contador = 0;
            }
            // fourteenthMove end
        } else if (r15 && contador < 400) {
            location3D = transformations.translation3D(-10, -5, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 400) {
                r15 = false;
                r16 = true;
                contador = 0;
            }
            // fifteenthMove end
        } else if (r16 && contador < 190) {
            location3D = transformations.translation3D(-10, 5, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 190) {
                r16 = false;
                r17 = true;
                contador = 0;
            }
        } else if (r17 && contador < 390) {
            location3D = transformations.translation3D(10, 5, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 390) {
                r17 = false;
                r18 = true;
                contador = 0;
            }
        } else if (r18 && contador < 200) {
            location3D = transformations.translation3D(10, -8, cubertoIncZ, location3D);
            contador += 5;
            System.out.println(contador);
            if (contador == 200) {
                r18 = false;
                r19 = true;
                contador = 0;
            }
        }
    }

    public void RutinaLeftStick() {
        if (this.l1) {
            stickIncZ = 0;
            stickIncX = 0;
            if (stickIncY < 6.900000) {
                stickIncY += 5;
                stickIncX -= 0;
                location3DStickLeft = transformations.translation3D(stickIncX, stickIncY, stickIncZ, location3DStickLeft);
                System.out.println("this = " + stickIncY);

            } else {
                this.l1 = false;
                this.l2 = true;
                stickIncZ = 0;
                stickIncX = 0;
                stickIncY = 0;
                try {
                    sleep(120);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //firstMove end
        } else if (l2 && Rcontador < 50) {
            location3DStickLeft = transformations.translation3D(0, -10, stickIncZ, location3DStickLeft);
            Rcontador += 5;
            System.out.println("this = " + Rcontador);
            if (Rcontador == 50) {
                l2 = false;
                l3 = true;
                Rcontador = 0;
            }
        } else if (l3 && Rcontador < 50) {
            location3DStickLeft = transformations.translation3D(0, 10, stickIncZ, location3DStickLeft);
            Rcontador += 5;
            System.out.println("this = " + Rcontador);
            if (Rcontador == 50) {
                l3 = false;
                l4 = true;
                Rcontador = 0;
            }
        } else if (l3 && Rcontador < 50) {
            location3DStickLeft = transformations.translation3D(0, 10, stickIncZ, location3DStickLeft);
            Rcontador += 5;
            System.out.println("this abajo = " + Rcontador);
            if (Rcontador == 50) {
                l3 = false;
                l4 = true;
                Rcontador = 0;
            }
        } else if (l4 && Rcontador < 500) {
            location3DStickLeft = transformations.translation3D(0, -1, stickIncZ, location3DStickLeft);
            Rcontador += 5;
            System.out.println("this arriba = " + Rcontador);
            if (Rcontador == 500) {
                l4 = false;
                l5 = true;
                Rcontador = 0;
            }
        } else if (l5 && Rcontador < 1300) {
            location3DStickLeft = transformations.translation3D(0, 0, stickIncZ, location3DStickLeft);
            Rcontador += 5;
            System.out.println("this espera = " + Rcontador);
            if (Rcontador == 1300) {
                l5 = false;
                l6 = true;
                Rcontador = 0;
            }
        } else if (l6 && Rcontador < 300) {
            location3DStickLeft = transformations.translation3D(0, 5, stickIncZ, location3DStickLeft);
            Rcontador += 5;
            System.out.println("this abajo = " + Rcontador);
            if (Rcontador == 300) {
                l6 = false;
                l7 = true;
                Rcontador = 0;
            }
        } else if (l7 && Rcontador < 1000) {
            location3DStickLeft = transformations.translation3D(0, 0, stickIncZ, location3DStickLeft);
            Rcontador += 5;
            System.out.println("this espera = " + Rcontador);
            if (Rcontador == 1000) {
                l7 = false;
                l8 = true;
                Rcontador = 0;
            }
        } else if (l8 && Rcontador < 300) {
            location3DStickLeft = transformations.translation3D(0, -5, stickIncZ, location3DStickLeft);
            Rcontador += 5;
            System.out.println("this Arriba = " + Rcontador);
            if (Rcontador == 300) {
                l8 = false;
                l9 = true;
                Rcontador = 0;
            }
        }
    }

    public void RutinaRightStick() {
        if (this.e1) {
            stickIncZ = 0;
            stickIncX = 0;
            if (stickIncY < 6.900000) {
                stickIncY += 5;
                stickIncX -= 0;
                location3DStickRight = transformations.translation3D(stickIncX, stickIncY, stickIncZ, location3DStickRight);
                System.out.println("this = " + stickIncY);

            } else {
                this.e1 = false;
                this.e2 = true;
                stickIncZ = 0;
                stickIncX = 0;
                stickIncY = 0;
                try {
                    sleep(120);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (e2 && Lcontador < 200) {
            location3DStickRight = transformations.translation3D(0, 5, stickIncZ, location3DStickRight);
            Lcontador += 5;
            System.out.println("this Arriba = " + Lcontador);
            if (Lcontador == 200) {
                e2 = false;
                e3 = true;
                Lcontador = 0;
            }
        } else if (e3 && Lcontador < 200) {
            location3DStickRight = transformations.translation3D(0, -3, stickIncZ, location3DStickRight);
            Lcontador += 5;
            System.out.println("this Arriba = " + Lcontador);
            if (Lcontador == 200) {
                e3 = false;
                e4 = true;
                Lcontador = 0;
            }
        } else if (e4 && Lcontador < 1300) {
            location3DStickRight = transformations.translation3D(0, 0, stickIncZ, location3DStickRight);
            Lcontador += 5;
            System.out.println("this Arriba R = " + Lcontador);
            if (Lcontador == 1300) {
                e4 = false;
                e5 = true;
                Lcontador = 0;
            }
        } else if (e5 && Lcontador < 70) {
            location3DStickRight = transformations.translation3D(0, -10, stickIncZ, location3DStickRight);
            Lcontador += 5;
            System.out.println("this Arriba R = " + Lcontador);
            if (Lcontador == 70) {
                e5 = false;
                e6 = true;
                Lcontador = 0;
            }
        } else if (e6 && Lcontador < 2100) {
            location3DStickRight = transformations.translation3D(0, 0, stickIncZ, location3DStickRight);
            Lcontador += 5;
            System.out.println("this ESPERA R = " + Lcontador);
            if (Lcontador == 2100) {
                e6 = false;
                e7 = true;
                Lcontador = 0;
            }
        }else if (e7 && Lcontador < 50) {
            location3DStickRight = transformations.translation3D(0, 10, stickIncZ, location3DStickRight);
            Lcontador += 5;
            System.out.println("this ESPERA R = " + Lcontador);
            if (Lcontador == 50) {
                e7 = false;
                e8 = true;
                Lcontador = 0;
            }
        }
    }

    public void seamlessPattern() {
        int rows = 7;
        int cols = 1;
        double initialX = 60.0;
        double initialY = 180.0;
        double deltaX = 70.0;
        double deltaY = 70.0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                paraleloCentros();
                paralelaCubo(coorCubos, initialX + j * deltaX, initialY + i * deltaY);
                int x = (int) (initialX + j * deltaX) + 1;
                int y1 = (int) (initialY + i * deltaY) - 5;
                int y2 = (int) (initialY + i * deltaY) + 5;
                // Lógica de color aquí
                if (colorido) {
                    inundacion(x, y1, new Color(239, 239, 239));
                    inundacion(x, y2, new Color(171, 171, 171));
                    inundacion(x - 5, y2, new Color(210, 210, 210));
                } else {
                    inundacion(x, y1, new Color(20, 20, 20));
                    inundacion(x, y2, new Color(244, 211, 165));
                    inundacion(x - 5, y2, new Color(251, 243, 234));
                }
            }
        }

        initialX = 1290;
        initialY = 180;
        deltaX = 70.0;
        deltaY = 70.0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                paraleloCentros();
                paralelaCubo(coorCubos, initialX + j * deltaX, initialY + i * deltaY);
                int x = (int) (initialX + j * deltaX) + 1;
                int y1 = (int) (initialY + i * deltaY) - 5;
                int y2 = (int) (initialY + i * deltaY) + 5;
                // Lógica de color aquí
                if (colorido) {
                    inundacion(x, y1, new Color(239, 239, 239));
                    inundacion(x, y2, new Color(171, 171, 171));
                    inundacion(x - 5, y2, new Color(210, 210, 210));
                } else {
                    inundacion(x, y1, new Color(20, 20, 20));
                    inundacion(x, y2, new Color(244, 211, 165));
                    inundacion(x - 5, y2, new Color(251, 243, 234));
                }
            }
        }
        colorido = !colorido; // Alternar colores
    }

    public void Area() {
        double startX = 138.0;
        double startY = 690.0;
        double incrementoX = 140.0;
        double altura = 3.0;
        Color color1 = Color.WHITE;

        for (int i = 0; i < 8; i++) {
            dibujarEdificioAlargadoEnX(startX, startY, altura, color1, color1, color1);
            startX += incrementoX;
        }
        dibujarEdificioAlargadoEnX(1238.0, 690.0, 2.0, Color.WHITE, Color.WHITE, Color.WHITE);

        startY = 52.0;
        startX = 138.0;
        for (int j = 0; j < 8; j++) {
            dibujarEdificioAlargadoEnX(startX, startY, altura, color1, color1, color1);
            startX += incrementoX;
        }

        dibujarEdificioAlargadoEnX(1238.0, 52.0, 2.0, Color.WHITE, Color.WHITE, Color.WHITE);

    }

    public static void main(String[] args) {
        new Animation3D();
    }

    @Override
    public void run() {
        while (true) {
            try {
                incX += .001;
                incY = 1;
                incZ = 1;

                repaint();
                sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("algo salio mal");
            }
        }
    }

    private void cubes() {
        for (int i = 0; i < pointsXYZ.size(); i++) {
            float u = (float) (pointsXYZ.get(i).pointZ) / vector.pointZ;
            float x = (pointsXYZ.get(i).pointX + (vector.pointX * u)) / 3;
            float y = (pointsXYZ.get(i).pointY + (vector.pointY * u)) / 3;
            pointsXY.add(new Location((int) x + 0, (int) y + 0));
        }
        pointsXYZ.set(0, new Location3D(50, 150, 50));
        pointsXYZ.set(2, new Location3D(50, 250, 50));
        pointsXYZ.set(4, new Location3D(50, 150, 150));
        pointsXYZ.set(6, new Location3D(50, 250, 150));

        pointsXYZ = Transformations.Escalation3D(incX, incY, incZ, pointsXYZ);
        for (Location point : pointsXY) {
            putPixel(point.pointX, point.pointY, Color.black);
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
        fillFigure(square2, Color.YELLOW);
        int square3[][] = {
            {pointsXY.get(4).pointX, pointsXY.get(4).pointY},
            {pointsXY.get(5).pointX, pointsXY.get(5).pointY},
            {pointsXY.get(7).pointX, pointsXY.get(7).pointY},
            {pointsXY.get(6).pointX, pointsXY.get(6).pointY}

        };
        fillFigure(square3, Color.WHITE);
        pointsXY.clear();
    }

    private void cubes1() {
        for (int i = 0; i < pointsXYZ.size(); i++) {
            float u = (float) (pointsXYZ.get(i).pointZ) / vector.pointZ;
            float x = (pointsXYZ.get(i).pointX + (vector.pointX * u)) / 3;
            float y = (pointsXYZ.get(i).pointY + (vector.pointY * u)) / 3;
            pointsXY.add(new Location((int) x + 0, (int) y + 600));
        }
        pointsXYZ.set(0, new Location3D(50, 150, 50));
        pointsXYZ.set(2, new Location3D(50, 250, 50));
        pointsXYZ.set(4, new Location3D(50, 150, 150));
        pointsXYZ.set(6, new Location3D(50, 250, 150));

        pointsXYZ = Transformations.Escalation3D(incX, incY, incZ, pointsXYZ);
        for (Location point : pointsXY) {
            putPixel(point.pointX, point.pointY, Color.black);
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
        fillFigure(square2, Color.YELLOW);
        int square3[][] = {
            {pointsXY.get(4).pointX, pointsXY.get(4).pointY},
            {pointsXY.get(5).pointX, pointsXY.get(5).pointY},
            {pointsXY.get(7).pointX, pointsXY.get(7).pointY},
            {pointsXY.get(6).pointX, pointsXY.get(6).pointY}

        };
        fillFigure(square3, Color.WHITE);
        pointsXY.clear();
    }

    private void cuberto() {
        rutina();
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < location3D.size(); i++) {
            float u = (float) (location3D.get(i).pointZ) / vector.pointZ;
            float x = location3D.get(i).pointX + (vector.pointX * u);
            float y = location3D.get(i).pointY + (vector.pointY * u);

            locations.add(new Location((int) x + 470, (int) y + 150));
        }
        Location location = new Location(2, 3);
        for (Location point : locations) {
            putPixel(point.pointX, point.pointY, Color.red);
            location.setPointY(point.pointY);
            location.setPointX(point.pointX);
        }
        putPixel(location.pointX, location.pointY, Color.white);
        int square[][] = {
            {locations.get(0).pointX, locations.get(0).pointY},
            {locations.get(1).pointX, locations.get(1).pointY},
            {locations.get(5).pointX, locations.get(5).pointY},
            {locations.get(4).pointX, locations.get(4).pointY}
        };
        fillFigure(square, Color.magenta);
        int square2[][] = {
            {locations.get(0).pointX, locations.get(0).pointY},
            {locations.get(4).pointX, locations.get(4).pointY},
            {locations.get(6).pointX, locations.get(6).pointY},
            {locations.get(2).pointX, locations.get(2).pointY}

        };
        fillFigure(square2, Color.PINK);
        int square3[][] = {
            {locations.get(4).pointX, locations.get(4).pointY},
            {locations.get(5).pointX, locations.get(5).pointY},
            {locations.get(7).pointX, locations.get(7).pointY},
            {locations.get(6).pointX, locations.get(6).pointY}

        };
        fillFigure(square3, Color.red);
        pointsXY.clear();
    }

    public void LeftSticks() {
        RutinaLeftStick();
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < location3DStickLeft.size(); i++) {
            float u = (float) (location3DStickLeft.get(i).pointZ) / vectorSticks.pointZ;
            float x = location3DStickLeft.get(i).pointX + (vectorSticks.pointX * u);
            float y = location3DStickLeft.get(i).pointY + (vectorSticks.pointY * u * 6);

            locations.add(new Location((int) x + 30, (int) y + 150));
        }
        Location location = new Location(2, 3);
        for (Location point : locations) {
            putPixel(point.pointX, point.pointY, Color.red);
            location.setPointY(point.pointY);
            location.setPointX(point.pointX);
        }
        putPixel(location.pointX, location.pointY, Color.white);
        int square[][] = {
            {locations.get(0).pointX, locations.get(0).pointY},
            {locations.get(1).pointX, locations.get(1).pointY},
            {locations.get(5).pointX, locations.get(5).pointY},
            {locations.get(4).pointX, locations.get(4).pointY}
        };
        fillFigure(square, Color.RED);
        int square2[][] = {
            {locations.get(0).pointX, locations.get(0).pointY},
            {locations.get(4).pointX, locations.get(4).pointY},
            {locations.get(6).pointX, locations.get(6).pointY},
            {locations.get(2).pointX, locations.get(2).pointY}

        };
        fillFigure(square2, Color.PINK);
        int square3[][] = {
            {locations.get(4).pointX, locations.get(4).pointY},
            {locations.get(5).pointX, locations.get(5).pointY},
            {locations.get(7).pointX, locations.get(7).pointY},
            {locations.get(6).pointX, locations.get(6).pointY}

        };
        fillFigure(square3, Color.white);
        pointsXY.clear();
        locations.clear();
    }

    public void RightStick() {
        RutinaRightStick();
        //second stick
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < location3DStickRight.size(); i++) {
            float u = (float) (location3DStickRight.get(i).pointZ) / vectorSticks.pointZ;
            float x = location3DStickRight.get(i).pointX + (vectorSticks.pointX * u);
            float y = location3DStickRight.get(i).pointY + (vectorSticks.pointY * u * 6);

            locations.add(new Location((int) x + 1280, (int) y + 150));
        }

        Location location = new Location(2, 3);
        location = new Location(2, 3);
        for (Location point : locations) {
            putPixel(point.pointX, point.pointY, Color.red);
            location.setPointY(point.pointY);
            location.setPointX(point.pointX);
        }
        putPixel(location.pointX, location.pointY, Color.white);
        int square4[][] = {
            {locations.get(0).pointX, locations.get(0).pointY},
            {locations.get(1).pointX, locations.get(1).pointY},
            {locations.get(5).pointX, locations.get(5).pointY},
            {locations.get(4).pointX, locations.get(4).pointY}
        };
        fillFigure(square4, Color.RED);
        int square5[][] = {
            {locations.get(0).pointX, locations.get(0).pointY},
            {locations.get(4).pointX, locations.get(4).pointY},
            {locations.get(6).pointX, locations.get(6).pointY},
            {locations.get(2).pointX, locations.get(2).pointY}

        };
        fillFigure(square5, Color.PINK);
        int square6[][] = {
            {locations.get(4).pointX, locations.get(4).pointY},
            {locations.get(5).pointX, locations.get(5).pointY},
            {locations.get(7).pointX, locations.get(7).pointY},
            {locations.get(6).pointX, locations.get(6).pointY}

        };
        fillFigure(square6, Color.white);
        pointsXY.clear();
        locations.clear();

    }
}
