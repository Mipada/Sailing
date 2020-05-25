/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.boat.boat;

import static my.app.utilities.Utils.println;

/**
 *
 * @author Peter
 */
public class Course {
    float course = 0f;
    
    //float[] POS = new float[] {-180.0f, -150.0f, -120.5f, -90.0f, -60.0f, -40.0f, -20.0f, 0.0f, 20.0f, 40.0f, 60.0f, 90.0f, 120.0f, 150.0f, 180.0f};//14

    public Course(){
        this(0f);
    }
    
    
    public Course(float course){
        set(course);
    }
    
    
    public void set(float course){
        if (course >  180f)
            course -= 360f;
        else if (course < -180f)
            course += 360f;
        this.course = course;
    }
    
    
    public float get(){
        return course;
    }
    
    
    public float getReciprical(){
        float c = course;
        if (c < 0){
            c += 180f;
        }
        else{
            c -= 180f;
        }
        return(adjust(c));
    }
    
    
    //static
    static public float adjustDegree(float c){
        if (c >  180f)
            c -= 360f;
        else if (c < -180f)
            c += 360f;
        return c;
    }
    
    
    static public float addDegree(float ci, float d){
        float c = ci += d;
        if (c >  180f)
            c -= 360f;
        else if (c < -180f)
            c += 360f;
        return c;
    }
    
    
    public Course add(float d){
        float c = course + d;
        if (c >  180f)
            c -= 360f;
        else if (c < -180f)
            c += 360f;
        return new Course(c);
    }
    
    
    public float add(float d, int n){
        
        return adjust(course += d);
    }
    
    
    public float adjust(float c){
        //println("Course.adjust(f) ");// + "Error: should not get here"
        if (c >  180f)
            c -= 360f;
        else if (c < -180f)
            c += 360f;
        //println("Course.adjust(f) " + "c=" + c);
        return c;
    }
    
    
    public void subtract(float d){
        course -= d;
        adjust(d);
    }
    
    
    public String toString(){
        String s = new String("Course[" + "course=" + course + "]");
        return s;
    }


}
