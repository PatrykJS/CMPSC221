package smith.patryk;

/**
 * @author Patryk Smith
 * 
 * The player class is the same as the treasure chest class but
 * for the future it will be changed, having inventory and such
 * to fend monsters, gaining loot from the treasure and talking
 * to NPCS.
 */

  
public class Player extends Entity {
    public enum Move {UP, LEFT, DOWN, RIGHT};
    
    public Player(String _token){
        super(_token);
    }
    public Player(String _token, String _name){
       super(_token, _name);
    }
    public Player(String _token, String _name, Position _position){
       super(_token, _name, _position);
    }
    public Player(String _token, Position _position){
       super(_token,"", _position);
    }
    
}
