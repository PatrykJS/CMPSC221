/*
 */
package youtubedownloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Patryk Smith
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        try {
            // TODO code application logic here
           
            URL url = new URL("https://www.youtube.com/watch?v=Z9e2c4orAh0");
             
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            Map<String, List<String>> map = conn.getHeaderFields();
            map.entrySet().forEach((entry) -> {
                System.out.println("Key : " + entry.getKey()
                        + " ,Value : " + entry.getValue());
            });
            System.out.println("Content Length :" +conn.getContentLengthLong());


//       Window app = new Window();
//       app.setSize(400,400);
//       app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//       app.setVisible(true);


        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
