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
public class Node extends Spatial{
    protected ArrayList <Spatial> children = new ArrayList();
    
    
    
    public Node(){
        this(null);
    }
    
    
    public Node(String name){
        super(name);
    }
    
    
    public Node(String name, Vector3 location, Vector3 orientation){
        super(name, location, orientation);
    }


    public Node(String name, Vector3 location, Vector3 orientation, Vector3 dimension){
        super(name, location, orientation, dimension);
    }


    public boolean isEmpty(){
        if (children == null) return false;
        else return(children.isEmpty());
    }
    
    
    protected boolean hasChildren(){
        return(children.size() > 0);
    }
    
    
    public int children(){
        return children.size();
    }
    
    
    public ArrayList<Spatial> getChildren(){
        return(children);
    }
    
    
    public boolean hasDescentants(){
        return(descendants() > 0);
    }
    
    
    public int descendants(){
        int descendants = 0;
        for (Spatial child : children){
            if (child instanceof Node){
                descendants += ((Node)child).descendants();
            }
        }
        return descendants;
    }
    
    
    public int attach(Node child){
        return attachAt(child, children.size());
    }


    public int attachAt(Node child, int index) {
        if (child == null)
            throw new NullPointerException();

        //println("Node.attachAt(N, I)" + "ID=" + ID + ", parent=" + name + ", child=" + child.getName() + ", index=" + index);

        if (child.getParent() != this && child != this) {
            if (child.getParent() != null) {
                child.getParent().detach(child);
            }
            child.setParent(this);
        }
        children.add(index, child);
        //boolean success = children.add(node);
        //return success;
        return children.size();
    }
    
    
    public void attach(int i, Node node){
        children.add(i, node);
    }
    
    
    public boolean attachAll(Collection collection){
        boolean okay = children.addAll(collection);
        return okay;
    }
    
    
    public boolean attachAll(int i, Collection collection){
        boolean okay = children.addAll(i, collection);
        return okay;
    }
    
    
    public int detach(Node child) {
        if (child == null)
            throw new NullPointerException();

        if (child.getParent() == this) {
            int index = children.indexOf(child);
            if (index != -1) {
                detachAt(index);
            }
            return index;
        }

        return -1;
    }


    /**
     * <code>detachChild</code> removes a given child from the node's list.
     * This child will no longe be maintained. Only the first child with a
     * matching name is removed.
     *
     * @param childName
     *            the child to remove.
     * @return the index the child was at. -1 if the child was not in the list.
     */
    public int detachNamed(String childName) {
        if (childName == null)
            throw new NullPointerException();

        for (int x = 0, max = children.size(); x < max; x++) {
            Spatial child =  children.get(x);
            if (childName.equals(child.getName())) {
                detachAt(x);
                return x;
            }
        }
        return -1;
    }

    /**
     *
     * <code>detachChildAt</code> removes a child at a given index. That child
     * is returned for saving purposes.
     *
     * @param index
     *            the index of the child to be removed.
     * @return the child at the supplied index.
     */
    public Spatial detachAt(int index) {
        Spatial child =  children.remove(index);
        if (child != null) {
            child.setParent(null);

            // since a child with a bound was detached;
            // our own bound will probably change.
            //setBoundRefresh();

            // our world transform no longer influences the child.
            // XXX: Not neccessary? Since child will have transform updated
            // when attached anyway.
            //child.setTransformRefresh();
            // lights are also inherited from parent
            //child.setLightListRefresh();
            //child.setMatParamOverrideRefresh();
            
            //invalidateUpdateList();
        }
        return child;
    }

    
    
    public Spatial getChild(String name){
        //Spatial[] array = (Spatial[])children.toArray();
        for (Spatial child : children){
            if (child.name.equals(name)){
                return child;
            }
        }
        return null;
    }

    
    public Spatial getChild(int i){
        Spatial node = children.get(i);
        return node;
    }
    
    
    public Spatial getDescendant(String name){
        if (children == null) children = new ArrayList();
        for (Spatial child : children){
            if (child.name.equals(name)){
                return child;
            }
        }
        for (Spatial child : children){
            if (child instanceof Node){
                if (((Node)child).getDescendant(name) != null){
                    return child;
                }
            }
        }
        return null;
    }
    
    
    public ArrayList<Spatial> getDescendants(){
        ArrayList<Spatial> list = new ArrayList();
        list = getDescendants(Spatial.class, false);
        return (list);
    }
    
    
    public ArrayList<Spatial> getDescendants(Class theClass, boolean debug){
        ArrayList<Spatial> list = new ArrayList();
        list = getDescendants(this, list, theClass, 0, debug);
        return (list);
    }
    
    
    //main getDescendants
	public ArrayList<Spatial> getDescendants(Node node, ArrayList<Spatial> list,  Class theClass, int level, boolean debug){//java.lang.reflect.Type
        if (list == null) list = new ArrayList();
        
        if (debug){System.out.println(Utils.pad(level) + "Node.getDescendants() " + "node=" + node.getName() + ", class=" + node.getClass().getSimpleName() + ", test class=" + theClass.getSimpleName());}
        //
        if (theClass.isAssignableFrom(node.getClass())){
            if (debug){System.out.println(Utils.pad(level) + "Node.getDescendants() " + "adding " + node.getName() + ".............................");}
			list.add(node);
		}
        else {
            if (debug){
                //System.out.println(Utils.pad(level) + "Spatial.getDescendants() " + "not adding " + node.getName());
            }
            
        }
        //or
        for (Spatial child : node.getChildren()){
            if (debug){
                System.out.println(Utils.pad(level) + "Node.getDescendants() " + "checking child " + child.getName() + " of " + node.getName() + ", children count=" + node.getChildren().size());
            }
            if (child instanceof Node){
                list = getDescendants((Node)child, list, theClass, level + 1, debug);
            }
        }
        return(list);
	}//getDescendants
    
    
    public ArrayList<Spatial> getDescendants2(Class theClass){
        ArrayList<Spatial> list = new ArrayList();
        list = getDescendants2(theClass, false);
        return list;
    }
    

