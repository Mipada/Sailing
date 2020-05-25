/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.mass;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import my.app.App;
import my.app.environment.Cube;
import my.app.utilities.Utils;
import static my.app.utilities.Utils.pad;
import static my.app.utilities.Utils.println;
import my.app.vector3.Vector3;

/**
 *
 * @author Peter
 */
public class Spatial {
    
    protected static int count = 0;
    protected int ID = 0;
    protected static int testMode = 0;
    
    protected App app;
    protected String name;
    protected String longName;

    protected Vector3 location = new Vector3(Vector3.TYPE_SIZE);
    protected Vector3 worldLocation = new Vector3(Vector3.TYPE_DISTANCE);
    protected Vector3 velocity = new Vector3(Vector3.TYPE_DISTANCE);
    protected float   speed;
    protected float   maxSpeed;
    protected Vector3 orientation = new Vector3(Vector3.TYPE_DEGREE360);
    protected Vector3 worldOrientation = new Vector3(Vector3.TYPE_DEGREE360);
    protected Vector3 rotation = new Vector3(Vector3.TYPE_DEGREE360);
    protected Vector3 worldRotation = new Vector3(Vector3.TYPE_DEGREE360);
    protected Vector3 dimension = new Vector3(Vector3.TYPE_SIZE);
    protected Vector3 bound = new Vector3(Vector3.TYPE_DISTANCE);
    protected Vector3 worldBound = new Vector3(Vector3.TYPE_DISTANCE);
    protected Node parent;
    //protected Cube world;
    protected boolean selected = false;
    protected boolean turnRight = false;
    protected boolean turnLeft = false;
    protected boolean input = false;
    
    
    public Spatial(){
        this((String)null);
    }
    
    
    public Spatial(String name){
        setID(count++);
        setName(name);
        //setLongName(name + ID);
        println("Spatial.Spatial(S) " + "ID=" + this.ID + ", name=" + this.name);
    }
    
    
    public Spatial(Spatial spatial){
        //this.ID = count++;
        setID(spatial.getID());
        setName(spatial.name);
        setLongName(spatial.longName);
        setLocation(spatial.location);
        setWorldLocation(spatial.worldLocation);
        setVelocity(spatial.velocity);
        setOrientation(spatial.orientation);
        setWorldOrientation(spatial.worldOrientation);
        setRotation(spatial.rotation);
        setSpeed(spatial.speed);
        setMaxSpeed(spatial.maxSpeed);
        setDimension(spatial.dimension);
        setBounds(spatial.bound);
        setWorldBound(spatial.worldBound);
        setParent(null);
        
        
        
        println("Spatial.Spatial(S) " + "name=" + this.name);
    }
    
    
    public Spatial(String name, Vector3 location, Vector3 orientation){
        setID(count++);
        setName(name);
        //setLongName(name + ID);
        setLocation(location);
        setOrientation(orientation);
    };
    
    
    public Spatial(String name, Vector3 location, Vector3 orientation, Vector3 dimension){
        setID(count++);
        setName(name);
        //setLongName(name + ID);
        setLocation(location);
        setOrientation(orientation);
        setDimension(dimension);
    };
    
    
    public void setTestMode(int n){
        testMode = n;
    }
    
    
    public int getCount(){
        return count;
    }
    
    
    public void setID(int ID){
        this.ID = ID;
    }
    
    
    public int getID(){
        return ID;
    }
    
    
    public void setName(String name){
        this.name = name;
    }
    
    
    public String getName(){
        return name;
    }
    
    
    public void setLongName(String longName){
        this.longName = longName;
    }
    
    
    public String getLongName(){
        return longName;
    }
    
    
    public Spatial clone(){
        try{
            Spatial clone = (Spatial) super.clone();
            clone = new Spatial(this);

            if (clone instanceof Node) {
                Node node = (Node) this;
                Node nodeClone = (Node) clone;
                nodeClone.children = new ArrayList<Spatial>();
                for (Spatial child : node.children) {
                    Spatial childClone = child.clone();
                    childClone.parent = nodeClone;
                    nodeClone.children.add(childClone);
                }
            }
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new AssertionError();
        }
    }
    
    
    public void setLocation(Vector3 location){
        this.location = location;
    }
    
    
    public Vector3 getLocation(){
        return location;
    }
    
    
    public void setWorldLocation(Vector3 worldLocation){
        this.worldLocation = worldLocation;
    }
    
    
    public Vector3 getWorldLocation(){
        return worldLocation;
    }
    
    
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    
    
