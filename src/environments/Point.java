package environments;

import java.awt.*;

public class Point {

    private Location worldLocation;
    private Location pLocation;
    double px,py;
    double[] textureCoords = new double[]{0,0};
    private Color c;
    private Window w;
    boolean perspective = true;
    private final double radius = 5;

    public Point(Window w, Location loc, Color c) {
        this.worldLocation = new Location(loc.x,loc.y,loc.z);
        this.pLocation = new Location(loc.x,loc.y,loc.z);
        if(loc.x == 0) {
            worldLocation.x = 0.0001;
        }
        if(loc.y == 0) {
            worldLocation.y = 0.0001;
        }
        if(loc.z == 0) {
            worldLocation.z = 0.0001;
        }
        this.c = c;
        this.w = w;
    }
    Window getWindow() {
        return w;
    }

    public void moveLocation(double x, double y, double z) {
        this.worldLocation.x = x;
        this.worldLocation.y = y;
        this.worldLocation.z = z;
    }

    public void ChangeColor(Color c) {
        this.c = c;
    }

    public double getX() {
        return worldLocation.x;
    }

    public double getY() {
        return worldLocation.y;
    }

    public double getZ() {
        return worldLocation.z;
    }

    public void setX(double x) {
        this.worldLocation.x = x;
        //w.update();
    }

    public void setY(double y) {
        this.worldLocation.y = y;
        //w.update();
    }

    public void setZ(double z) {
        this.worldLocation.z = z;
        //w.update();
    }

    public void moveXBy(double length) {
        this.worldLocation.x += length;
    }

    public void moveYBy(double length) {
        this.worldLocation.y += length;
    }

