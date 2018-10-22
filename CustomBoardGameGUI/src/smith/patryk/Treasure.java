package smith.patryk;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Patryk Smith
 * 
 * The treasure class is a game where you find the "hidden" treasure.
 * We extend it from BoardGame which has characteristics of a general
 * board game. There are special custom classes to better organize data
 * such as the @see #Player class and @see #TreasureChest class. These
 * two classes are of subclass @see Entity, which is described in its
 * file more detailed. WinScreen is used for end of game animation 
 * purposes, just for extra stuff. The @see #score is calculated using
 * time and arithmetic to generate a score dependent on time.
 * 
 * @see #init() initializes the class, as every class that I have made, 
 * to keep the constructor cleaner. I need to create two different 
 * 2-D arrays to keep track of the entities and the terrain. (further 
 * explained in @see #BoardGame class.
 * 
 * When we initialize a player we center it inside the map. We do so
 * dynamically if the size of the map changes. We also create a randomly
 * selected coordinate on the map for the @see #TreasureChest to spawn.
 * 
 * The player list array is used for future expansion as well as the 
 * Boundary Class.
 * 
 * @see #movePlayer() takes the input from the client's user input and
 * update the player's coordinates properly without going out of bounds
 * and off the map.
 * 
 * @see #display() is returned to the client to output the current state
 * of the map. 
 * 
 * @see #win() and @see #winScreen() are just functions to return and create
 * an endscreen animation for extra pizzazz.
 * 
 */

public class Treasure extends BoardGame {
    private BoardGame board;
    private Player p;
    private TreasureChest t;
    private WinScreen w;
    private double begin;
    private double end;
    
    public Treasure(int _width, int _height){
        
        super(_width, _height);
        begin = System.currentTimeMillis();
        this.setWidth(_width);
        this.setHeight(_height);
        init();
    }
    
          
    private void init(){
        
        w = new WinScreen();
        board = new BoardGame(this.getWidth(), this.getHeight());
        Random rand = new Random();
        Position player_starting_position = new Position( (int)(this.getWidth() / 2.0), (int)(this.getHeight() / 2.0));
        Position treasure_starting_position = new Position(rand.nextInt(this.getWidth()), rand.nextInt(this.getHeight()));
        
        p = new Player("P", player_starting_position);
        t = new TreasureChest("%", treasure_starting_position);
        board.players = new ArrayList<>(1);
        board.players.add(p);
        board.base[this.getWidth()/2][this.getHeight()/2] = p.getToken();                  
    }
    
    public void movePlayer(String s){
        
        for( int i = 0; i < this.getWidth(); i++){
            for( int j = 0; j < this.getHeight(); j++){
                board.base[i][j] = board.background[i][j];
            }
        }
        base[p.getPositionY()][p.getPositionY()] = "#";
        switch (s){
            
            case "w":
                if((p.getPositionY() > 0)){
                    p.increasePosition(0, -1);
                }
                break;
            case "a":
                if((p.getPositionX() > 0)){
                    p.increasePosition(-1, 0);
                }
                break;
            case "s":
                if((p.getPositionY()+1 < this.getHeight())){
                    p.increasePosition(0, 1);
                }
                break;
            case "d":
                if((p.getPositionX()+1 < this.getWidth())){
                    p.increasePosition(1, 0);
                }
                break;
            default:
                p.increasePosition(0, 0);
                break;
        }
    }
    
    public String display(){
        board.base[p.getPositionX()][p.getPositionY()] = p.getToken();
        board.base[t.getPositionX()][t.getPositionY()] = t.getToken();
        
        String s ="";
        for( int i = 0; i < this.getHeight(); i++){
            for( int j = 0; j < this.getWidth(); j++){
                s += board.base[j][i];
            }
            s+= "\n";
        }      
        return s;
    }
    
    public String[] winscreen (){        
        return w.screen();        
    }
    public boolean win(){
        
        if((p.getPositionX() == t.getPositionX()) && (p.getPositionY() == t.getPositionY())){            
            end = System.currentTimeMillis();
            if(end-begin != 0){
            	this.setScore(Math.abs((int)(1/( (end - begin)/begin)) / 365 * 4000));
            }else{
		this.setScore(10000);
            }
            return true;            
        }else{            
            return false;            
        }        
    }    
}