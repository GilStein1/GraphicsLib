package environments;

import java.awt.*;

public abstract class Shape2D implements Component2D{

    double w;
    double h;
    double x;
    double y;
    Color c;
    Window window;

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(double width, double height) {
        this.w = width;
        this.h = height;
    }
    public void setColor(Color color) {
        this.c = color;
    }
    public Color getColor() {
        return c;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return w;
    }
    public double getHeight() {
        return h;
    }
    public Window getWindow() {
        return window;
    }


}
