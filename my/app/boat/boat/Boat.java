//Boat.java

package my.app.boat.boat;

//import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics.*;
import java.awt.Shape;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import my.app.App;
import my.app.environment.Cube;
import my.app.environment.Environment;
import my.app.mass.Mass;
//import my.app.size.Location;
import my.app.size.Size;
import static my.app.utilities.Utils.pad;
import static my.app.utilities.Utils.print;
import static my.app.utilities.Utils.println;
import my.app.vector3.Vector3;
import my.app.water.Water;

//other imports
//import ruffner.peter.*;
//import ruffner.peter.WaterPkg.*;
//import ruffner.peter.WaterPkg.SailboatPkg.*;



//*f = )
//Arc2D.Double(float x, float y, float w, float h, float start, float extent, int type)

//public class Sailboat extends GeneralPath implements Shape {
//public class Sailboat extends Object implements Shape {
public class Boat extends Mass
{
    static final float DEG090 = (float)Math.toRadians( 90f);
    static final float DEG180 = (float)Math.toRadians(180f);
    static final float DEG270 = (float)Math.toRadians(270f);
    
    protected App app;
	//protected float x, x2;
    //protected Location location = new Location();
    protected float xv, yv;
    //protected Point2D loc = new Point(0, 0);//ints
	//protected Point2D.Float size = new Point2D.Float();
    //protected Point2D.Float location = new Point2D.Float(0f, 0f);//ints

	protected int bowx, bowy;
	protected float rudder = 0f;
    protected Vector3 maximumSpeed = new Vector3(Vector3.TYPE_SIZE);
	//protected float main;
	//protected float jib;
	//protected Boolean splitH = false;
	//protected Boolean splitV = false;
	protected Arc2D myshape;


	public Boat(){
        this(null);
	}
    
    
    public Boat(String name){
        super(name);
    }
    
/*
	public Boat(String name, Vector3 location, Vector3 orientation, Vector3 dimension, float speed, float maxSpeed, Cube world, Water water, Environment env, App app){
        super(name, location, orientation, dimension, world, water, env, app);
        //System.out.println("Boat.Boat() ");
        setSpeed(speed);
        setMaxSpeed(maxSpeed);
	}
*/

	public Boat(String name, Vector3 location, Vector3 orientation, Vector3 dimension, float course, float speed, float maxSpeed, Cube world, Water water, Environment env, App app){
        super(name, location, orientation, dimension, course, speed, maxSpeed, world, water, env, app);
        //System.out.println("Boat.Boat() ");
	}


	//public void moveTo(int nx, int ny){
	//	x = nx;
	//	y = ny;
	//}
    
    
	public void addVelocity(float d){
        Vector3 v = velocity.normalize();
        v = v.multiply(d);
		Vector3 v2 = velocity.add(v);
        if (v2.length() < maxSpeed)
            velocity = v2;
		//System.out.println("x=" + x + "/y=" + y + "/course=" + course + "/rudder=" + rudder + "/speed=" + speed);
	}

    
	public void addVelocity(Vector3 dv){
        Vector3 v = velocity.normalize().multiply(dv);
		Vector3 v2 = velocity.add(v);
        if (v2.length() > maxSpeed)
    		velocity = velocity.add(dv);
        
        //if (velocity.length() > maxSpeed)
            // = maxSpeed;
		//System.out.println("x=" + x + "/y=" + y + "/course=" + course + "/rudder=" + rudder + "/speed=" + speed);
	}

    
    @Override
	public void doTurnRight(){
        //println("Boat.turnRight() ");
		rudder = rudder + 10f;
		if (rudder > 90f){
			rudder = 90f;
		}
        setInput(true);
        println("Boat.turnRight() " + "rudder=" + rudder);
	}

    @Override
	public void doTurnLeft(){
        //println("Boat.turnLeft() ");
		rudder = rudder - 10f;
		if (rudder < -90f){
			rudder = -90f;
		}
        println("Boat.turnLeft() " + "rudder=" + rudder);
        setInput(true);
	}


    //public void adjustCourse(float rudder){
    //    course.adjust(rudder);
    //}
    

