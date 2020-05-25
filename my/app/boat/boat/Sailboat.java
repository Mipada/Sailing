//Sailboat.java
//by Peter Ruffner

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
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.vecmath.Vector3f;
import my.app.App;
import my.app.environment.Cube;
import my.app.environment.Environment;
//import my.app.size.Location;
import my.app.utilities.TextCell;
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
//Arc2D.Double(double x, double y, double w, double h, double start, double extent, int type)

//public class Sailboat extends GeneralPath implements Shape {
//public class Sailboat extends Object implements Shape {
public class Sailboat extends Boat
{
    static public int STARBOARD = 1;
    static public int PORT = 2;

    static public int RUN_PORT = -7;
    static public int TRAININGRUN_PORT = -6;
    static public int BROADREACH_PORT = -5;
    static public int BEAMREACH_PORT = -4;
    static public int CLOSEREACH_PORT = -3;
    static public int CLOSEHAULED_PORT = -2;
    static public int PORTTAC = -1;
    static public int NOSAIL = 0;
    static public int STARBOARDTAC = 1;
    static public int CLOSEHAULED_STARBOARD = 2;
    static public int CLOSEREACH_STARBOARD = 3;
    static public int BEAMREACH_STARBOARD = 4;
    static public int BROADREACH_STARBOARD = 5;
    static public int TRAININGRUN_STARBOARD = 6;
    static public int RUN_STARBOARD = 7;

    int bogusMassData[][] = new int[][] 
    {
        {1, 1,  12, 0, 0, 4, 1},
        {2, 2,  30, 0, 0, 10, 1},
        {3, 3,  50, 0, 0, 15, 1},
        {4, 4,  75, 0, 0, 17, 1},
        {5, 5, 100, 0, 0, 20, 1}
    };

    //float[] POS = new float[16];
        
    float[] POS = new float[] {-180.0f, -150.0f, -120.5f, -90.0f, -60.0f, -40.0f, -20.0f, 0.0f, 20.0f, 40.0f, 60.0f, 90.0f, 120.0f, 150.0f, 180.0f};//14
    //float[] POS = new float[] {-180.0f, -168.0f, -144.0f, -120.5f, -90.0f, -72.0f, -48.0f, -24.0f, 0.0f, 24.0f, 48.0f, 72.0f, 90.0f, 120.0f, 144.0f, 168.0f, 180.0f};//14
    //float[] POS = new float[] {-180.0f, -168.0f, -144.0f, -120.5f, -90.0f, -72.0f, -48.0f, -24.0f, 0.0f, 24.0f, 48.0f, 72.0f, 90.0f, 120.0f, 144.0f, 168.0f, 180.0f};//14
    //float[] POS = new float[14];//14
    boolean SETUPPOS = false;

    float arc[][] = new float[][]
    {
        {-180, -170}, 
        {-170, -150}, 
        {-150, -130}, 
        {-130, -105}, 
        {-105,  -75}, 
        { -75,  -45}, 
        { -45,  -15}, 
        { -15,   15}, 
        {  15,   45}, 
        {  45,   75}, 
        {  75,  105}, 
        { 105,  130}, 
        { 130,  150}, 
        { 150,  170}, 
        { 170,  180} 
    }; 

    App app;
    protected boolean showPOS = false;
    protected boolean showArcs = false;
    int pointOfSail = 0;
    float main = 0f;
    float newMain = 0f;
	float jib = 0f;
    float newJib = 0f;
    int selectedArc = 0;
	//Boolean splitH = false;
	//Boolean splitV = false;
	//Arc2D myshape;
    float Fr = 0f;
    float Flat = 0f;
    float L = 1f;
    float D = 1f;
    
    float[][] speedFactorTable = new float[][]//VMGTable = course/10 + 1, (a)/10
    {
/* 10*/ { 0.00f,-1.00f,-1.00f,-1.00f,-1.00f,-1.00f,-1.00f,-1.00f},
/* 20*/ { 0.94f, 0.32f,-1.00f,-1.00f,-1.00f,-1.00f,-1.00f,-1.00f},
/* 30*/ { 1.71f, 0.87f, 0.44f, 0.18f,-1.00f,-1.00f,-1.00f,-1.00f},
/* 40*/ { 2.21f, 1.25f, 0.77f, 0.47f, 0.27f, 0.12f,-1.00f,-1.00f},
/* 50*/ { 2.38f, 1.42f, 0.94f, 0.64f, 0.44f, 0.29f, 0.17f, 0.08f},
/* 60*/ { 2.21f, 1.37f, 0.94f, 0.68f, 0.50f, 0.37f, 0.27f, 0.18f},
/* 70*/ { 1.71f, 1.08f, 0.77f, 0.57f, 0.44f, 0.34f, 0.27f, 0.20f},
/* 80*/ { 0.94f, 0.61f, 0.44f, 0.34f, 0.27f, 0.21f, 0.17f, 0.14f},
/* 90*/ { 0.00f, 0.00f, 0.00f, 0.00f, 0.00f, 0.00f, 0.00f, 0.00f},
        
/*100*/ { 1.00f, 0.67f, 0.50f, 0.40f, 0.33f, 0.27f, 0.23f, 0.20f},
/*110*/ { 1.94f, 1.32f, 1.00f, 0.81f, 0.67f, 0.58f, 0.50f, 0.44f},
/*120*/ { 2.71f, 1.87f, 1.44f, 1.18f, 1.00f, 0.87f, 0.77f, 0.68f},
/*130*/ { 3.21f, 2.25f, 1.77f, 1.47f, 1.27f, 1.12f, 1.00f, 0.91f},
/*140*/ { 3.38f, 2.42f, 1.94f, 1.64f, 1.44f, 1.29f, 1.17f, 1.08f},
/*150*/ { 3.21f, 2.37f, 1.94f, 1.68f, 1.50f, 1.37f, 1.27f, 1.18f},
/*160*/ { 2.71f, 2.08f, 1.77f, 1.57f, 1.44f, 1.34f, 1.27f, 1.20f},
/*170*/ { 1.94f, 1.61f, 1.44f, 1.34f, 1.27f, 1.21f, 1.17f, 1.14f},
/*180*/ { 1.00f, 1.00f, 1.00f, 1.00f, 1.00f, 1.00f, 1.00f, 1.00f},
    };
    
    
    float velocityFactor = 0f;//VMGTable = course/10 + 1
    
