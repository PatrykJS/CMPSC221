/*
 */
package smith.patryk;

import java.awt.Graphics;

/**
 *
 * @author Patryk Smith
 */
public class Compass {
    private Vector2D directionOfChest;
    private TreasureChest treasureChest;
    private Player player;
    private int uses;
    private final int size = 50;
    public Compass(TreasureChest t, Player p){
        uses = 3; 
        directionOfChest = new Vector2D( t.getPosition().getX()- p.getPosition().getX(), t.getPosition().getY()- p.getPosition().getY());
        directionOfChest.normalize();
    }
    
    public void use(){
        uses--;
       
    }
    public boolean canUse(){
        if(uses > 0){
            return true;
        }else{
                return false;
        }
    }
    public Vector2D direction(TreasureChest t, Player p){
        directionOfChest = new Vector2D( t.getPosition().getX()- p.getPosition().getX(), t.getPosition().getY()- p.getPosition().getY());
        directionOfChest.normalize();
        return directionOfChest;
    }
    
    public void draw(Graphics g, Vector2D position, Vector2D direction){
        
        g.drawLine(position.getIntX(), position.getIntY(), position.getIntX() + direction.getIntX(), position.getIntY() + direction.getIntY());
        
    }
}
