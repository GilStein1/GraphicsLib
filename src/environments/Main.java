package environments;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {

        Window w = new Window( Color.WHITE,900,600,"Toucan");

        w.showPoints(false);
        w.setPerspective(true);
        w.addBasicMovements();

//        double[][] arr = new double[100][100];
//
//        for(int i = 0; i < arr.length; i++) {
//            for(int j = 0; j < arr[i].length; j++) {
//                //arr[i][j] = (Math.cos((i-50)/2.0) + Math.cos((j-50)/2.0))*3;
//                //arr[i][j] = w.generateNoise(i/2.0,j/2.0,0.1)*30;
//                arr[i][j] = (Math.pow((i-50)/2.0,2) + Math.pow((j-50)/2.0,2))/-20.0;
//            }
//        }

        //Component m = new ObjModel(w,"src//mouse.obj","src//mouse - texture.png",new Location(0,0,0), 100,100,100,false);
        Component m = new ObjModel(w,"src//toucan_mat_toucan_0.obj","src//texture_2.png",new Location(0,-20,-400), 100,100,100,false);

        m.setLoadFacesThatDoNotFaceTheCamera(false);

        m.setRotationY(135);

        w.setKeyAction(KeyEvent.VK_E, () -> {
            m.setRotationY(m.getRotation().yRotation - 5);
            //System.out.println(m.getRotation().yRotation);
        });
        w.setKeyAction(KeyEvent.VK_Q, () -> {
            m.setRotationY(m.getRotation().yRotation + 5);
            //System.out.println(m.getRotation().yRotation);
        });
        w.setKeyAction(KeyEvent.VK_R, () -> {
            m.setRotationX(m.getRotation().xRotation - 5);
            //System.out.println(m.getRotation().xRotation);
        });
        w.setKeyAction(KeyEvent.VK_F, () -> {
            m.setRotationX(m.getRotation().xRotation + 5);
            //System.out.println(m.getRotation().xRotation);
        });
        w.setKeyAction(KeyEvent.VK_ENTER, () -> {
            w.setPerspective(!w.getPerspective());
        });
        w.setKeyAction(KeyEvent.VK_LEFT, () -> {
            w.setCameraYRotation(w.cameraYRotation + 5);
            //System.out.println(w.cameraYRotation);
        });
        w.setKeyAction(KeyEvent.VK_RIGHT, () -> {
            w.setCameraYRotation(w.cameraYRotation - 5);
            //System.out.println(w.cameraYRotation);
        });
        w.setKeyAction(KeyEvent.VK_UP, () -> {
            w.setCameraXRotation(w.cameraXRotation + 5);
            //System.out.println(w.cameraYRotation);
        });
        w.setKeyAction(KeyEvent.VK_DOWN, () -> {
            w.setCameraXRotation(w.cameraXRotation - 5);
            //System.out.println(w.cameraYRotation);
        });
    }
}