    float dragForce = 1f;
    float keelForce = 1f;
    float hullforce = 1f;
    float tempCourse = 0f;
    float directionWind = 0f;

    Vector3 forceTotal = new Vector3(1f, 1f, 1f, Vector3.TYPE_SIZE);
    Vector3 forceWind = new Vector3( 0f, -1f,  0f, Vector3.TYPE_SIZE);//wind, clockwide torque
    Vector3 forceWater = new Vector3(0f,  1f,  0f, Vector3.TYPE_SIZE);//water, clockwide torque
    Vector3 forceBuoyancy = new Vector3( 0f,  0f,  1f, Vector3.TYPE_SIZE);//boyoncy, (lift), anti-clockwide torque
    Vector3 forceWeight = new Vector3( 0f,  0f, -1f, Vector3.TYPE_SIZE);//weight, anti-clockwide torque
    Vector3 forceDrag = new Vector3(-0.0001f, 0f, 0f, Vector3.TYPE_SIZE);
    Vector3 forceKeel = new Vector3(-0.0001f, 0f, 0f, Vector3.TYPE_SIZE);
    Vector3 forceDriving = new Vector3(1f, 0f, 0f, Vector3.TYPE_SIZE);//Fr
    boolean drawStats = true;
    

    int arcs = 0;

    public Sailboat(Water water){
        //this(water, new Cube(), new Vector3(water.getWidth()/2f, water.getHeight()/2f, 0f), new Vector3(), new Vector3(30f, 10f, 0f), 3, 5, (float)(Math.random() * 360f) - 180f, 100);
        setShowPOS(false);
        setShowArcs(false);
    }


    //main constructor
/*
    public Sailboat(String name, Vector3 location, Vector3 orientation, Vector3 dimension, float speed, float maxSpeed, Cube world, Water water, Environment env, App app){//, int pos
		super(name, location, orientation, dimension, speed, maxSpeed, world, water, env, app);
        //if (ID == 0){
        //System.out.println("Sailboat.Sailboat() " + "ID=" + ID + ", orientation=" + this.orientation);
        initS();
        //}
        //System.out.println("Sailboat.Sailboat()0 " + "ID=" + ID + ", speed=" + speed);
        //init();
	}
*/
    
    public Sailboat(String name, Vector3 location, Vector3 orientation, Vector3 dimension, float course, float speed, float maxSpeed, Cube world, Water water, Environment env, App app){//, int pos
		super(name, location, orientation, dimension, course, speed, maxSpeed, world, water, env, app);
        if (true){
            System.out.println("Sailboat.Sailboat() " + "ID=" + ID + ", speed=" + this.speed);
        }
        //System.out.println("Sailboat.Sailboat()0 " + "ID=" + ID + ", speed=" + speed);
        setShowPOS(false);
        setShowArcs(false);
	}
    
/*    
    public Sailboat(String name, Vector3 location, Vector3 orientation, Vector3 dim, Cube world, Water water, Environment env, App app){//, int pos
		super(name, location, orientation, dim, world, water, env, app);
        if (ID == 0){
            //System.out.println("Sailboat.Sailboat() " + "ID=" + ID + ", orientation=" + this.orientation);
        }
        //System.out.println("Sailboat.Sailboat()0 " + "ID=" + ID + ", speed=" + speed);
        //initS();
	}
*/    
    
    boolean inited = false;
    public int getIndex(int n){
        int n2 = n;
        if (n2 < 0){
            n2 = arc.length;
        }
        else if (n2 > arc.length){
            n2 = 0;
        }
        if (ID == 0) println("Sailboat.getIndex() " + "ID=" + ID + ", n=" + n + ", n2=" + n2);
        return n2;
    }
    
    
    public void initArcs(){
        //if (ID == 0) println("Sailboat.initArcs() ");
        float n = -180f;
        arc = new float[POS.length][2];

        float arcStart = 0f;
        float arcEnd = 0f;
        //System.out.println("Sailboat.init() 1");
        for (int i = 0; i < POS.length - 1;i++){//
            //System.out.println("Sailboat.init() 2 - ");
            arcStart = (POS[getIndex(i)] + POS[getIndex(i + 1)])/2f;
            arcEnd = (POS[getIndex(i + 1)] + POS[getIndex(i + 2)])/2f;
            arc[i][0] = arcStart;
            arc[i][1] = arcEnd;
            if (ID == 0 && !inited){
                //println("POS[" + i + "]=arcStart=" + arcStart + ", arcEnd=" + arcEnd);
                println("Sailboat.initArcs() " + "POS[" + i + "] arc[" + i + "][" + 0 + "](" + (i - 7) + ")=" + arc[getIndex(i + 1)][0] + ", arc[" + i + "][" + 1 + "]=" + arc[getIndex(i + 1)][1]);
            }
            arcStart = arcEnd;
        }
        arc[13][0] =  165f;
        arc[13][1] =  180f;
        arc[14][0] = -165f;
        arc[14][1] = -180f;
        
        //printout
        if (ID == 0){
            println();
            for (int i = 0;i < arc.length;i++){
                //println("Sailboat.initArcs() " + "arc[" + i + "] arc[" + i + "][" + 0 + "](" + (i - 7) + ")=" + arc[i][0] + ", arc[" + i + "][" + 1 + "]=" + arc[i][1] + ", arc=" + (arc[i][1] - arc[i][0]));
                //println("Sailboat.init() " + "POS[" + i + "]/" + POS.length + "=" + POS[i][0] + " - " + POS[i][1] + " (" + (i - POS.length/2) + ")");
                //println("Sailboat.init() " + "ID=" + ID + " POS[" + i + "/" + (i - 7) + "]/" + POS.length + "=" + POS[i] + " (" + (i - POS.length/2) + ")");
            }
        }
        System.exit(1);
    }
    
    
    public boolean setPointOfSail(int pos){
        boolean changed = false;
        if (pointOfSail != pos){
            changed = true;
            //if (ID == 0) println("Sailboat.setPointOfSail(int) " + "ID=" + ID + ", pos=" + pos + ", pointOfSail=" + pointOfSail);
        }
        pointOfSail = pos;
        return changed;
    }
    
    
    public float setPointOfSailForCourse(int pos){
        float c = 1000f;
        boolean changed = false;

        //println("Sailboat.setPointOfSailForCourse(int)0 " + "ID=" + ID + ", pos=" + pos);
        //println("Sailboat.setPointOfSailForCourse(int)1 " + "POS[" + pos + "]=" + POS[pos]);
        changed = setPointOfSail(pos + 7);
        //println("Sailboat.setPointOfSailForCourse(int)2 " + "POS[" + pos + "]=" + POS[pos]);
        c = POS[pos + 7];
        //if (ID == 0) 
        if (changed) println("Sailboat.setPointOfSailForCourse(int)3 " + "ID=" + ID + ", c=" + c + ", POS[" + (pos + 7) + "]=" + POS[pos + 7]);
        return c;
    }
    