    @Override
	public void move(float tpf) {
        move(null, tpf, 0);
    }
    
    
    @Override
	public void move(Cube world, float tpf) {
        move(world, tpf, 0);
    }
    
    
    @Override
    public void move(Cube world, float tpf, int level){
        //println(pad(level) + "Boat.move(f, i) " + "ID=" + ID + ", name=" + getName());
        course = Vector3.validateDegree(course + (rudder * tpf), Vector3.TYPE_DEGREE180);
        super.move(world, tpf, level);
    }
    
    
    public void accelerate(){
        
    }
    
    
    @Override
    public void draw(Graphics g){
        //println("Boat.draw(G) ");
        //super.draw(g);
        draw(null, g, 0);
    }
    
    
    @Override
    public void draw(Cube world, Graphics g){
        //println("Boat.draw(G) ");
        //super.draw(g);
        draw(world, g, 0);
    }
    
    
    @Override
    public void draw(Cube world, Graphics g, int level){
        //println("Boat.draw(G, i) " + "ID=" + ID + ", level=" + level);
        Cube area = null;
        super.draw(world, g, level);
        //println("Boat.draw(G, i) " + "ID=" + ID + ", name=" + name + ", location=" + location.toString(0) + ", velocity=" + velocity + ", children=" + children.size());

        //if ((ID == 0)) 
            //println("Boat.draw(G, i) " + "ID=" + ID + ", name=" + getName() + ", children=" + children.size());

        drawMassB(g, location, Color.white);

        Vector3 loc = null;
        if (world != null) loc = world.scale(0f, 0f, 0f).validate(location);
        if (loc != null){
            //if (ID == 0) println("Boat.draw() (echo) " + "box=" + box.toString(0) + ", location" + location.toString(0) + ", area=" + area.toString(0) + ", pos2=" + pos2.toString(0));
            drawMassB(g, loc, Color.red);
        }
    }//draw1


