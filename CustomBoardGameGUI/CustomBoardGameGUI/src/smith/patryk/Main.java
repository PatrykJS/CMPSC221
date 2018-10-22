/*
 */
package smith.patryk;


/**
 *
 * @author Patryk Smith
 */
public class Main{
    final static int w = 420;
    final static int h = 450;
    
    public static void main(String[] args) {
        
        TreasureGUI game = new TreasureGUI(h, w, 10,10);
        game.run();
        
    }
}