    public void setShowPOS(boolean b){
        showPOS = b;
    }
    

    public void toggleShowPOS(){
        showPOS = showPOS == false;
		System.out.println("Boat.tSP() " + "showPOS=" + showPOS);
    }
    

    public void setShowArcs(boolean b){
        showArcs = b;
    }
    

    public void toggleShowArcs(){
        this.showArcs = showArcs == false;
		System.out.println("Boat.tSP() " + "showArcs=" + showArcs);
    }
    

/*    
    boolean printCourse = true;
    public float getCourseFromPOS(int pos){//, float winDir
        //System.out.println("Sailboat.POSToCourse2() " + "POS=" + POS + ", winDir=" + winDir);
        //System.out.println("Sailboat.POSToCourse2() " + "shift=" + 180/8);
        float tempCourse = 1000f;
        float winDir = water.getWind().getDegree();
        if (pos == RUN_PORT){//-7
            tempCourse = Boat.addDegree(winDir, 180f);
            //setCourse(addDegree(winDir, 180f));
            //setCourse(POS[pos + 7]);
            setMain( 90f);
            setJib(-90f);
        }
        else if (pos == TRAININGRUN_PORT){//-6
            tempCourse = Boat.addDegree(winDir, 150);// -(winDir - 15f);//to rads
            //setCourse(POS[pos + 6]);
            setMain( 80f);
            setJib( 80f);
        }
        else if (pos == BROADREACH_PORT){//-5
            tempCourse = Boat.addDegree(winDir, 120);// -(winDir - 15f);//to rads
            setMain( 70f);
            setJib( 70f);
        }
        else if (pos == BEAMREACH_PORT){//-4
            tempCourse = Boat.addDegree(winDir, 90);// -(winDir - 15f);//to rads
            setMain( 45f);
            setJib ( 45f);
        }
        else if (pos == CLOSEREACH_PORT){//-3
            tempCourse = Boat.addDegree(winDir, 60);// -(winDir - 15f);//to rads
            setMain( 05f);
            setJib ( 15f);
        }
        else if (pos == CLOSEHAULED_PORT){//-2
            tempCourse = Boat.addDegree(winDir, 45);// -(winDir - 15f);//to rads
            setMain( 05f);
            setJib ( 05f);
        }
        else if (pos == PORTTAC){//-1
            tempCourse = Boat.addDegree(winDir, 15);// -(winDir - 15f);//to rads
            setMain( 10f);
            setJib ( 10f);
        }
        else if (pos == NOSAIL){//0
            tempCourse = Boat.addDegree(winDir, 0);// -(winDir - 15f);//to rads
            setMain( 0f);
            setJib ( 0f);
        }
        else{
            println("Sailboat.setCourse(int) " + "no valid pos (" + pos + ")");
        }
        if (tempCourse == 1000){
            println("Sailboat.setCourse(i, f) ");
            System.exit(1);
        }
        if (ID == 0 && printCourse){
            System.out.println("Sailboat.setCourse() " + "ID=" + ID + ", pos=" + pos + ", course=" + course + " main=" + main + " jib=" + jib);
            printCourse = false;
        }
        //setCourse(tempCourse);
        return(tempCourse);
    }//getCourse
*/    
    
    float mainDir = 0f;
    public void setMain(float newMain){
        this.newMain = newMain;
        mainDir = main > newMain ? -1f: 1f;
    }
    

    float jibDir = 0f;
    public void setJib(float newJib){
        this.newJib = newJib;
        jibDir = jib > newJib ? -1f: 1f;
    }
    
/*
    public float POSToCourseOld(int pos){
        //if (ID == 0) System.out.println("Sailboat.POSToCourse() " + "pos=" + pos + ", arc=" + arc);
        float course = 0f;
        
        //course = (POS[pos + 7][0] + POS[pos + 7][1])/2f;
        //println("Sailboat.POSToCourse() " + "pos=" + pos + ", course=" + course);
        //course = POS[pos + 7];
        course = POS[pos + (POS.length/2)];//7
        //if (ID == 0) println("Sailboat.POSToCourse() " + "ID=" + ID + " pos=" + pos + ", arc=" + arc + ", course=" + course);
        setMain(90f);
        newMain = 90f;
        main = 90f;
        newJib = 90f;
        jib = 90f;
        return course;
    }
*/
    
