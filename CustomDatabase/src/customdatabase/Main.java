/*
 */
package customdatabase;

import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author Patryk Smith
 */
public class Main {
    
    public static void main(String[] args) throws SQLException{
         
        ApplicationWindow app = new ApplicationWindow(); 
        
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setTitle("Leber & Bonham Electrical Inc");
        
    }
}
