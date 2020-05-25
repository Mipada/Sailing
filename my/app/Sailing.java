//Sailing by
//Peter Ruffner

package my.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;

//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
//import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.event.MouseInputListener;
//import javax.swing.event.MouseInputAdapter;
//import java.awt.event.MouseMotionListener;
import java.lang.Math.*;
import java.util.ArrayList;
//import javax.swing.JApplet;
//import javax.swing.JComponent;
//import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import my.app.App;
import my.app.boat.boat.Boat;
import my.app.mass.Mass;
//import my.app.boat.Boat.Location;
import my.app.boat.boat.Sailboat;
import my.app.environment.Cube;
import my.app.environment.Environment;
import my.app.environment.Wind;
import my.app.mass.Node;
import my.app.mass.Spatial;
import my.app.size.Size;
import static my.app.utilities.Utils.println;
import static my.app.utilities.Utils.print;
import static my.app.utilities.Utils.println;
import my.app.vector3.Vector3;
import my.app.water.Water;

//other imports
//import ruffner.peter.*;
//import ruffner.peter.WaterPkg.*;
//import ruffner.peter.WaterPkg.SailboatPkg.*;


//public class Sailing
public class Sailing extends App implements KeyListener, FocusListener, WindowFocusListener
{
    public static final int MASK_ANY      =   -1;    //1^32
    public static final int MASK_NONE     =    0;    //0000 0000 0000 0000
    
    public static final int MASK_SHIFT    =    1;    //0000 0000 0000 0001
    public static final int MASK_CONTROL  =    2;    //0000 0000 0000 0010
    public static final int MASK_META     =    4;    //0000 0000 0000 0100
    public static final int MASK_ALT      =    8;    //0000 0000 0000 1000
    
    public static final int MASK_LSHIFT   =   16;    //0000 0000 0001 0000
    public static final int MASK_RSHIFT   =   32;    //0000 0000 0010 0000
    public static final int MASK_LCONTROL =   64;    //0000 0000 0100 0000
    public static final int MASK_RCONTROL =  128;    //0000 0000 1000 0000
    
    public static final int MASK_LMETA   =   256;    //0000 0001 0000 0000
    public static final int MASK_RMETA   =   512;    //0000 0010 0000 0000
    public static final int MASK_LALT    =  1024;    //0000 0100 0000 0000
    public static final int MASK_RALT    =  2048;    //0000 1000 0000 0000
    
    public static final int MASK_CAPLOCK =  4096;    //0001 0000 0000 0000
    public static final int MASK_NUMLOCK =  8192;    //0010 0000 0000 0000
    public static final int MASK_OTHER   = 16384;    //0100 0000 0000 0000
    public static final int MASK_NONE2   = 32768;    //1000 0000 0000 0000
    
    public static final int MASK_OTHER2  = 65535;    //1111 1111 1111 1111
    //1000 0000 0000 0000 currentModifier
    //1000 0000 0000 0000 modifier
    //1000 0000 0000 0000 ANDed
    public static final int RIGHT =           1;    //1111 1111 1111 1111
    public static final int LEFT  =           2;    //1111 1111 1111 1111

    //Environment environment;
    //Size waterSize = new Size(500, 500);
    //int waterWidth = 750;
    //int waterHeight = 500;
    Water water;
    //bool
    boolean pauseOnLostFocus = true;
    
