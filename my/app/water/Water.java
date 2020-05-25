//Water by
//Peter Ruffner

package my.app.water;

import my.app.environment.Environment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics.*;
import java.awt.Graphics2D;
import java.awt.Point;
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
//import javax.swing.Timer;
import java.util.Timer;
import my.app.App;
import my.app.Sailing;
import my.app.boat.boat.Boat;
import my.app.mass.Mass;
import my.app.boat.boat.Sailboat;
import my.app.environment.Cube;
import my.app.environment.Wind;
import my.app.mass.Node;
import my.app.mass.Spatial;
import my.app.size.Size;
import static my.app.utilities.Utils.println;
import my.app.vector3.Vector3;

//other imports
//import ruffner.peter.*;
//import ruffner.peter.WaterPkg.*;
//import ruffner.peter.WaterPkg.SailboatPkg.*;

public class Water extends Environment implements ActionListener, MouseInputListener, KeyListener, FocusListener//Runnable, 
{
//public class Water extends JPanel implements MouseMotionListener {
//public class Water extends JPanel implements MouseEvent {
	//static final int NUMBOATS = 5;
    public Timer timer = null;
    public Thread runner = null;
    public int fps = 30;
    //public int speed = 30;
    public int delay = 1000/fps;
    public float oldTime = 0f;
    public float tpf = 0f;
    public boolean running = false;
    public boolean paused = false;
    public boolean showArcs = false;
    public boolean showRing = false;
    public boolean showPOS = false;
    public boolean showCrosshairs = false;

    Wind wind;
	int currentBoat = 0;
	Line2D[] line = new Line2D.Double[10];
    int linex1 = 0;
    int liney1 = 0;
    int linex2;
    int liney2;
	int lineindex = 0;
    Cube area;
    boolean debug = false;
    
    
    public Water(){
        this(null);
    }
    
    
    public Water(String name){
        super(name);
    }

	public Water(String name, Cube world, App app)
	{
		super(name, world, app); //color was added by GUI setup //new BorderLayout()
        System.out.println("Water.Water(String, App) " + "name=" + getName());

		//setSize(w, h);
        //setAlignmentX(0.5f);

		setBackground(Color.blue);
		//setSize(new Dimension(680, 350));
		ControlPanel controlPanel = new ControlPanel();
		controlPanel.setOpaque(true);
		controlPanel.setBackground(Color.white);

		//add(controlPanel, BorderLayout.SOUTH);
        //world.set(this.getX(), getY(), -Float.MAX_VALUE, getWidth(), getHeight(), Float.MAX_VALUE);
        //wind = new Wind(new Location3(getWidth() - 150, 50, 0), 50, 180f, 6f);
        float maxSpeed = 10f;
        wind = new Wind("Wind", new Vector3(getWidth() - 150, 50, 0, Vector3.TYPE_SIZE), new Vector3(Vector3.TYPE_SIZE), 180f, (float)Math.random() * maxSpeed, maxSpeed);
        //setup lines
		for (int i = 0;i < line.length;i++){
			line[i] = new Line2D.Double(20, 182, 20, 182);
		}

		//for (int i = 0; i < NUMBOATS;i++)
		//{
		//	//d = new Dimension(d.width, d.height - 46);//-46 (edges of panel - sail area)
		//	d = new Dimension(8, 25);
		//	addMass(new Sailboat(d));
		//}
        //init();
        //System.out.println("Water.Water() done");
	}
    
    
    @Override
    public void initialize(){
        super.initialize();
        println("Water.initialize() ");
        //timer = new Timer(speed, this);
        //timer.setInitialDelay(10);
        //timer.start();
        //addKeyListener(this);
        //addMouseListener(this);
        //addFocusListener(this);
		//requestFocusInWindow();
        //requestFocus();
        //System.out.println("Water.init() done");
    }
    
    
    public void addFPS(int n){
        fps += n;
        if (fps < 5){
            fps = 5;
        }
        else if (fps > 10000){
            fps = 10000;
        }

        delay = (1000/fps);
        if (delay < 0){
            delay = 0;
        }
        //else if (delay > 1000){
        //    delay = 1000;
        //}
        println("fps=" + fps + ", delay=" + delay);
    }
    
    
    public Wind getWind(){
        return wind;
    }


    @Override
    public void setLocation(int x, int y){
        super.setLocation(x, y);
        //System.out.println("Water.setLocation(" + x + ", " + y + ")");
        
    }


