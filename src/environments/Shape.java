package environments;

import java.awt.*;

public abstract class Shape {
    protected Location loc;
    protected Rotation rot = new Rotation(0,0,0);
    protected Window w;
    protected Color c;
    protected boolean loadFacesThatDoNotFaceTheCamera = false;
    protected java.util.List<Point> pointList = new java.util.ArrayList<>();
    java.util.List<Face> facelist = new java.util.ArrayList<>();
    Location[] pos;//added the pos array that represents the position of each point relative to the shape's location
    protected Line[] lines;
    public java.util.List<Point> getPoints() {
        return pointList;
    }


    public Line[] getLines() {
        return lines;
    }
    public void setColor(Color color) {
        this.c = color;
    }
    public void setColor(int red, int green, int blue) {
        this.c = new Color(red,green,blue);
    }
    public Color getColor() {
        return c;
    }
    public Rotation getRotation() {
        return rot;
    }
    public Location getLocation() {
        return loc;
    }
    public Location[] getPos() {
        return pos;
    }
    public java.util.List<Face> getFace() {
        return facelist;
    }
    public void setLoadFacesThatDoNotFaceTheCamera(boolean set) {
        loadFacesThatDoNotFaceTheCamera = set;
    }
    public boolean GetLoadFacesThatDoNotFaceTheCamera() {
        return loadFacesThatDoNotFaceTheCamera;
    }
    public void addPoint(Point p) {
        System.out.println("Not a NewShape");
    }
    public void addLine(Line l) {
        System.out.println("Not a NewShape");
    }
    public void addPolygon(Face f) {
        System.out.println("Not a NewShape");
    }
    public void sortFace() {

        for(Face f : facelist) {
            f.updateFront();
        }

//        Face[] farr = new Face[facelist.size()];
//        for(int i = 0; i < farr.length; i++) {
//            farr[i] = facelist.get(i);
//        }
//        Window.mergeSort(farr);
//
//        facelist = new java.util.ArrayList<>();
//
//        for(int i = 0; i < farr.length; i++) {
//            facelist.add(farr[i]);
//        }

        Window.mergeSort(facelist);

//        System.out.println("start");
//
//        for(Face f : facelist) {
//            System.out.println(f.front);
//        }

        //System.out.println("sorted");


    }

    public void setRotationX(double deg){
        double deg2 = deg - rot.xRotation;
        double ang;
        double y, z, d;

        deg2 = Math.toRadians(deg2);

        for(int i = 0; i < pointList.size();i++) {

            //ang = Math.toDegrees(Math.atan(pos[i].z/pos[i].y));

            ang = Math.atan(pos[i].z/pos[i].y);

            if (pos[i].y < 0 && pos[i].z > 0) {
                ang = Math.PI + ang;
            }
            else if(pos[i].y < 0 && pos[i].z < 0) {
                ang = (Math.PI + ang);
            }

            ang = ang + deg2;

            d = Math.sqrt(pos[i].z*pos[i].z + pos[i].y*pos[i].y);

//            z = d*Math.sin(Math.toRadians(ang));
//            y = d*Math.cos(Math.toRadians(ang));

            z = d*Math.sin(ang);
            y = d*Math.cos(ang);

            pointList.get(i).setZ(z + loc.z);
            pointList.get(i).setY(y + loc.y);
            pos[i].y = y;
            pos[i].z = z;

        }
        rot.xRotation = deg;
        sortFace();
        //w.update();
    }

    public void setRotationY(double deg){
        double deg2 = deg - rot.yRotation;
        double ang;
        double x, z, d;

        deg2 = Math.toRadians(deg2);

        for(int i = 0; i < pointList.size();i++) {

            //ang = Math.toDegrees(Math.atan(pos[i].z/pos[i].x));



            ang = Math.atan(pos[i].z/pos[i].x);

            if (pos[i].x < 0 && pos[i].z > 0) {
                ang = Math.PI + ang;
            }
            else if(pos[i].x < 0 && pos[i].z < 0) {
                ang = (Math.PI + ang);
            }

            ang = ang + deg2;

            d = Math.sqrt(pos[i].z*pos[i].z + pos[i].x*pos[i].x);

//            z = d*Math.sin(Math.toRadians(ang));
//            x = d*Math.cos(Math.toRadians(ang));

            z = d*Math.sin(ang);
            x = d*Math.cos(ang);

            pointList.get(i).setZ(z + loc.z);
            pointList.get(i).setX(x + loc.x);

            pos[i].x = x;
            pos[i].z = z;

        }
        rot.yRotation = deg;
        sortFace();
        //w.update();
    }


    public void setRotationZ(double deg){
        double deg2 = deg - rot.zRotation;
        double ang;
        double x, y, d;

        deg2 = Math.toRadians(deg2);

        for(int i = 0; i < pointList.size();i++) {

            //ang = Math.toDegrees(Math.atan(pos[i].y/pos[i].x));

            ang = Math.atan(pos[i].y/pos[i].x);

            if (pos[i].x < 0 && pos[i].y > 0) {
                ang = Math.PI + ang;
            }
            else if(pos[i].x < 0 && pos[i].y < 0) {
                ang = Math.PI + ang;
            }

            ang = ang + deg2;

            d = Math.sqrt(pos[i].y*pos[i].y + pos[i].x*pos[i].x);

//            y = d*Math.sin(Math.toRadians(ang));
//            x = d*Math.cos(Math.toRadians(ang));

            y = d*Math.sin(ang);
            x = d*Math.cos(ang);

            pointList.get(i).setY(y + loc.y);
            pointList.get(i).setX(x + loc.x);

            pos[i].y = y;
            pos[i].x = x;
        }
        rot.zRotation = deg;
        sortFace();
        //w.update();
    }

    public void setLocation(double x, double y, double z){
        this.loc.setLocation(x,y,z);
    }

    public void setX(double x){
        this.loc.setLocation(x,loc.y, loc.z);
        for (int i = 0; i < pointList.size(); i++) {
            pointList.get(i).setX(x + pos[i].x);
        }
        sortFace();
    }
    public void setY(double y){
        this.loc.setLocation(loc.x,y, loc.z);
        for (int i = 0; i < pointList.size(); i++) {
            pointList.get(i).setY(y + pos[i].y);
        }
        sortFace();
    }
    public void setZ(double z){
        this.loc.setLocation(loc.x,loc.y, z);
        for (int i = 0; i < pointList.size(); i++) {
            pointList.get(i).setZ(z + pos[i].z);
        }
        sortFace();
    }
}
