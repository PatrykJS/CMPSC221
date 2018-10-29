/*
 */
package smith.patryk;


import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Patryk Smith
 */
public class TreasureGUI extends Treasure{
    private final JFrame application;
    private final StartingScreen openingWindow;
    private final GameScreen gameWindow;
    private final EndScreen endScreen;
    public TreasureGUI(int windW, int windH, int _width, int _height) {
        super(_width, _height);
        
        application = new JFrame();
        endScreen = new EndScreen(0);
        gameWindow = new GameScreen(this, endScreen);
        openingWindow = new StartingScreen(gameWindow);
        
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        openingWindow.setSize(400,400);
        gameWindow.setSize(400,400);
        endScreen.setSize(400,400);
        application.setSize(420,420);
        
        openingWindow.setVisible(true);
        gameWindow.setVisible(false);
        endScreen.setVisible(false);
        
        
        application.add(openingWindow);
        application.add(gameWindow);
        application.add(endScreen);
                
        
        
    }
    public void run(){
        application.setVisible(true);
        
    } 
}