    @Override
    public void setLocation(Point p){
        super.setLocation(p);
        //System.out.println("Water.setLocation(" + p + ")");
        
    }


    @Override
    public void setBounds(int x, int y, int w, int h){
        super.setBounds(x, y, w, h);
        //System.out.println("Water.setBounds(" + x + ", " + y + ", " + w + ", " + h + ")");
        
    }
    

	@Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Water.actionPerformed() ");
        System.out.println("Water.actionPerformed() " + "e=" + e + " should not get here");
        //float tpf = fps/1000f;
        //move1(tpf);
        //draw(getGraphics());
        //water.repaint();
        //repaint();
    }
    
/*
    @Override
	public void move(float tpf){
        super.move(tpf);
        System.out.println("Water.move(f)");
        //if (isMyFrame) println("Water.move() " + "isMyFrame=" + true);
        //if (mass.length == 0) return;
        rootNode.move(tpf);
	}
*/

    public void input(float tpf){
        
    }
    
/*
    @Override
    public void update(Graphics g){
		//println("Water.update() ");
        if (g == null){
            println("Water.update(G) " + "g=null");
            Thread.dumpStack();
            System.exit(1);
        }
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        paint(g);
    }
    
    
    //see paintComponent below
    @Override
    public void paint(Graphics g)
    {
		//System.out.println("Water.paint(G) ");
        paintComponent(g);
        paintBorder(g);
        paintChildren(g);
    }
    
*/
	@Override
	public void paintComponent(Graphics g) {
		paintComponent(world, g);
    }
    
    
	@Override
	public void paintComponent(Cube world, Graphics g) {
        println("Water.paintComponent() ");
		super.paintComponent(g);

		int w = getSize().width;
		int h = getSize().height;
		int h2 = getSize().height/2;
        
        //println("Water.paintComponent() " + "drawWeighpoints");
        drawWeighpoints(h2, g);

        //println("Water.paintComponent() " + "draw wind");
        wind.draw(g);

        //println("Water.paintComponent() " + "draw spatials");
        //((Sailboat)rootNode).draw(g);//can't caste
        rootNode.draw(world, g);
	}//Water.paintComponent

    
    
    public void drawWeighpoints(int c, Graphics g){
		g.setColor(Color.white);

		//x, y, x1, y1, inc, radius, style of line, graphics
		drawStylizedLine(20 + (0 * 10),c - (0 * 10), 20, c - (7 * 10), 10, 2, 1, g);
		drawStylizedLine(20 + (0 * 10),c - (7 * 10), 20 + (14 * 10), c + (7 * 10), 10, 2, 1, g);
		drawStylizedLine(20 + (14 * 10),c + (7 * 10), 20 + (28 * 10), c - (7 * 10), 10, 2, 1, g);
		drawStylizedLine(20 + (28 * 10),c - (7 * 10), 20 + (42 * 10), c + (7 * 10), 10, 2, 1, g);
		drawStylizedLine(20 + (42 * 10),c + (7 * 10), 20 + (56 * 10), c - (7 * 10), 10, 2, 1, g);
		drawStylizedLine(20 + (56 * 10),c - (7 * 10), 20 + (63 * 10), c + (0 * 10), 10, 2, 1, g);
		drawStylizedLine(20 + (63 * 10),c - (0 * 10), 20 + (63 * 10), c + (7 * 10), 10, 2, 1, g);
		drawStylizedLine(20 + (63 * 10),c + (7 * 10), 20 + (56 * 10), c + (0 * 10), 10, 2, 1, g);
		drawStylizedLine(20 + (56 * 10),c + (0 * 10), 20 + (0 * 10), c + (0 * 10), 10, 2, 1, g);
    }
    
    
    public void setCurrentBoat(int n){
        currentBoat = n;
    }
    
    
    public Boat getCurrentBoat(){
        //return (Boat)mass[currentBoat];
        ArrayList<Spatial> list = ((Node)rootNode).getDescendants2(Boat.class);
        return (Boat)list.get(currentBoat);
    }
    
    
    public void setSelected(int n){
        currentBoat = n;
        ArrayList<Spatial> list = ((Node)rootNode).getDescendants2(Boat.class);
        for (int i = 0;i < list.size();i++){
            Spatial spatial = list.get(i);
            if (spatial instanceof Boat){
                ((Boat)spatial).setSelected(false);
            }
        }
        ((Boat)list.get(n)).setSelected(true);
    }
    
    
    public ArrayList<Spatial> getSelected(){
        //return (Boat)mass[currentBoat];
        ArrayList<Spatial> list = ((Node)rootNode).getDescendants2(Boat.class);
        ArrayList<Spatial> selected = new ArrayList();
        for (int i = 0;i < list.size();i++){
            Spatial spatial = list.get(i);
            if (spatial instanceof Boat){
                selected.add(spatial);
            }
        }
        return selected;
    }
    
    
    //public void turnLeft(String action, boolean isPressed){
    //    ((Boat)mass[currentBoat]).turnLeft();
    //}

    
    //public void turnRight(String action, boolean isPressed){
    //    ((Boat)mass[currentBoat]).turnRight();
    //}

    
    public void incSpeed(String action, boolean isPressed){
        Boat boat = getCurrentBoat();
        boat.addVelocity(1f);
    }

    
    public void decSpeed(String action, boolean isPressed){
        Boat boat = getCurrentBoat();
        boat.addVelocity(1f);
    }

    
    //rings, crosshairs, POS, Arcs
    public void toggleShowRing(){
        showRing = (showRing == false);
        ArrayList<Spatial> list = rootNode.getDescendants();
        for (int n = 0;n < list.size();n++){
            Spatial spatial = list.get(n);
            if (spatial instanceof Boat){
                ((Boat)spatial).setShowRing(showRing);
            }
        }
    }

    
    public void toggleShowCrosshairs(){
        showCrosshairs = (showCrosshairs == false);
        ArrayList<Spatial> list = rootNode.getDescendants();
        for (int n = 0;n < list.size();n++){
            Spatial spatial = list.get(n);
            if (spatial instanceof Boat){
                ((Boat)spatial).setShowCrosshairs(showCrosshairs);
            }
        }
    }

    
    public void toggleShowPOS(){
        showPOS = (showPOS == false);
        ArrayList<Spatial> list = rootNode.getDescendants();
        for (int n = 0;n < list.size();n++){
            Spatial spatial = list.get(n);
            if (spatial instanceof Sailboat){
                ((Sailboat)spatial).setShowPOS(showPOS);
            }
        }
    }

    
    public void toggleShowArcs(){
        showArcs = (showArcs == false);
        ArrayList<Spatial> list = rootNode.getDescendants();
        for (int n = 0;n < list.size();n++){
            Spatial spatial = list.get(n);
            if (spatial instanceof Sailboat){
                ((Sailboat)spatial).setShowArcs(showArcs);
            }
        }
    }

    
    //public void showArcs(String action, boolean isPressed){
    //    Sailboat sailboat = (Sailboat)getCurrentBoat();
    //    sailboat.setShowArcs(isPressed);
    //}

    
	public void makeBoats(int n){
		Dimension d = getSize();
		//System.out.println(d);
		Size l = new Size(d.width, d.height - 46);//-46 = ???
		for (int i = 0;i < n;i++)
		{
			//mass[i] = new Sailboat(this, 30, 10);
		}
	}

	//when the hypotanuse of the right triangle is a multiple of 'inc', draw a dot.
	//c = sqrt of a^2 + b^2
	//c % (mod) inc
	public void drawStylizedLine(int x0, int y0, int x1, int y1, int inc, int r, int style, Graphics g) {
		//int pix = color.getRGB();
		//int pix = g.getColor().getRGB();
		int dy = y1 - y0;
		int dx = x1 - x0;
		float t = (float) 0.5;                      // offset for rounding
		int sy = y0;
		double re;
		double re2;
		float xv;
		float yv;
		float xv2;
		float yv2;
		int i2;

		//System.out.println("line = (" + x0 + "," + y0 + "),(" + x1 + "," + y1 + ")");
		//g.drawLine(x0,y0,x1,y1);

		//raster.setPixel(pix, x0, y0);
		if (Math.abs(dx) > Math.abs(dy)) {          // slope < 1
			int sx = x0;
			float m = (float) dy / (float) dx;      // compute slope
			t += y0;
			dx = (dx < 0) ? -1 : 1;
			m *= dx;
			g.fillOval(x0-r, (int)t-r, (r * 2) + 1, (r * 2) + 1);
			re2 = -inc;
			while (x0 != x1) {
				x0 += dx;                           // step to next x value
				t += m;                             // add slope to y value

				//
				//System.out.println("x0=" + x0 + "/x1=" + x1 + "/t=" + t + "/xv=" + (x0 - sx));
				xv = sx - x0;
				yv = t - sy;
				re = Math.sqrt((xv*xv) + (yv*yv))%inc; //xv^2 does not work - must use xv*xv
				//System.out.println("1) xv=" + xv + "/yv=" + yv + "/xv^2=" + xv * xv + "/yv^2=" + yv * yv + "/sqr=" + (xv * xv + yv * yv) + "/sqrt=" + (int)Math.sqrt((xv * xv +yv * yv)) + "/re=" + (int)Math.sqrt((xv*xv) + (yv*yv))%inc);
				//System.out.println("1) re=" + re + "/re2=" + re2);

				if (re == 0 || re2 > re) {
					g.fillOval(x0-r, (int)t-r, r * 2, r * 2);
				}
				re2 = re;
			}
			g.fillOval(x0-r, (int)t-r, (r * 2) + 1, (r * 2) + 1);
		} else {                                    // slope >= 1
			int sx = x0;
			float m = (float) dx / (float) dy;      // compute slope
			t += x0;
			dy = (dy < 0) ? -1 : 1;
			m *= dy;
			re2 = inc;
			g.fillOval((int)t-r, y0-r, (r * 2) + 1, (r * 2) + 1);
			while (y0 != y1) {
				y0 += dy;                           // step to next y value
				t += m;                             // add slope to x value

				xv = sx - (float)(t-0.5);
				yv = sy - y0;
				//i2 = (int)Math.sqrt((xv*xv) + (yv*yv))%inc; //xv^2 does not work - must use xv*xv
				re = Math.sqrt((xv*xv) + (yv*yv))%inc; //xv^2 does not work - must use xv*xv
				//System.out.println("2) xv=" + xv + "/yv=" + yv + "/xv^2=" + xv * xv + "/yv^2=" + yv * yv + "/sqr=" + (xv * xv + yv * yv) + "/sqrt=" + (int)Math.sqrt((xv * xv +yv * yv)) + "/re=" + (int)Math.sqrt((xv*xv) + (yv*yv))%inc);
				//System.out.println("2) re=" + re + "/re2=" + re2);

				if (re == 0 || re2 > re) {
					//System.out.println("xv=" + xv + "/yv=" + yv + "/xv^2=" + xv * xv + "/yv^2=" + yv * yv + "/sqr=" + ((xv * xv) + (yv * yv)) + "/sqrt=" + (int)Math.sqrt(((xv * xv) + (yv * yv))) + "/mod=" + (int)Math.sqrt((xv*xv) + (yv*yv))%inc);
					g.fillOval((int)t-r, y0-r, (r * 2)+1, (r * 2)+1);
				}
				re2 = re;
			}
			g.fillOval((int)t-r, y0-r, (r * 2)+1, (r * 2)+1);
		}
	}


	public void drawStylizedLine2(int x0, int y0, int x1, int y1, int inc, int r, int style, Graphics2D g2) {
		//int pix = color.getRGB();
		//int pix = g.getColor().getRGB();
		int dy = y1 - y0;
		int dx = x1 - x0;
		float t = (float) 0.5;                      // offset for rounding
		int sy = y0;
		double re;
		double re2;
		float xv;
		float yv;
		float xv2;
		float yv2;
		int i2;

		//System.out.println("line = (" + x0 + "," + y0 + "),(" + x1 + "," + y1 + ")");
		//g2.drawLine(x0,y0,x1,y1);

		//raster.setPixel(pix, x0, y0);
		if (Math.abs(dx) > Math.abs(dy)) {          // slope < 1
			int sx = x0;
			float m = (float) dy / (float) dx;      // compute slope
			t += y0;
			dx = (dx < 0) ? -1 : 1;
			m *= dx;
			g2.fillOval(x0-r, (int)t-r, (r * 2) + 1, (r * 2) + 1);
			re2 = -inc;
			while (x0 != x1) {
				x0 += dx;                           // step to next x value
				t += m;                             // add slope to y value

				//
				//System.out.println("x0=" + x0 + "/x1=" + x1 + "/t=" + t + "/xv=" + (x0 - sx));
				xv = sx - x0;
				yv = t - sy;
				re = Math.sqrt((xv*xv) + (yv*yv))%inc; //xv^2 does not work - must use xv*xv
				//System.out.println("1) xv=" + xv + "/yv=" + yv + "/xv^2=" + xv * xv + "/yv^2=" + yv * yv + "/sqr=" + (xv * xv + yv * yv) + "/sqrt=" + (int)Math.sqrt((xv * xv +yv * yv)) + "/re=" + (int)Math.sqrt((xv*xv) + (yv*yv))%inc);
				//System.out.println("1) re=" + re + "/re2=" + re2);

				if (re == 0 || re2 > re) {
					g2.fillOval(x0-r, (int)t-r, r * 2, r * 2);
				}
				re2 = re;
			}
			g2.fillOval(x0-r, (int)t-r, (r * 2) + 1, (r * 2) + 1);
		} else {                                    // slope >= 1
			int sx = x0;
			float m = (float) dx / (float) dy;      // compute slope
			t += x0;
			dy = (dy < 0) ? -1 : 1;
			m *= dy;
			re2 = inc;
			g2.fillOval((int)t-r, y0-r, (r * 2) + 1, (r * 2) + 1);
			while (y0 != y1) {
				y0 += dy;                           // step to next y value
				t += m;                             // add slope to x value

				xv = sx - (float)(t-0.5);
				yv = sy - y0;
				//i2 = (int)Math.sqrt((xv*xv) + (yv*yv))%inc; //xv^2 does not work - must use xv*xv
				re = Math.sqrt((xv*xv) + (yv*yv))%inc; //xv^2 does not work - must use xv*xv
				//System.out.println("2) xv=" + xv + "/yv=" + yv + "/xv^2=" + xv * xv + "/yv^2=" + yv * yv + "/sqr=" + (xv * xv + yv * yv) + "/sqrt=" + (int)Math.sqrt((xv * xv +yv * yv)) + "/re=" + (int)Math.sqrt((xv*xv) + (yv*yv))%inc);
				//System.out.println("2) re=" + re + "/re2=" + re2);

				if (re == 0 || re2 > re) {
					//System.out.println("xv=" + xv + "/yv=" + yv + "/xv^2=" + xv * xv + "/yv^2=" + yv * yv + "/sqr=" + ((xv * xv) + (yv * yv)) + "/sqrt=" + (int)Math.sqrt(((xv * xv) + (yv * yv))) + "/mod=" + (int)Math.sqrt((xv*xv) + (yv*yv))%inc);
					g2.fillOval((int)t-r, y0-r, (r * 2)+1, (r * 2)+1);
				}
				re2 = re;
			}
			g2.fillOval((int)t-r, y0-r, (r * 2)+1, (r * 2)+1);
		}
	}


	//water panel events
	//click- save position
    @Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mouse pressed - " + e.getX() + "," + e.getY());
        
		linex1 = e.getX();
		liney1 = e.getY();
		linex2 = e.getX();
		liney2 = e.getY();
		//showStatus(e.getPoint().toString());
	}

	//add line to line list, drawn later
	public void mouseReleased(MouseEvent e) {
		//System.out.println("mouse released");
		lineindex++;
		if (lineindex > line.length - 1){
			lineindex = 0;
		}
		//System.out.println("lineindex=" + lineindex);
		//showStatus(e.getPoint().toString());
	}

	public void mouseClicked(MouseEvent e) {
		//System.out.println("focusable? - " + isFocusable());
		//System.out.println("Setting focus");
		if (isFocusable()){
			requestFocusInWindow();
		}
		//showStatus(e.getPoint().toString());
	}

	public void mouseEntered(MouseEvent e) {
		//System.out.println("mouse ");
		//showStatus(e.getPoint().toString());
	}

	public void mouseExited(MouseEvent e) {
		//System.out.println("mouse ");
		//showStatus(e.getPoint().toString());
	}

	public void mouseMoved(MouseEvent e) {
		//System.out.println("mouse moved");
		//showStatus(e.getPoint().toString());
	}

	//move end of line
	public void mouseDragged(MouseEvent e) {
		line[lineindex].setLine(linex1, liney1, e.getX(), e.getY());
		String s = "(" + linex1 + "," + liney1 + "),(" + linex2 + "," + liney2 + ")";
		//showStatus(s);
	}

    
	//water key events
	public void keyTyped(KeyEvent e) {
		//System.out.println("typed - " + e.getKeyCode());

	}

	//steer boat
	//37, 38, 39, 40
    @Override
	public void keyPressed(KeyEvent e) {
/*
		System.out.println("Water.keyPressed() " + "keyCode=" + e.getKeyCode() + "keyChar=" + e.getKeyChar());
        String action = new String("none");
		//Sailboat sb = (Sailboat) mass[currentMass];
		if (e.getKeyChar() == 'A'){
            action = "SHOWARCS";
		}
        else if (e.getKeyChar() == 'P'){
            action = "SHOWPOS";
		}
		else if (e.getKeyChar() == 'X'){
            action = "SHOWCROSSHAIRS";
		}
		else if (e.getKeyCode() == 27){
            action = "ESC";
		}
		else if (e.getKeyCode() == 37){	//left arrow
            action = "LeftArrow";
		}
		else if (e.getKeyCode() == 38){	//up arrow
            action = "UpArrow";
		}
		else if (e.getKeyCode() == 39){	//right arrow
			action = "RightArrow";
		}
		else if (e.getKeyCode() == 40){	//down arrow
			action = "DownArrow";
		}
		else if (e.getKeyCode() == 80){	//p
			action = "Pause";
		}
        doAction(action, true, e.getModifiersEx());
*/
	}

    
    @Override
	public void keyReleased(KeyEvent e)
	{
        //String action = new String("none");
		//System.out.println("Water.keyReleased - " + e.getKeyCode());
	}
    
    
    public void doAction(String action, boolean isPressed, int modifiers){
/*
        if (action.equals("ESC")){
            System.exit(0);
        }
        else if (action.equals("SHOWCROSSHAIRSS")){
            ((Boat)mass[currentBoat]).setShowCrosshairs(isPressed);
        }
        else if (action.equals("SHOWARCS")){
            ((Boat)mass[currentBoat]).setShowArcs(isPressed);
        }
        else if (action.equals("SHOWPOS")){
            ((Boat)mass[currentBoat]).setShowPOS(paused);
        }
        else if (action.equals("LeftArrow")){
            ((Boat)mass[currentBoat]).turnLeft();
        }
        else if (action.equals("RightArrow")){
            ((Boat)mass[currentBoat]).turnRight();
        }
        else if (action.equals("UpArrow")){
            ((Boat)mass[currentBoat]).incSpeed();
        }
        else if (action.equals("DownArrow")){
            ((Boat)mass[currentBoat]).decSpeed();
        }
        else if (action.equals("Pause")){
            paused = (paused == false);
            System.out.println("paused=" + paused);
            //setPaused(paused);
        }
*/
    }
    
    
    
    
    public void focusLost(FocusEvent e){
		System.out.println("Water.focusLost() ");
    }
    
    
    public void focusGained(FocusEvent e){
		System.out.println("Water.focusGained() ");
    }


    public class ControlPanel extends JPanel implements MouseInputListener, KeyListener {
        public ControlPanel(){
            super();
            setBackground(Color.white);
            add(new JButton("="));
            add(new JButton(">"));
            addKeyListener(this);
            requestFocusInWindow();
        }

        public void keyTyped(KeyEvent e) {
            System.out.println("typed");
        }

        public void keyPressed(KeyEvent e) {
            System.out.println("pressed");
        }

        public void keyReleased(KeyEvent e) {
            System.out.println("released");
        }

        //control panel mouse events
        public void mousePressed(MouseEvent e) {
            System.out.println("mouse pressed - " + e.getX() + "," + e.getY());
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
            System.out.println("focusable? - " + isFocusable());
            if (isFocusable()){
                requestFocusInWindow();
            }
            ////showStatus(e.getPoint().toString());
        }

        public void mouseEntered(MouseEvent e) {
            //System.out.println("mouse ");
        }

        public void mouseExited(MouseEvent e) {
            //System.out.println("mouse ");
        }

        public void mouseMoved(MouseEvent e) {
            //System.out.println("mouse moved");
            //showStatus(e.getPoint().toString());
        }

        public void mouseDragged(MouseEvent e) {
            linex2 = e.getX();
            liney2 = e.getY();
            String s = "(" + linex1 + "," + liney1 + "),(" + linex2 + "," + liney2 + ")";
            //showStatus(s);
        }
    }
    
    //type, ?, length, 
/*
    int oldMassData[][] = new int[][] 
    {
        {1, 1,  12, 0, 0, 10, 1},
        {2, 2,  30, 0, 0, 10, 1},
        {3, 3,  50, 0, 0, 15, 1},
        {4, 4,  75, 0, 0, 17, 1},
        {5, 5, 100, 0, 0, 20, 1}
    };
*/    
    
    
    
}
