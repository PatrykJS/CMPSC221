package smith.patryk;

/**
 * @author Patryk Smith
 * 
 * Basic position object with
 * x and y components. Can be initialized
 * via new position or sending it x and y 
 * components.
 */
public class Position {
    public int y;
    public int x;
    public Position(int _x, int _y){
        x = _x;
        y = _y;
    }
    public Position(Position p){
        y = p.y;
        x = p.x;
    }
    public boolean compareTo(Position p){
        if((this.x == p.x) && (this.y ==p.y)){
            return true;
        }else{
            return false;
        }
    }
}
