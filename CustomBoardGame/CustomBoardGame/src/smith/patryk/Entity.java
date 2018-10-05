package smith.patryk;

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
    private Position position;
    private String name;
    private String token;
    
    public Entity(String _token){
        token = _token;
        name = "Player 1";
        position = new Position(0, 0);
    }
    public Entity(String _token, String _name){
        token = _token;
        name = _name;
        position = new Position(0,0);
    }
    public Entity(String _token, String _name, Position _position){
        token = _token;
        name = _name;
        position = new Position(_position);
    } 
    public String getName(){
        return name;
    }
    public String getToken(){
        return token;
    }
    public Position getPosition(){
        return position;
    }
    public int getPositionY(){
        return position.y;
    }
    public int getPositionX(){
        return position.x;
    }
    public void setName(String _name){
        name = _name;
    }
    public void setToken(String _token){
        token = _token;
    }
    public void setPosition(Position _position){
        position = _position;
    }
    public void setPosition(int _x, int _y){
        position = new Position(_x, _y);
    }
    public void setPositionX(int _x){
        position.x =_x;
    }
    public void setPositionY(int _y){
        position.x =_y;
    }
    public void increasePosition(int delta_x, int delta_y){
        position.x += delta_x;
        position.y += delta_y;
    }
}
