package smith.patryk;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

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
    public void dig(Treasure t, EndScreen e) throws InterruptedException{
         
        if (t.getPlayerPosition().getIntX() == t.getTreasurePosition().getIntX()
                && (t.getTreasurePosition().getIntY() == t.getPlayerPosition().getIntY()
                || t.getTreasurePosition().getY() == t.getPlayerPosition().getIntY() + 1)) {

            t.getTreasureChest().show();
            Thread.sleep(500);
            try {
                e.setScore(25000);
            } catch (IOException ex) {
                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            t.setWin(true);
            
            e.setVisible(true);
        }else{
            System.out.println("Didn't find it.");
        }
    }
}