    public int getPointOfSail(){
        return pointOfSail;
    }
    
        
    public int getCourseToPOS(float course){
        float deg = 0f;
        deg = (float)Math.floor(course);
        int pos = 100;

        for (int i = 0;i < POS.length - 1;i++){
            //if (ID == 0) println("Sailboat.courseToPOS() " + "ID=" + ID + ", i=" + i + ", c=" + c + ", left=" + POS[i] + " " + ", right=" + POS[i + 1]);
            if (deg >= POS[i] && deg <= POS[i + 1]){
                pos = i - 7;
                //if (ID == 0) println("Sailboat.courseToPOS() " + "ID=" + ID +  ", i=" + i + ", pos=" + pos);
                return pos;
            }
        }

        if (pos == 100 || pos < -POS.length/2 || pos > POS.length/2){ 
            //println("Sailboat.courseToPOS() " + ", c=" + c + "pos=" + pos);
            //println("Sailboat.courseToPOS() " + ", c=" + c + ", pos=" + pos + ", POS[" + i + "]=" + POS[i] + ", POS{" + (i + 1) + "}=" + POS[i + 1]);
            for (int i = 0;i < POS.length - 1;i++){
                if (ID == 0) println("Sailboat.getCourseToPOS() " + "ID=" + ID + ", i=" + i + ", deg=" + deg + ", left=" + POS[i] + " " + ", right=" + POS[i + 1]);
                if (deg >= POS[i] && deg <= POS[i + 1]){
                    pos = i - 7;
                    //if (ID == 0) println("Sailboat.courseToPOS() " +  "i=" + i + ", pos=" + pos);
                    break;
                }
            }
            println("Sailboat.CourseToPOS() " + "Error: "  + "ID=" + ID + ", bad course=" + deg);
            System.exit(1);
        }

        if (pos == 100){
            println("Sailboat.CourseToPOS() " + "Error: "  + "ID=" + ID + ", bad course=" + deg);
            System.exit(1);
        }
        return pos;
    }//courseToPOS
    
    
    public void setSails(int pos){
        //if (ID == 0) println("Sailboat.setSails() " + "pos=" + pos + ", course=" + course);
        //float tCourse = orientation.getCourse();
        float windDir = water.getWind().getCourse();
        float appWindDeg = 0f;
        switch(pos){
            case -7://Run port
                //setMain( 90f);
                //setJib (-90f);
                
                break;
            case -6://traing run port
                setMain( 90f);
                setJib (-90f);
                break;
            case -5://broad reach port
                setMain( 70f);
                setJib ( 80f);
                break;
            case -4://beam reach port
                setMain( 45f);
                setJib ( 60f);
                break;
            case -3://close reach port
                setMain( 55f);
                setJib ( 65f);
                break;
            case -2://close hauled port
                setMain( 30f);
                setJib ( 40f);
                break;
            case -1://port tac
                setMain( 10f);
                setJib ( 20f);
                break;
            case  0://no sail
                setMain( -00f);
                setJib ( -00f);
                break;
            case  1://starboard tac
                setMain(-10f);
                setJib (-20f);
                break;
            case  2://close halled starboard
                setMain( -30f);
                setJib ( -40f);
                break;
            case  3://close reach starboard
                setMain(-55f);
                setJib (-65f);
                break;
            case  4://beam reach starboard
                setMain(-45f);
                setJib (-60f);
                break;
            case  5://broad reach starboard
                setMain(-70f);
                setJib (-80f);
                break;
            case  6://training run starboard
                setMain(-90f);
                setJib ( 90f);
                break;
            case  7://run starboard
                //setMain(-90f);
                //setJib ( 90f);
                break;
            default:
                println("Sailboat.setSails() " + "Error: bad pointOfSail (" + pos + ")");
                break;
        }
        if (ID == 0){
            //System.out.println("Sailboat.setSails()" + "ID=" + ID + ", course=" + course + ", pos=" + pos + " main=" + main + " jib=" + jib);
            //foo2 = false;
        }
    }//setSails
    
    
    public void adjustSails(){
        if (main > newMain){
            //if (ID == 0) System.out.println("Sailboat.adjustSails() inc " + "main=" + main + ", newMain=" + newMain + ", pointOfSail=" + pointOfSail);
            main -= 5f;
            if (main < newMain)
                main = newMain;
        }
        else if (main < newMain){
            //if (ID == 0) System.out.println("Sailboat.adjustSails() dec " + "main=" + main + ", newMain=" + newMain + ", pointOfSail=" + pointOfSail);
            main += 5f;
            if (main > newMain)
                main = newMain;
        }
        if (jib > newJib){
            //if (ID == 0) System.out.println("Sailboat.adjustSails() inc " + "jib=" + jib + ", newJib=" + newJib + ", pointOfSail=" + pointOfSail);
            jib -= 5f;
            if (jib < newJib)
                jib = newJib;
        }
        else if (jib < newJib){
            //if (ID == 0) System.out.println("Sailboat.adjustSails() dec " + "jib=" + jib + ", newJib=" + newJib + ", pointOfSail=" + pointOfSail);
            jib += 5f;
            if (jib > newJib)
                jib = newJib;
        }
    }//adjustSails
    
    
    public float trueWindVelocity(float boatVelocity, float apparentWindVelocity, float apparentWindAngle){
        float W = 0f;
        
        return W;
    }
    
    
    public float trueWindAngle(float boatVelocity, float apparentWindVelocity, float apparentWindAngle){
        float W = 0f;
        
        return W;
    }
    
    
    @Override
    public void move(float tpf){
        //println("Sailboat.move(f)");
        move(null, tpf, 0);
    }
    
    
    @Override
    public void move(Cube world, float tpf){
        //println("Sailboat.move(f)");
        move(world, tpf, 0);
    }
    
    
    @Override
    public void move(Cube world, float tpf, int level){
        //println(pad(level) + "Sailboat.move(C, f, i) " + "ID=" + ID + ", name=" + getName());
        super.move(world, tpf, level);
        
        int loc = getCourseToPOS(Vector3.validateDegree(course, Vector3.TYPE_DEGREE180));//addCourse(180f)
        setPointOfSail(loc);
        setSails(getPointOfSail());
        adjustSails();
        acceleration();
    }
    
    
    public float apparentWindVelocity(Vector3 Vw, Vector3 Vb, float windAngle){
        float A;
        float bV = Vb.length();
        float tWV = Vw.length();
        float α = (float)Math.toRadians(windAngle);
        //α = Vw.subtract(Vb).length();
        
        A = (float)Math.sqrt(tWV * tWV + bV * bV + (2 * tWV * bV * Math.cos(α)));
        //setApparentWindVelocity(A);
        return A;
    }
    
    
    //
    //alt 224
    //alt 225
    //wind velocity vector
    //boat velocity vector
    public Vector3 velocityApparentWind(Vector3 wV, Vector3 bV){
        Vector3 v = wV.subtract(bV);
        return v;
    }


