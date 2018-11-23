package smith.patryk;

import javax.swing.Timer;



/**
 * @author Patryk Smith
 * 
 * The Entity class needs to keep track of three things:
 * @see #token the icon used to represent the entity
 * @see #position the current position of entity
 * @see #name the name of the entity if used
 * 
 * 
 * Standard constructors, accessors and modifiers are
 * used to change and extract data. 
 * 
 * @see #increasePosition(int, int) is a very basic 
 * physics engine that updates the position based on 
 * user input sent to it. 
 */
public class Entity {
    enum Type {Player, Chest, Ground};
    
    protected Timer animation;
    private Vector2D position;
    private Vector2D velocity;
    
    private String name;
    
    public Entity(){
        name = "Entity";
        position = new Vector2D(0, 0);
    }
    public Entity(String _name){
        name = _name;
        position = new Vector2D(0,0);
    }
    public Entity(String _name, Vector2D _position){
        name = _name;
        position = new Vector2D(_position);
    } 
    public String getName(){
        return name;
    }
    public Vector2D getPosition(){
        return position;
    }
    public int getPositionY(){
        return position.getIntY();
    }
    public int getPositionX(){
        return position.getIntX();
    }
    public Timer getAnimation(){
        return animation;
    }
    public void setAnimation(Timer t){
        animation = t;
    }
    public void setName(String _name){
        name = _name;
    }
    public void setPosition(Vector2D _position){
        position = _position;
    }
    public void setPosition(int _x, int _y){
        position = new Vector2D(_x, _y);
    }
    public void move(Vector2D _velocity){
        position = position.add(_velocity);
               
    }
}
