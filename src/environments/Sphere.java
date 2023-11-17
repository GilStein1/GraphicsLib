package environments;

import java.awt.*;

public class Sphere extends Shape implements Component{

    double r;
    double[][][] side;

    public Sphere(Window window, Location location, double Radius, double[][][] sides, Color color) {

        this.w = window;
        this.loc = location;
        this.r = Radius;
        this.side = sides;
        this.c = color;
        double size = Math.sqrt(2*r*r);
        size = 2*r;
        double[][] round = new double[side[0].length][side[0][0].length];
        double[][] round1 = new double[side[0].length][side[0][0].length];
        double[][] round2 = new double[side[0].length][side[0][0].length];
        double[][] round3 = new double[side[0].length][side[0][0].length];
        double[][] round4 = new double[side[0].length][side[0][0].length];
        double[][] round5 = new double[side[0].length][side[0][0].length];
        double[][] round6 = new double[side[0].length][side[0][0].length];

        for(int i = 0; i < round.length; i++) {
            for(int j = 0; j < round[0].length; j++) {

                round[j][i] = Math.sqrt(r*r - Math.pow(loc.x - size/2 + size*i/round[0].length,2) - Math.pow(loc.x - size/2 + size*j/round[0].length,2)) + loc.y ;

                //round[j][i] = 0;

                //round[j][i] = Math.sqrt(r*r - (size/2 - i/round.length * size - loc.z) * (size/2 - i/round.length * size - loc.z) - (size/2 - j/round[0].length * size - loc.x)*(size/2 - j/round[0].length * size - loc.x)) + loc.y;
            }
        }

//        for(int i = 0; i < round.length; i++) {
//            for(int j = 0; j < round[0].length; j++) {
//                round1[j][i] = round[j][i] + side[0][j][i];
//            }
//        }
//        for(int i = 0; i < round.length; i++) {
//            for(int j = 0; j < round[0].length; j++) {
//                round2[j][i] = round[j][i] + side[1][j][i];
//            }
//        }
//        for(int i = 0; i < round.length; i++) {
//            for(int j = 0; j < round[0].length; j++) {
//                round3[j][i] = round[j][i] + side[2][j][i];
//            }
//        }
//        for(int i = 0; i < round.length; i++) {
//            for(int j = 0; j < round[0].length; j++) {
//                round4[j][i] = round[j][i] + side[3][j][i];
//            }
//        }
//        for(int i = 0; i < round.length; i++) {
//            for(int j = 0; j < round[0].length; j++) {
//                round5[j][i] = round[j][i] + side[4][j][i];
//            }
//        }
//        for(int i = 0; i < round.length; i++) {
//            for(int j = 0; j < round[0].length; j++) {
//                round6[j][i] = round[j][i] + side[5][j][i];
//            }
//        }

        Component m1 = new Mesh(w,new Location(loc.x,loc.y + r,loc.z), size, round1, size, c);

        Component m2 = new Mesh(w,new Location(loc.x,loc.y - r,loc.z), size, round2, size, c);

        Component m3 = new Mesh(w,new Location(loc.x,loc.y,loc.z + r), size, round3, size, c);

        Component m4 = new Mesh(w,new Location(loc.x,loc.y,loc.z - r), size, round4, size, c);

        Component m5 = new Mesh(w,new Location(loc.x + r ,loc.y,loc.z), size, round5, size, c);

        Component m6 = new Mesh(w,new Location(loc.x - r,loc.y,loc.z), size, round6, size, c);


        //m1.setRotationY(-90);
        m2.setRotationX(180);
        m3.setRotationX(90);
        m4.setRotationX(-90);
        m4.setRotationZ(90);
        m5.setRotationZ(90);
        m5.setRotationY(180);
        m6.setRotationZ(-90);
        m6.setRotationY(180);
        //m6.setRotationX(90);

        Component g1 = new Group(w, m1, m2);
        g1 = new Group(w, g1, m3);
        g1 = new Group(w, g1, m4);
        g1 = new Group(w, g1, m5);
        g1 = new Group(w, g1, m6);




        /*
        w.remove(m1);
        w.remove(m2);
        w.remove(m3);
        w.remove(m4);
        w.remove(m5);
        w.remove(m6);
        */
        //m5.setRotationY(-90);
        //m6.setRotationY(-90);
        //m6.setRotationX(180);

        this.pos = g1.getPos();
        this.pointList = g1.getPoints();
        this.lines = g1.getLines();
        double x,y,z;
        for(Location l : pos) {

            x = Math.sqrt((r*r*(l.x*l.x + l.z*l.z))/(l.y*l.y + l.x*l.x + l.z*l.z)) * l.x/Math.sqrt(l.x*l.x + l.z*l.z);

            y = Math.sqrt((r*r*(l.y*l.y + l.z*l.z))/(l.y*l.y + l.x*l.x + l.z*l.z)) * l.y/Math.sqrt(l.y*l.y + l.z*l.z);

            //x = x * (l.x/Math.abs(l.x));

            z = Math.sqrt((r*r*(l.z*l.z + l.x*l.x))/(l.y*l.y + l.z*l.z + l.x*l.x)) * l.z/Math.sqrt(l.x*l.x + l.z*l.z);

            //z = z * (l.z/Math.abs(l.z));



            l.x = x;
            l.z = z;
            l.y = y;

        }

        for(int i = 0; i < pos.length; i++) {
            pointList.get(i).setX(pos[i].x);
            pointList.get(i).setY(pos[i].y);
            pointList.get(i).setZ(pos[i].z);
        }

        this.setRotationX(0);
        this.sortFace();


        /*
        for(int i = 0; i < shape1.getPoints().size(); i++) {
            pointList.add(shape1.getPoints().get(i));
        }
        for(int i = 0; i < shape2.getPoints().size(); i++) {
            pointList.add(shape2.getPoints().get(i));
        }
        loc.x = (shape1.getLocation().x + shape2.getLocation().x)/2;
        loc.y = (shape1.getLocation().y + shape2.getLocation().y)/2;
        loc.z = (shape1.getLocation().z + shape2.getLocation().z)/2;

        pos = new Location[shape1.getPoints().size() + shape2.getPoints().size()];
        lines = new Line[shape1.getLines().length + shape2.getLines().length];

        for(int i = 0; i < pos.length; i++) {
            pos[i] = new Location(0,0,0);
        }
        for(int i = 0; i < lines.length; i++) {
            lines[i] = new Line();
        }

        for(int i = 0; i < shape1.getPoints().size(); i++) {
            pos[i].x = shape1.getPos()[i].x - (loc.x - shape1.getLocation().x);
            pos[i].y = shape1.getPos()[i].y - (loc.y - shape1.getLocation().y);
            pos[i].z = shape1.getPos()[i].z - (loc.z - shape1.getLocation().z);
            //lines[i].p1 = shape1.getLines()[i].p1;
            //lines[i].p2 = shape1.getLines()[i].p2;
        }

        for(int i = 0; i < shape2.getPoints().size(); i++) {
            pos[i + shape1.getPoints().size()].x = shape2.getPos()[i].x - (loc.x - shape2.getLocation().x);
            pos[i + shape1.getPoints().size()].y = shape2.getPos()[i].y - (loc.y - shape2.getLocation().y);
            pos[i + shape1.getPoints().size()].z = shape2.getPos()[i].z - (loc.z - shape2.getLocation().z);
            //lines[i + shape1.getPoints().size()] = shape2.getLines()[i];
        }
        for(int i = 0; i < shape1.getLines().length; i++) {
            lines[i] = shape1.getLines()[i];
        }
        for(int i = 0; i < shape2.getLines().length; i++) {
            lines[i + shape1.getLines().length ] = shape2.getLines()[i];
        }

        add();

        setRotationY(30);
        setRotationY(0);
        */

    }

    @Override
    public void add() {
        w.add(this);
    }

    @Override
    public void addPoint(Point p) {
        System.out.println("Not a NewShape");
    }

}
