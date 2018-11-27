/*
 */
package smith.patryk;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Patryk Smith
 */
public class BackgroundImage extends JPanel {
    private Image img;
    
    public BackgroundImage(Image _img){
        img = _img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }
    
    
    @Override
    public void paintComponents(Graphics g){
        g.drawImage(img, 0, 0, this);
    }
    
}
