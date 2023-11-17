package environments;

import java.awt.*;

public class Rectangle extends Shape2D{

    private boolean fill = false;
    @Override
    public void draw(Graphics g) {
        g.setColor(c);

        if(fill) {
            g.fillRect((int) ((int)(window.getFrame().getWidth()/2) + x - (int)w/2), (int) ((int)(window.getFrame().getHeight()/2) - y - (int)h/2), (int) w, (int) h);
        }
        else {
            g.drawRect((int) ((int)(window.getFrame().getWidth()/2) + x - (int)w/2), (int) ((int)(window.getFrame().getHeight()/2) - y - (int)h/2), (int) w, (int) h);
        }

    }

    @Override
    public void add() {
        window.add((Component2D) this);
    }

    public Rectangle(Window window, double x, double y, double width, double height, Color color) {
        this.window = window;
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.c = color;
        add();
    }
    public void fillRectangle(boolean is) {
        this.fill = is;
    }
}
