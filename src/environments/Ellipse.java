package environments;

import java.awt.*;

public class Ellipse extends Shape2D{

    private boolean fill = true;
    @Override
    public void draw(Graphics g) {
        g.setColor(c);

        if(fill) {
            g.fillOval((int) ((int)(window.getFrame().getWidth()/2) + x - (int)w/2), (int) ((int)(window.getFrame().getHeight()/2) - y - (int)h/2), (int) w, (int) h);
        }
        else {
            g.drawOval((int) ((int)(window.getFrame().getWidth()/2) + x - (int)w/2), (int) ((int)(window.getFrame().getHeight()/2) - y - (int)h/2), (int) w, (int) h);
        }

    }

    @Override
    public void add() {
        window.add((Component2D) this);
    }

    public Ellipse(Window window, double x, double y, double width, double height, Color color) {
        this.window = window;
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.c = color;
        add();
    }
    public void fillOval(boolean is) {
        this.fill = is;
    }
}
