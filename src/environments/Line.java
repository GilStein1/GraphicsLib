package environments;

import java.awt.*;

public class Line {

    Point p1,p2;
    Color color;

    Line(Point p1, Point p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
    }
    Line() {

    }

    void draw(Graphics g) {
        double width = p1.getWindow().getFrame().getWidth();
        double height = p1.getWindow().getFrame().getHeight();

        g.setColor(color);
        g.drawLine((int)((width/2) + p1.px),(int)((height/2) - p1.py),(int)((width/2) + p2.px),(int)((height/2) - p2.py));

    }
}
