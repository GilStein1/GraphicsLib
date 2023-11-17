package environments;

import java.awt.*;

public interface Component2D {

    void draw(Graphics g);
    public void setLocation(double x, double y);
    public void setSize(double width, double height);
    public void setColor(Color color);
    public Color getColor();
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
    public Window getWindow();
    public void add();


}
