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
    
    public Player(){
        this(new Vector2D(0,0));
    }
    public Player(String _name){
       super(_name); 
    }
    
    public Player( String _name, Vector2D _position){
       super( _name, _position);
    }
    
    public Player(Vector2D _position){
       super("Player", _position);
    }
    
    public boolean dig(Treasure t){
         
        if (t.getPlayerPosition().getIntX() == t.getTreasurePosition().getIntX()
                && (t.getTreasurePosition().getIntY() == t.getPlayerPosition().getIntY()
                || t.getTreasurePosition().getY() == t.getPlayerPosition().getIntY() + 1)) {

            t.getTreasureChest().show(); 
            return true;
        }else{
            return false;
            //System.out.println("Didn't find it.");
        }
    }
}
