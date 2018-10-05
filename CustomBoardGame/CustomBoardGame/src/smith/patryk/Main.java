package smith.patryk;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Patryk Smith
 * @version 1.4
 * 
 * @see #main:    displays the main menu and then initializes and loops the game
 * @see #runGame: runs the game:
 *      Pseudocode: 
 *      display the starting map
 *      get user input
 *      update player position based on input unless they press q to quit
 *      loop to display and get input and update position.
 */
public class Main {
    
    public static void main(String[] args) {
        
        displayMenu();                                                          
        runGame();                                                                  
    }
     
    public static void displayMenu(){
        
        Scanner scnr = new Scanner(System.in);
        String s;
        System.out.println("Welcome to Treasure!");
        System.out.println("Try to find the treasure!");
        System.out.println("To MOVE around use the WASD keys and press ENTER after each move.");
        System.out.println("To QUIT the game press q and ENTER at any time.");
        System.out.println("Press and key and ENTER to begin!");
        s = scnr.next();
        
    }
    
    public static void runGame(){
        
        Scanner scnr = new Scanner(System.in);
        Treasure game = new Treasure(10,10);
        String userInput;
        game.movePlayer(" ");

        do{            
            System.out.println(game.display());
            System.out.print("Move: ");
            userInput = scnr.next();
            if(!userInput.equals("q")){                
                game.movePlayer(userInput);
                if(game.win()){                    
                    System.out.println(game.display());
                    for(String s: game.winscreen()){                        
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        System.out.println(s);                    
                    }                    
                    System.out.println("Your Score:"+game.getScore());
                    game.quit(0);
                }                
            }
        }while(!userInput.equals("q"));        
     }    
}
