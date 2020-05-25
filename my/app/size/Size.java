/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.size;

/**
 *
 * @author Peter
 */

public class Size
{
    public float x = 0f;
    public float y = 0f;

    
    public Size(){
        
    }
    
    
    public Size(int x, int y){
        this.x = x;
        this.y = y;
    }


    public Size(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    
    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    
    public int getX(){
        return (int)x;
    }
    
    
    public void setX(int x){
        this.x = x;
    }


    public void setX(float x){
        this.x = x;
    }


    public int getY(){
        return (int)y;
    }


    public void setY(int y){
        this.y  = y;
    }


    public void setY(float y){
        this.y = y;
    }

/*
    public void add(float xv, float yv){
        this.x = this.x + xv;
        this.y = this.y + yv;
    }
    
    
    public void move(float xv, float yv){
        this.x = this.x + xv;
        this.y = this.y + yv;
    }
*/    
    
    public String toString(){
        String s = new String("Size(" + x + ", " + y + ")");
        return s;
    }
}
