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
    
    private boolean win; 
    
    private double begin;
    private double end;
    
    public Treasure(int _width, int _height){
        super(_width, _height, 1);
        win = false;
        begin = System.currentTimeMillis();
        this.setWidth(_width);
        this.setHeight(_height);
        init();
    }
    public BoardGame getBoard(){
        return board;
    }
        
    private void init(){
        
        board = new BoardGame(this.getWidth(), this.getHeight(), 1);
        Random rand = new Random();
        
        
        
        Vector2D player_starting_position = new Vector2D( (int)(this.getWidth() / 2.0), (int)(this.getHeight() / 2.0));
        Vector2D treasure_starting_position = new Vector2D(rand.nextInt(this.getWidth()-3)+3, rand.nextInt(this.getHeight()-3)+3);
        
        p = new Player("Pplayer", player_starting_position);
        t = new TreasureChest("Treasure", treasure_starting_position);
        
        board.players = new ArrayList<>(1);
        board.players.add(p);
        
    }
    public Player getPlayer(){
        return p;
    }
    public TreasureChest getTreasureChest(){
        return t;
    }
    public void movePlayer(String s){
        
        switch (s){
            case "w":
                if((p.getPositionY() > 0)){
                    p.move(new Vector2D(0, -1));
                }else{
                    p.setPosition(p.getPositionX(), this.getHeight()-3);
                }
                break;
            case "a":
                if((p.getPositionX() > 0)){
                    p.move(new Vector2D(-1, 0));
                }else{
                    p.setPosition(this.getWidth()-1, p.getPositionY());
                }
                break;
            case "s":
                if((p.getPositionY()+3 < this.getHeight())){
                    p.move(new Vector2D(0,  1));
                }else{
                    p.setPosition(p.getPositionX(), 0);
                }
                break;
            case "d":
                if((p.getPositionX()+1 < this.getWidth())){
                    p.move(new Vector2D(1, 0));
                }else {
                    p.setPosition(0, p.getPositionY());
                }
                break;
            default:
                p.move(new Vector2D(0, 0));
                break;
        }
        //System.out.println(p.getPositionX() +" " + p.getPositionY());
    }
    public void move(Vector2D v){
        p.move(v);
    }
    public Vector2D getPlayerPosition(){
        return p.getPosition();
    }
    public Vector2D getTreasurePosition(){
        return t.getPosition();
    }
    public void setWin(boolean w){
        win = w;
    }
    public boolean didWin(){
        return win;
    }    
}