/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.utilities;


/**
 *
 * @author Peter
 */
public class Utils {
    static public final int X = 0;
    static public final int Y = 1;
    static public final int Z = 2;
    
    static private boolean echo = true;

    //public static void print(){
    //    if (echo) System.out.print();
    //}
    
    
    public static void print(String string){
        if (echo) System.out.print(string);
    }
    
    
    public static void print(float f){
        if (echo) System.out.print(f);
    }
    
    
    public static void println(){
        if (echo) System.out.println();
    }
    
    
    public static void println(String string, int count){
        if (echo){
            for (int i = 0;i < count;i++){
                System.out.print("-");
            }
            println(string);
        }
    }
    
    
    public static void println(String string){
        if (echo){
            if (string != null)
                System.out.println(string);
            else
                System.out.println("null");
        }
    }
    
    
    public static String pad(int n){
        char[] pad = new char[n];
        for (int i = 0;i < pad.length;i++){
            pad[i] = '-';
        }
        String s = new String(pad);
        return s;
    }
    
    
    public void setEcho(boolean echo){
        this.echo = echo;
    }
    
    
    
    
}
