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
    private Position p;
    
    public Boundary(String _token){
        super(_token);
    }
    public Boundary(String _token, String _name){
       super(_token, _name);
    }
    public Boundary(String _token, String _name, Position _position){
       super(_token, _name, _position);
    }
    public Boundary(String _token, Position _position){
       super(_token,"", _position);
    }
        
}
