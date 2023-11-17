package environments;

public class NewShape extends Shape implements Component{

    boolean finished = false;

    public NewShape(Window window, Location location) {
        w = window;
        loc = location;
        lines = new Line[0];
        add();
    }

    @Override
    public void addPolygon(Face f) {
        facelist.add(f);
    }
    @Override
    public void addLine(Line l) {
        Line[] line = new Line[lines.length + 1];
        for(int i = 0; i < lines.length; i++) {
            line[i] = lines[i];
        }
        line[lines.length] = l;
        lines = line;
    }
    @Override
    public void addPoint(Point p) {
        p.update();
        if(pos != null) {
            pointList.add(p);
            Location[] pos2 = new Location[pos.length + 1];
            for(int i = 0; i < pos.length; i++) {
                pos2[i] = pos[i];
            }
            pos2[pos.length] = new Location(p.getX()- loc.x, p.getY()- loc.y, p.getZ()- loc.z);
            pos = pos2;
        }
        else {
            pos = new Location[1];
            pos[0] = new Location(p.getX()- loc.x, p.getY()- loc.y, p.getZ()- loc.z);
            pointList.add(p);
        }
        w.update();
    }

    @Override
    public void add() {
        w.add(this);
    }
}