    public boolean getSelected(){
        return selected;
    }
    
    
    public void toggleSelected(){
        selected = (selected == false);
    }
    
    
    public void setVelocity(Vector3 velocity){
        this.velocity = velocity;
    }
    
    
    public Vector3 getVelocity(){
        return velocity;
    }
    
    
    public void setRotation(Vector3 rotation){
        this.rotation = rotation;
    }
    
    
    public Vector3 getRotation(){
        return rotation;
    }
    
    
    public void setOrientation(Vector3 orientation){
        this.orientation = orientation;
    }
    
    
    public Vector3 getOrientation(){
        return orientation;
    }
    
    
    public void setWorldOrientation(Vector3 worldOrientation){
        this.worldOrientation = worldOrientation;
    }
    
    
    public Vector3 getWorldOrientation(){
        return worldOrientation;
    }
    
    
    public void setDimension(Vector3 dimension){
        this.dimension = dimension;
    }
    
    
    public Vector3 getDimension(){
        return dimension;
    }
    
    
    //public void setWorld(Cube world){
    //    this.world = world;
    //}
    
    
    //public Cube getWorld(){
    //    return world;
    //}
    
    
    public void setApp(App app){
        this.app = app;
    }
    
    
    public App getApp(){
        return app;
    }
    
    
    public void setSpeed(float speed){
        this.speed = speed;
    }
    
    
    public float getSpeed(){
        return speed;
    }
    
    
    public void setMaxSpeed(float maxSpeed){
        this.maxSpeed = maxSpeed;
    }
    
    
    public float getMaxSpeed(){
        return maxSpeed;
    }
    
    
    public void setBounds(Vector3 bound){
        this.bound = bound;
    }
    
    
    public Vector3 getBound(){
        return bound;
    }
    
    
    private void setWorldBound(Vector3 worldBound){
        this.worldBound = worldBound;
    }
    
    
    public Vector3 getWorldBound(){
        return worldBound;
    }
    
    
    public void setParent(Node parent){
        this.parent = parent;
    }
    

    public Node getParent(){
        return parent;
    }
    
    
    public void setInput(boolean input){
        this.input = input;
    }
    
    
    public boolean getInput(){
        return input;
    }
    
    
    //turning
    public void setTurnRight(){
        //println("Spatial.setTurnRight() " + "b=" + b);
        turnRight = true;
    }
    
    
    public void setTurnLeft(){
        //println("Spatial.setTurnLeft() " + "b=" + b);
        turnLeft = true;
    }
    
    
    public void doTurnRight(){
        println("Spatail.turnRight() ");
        //setTurnRight(false);
    }
    
    
    public void doTurnLeft(){
        println("Spatail.turnLeft() ");
        //setTurnLeft(false);
    }
    
    
    public void move(float tpf){
        move(null, tpf, 0);
    }
    

