package environments;

import java.awt.*;

public class Face {
    public Point[] arr;
    int[] xarr,yarr;
    public Color c;
    public Color cc;
    public Component com;
    public int front;
    double[] normal;
    Runnable shader;

    Face(Component com ,Point[] points, Color color) {
        this.arr = points;
        this.c = color;
        this.cc = color;
        this.com = com;



        update();
    }
    public boolean facedToCamera() {

        double[] temp = new double[3];

        temp[0] = (arr[0].getcy() - arr[1].getcy()) * (arr[0].getcz() - arr[2].getcz()) - (arr[0].getcz() - arr[1].getcz()) * (arr[0].getcy() - arr[2].getcy());
        temp[1] = (arr[0].getcz() - arr[1].getcz()) * (arr[0].getcx() - arr[2].getcx()) - (arr[0].getcx() - arr[1].getcx()) * (arr[0].getcz() - arr[2].getcz());
        temp[2] = (arr[0].getcx() - arr[1].getcx()) * (arr[0].getcy() - arr[2].getcy()) - (arr[0].getcy() - arr[1].getcy()) * (arr[0].getcx() - arr[2].getcx());

//        temp[0] = (arr[0].getY() - arr[1].getY()) * (arr[0].getZ() - arr[2].getZ()) - (arr[0].getZ() - arr[1].getZ()) * (arr[0].getY() - arr[2].getY());
//        temp[1] = (arr[0].getZ() - arr[1].getZ()) * (arr[0].getX() - arr[2].getX()) - (arr[0].getX() - arr[1].getX()) * (arr[0].getZ() - arr[2].getZ());
//        temp[2] = (arr[0].getX() - arr[1].getX()) * (arr[0].getY() - arr[2].getY()) - (arr[0].getY() - arr[1].getY()) * (arr[0].getX() - arr[2].getX());

        double l = Math.sqrt(temp[0]*temp[0] + temp[1]*temp[1] + temp[2]*temp[2]);

        temp[0] /= l;
        temp[1] /= l;
        temp[2] /= l;

        double m = ((arr[0].getcy() - arr[0].getWindow().cameraY)/(arr[0].getcx() - arr[0].getWindow().cameraX));
        double n = ((arr[0].getcz() - arr[0].getWindow().cameraZ)/(arr[0].getcx() - arr[0].getWindow().cameraX));

        double total = Math.sqrt(1*1 + m*m + n*n);

        double[] ray = {1/total,m/total,n/total};

        if(!arr[0].getWindow().getPerspective()) {
            ray = new double[]{0,0,1};
        }
        //double[] ray = {1,1/m,1/n};

        boolean result = (((ray[0]) * (temp[0]) + ray[1]*temp[1] + ray[2]*temp[2]) > 0);

        if(arr[0].px > 0 && arr[0].getWindow().perspective) {
            result = ! result;
        }

        return result || com.GetLoadFacesThatDoNotFaceTheCamera();

    }

    public void setShader(Runnable shader) {
        this.shader = shader;
    }
    public double[] getNormal() {
        return normal.clone();
    }
    public void updateWorldNormal() {

    }

    public void setColor() {

        if(shader != null) {
            shader.run();
        }
        else {
            double d = (normal[0]*arr[0].getWindow().light[0] + normal[1]*arr[0].getWindow().light[1] + normal[2]*arr[0].getWindow().light[2]) + 0.5;

            if(d < 0) {
                d = 0;
            }

            this.c = new Color((int)(cc.getRed()*d), (int)(cc.getGreen()*d),(int)(cc.getBlue()*d));

        }


        /*
        double avg = 0;
        double avg2 = 0;
        double place = 0;
        //int count = 0;
        double min = 0;
        double max = 0;
        double max2,min2;
        for(Point p : com.getPoints()) {
            avg = avg + p.getY();
            if(p.getY() < min) {
                min = p.getY();
            }
            if(p.getY() > max) {
                max = p.getY();
            }
        }
        //max = max + Math.abs(min);
        //min = min + Math.abs(min);
        max2 = max - min;
        min2 = min - min;
        //avg = avg + min;
        //avg = avg / com.getPoints().size();
        for(Point p : arr) {
            avg2 = avg2 + p.getY();
        }
        avg2 = avg2 / arr.length;
        //avg2 = avg2 + Math.abs(min);
        avg2 = avg2 - min;
        //avg2 = avg2 / arr.length;

        place = avg2 / (max2 - min2);

        double r,g,b;

        r = (place * cc.getRed());
        g = (place * cc.getGreen());
        b = (place * cc.getBlue());
        if(r > 255 || r < 0) {
            r = 255;
        }
        if(g > 255 || g < 0) {
            g = 255;
        }
        if(b > 255 || b < 0) {
            b = 255;
        }
        //System.out.println("r:" + (int)r + " g:" + (int)g + " b:" + (int)b);
        c = new Color((int)r,(int)g,(int)b);
        //System.out.println(avg2);
        //System.out.println("r:" + (int)r + " g:" + (int)g + " b:" + (int)b);
        //System.out.println(c.toString());


        xarr = new int[arr.length];
        yarr = new int[arr.length];

        */


    }

