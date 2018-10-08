package customgraphics;

import java.awt.*;
import javax.swing.*;

/**
 * @author Patryk Smith
 */
public class CustomGraphics extends JPanel{
    
    public CustomGraphics(){
        super();
        init();
    }
    
    public void init(){
        setBackground(Color.WHITE);              
    }
    
    @Override
    public void paintComponent(Graphics g){
        int width = getWidth();           
        int height = getHeight();
        int size = 50;
        
        
        g.setColor(Color.YELLOW);        
        g.fillOval((height-size)/2, (width-size)/2, size, size);
        
        
        //g.fillPolygon(xPoints, yPoints, width);
        
    }
}

