package smith.patryk;

/**
 * @author Patryk Smith
 * 
 * For future use, basically
 * to create maze like walls to prevent
 * the player to move through to create 
 * difficulty. Need more time for that
 * feature, requires more logic.
 */

public class Boundary extends Entity{
    
    public Boundary(String _token){
        super(_token);
    }
    public Boundary(String _token, String _name){
       super( _name);
    }
    public Boundary(String _token, String _name, Vector2D _position){
       super( _name, _position);
    }
    public Boundary(String _token, Vector2D _position){
       super("Boundary", _position);
    }
        
}
