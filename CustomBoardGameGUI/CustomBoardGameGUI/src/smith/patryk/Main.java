/*
 */
package smith.patryk;

import java.awt.Dimension;
import java.awt.Toolkit; 
import javax.swing.JFrame;

/**
 *
 * @author Patryk Smith
 */
public class Main {
    public static void main(String[] args)  { 
        // Create loading screen...;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        JFrame application;
        // Creating JFrame
        System.out.println("Creating Main Menu..."); 
        Starting openingWindow = new Starting(screenSize); 
        System.out.println("Done Creating Main Menu."); 
        System.out.println("Creating JFrame..."); 
        application = new JFrame();  
        System.out.println("Done Creating JFrame."); 
        System.out.println("Configuing Window...");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        application.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        application.setUndecorated(true);
        application.setVisible(true); 
        System.out.println("Done Configuring Window.");
        System.out.print("Adding Music");
        openingWindow.setSize(application.getSize());
        openingWindow.setVisible(true);
        openingWindow.startSong();
        System.out.println("Done adding Music.");
        application.add(openingWindow);
        application.add(Starting.gameWindow);
        application.add(Starting.endScreen);
        application.add(Starting.settings);
        application.add(Starting.tutScreen);
        System.out.println("Done Creating Windows.");
        application.setVisible(true); 
        
    } 
}
