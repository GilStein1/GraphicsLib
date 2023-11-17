package environments;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Window extends JPanel {

    boolean perspective = true;
    private String t;
    private Color c;
    private JFrame f;
    private java.util.List<Component> shapeList = new java.util.ArrayList<>();
    private java.util.List<Component2D> shape2dList = new java.util.ArrayList<>();
    private boolean ShowPoints = true;
    public double cameraXRotation = 0.01;
    public double cameraYRotation = 0.01;
    public double cameraZRotation = 0.01;
    public double cameraX = 0.01;
    public double cameraY = 0.01;
    public double cameraZ = 0.1;
    double focalLength = 660;
    public double[] light = {0.2,0,0};
    boolean isChanged = false;
    private BufferedImage screen;
    private Graphics2D graphicsThing;
    //double focalLength = 100;
    environments.PerlinNoiseGenerator p;

    public void madeByItayZukinAndGilStein() {
        System.out.println("This Library Was Made By Gil Stein And Itay Zukin");
    }
    public void setCameraXRotation(double xRotation) {
        cameraXRotation = xRotation;
        //update();
        for(Component m : shapeList) {
            m.sortFace();
        }
    }
    public void setCameraYRotation(double yRotation) {
        cameraYRotation = yRotation;
        //update();
        for(Component m : shapeList) {
            m.sortFace();
        }
    }
    public void setCameraZRotation(double zRotation) {
        cameraZRotation = zRotation;
        //update();
        for(Component m : shapeList) {
            m.sortFace();
        }
    }
    public void setCameraLocation(Location location) {
        cameraX = location.x;
        cameraY = location.y;
        cameraZ = location.z;
        //this.update();
    }

    public void setCameraLocation(double x, double y, double z) {
        cameraX = x;
        cameraY = y;
        cameraZ = z;
        //this.update();
    }


    public Window(Color background,double Width, double Height, String title) {

        this.t = title;
        this.c = background;

        JFrame frame = new JFrame(t);

        this.setBackground(c);

        this.f = frame;

        f.setSize((int)Width,(int)Height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.getContentPane().setBackground(c);
        f.setVisible(true);

        f.add(this);

        this.p = new environments.PerlinNoiseGenerator(System.currentTimeMillis());

//        Thread t = new Thread(() -> {
//            while (true) {
//                repaint();
//            }
//        });
        LoadingThread l = LoadingThread.getInstance(() -> {
            while (true) {
                //this.update();
                repaint();
                //System.out.println("loaded");
            }
        });
        l.start();

    }
    public int getWidth() {
        return f.getWidth();
    }
    public int getHeight() {
        return f.getHeight();
    }
    public void remove(Component p) {
        shapeList.remove(p);
    }

    public JFrame getFrame() {
        return f;
    }

    void add(Component p) {
        shapeList.add(p);
    }
    void add(Component2D c) {
        shape2dList.add(c);
    }

    public void waitInSeconds(double seconds) {
        try {
            long milliseconds = Math.round(seconds * 1000);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {

        }
    }
    public void showPoints(boolean expression) {
        this.ShowPoints = expression;
    }
    public void setPerspective(boolean perspective) {
        this.perspective = perspective;
        this.update();
    }
    public boolean getPerspective() {
        return perspective;
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (screen == null || (screen.getHeight() != f.getHeight() || screen.getWidth() != f.getWidth())) {
            screen = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        graphicsThing = screen.createGraphics();

        //System.out.println("painted");

        graphicsThing.setColor(c);

        graphicsThing.fillRect(0,0,f.getWidth(),f.getHeight());



        for(Component p : shapeList) {



            for (Point point : p.getPoints()) {
                point.perspective = perspective;
                point.update();
                if(ShowPoints)
                    point.draw(graphicsThing);

            }

            for(Line line : p.getLines()) {
                /*
                double ang1 = Math.toDegrees(Math.atan((line.p1.getZ()/line.p1.getX())));
                double ang2 = Math.toDegrees(Math.atan((line.p2.getZ()/line.p2.getX())));

                if (line.p1.getX() < cameraX && line.p1.getZ() > cameraZ) {
                    ang1 = 180 + ang1;
                }
                else if(line.p1.getX() < cameraX && line.p1.getZ() < cameraZ) {
                    ang1 = (180 + ang1);
                }

                System.out.println(ang1 + " camera: " + cameraXRotation);
                */

                //if((ang1 > cameraXRotation - 90 && ang1 < cameraXRotation + 90))
                if((line.p1.getcz() < line.p1.getWindow().cameraZ && line.p2.getcz() < line.p1.getWindow().cameraZ) && p.getFace().size() == 0)
                    line.draw(graphicsThing);
                //line.draw(g);
            }

            for(int i = 0; i < p.getFace().size(); i++) {

                p.getFace().get(i).updateFront();

                //System.out.println(p.getFace().get(i).front);
                if((p.getFace().get(i).front < cameraZ && perspective) || !perspective) {

                    //p.getFace().get(i).update();

                    if(p.getFace().get(i).facedToCamera()) {
                        p.getFace().get(i).update();
                        p.getFace().get(i).draw(graphicsThing);
                    }

                    //System.out.println(p.getFace().get(i).front);
                }
            }

            /*
            for(int i = 700; i > 0; i = i - 5) {
                for(Face f : p.getFace()) {

                    if((f.front <= (int)(cameraZ - i) + 2.5 && f.front > (int)(cameraZ - i) - 2.5)) {
                        f.update();
                        f.draw(g);
                    }
                    //f.update();

                    f.updateFront();

                }
            }
            */
        }

        for(Component2D c : shape2dList) {
            c.draw(graphicsThing);
        }

        graphicsThing.dispose();
        g.drawImage(screen, 0, 0, this);

    }

    public void update() {
        //System.out.println("updated");
        this.repaint();
    }

    //Methods that set what happens when a key is pressed

    public void setKeyAction(int key, Runnable r) {
        f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == key) {
                    r.run();
                }

            }
        });
    }
    public void setWKeyAction(Runnable r) {
        f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_W) {
                    r.run();
                }

            }
        });
    }
    public void setSKeyAction(Runnable r) {
        f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_S) {
                    r.run();
                }

            }
        });
    }
    public void setDKeyAction(Runnable r) {
        f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_D) {
                    r.run();
                }

            }
        });
    }
    public void setAKeyAction(Runnable r) {
        f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_A) {
                    r.run();
                }

            }
        });
    }
    public void addBasicMovements() {
        f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_W) {

                    //setCameraLocation(cameraX,cameraY,cameraZ - 5);
                    setCameraLocation(cameraX - 5*Math.sin(Math.toRadians(cameraYRotation)), cameraY - 5*Math.sin(Math.toRadians(cameraXRotation)), cameraZ - 5*Math.cos(Math.toRadians(cameraYRotation)));

                }
                else if (keyCode == KeyEvent.VK_S) {
                    setCameraLocation(cameraX + 5*Math.sin(Math.toRadians(cameraYRotation)), cameraY + 5*Math.sin(Math.toRadians(cameraXRotation)), cameraZ + 5*Math.cos(Math.toRadians(cameraYRotation)));
                }
                else if (keyCode == KeyEvent.VK_D) {
                    setCameraLocation(cameraX - 5*Math.sin(Math.toRadians(cameraYRotation-90)), cameraY, cameraZ - 5*Math.cos(Math.toRadians(cameraYRotation-90)));
                }
                else if (keyCode == KeyEvent.VK_A) {
                    setCameraLocation(cameraX - 5*Math.sin(Math.toRadians(cameraYRotation+90)), cameraY, cameraZ - 5*Math.cos(Math.toRadians(cameraYRotation+90)));
                }
                else if (keyCode == KeyEvent.VK_SPACE) {
                    setCameraLocation(cameraX,cameraY + 5,cameraZ);
                }
                else if (keyCode == KeyEvent.VK_SHIFT) {
                    setCameraLocation(cameraX,cameraY - 5,cameraZ);
                }

            }
        });
    }

    public static void mergeSort(java.util.List<Face> arr) {
        if (arr == null || arr.size() <= 1) {
            return;
        }
        //Face[] temp = new Face[arr.size()];
        java.util.List<Face> temp = new java.util.ArrayList<>();

        for(int i = 0; i < arr.size(); i++) {
            temp.add(null);
        }

        mergeSortHelper(arr, 0, arr.size() - 1, temp);
    }

    private static void mergeSortHelper(java.util.List<Face> arr, int left, int right, java.util.List<Face> temp) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid, temp);
            mergeSortHelper(arr, mid + 1, right, temp);
            merge(arr, left, mid, right, temp);
        }
    }

    private static void merge(java.util.List<Face> arr, int left, int mid, int right, java.util.List<Face> temp) {
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            if (arr.get(i).front <= arr.get(j).front) {
                //temp.get(k++) = arr.get(i++);
                temp.set(k++, arr.get(i++));
            } else {
                temp.set(k++, arr.get(j++));;
            }
        }
        while (i <= mid) {
            temp.set(k++, arr.get(i++));
        }
        while (j <= right) {
            temp.set(k++, arr.get(j++));
        }
        //System.arraycopy(temp, left, arr, left, right - left + 1);

        for (int h = 0; h < right - left + 1; h++) {
            arr.set(left + h, temp.get(left + h));
        }
    }




    public double generateNoise(double x, double y, double frequency) {
        return p.generateNoise(x,y,frequency);
    }






    class PerlinNoiseGenerator {
        private static final int PERSISTENCE = 4;
        private static final int OCTAVES = 8;

        private int[] p = new int[512];
        private double[] g1 = new double[512];
        private double[][] g2 = new double[512][2];
        private double[][] g3 = new double[512][3];

        public PerlinNoiseGenerator(long seed) {
            Random random = new Random(seed);

            for (int i = 0; i < 256; i++) {
                p[i] = i;
                g1[i] = random.nextDouble() * 2 - 1;

                for (int j = 0; j < 2; j++) {
                    g2[i][j] = random.nextDouble() * 2 - 1;
                }
                normalize2(g2[i]);

                for (int j = 0; j < 3; j++) {
                    g3[i][j] = random.nextDouble() * 2 - 1;
                }
                normalize3(g3[i]);
            }

            for (int i = 0; i < 256; i++) {
                int j = random.nextInt(256 - i) + i;
                int t = p[i];
                p[i] = p[j];
                p[j] = t;

                // Extend the arrays with the same values
                p[i + 256] = p[i];
                g1[i + 256] = g1[i];
                g2[i + 256][0] = g2[i][0];
                g2[i + 256][1] = g2[i][1];
                g3[i + 256][0] = g3[i][0];
                g3[i + 256][1] = g3[i][1];
                g3[i + 256][2] = g3[i][2];
            }
        }

        // Rest of the code remains the same



        private double fade(double t) {
            return t * t * t * (t * (t * 6 - 15) + 10);
        }

        private double lerp(double t, double a, double b) {
            return a + t * (b - a);
        }

        private double grad(int hash, double x) {
            return (hash & 1) == 0 ? x : -x;
        }

        private double grad(int hash, double x, double y) {
            int h = hash & 3;
            double u = h < 2 ? x : y;
            double v = h < 2 ? y : x;
            return ((h & 2) == 0 ? u : -u) + ((h & 1) == 0 ? v : -v);
        }

        private double grad(int hash, double x, double y, double z) {
            int h = hash & 15;
            double u = h < 8 ? x : y;
            double v = h < 4 ? y : h == 12 || h == 14 ? x : z;
            return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
        }

        private double noise1(double x) {
            int ix0 = (int) Math.floor(x);
            double fx0 = x - ix0;
            double fx1 = fx0 - 1;
            double wx = fade(fx0);

            int ix = ix0 & 255;
            int ix1 = (ix0 + 1) & 255;

            double n0 = grad(p[ix], fx0);
            double n1 = grad(p[ix1], fx1);
            double noiseValue = lerp(wx, n0, n1);

            return noiseValue;
        }

        private double noise2(double x, double y) {
            int ix0 = (int) Math.floor(x);
            int iy0 = (int) Math.floor(y);
            double fx0 = x - ix0;
            double fy0 = y - iy0;
            double fx1 = fx0 - 1;
            double fy1 = fy0 - 1;
            double wx = fade(fx0);
            double wy = fade(fy0);

            int ix = ix0 & 255;
            int iy = iy0 & 255;
            int ix1 = (ix0 + 1) & 255;
            int iy1 = (iy0 + 1) & 255;

            double n0 = grad(p[ix + p[iy]], fx0, fy0);
            double n1 = grad(p[ix + p[iy1]], fx0, fy1);
            double n2 = grad(p[ix1 + p[iy]], fx1, fy0);
            double n3 = grad(p[ix1 + p[iy1]], fx1, fy1);
            double noiseValue = lerp(wy, lerp(wx, n0, n2), lerp(wx, n1, n3));

            return noiseValue;
        }

        private double noise3(double x, double y, double z) {
            int ix0 = (int) Math.floor(x);
            int iy0 = (int) Math.floor(y);
            int iz0 = (int) Math.floor(z);
            double fx0 = x - ix0;
            double fy0 = y - iy0;
            double fz0 = z - iz0;
            double fx1 = fx0 - 1;
            double fy1 = fy0 - 1;
            double fz1 = fz0 - 1;
            double wx = fade(fx0);
            double wy = fade(fy0);
            double wz = fade(fz0);

            int ix = ix0 & 255;
            int iy = iy0 & 255;
            int iz = iz0 & 255;
            int ix1 = (ix0 + 1) & 255;
            int iy1 = (iy0 + 1) & 255;
            int iz1 = (iz0 + 1) & 255;

            double n0 = grad(p[ix + p[iy + p[iz]]], fx0, fy0, fz0);
            double n1 = grad(p[ix + p[iy + p[iz1]]], fx0, fy0, fz1);
            double n2 = grad(p[ix + p[iy1 + p[iz]]], fx0, fy1, fz0);
            double n3 = grad(p[ix + p[iy1 + p[iz1]]], fx0, fy1, fz1);
            double n4 = grad(p[ix1 + p[iy + p[iz]]], fx1, fy0, fz0);
            double n5 = grad(p[ix1 + p[iy + p[iz1]]], fx1, fy0, fz1);
            double n6 = grad(p[ix1 + p[iy1 + p[iz]]], fx1, fy1, fz0);
            double n7 = grad(p[ix1 + p[iy1 + p[iz1]]], fx1, fy1, fz1);

            double noiseValue = lerp(wz,
                    lerp(wy, lerp(wx, n0, n4), lerp(wx, n1, n5)),
                    lerp(wy, lerp(wx, n2, n6), lerp(wx, n3, n7)));

            return noiseValue;
        }

        public double generateNoise(double x, double y, double frequency) {
            double noise = 0;
            double amplitude = 1;
            double maxAmplitude = 0;

            for (int i = 0; i < OCTAVES; i++) {
                noise += noise2(x * frequency, y * frequency) * amplitude;
                frequency *= 2;
                maxAmplitude += amplitude;
                amplitude /= PERSISTENCE;
            }

            return noise / maxAmplitude;
        }

        private void normalize2(double[] v) {
            double s = Math.sqrt(v[0] * v[0] + v[1] * v[1]);
            v[0] /= s;
            v[1] /= s;
        }

        private void normalize3(double[] v) {
            double s = Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
            v[0] /= s;
            v[1] /= s;
            v[2] /= s;
        }
    }


}
