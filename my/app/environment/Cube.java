/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.environment;

import java.awt.Rectangle;
import my.app.mass.Mass;
import my.app.mass.Node;
import static my.app.utilities.Utils.println;
import my.app.vector3.Vector3;

/**
 *
 * @author Peter
 */
public class Cube {
    //int plane = 1;//1 is z = up/down, 2 is z = front/right
    
    static public final int STYLEWRAP = 1;
    static public final int STYLEBOUNCE = 2;
    
    Vector3 style = new Vector3(0f, 0f, 0f, Vector3.TYPE_DISTANCE);
    public float right = Float.MAX_VALUE;//max x
    public float top = Float.MAX_VALUE;//max y
    public float front = Float.MAX_VALUE;//max z
    public float left = Float.MIN_VALUE;//min x
    public float bottom = Float.MIN_VALUE;//min y
    public float back = Float.MIN_VALUE;//min z
    
    public Cube(){
        
    }
    
    
    public Cube(Vector3 size, Vector3 style){
        this.left = 0;
        this.top = 0;
        this.front = 0;//z
        this.right = size.x;
        this.bottom = size.y;
        this.back = size.z;
        this.style = style;
    }
    
    
    public Cube(Vector3 style){
        this.style = style;
    }
    
    
    public Cube(float left, float top, float right, float bottom, float front, float back){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.front = front;//z
        this.back = back;
    }
    
    
    public Cube(float left, float back, float right, float front, float top, float bottom, Vector3 style){
        this.left = left;
        this.back = back;
        this.right = right;
        this.front = front;//z
        this.top = top;
        this.bottom = bottom;
        this.style = style;
    }
    
    
    //rectangle
    public Cube(float left, float back, float right, float front){
        this.left = left;
        this.back = back;
        this.right = right;
        this.front = front;
    }
    
    
    public Cube(float left, float back, float right, float front, Vector3 style){
        this.left = left;
        this.back = back;
        this.right = right;
        this.front = front;
        this.style = style;
    }
    
    
    //public void set(float left, float top, float right, float bottom){
    //    this.left = left;
    //    this.top = top;
    //    this.right = right;
    //    this.bottom = bottom;
    //}
    
    
    public Cube(Rectangle r){
        left = r.x;
        back = r.y;
        right = r.width;
        front = r.height;
    }
    
    
    public Cube(Rectangle r, Vector3 style){
        this.left = r.x;
        this.back = r.y;
        this.right = r.width;
        this.front = r.height;
        this.top = 0f;
        this.bottom = 0f;
        this.style = style;
    }
    
    
    public void set(float left, float back, float right, float front, Vector3 style){
        this.left = left;
        this.back = back;
        this.right = right;
        this.front = front;
        this.style = style;
    }
    
    
    public void setStyle(Vector3 style){
        setStyle(style.x, style.y, style.z);
    }
    
    
    public Vector3 getStyle(){ 
        return style;
    }
    
    
    public void set(float left, float back, float right, float front, float top, float bottom){
        this.left = left;
        this.back = back;
        this.right = right;
        this.front = front;
        this.top = top;
        this.bottom = bottom;
        this.back = back;
    }
    
    
    public void set(float left, float back, float right, float front, float top, float bottom, Vector3 style){
        this.left = left;
        this.back = back;
        this.right = right;
        this.front = front;
        this.top = top;
        this.bottom = bottom;
        this.back = back;
        this.style = style;
    }
    
    
    public void set(Cube bounds){
        this.left = bounds.left;
        this.back = bounds.back;
        this.right = bounds.right;
        this.front = bounds.front;
        this.top = bounds.top;
        this.bottom = bounds.bottom;
    }
    
    
    public Cube scale(Vector3 d){
        return scale(d.x, d.y, d.z);
    }
 
    
    public Cube scale(float xd, float yd, float zd){
        Cube c2 = new Cube(left + xd, top + yd, front + zd, right - xd, bottom - yd, back - zd);
        return c2;
    }
    
    
    public Vector3 move(Mass mass, float tpf){
        return move(mass.getLocation(), mass.getVelocity().multiply(tpf));
        
    }
    
    
    public Vector3 move(Vector3 p, Vector3 v){
        return move(p.x, p.y, p.z, v.x, v.y, v.z);
    }
    
    
    public Vector3 move(float px, float py, float pz, float vx, float vy, float vz){
        float nx = px + vx;
        float ny = py + vy;
        float nz = pz + vz;
        
        if (style.getIntX() == STYLEWRAP){
            if (nx <= left){
                nx = right;
            }
            else if (nx >= right){
                nx = left;
            }

            if (ny >= top){
                ny = bottom;
            }
            else if (ny <= bottom){
                ny = top;
            }

            if (nz >= front){
                nz = back;
            }
            else if (nz <= back){
                nz = front;
            }
        }
        if (style.getIntX() == STYLEBOUNCE){
            if (nx <= left){
                nx = left;
            }
            else if (nx >= right){
                nx = right;
            }

            if (ny >= top){
                ny = top;
            }
            else if (ny <= bottom){
                ny = bottom;
            }

            if (nz >= front){
                nz = front;
            }
            else if (nz <= back){
                nz = back;
            }
        }

        //println("Cube.move(f x 6) " + "must set type");
        Vector3 p2 = new Vector3(nx, ny, nz, Vector3.TYPE_SIZE);
        
        return p2;
    }//move()
    
    
    public Vector3 move2(Vector3 l){
        float nx = l.x;
        float ny = l.y;
        float nz = l.z;
        
        if (style.getIntX() == STYLEWRAP){
            if (nx <= left){
                nx = right;
            }
            else if (nx >= right){
                nx = left;
            }

            if (ny >= top){
                ny = bottom;
            }
            else if (ny <= bottom){
                ny = top;
            }

            if (nz >= front){
                nz = back;
            }
            else if (nz <= back){
                nz = front;
            }
        }
        if (style.getIntX() == STYLEBOUNCE){
            if (nx <= left){
                nx = left;
            }
            else if (nx >= right){
                nx = right;
            }

            if (ny >= top){
                ny = top;
            }
            else if (ny <= bottom){
                ny = bottom;
            }

            if (nz >= front){
                nz = front;
            }
            else if (nz <= back){
                nz = back;
            }
        }
        Vector3 p2 = new Vector3(nx, ny, nz);
        return p2;
    }//move2
    
    
    public Vector3 validate(Vector3 location){
        Vector3 v2 = new Vector3(location);
        if (true) return v2;
        //float nx = location.x;
        //float ny = location.y;
        //float nz = location.z;
        
        if (style.getIntX() == STYLEWRAP){
            if (v2.x <= left){
                v2.x = right;
            }
            else if (v2.x >= right){
                v2.x = left;
            }

            if (v2.y >= top){
                v2.y = bottom;
            }
            else if (v2.y <= bottom){
                v2.y = top;
            }

            if (v2.z >= front){
                v2.z = back;
            }
            else if (v2.z <= back){
                v2.z = front;
            }
        }
        if (style.getIntX() == STYLEBOUNCE){
            if (v2.x <= left){
                v2.x = left;
            }
            else if (v2.x >= right){
                v2.x = right;
            }

            if (v2.y >= top){
                v2.y = top;
            }
            else if (v2.y <= bottom){
                v2.y = bottom;
            }

            if (v2.z >= front){
                v2.z = front;
            }
            else if (v2.z <= back){
                v2.z = back;
            }
        }
        return v2;
    }
    
/*
    public Vector3 validate2(Vector3 location){
        Vector3 v2 = null;
        boolean echo = false;
        float nx = location.x;
        float ny = location.y;
        float nz = location.z;
        
        if (style.getIntX() == STYLEWRAP){
            if (v.x <= left){
                nx = right;
                echo = true;
            }
            else if (v.x >= right){
                nx = left;
                echo = true;
            }

            if (v.y >= top){
                ny = bottom;
                echo = true;
            }
            else if (v.y <= bottom){
                ny = top;
                echo = true;
            }

            if (v.z >= front){
                nz = back;
                echo = true;
            }
            else if (v.z <= back){
               nz = front;
                echo = true;
            }
        }
        if (style.getIntX() == STYLEBOUNCE){
            if (v.x <= left){
                nx = left;
                echo = true;
            }
            else if (v.x >= right){
                nx = right;
                echo = true;
            }

            if (v.y >= top){
                ny = top;
                echo = true;
            }
            else if (v.y <= bottom){
                ny = bottom;
                echo = true;
            }

            if (v.z >= front){
                nz = front;
                echo = true;
            }
            else if (v.z <= back){
                nz = back;
                echo = true;
            }
            if (echo){
                v2 = new Vector3(nx, ny, nz, Vector3.TYPE_SIZE);
            }
        }
        return v2;
    }//move()
*/
    
