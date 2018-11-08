package smith.patryk;

import smith.patryk.Position;

/**
 * @author Patryk Smith
 * 
 * Is a basic class that extends Entity to make
 * use of position logic and token systematics.
 */
public class TreasureChest extends Entity {
    
    public TreasureChest(String _token) {
        super(_token);
    }    
    public TreasureChest(String _token, String _name){
       super(_token, _name);
    }
    public TreasureChest(String _token, String _name, Position _position){
       super(_token, _name, _position);
    }    
    public TreasureChest(String _token, Position _position){
       super(_token,"", _position);
    }
    
}
