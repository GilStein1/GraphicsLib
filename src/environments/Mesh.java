package environments;

import java.awt.*;

public class Mesh extends Shape implements Component {

    double W = 0;
    double D = 0;
    double[][] H = {{0},{0}};
    java.util.List<Line> lineList = new java.util.ArrayList<>();
    Point[][] arr;

    public Mesh(Window window, Location location, double width, double[][] height, double depth, Color color) {

        W = width;
        this.lines = new Line[0];
        D = depth;
        H = height;
        this.w = window;
        this.c = color;
        this.loc = location;
        arr = new Point[height.length][height[0].length];
        pos = new Location[arr.length * arr[0].length];
        setShapePointsLocationAndAddRelativityToPosArray();
        //w.add(this);
        add();

        setRotationY(30);
        setRotationY(0);
    }

    public void setShapePointsLocationAndAddRelativityToPosArray() {

        int count = 0;

        for(int i = 0; i < H.length ; i++) {
            for(int j = 0; j < H[0].length; j++) {

                arr[i][j] = new Point(w,new Location(loc.x - W/2 + W*i/(H[0].length-1),H[j][i] + loc.y, loc.z - D/2 + D*j/(H.length-1)),c);
                pos[count] = new Location(-W/2 + W*i/(H[0].length-1) + 0.001,H[j][i] + 0.001,-D/2 + D*j/(H.length-1) + 0.001);
                count++;
            }
        }
        /*
        for(Location l : pos) {
            System.out.println(l.x + "," + l.y + "," + l.z);
        }
        */
        for(int i = 0; i < H.length - 1 ; i++) {
            for(int j = 0; j < H[0].length - 1; j++) {

                lineList.add(new Line ((arr[j][i]),(arr[j + 1][i]), c));

                Point[] arr3 = {arr[j][i],arr[j + 1][i],arr[j + 1][i + 1]};

                Point[] arr4 = {arr[j][i],arr[j + 1][i + 1],arr[j][i + 1]};

                facelist.add(new Face(this,arr3,c));
                facelist.add(new Face(this,arr4,c));

                lineList.add(new Line ((arr[j][i]),(arr[j][i + 1]), c));

                lineList.add(new Line ((arr[j][i]),(arr[j + 1][i + 1]), c));

            }
        }



        for(int i = 0; i < H.length ; i++) {
            for(int j = 0; j < H[0].length; j++) {

                pointList.add(arr[j][i]);

            }
        }

        this.lines = new Line[lineList.size()];

        for(int i = 0; i < lines.length; i++) {
            lines[i] = lineList.get(i);
        }

        for(Face f : facelist) {
            f.setColor();
        }

    }


    @Override
    public void add() {
        w.add(this);
    }
}