	public Sailing(){ //JApplet or JPanel
		super();
		//println("Sailing");
        println("Sailing.Sailing() ");
        
        float a =  3f;
        float b =  4f;
        float c = 12f;
        //println("Sailing.Sailing() " + "(a=" + a + ", b=" + b + ", c=" + c + ")=" + Math.sqrt(a * a + b * b + c * c));

        //System.out.println("layout=" + getLayout());
        //environment = new Environment(this);
	}
    
    
	@Override
    public void initApp() {
        println("Sailing.initApp() ");
        super.initApp();//env man, 
        //theFrame = new JFrame("Sailing...");
        setLayout(null);
        //Dimension d = new Dimension(220 * 4, 220 * 3);//h, w
		//setSize(d);
        //setLocation(600, 200);
        setBounds(500, 125, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Rectangle r = new Rectangle(0, 0, getWidth(), getHeight());
        //world = new Cube(new Vector3(400, 300, 0), new Vector3(1, 2, 0, Vector3.TYPE_DISTANCE));

        println("Sailing.initApp()1 ");
        water = new Water("water", new Cube(new Vector3(400, 300, 0, Vector3.TYPE_DISTANCE), new Vector3(1, 2, 0, Vector3.TYPE_DISTANCE)), this);
        environmentManager.add(water);
        //water.initialize();
        
        println("Sailing.initApp()2 ");
        //water.addBoats(massData, 5);
        for (int n = 0;n < massData.length - 1;n++){
            //println("Sailing.initApp() " + "making mass (" + n + ")");
            Mass mass = getSailboat(n, massData);
            rootNode.attach(mass);
            if (n == 3){
                mass.attach(getSailboat(n + 1, massData));
            }
        }

        
        keyList.add(new CharKey('A', "A"));
        keyList.add(new CharKey('P', "P"));
        keyList.add(new CharKey('R', "R"));
        keyList.add(new CharKey('X', "X"));
        keyList.add(new CharKey('1', "1"));
        keyList.add(new CharKey('2', "2"));
        keyList.add(new CharKey('3', "3"));
        keyList.add(new CharKey('4', "4"));
        keyList.add(new CharKey('5', "5"));
        keyList.add(new CharKey(16, "SHIFT"));
        keyList.add(new CharKey(17, "CONTROL"));
        keyList.add(new CharKey(18, "ALT"));
        keyList.add(new CharKey(27, "ESC"));
        keyList.add(new CharKey(32, "SPACEBAR"));
        keyList.add(new CharKey(37, "LeftArrow"));
        keyList.add(new CharKey(38, "UpArrow"));
        keyList.add(new CharKey(39, "RightArrow"));
        keyList.add(new CharKey(40, "DownArrow"));

        
        println("Sailing.initApp()3 ");
        createGUI();

    }
    
    
    public Water getWater(){
        return water;
    }
    

    public Water getThePanel(){
        return thePanel;
    }
    
    
    //public Environment getEnvironment(){
    //    return environment;
    //}
    
    public Sailboat getSailboat(int n, int[][] massData){
        boolean echo = false;
        Sailboat sailboat = null;
        int type = massData[n][Sailing.dataIndex.TYPE.ordinal()];
        float length, width, depth, speed, maxSpeed, course;
        if (echo) println("Sailing.addBoats()1 " + "boat[" + n + "]");
        Vector3 location = new Vector3((float)Math.random() * getWidth(), (float)Math.random() * getHeight(), 0f, Vector3.TYPE_SIZE);
        course = (float)((Math.random() * 360d));// - 180d
        Vector3 orientation = Vector3.getCourse(course);
        if (echo) System.out.println("Sailing.addBoats()2 " + "boat[" + n + "] location=" + location + ", orientation" + orientation);

        length = massData[type][Sailing.dataIndex.LENGTH.ordinal()];
        width = massData[type][Sailing.dataIndex.WIDTH.ordinal()];///5
        depth = 0f;
        Vector3 dim = new Vector3(length, width, depth, Vector3.TYPE_SIZE);//length, width, depth
        if (echo) System.out.println("Sailing.addBoats()3 " + "boat[" + n + "] dim=" + dim);

        //if (n == 0) 
            //System.out.println("Water.getBoats() " + ", type=" + type + ", course=" + course);
        maxSpeed = massData[n][Sailing.dataIndex.MAXSPEED.ordinal()];
        speed = (int)(Math.random() * maxSpeed);

        if (false) System.out.println("Sailing.addBoats()4 " + "speed=" + speed);
        if (n == 0){
            //System.out.println("Water.addBoats()2.0 ");
            dim = new Vector3(30f, 10f, 0f, Vector3.TYPE_SIZE);
            location.set(300, 100, 0, Vector3.TYPE_SIZE);//x, y
            if (n == 0){ 
                //println("Water.addBoats() " + "type=" + type + ", location="+ location + ", (w=" + getWidth() + ", h=" + getHeight() + ")");
            }
            orientation = Vector3.getCourse(0f);
            course = -45f;
            //String name = "Sailboat#" + type;
            sailboat = new Sailboat("Sailboat#" + n, location, orientation, dim, course, speed, maxSpeed, world, water, (Environment)water, this);//.RUN_PORT,//, Sailboat.CLOSEHAULED_STARBOARD
            //((Sailboat)tempMass).setCourse(Sailboat.RUN_PORT, getWind().getDirection());
        }
        else{
            //System.out.println("Water.addBoats() 3");
            sailboat = new Sailboat("Sailboat#" + n, location, orientation, dim, course, speed, maxSpeed, world, water, (Environment)water, this);
        }
        if (echo) System.out.println("Sailing.addBoats()5 ");
        println("Sailing.getBoat() " + "sailboat=" + sailboat);
        return sailboat;
    }//getBoat


    @Override
    public void createGUI(){ 
        System.out.println("Sailing.createGUI() ");
        super.createGUI();//don't do this
        
        System.out.println("Sailing.createGUI()1 ");
        int mode = 2;
        if (mode == 1){
            System.out.println("Sailing.createGUI()2.1 ");
            water.setAlignmentX(0.5f);
            water.setBounds(25, 25, (int)(getWidth() * 0.8f), (int)(getHeight() * 0.8f));
            getContentPane().add(water, BorderLayout.CENTER);
        }
        else if (mode == 2){
            System.out.println("Sailing.createGUI()2.2 ");
            water.setAlignmentX(0.5f);
            water.setBounds(25, 25, (int)(getWidth() * 0.8f), (int)(getHeight() * 0.8f));
            getContentPane().add(water, BorderLayout.CENTER);
        }
        else{
            println("Sailing.createGUI() " + "bad mode (" + mode + ")");
        }
        System.out.println("Sailing.createGUI()3 ");
        //
/*        
        addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e){
                System.out.println("Sailing.windowFocusGained() (theFrame) ");
                requestFocusInWindow();
            }


            @Override
            public void windowLostFocus(WindowEvent e){
                System.out.println("Sailing.windowFocusLost() (theFrame) ");
            }
        });
        
        addFocusListener(new FocusListener() {
            @Override
			public void focusGained(FocusEvent e) {
                println("Sailing.focusGained() ");
				//newDocument();
			}

            @Override
			public void focusLost(FocusEvent e) {
                println("Sailing.focusLost() ");
				//newDocument();
			}
        });
*/
        //thePanel.addKeyListener(this);
        //thePanel.addKeyListener(new KeyListener(
/*
        addKeyListener(new KeyListener() {
            @Override
			public void keyPressed(KeyEvent e) {
                println("Sailing.createGUI.keyPressed() ");
				//newDocument();
			}

            @Override
			public void keyReleased(KeyEvent e) {
                println("Sailing.createGUI.keyReleased() ");
				//newDocument();
			}

            @Override
			public void keyTyped(KeyEvent e) {
                println("Sailing.createGUI.keyTyped() ");
				//newDocument();
			}
        });
*/
        //thePanel.ad
        //thePanel = water;

        //Set up timer to drive animation events.
        //speed = 1000;//100
        //timer = new Timer((int)speed, this);
        //timer.setInitialDelay(10);
        //timer.start();

        //addKeyListener(this);
        //addFocusListener(this);
        //addWindowFocusListener(this);
    }
    

    @Override
    public void run(){
        println("Sailing.run() " + "tpf=" + tpf);
        delay = (int)(1000f/fps);

        while (running){
            //System.out.println("Sailing.run() " + "running...");
            //oldTime = timer.
            if (!paused){
                environmentManager.move(tpf);
                //environmentManager.draw();
                environmentManager.input();
                
                //water.getCurrentBoat().setInput(false);//turn off old input test
                //water.move(tpf);//true - is myFrame
                //water.update(water.getGraphics());
                //water.draw(water.getGraphics());
                //water.input(tpf);
                repaint();
                //println();
            }
            try {Thread.sleep(delay);}
            catch(InterruptedException e){};
            tpf = fps/1000f;
            //System.out.println("Water.runing ");
        }
    }
    
/*
    @Override
    public void move(float tpf){
        //super.move(tpf);
    }

    
    public void draw(){
        
    }

    
    public void input(){
        
    }
*/    
/*    
    //@Override
    public void setLocation(int x, int y){
        setLocation(x, y);
        //System.out.println("Sailing.setLocation(" + x + ", " + y + ")");
        
    }


    //@Override
    public void setBounds(Rectangle r){
        setBounds(r);
        //System.out.println("Sailing.setBounds(" + r + ")");
    }


    //@Override
    public void setBounds(int x, int y, int h, int w){
        super.setBounds(x, y, h, w);
        //System.out.println("Sailing.setBounds(" + x + ", " + y + ", " + h + ", " + w + ")");
        
    }


    //@Override
    public void setSize(int x, int y){
        super.setSize(x, y);
        //System.out.println("Sailing.setSize(" + d + ")");
    }
    
    
    //@Override
    public void setSize(Dimension d){
        super.setSize(d);
        //System.out.println("Sailing.setSize(" + x + ", " + y + ")");
        if (water == null) return;
        //System.out.println("Sailing.setLocation(" + x + ", " + y + ")");
        Size w = new Size(water.getHeight(), water.getWidth());
        Size f = new Size(getHeight(), getWidth());
        Size t = new Size((f.x - w.x) / 2, (f.y - w.y) / 2);
        //water.setLocation(t.x, t.y);
        water.getWind().setLocation(water.getHeight()/2, water.getWidth()/2);
    }


    //@Override
     public void resize(Dimension d){
        super.resize(d);
        //System.out.println("Sailing.resize(" + d + ")");
    }
    
    
    @Override
     public void resize(int x, int y){
        super.resize(x, y);
        //System.out.println("Sailing.resize(" + x + ", " + y + ")");
    }
    
    
	public void makeBoats(int n)
	{
		Dimension d = getSize();
        Sailboat boat;
		//System.out.println(d);
		Size l = new Size(d.width, d.height - 46);//-46 is ???
		for (int i = 0;i < n;i++)
		{
			//boat = new Sailboat(water, 30, 10);
            //water.addMass(boat);
		}
	}


	@Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Sailing.actionPerformed() " + "e=" + e);
        //super.actionPerformed(e);
        //water.move();
        //water.draw(getGraphics());
        //water.repaint();
        //repaint();
    }
*/
    
	//water key events
    @Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("Sailing.keyTyped() " + " keyCode=" + e.getKeyCode() + " keyChar=" + e.getKeyChar());
	}

    
	//steer boat
	//37, 38, 39, 40
    @Override
	public void keyPressed(KeyEvent e) {
        String action = "";
		if (true) System.out.println("Sailing.keyPressed() " + "keyCode=" + e.getKeyCode() + ", keyChar=" + e.getKeyChar() + ", mods=" + e.getModifiersEx());// + ", e=" + e
        //switch(e.getKeyChar()){
        switch(e.getKeyCode()){
            case 27:
                action = "ESC";
                break;
            case 37:
                action = "LeftArrow";
                break;
            case 38:
                action = "UpArrow";
                break;
            case 39:
                action = "RightArrow";
                break;
            case 40:
                action = "DownArrow";
                break;
            case 59:
                println("Sailing.keyPressed() " + "keyChar=" + e.getKeyChar() + ", keyKeyCode=" + e.getKeyCode());
                break;
            case 65:
                action = "A";
                break;
            case 80:
                //println("Sailing.keyPressed() " + "keyChar=" + e.getKeyCode());
                action = "P";
                break;
            default:
                for (int i = 0;i < keyList.size();i++){
                    CharKey ck = (CharKey)keyList.get(i);
                    //println("keyList[" + i + "]=" + ck + " (" + e.getKeyCode() + ")");
                    if (ck.type == 1){
                        if (e.getKeyCode() == ck.intKey){
                            action = ck.action;
                            break;
                        }
                    }
                    else{
                        if (e.getKeyCode() == ck.charKey){
                            action = ck.action;
                            break;
                        }
                    }
                }
                break;
        }
        
        doAction(action, true, e.getModifiers());
	}//keyPressed

    
    @Override
	public void keyReleased(KeyEvent e){
        String action = "";
		//System.out.println("Sailing.keyReleased() " + "keyCode=" + e.getKeyCode());
        for (int i = 0;i < keyList.size();i++){
            CharKey ck = (CharKey)keyList.get(i);
            //println("keyList[" + i + "]=" + ck + " (" + e.getKeyCode() + ")");
            if (ck.type == 1){
                if (e.getKeyCode() == ck.intKey){
                    action = ck.action;
                    break;
                }
            }
            else{
                if (e.getKeyCode() == ck.charKey){
                    action = ck.action;
                    break;
                }
            }
        }
        doAction(action, false, e.getModifiers());
	}
    
