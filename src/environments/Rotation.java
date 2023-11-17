package environments;

public class Rotation {

    public double xRotation,yRotation,zRotation;

    public Rotation(double xRotation, double yRotation, double zRotation){
        this.xRotation = xRotation;
        this.yRotation = yRotation;
        this.zRotation = zRotation;
    }

    public Rotation(Rotation rot){
        this.xRotation = rot.xRotation;
        this.yRotation = rot.yRotation;
        this.zRotation = rot.zRotation;
    }

    public void setRotation(Rotation rot){
        this.xRotation = rot.xRotation;
        this.yRotation = rot.yRotation;
        this.zRotation = rot.zRotation;
    }

    public void setRotation(double xRotation, double yRotation, double zRotation){
        this.xRotation  = xRotation;
        this.yRotation  = yRotation;
        this.zRotation  = zRotation;
    }
}
