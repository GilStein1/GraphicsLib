package environments;

import java.awt.*;

public class Pyramid extends Shape implements Component {

    public Pyramid(Window window, Location location, Rotation rotation,double width, double height, double depth, Color color) {

        this.loc = location;
        this.rot = rotation;
        this.w = window;
        this.c = color;
        this.pos = new Location[5];

        setShapePointsLocationAndAddRelativityToPosArray(width, depth, height);

        this.add(); // adds to shapeList
    }

    public Pyramid(Window window, Location location,double width, double height, double depth, Color color) {

        this.loc = location;
        this.w = window;
        this.c = color;
        this.pos = new Location[5];

        setShapePointsLocationAndAddRelativityToPosArray(width, depth, height);

        this.add(); // adds to shapeList
    }

    public Pyramid(Window window, double x, double y, double z, double rotX, double rotY, double rotZ,double width, double height, double depth, Color color) {

        this.loc.setLocation(x,y,z);
        this.rot.setRotation(rotX, rotY, rotZ);
        this.w = window;
        this.c = color;
        this.pos = new Location[5];

        setShapePointsLocationAndAddRelativityToPosArray(width, depth, height);

        this.add(); // adds to shapeList
    }

    public Pyramid(Window window, double x, double y, double z,double width, double height, double depth, Color color) {

        //this.loc.setLocation(x,y,z);
        this.loc = new Location(x,y,z);
        this.w = window;
        this.c = color;
        this.pos = new Location[5];

        setShapePointsLocationAndAddRelativityToPosArray(width, depth, height);

        this.add(); // adds to shapeList
    }

    private void setShapePointsLocationAndAddRelativityToPosArray(double width, double depth, double height){
        Location cc;
        lines = new Line[8];
        double halfWidth = width / 2;
        double halfDepth = depth / 2;
        double halfHeight = height / 2;

        // Point at top left
        pos[0] = new Location(- halfWidth,- halfHeight,- halfDepth);
        cc = new Location(loc.x - halfWidth ,loc.y - halfHeight ,loc.z - halfDepth);
        pointList.add(new Point(w, cc,c));

        // point at top right
        pos[1] = new Location(halfWidth,- halfHeight,- halfDepth);
        cc = new Location(loc.x + halfWidth ,loc.y - halfHeight,loc.z - halfDepth);
        pointList.add(new Point(w, cc ,c));

        // point at bottom left
        pos[2] = new Location(- halfWidth,- halfHeight,halfDepth);
        cc = new Location(loc.x - halfWidth ,loc.y - halfHeight,loc.z + halfDepth);
        pointList.add(new Point(w, cc ,c));

        // point at bottom right
        pos[3] = new Location(halfWidth, -halfHeight, halfDepth);
        cc = new Location(loc.x + halfWidth ,loc.y - halfHeight,loc.z + halfDepth);
        pointList.add(new Point(w, cc ,c));

        // point at middle, top of shape (pointy)
        pos[4] = new Location(0.1, halfHeight, 0.1);
        cc = new Location(loc.x+0.1, loc.y + halfHeight, loc.z+0.1);
        pointList.add(new Point(w, cc ,c));

        Point[] temp = {pointList.get(2),pointList.get(3),pointList.get(4)};
        facelist.add(new Face(this,temp,c));

        Point[] temp2 = {pointList.get(0),pointList.get(2),pointList.get(4)};
        facelist.add(new Face(this,temp2,c));

        Point[] temp3 = {pointList.get(3),pointList.get(1),pointList.get(4)};
        facelist.add(new Face(this,temp3,c));

        Point[] temp4 = {pointList.get(1),pointList.get(0),pointList.get(4)};
        facelist.add(new Face(this,temp4,c));

        Point[] temp5 = {pointList.get(0),pointList.get(1),pointList.get(3),pointList.get(2)};
        facelist.add(new Face(this,temp5,c));

        lines[0] = new Line(pointList.get(0),pointList.get(1),c);
        lines[1] = new Line(pointList.get(0),pointList.get(2),c);
        lines[2] = new Line(pointList.get(0),pointList.get(4),c);
        lines[3] = new Line(pointList.get(3),pointList.get(1),c);
        lines[4] = new Line(pointList.get(3),pointList.get(2),c);
        lines[5] = new Line(pointList.get(3),pointList.get(4),c);
        lines[6] = new Line(pointList.get(1),pointList.get(4),c);
        lines[7] = new Line(pointList.get(2),pointList.get(4),c);
    }

    @Override
    public void add() {
        w.add(this);
    }
}
