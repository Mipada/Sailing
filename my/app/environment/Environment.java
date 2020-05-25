package my.app.environment;

//Env by
//Peter Ruffner

//package ruffner.peter.WaterPkg;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics.*;
import java.awt.Graphics2D;
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
import my.app.mass.Mass;
import my.app.mass.Node;
import my.app.mass.Spatial;
import static my.app.utilities.Utils.println;
import my.app.vector3.Vector3;

//other imports
//import ruffner.peter.*;
//import ruffner.peter.WaterPkg.*;
//import ruffner.peter.WaterPkg.SailboatPkg.*;

public class Environment extends JPanel// implements FocusListener// implements MouseInputListener, KeyListener //
{
//public class Water extends JPanel implements MouseMotionListener {
//public class Water extends JPanel implements MouseEvent {

    //public JFrame theFrame;
    protected App app;
    //public JPanel thePanel;
    //public Component canvas;
    protected Node rootNode;
    protected String name = new String();
    protected Cube world = new Cube();
	//protected Mass[]	mass  = new Mass[20];
	protected int		currentMass = 0;
    //public JPanel theScreen = new JPanel();
    //public Canvas theScreen = new Canvas();
    protected boolean isFrame = true;
    
    
    public Environment(){
        this("no name");
    }

    
    public Environment(String name){
        this.name = name;
    }

    
	public Environment(String name, Cube world, App app){
        super();
        setName(name);
        setWorld(world);
        setApp(app);
        //this.rootNode = app.getRootNode();
        System.out.println("Environment.Environment(String, App) " + "name=" + getName());

        //thePanel = new JPanel();
        //setBounds(700, 350, 900, 700);
        ///setVisible(true);
        isFrame = false;
	}
    
    
    public void setWorld(Cube world){
        this.world = world;
    }

    
    public Cube getWorld(){
        return world;
    }

    
    public void setApp(App app){
        this.app = app;
    }

    
    public App getApp(){
        return app;
    }

/*
	public Environment(String name, JFrame theFrame){
        super();

        //theFrame = new JFrame("Sailing (env)...");
        //this.theFrame = theFrame;
        isFrame = true;
	}
*/
    
    //@Override
    public void initialize(){
		System.out.println("Environment.initialize()");
        rootNode = app.getRootNode();
        
/*
        if (isFrame){
    		System.out.println("Environment.init() " + "isFrame=" + isFrame);
            //theFrame.setLayout(new BorderLayout());
            //theFrame.setBackground(Color.black);
            //theFrame.addFocusListener(this);
        }
        else{
    		System.out.println("Environment.init() " + "isFrame=" + isFrame);
            setLayout(new BorderLayout());
            setBackground(Color.green);
            //addFocusListener(this);
        }
*/
	}


/*    
	public Environment(BorderLayout l)
	{
		super(l); //color was added by GUI setup
        //setBounds(100, 150, 550, 450);

        theFrame = new JFrame("Sailing...");
        theFrame.setBounds(700, 350, 900, 700);
        theFrame.setVisible(true);
        
        //setVisible(true);
		//Dimension d = getSize();
		//setBackground(Color.black);
	}
*/
    
    //public JFrame getJFrame(){
    //    return theFrame;
    //}
    
    
    //public JPanel getJPanel(){
    //    return thePanel;
    //}
    
/*    
    public Graphics getGraphics(){
        return theScreen.getGraphics();
    }
*/
/*
	public void addMass(Mass m)
	{
		for (int i = 0;i < mass.length;i++)
		{
			if (mass[i] == null)
			{
				//System.out.println("Add mass[" + i + "]=" + mass);
				mass[i] = m;
				break;
			}
		}
	}
*/
    //@Override
	public void move(float tpf)
	{
		//System.out.println("Environment.move(f) " + "rootNode=" + rootNode);
        rootNode.move(world, tpf);
        //for (Node node : rootNode.getChildren()){
        //    node.move(tpf);
        //}
        //repaint();
	}
    

    @Override
    public void update(Graphics g){ 
		System.out.println("Environment.update(G)");
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        paint(g);
    }
    
    
    @Override
    public void paint(Graphics g){
		//println("Environment.paint(G)");
        paintComponent(g);
        paintBorder(g);
        paintChildren(g);
    }
    
    
    @Override
    public void paintComponent(Graphics g){
		//println("Environment.paintComponent(G) " + "name=" + getName());
		//println("Environment.paintComponent(G) " + "name=" + getName() + ", rootNode children=" + rootNode.getChildren().size());
        super.paintComponent(g);
        //paintComponent(world, g);
    }
    
    
    //@Override
    public void paintComponent(Cube world, Graphics g){
		//println("Environment.paintComponent(G) " + "name=" + getName());
		//println("Environment.paintComponent(G) " + "name=" + getName() + ", rootNode children=" + rootNode.getChildren().size());
        super.paintComponent(g);
        //paintComponent(world, g);
    }
    
    
    public void input(){
        
    }
    
    
    public void focusLost(FocusEvent e){
		System.out.println("Environment.focusLost() ");
    }
    
    
    public void focusGained(FocusEvent e){
		System.out.println("Environment.focusGained() ");
    }



}
