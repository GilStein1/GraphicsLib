package environments;

import java.awt.*;
import java.awt.image.ImageObserver;

public class ImageLoader extends Shape2D{

    String path;
    Image img;

    public ImageLoader(Window window, String path, double x, double y, double width, double height) {

        this.w = width;
        this.h = height;
        this.x = x;
        this.y = y;
        this.path = path;
        this.window = window;
        MediaTracker tracker = new MediaTracker(window.getFrame());
        this.img = Toolkit.getDefaultToolkit().createImage(path);
        tracker.addImage(this.img, 0);
        add();

    }
    public void draw(Graphics g) {
        g.drawImage(img,(int)x,(int)y,(int)w,(int)h,(ImageObserver)null);
    }
    @Override
    public void setColor(Color color) {
        System.out.println("an image cannot be given a color");
    }
    @Override
    public Color getColor() {
        System.out.println("an image cannot be given a color");
        return null;
    }

    @Override
    public void add() {
        window.add((Component2D) this);
    }

}
