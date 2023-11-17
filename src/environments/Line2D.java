package environments;

import java.awt.*;

public class Line2D extends Shape2D{


    public Line2D(Window window, double x1, double y1, double x2, double y2, Color color) {
        this.x = x1;
        this.y = y1;
        this.w = x2;
        this.h = y2;
        this.c = color;
        this.window = window;
        add();
    }
    public void setPoints(double x1, double y1, double x2, double y2) {
        this.w = x1;
        this.x = x2;
        this.h = y1;
        this.y = y2;
    }
    public double[][] getPoints() {
        return new double[][]{{x,y},{w,h}};
    }
    @Override
    public double getWidth() {
        return w - x;
    }
    @Override
    public double getHeight() {
        return h - y;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(c);
        g.drawLine((int)x,(int)y,(int)w,(int)h);
    }
    @Override
    public void setSize(double width, double height) {
        w = x + width;
        h = y + height;
    }

    @Override
    public void add() {
        window.add((Component2D) this);
    }
}
