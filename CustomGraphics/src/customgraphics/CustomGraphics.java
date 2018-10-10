package customgraphics;

import java.awt.*;
import java.util.Random;
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
        
        drawWreath(g);
        drawCherries(g, 20);
        drawText(g);
        drawBow(g);
    }
    
    public void drawCenteredOval(Graphics g, Color c,  int x, int y, int width, int height){
        
        x += getWidth()/2;
        y += getHeight()/2;
        
        g.setColor(c);
        g.fillOval((x-width/2), (y-height/2), width, height);
    }
    public void drawWreath(Graphics g){
        
        drawCenteredOval(g, Color.GREEN, 0, 0, getWidth(), getHeight());
        drawCenteredOval(g, Color.WHITE, 0, 0, getWidth()/4, getHeight()/4);
    }
    public void drawCherries(Graphics g, int i){
        
        Random rand = new Random();
        int k = 1;
        int l = 1;
        int temp = 0;
        for(int j=0; j<i; j++){
            
            temp = rand.nextInt()%2;
            if(temp==0){
                k=1;
            }else{
                k=-1;
            }
            temp = rand.nextInt()%2;
            if(temp==0){
                l=-1;
            }else{
                l=1;
            }
            drawCenteredOval(g, Color.RED,(rand.nextInt(getWidth()/8) + (getWidth()/4))*k ,(rand.nextInt(1 + getHeight()- (getHeight()/8)) + (getHeight()/8))*l, 20, 20);
        }
    }
    public void drawText(Graphics g){
        
       
    }
    public void drawBow(Graphics g){
        
       
    }
}

