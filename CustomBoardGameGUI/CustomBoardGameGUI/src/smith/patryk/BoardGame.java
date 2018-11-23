package smith.patryk;


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
    
    
    private final int numRows;
    private final int numCols;
    
    BoardGame(int _x, int _y, int numberOfPlayers){
        init( _x, _y, numberOfPlayers);
        numRows = _x/ 16;
        numCols = _y/ 16;
    }
    
    private void init(int _x, int _y, int numberOfPlayers){
        this.setNumPlayers(numberOfPlayers);
        
    }
}
