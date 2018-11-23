package smith.patryk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public final class GameScreen extends JPanel implements KeyListener, MouseListener {

    double SleepTime = 1000 / 30, lastRefresh = 0;
    private final Treasure treasure;
    private final EndScreen endscreen;
    private BufferedImage background;
    private BufferedImage[] sprites;
    private Dimension screenSize;
    private Vector2D treasureDirection;
    private Compass compass;

    final int width = 72;
    final int height = 72;
    final int rows = 15;
    final int cols = 15;

    private final int x = 72;
    private final int y = 72;
    private final BufferedImage textures;

    /**
     * Creates new form GameScreen
     *
     * @param _endscreen
     * @param d
     * @throws java.io.IOException
     */

    public GameScreen(EndScreen _endscreen, Dimension d) throws IOException {
        super();
        addKeyListener(this);
        addMouseListener(this);

        InputStream url = getClass().getResourceAsStream("/resources/Textures.png");

        textures = ImageIO.read(url);

        treasure = new Treasure(d.width / 72, d.height / 72);
        endscreen = _endscreen;
        screenSize = d;
        init();

    }

    public void init() throws IOException {
        int tileSize = (int) ((int) screenSize.height / 72.0);

        setBackground(Color.white);
        sprites = new BufferedImage[rows * cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sprites[(i * cols) + j] = textures.getSubimage(
                        j * width,
                        i * height,
                        width,
                        height
                );
            }
        }
        background = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        //System.out.print(width +", "+ height+"\n");
        background = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
        Graphics g_res = background.getGraphics();

        for (int i = 0; i < screenSize.width; i++) {
            for (int j = 0; j < screenSize.height; j++) {
                g_res.drawImage(sprites[0], i * width, j * height, endscreen);
            }
        }

        InputStream url = getClass().getResourceAsStream("/resources/background.png");

        ImageIO.write((RenderedImage) background, "png", new File(url.toString()));
        background = ImageIO.read(new File(url.toString()));
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        treasureDirection = new Vector2D(treasure.getTreasurePosition().getIntX() - treasure.getPlayerPosition().getX(),
                treasure.getTreasurePosition().getY() - treasure.getPlayerPosition().getY());

        compass = new Compass(treasure.getTreasureChest(), treasure.getPlayer());
    }

    @Override
    public void paintComponent(Graphics g) {
        requestFocus();
        g.drawImage(background, 0, 0, endscreen);
        g.drawImage(sprites[1], treasure.getTreasurePosition().getIntX()*72, treasure.getTreasurePosition().getIntY()*72, endscreen);
        drawPlayer(g, treasure.getPlayerPosition().getIntX() * 72, treasure.getPlayerPosition().getIntY() * 72);

//        g.setColor(Color.red);
//        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
//        g.drawString("X: "+treasure.getTreasurePosition().getIntX()+", Y:"+treasure.getTreasurePosition().getIntY(),0 , 20);
        g.drawString("X: " + treasure.getPlayerPosition().getIntX() + ", Y:" + treasure.getPlayerPosition().getIntY(), 0, 40);
        
        Vector2D compassPosition = new Vector2D( screenSize.width - 50, screenSize.height -50);
        
        int chestX = treasure.getTreasurePosition().getIntX();
        int chestY = treasure.getTreasurePosition().getIntY();
        
        int playerX = treasure.getPlayerPosition().getIntX();
        int playerY = treasure.getPlayerPosition().getIntY();
        
        Vector2D direction = new Vector2D (Math.abs(chestX - playerX)/chestX-playerX, Math.abs(chestY - playerY)/chestY-playerY);
        System.out.println("X: " + direction.getIntX() + " Y: " + direction.getIntY());
        compass.draw(g, compassPosition, direction);

        repaint();
    }

    private void drawPlayer(Graphics g, int _x, int _y) {
        g.drawImage(sprites[2], _x, _y, null);
        g.drawImage(sprites[3], _x, _y + sprites[2].getHeight(), null);
    }

    private void drawTreasureChest(Graphics g) {
        g.drawImage(sprites[1], treasure.getTreasurePosition().getIntX() * 72, treasure.getTreasurePosition().getIntY() * 72, endscreen);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                treasure.movePlayer("w");
                //treasure.getPlayer().setPosition(treasure.getPlayerPosition().getIntX(),treasure.getPlayerPosition().getIntY()-1);
                //treasure.move(new Vector2D(0,-1));
                break;
            case 'a':
                treasure.movePlayer("a");
                //treasure.getPlayer().setPosition(treasure.getPlayerPosition().getIntX()-1,treasure.getPlayerPosition().getIntY());
                break;
            case 's':
                treasure.movePlayer("s");
                //treasure.getPlayer().setPosition(treasure.getPlayerPosition().getIntX(),treasure.getPlayerPosition().getIntY()+1);
                break;
            case 'd':
                treasure.movePlayer("d");
                // treasure.getPlayer().setPosition(treasure.getPlayerPosition().getIntX()+1,treasure.getPlayerPosition().getIntY());
                break;
            case ' ':
                if (compass.canUse()) {
                    compass.use();
                } else {
                    Timer tempMessage = new Timer(200, (ActionEvent evt) -> {
                        displayCompassUsed();
                        repaint();
                    });

                }

                break;
            default:
                break;
        }
        if (treasure.getPlayerPosition().getIntX() == treasure.getTreasurePosition().getIntX()
                && (treasure.getTreasurePosition().getIntY() == treasure.getPlayerPosition().getIntY()
                || treasure.getTreasurePosition().getY() == treasure.getPlayerPosition().getIntY() + 1)) {

            this.setVisible(false);
            endscreen.setVisible(true);
        }
    }

    public void displayCompassUsed() {
        Graphics g = this.getGraphics();
        g.setColor(Color.red);
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g.drawString("Compass used up!", 0, this.getHeight() - 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //System.out.println("Typed!");

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //System.out.println("Typed!");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //System.out.println("Clicked!");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
