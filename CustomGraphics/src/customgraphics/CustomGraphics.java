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
        //g.fillOval((height-size)/2, (width-size)/2, size, size);
        
        int numPoints = 3;
        int triangleSize = 50;
        
        int posX = width/2;
        int posY = height/2;
        
        int[] xPoints = {(posX+(0*triangleSize)), (posX+(triangleSize*2)), (posX+(0*triangleSize))};
        int[] yPoints = {(posY+(1*triangleSize)), (posY+(0*triangleSize)), (posY+(0*triangleSize))};
        
        
        //int[] pointA = [((int)(posX-triangleSize)/2)];
        g.fillPolygon(xPoints , yPoints, numPoints);   
        
        g.setColor(Color.BLACK);
        g.fillOval(((posX-triangleSize)/2),((posY-triangleSize)/2), 10, 10 );
        
    }
}