    /*
    0100 //mods
    0100 //mask
    
    
    
    */
    
    
    @Override
    public void doAction(String action, boolean isPressed, int mods){
		System.out.println("Sailing.doAction() " + "action=" + action + ", isPressed=" + isPressed + ", mods=" + mods);

		//if (isPressed) System.out.println("Sailing.doAction() " + "mods=" + mods + ", MASK_CONTROL=" + MASK_CONTROL + ", mods=" + mods);

        //println("mods=" + mods + ", MASK_SHIFT=" + MASK_SHIFT);
        //println("mods=" + mods + ", MASK_CONTROL=" + MASK_CONTROL);
        if (mods == 0){
            doNoMods(action, isPressed, mods);
        }
        //else if (mods == (MASK_CONTROL)){
        //else if ((mods & MASK_CONTROL) == MASK_CONTROL){
        //else if ((mods & MASK_CONTROL) == 0){//always
        else if ((mods & MASK_CONTROL) == MASK_CONTROL){
            doControlMods(action, isPressed, mods);
        }
        else if ((mods & MASK_SHIFT) == MASK_SHIFT){
            doShiftMods(action, isPressed, mods);
        }
        else if ((mods & MASK_ALT) == MASK_ALT){
            doAltMods(action, isPressed, mods);
        }
        else{
    		System.out.println("Sailing.doAction() " + ":mods not found " + "action=" + action + ", isPressed=" + isPressed + ", mods=" + mods);
        }
         
    }//doAction
    
    
    public void doNoMods(String action, boolean isPressed, int mods){
		System.out.println("Sailing.doNoMods() " + "action=" + action + ", isPressed=" + isPressed + ", mods=" + mods);
        if (action.equals("ESC")){
            System.exit(0);
        }
        if (action.equals("LeftArrow") && !isPressed){
            water.getCurrentBoat().setTurnLeft();
        }
        if (action.equals("RightArrow") && !isPressed){
            water.getCurrentBoat().setTurnRight();
        }
        if (action.equals("UpArrow") && isPressed){
            println("Sailing.doNoMods() " + "disconnected.");
            System.exit(1);
            //water.getCurrentBoat().addVelocity(1f);
        }
        if (action.equals("DownArrow") && isPressed){
            println("Sailing.doNoMods() " + "disconnected.");
            System.exit(1);
            //water.getCurrentBoat().addVelocity(-1f);
        }
        if (action.equals("A") && isPressed){
            //water.toggleShowArcs(action, isPressed);
            ((Sailboat)water.getCurrentBoat()).toggleShowArcs();
        }
        if (action.equals("P") && isPressed){
            //water.toggleShowPOS(action, isPressed);
            ((Sailboat)water.getCurrentBoat()).toggleShowPOS();
        }
        if (action.equals("R") && isPressed){
            //water.toggleShowRing(action, isPressed);
            ((Sailboat)water.getCurrentBoat()).toggleShowRing();
        }
        if (action.equals("X") && isPressed){//no courseToPOS
            //water.toggleShowCrosshairs(action, isPressed);
            ((Sailboat)water.getCurrentBoat()).toggleShowCrosshairs();
        }

        if (action.equals("1")){//no courseToPOS
            water.setCurrentBoat(0);
            //thePanel.setCurrentBoat(1);
        }
        if (action.equals("2")){//no courseToPOS
            water.setCurrentBoat(1);
            //thePanel.setCurrentBoat(1);
        }
        if (action.equals("3")){//no courseToPOS
            water.setCurrentBoat(2);
            //thePanel.setCurrentBoat(2);
        }
        if (action.equals("4")){//no courseToPOS
            water.setCurrentBoat(3);
            //thePanel.setCurrentBoat(3);
        }
        if (action.equals("5")){//no courseToPOS
            water.setCurrentBoat(4);
            //thePanel.setCurrentBoat(4);
        }
    }
    
    
    public void doShiftMods(String action, boolean isPressed, int mods){
		//System.out.println("Sailing.doShiftMods() " + "action=" + action + ", isPressed=" + isPressed + ", mods=" + mods);
        if (action.equals("LeftArrow")){
            water.getWind().addDegrees(-5);
            water.getCurrentBoat().setInput(true);
            //thePanel.getCurrentBoat().setInput(true);
            println("Sailing.dSM() " + "wind=" + water.getWind());
        }
        if (action.equals("RightArrow")){
            water.getWind().addDegrees( 5f);
            println("Sailing.dSM() " + "wind=" + water.getWind());
        }
        
        if (action.equals("A") && isPressed){
            water.toggleShowArcs();
            //((Sailboat)water.getCurrentBoat()).toggleShowArcs();
        }
        if (action.equals("P") && isPressed){
            water.toggleShowPOS();
            //water.getCurrentBoat().toggleShowPOS();
        }
        if (action.equals("R") && isPressed){
            water.toggleShowRing();
            //water.getCurrentBoat().toggleShowRing();
        }
        if (action.equals("X") && isPressed){//no courseToPOS
            water.toggleShowCrosshairs();
            //water.getCurrentBoat().toggleShowCrosshairs();
        }
    }//doShiftMods
    
    
    public void doControlMods(String action, boolean isPressed, int mods){
		//System.out.println("Sailing.doControlMods() " + "action=" + action + ", isPressed=" + isPressed + ", mods=" + mods);
        if (action.equals("1") && isPressed){
            ArrayList<Spatial> list = rootNode.getChildren();
            ((Boat)list.get(0)).toggleSelected();
        }
        if (action.equals("2") && isPressed){
            ArrayList<Spatial> list = rootNode.getChildren();
            ((Boat)list.get(1)).toggleSelected();
        }
        if (action.equals("3") && isPressed){
            ArrayList<Spatial> list = rootNode.getChildren();
            ((Boat)list.get(2)).toggleSelected();
        }
        if (action.equals("4") && isPressed){
            ArrayList<Spatial> list = rootNode.getChildren();
            ((Boat)list.get(3)).toggleSelected();
        }
        if (action.equals("5") && isPressed){
            ArrayList<Spatial> list = rootNode.getChildren();
            ((Boat)list.get(4)).toggleSelected();
        }

        if (action.equals("LeftArrow") && isPressed){
            ((Sailboat)water.getCurrentBoat()).addSelectedArc(1);
        }
        else if (action.equals("RightArrow") && isPressed){
            ((Sailboat)water.getCurrentBoat()).addSelectedArc(-1);
            //((Sailboat)thePanel.getCurrentBoat()).addSelectedArc(-1);
        }
        else if (action.equals("UpArrow") && isPressed){
            water.addFPS(5);
            //thePanel.addFPS(5);
        }
        else if (action.equals("DownArrow") && isPressed){
            water.addFPS(-5);
            //thePanel.addFPS(-5);
        }
        else if (action.equals("A") && isPressed){
            ((Sailboat)water.getCurrentBoat()).setShowArcs(isPressed);
            //((Sailboat)thePanel.getCurrentBoat()).setShowArcs(isPressed);
        }
        else if (action.equals("P")){
            togglePaused();
        }
    }
    
    
    public void doAltMods(String action, boolean isPressed, int mods){
		System.out.println("Sailing.doAltMods() " + "action=" + action + ", isPressed=" + isPressed + ", mods=" + mods);
        //if (action.equals("LeftArrow")){
        //    water.getWind().addDegree(0f, -5f, 0f);
        //}
        //else if (action.equals("RightArrow")){
        //    water.getWind().addDegree(0f, 5f, 0f);
        //}
    }
    
    
    @Override
    public void focusLost(FocusEvent e){
		System.out.println("Sailing.focusLost() ");
    }
    
    
    @Override
    public void focusGained(FocusEvent e){
		System.out.println("Sailing.focusGained() ");
    }


