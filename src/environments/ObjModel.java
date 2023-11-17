package environments;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ObjModel extends Shape implements Component{

    String p;
    double W,H,D;
    private Location Loc2;

    java.util.List<Location> pos2 = new ArrayList<>();


    public ObjModel(Window window, String path,String texturePath, Location location, double width, double height, double depth,boolean rescale) {

        
        
        BufferedImage image = null;

        try {
            File imageFile = new File(texturePath);
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        java.util.List<Color> colors = new ArrayList<>();

        this.p = path;
        this.w = w;
        this.Loc2 = location;
        this.w = window;
        this.c = new Color(0,0,0);
        lines = new Line[0];
        this.W = width;
        this.H = height;
        this.D = depth;
        boolean spaces = true;
        boolean five = false;
        this.loc = new Location(0,0,0);
        //(BufferedReader br = new BufferedReader(new FileReader(p)))

        InputStream inputStream = ObjModel.class.getResourceAsStream(p);

        try (BufferedReader br = new BufferedReader(new FileReader(p))) {
            String line;
            ArrayList<Point> currentPolygonVertices = new ArrayList<>();
            int count = 0;
            while ((line = br.readLine()) != null) {
                // Parse the line and extract the relevant data
                if (line.startsWith("v ")) {

                    String[] parts = line.split("\\s+");
                    float x = Float.parseFloat(parts[1]);
                    float y = Float.parseFloat(parts[2]);
                    float z = Float.parseFloat(parts[3]);
                    // Process vertex data
                    if(x == 0f) {
                        x = 0.01f;
                    }
                    if(y == 0f) {
                        y = 0.01f;
                    }
                    if(z == 0f) {
                        z = 0.01f;
                    }

                    pos2.add(new Location((double)x,(double)y,(double)z));
                    pointList.add(new Point(w, new Location((double)x + loc.x,(double)y + loc.y,(double)z + loc.z),c));


                } else if (line.startsWith("vn ")) {

                } else if (line.startsWith("f ")) {

                    String[] parts = line.split("\\s+");
                    currentPolygonVertices.clear();

                    // Start from index 1 since the first element is "f"
                    for (int i = 1; i < parts.length; i++) {
                        String[] vertexData = parts[i].split("/");

                        // Parse the vertex index (the first part)
                        int vertexIndex = Integer.parseInt(vertexData[0]) - 1;
                        currentPolygonVertices.add(pointList.get(vertexIndex));
                    }

                    Point[] temp = new Point[currentPolygonVertices.size()];
                    for(int i = 0; i < currentPolygonVertices.size(); i++) {
                        temp[i] = currentPolygonVertices.get(i);
                    }

                    // Create a new Polygon and add it to the ArrayList
                    facelist.add(new Face(this,temp,c));
                    // Process face data
                } else if (line.startsWith("vt ")) {
                    // Process texture coordinate data (optional)

                    String[] parts = line.split("\\s+");

                    //System.out.println(parts[0]);

                    //pointList.get(count).textureCoords = new double[]{((Double.parseDouble(parts[1]) + 1.0)/2.0)*image.getWidth(),((Double.parseDouble(parts[2]) + 1.0)/2.0)*image.getHeight()};

                    pointList.get(count).textureCoords = new double[]{((Double.parseDouble(parts[1])))*image.getWidth(),(Math.abs(Double.parseDouble(parts[2])))*image.getHeight()};

                    //int c = image.getRGB((int)(Double.parseDouble(parts[0])*image.getWidth()),(int)(Double.parseDouble(parts[1])*image.getWidth()));

                    //colors.add(new Color((c >> 16) & 0xFF,c >> 8 & 0xFF,c & 0xFF));

                    count++;

                }
                else if (line.startsWith("mtllib ")) {
                    // Process material library (optional)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        pos = new Location[pos2.size()];


        double minx = pointList.get(0).getX();
        double maxx = pointList.get(0).getX();
        double miny = pointList.get(0).getY();
        double maxy = pointList.get(0).getY();
        double minz = pointList.get(0).getZ();
        double maxz = pointList.get(0).getZ();

        for(Point p : pointList) {
            if(p.getZ() > maxz) {

                maxz = p.getZ();

            }
            else if(p.getZ() < minz) {

                minz = p.getZ();

            }

            if(p.getY() > maxy) {

                maxy = p.getY();

            }
            else if(p.getY() < miny) {

                miny = p.getY();

            }

            if(p.getX() > maxx) {

                maxx = p.getX();

            }
            else if(p.getX() < minx) {

                minx = p.getX();

            }
        }

        double xavg = 0;
        double yavg = 0;

        double Xg,Xk,Yg,Yk;

        Xg = 0;
        Xk = 0;
        Yg = 0;
        Yk = 0;

        int col;

        for(int i = 0; i < facelist.size(); i++) {

//            for(Point p : facelist.get(i).arr) {
//                Xg = Math.max(Xg,p.textureCoords[0]);
//                Xk = Math.min(Xk,p.textureCoords[0]);
//
//                Yg = Math.max(Yg,p.textureCoords[1]);
//                Yk = Math.min(Yk,p.textureCoords[1]);
//            }
//
//            double[][] coords = new double[facelist.get(i).arr.length][2];
//
//            for(int l = 0; l < coords.length; l++) {
//                coords[l] = facelist.get(i).arr[l].textureCoords;
//            }
//
//            //System.out.println("done 1 part");
//
//            double red = 0;
//            double green = 0;
//            double blue = 0;
//            int countColors = 0;
//
//            for(int x = (int)Xk; x < (int)Xg; x++) {
//                for(int y = (int)Yk; y < (int)Yg; y++) {
//                    if(checkIfInPolygon(new double[]{x,y},coords)) {
//                        //System.out.println("it is in polygon");
//                        countColors++;
//                        red += (image.getRGB(x,y) >> 16) & 0xFF;
//                        green += (image.getRGB(x,y) >> 8) & 0xFF;
//                        blue += (image.getRGB(x,y)) & 0xFF;
//                    }
//                }
//            }
//            //System.out.println("done 2 part");
//
//            //System.out.println("(" + red + "," + green + "," + blue + ")");
//
//            red = red/countColors;
//            green = green/countColors;
//            blue = blue/countColors;
//
//            facelist.get(i).cc = new Color((int)red,(int)green,(int)blue);



            xavg = 0;
            yavg = 0;

            for(Point p :facelist.get(i).arr) {
                xavg += p.textureCoords[0];
                yavg += p.textureCoords[1];
            }
            xavg = xavg/facelist.get(i).arr.length;
            yavg = yavg/facelist.get(i).arr.length;

            //System.out.println("(" + xavg + "," + yavg + ")");

            col = image.getRGB((int)xavg,(int)yavg);

            facelist.get(i).cc = new Color((col >> 16) & 0xFF,col >> 8 & 0xFF,col & 0xFF);

            //facelist.get(i).cc = colors.get(i);
        }

//        System.out.println("done");

        maxx = maxx - minx;
        //minx = 0;
        maxy = maxy - miny;
        //miny = 0;
        maxz = maxz - minz;
        //minz = 0;

        double x = width/maxx;
        double y = height/maxy;
        double z = depth/maxz;

        if(rescale) {
            for(Location l : pos2) {
                l.setLocation(l.x*x,l.y*y,l.z*z);
            }
        }
        else {
            for(Location l : pos2) {

                l.setLocation(l.x*x,l.y*x,l.z*x);
            }
        }



        for(int i = 0; i < pos2.size(); i++) {
            pos[i] = pos2.get(i);
        }

        for(int i = 0; i < pos2.size(); i++) {
            pointList.get(i).setX(pos2.get(i).x + loc.x);
            pointList.get(i).setY(pos2.get(i).y + loc.y);
            pointList.get(i).setZ(pos2.get(i).z + loc.z);
        }

        add();
//        this.setRotationX(0);
//        this.setRotationY(0);
//        this.setRotationZ(0);

        double X = 0;
        double Y = 0;
        double Z = 0;


        for(Point p : pointList) {

            x += p.getX();
            y += p.getY();
            z += p.getZ();

        }

        x /= pointList.size();
        y /= pointList.size();
        z /= pointList.size();

        for(Location loc2 : pos) {
            loc2.setLocation(loc2.x - x, loc2.y - y, loc2.z - z);
        }
        for(int i = 0; i < pointList.size(); i++) {
            pointList.get(i).setX(pos[i].x + loc.x);
            pointList.get(i).setY(pos[i].y + loc.y);
            pointList.get(i).setZ(pos[i].z + loc.z);
        }

        this.setX(Loc2.x);
        this.setY(Loc2.y);
        this.setZ(Loc2.z);

        this.setRotationX(0.5);
        this.setRotationY(0.5);
        this.setRotationZ(0.5);
        this.setRotationX(0);
        this.setRotationY(0);
        this.setRotationZ(0);


    }
    public ObjModel(Window window, String path, Location location, double width, double height, double depth,boolean rescale, Color color) {
        this.p = path;
        this.w = w;
        this.Loc2 = location;
        this.w = window;
        this.c = color;
        lines = new Line[0];
        this.W = width;
        this.H = height;
        this.D = depth;
        boolean spaces = true;
        boolean five = false;
        this.loc = new Location(0,0,0);
        //(BufferedReader br = new BufferedReader(new FileReader(p)))

        InputStream inputStream = ObjModel.class.getResourceAsStream(p);

        try (BufferedReader br = new BufferedReader(new FileReader(p))) {
            String line;
            ArrayList<Point> currentPolygonVertices = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                // Parse the line and extract the relevant data
                if (line.startsWith("v ")) {

                    String[] parts = line.split("\\s+");
                    float x = Float.parseFloat(parts[1]);
                    float y = Float.parseFloat(parts[2]);
                    float z = Float.parseFloat(parts[3]);
                    // Process vertex data
                    if(x == 0f) {
                        x = 0.01f;
                    }
                    if(y == 0f) {
                        y = 0.01f;
                    }
                    if(z == 0f) {
                        z = 0.01f;
                    }

                    pos2.add(new Location((double)x,(double)y,(double)z));
                    pointList.add(new Point(w, new Location((double)x + loc.x,(double)y + loc.y,(double)z + loc.z),c));


                } else if (line.startsWith("vn ")) {

                } else if (line.startsWith("f ")) {

                    String[] parts = line.split("\\s+");
                    currentPolygonVertices.clear();

                    // Start from index 1 since the first element is "f"
                    for (int i = 1; i < parts.length; i++) {
                        String[] vertexData = parts[i].split("/");

                        // Parse the vertex index (the first part)
                        int vertexIndex = Integer.parseInt(vertexData[0]) - 1;
                        currentPolygonVertices.add(pointList.get(vertexIndex));
                    }

                    Point[] temp = new Point[currentPolygonVertices.size()];
                    for(int i = 0; i < currentPolygonVertices.size(); i++) {
                        temp[i] = currentPolygonVertices.get(i);
                    }

                    // Create a new Polygon and add it to the ArrayList
                    facelist.add(new Face(this,temp,c));
                    // Process face data
                } else if (line.startsWith("vt ")) {
                    // Process texture coordinate data (optional)
                } else if (line.startsWith("mtllib ")) {
                    // Process material library (optional)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        pos = new Location[pos2.size()];


        double minx = pointList.get(0).getX();
        double maxx = pointList.get(0).getX();
        double miny = pointList.get(0).getY();
        double maxy = pointList.get(0).getY();
        double minz = pointList.get(0).getZ();
        double maxz = pointList.get(0).getZ();

        for(Point p : pointList) {
            if(p.getZ() > maxz) {

                maxz = p.getZ();

            }
            else if(p.getZ() < minz) {

                minz = p.getZ();

            }

            if(p.getY() > maxy) {

                maxy = p.getY();

            }
            else if(p.getY() < miny) {

                miny = p.getY();

            }

            if(p.getX() > maxx) {

                maxx = p.getX();

            }
            else if(p.getX() < minx) {

                minx = p.getX();

            }
        }

        maxx = maxx - minx;
        //minx = 0;
        maxy = maxy - miny;
        //miny = 0;
        maxz = maxz - minz;
        //minz = 0;

        double x = width/maxx;
        double y = height/maxy;
        double z = depth/maxz;

        if(rescale) {
            for(Location l : pos2) {
                l.setLocation(l.x*x,l.y*y,l.z*z);
            }
        }
        else {
            for(Location l : pos2) {

                l.setLocation(l.x*x,l.y*x,l.z*x);
            }
        }



        for(int i = 0; i < pos2.size(); i++) {
            pos[i] = pos2.get(i);
        }

        for(int i = 0; i < pos2.size(); i++) {
            pointList.get(i).setX(pos2.get(i).x + loc.x);
            pointList.get(i).setY(pos2.get(i).y + loc.y);
            pointList.get(i).setZ(pos2.get(i).z + loc.z);
        }

        add();
//        this.setRotationX(0);
//        this.setRotationY(0);
//        this.setRotationZ(0);

        double X = 0;
        double Y = 0;
        double Z = 0;


        for(Point p : pointList) {

            x += p.getX();
            y += p.getY();
            z += p.getZ();

        }

        x /= pointList.size();
        y /= pointList.size();
        z /= pointList.size();

        for(Location loc2 : pos) {
            loc2.setLocation(loc2.x - x, loc2.y - y, loc2.z - z);
        }
        for(int i = 0; i < pointList.size(); i++) {
            pointList.get(i).setX(pos[i].x + loc.x);
            pointList.get(i).setY(pos[i].y + loc.y);
            pointList.get(i).setZ(pos[i].z + loc.z);
        }

        this.setX(Loc2.x);
        this.setY(Loc2.y);
        this.setZ(Loc2.z);

        this.setRotationX(0.5);
        this.setRotationY(0.5);
        this.setRotationZ(0.5);
        this.setRotationX(0);
        this.setRotationY(0);
        this.setRotationZ(0);

    }
    public static boolean checkIfInPolygon(double[] xy, double[][] points) {

        double[] m = new double[points.length];
        double[] bt = new double[points.length];

        //boolean is = true;

        for(int i = 0; i < m.length; i++) {
            m[i] = (points[i][1] - points[(i + 1)% points.length][1])/(points[i][0] - points[(i + 1)% points.length][0]);
            bt[i] = points[i][1] - m[i]*points[i][0];
        }

        int count = 0;

        for(int i = 0; i < points.length; i++) {

            double x = (xy[1] - bt[(i + 1)% points.length])/m[(i + 1)% points.length];

            if(((xy[1] < Math.max(points[i][1], points[(i + 1)% points.length][1])) && (xy[1] > Math.min(points[i][1], points[(i + 1)% points.length][1]))) && ((x < Math.max(points[i][0], points[(i + 1)% points.length][0])) && (x > Math.min(points[i][0], points[(i + 1)% points.length][0])))) {
                count++;
            }
        }

        //System.out.println("count = " + count);

        return !(count%2 == 0);
    }


    @Override
    public void add() {
        w.add(this);
    }
}
