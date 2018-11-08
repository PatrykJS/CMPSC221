/*
 */
package smith.patryk;

import smith.patryk.TreasureGUI;


/**
 *
 * @author Patryk Smith
 */
public class Main{
    final static int w = 400;
    final static int h = 400;
    
    public static void main(String[] args) {
        
        TreasureGUI game = new TreasureGUI(h, w, 50,21);
        game.run();
        
    }
}
