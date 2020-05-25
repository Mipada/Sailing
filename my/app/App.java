/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import static my.app.Sailing.MASK_CONTROL;
import my.app.environment.Cube;
import my.app.environment.Environment;
import my.app.environment.EnvironmentManager;
import my.app.mass.Node;
import my.app.mass.Spatial;
import my.app.size.Size;
import static my.app.utilities.Utils.println;
import my.app.vector3.Vector3;
import my.app.water.Water;

/**
 *
 * @author Peter
 */
public class App extends JFrame implements Runnable, KeyListener, FocusListener, WindowFocusListener//, ActionListener
{
    //public JFrame theFrame;
    public Node rootNode;
    public ArrayList<Sailing.CharKey> keyList = new ArrayList();
    public EnvironmentManager environmentManager = new EnvironmentManager();
    public Timer timer;
    public int speed = 100;
    public int delay = 100;
    public float fps = 30f;
    public float tpf = fps/1000f;
    public Thread runner;
    public boolean running = false;
    public boolean paused = false;
	//Line2D[] line;
	public Dimension d;
    //public Vector3 style = new Vector3(1, 2, 0, Vector3.TYPE_DISTANCE);
    //JFrame theFrame;
    public Cube world = new Cube(new Vector3(1, 2, 0, Vector3.TYPE_DISTANCE));
    Water thePanel;
    
	public static void main(String[] args)
	{
        System.out.println("App.main()...");
        for (int i = 0;i < args.length;i++)
        {	//1
            System.out.println("Arg " + i + " is " + args[i]);
        }
        
        
        //Sailing app = new Sailing();
        //app.init();
        //app.start();
        //app.setVisible(true);
        
        try {
            SwingUtilities.invokeAndWait(new Runnable(){ 
                public void run() {
                    App app = new Sailing();
                    app.init();
                    app.start();
                    //app.setVisible(true);
                }
            });
        } catch (Exception e) {
            System.err.println("App.init() " + "createGUI didn't successfully complete");
            e.printStackTrace();
            //System.exit(1);
        }
        
        
	}

    
    
    public App(){
        println("App.App()");
    }

    public Node getRootNode(){
        return rootNode;
    }
    
    
    //public void initialize(){
    //    init();
    //    environmentManager.initialize();
    //}
    
    
    public void init(){
        println("App.init() ");
        rootNode = new Node("rootNode");
        initApp();
        environmentManager.initialize();
        setVisible(true);
        requestFocusInWindow();
    }
    
    
    public void initApp(){
        println("App.initApp() ");
        

    }
    
    
    public void createGUI()
    {
        println("App.createGUI() ");
        addKeyListener(this);
        addFocusListener(this);
        //addWindowFocusListener(this);

        addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e){
                System.out.println("Sailing.windowFocusGained() (theFrame) ");
                //requestFocusInWindow();
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
    }
    
    
    public void start() {
        System.out.println("App.start()");
		if (runner == null){
            runner = new Thread(this);
            runner.start();
            running = true;
            //println();
        }
		//timer.restart();
    }


    public void stop() {
        System.out.println("App.stop()");
        running = false;
        //timer.stop();
    }
    
    
    public void run(){
        //System.out.println("App.run()");
        //while(running){
        //    System.out.println("App.run() " + "running...");
        //}
        tpf = fps/1000f;
    }
    
/*
    public void move(float tpf){
        
    }
    
    
    public void draw(){
        
    }
    
    
    public void input(){
        
    }
*/
    
    public synchronized void myExit(int n, String s){
        println("App.myExit(" + n + ")");
        if (n == 1){
            println(s);
        }
        //e.printStackTrace();
        Thread.dumpStack();
        System.exit(1);
    }


    @Override
    public void reshape(int i, int i1, int i2, int i3) {
        super.reshape(i, i1, i2, i3); //To change body of generated methods, choose Tools | Templates.
    }
    public void setBounds(Rectangle r){
        super.setBounds(r);
        System.out.println("App.setBounds(" + r + ")");
    }


    @Override
    public void setBounds(int x, int y, int h, int w){
        super.setBounds(x, y, h, w);
        System.out.println("App.setBounds(" + x + ", " + y + ", " + h + ", " + w + ")");
        
    }


    @Override
    public void setSize(int x, int y){
        super.setSize(x, y);
        //System.out.println("App.setSize(" + x + ", " + y + ")");
    }


    @Override
     public void setSize(Dimension d){
        super.setSize(d);
        //System.out.println("App.setSize(" + (int)d.getWidth() + ", " + (int)d.getHeight() + ")");
    }
    
    
	//@Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("App.actionPerformed() ");
    }
    
    
	//water key events
	public void keyTyped(KeyEvent e) {
		//System.out.println("App.keyTyped() " + "typed - " + e.getKeyCode());
	}

    
	//steer boat
	//37, 38, 39, 40
	public void keyPressed(KeyEvent e) {
        String action = "no action found";
        doAction(action, true, e.getModifiersEx());
	}

    
    @Override
	public void keyReleased(KeyEvent e)
	{
		//System.out.println("keyReleased() " + "keyCode=" + e.getKeyCode());
	}
    
    
    public void doAction(String action, boolean isPressed, int mods){
        //if (action.equals("ESC")){
        //    System.exit(0);
        //}
    }
    
    
    //window focus events
    public void windowGainedFocus(WindowEvent e){
		System.out.println("App.windowFocusGained() ");
    }


    public void windowLostFocus(WindowEvent e){
		System.out.println("App.windowFocusLost() ");
    }
    
    
    public void focusLost(FocusEvent e){
		System.out.println("App.focusLost() ");
    }
    
    
    public void focusGained(FocusEvent e){
		System.out.println("App.focusGained() ");
    }


    public String getAppletInfo() {
        return "Sailing by Peter Ruffner\n";
    }


    public String[][] getParameterInfo() {
        String[][] info = {
        };
        return info;
    }
}
