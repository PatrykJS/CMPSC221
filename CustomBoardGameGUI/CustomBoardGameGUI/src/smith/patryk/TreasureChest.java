package smith.patryk;

/**
 * @author Patryk Smith
 * 
 * Is a basic class that extends Entity to make
 * use of position logic and token systematics.
 */
public class TreasureChest extends Entity {
    
    
    public TreasureChest() {
       
    }    
    public TreasureChest(String _name){
       super(_name);
    }
    public TreasureChest( String _name, Vector2D _position){
       super( _name, _position);
    }    
    public TreasureChest(Vector2D _position){
       super("Chest", _position);
    }
    
    
}