    public Cube translate(Vector3 v){
        Cube c2 = new Cube(left + v.x, top + v.y, front + v.z, right + v.x, bottom + v.y, back + v.z);
        
        return c2;
    }
    
    
    public Vector3 contains(Vector3 pos){
        Vector3 contains = new Vector3(Vector3.TYPE_SIZE);
        
        if (pos.x < left){
            contains.x = -1;
        }
        else if (pos.x > right){
            contains.x = 1;
        }

        if (pos.y < top){
            contains.y = -1;
        }
        else if (pos.y > bottom){
            contains.y = 1;
        }

        if (pos.z < front){
            contains.z = -1;
        }
        else if (pos.z > back){
            contains.z = 1;
        }
        //if (contains.length() != 0) println("Cube.contains() " + "contains=" + contains);

        return contains;
    }
    
/* 
    public boolean contains(Vector3 pos, Vector3 pos2){
        boolean out = false;
        pos2 = new Vector3(pos);
        float xd = right - x;
        float yd = bottom - y;
        float zd = back - z;
        
        if (pos.x < x){
            pos2.x = pos.x + xd;
            out = true;
        }
        else if (pos.x > right){
            pos2.x = pos.x - xd;
            out = true;
        }

        if (pos.y < y){
            pos2.y = pos.y + yd;
            out = true;
        }
        else if (pos.y > bottom){
            pos2.y = pos.y - yd;
            out = true;
        }

        if (pos.z < z){
            pos2.z = pos.z + zd;
            out = true;
        }
        else if (pos.z > back){
            pos2.z = pos.z - zd;
            out = true;
        }
        if (out) println("Cube.contains() " + "out=" + out);

        return out;
    }
*/
    
    public void setStyle(float x, float y, float z){
        style.x = x;
        style.y = y;
        style.z = z;
    }
    
 
    public void setStyleX(float n){
        style.x = n;
    }
    
 
    public void setStyleY(float n){
        style.y = n;
    }
    
 
    public void setStyleZ(float n){
        style.z = n;
    }
    
 
    public String toString(){
        String s = new String("Cube1((" + left + ", " + back + "), (" + right + ", " + front + "), (" + top + ", " + bottom + "))");
        return s;
    }


    public String toString(int n){
        String s = new String("Cube2((" + (int)left + ", " + (int)back + "), (" + (int)right + ", " + (int)front + "), (" + (int)top + ", " + (int)bottom + "))");
        return s;
    }



}