    //window focus events
    @Override
    public void windowGainedFocus(WindowEvent e){
        super.windowGainedFocus(e);
		System.out.println("Sailing.windowFocusGained() ");
        //water.requestFocusInWindow();
        requestFocusInWindow();
    }


    @Override
    public void windowLostFocus(WindowEvent e){
        super.windowLostFocus(e);
		System.out.println("Sailing.windowFocusLost() ");
    }
    
    
    public void togglePaused(){
        paused = (paused == false);
    }
    
    
    public enum dataIndex {
        TYPE, //0
        WIDTH, //1
		LENGTH, //2
        MAXSPEED, //3
        EOD //4
    }
    
    //ID, type, width, length, calculated, max speed, end of data
    int massData[][] = new int[][] 
    {
        {0,  10,  30, 10, 1},
        {1,  10,  30,  7, 1},
        {0,  15,  50, 10, 1},
        {1,  20,  75, 10, 1},
        {1,  20, 100, 10, 1}
    };
//}

    public class CharKey{
        int type = 0;
        int intKey = -1;
        int charKey = ' ';
        String action = null;
        
        
        public CharKey(){
            this(' ', "");
        }
        
        
        public CharKey(int intKey, String action){
            type = 1;
            this.intKey = intKey;
            this.action = action;
        }
        
        
        public CharKey(char charKey, String action){
            type = 2;
            this.charKey = charKey;
            this.action = action;
        }
        
        
        public int getIntKey(){
            return intKey;
        }
        
        
        public int getCharKey(){
            return charKey;
        }
        
        
        public String toString(){
            String s = null;
            
            s = new String("CharKey[charKey=" + charKey + ", intKey=" + intKey + ", action=" + action + "}");
            return s;
        }
    }
    
     
    
}