    //beta/ß
    public float angleApparentWind(float angleTrueWind, float course){//ß
        float d = Vector3.subtractDegrees(angleTrueWind, course);
        return d;
    }

    
    //alpha/α
    public float angleApparentCourse(float angleTrueWind, float course){//ß
        float d = Vector3.subtractDegrees(angleTrueWind, course);
        return d;
    }
    
/*    
    public float apparentWindAngle(Vector3 wV, Vector3 bV, float windDegree){//α
        float β = 0f;//alt 225
        float B = bV.length();
        float W = wV.length();
        float α = (float)Math.toRadians(windDegree);
        //α = Vw.subtract(Vb).length();
        float A = (float)Math.sqrt((W * W) + (B * B) + (2 * W * B * Math.cos(α)));
        β = (float)Math.acos((W * Math.cos(α) + B)/A);
        setApparentWindAngle(β);
        
        return β;
    }
*/
    
    Vector3 deltaV = new Vector3(Vector3.TYPE_SIZE);
    Vector3 trueWind = new Vector3(Vector3.TYPE_SIZE);
    Vector3 apparentWind = new Vector3(Vector3.TYPE_SIZE);
    float angleApparentWind = 0f;
    public void acceleration(){
        if (getSelected()) println("Sailboat.acceleration() " + "ID=" + ID);
        int pos = pointOfSail;
        Vector3 vector = Vector3.getVector(course);
        Vector3 deltaV = null;
        //int tempPOS = 0;//course is an angle off true wind (c = trueWind - course)
        float α = 0;//closest angle to apparent wind at which boat can sail
        int alpha = 0;
        
        int β = 0;//alt 225
        float beta = 0f;
        float factor = 1f;

        trueWind = water.getWind().getVelocity();
        apparentWind = trueWind.subtract(velocity);

        //Vector3 velocityApparentWind = velocityApparentWind(water.getWind().getVelocity(), velocity);
        float deg = course;
        float angleTrueWind = water.getWind().getCourse();

        int mode = 1;
        if (mode == 1){
            //deltaV = new Vector3((float)(Math.cos(Math.toRadians(c))), (float)(Math.sin(Math.toRadians(c))), 0f);
            //rotationVelocity.set(deltaV);
            //deltaV = new Vector3((float)(Math.sin(Math.toRadians(c))), (float)(Math.cos(Math.toRadians(c))), 0f);//bad
            pos = pointOfSail;
            α = (float)Math.toRadians(water.getWind().getCourse());
            alpha = (int)α;
            factor = speedFactorTable[pos + 7][alpha];
            vector = Vector3.getVector(course);
            //deltaV = vector.multiply(speed);
            maximumSpeed = vector.multiply(speed);
            velocity = velocity.add(vector);//vector.multiply(speed)
            velocity = velocity.clip(maximumSpeed);
            if (input){// ID == 0 && 
                //println("Sailboat.acceleration()1 " + "input=" + input + ", ID=" + ID + ", course=" + course + ", velocity=" + velocity + ", angleTrueWind=" + angleTrueWind + ", angleApparentWind=" + (int)angleApparentWind + ", beta=" + beta + ", β=" + β + " / " + "angleApparentWind=" + (int)angleApparentWind + ", course=" + course + ", aAW=" + aAW + ", alpha=" + alpha + ", α=" + α + "\n" + "Sailboat.acceleration()1 " + "ID=" + ID + ", sFT[ß=" + β + "/" + speedFactorTable.length + "][α=" + α + "/" + speedFactorTable[0].length + "]");
                setInput(false);
            }
        }
        else if (mode == 2){
            //beta
            float angleApparentCourse;
            //angleApparentCourse = angleApparentCourse(angleApparentWind, course);
            angleApparentWind = Vector3.subtractDegrees(angleTrueWind, deg);
            beta = (float)Math.abs(angleApparentWind/10f);
            β = (int)(beta);//alt 225

            //alpha
            //angleApparentWind = angleApparentWind(angleTrueWind, course);
            float aAW = Vector3.subtractDegrees(angleApparentWind, deg);
            alpha = (int)(Math.abs(Vector3.subtractDegrees(aAW, deg)/10f));
            α = (int)(alpha)/2;

            if (β >= speedFactorTable.length || α >= speedFactorTable[0].length){

                String s = new String("Sailboat.acceleration()2 " + "index out of bounds" + "\n" + "Sailboat.acceleration()2 " + "ID=" + ID + ", sFT[ß=" + β + "/" + speedFactorTable.length + "][α=" + α + "/" + speedFactorTable[0].length + "]");
                //println("Sailboat.acceleration()2 " + "index out of bounds" + "\n" + "Sailboat.acceleration()2 " + "ID=" + ID + ", sFT[ß=" + β + "/" + speedFactorTable.length + "][α=" + α + "/" + speedFactorTable[0].length + "]");

                //app.myExit(1, s);
                //println("Sailboat.acceleration()2 " + "ID=" + ID + ", sFT[ß=" + β + "/" + speedFactorTable.length + "][α=" + α + "/" + speedFactorTable[0].length + "]");
                //println("Sailboat.acceleration()2 " + "ID=" + ID + ", angleTrueWind=" + angleTrueWind + ", course=" + course + ", angleApparentWind=" + (int)angleApparentWind + ", beta=" + beta + ", β=" + β + " / " + "angleApparentWind=" + (int)angleApparentWind + ", course=" + course + ", alpha=" + alpha + ", α=" + α);
                //e.printStackTrace();
                //Thread.dumpStack();
                //System.exit(1);
            }
            else{
                factor = speedFactorTable[pos + 7][alpha];
                //factor = speedFactorTable[β][α];
                statFactor = factor;
            }
            if (input){// ID == 0 && 
                println("Sailboat.acceleration()1 " + "input=" + input + "\n" + "Sailboat.acceleration()1 " + "ID=" + ID + ", course=" + course + ", velocity=" + velocity + ", angleTrueWind=" + angleTrueWind + ", angleApparentWind=" + (int)angleApparentWind + ", beta=" + beta + ", β=" + β + " / " + "angleApparentWind=" + (int)angleApparentWind + ", course=" + course + ", aAW=" + aAW + ", alpha=" + alpha + ", α=" + α + "\n" + "Sailboat.acceleration()1 " + "ID=" + ID + ", sFT[ß=" + β + "/" + speedFactorTable.length + "][α=" + α + "/" + speedFactorTable[0].length + "]");
                setInput(false);
            }
        }
        else if (mode == 3){
            vector = Vector3.getVector(course);
            deltaV = vector.multiply(speed);
            maximumSpeed = vector.multiply(maxSpeed).multiply(factor);
            //velocity = velocity.accelerate(deltaV, maximumSpeed);
            Vector3 tempVelocity;
            //tempVelocity = velocity.add(Vector3.getVector(course).multiply(speed));
            tempVelocity = velocity.add(Vector3.getVector(course));
            if (tempVelocity.length() <= Math.abs(maxSpeed)){
                velocity = tempVelocity;
            }
            else{
                velocity = velocity.multiply(maxSpeed);
            }
        }
        else{
            println("Sailboat.acc() " + "bad mode=" + mode);
        }

        //println("Sailboat.acc() " + "ID=" + ID + ", speed=" + speed + ", tempVelocity=" + tempVelocity + ", velocity=" + velocity);
        apparentWind = trueWind.subtract(velocity);
        //velocity.set(1f, 0f, 0f);
        //velocity = orientation.getCourseVector();

        //if (getSelected()) println("Sailboat.acc() " + "ID=" + ID + ", course=" + getCourse() + ", vector=" + vector + ", speed=" + speed + ", deltaV=" + deltaV + ", velocity=" + velocity + ", factor=" + factor + ", maximumSpeed=" + maximumSpeed);
        //if (ID == 0) println("Sailboat.acc() " + "ID=" + ID + ", vector=" + vector + ", speed=" + speed + ", deltaV=" + deltaV);

        //if (ID == 0) println("Sailboat.acc() " + "ID=" + ID + ", vAW=" + apparentWind.toString() + ", vTW=" + velocityTrueWind.toString() + ", velocity3D=" + velocity3D.toString());
        
        //velocity3D.set(-1f, 0f, 0f);
    }//acceleration
    
