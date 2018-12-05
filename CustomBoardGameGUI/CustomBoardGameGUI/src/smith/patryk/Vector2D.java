/*
 */
package smith.patryk;

/**
 *
 * @author Patryk Smith
 */
public class Vector2D {
    private double x, y;
    Vector2D() { 
        this(new Vector2D(0, 0));
    }
    
    public Vector2D(double _x, double _y){
        x = _x;
        y = _y;       
    }  
    
    public Vector2D(Vector2D _v){
        x = _v.getX();
        y = _v.getY();
    } 
     
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public int getIntX(){
        return (int)x;
    }
    
    public int getIntY(){
        return (int)y;
    }
    
    @Override
    public String toString(){
        return "{"+x+", " +y+"}";
    }
    
    public Vector2D add(Vector2D _v){
        return new Vector2D(this.x + _v.x, this.y+_v.y);
    }
    
    //Vector Functions
    public double getMagnitude(){
        return Math.sqrt(x*x + y*y );
    }
    
    public Vector2D cross(Vector2D v){
        return new Vector2D(x*v.y, -y*v.x);
    }
    
    public double dot(Vector2D v){
        return (x*v.x) + (y*v.y);
    }
    
    public Vector2D direction (Vector2D from, Vector2D to){
        return new Vector2D(to.x - from.x,  to.y - from.y);
    }
    
    public void normalize(){
        if(x > 0){
            x = 1;
        }else if(x < 0){
            x = -1;
        } else {
            x = 0;
        }
        if( y > 0){
            y = 1;
        }else if (y < 0){
            y = -1;
        } else {
            y = 0;
        }
    }
}
