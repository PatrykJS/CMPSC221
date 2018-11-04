package smith.patryk;


import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import javafx.scene.shape.Line;

import javax.swing.JFrame;

public class Main {
	
   public static void main(String[] args) {
   
        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        
        // objects
        Line l = new Line();
        Pyramid p = new Pyramid();
        glPushMatrix();
        glTranslate(10,10,10);
        Cube c = new Cube();
        glPopMatrix();
        glcanvas.addGLEventListener(p);
        glcanvas.addGLEventListener(c);
        glcanvas.setSize(400, 400);
        
	
        //creating frame
        final JFrame frame = new JFrame (" Basic 3D Objects");

        //adding canvas to it
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final FPSAnimator animator = new FPSAnimator(glcanvas,300,true);
      animator.start();
   }//end of main
	
}

