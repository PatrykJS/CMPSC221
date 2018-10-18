package customgraphics;

import javax.swing.JFrame;

/**
 * @author Patryk Smith
 */
public class Main {
    
    final static int WIDTH = 420;
    final static int HEIGHT = 450;
    
    public static void main(String[] args){
        JFrame application = new JFrame();
        
        CustomGraphics window = new CustomGraphics();
        window.setSize(WIDTH, HEIGHT);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(window);          
        application.setSize(WIDTH, HEIGHT); 
        
        application.setVisible(true);
                  
    }
}
