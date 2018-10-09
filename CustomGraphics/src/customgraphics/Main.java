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

/*



create a recursive function that goes in circles to make a holiday
christmas wreath. 
Use once usiing :
    Eectangles,
    Arcs,
    Ovals,
    Custom Polygon

Draw String = "Happy holidays"



*/