    public void moveZBy(double length) {
        this.worldLocation.z += length;
    }
    private static double[][] matrixMultiply(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        double[][] result = new double[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }
    double xMap;
    double yMap;
    double zMap;
    double ang1Map;
    double ang2Map;
    double dMap;
    double dMap2;
    double getcx() {
        return pLocation.x;
    }
    double getcy() {
        return pLocation.y;
    }
    double getcz() {
        return pLocation.z;
    }
    void update() {

        double x = worldLocation.x - w.cameraX;
        double y = worldLocation.y - w.cameraY;
        double z = worldLocation.z - w.cameraZ;

        ang1Map = Math.toDegrees(Math.atan(((z)/ (x))));


        if ((x < 0) && (z > 0)) {
            ang1Map = 180 + ang1Map;
        }
        else if((x < 0) && (z < 0)) {
            ang1Map = (180 + ang1Map);
        }



        ang1Map += w.cameraYRotation;


        dMap = Math.sqrt((z)*(z) + (x)*(x));


        zMap = dMap*Math.sin(Math.toRadians(ang1Map)) + w.cameraZ;
        xMap = dMap*Math.cos(Math.toRadians(ang1Map)) + w.cameraX;

        dMap2 = Math.sqrt((zMap - w.cameraZ)*(zMap - w.cameraZ) + (y)*(y));

        ang2Map = Math.toDegrees(Math.atan(((zMap - w.cameraZ)/ (y))));

        if ((y < 0) && (zMap - w.cameraZ > 0)) {
            ang2Map = 180 + ang2Map;
        }
        else if((y < 0) && (zMap - w.cameraZ < 0)) {
            ang2Map = (180 + ang2Map);
        }

        ang2Map += w.cameraXRotation;

        yMap = dMap2*Math.cos(Math.toRadians(ang2Map)) + w.cameraY;
        zMap = dMap2*Math.sin(Math.toRadians(ang2Map)) + w.cameraZ;

//        dMap2 = Math.sqrt((dMap)*(dMap) + (y)*(y));
//
//        ang2Map = Math.toDegrees(Math.atan(((dMap)/ (y))));
//
//        if ((y < 0) && (dMap > 0)) {
//            ang2Map = 180 + ang2Map;
//        }
//        else if((y < 0) && (dMap < 0)) {
//            ang2Map = (180 + ang2Map);
//        }
//
//        ang2Map += w.cameraXRotation;
//
//        yMap = dMap2*Math.cos(Math.toRadians(ang2Map)) + w.cameraY;

        pLocation.x = xMap;
        pLocation.y = yMap;
        pLocation.z = zMap;
//        xMap = worldLocation.x;
//        yMap = worldLocation.y;
//        zMap = worldLocation.z;
//
//        pLocation.x = worldLocation.x;
//        pLocation.y = worldLocation.y;
//        pLocation.z = worldLocation.z;

        px = (w.focalLength*(xMap - w.cameraX) + (xMap - w.cameraX)*(-zMap - -w.cameraZ))/(-zMap - -w.cameraZ);
        py = (w.focalLength*(yMap - w.cameraY) + (yMap - w.cameraY)*(-zMap - -w.cameraZ))/(-zMap - -w.cameraZ);

//        px = (w.focalLength*(worldLocation.x - w.cameraX) + (worldLocation.x - w.cameraX)*(-worldLocation.z - -w.cameraZ))/(-worldLocation.z - -w.cameraZ);
//        py = (w.focalLength*(worldLocation.y - w.cameraY) + (worldLocation.y - w.cameraY)*(-worldLocation.z - -w.cameraZ))/(-worldLocation.z - -w.cameraZ);

        if(pLocation.z >= w.cameraZ) {
            //px = px + (500* (Math.abs(py)/py));
            //py = py + (500* (Math.abs(py)/py));

            px = -px * 50 - (500* (Math.abs(px)/px));
            py = -py * 50 - (500* (Math.abs(py)/py));

            if(pLocation.z == w.cameraZ) {
                if(px < 0) {
                    px = 5000;
                }
                else {
                    px =  -5000;
                }
                //System.out.println("px = " + px);
            }
            if(pLocation.z == w.cameraZ) {
                if(py < 0) {
                    py = 5000;
                }
                else {
                    py =  -5000;
                }
                //System.out.println("py = "+ py);
            }
        }

        if(!perspective) {
            px = pLocation.x - w.cameraX;
            py = pLocation.y - w.cameraY;
        }

        //System.out.println(px + "," + py);

        //px = px*5;
        //py = py*5;
        /*
        double dx,dy,dz;
        double x = (pLocation.x - w.cameraX);
        double y = (pLocation.y - w.cameraY);
        double z = (pLocation.z - w.cameraZ);
        double xd = Math.toRadians(w.cameraXRotation);
        double yd = Math.toRadians(w.cameraYRotation);
        double zd = Math.toRadians(w.cameraZRotation);

        double xe = -w.focalLength*Math.sin(zd);
        double ye = -w.focalLength*Math.cos(Math.toRadians(270 + Math.toDegrees(xd)));
        double ze = -w.focalLength*Math.cos(yd);

        dx = Math.cos(yd)*(Math.sin(zd)*y + Math.cos(zd)*x) - Math.sin(yd)*z;
        dy = Math.sin(xd)*(Math.cos(yd)*z + Math.sin(yd)*(Math.sin(zd)*y + Math.cos(zd)*x)) + Math.cos(xd)*(Math.cos(zd)*y - Math.sin(zd)*x);
        dz = Math.cos(xd)*(Math.cos(yd)*z + Math.sin(yd)*(Math.sin(zd)*y + Math.cos(zd)*x)) - Math.sin(xd)*(Math.cos(zd)*y - Math.sin(zd)*x);

        px = (ze/dz) * dx + xe;
        py = (ze/dz) * dy + ye;
        */
    }
    public void draw(Graphics g) {

        update();

        //System.out.println("(" + px + "," + py + ")");

        g.setColor(c);
        g.fillOval((int) ((int)(w.getFrame().getWidth()/2) + px - (int)radius/2), (int) ((int)(w.getFrame().getHeight()/2) - py - (int)radius/2), (int) radius, (int) radius);
    }
}