    float statFactor = 0f;

    public void drawStats(Graphics2D g2){
        if (true) return;
        //if (ID != water.getCurrentBoat().getID()) return;
        int x = 10;
        int y = 19;
        int lineHeight = 14;
        int xTab = 100;
        
        //courseCell.update(Float.toString(course.get()));
        //string = 
        g2.setColor(Color.white);
        g2.drawString(new String("Position:"),          x,    y + (0 * lineHeight));
        g2.drawString(location.toString(0),           xTab, y + (0 * lineHeight));
        g2.drawString(new String("Orientation:"),            x,    y + (1 * lineHeight));
        g2.drawString("course=(" + Float.toString(course) + "), " + orientation.toString(1) + ", speed=" + speed + ", deltaV=" + deltaV + ", statFactor=" + statFactor,       xTab, y + (1 * lineHeight));
        //g2.drawString("deltaV=" + deltaV.toString(3),       500, y + (1 * lineHeight));
        g2.drawString(new String("Wind Direction:"),    x,    y + (2 * lineHeight));
        //g2.drawString(Float.toString(water.getWind().getVectorDirection()),    xTab, y + (2 * lineHeight));
        g2.drawString(Float.toString(water.getWind().getCourse()),    xTab, y + (2 * lineHeight));
        g2.drawString(new String("True Wind:"),         x,    y + (3 * lineHeight));
        g2.drawString(new String(trueWind.toString(0) + ", " + trueWind.length()),      xTab, y + (3 * lineHeight));
        g2.drawString(new String("Boat Speed:"),        x,    y + (4 * lineHeight));
        g2.drawString(new String(velocity.toString(0) + ", " + velocity.length()),            xTab, y + (4 * lineHeight));
        g2.drawString("Apparent Wind:",     x,    y + (5 * lineHeight));
        g2.drawString(new String(apparentWind.toString(0) + ", " + apparentWind.length()),       xTab, y + (5 * lineHeight));
        g2.drawString(new String("Boat Speed:"),        x,    y + (6 * lineHeight));
        g2.drawString(velocity.toString(),           xTab, y + (6 * lineHeight));
        
        g2.drawString(new String("App Wind Angle:"),        x,    y + (7 * lineHeight));
        //g2.drawString(String.valueOf(apparentWindAngle),           xTab, y + (7 * lineHeight));
        g2.drawString("" + 0f,           xTab, y + (7 * lineHeight));
        
        //g2.drawString(new String("Wind Speed:     " + tempVelocityWind),  x, y + (4 * lineHeight));
        //g2.drawString(new String("Wind Speed:     " + tempVelocityWind),  x, y + (5 * lineHeight));
    }
    
    
    @Override
    public void draw(Cube world, Graphics g){
        //println("Sailboat.draw(G) ");
        //super.draw(g);
        draw(world, g, 0);
    }
    
    
    @Override
    public void draw(Cube world, Graphics g, int level){
        //println("Sailboat.draw(G, i) " + "ID=" + ID + ", level=" + level);
        super.draw(world, g, level);//super, don't need this
        //println("Sailboat.draw(G, i) " + "ID=" + ID + ", name=" + name + ", location=" + location.toString(0) + ", velocity=" + velocity + ", children=" + children.size());
        //if ((ID == 0 || ID == 1)) 
            //println("Sailboat.draw(G, i) " + "ID=" + ID + ", name=" + getName() + ", children=" + children.size() + ", location=" + location.toString(0) + ", area=" + area.toString(0));
        //Graphics2D g2 = (Graphics2D)g;
		//aft is course - 180 degrees
		//draw hull
        
        //if (ID == 0) println("Sailboat.draw() " + "bounds=" + bounds.toString(0) + ", locationD" + locationD.toString(0));
        drawMassS(g, location, Color.white);

        Cube area = world.scale(0f, 0f, 0f);
        Vector3 loc = area.validate(location);
        if (loc != null){
            //if (ID == 0) println("Boat.draw() (echo) " + "box=" + box.toString(0) + ", location" + location.toString(0) + ", area=" + area.toString(0) + ", pos2=" + pos2.toString(0));
            drawMassB(g, loc, Color.red);
        }
        if (drawStats) drawStats((Graphics2D)g);//(Graphics2D)
	}//draw(g)
    
    
    @Override
    public void draw(Graphics g){
        //println("Sailboat.draw(G) ");
        //super.draw(g);
        draw(g, 0);
    }
    
    
    @Override
    public void draw(Graphics g, int level){
        //println("Sailboat.draw(G, i) " + "ID=" + ID + ", level=" + level);
        super.draw(g, level);//super, don't need this
        //println("Sailboat.draw(G, i) " + "ID=" + ID + ", name=" + name + ", location=" + location.toString(0) + ", velocity=" + velocity + ", children=" + children.size());
        //if ((ID == 0 || ID == 1)) 
            //println("Sailboat.draw(G, i) " + "ID=" + ID + ", name=" + getName() + ", children=" + children.size() + ", location=" + location.toString(0) + ", area=" + area.toString(0));
        //Graphics2D g2 = (Graphics2D)g;
		//aft is course - 180 degrees
		//draw hull
        
        //if (ID == 0) println("Sailboat.draw() " + "bounds=" + bounds.toString(0) + ", locationD" + locationD.toString(0));
        drawMassS(g, location, Color.white);
        if (drawStats) drawStats((Graphics2D)g);//(Graphics2D)
	}//draw(g)
    
    
    //@Override
    public void drawMassS(Graphics g, Vector3 location, Color color){
        //super.drawMass(g, location, color, 1);//not here
        //println("Sailboat.drawMassS(g, v, c) " + "ID=" + ID);
        if (showArcs) drawArcs((Graphics2D)g, location);
        if (showPOS) drawPOS(g, location);
		//if (true) drawHull(g, pos);
		if (true) drawMain(g, location);
		if (true) drawJib(g, location);
    }
    
    
    public void drawMain(Graphics g, Vector3 loc){
        float deg = course;
        int x1 = loc.getIntX();
        int y1 = loc.getIntY();
		int x2 = (int)(x1 - (Math.cos(Math.toRadians(deg + main)) * dimension.x));
		int y2 = (int)(y1 - (Math.sin(Math.toRadians(deg + main)) * dimension.x));
		g.setColor(Color.black);
		g.drawLine(x1, y1, x2, y2);

	}

    
    boolean printJib = false;
	public void drawJib(Graphics g, Vector3 loc){
        //if (false) return;
        float deg = course;//addCourse(180f)
        
        int x1 = (loc.getIntX() + (int)(Math.cos(Math.toRadians(deg)) * (dimension.x * 0.33f)));
        int y1 = (loc.getIntY() + (int)(Math.sin(Math.toRadians(deg)) * (dimension.x * 0.33f)));
        float tCos = 0f;
        float tSin = 0f;
		//int x2 = (int)(x1 + (Math.cos(Math.toRadians(addDegree(orientation.addCourse(180f), 180f, jib))) * (dimension.x * 0.33f)));
		//int y2 = (int)(y1 + (Math.sin(Math.toRadians(addDegree(orientation.addCourse(180f), 180f, jib))) * (dimension.x * 0.33f)));
		int x2 = (int)(x1 + (Math.cos(Math.toRadians(deg + 180f + jib)) * (dimension.x * 0.33f)));//
		int y2 = (int)(y1 + (Math.sin(Math.toRadians(deg + 180f + jib)) * (dimension.x * 0.33f)));//
		g.setColor(Color.black);
		//g2.setColor(Color.red);
		g.drawLine(x1, y1, x2, y2);
        if (ID == 0 && printJib){
            System.out.println("Sailboat.drawJib()" + "ID=" + ID + ", course=" + course + " jib=" + jib + ", deg=" + deg + ", x1=" + x1 + ", z1=" + y1 + ", x2=" + x2 + ", z2=" + y2 + ", cos=" + (int)tCos + ", sin=" + (int)tSin);
            printJib = false;
        }
	}

