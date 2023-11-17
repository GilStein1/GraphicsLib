package environments;

public class Group extends Shape implements Component {

    public Group(Window window, Component shape1, Component shape2) {

        this.w = window;
        loc = new Location(0,0,0);

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
        for(int i = 0; i < shape1.getFace().size(); i++) {
            facelist.add(shape1.getFace().get(i));
        }
        for(int i = 0; i < shape2.getFace().size(); i++) {
            facelist.add(shape2.getFace().get(i));
        }
        sortFace();

        add();

        setRotationY(30);
        setRotationY(0);

        w.remove(shape1);
        w.remove(shape2);
    }

    @Override
    public void add() {
        w.add(this);
    }
}
