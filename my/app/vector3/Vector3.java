/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.vector3;

import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.text.NumberFormatter;
import my.app.environment.Cube;
import static my.app.utilities.Utils.println;

/**
 *
 * @author Peter
 */
public class Vector3 {
    static public float PI = (float)Math.PI;
    static public float PI2 = (float)(Math.PI * 2d);
    
    static final public int TYPE_UNSET = 0;
    static final public int TYPE_UNITVECTOR = 1;
    static final public int TYPE_SIZE = 2;
    static final public int TYPE_DISTANCE = 3;
    static final public int TYPE_RADIAN = 4;
    static final public int TYPE_DEGREE360 = 5;
    static final public int TYPE_DEGREE180 = 6;

    public float x = 0f;
    public float y = 0f;
    public float z = 0f;
    public boolean is360 = true;
    public int type = TYPE_UNSET;
    
	DecimalFormat scientificNotationFormat = new DecimalFormat("0.000E00");
	DecimalFormat degreeFormat = new DecimalFormat("000.00");
	NumberFormatter scientificNotationFormatter = new NumberFormatter(scientificNotationFormat);
	NumberFormatter degreeFormatter = new NumberFormatter(degreeFormat);


    public Vector3(){
        this(0);
    }
    
    
    public Vector3(int type){
        setType(type);
        //println("Vector3.Vector3(i) " + "type=" + type);
        if (type == TYPE_UNSET){
            println("Vector3.Vector3(i) " + "type 0" + type + ") is invalid. You must set a type: Vector(1 - 5) ");
            Thread.dumpStack();
            System.exit(1);
        }
        
    }
    
    
    public Vector3(Vector3 v){
        this(v, v.type);
    }
    
    
    public Vector3(Vector3 v, int type){
        if (type == TYPE_UNSET) println("Vector3.Vector3(v) " + "type 0 is invalid. You must set a type: Vector(1 - 5) ");
        setType(type);
        set(v, type);
    }
    
    
    public Vector3(float x, float y, float z){
        this(x, y, z, 0);

    }
    
    
    public Vector3(float x, float y, float z, int type){
        setType(type);
        set(x, y, z, type);

		//scientificNotationFormatter.setOverwriteMode(true);
		scientificNotationFormatter.setAllowsInvalid(false);

    }
    

    //2D
    //public Vector3(float degree){
    //    this(degree, TYPE_DEGREE360);
    //}
    
    
    //public Vector3(float degree, int type){
    //    setType(type);
    //    setCourse(degree);
    //}
    
    
    public int getIntX(){
        return (int)x;
    }
    
    
    public int getIntY(){
        return (int)y;
    }
    
    
    public int getIntZ(){
        return (int)z;
    }
    
    
    public void setType(int type){
        this.type = type;
    }
    
    
    public int getType(){
        return type;
    }
    
    
    public void set(Vector3 v){
        set(v, type);
    }
    
    
    public void set(Vector3 v, int type){
        setType(type);
        set(v.x, v.y, v.z, type);
    }
    
    
    //public void setByRad(float x, float y, float z){
    //    set(x, y, z);
    //}
    
    
    public void set(float x, float y, float z){
        set(x, y, z, type);
    }
    

