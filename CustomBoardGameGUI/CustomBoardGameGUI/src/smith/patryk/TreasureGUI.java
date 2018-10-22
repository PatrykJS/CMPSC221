/*
 */
package smith.patryk;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Patryk Smith
 */
public class TreasureGUI extends Treasure{
    
    public TreasureGUI(int windW, int windH, int _width, int _height) {
        super(_width, _height);
        
        JFrame application = new JFrame();
        JPanel window = new JPanel();
        
        
        window.setSize(windW, windH);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(window);          
        application.setSize(_width, _height); 
        application.setVisible(true);
    }
    public void run(){
        // Get User Input
        // Move Player
        // Update Map
        String userInput = "";
        this.movePlayer(" ");
        
        do{      
            
            // Get Userinput
            if(!userInput.equals("q")){                
                this.movePlayer(userInput);
                if(this.win()){                    
                    // Show endscreen
                    // Print Score
                }                
            }
        }while(!userInput.equals("q"));
    }
    public String displayMenu(){
        
        String s = "";
        s+= "Welcome to Treasure!";
        s+= "Try to find the treasure!";
        s+= "To MOVE around use the WASD keys and press ENTER after each move.";
        s+= "To QUIT the game press q and ENTER at any time.";
        s+= "Press and key and ENTER to begin!";
        return s;
        
    }
    
    
}
