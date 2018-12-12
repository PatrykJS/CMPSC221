/*
 */
package smith.patryk;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Patryk Smith
 */
public class Compass {
    private Vector2D directionOfChest;
    private int uses;
    private final int size = 50;
    private final int radius = size/2;
    private Color compassColor = new Color(139, 145, 155);
    private boolean show;
    
    public Compass(TreasureChest t, Player p){
        uses = 3; 
        directionOfChest = new Vector2D( t.getPosition().getX()- p.getPosition().getX(), t.getPosition().getY()- p.getPosition().getY());
        directionOfChest.normalize();
        show = false;
    }
    
    public void use(){
        uses--; 
    }
    
    public boolean canUse(){
        if(uses >= 0){
            return true;
        }else{
            return false;
        }
    }
    public int getUses(){
        return Math.abs(uses);
    }
    public Vector2D direction(TreasureChest t, Player p){
        directionOfChest = new Vector2D( t.getPosition().getX()- p.getPosition().getX(), t.getPosition().getY()- p.getPosition().getY());
        directionOfChest.normalize();
        return directionOfChest;
    }
    
    public int getSize(){
        return size;
    }
    
    public void toggle(){
        show = !show;
    }
    
    public void setShow(boolean _show){
        show = _show;
    }
    
    public void draw(Graphics g, Treasure treasure, Vector2D position, Vector2D direction){
        if (uses < 0){
            g.setColor(Color.red);
            g.setFont(new Font("Forte", 3, size));
            g.drawString("Compass used up!", position.getIntX(), position.getIntY());
        }else if(show){
            int chestX = treasure.getTreasurePosition().getIntX();
            int chestY = treasure.getTreasurePosition().getIntY(); 
            int playerX = treasure.getPlayerPosition().getIntX();
            int playerY = treasure.getPlayerPosition().getIntY();

            int directionX = 0, directionY = 0;

            if(chestX - playerX > 0){
               directionX = 1;
            }else if( chestX - playerX < 0){
                directionX = -1;
            }else{
                directionX = 0;
            }

            if(chestY - playerY > 0){
                directionY = 1;
            }else if( chestY - playerY < 0 ){
                directionY = -1;
            }else {
                directionY = 0;
            }

            if(Math.abs(directionY) == Math.abs( directionX)){
                direction = new Vector2D( directionX * Math.sqrt(2) / 4 * size,  directionY * Math.sqrt(2) / 4 * size);
            }else{
                direction = new Vector2D(directionX * radius, directionY *radius);
            }

            int x = position.getIntX()+(size/2);
            int y = position.getIntY()+(size/2);
            g.setColor(compassColor);
            g.fillOval(position.getIntX(), position.getIntY(), size, size);
            g.setColor(Color.red);
            g.drawLine(x, y, x + direction.getIntX(), y + direction.getIntY());
        } 
    }
}
