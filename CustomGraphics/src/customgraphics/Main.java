package customgraphics;

import javax.swing.JFrame;

/**
 * @author Patryk Smith
 */
public class Main {
    
    final static int WIDTH = 200;
    final static int HEIGHT = 200;
    
    public static void main(String[] args){
        JFrame application = new JFrame();
        CustomGraphics window = new CustomGraphics();
        
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(window);          
        application.setSize(WIDTH, HEIGHT); 
        
        application.setVisible(true);
                  
    }
}
