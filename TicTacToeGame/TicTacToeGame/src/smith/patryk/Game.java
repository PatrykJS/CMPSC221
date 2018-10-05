package smith.patryk;

import java.util.ArrayList;

/**
 * @author Patryk Smith
 * 
 * Is an abstract class that has the fundamentals of
 * a game in my definition which has
 * @see #numPlayers is the number of players
 * @see #height is the height of the game
 * @see #width is the width of the game
 * @see #score is the final score of the game
 */
abstract public class Game {
    
    private int numPlayers;
    private int height;
    private int width;
    private int score;
    
    ArrayList<Player> players;
    
    public void setWidth(int _width){
        width = _width;
    }
    public void setHeight(int _height){
        height = _height;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getScore(){
        return score;
    }
    public void setScore(int _score){
        score = _score;
    }
    public void setNumPlayers(int _numPlayers){
        numPlayers = _numPlayers;
    }
    public void quit(int i){
        System.exit(i);
    }
}
