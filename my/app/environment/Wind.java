/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javafx.scene.shape.Line;
import my.app.mass.Mass; 
import static my.app.utilities.Utils.println;
import my.app.vector3.Vector3;
import my.app.water.Water;

/**
 *
 * @author Peter
 */
public class Wind extends Mass{
    
    public Wind(){
        this("blank");
    }
    
    
    public Wind(String name){
        super(name);
    }
    
    
    public Wind(String name, Wind wind){
        this.name = wind.name;
        this.location = wind.location;
        this.orientation = wind.orientation;
        this.speed = wind.speed;
    }
    
    
    public Wind(String name, Vector3 location, Vector3 orientation, float degree, float speed, float maxSpeed){
        super(name, location, orientation, degree, speed, maxSpeed);
        System.out.println("Wind=" + this);
    }
    
    
    //public Wind(String name, Vector3 location, Vector3 vector, float speed){
    //    super(name, location, new Vector3());
    //    System.out.println("Wind=" + this);
    //}
    
    
/*
    public Wind(Location3 center, int length, float direction, float speed){
        this.center = center;
        this.length = length;
        this.direction.set(direction);
        this.speed = speed;
        setVelocity(direction, speed);
        System.out.println("Wind=" + this);
    }
*/
    public float getVectorDegree(){//from vector to deg
        println("Wind.getVectorDegree() " + "vector may not be set. orientation.x=" + orientation.x + ", orientation.y=" + orientation.y);
        float degree = (float)Math.atan2(orientation.y, orientation.x);
        return degree;
    }
    

    public float getVectorRadian(){
        println("Wind.getVectorRadian() " + "vector may not be set. orientation.x=" + orientation.x + ", orientation.y=" + orientation.y);
        float degree = (float)Math.atan2(orientation.y, orientation.x);
        float rad = (float)Math.toRadians(degree);
        return rad;
    }
    
    
    public Vector3 getVelocity(){
        return velocity;
    }
    

    public float getStrength(){
        return getLength();
    }
    

    public float getLength(){
        return (float)Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z);
    }
    

    //public void addDirection(Vector3 d){
    //    velocity = velocity.add(d);
    //}
    
/*
    public void addDegree(float degree){
        course = course + degree;
        orientation = orientation.get(degree);
        //addRadian((float)Math.toRadians(xd), (float)Math.toRadians(yd), (float)Math.toRadians(zd));
    }
*/
    
    public void addDegrees(float degree){
        course = course + degree;
    }
    
/*    
    public void addDegree(float xd, float yd, float zd){
        
        addRadian((float)Math.toRadians(xd), (float)Math.toRadians(yd), (float)Math.toRadians(zd));
    }
    
    
    public void addRadian(float xd, float yd, float zd){
        
        orientation = orientation.add(xd, yd, yd);
    }
*/    
    
    //public void addSpeed(int v){
    //    speed += v;
    //}
    
    
    public void addVectorSpeed(Vector3 v){
        velocity.add(v);
    }
    
    
    public void draw(Graphics g){
        //println("Wind.draw(G) ");
        Graphics2D g2 = (Graphics2D)g;
        float deg = course;
        float rad = (float)Math.toRadians(deg);
        
        //loc.add((int)(Math.cos(Math.toRadians(direction)) * speed), (int)(Math.sin(Math.toRadians(direction)) * speed));
        int x = (int)(location.x + (Math.cos(rad) * 100f));//orientation.getRadian()
        int y = (int)(location.y + (Math.sin(rad) * 100f));
        int x2 = (int)(location.x - (Math.cos(rad) * 100f));
        int y2 = (int)(location.y - (Math.sin(rad) * 100f));
        //println("Wind.draw() " + "center=(" + center.x + ", " + center.y + "), x=" + x + ", y=" + y + ", x2=" + x2 + ", y2=" + y2);
        //println("Wind.draw() " + " direction=" + direction.get());
        g2.setColor(Color.black);
        //Line line = new Line();
        //line.set
        g2.setStroke(new BasicStroke(3));
        //main line
        g2.drawLine(x, y, x2, y2);
        //glifs
        g2.setColor(Color.black);
        g2.drawLine(x, y, (int)(x - (Math.cos(Math.toRadians(deg + 20f)) * 20f)), y - (int)(Math.sin(Math.toRadians(deg + 20f)) * 20));
        g2.drawLine(x, y, (int)(x - (Math.cos(Math.toRadians(deg - 20f)) * 20f)), y - (int)(Math.sin(Math.toRadians(deg - 20f)) * 20));
        g2.setColor(Color.black);
        g2.drawLine(x2, y2, x2 - (int)(Math.cos(Math.toRadians(deg + 20)) * 20), y2 - (int)(Math.sin(Math.toRadians(deg + 20f)) * 20));
        g2.drawLine(x2, y2, x2 - (int)(Math.cos(Math.toRadians(deg - 20)) * 20), y2 - (int)(Math.sin(Math.toRadians(deg - 20f)) * 20));
        g2.setStroke(new BasicStroke(1));
    }
    
    
    public String toString(){
        String s = new String("location=" + location + ", velocity=" + velocity + ", orientation=" + orientation + ",  length=" + getLength());
        return s;
    }
    
    
}
