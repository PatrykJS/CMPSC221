/*
 */
package youtubedownloader;

import javax.swing.JFrame;


/**
 *
 * @author Patryk Smith
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        // TODO code application logic here
       
       Window app = new Window();
       app.setSize(400,400);
       app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       app.setVisible(true);
        
    }
    
}
