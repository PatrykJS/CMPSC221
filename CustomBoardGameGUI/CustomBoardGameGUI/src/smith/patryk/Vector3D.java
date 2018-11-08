/*
 */
package smith.patryk;

/**
 *
 * @author Patryk Smith
 */
public class Vector3D {
    private double x, y, z;
    public Vector3D(double _x, double _y, double _z){
        x = _x;
        y = _y;
        z = _z;        
    }  
    
    // Getters
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    
    
    //Vector Functions
    public double getMagnitude(){
        return Math.sqrt(x*x + y*y + z*z);
    }
    public Vector3D cross(Vector3D v){
        
        return new Vector3D(y*v.z - z*v.y, x*v.z - z*v.x, x*v.y - y*v.z);
    }
    public double dot(Vector3D v){
        return (x*v.x) + (y*v.y) + (z*v.z);
    }
    
}