    public void move(Cube world, float tpf){
        move(world, tpf, 0);
    }
    
    
    public void move(Cube world, float tpf, int level){
        if (getSelected()) println(pad(level) + "Spatial.move(C, f, i)1 " + "location=" + location + ", velocity=" + velocity);
        if (turnRight){
            //println("Mass.turningRight() ");
            doTurnRight();
        }
        if (turnLeft){
            //println("Mass.turnintRight() ");
            doTurnLeft();
        }
        location = location.add(velocity.multiply(tpf));
        if (world != null) location = world.validate(location);
        //println(pad(level) + "Sparial.move(f, i)2 " + "location=" + location + ", velocity=" + velocity);
        updateWorldLocation(world);
        
        //no children for Spatial
        //for (Spatial spatial : children){
        //    spatial.move(tpf, level + 1);
        //}
    }
    
    
    public void updateWorldLocation(Cube world){
        if (parent == null){
            setWorldLocation(location);
        }
        else{
            Vector3 loc = parent.getWorldLocation().add(location);
            if (world != null) loc = world.validate(loc);
            setWorldLocation(loc);
        }
        //for (Spatial spatial : children){
        //    spatial.updateWorldLocation();
        //}
    }
    
    
    public void rotate(float tpf){
        rotate(null, tpf, 0);
    }
    
    
    public void rotate(Vector3 world, float tpf, int level){
        orientation = orientation.add(rotation.multiply(tpf));
        //if (world != null) location = world.validate(location);
        updateWorldOrientation(world);
    }
    
    
    public void updateWorldOrientation(Vector3 world){
        //worldRotation = getWorldRotation().add(orientation);
        if (parent == null){
            setWorldOrientation(orientation);
        }
        else{
            Vector3 rot = parent.getWorldOrientation().add(orientation);
            //if (world != null) rot = world.validate(rot);
            setWorldOrientation(rot);
        }
        //for (Spatial spatial : children){
        //    spatial.updateWorldOrientation();
        //}
    }
    
    
    public void draw(Cube world, Graphics g){
        println("Spatial.draw(C, G) ");
        //super.draw(g);
        draw(world, g, 0);
    }
    
    
    public void draw(Cube world, Graphics g, int level){ 
        //super.draw(g);
        //println("Spatial.draw(G, i) " + "ID=" + ID + ", level=" + level);
        //if (getName().equals("rootNode")){
        //    println("Spatial.draw(G, i) " + "rootNode......................");
        //}
        //println("Spatial.draw(G, i) " + "ID=" + ID + ", name=" + name + ", location=" + location + ", velocity=" + velocity + ", children=" + children.size());
        //println(pad(level) + "Sparial.move(f, i)1 " + "location=" + location + ", velocity=" + velocity);
    
        if (this instanceof Node){
            Node node = (Node)this;
            int n = 0;
            for (Spatial child : node.children){
                //println("Spatial.draw(G, i) " + "ID=" + child.getID() + ", n=" + n);
                child.draw(world, g, level + 1);
                n++;
                //break;
            }
        }
        
        //if (getName().equals("rootNode")){
        //    println("Spatial.draw(G, i) " + "................................");
        //}
    }
    
    
    public void draw(Graphics g){
        println("Spatial.draw(G) ");
        //super.draw(g);
        draw(g, 0);
    }
    
    
    public void draw(Graphics g, int level){ 
        //super.draw(g);
        //println("Spatial.draw(G, i) " + "ID=" + ID + ", level=" + level);
        //if (getName().equals("rootNode")){
        //    println("Spatial.draw(G, i) " + "rootNode......................");
        //}
        //println("Spatial.draw(G, i) " + "ID=" + ID + ", name=" + name + ", location=" + location + ", velocity=" + velocity + ", children=" + children.size());
        //println(pad(level) + "Sparial.move(f, i)1 " + "location=" + location + ", velocity=" + velocity);
    
        if (this instanceof Node){
            Node node = (Node)this;
            int n = 0;
            for (Spatial child : node.children){
                //println("Spatial.draw(G, i) " + "ID=" + child.getID() + ", n=" + n);
                child.draw(g, level + 1);
                n++;
                //break;
            }
        }
        
        //if (getName().equals("rootNode")){
        //    println("Spatial.draw(G, i) " + "................................");
        //}
    }
    
    

}
