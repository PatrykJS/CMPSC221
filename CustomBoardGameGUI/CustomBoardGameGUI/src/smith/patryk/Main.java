/*
 */
package smith.patryk;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 *
 * @author Patryk Smith
 */
public class Main {
    public static void main(String[] args)  {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                
        JFrame application;
        Starting openingWindow = null;
        GameScreen gameWindow = null;
        EndScreen endScreen;
        
        application = new JFrame();
        endScreen = new EndScreen(0);
        try {
            gameWindow = new GameScreen(endScreen, screenSize);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            openingWindow = new Starting(gameWindow);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
