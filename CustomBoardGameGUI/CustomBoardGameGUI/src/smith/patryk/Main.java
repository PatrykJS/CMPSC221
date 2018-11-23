/*
 */
package smith.patryk;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Patryk Smith
 */
public class Main {
    public static void main(String[] args) throws IOException  {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                
        JFrame application;
        StartingScreen openingWindow;
        GameScreen gameWindow;
        EndScreen endScreen;
        
        application = new JFrame();
        endScreen = new EndScreen(0);
        gameWindow = new GameScreen(endScreen, screenSize);
        openingWindow = new StartingScreen(gameWindow);
        
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        openingWindow.setSize(screenSize.width,screenSize.width);
        gameWindow.setSize(screenSize.width,screenSize.width);
        endScreen.setSize(screenSize.width,screenSize.width);
        application.setSize(screenSize.width,screenSize.width);
        
        
        openingWindow.setVisible(true);
        gameWindow.setVisible(false);
        endScreen.setVisible(false);
        
        
        application.add(openingWindow);
        application.add(gameWindow);
        application.add(endScreen);
        
        application.setVisible(true);
        
        
    }

}