    public ArrayList<Spatial> getDescendants2(Class theClass, boolean debug){
        ArrayList<Spatial> list = new ArrayList();
        list = this.getDescendants2(list, theClass, 0, debug);
        return (list);
    }
    
    
    //main getDescendants
	public ArrayList<Spatial> getDescendants2(ArrayList<Spatial> list,  Class theClass, int level, boolean debug){//java.lang.reflect.Type
        if (list == null) list = new ArrayList();
        
        if (debug){System.out.println(Utils.pad(level) + "Spatial.getDescendants() " + "spatial=" + getName() + ", class=" + getClass().getSimpleName() + ", test class=" + theClass.getSimpleName());}
        //
        if (theClass.isAssignableFrom(getClass())){
            if (debug){System.out.println(Utils.pad(level) + "Spatial.getDescendants() " + "adding " + getName() + ".............................");}
			list.add(this);
		}
        else {if (debug){System.out.println(Utils.pad(level) + "Spatial.getDescendants() " + "not adding " + getName());}}
        //or
        for (Spatial child : getChildren()){
            if (debug){System.out.println(Utils.pad(level) + "Node.getDescendants() " + "checking child " + child.getName() + " of " + getName() + ", children count=" + getChildren().size());}
            if (child instanceof Node){
                list = ((Node)child).getDescendants2(list, theClass, level + 1, debug);
            }
        }
        return(list);
	}//getDescendants
    

    public ArrayList<Spatial> getDescendants3(Class theClass, boolean debug){
        ArrayList<Spatial> list = new ArrayList();
        list = this.getDescendants2(list, theClass, 0, debug);
        return (list);
    }
    
    
    //main getDescendants
	public ArrayList<Spatial> getDescendants3(ArrayList<Spatial> list,  Class theClass, int level, boolean debug){//java.lang.reflect.Type
        //if (list == null) list = new ArrayList();
        
        if (debug){System.out.println(Utils.pad(level) + "Spatial.getDescendants() " + "spatial=" + getName() + ", class=" + getClass().getSimpleName() + ", test class=" + theClass.getSimpleName());}
        for (Spatial child : getChildren()){
            if (debug){System.out.println(Utils.pad(level) + "Spatial.getDescendants() " + "checking child " + child.getName() + " of " + getName() + ", children count=" + getChildren().size());}
            if (theClass.isAssignableFrom(getClass())){
                if (debug){System.out.println(Utils.pad(level) + "Spatial.getDescendants() " + "adding " + getName() + ".............................");}
                list.add(this);
            }
            else {if (debug){System.out.println(Utils.pad(level) + "Spatial.getDescendants() " + "not adding " + getName());}}
        }
        //
        //or
        for (Spatial child : getChildren()){
            if (debug){System.out.println(Utils.pad(level) + "Spatial.getDescendants() " + "checking child " + child.getName() + " of " + getName() + ", children count=" + getChildren().size());}
            if (child instanceof Node){
                list = ((Node)child).getDescendants3(list, theClass, level + 1, debug);
            }
        }
        return(list);
	}//getDescendants
    

    public boolean remove(Spatial spatial){
        boolean okay = children.remove(spatial);
        return okay;
    }
    
    
    public Spatial remove(int i){
        Spatial spatial = children.remove(i);
        return spatial;
    }
    

    @Override
    public void draw(Cube world, Graphics g){
        //println("Node.draw(G) ");
        //super.draw(g);
        draw(world, g, 0);
    }
    
    
    @Override
    public void draw(Cube world, Graphics g, int level){
        //println("Node.draw(G, i) " + "ID=" + ID + ", level=" + level);
        super.draw(world, g, level);
        //println("Node.draw(G, i) " + "ID=" + ID + ", name=" + name + ", location=" + location + ", velocity=" + velocity + ", children=" + children.size());
        //println(pad(level) + "Sparial.move(f, i)1 " + "location=" + location + ", velocity=" + velocity);
    }

    
    @Override
    public void draw(Graphics g){
        //println("Node.draw(G) ");
        //super.draw(g);
        draw(g, 0);
    }
    
    
    @Override
    public void draw(Graphics g, int level){
        //println("Node.draw(G, i) " + "ID=" + ID + ", level=" + level);
        super.draw(g, level);
        //println("Node.draw(G, i) " + "ID=" + ID + ", name=" + name + ", location=" + location + ", velocity=" + velocity + ", children=" + children.size());
        //println(pad(level) + "Sparial.move(f, i)1 " + "location=" + location + ", velocity=" + velocity);
    }


    @Override
    public void move(float tpf){
        move(null, tpf, 0);
    }
    
    
    @Override
    public void move(Cube world, float tpf){
        move(world, tpf, 0);
    }
    
    @Override
    public void move(Cube world, float tpf, int level){
        //println(pad(level) + "Node.move(f, i) " + "ID=" + ID + ", name=" + getName());
        super.move(world, tpf, level);
        for (Spatial spatial : children){
            spatial.move(world, tpf, level + 1);
        }
    }


    @Override
    public void rotate(float tpf){
        rotate(null, tpf, 0);
    }
    

    @Override
    public void rotate(Vector3 world, float tpf, int level){
        super.rotate(world, tpf, level);
        for (Spatial spatial : children){
            spatial.rotate(world, tpf, level);
        };
    }

    
    public String toString(){
        String s = new String("Node");
        return s;
    }
        
    
    
}
