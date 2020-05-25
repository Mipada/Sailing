/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.mass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import my.app.App;
import my.app.boat.boat.Course;
import my.app.environment.Cube;
import my.app.environment.Environment;
import static my.app.utilities.Utils.pad;
import static my.app.utilities.Utils.print;
import static my.app.utilities.Utils.println;
import my.app.vector3.Vector3;
import my.app.water.Water;

/**
 *
 * @author Peter
 */
public class Mass extends Node{
    static public int count = 0;
    
    public App app;
    protected Environment env;
    protected Water water;
    protected float mass = 10f;
    //protected Cube area = new Cube();//shape bounds
    //protected float length = 30f;//x
	//protected float width = 10f;//y
    protected float course = 0f;
    //protected float turn = 0f;
    //protected float speed = 0f;
    //protected float maxSpeed = 0f;
    protected boolean debug = false;
    protected boolean showRing = false;
    protected boolean showCrosshairs = false;
    
    public Mass(){ 
        this(null);
    }
    
    
    public Mass(String name){
        super(name);
        setShowRing(false);
        setShowCrosshairs(false);
    }
    
    
    public Mass(String name, Vector3 location, Vector3 orientation){
        super(name, location, orientation);
        //System.out.println("Mass.Mass()1 " + ", count=" + count + ", ID=" + ID);
        setShowRing(false);
        setShowCrosshairs(false);
    }
    
    
    public Mass(String name, Vector3 location, Vector3 orientation, float course, float speed, float maxSpeed){
        super(name, location, orientation);
        //System.out.println("Mass.Mass()1 " + ", count=" + count + ", ID=" + ID);
        setSpeed(speed);
        setMaxSpeed(maxSpeed);
        setShowRing(false);
        setShowCrosshairs(false);
    }
    
    
    //shape bounds
    public Mass(String name, Vector3 location, Vector3 orientation, Vector3 dimension, float course, float speed, float maxSpeed, Cube world, Water water, Environment env, App app){
        super(name, location, orientation, dimension);
        //System.out.println("Mass.Mass()1 " + ", ID=" + ID + ", name=" + name);
        setCourse(course);
        setSpeed(speed);
        setMaxSpeed(maxSpeed);
        //setWorld(world);
        setWater(water);
        setEnvironment(env);
        setApp(app);
        setShowRing(false);
        setShowCrosshairs(false);
        //System.out.println("Mass.Mass()2 " + "count=" + count + ", ID=" + ID + ", position=" + this.location.toString(0));
        //if (ID == 0){
        //}
    }
    
    
    public int getID(){
        return ID;
    }
    
    
    public void setWater(Water water){
        this.water = water;
    }
    
    
    public Water getWater(){
        return water;
    }
    
    
    public void setEnvironment(Environment env){
        this.env = env;
    }
    
    
    public Environment getEnvironment(){
        return env;
    }
    
    
    public void setLocation(Vector3 location){
        this.location = location;
    }
    
    
    public Vector3 getLocation(){
        return location;
    }
    
    
    public void setVelocity(Vector3 velocity){
        this.velocity = velocity;
    }
    
    
    public Vector3 getVelocity(){
        return velocity;
    }
    
    
    public void setCourse(float degree){
        this.course = degree;
    }
    
    
    public float getCourse(){
        return course;
    }
    
    
    public Vector3 getOrientation(){
        return orientation;
    }
    
    
    //public void addDegree(float deg){
    //    orientation.y = orientation.y + (float)Math.toRadians(deg);
        //return;
    //}
    
    
/*    
    public void accelerate(Vector3 dv){
        velocity = velocity.accelerate(dv);
    }
    
    
    public void accelerate(Vector3 dv, Cube max){
        velocity = velocity.accelerate(dv, max);
    }
*/  
    
    
    @Override
    public void move(float tpf){
        move(null, tpf, 0);
    }
    
    
    public void move(Cube world, float tpf){
        move(world, tpf, 0);
    }
    
    
    @Override 
    public void move(Cube world, float tpf, int level){
        //println(pad(level) + "Mass.move(f, i) " + "ID=" + ID + ", name=" + getName());
        if (turnRight){
            //println("Mass.turningRight() ");
            doTurnRight();
            turnRight = false;
        }
        if (turnLeft){
            //println("Mass.turnintRight() ");
            doTurnLeft();
            turnLeft = false;
        }
        super.move(world, tpf, level);
    }
    
    
    
