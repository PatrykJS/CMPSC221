package smith.patryk;

import java.util.ArrayList;

/**
 * @author Patryk Smith
 * 
 * This class extends the general Game abstract class.
 * It needs a current state and a previous state, thus
 * @see #base and @see #background variables are used 
 * to create two two-dimensional arrays that keep track
 * of the text based on location of entities and the 
 * ground.
 * 
 * @see #init() is used to keep the constructor clean.
 * It initializes and creates the base and background
 * components of the game. It initializes them to the
 * background tile #.
 * 
 */
public class BoardGame extends Game{
    
    public String[][] base;
    public String[][] background;
    public ArrayList<Player> players;
    
    BoardGame(int _x, int _y){
        init( _x, _y);
    }
        
    private void init(int _x, int _y){
        this.setNumPlayers(1);
        base = new String[_x][_y];
        background = new String[_x][_y];
        
        for( int i = 0; i < _x; i++){
            for( int j = 0; j < _y; j++){
                base[i][j] = "#";
                background[i][j] = "#";
                
            }
        }
    }    
}