    int frame = -1;
    boolean fillarc = true;
    public void drawPOS(Graphics g, Vector3 loc){
        //if (ID != 0) return;
        int x1 = loc.getIntX();
        int y1 = loc.getIntY();
        float wind = water.getWind().getCourse();
        //println("Sailboat.drawPOS() ");
            
        g.setColor(Color.black);//black
        for (int i = 0; i < POS.length;i++){
    		//bowx = (int)(locationD.x + (Math.cos(Math.toRadians(course.get())) * length/3f));
        	//bowy = (int)(locationD.y + (Math.sin(Math.toRadians(course.get())) * length/3f));
            float deg = Vector3.validateDegree(wind + POS[i], Vector3.TYPE_DEGREE180);
            int x2 = (int)(loc.x - (Math.cos(Math.toRadians(deg))) * 100);
            int y2 = (int)(loc.y - (Math.sin(Math.toRadians(deg))) * 100);
            //if (ID == 0 && i == 0) println("Sailboat.drawPOS() " + "ID=" + ID + ", wind=" + wind + ", POS[" + i + "}=" + POS[i] + ",deg=" + deg);
            g.drawLine(x1, y1, x2, y2);
        }
    }
    
    
    boolean printArc = true;
    boolean printArcs = true;
    public void drawArcs(Graphics g, Vector3 loc){
        //if (ID != 0) return;
        int x1 = loc.getIntX();
        int y1 = loc.getIntY();
        int x2;
        int y2;
        float wind = water.getWind().getCourse();
        double cos2 = 0f;
        double sin2 = 0f;
        
        //all arcs
        g.setColor(Color.red);
        for (int i = 0; i < POS.length - 1;i++){
        //for (int i = -7; i < 7;i++){
            //if (ID == 0 && showArcs && printArcs) println("i=" + i);
            float arcLeft = POS[i];//getPOS(i)
            float arcRight = POS[i + 1];//getPOS
            float halfArc = (arcLeft + arcRight)/2f;
            //float halfArc = (arcRight - arcLeft);

            x2 = (int)(x1 - (cos2 = Math.cos(Math.toRadians(wind + halfArc))) * 100);
            y2 = (int)(y1 - (sin2 = Math.sin(Math.toRadians(wind + halfArc))) * 100);
            g.drawLine(x1, y1, x2, y2);
            
            if (ID == 0 && printArcs){
                //println("POS[" + i + "]=arc+=" + addDegree(course, POS[i],  halfArc) + ", arc-=" + addDegree(course, POS[i], -halfArc));
                //println("Sailboat.drawArcs() " + "wind=" + wind + ", POS[" + i + "](" + (i - 7) + ")=arcLeft=" + arcLeft + ", arcRight=" + arcRight + ", arc=" + halfArc + ", cos2=" + cos2);
            }
        }

        //selected arc
        int len = 100;
        //draw arc
        int arcStyle = 1;
        if (arcStyle == 1){
            wind = water.getWind().getCourse();
            //int len = (int)water.getWind().getLength();
            x2 = x1 - len;
            y2 = y1 - len;
            int w = len * 2;
            int h = len * 2;
            int startAngle = (int)(arc[selectedArc][0] - wind);// + wind
            int arcAngle   = (int)(arc[selectedArc][1] - arc[selectedArc][0]);
            g.setColor(Color.red);
            g.fillArc(x2, y2, w, h, startAngle, arcAngle);
            //if (ID == 0 && showArcs && printArc) println("Sailboat.drawArcs()2 " + "fillArc(" + x + ", " + y + ", " + w + ", " + h + ", arc[" + selectedArc + "][0]=" + arc[selectedArc][0] + ", arc[" + selectedArc + "][1]=" + arc[selectedArc][1] + ", startAngle=" + startAngle + ", arcAngle=" + arcAngle);
        }
        else if (arcStyle == 2){
            frame++;
            if (frame >= 60){
                addSelectedArc(1);
                wind = water.getWind().getCourse();
                //int len = (int)water.getWind().getLength();
                x2 = x1 - len;
                y2 = y1 - len;
                int w = (int)len * 2;
                int h = (int)len * 2;
                int startAngle = (int)(arc[selectedArc][0] + wind + 90f);// + 90
                int arcAngle = (int)(arc[selectedArc][0] - arc[selectedArc][1]);
                //println("Sailboat.drawPOS() 1" + "fillArc(" + x + ", " + y + ", " + w + ", " + h + ", " + startAngle + ", " + arcAngle);

                g.setColor(Color.red);
                g.fillArc(x2, y2, w, h, startAngle, arcAngle);
                //g2.fillArc
                frame = 0;
            }
        }
        if (ID == 0 && showArcs && printArcs){
            printArcs = false;
        }
        if (ID == 0 && showArcs && printArc){
            printArc = false;
        }
        g.setColor(Color.green);
        //println();
    }
    
    
    public void addSelectedArc(int n){
        selectedArc += n;
        if (selectedArc > arc.length - 1){
            selectedArc = 0;
        }
        else if (selectedArc < 0){
            selectedArc = arc.length - 1;//
        }
        printArc = true;
        //println("Sailboat.addSelectedArc(int) " + "selectedArc=" + selectedArc + ", POS=" + (selectedArc - 7));
    }
    
    
    @Override
	public String toString(){
		String s = "Sailboat: ID=" + ID + ", name=" + name + ", position=" + location + ", velocity=" + (int)velocity.length() + ", orientation=" + orientation + ", dim=" + dimension.toString(0) + ", course=" + course + ", speed=" + speed + ", maxSpeed=" + maxSpeed + ", pointOfSail=" + pointOfSail + ", rudder=" + (int)rudder + ", main=" + main + ", jib=" + jib + "]";
		//String s2 = " (" + px[0] + "," + py[0] + ")," + "(" + px[1] + "," + py[1] + ")," + "(" + px[2] + "," + py[2] + ")," + "(" + px[3] + "," + py[3] + ")," + "(" + px[4] + "," + py[4] + ")," + "(" + px[5] + "," + py[5] + ")";
		//s = s + s2;
		return (s);
	}
}

/*
Since, I believe, he's long gone...
What!? I thought everyone left 'cause of the weather. 
No, we leave because of the high crime.
Don't let the door hit ya on the way out.

*/