    void updateFront() {
        front = (int)arr[0].getcz();

        for(int i = 1; i < arr.length; i++) {
            front = front + (int)arr[i].getcz();
        }
        front = front / arr.length;
    }

    void update() {

//        double avg = 0;
//        double avg2 = 0;
//        double place = 0;
//        //int count = 0;
//        double min = 0;
//        double max = 0;
//        double max2,min2;
//        for(Point p : com.getPoints()) {
//            avg = avg + p.getY();
//            if(p.getY() < min) {
//                min = p.getY();
//            }
//            if(p.getY() > max) {
//                max = p.getY();
//            }
//        }
//        //max = max + Math.abs(min);
//        //min = min + Math.abs(min);
//        max2 = max - min;
//        min2 = min - min;
//        //avg = avg + min;
//        //avg = avg / com.getPoints().size();
//        for(Point p : arr) {
//            avg2 = avg2 + p.getY();
//        }
//        avg2 = avg2 / arr.length;
//        //avg2 = avg2 + Math.abs(min);
//        avg2 = avg2 - min;
//        //avg2 = avg2 / arr.length;
//
//        place = avg2 / (max2 - min2);
//
//        double r,g,b;
//
//        r = (place * cc.getRed());
//        g = (place * cc.getGreen());
//        b = (place * cc.getBlue());
//        if(r > 255 || r < 0) {
//            r = 255;
//        }
//        if(g > 255 || g < 0) {
//            g = 255;
//        }
//        if(b > 255 || b < 0) {
//            b = 255;
//        }
//        //System.out.println("r:" + (int)r + " g:" + (int)g + " b:" + (int)b);
//        c = new Color((int)r,(int)g,(int)b);
//        //System.out.println(avg2);
//        //System.out.println("r:" + (int)r + " g:" + (int)g + " b:" + (int)b);
//        //System.out.println(c.toString());
//

        //System.out.println(front);

        xarr = new int[arr.length];
        yarr = new int[arr.length];



        double[] temp = new double[3];
//        temp[0] = (arr[0].getcy() - arr[1].getcy()) * (arr[0].getcz() - arr[2].getcz()) - (arr[0].getcz() - arr[1].getcz()) * (arr[0].getcy() - arr[2].getcy());
//        temp[1] = (arr[0].getcz() - arr[1].getcz()) * (arr[0].getcx() - arr[2].getcx()) - (arr[0].getcx() - arr[1].getcx()) * (arr[0].getcz() - arr[2].getcz());
//        temp[2] = (arr[0].getcx() - arr[1].getcx()) * (arr[0].getcy() - arr[2].getcy()) - (arr[0].getcy() - arr[1].getcy()) * (arr[0].getcx() - arr[2].getcx());

        temp[0] = (arr[0].getY() - arr[1].getY()) * (arr[0].getZ() - arr[2].getZ()) - (arr[0].getZ() - arr[1].getZ()) * (arr[0].getY() - arr[2].getY());
        temp[1] = (arr[0].getZ() - arr[1].getZ()) * (arr[0].getX() - arr[2].getX()) - (arr[0].getX() - arr[1].getX()) * (arr[0].getZ() - arr[2].getZ());
        temp[2] = (arr[0].getX() - arr[1].getX()) * (arr[0].getY() - arr[2].getY()) - (arr[0].getY() - arr[1].getY()) * (arr[0].getX() - arr[2].getX());


//        temp[0] = (arr[0].getY() - arr[1].getY()) * (arr[0].getZ() - arr[2].getZ()) - (arr[0].getZ() - arr[1].getZ()) * (arr[0].getY() - arr[2].getY());
//        temp[1] = (arr[0].getZ() - arr[1].getZ()) * (arr[0].getX() - arr[2].getX()) - (arr[0].getX() - arr[1].getX()) * (arr[0].getZ() - arr[2].getZ());
//        temp[2] = (arr[0].getX() - arr[1].getX()) * (arr[0].getY() - arr[2].getY()) - (arr[0].getY() - arr[1].getY()) * (arr[0].getX() - arr[2].getX());
        normal = temp;

        double l = Math.sqrt(normal[0]*normal[0] + normal[1]*normal[1] + normal[2] * normal[2]);
        normal[0] = normal[0] / l;
        normal[1] = normal[1] / l;
        normal[2] = normal[2] / l;

        for(int i = 0; i < arr.length; i++) {

            //xarr[i] = (int)arr[i].px;
            //yarr[i] = (int)arr[i].py;

            //(int)(w.getFrame().getWidth()/2) + px - (int)radius/2), (int) ((int)(w.getFrame().getHeight()/2) - py - (int)radius/2)

            xarr[i] = (int)((arr[0].getWindow().getFrame().getWidth()/2) + arr[i].px);
            yarr[i] = (int) ((int)(arr[0].getWindow().getFrame().getHeight()/2) - arr[i].py);

        }
        setColor();
    }

    public void draw(Graphics g) {
        //update();
        g.setColor(c);
        g.fillPolygon(xarr,yarr,arr.length);
    }

}
