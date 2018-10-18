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
        g.setColor(Color.GREEN);
        g.fillOval(0, 0, 400, 400);
        g.setColor(Color.WHITE);
        g.fillOval(100, 100, 200, 200);
        
        g.setColor(Color.ORANGE);        
        g.fillRect(350, 100, 20, 20);
        g.fillRect(75, 210, 20, 20);
        g.fillRect(60, 300, 20, 20);
        g.fillRect(30, 250, 20, 20);
        g.fillRect(350, 175, 20, 20);
        g.fillRect(250, 300, 20, 20);
        g.fillRect(300, 250, 20, 20);
        g.fillRect(80, 50, 20, 20);
        
        g.setColor(Color.RED);
        g.fillOval(100, 25, 20, 20);
        g.fillOval(210, 75, 20, 20);
        g.fillOval(300, 60, 20, 20);
        g.fillOval(250, 30, 20, 20);
        g.fillOval(70, 160, 20, 20);
        g.fillOval(230, 300, 20, 20);
        g.fillOval(50, 250, 20, 20);
        
        g.setColor(Color.darkGray);
        g.drawArc(50, 100, 20, 20, 60, 90);
        g.drawArc(50, 250, 20, 20, 60, 90);
        g.drawArc(100, 250, 20, 20, 0, 60);
        g.drawArc(100, 350, 20, 20, -45, -120);
        g.drawArc(150, 80, 20, 20, 180, 245);
        g.drawArc(150, 375, 20, 20, 0, 32);
        g.drawArc(200, 60, 20, 20, 128, 190);
        g.drawArc(200, 300, 20, 20, 240, 290);
        g.drawArc(250, 80, 20, 20, 256, 312);
        g.drawArc(250, 290, 20, 20, 78, 111);
        g.drawArc(300, 270, 20, 20, 136, 159);
        g.drawArc(300, 60, 20, 20, 200, 260);
        
        
        
        
//        g.setColor(Color.BLACK);
//                for(int i = 0; i < 40; i++){
//            for(int j = 0; j < 40; j++){
//                g.drawRect(i*10, j*10, 10, 10);
//                
//            }  
//        }
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30)); 
        g.drawString("HAPPY HOLIDAYS!", 60, 350);
        
    }
    
   
}