    //@Override
    public void drawMassB(Graphics g, Vector3 loc, Color color){ 
        println("Boat.drawMassB(g, v, c) " + "ID=" + ID + ", loc=" + loc.toString(0));
        drawHullB(g, loc, color);
        drawRudderB(g, loc, color);
    }

    
	//draw bow
	public void drawHullB(Graphics g, Vector3 loc, Color color){
        //println("Boat.drawHull(g, v, c)1 " + "ID=" + ID + ", loc=" + loc.toString(0) + ", course=" + course + ", dim=" + dimension.toString(0) + "....................");
		int x1, y1, x2, y2;
        float deg = course;
        float rad;

        rad = (float)Math.toRadians(course);

        //if (getName().equals("Sailboat#0")) println("Boat.drawHullB() " + "ID=" + ID + ", location=" + loc + ", course=" + course);
        int mode = 3;
        if (mode == 1){
        }
        else if (mode == 2){
        }
        else if (mode == 3){
            double[] cx = new double[5];
            double[] cy = new double[5];
            
            int[] px = new int[9];
            int[] py = new int[9];

            rad = (float)Math.toRadians(course);
            double cos = Math.cos(rad);
            double sin = Math.sin(rad);
            
            //if (getName().equals("Sailboat#0") && true){
            //    println("Boat.drawHullB() " + "loc=" + loc.toString(0) + "dimension=" + dimension.toString(0) + ", deg=" + deg + ", rad=" + rad + ", cos=" + cos + ", sin=" + sin);
            //}
            //if (getName().equals("Sailboat#0") || getSelected()){
            if (getSelected()){
                //println("Boat.drawHullB() " + "ID=" + ID + ", loc=" + loc.toString(0) + "dimension=" + dimension.toString(0) + ", deg=" + deg + ", rad=" + rad + ", cos=" + cos + ", sin=" + sin);
            }
            cx[0] = (loc.x + (cos * dimension.x *  0.25f));
            cy[0] = (loc.y + (sin * dimension.x *  0.25f));

            cx[1] = (loc.x + (cos * dimension.x *  0.00f));
            cy[1] = (loc.y + (sin * dimension.x *  0.00f));

            cx[2] = (loc.x + (cos * dimension.x * -0.25f));
            cy[2] = (loc.y + (sin * dimension.x * -0.25f));

            cx[3] = (loc.x + (cos * dimension.x * -0.66f));
            cy[3] = (loc.y + (sin * dimension.x * -0.66f));

            cx[4] = (loc.x + (cos * dimension.x * -0.75f));
            cy[4] = (loc.y + (sin * dimension.x * -0.75f));


            //set polygon
            //bow
            px[0] = (int)(cx[0] - (sin * dimension.y * 0.00f));
            py[0] = (int)(cy[0] + (cos * dimension.y * 0.00f));

            //starboard waterline
            px[1] = (int)(cx[1] - (sin * dimension.y * 0.50f));
            py[1] = (int)(cy[1] + (cos * dimension.y * 0.50f));

            //starboard width1
            px[2] = (int)(cx[2] - (sin * dimension.y * 0.50f));
            py[2] = (int)(cy[2] + (cos * dimension.y * 0.50f));

            //starboard width2
            px[3] = (int)(cx[3] - (sin * dimension.y * 0.50f));
            py[3] = (int)(cy[3] + (cos * dimension.y * 0.50f));

            //starboard rear corner
            px[4] = (int)(cx[4] - (sin * dimension.y * 0.50f));
            py[4] = (int)(cy[4] + (cos * dimension.y * 0.50f));

            //port
            //port rear corner
            px[4] = (int)(cx[4] + (sin * dimension.y * 0.50f));
            py[4] = (int)(cy[4] - (cos * dimension.y * 0.50f));

            //port width2
            px[5] = (int)(cx[3] + (sin * dimension.y * 0.50f));
            py[5] = (int)(cy[3] - (cos * dimension.y * 0.50f));

            //port width1
            px[6] = (int)(cx[2] + (sin * dimension.y * 0.50f));
            py[6] = (int)(cy[2] - (cos * dimension.y * 0.50f));

            //port waterline
            px[7] = (int)(cx[1] + (sin * dimension.y * 0.50f));
            py[7] = (int)(cy[1] - (cos * dimension.y * 0.50f));

            px[8] = (int)(cx[0] + (sin * dimension.y * 0.00f));
            py[8] = (int)(cy[0] - (cos * dimension.y * 0.00f));

            //px[px.length - 1] = px[0];
            //py[py.length - 1] = py[0];

            //if (getSelected() && true) dumpLine(cx, cy, cx.length);
            //if (getSelected() && true) dumpPolygon(px, py, px.length);

            g.setColor(Color.red);
            g.drawPolygon(px, py, px.length);

            g.setColor(color);
            g.setColor(Color.white);
            g.fillPolygon(px, py, px.length);

            g.setColor(Color.orange);
            g.drawLine((int)cx[0], (int)cy[0], (int)cx[4], (int)cy[4]);

        }
        else {
            println("Boat.drawHull() " + " bad mode (" + mode + ")");
        }
	}//drawHall
    
    
    public void dumpPolygon(int[] px, int[] py, int n){
        //println("Boat.dumpPolygon(G, v, c) " + "ID=" + ID + ", name=" + getName() + ", course=" + course + ", +++++++++++++++++++++++++++++++++++++++++++++++");
        int pair = 1;
        for (int i = 0;i < n;i++){
            print("(" + (int)px[i] + ", " + (int)py[i] + ")");
            if (i < px.length - 1){
                print(", ");
            }
        }
        println();
    }
    
    
    public void dumpLine(double[] cx, double[] cy, int n){
        //println("Boat.dumpLine(G, v, c) " + "ID=" + ID + ", name=" + getName() + ", course=" + course + ", +++++++++++++++++++++++++++++++++++++++++++++++");
        int pair = 1;
        for (int i = 0;i < n;i++){
            print("(" + Math.round(cx[i]) + ", " + Math.round(cy[i]) + ")");
            if (i < cx.length - 1){
                print(", ");
            }
        }
        println();
    }
    
    
    public void drawRudderB(Graphics g, Vector3 loc, Color color){
        //draw rudder - start at the end of the boat and go 5 pixcels
        int x1, y1, x2, y2;
        float rad = course;
        rad = (float)Math.toRadians(Vector3.validateDegree(course + 180f, Vector3.TYPE_DEGREE180));
        x1 = (int)(loc.x + (Math.cos(rad) * (dimension.x * 0.75f)));
        y1 = (int)(loc.y + (int)(Math.sin(rad) * (dimension.x * 0.75f)));
        
        float deg = Vector3.validateDegree(course + 180f - (float)rudder, Vector3.TYPE_DEGREE180);

        rad = (float)Math.toRadians(Vector3.validateDegree(deg, Vector3.TYPE_DEGREE180));
        x2 = (int)(x1 + (Math.cos(rad) * dimension.x * 0.25f));
        y2 = (int)(y1 + (Math.sin(rad) * dimension.x * 0.25f));
        g.setColor(Color.white);
        g.setColor(color);
        if (getSelected()){
            println("Boat.drawRudder(G, v, c)2 " + "ID=" + ID + ", course=" + course + ", rudder=" + rudder + ", deg=" + deg + ", rad=" + rad + ", (x1=" + (int)x1 + ", y1=" + (int)y1 + ")(x2=" + (int)x2 + ", y2=" + (int)y2 + ")..............................");
        }
        g.drawLine(x1, y1, x2, y2);
        
    }
    

    
    
    @Override
	public String toString(){
		String s = "[Boat] " + "x=" + location.x + ", y=" + location.y + ", course=" + course + ", velocity=" + (int)velocity.length() + ", rudder=" + (int)rudder;
		//String s2 = " (" + px[0] + "," + py[0] + ")," + "(" + px[1] + "," + py[1] + ")," + "(" + px[2] + "," + py[2] + ")," + "(" + px[3] + "," + py[3] + ")," + "(" + px[4] + "," + py[4] + ")," + "(" + px[5] + "," + py[5] + ")";
		//s = s + s2;
		return (s);
	}
    
    
}


    