    @Override
    public void draw(Cube world, Graphics g){
        //println("Mass.draw(G) ");
        super.draw(g);
        draw(g, 0);
    }

    
    @Override
    public void draw(Cube world, Graphics g, int level){
        //println("Mass.draw(G, i) " + "ID=" + ID + ", level=" + level);
        super.draw(world, g, level);
        //println("Mass.draw(G, i) " + "ID=" + ID + ", name=" + name + ", location=" + location.toString(0) + ", velocity=" + velocity + ", children=" + children.size() + ", showRing=" + showRing + ", showCrosshairs=" + showCrosshairs);

        drawMassM(g, location, Color.black);

        Cube area = world.scale(0f, 0f, 0f);
        Vector3 loc = area.validate(location);
        if (loc != null){
            //if (ID == 0) println("Boat.draw() (echo) " + ", pos2=" + pos2.toString(0));
            this.drawMassM(g, loc, Color.red);
        }
    }//draw(G)
    
    
    @Override
    public void draw(Graphics g){
        //println("Mass.draw(G) ");
        super.draw(g);
        draw(g, 0);
    }

    
    @Override
    public void draw(Graphics g, int level){
        //println("Mass.draw(G, i) " + "ID=" + ID + ", level=" + level);
        super.draw(g, level);
        //println("Mass.draw(G, i) " + "ID=" + ID + ", name=" + name + ", location=" + location.toString(0) + ", velocity=" + velocity + ", children=" + children.size() + ", showRing=" + showRing + ", showCrosshairs=" + showCrosshairs);

        drawMassM(g, location, Color.black);
    }//draw(G)
    
    
    public void drawMassM(Graphics g, Vector3 loc, Color color){
        //if (ID == 0) 
        //println("Mass.drawMassM() " + "ID=" + ID + ", showCrosshairs=" + showCrosshairs + ", showRing=" + getShowRing());
		if (showCrosshairs) drawCrosshairs(g, loc);
		if (getShowRing()) drawRing(g, loc);
    }
    
    
	//draw crosshairs
	public void drawCrosshairs(Graphics g, Vector3 loc){
        if (false) println("Mass.drawCrosshairs() " + "ID=" + ID + ", course=" + course + ", location=" + location.toString(0) + ", dimension=" + dimension.toString(0));

        //float deg = course;
        int x1, y1, x2, y2;
        float deg = course;

        x1 = loc.getIntX();
        y1 = loc.getIntY();
        x2 = (int)(loc.getIntX() + (Math.cos(Math.toRadians(deg + 0)) * (dimension.x * 1.5f)));
		y2 = (int)(loc.getIntY() + (Math.sin(Math.toRadians(deg + 0)) * (dimension.x * 1.5f)));
		g.setColor(Color.black);
		//g.setColor(Color.red);
		g.drawLine(x1, y1, x2, y2);

		//draw aft line
		x2 = (int)(loc.getIntX() - (Math.cos(Math.toRadians(deg + 0f)) * (dimension.x * 3f)));
		y2 = (int)(loc.getIntY() - (Math.sin(Math.toRadians(deg + 0f)) * (dimension.x * 3f)));
		g.drawLine(x1, y1, x2, y2);

        //draw starboard line
		x2 = (int)(loc.getIntX() + (Math.cos(Math.toRadians(deg + 90f)) * (dimension.x * 1.5f)));
		y2 = (int)(loc.getIntY() + (Math.sin(Math.toRadians(deg + 90f)) * (dimension.x * 1.5f)));
		g.drawLine(x1, y1, x2, y2);

		//draw port line
		x2 = (int)(loc.getIntX() + (Math.cos(Math.toRadians(deg - 90f)) * (dimension.x * 1.5f)));
		y2 = (int)(loc.getIntY() + (Math.sin(Math.toRadians(deg - 90f)) * (dimension.x * 1.5f)));
		g.drawLine(x1, y1, x2, y2);
}
    
    
	//draw ring
	public void drawRing(Graphics g, Vector3 loc){
        //println("Mass.drawRing() " + "ID=" + ID);
		int x1 = (int)(loc.getIntX() - dimension.x * 0.5f);
		int y1 = (int)(loc.getIntY() - dimension.x * 0.5f);
        int w = (int)dimension.x;
        int h = (int)dimension.x;

        g.setColor(Color.red);
		g.setColor(Color.white);
		g.drawOval(x1, y1, w, h);
	}
    
    
    public void setShowRing(boolean b){
        this.showRing = b;
        //println("Mass.setShowRing(b) " + "ID=" + ID + ", showRing=" + this.showRing);
    }
    
    
    public boolean getShowRing(){
        return showRing;
    }


    public void toggleShowRing(){
        showRing = showRing == false;
        //println("Mass.toggleShowRing(b) " + "ID=" + ID + ", showRing=" + this.showRing);
    }


    public void setShowCrosshairs(boolean b){//key X
        this.showCrosshairs = b;
        //println("Mass.setShowCrosshairs(b) " + "ID=" + ID + ", showCrosshairs=" + showCrosshairs);
    }
    
    
    public boolean getShowCrossharis(){
        return showCrosshairs;
    }


    public void toggleShowCrosshairs(){
        this.showCrosshairs = showCrosshairs == false;
        //println("Mass.toggleShowCrosshairs(b) " + "ID=" + ID + ", showCrosshairs=" + showCrosshairs);
    }


    public String toString(){
        String s = new String("[Mass] ID=" + ID + ", position=" + location.toString(0));
        return s;
    }
    
}
