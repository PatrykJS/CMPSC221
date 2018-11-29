/*
 */
package nec.reference.application;

import javax.swing.JFrame;

/**
 *
 * @author Patryk Smith
 */
public class Main {
    public static void main(String[] args){
        
        ApplicationWindow app = new ApplicationWindow();
        //app.setSize(1080,300);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setTitle("Leber & Bonham Electrical Inc");
        
    }
}
