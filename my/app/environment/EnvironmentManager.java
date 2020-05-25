/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.environment;

import java.util.ArrayList;
import static my.app.utilities.Utils.println;

/**
 *
 * @author Peter
 */
public class EnvironmentManager {
    ArrayList<Environment> environment = new ArrayList();
    
    public void add(Environment e){
        this.environment.add(e);
        println("EM.add() " + "name=" + e.getName() + ", size=" + environment.size());
    }
    
    
    public void initialize(){
        for (Environment e : environment){
            println("EM.init(f) " + "e=" + e.getName());
            e.initialize();
        }
        
    }
    
    
    public void move(float tpf){
        //println("EnvironmentManager.move() " + "size=" + environment.size());
        for (Environment e: environment){
            //println("EM.move(f) " + "name=" + e.getName());
            e.move(tpf);
        }
    }
    
    
    public void draw(){
        //println("EM.draw() " + "size=" + environment.size());
        int n = 0;
        for (Environment e: environment){
           // println("EM.draw() " + "name=" + e.getName());
            //println("EM.draw() " + "env(" + (n + 1) + ")/" + environment.size() + ", name=" + e.getName());
            //e.update(e.getGraphics());
            //e.draw(e.getGraphics());
            e.paintComponent(e.getGraphics());
            //e.paintComponents(e.getGraphics());
            n++;
            //println();
        }
    }
    
    
    public void input(){
        
    }
    
}
