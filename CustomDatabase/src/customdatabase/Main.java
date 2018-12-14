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
         
        MainScreen app = new MainScreen(); 
        app.setSize(600,600);
         
        app.add(MainScreen.deleteWindow);
        app.add(MainScreen.insertWindow);
        app.add(MainScreen.searchWindow);
        
        app.setLocationRelativeTo(null);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setTitle("Custom Database");
        
    }
}
