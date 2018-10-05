package com.zyBooks.com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;

// HistogramComponent extends the functionality of a JComponent
// in order to draw a histogram.
public class HistogramComponent extends JComponent {
   
   // Paints a histogram with three bins
   @Override
   public void paintComponent(Graphics g) {  
      // Cast to Graphics2D
      Graphics2D graphicsObj = (Graphics2D) g;
      
      // Draw 1st bin as an olive colored rectangle at (10,10)
      // with width = 200 and height = 50
      Rectangle binRectangle1 = new Rectangle(10, 10, 200, 50); 
      Color binColor1 = new Color(128, 128, 0);
      graphicsObj.setColor(binColor1);
      graphicsObj.fill(binRectangle1);
      
      // Draw 2nd bin as a teal blue rectangle at (10,75)
      // with width = 150 and height = 50
      Rectangle binRectangle2 = new Rectangle(10, 75, 150, 50); 
      Color binColor2 = new Color(0, 200, 200);
      graphicsObj.setColor(binColor2);
      graphicsObj.fill(binRectangle2);
      
      // Draw 3rd bin as a gray rectangle at (10,140)
      // with width = 350 and height = 50
      Rectangle binRectangle3 = new Rectangle(10, 140, 350, 50); 
      Color binColor3 = new Color(100, 100, 100);
      graphicsObj.setColor(binColor3);
      graphicsObj.fill(binRectangle3);
   }
}