    public void set(float x, float y, float z, int type){
        if (type == TYPE_UNSET){
            println("Vector3.Vector3(i) " + "type 0" + type + ") is invalid. You must set a type: Vector(1 - 5) ");
            Thread.dumpStack();
            System.exit(1);
        }
        setType(type);
        switch (type){
            //case TYPE_UNSET: 
            //    setVector(x, y, z);
            //    break;
            case TYPE_UNITVECTOR: 
                setVector(x, y, z);
                break;
            case TYPE_SIZE:
            case TYPE_DISTANCE: 
                setSize(x, y, z);
                break;
            case TYPE_RADIAN: 
                setRadian(x, y, z);
                break;
            case TYPE_DEGREE360: 
                setDegree360(x, y, z);
                break;
            case TYPE_DEGREE180: 
                setDegree180(x, y, z);
                break;
            default:
                println("Vector.set(f, f, f, i) " + "bad type given (" + type + ")");
                this.x = x;
                this.y = y;
                this.z = z;
                break;
        }
    }
    
    
    public void setVector(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    
    public void setSize(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;

    }
    
    
    public void setRadian(float x, float y, float z){
        float nx = validateRadian(x, "x");
        float ny = validateRadian(y, "y");
        float nz = validateRadian(z, "z");

        this.x = nx;
        this.y = ny;
        this.z = nz;
    }
    
    
    public float validateRadian(float r, String s){
        float nr = r;
        if (nr < -PI){
            println("Vector3.setRadian(f, f, f) " + "bad value (" + r + "). -PI < " + s + " < PI...............................");
            while (nr < -PI){
                nr += PI;
            }
        }
        else if (nr > PI){
            println("Vector3.setRadian(f, f, f) " + "bad value (" + r + "). -PI < " + s + " < PI");
            while (nr > PI){
                nr -= PI;
            }
        }
        return nr;
    }
    
    
    public static float validateDegree(float d, int type){
        float nd = d;
        switch (type){
            case TYPE_DEGREE180:
                if (nd < -180f){
                    while (nd < -180f){
                        nd += 360f;
                    }
                }
                else if (nd > 180f){
                    while (nd > 180f){
                        nd -= 360f;
                    }
                }
                break;
            case TYPE_DEGREE360:
                if (nd < 0f){
                    while (nd < 0f){
                        nd += 360f;
                    }
                }
                else if (nd > 180f){
                    while (nd > 180f){
                        nd -= 360f;
                    }
                }
                break;
            default:
                println("Vector3.validateDegree(degree, type " + "bad type (" + type + ")");
                break;
        }
        return nd;
    }
    
    
    public void setDegree360(float x, float y, float z){
        float nx = validateDegree360(x, "x");
        float ny = validateDegree360(y, "y");
        float nz = validateDegree360(z, "z");
        this.x = nx;
        this.y = ny;
        this.z = nz;
    }//setDegree360
    
    
    public void setDegree180(float x, float y, float z){
        float nx = validateDegree180(x, "x");
        float ny = validateDegree180(y, "y");
        float nz = validateDegree180(z, "z");
        this.x = nx;
        this.y = ny;
        this.z = nz;
    }//setDegree180
    
    
    public float validateDegree360(float n, String s){
        float nn = n;
        if (nn < 0f){
            println("Vector3.validateDegree360(f, f, f) " + "bad value (" + n + "). 0 < " + s + " < 360");
            while (nn < 0f){
                nn += 360f;
            }
        }
        else if (nn > 360f){
            println("Vector3.setRadian(f, f, f) " + "bad value (" + n + "). 0 < " + s + " < 360");
            while (nn > 360f){
                nn -= 360f;
            }
        }
        return nn;
    }
    
    
    public float validateDegree180(float n, String s){
        float nn = n;
        if (nn < -180f){
            println("Vector3.validateDegree180(f, f, f) " + "bad value (" + n + "). -180 < " + s + " < 180");
            while (nn < -180f){
                nn += 360f;
            }
        }
        else if (nn > 180f){
            println("Vector3.valdateDegree180(f, f, f) " + "bad value (" + n + "). -180 < " + s + " < 180");
            while (nn > 180f){
                nn -= 360f;
            }
        }
        return nn;
    }
    
    
    //2D
    //public void setCourse(float course){
    //    setCourse(course, 0f);
    //}
    
    
    //public void setCourse(float course, float degree){
    //    setCourse(course, degree, type);
    //}
        
/*
    public void setCourse(float course, float degree, int type){
        //setType(type);
        float c = (float)Math.toRadians(degree);
        float r = (float)Math.toRadians(degree);
        x = (float)Math.cos(c);
        y = (float)Math.sin(c);
        z = (float)Math.cos(r);
        //set(x, y, z, TYPE_UNITVECTOR);
    }
*/
        
    public static Vector3 getCourse(float course){
        Vector3 v2 = getCourse(course, 0f);
        return v2;
    }
    
    
    public static Vector3 getCourse(float course, float degree){
        float c = (float)Math.toRadians(course);
        float d = (float)Math.toRadians(degree);

        float nx = (float)Math.cos(c);
        float ny = (float)Math.sin(c);
        float nz = (float)Math.cos(d);
        Vector3 v2 = new Vector3(nx, ny, nz, TYPE_UNITVECTOR);
        return v2;
    }
        
        
    public static Vector3 getVector(float course){
        Vector3 v2 = getVector(course, 0f);
        return v2;
    }
    
    
    public static Vector3 getVector(float course, float degree){
        float c = (float)Math.toRadians(course);
        float d = (float)Math.toRadians(degree);

        float nx = (float)Math.cos(c);
        float ny = (float)Math.sin(c);
        float nz = (float)Math.cos(d);
        Vector3 v2 = new Vector3(nx, ny, nz, TYPE_UNITVECTOR);
        return v2;
    }
        
        
    public float getATAN2(){
        println("Vector3.getCourse() " + "may be using atan2 improperly");
        return (float)Math.atan2(y, x);
    }
    

    public Vector3 negate(){
        Vector3 v2 = new Vector3(-x, -y, -z);
        return v2;
    }
    

    public float length(){
        return (float)Math.sqrt(x * x + y * y + z * z);
    }
    
    
    public Vector3 scale(float s){
        Vector3 v2 = new Vector3(x * s, y * s, z * s);
        return v2;
    }
    
    
    public Vector3 normalize(){
        Vector3 v2 = divide(length());
        return v2;
    }
    

    static public float subtractDegrees(float n1, float n2){
        float n = n1 - n2;
        if (n < -180f){
            while (n < -180f){
             n += 360f;
            }
        }
        else if (n > 180f){
            while (n > 180f){
                n -= 360f;
            }
        }
        return n;
    }

/*    
    static public float addRadians(float r1, float r2){
        float r = r1 + r2;
        while (r < -PI){
            r = r + PI2;
        }
        while (r > PI){
            r = r - PI2;
        }
        return r;
    }
*/    
    
    
    public Vector3 add(Vector3 v){
        Vector3 v2 = add(v, type);
        return v2;
    }
    
    
    public Vector3 add(Vector3 v, int type){
        Vector3 v2 = add(v.x, v.y, v.z, type);
        return v2;
    }
    
/*    
    static final public int TYPE_UNSET = 0;
    static final public int TYPE_UNITVECTOR = 1;
    static final public int TYPE_DISTANCE = 2;
    static final public int TYPE_RADIAN = 3;
    static final public int TYPE_DEGREE = 4;
*/
    
    public Vector3 add(float x, float y, float z, int type){
        //println("Vector3.add(f, f, f, i) " + "type=" + type);
        Vector3 v2;
        switch(type){
            //case TYPE_UNSET:
            //    v2 = addVector(x, y, z);
            //    break;
            case TYPE_UNITVECTOR:
                v2 = addVector(x, y, z);
                break;
            case TYPE_SIZE:
            case TYPE_DISTANCE:
                v2 = addSize(x, y, z);
                break;
            case TYPE_RADIAN:
                v2 = addRadian(x, y, z);
                break;
            case TYPE_DEGREE360:
                v2 = addDegree360(x, y, z);
                break;
            case TYPE_DEGREE180:
                v2 = addDegree180(x, y, z);
                break;
            default:
                println("Vector3.add(f, f, f, i) " + "unset type (" + type + "). Use Vector(Vector.[type]);");
                v2 = new Vector3(this.x + x, this.y + y, this.z + z, -1);
                break;
        }
        return v2;
    }
    
    
    public Vector3 addVector(float x, float y, float z){
        float nx = this.x + x;
        float ny = this.y + y;
        float nz = this.z + z;

        Vector3 v2 = new Vector3(this.x + x, this.y + y, this.z + z, type);
        return v2.normalize();
    }
    
    
    public Vector3 addSize(float x, float y, float z){
        float nx = this.x + x;
        float ny = this.y + y;
        float nz = this.z + z;

        Vector3 v2 = new Vector3(TYPE_SIZE);
        v2.set(nx, ny, nz);
        //Vector3 v2 = new Vector3(nx, ny, nz, type);
        return v2;
    }
    
    
    public Vector3 addRadian(float x, float y, float z){
        float nx = addRadians(this.x, x);
        float ny = addRadians(this.y, y);
        float nz = addRadians(this.z, z);
        Vector3 v2 = new Vector3(nx, ny, nz, type);
        return v2;
    }
    
    
    public Vector3 addDegree360(float x, float y, float z){
        //Vector3 v2 = new Vector3(x + xd, y + yd, z + zd);
        float nx = addDegrees(this.x, x, TYPE_DEGREE360);
        float ny = addDegrees(this.y, y, TYPE_DEGREE360);
        float nz = addDegrees(this.z, z, TYPE_DEGREE360);
        
        Vector3 v2 = new Vector3(nx, ny, nz, type);
        return v2;
    }
    
    
    public Vector3 addDegree180(float x, float y, float z){
        //Vector3 v2 = new Vector3(x + xd, y + yd, z + zd);
        float nx = addDegrees(this.x, x, TYPE_DEGREE180);
        float ny = addDegrees(this.y, y, TYPE_DEGREE180);
        float nz = addDegrees(this.z, z, TYPE_DEGREE180);
        
        Vector3 v2 = new Vector3(nx, ny, nz, type);
        return v2;
    }
    
    
    public Vector3 subtract(float xd, float yd, float zd){
        Vector3 v2 = new Vector3(x - xd, y - yd, z - zd);
        return v2;
    }
    
    
    public Vector3 subtract(Vector3 v){
        Vector3 v2 = new Vector3(x - v.x, y - v.y, z - v.z, type);
        //println("Vector3.subtract() " + "this=(" + x + ", " + y + ", " + z + ")" + ", v=" + v.toString() + ", v2=" + v2.toString());
        return v2;
    }
    
    
    public Vector3 multiply(float n){
        
        return multiply(n, type);
    }
    
    
    public Vector3 multiply(float n, int type){
        Vector3 v2 = new Vector3(this.x * n, this.y * n, this.z * n, type);
        return v2;
    }
    
    
    public Vector3 multiply(Vector3 v){
        Vector3 v2 = multiply(v, type);
        return v2;
    }
    
    
    public Vector3 multiply(Vector3 v, int type){
        Vector3 v2 = multiply(this.x * v.x, this.y * v.y, this.z * v.z, type);
        return v2;
    }
    
    
    public Vector3 multiply(float x, float y, float z, int type){
        Vector3 v2 = new Vector3(this.x * x, this.y * y, this.z * z, type);
        return v2;
    }
    
    
    public Vector3 divide(float n){
        Vector3 v2;
        if (n == 0)
            v2 = new Vector3(-1f, -1f, -1f, type);
        else
            v2 = new Vector3(x/n, y/n, z/n, type);
        return v2;
    }
    
    
    public Vector3 divide(Vector3 v){
        Vector3 v2;
        float x;
        float y;
        float z;
        if (v.x == 0)
            x = 0f;
        else
            x = this.x/v.x;
        
        if (v.y == 0)
            y = 0f;
        else
            y = this.y/v.y;
        
        if (v.z == 0)
            z = 0f;
        else
            z = this.z/v.z;
        v2 = new Vector3(x, y, z, type);
        return v2;
    }
    
    
    public Vector3 modulas(float m){
        Vector3 v2 = new Vector3(x % m, y %m, z % m, type);
        return v2;
    }
    
    
    //for 3D movement
    public Vector3 clip(Vector3 v){
        Vector3 v2 = new Vector3(x, y, z, type);
        if (v2.x < v.x)
            v2.x = v.x;
        else if (v2.x > v.x)
            v2.x = v.x;

        if (v2.y < v.y)
            v2.y = v.y;
        else if (v2.y > v.y)
            v2.y = v.y;

        if (v2.z < v.z)
            v2.z = v.z;
        else if (v2.z > v.z)
            v2.z = v.z;
            
        return v2;
    }
    

    public Vector3 accelerate(Vector3 v){
        Vector3 v2 = new Vector3(x + v.x, y + v.y, z + v.z, type);
        return v2;
    }
    

    public Vector3 accelerate(Vector3 v, Vector3 bound){
        Vector3 v2 = new Vector3(x + v.x, y + v.y, z + v.z, type);
/*
        if (Float.compare(v2.x, bound.left) < 0f){//-3 - -8 = 5
            v2.x = bound.left;
        }
        else if (Float.compare(v2.x, bound.right) > 0f){
            v2.x = bound.right;
        }

        if (Float.compare(v2.y, bound.top) < 0f){//-3 - -8 = 5
            v2.y = bound.top;
        }
        else if (Float.compare(v2.y, bound.bottom) > 0f){
            v2.y = bound.bottom;
        }

        if (Float.compare(v2.z, bound.front) < 0f){//-3 - -8 = 5
            v2.z = bound.front;
        }
        else if (Float.compare(v2.z, bound.back) > 0f){
            v2.z = bound.back;
        }
*/

        if (isBiggerThan(v.x, bound.x))
            v2.x = bound.x;
        if (isSmallerThan(v.x, bound.x))
            v2.x = bound.x;

        if (isBiggerThan(v.y, bound.y))
            v2.y = bound.y;
        if (isSmallerThan(v.y, bound.y))
            v2.y = bound.y;

        if (isBiggerThan(v.z, bound.z))
            v2.z = bound.z;
        if (isSmallerThan(v.z, bound.z))
            v2.z = bound.z;

        return v2;
    }
    

    public boolean equals(Vector3 v){
        boolean equal = true;
        
        if (x != v.x || y != v.y || z != v.z){
            equal = false;
        }
        return equal;
    }
    
    
    public boolean isBiggerThan(float a, float b){
        boolean over = false;
        
        if (Float.compare(a, b) > 0f){
            over = true;
        }
        return over;
    }
    
    
    public boolean isSmallerThan(float a, float b){
        boolean over = false;
        
        if (Float.compare(a, b) < 0){
            over = true;
        }
        return over;
    }
    
    
    @Override
    public String toString(){
        String s = "(" + x + "," + y + "," + z + ", type=" + type + ")";
        return s;
    }
    
    
    public String toString(int style){
        String s = new String("Error: bad input code (" + style + ")");
        if (style == 0){
            s = "(" + (int)x + ", " + (int)y + ", " + (int)z + ")";
        }
        else if (style == 1){
            degreeFormat = new DecimalFormat("0.0");
            degreeFormatter.setFormat(degreeFormat);
            s = new String("(" + degreeFormat(x) + ", " + degreeFormat(y) + ", " + degreeFormat(z) + ")");
        }
        else if (style == 2){
            degreeFormat = new DecimalFormat("00.00");
            degreeFormatter.setFormat(degreeFormat);
            s = new String("(" + degreeFormat(x) + ", " + degreeFormat(y) + ", " + degreeFormat(z) + ")");
        }
        else if (style == 3){
            degreeFormat = new DecimalFormat(" 0.000");
            degreeFormatter.setFormat(degreeFormat);
            s = new String("(" + degreeFormat(x) + ", " + degreeFormat(y) + ", " + degreeFormat(z) + ")");
        }
        else if (style == 4){
            scientificNotationFormat = new DecimalFormat("0.00E00");
            s = new String("(" + sNF(x) + ", " + sNF(y) + ", " + sNF(z) + ")");
        }
        else if (style == 5){
            degreeFormat = new DecimalFormat("000.00");
            degreeFormatter.setFormat(degreeFormat);
            s = new String("(" + degreeFormat(x) + ", " + degreeFormat(y) + ", " + degreeFormat(z) + ")");
        }
        else if (style == 6){
            degreeFormat = new DecimalFormat("000.00");
            degreeFormatter.setFormat(degreeFormat);
            s = new String("(" + degreeFormat(x) + ", " + degreeFormat(y) + ", " + degreeFormat(z) + ")");
        }
        else{
            println("Vector3.toString(int) " + "Error: bad input code: style=" + style);
        }
        

        return s;
    }
    
    
    public String degreeFormat(float n){
		String s = new String("");
		
		try{
			s = degreeFormatter.valueToString(n);
		}
		catch(ParseException e){
			s = new String("Display3D.string parse error");
		}
		return(s);
	
	}
    
    
    public String sNF(float n){
		String s = new String("");
		
		try{
			s = scientificNotationFormatter.valueToString(n);
		}
		catch(ParseException e){
			s = new String("string parse error");
		}
		return(s);
    }
	
    
	public String scientificNotationFormat(float n){
		String s = new String("");
		
		try{
			s = scientificNotationFormatter.valueToString(n);
		}
		catch(ParseException e){
			s = new String("string parse error");
		}
		return(s);
	}
	

    static public float addRadians(float n1, float n2){
        float n = n1 + n2;
        if (n < 0f){
            while (n < -PI){
                n += PI2;
            }
        }
        else if (n > PI){
            while (n > PI){
                n -= PI2;
            }
        }
        return n;
    }


    static public float addDegrees(float n1, float n2, int type){
        float n = n1 + n2;
        if (type == TYPE_DEGREE360){
            if (n < 0f){
                while (n < 0f){
                    n += 360f;
                }
            }
            else if (n > 360f){
                while (n > 360f){
                    n -= 360f;
                }
            }
        }
        else if (type == TYPE_DEGREE180){
            if (n < -180f){
                while (n < -180f){
                    n += 360f;
                }
            }
            else if (n > 180f){
                while (n > 180f){
                    n -= 360f;
                }
            }
        }
        else{
            println("Vector3.addDegrees(f, f, i) " + "Error: bad type (" + type + ")");
        }
        return n;
    }


    
}


    

