package smith.patryk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer; 

public final class GameScreen extends JPanel implements KeyListener, MouseListener {

    double SleepTime = 1000 / 30, lastRefresh = 0;
    private final Treasure treasure;
    private EndScreen endScreen;
    private BufferedImage background;
    private BufferedImage[] sprites;
    private Dimension screenSize;
    private double begin, end;
    private static FloatControl gainControl;
    
    Vector2D compassPosition;
    Vector2D direction;
    Vector2D treasureDirection;
    
    private Compass compass;
    private Graphics graphics;
    private Timer t;
    private Timer time;
    
    private final int width = 72;
    private final int height = 72;
    private final int rows = 15;
    private final int cols = 15;
    private final int compassUseTime = 3000;
    private int timeCount;
    
    private final int x = 72;
    private final int y = 72;
    private final BufferedImage textures;
    
    private InputStream audioInDirect;
    private AudioInputStream audioInBuffer;
    private Clip song;
    private final String audioClip = "/resources/GamePlay1.wav";
    
    /**
     * Creates new form GameScreen
     *
     * @param _endscreen
     * @param d
     * @throws java.io.IOException
     */

    public GameScreen(EndScreen _endscreen, Dimension d) throws IOException {
        super();
        System.out.println("Initializing Game Screen...");
        addKeyListener(this);
        addMouseListener(this);
        
        InputStream url = getClass().getResourceAsStream("/resources/Textures.png");
        try {
            System.out.println("Adding Music To Main Game Screen...");
            audioInDirect = getClass().getResourceAsStream(audioClip);
            InputStream bufferedIn = new BufferedInputStream(audioInDirect);
            audioInBuffer = AudioSystem.getAudioInputStream(bufferedIn);
            song = AudioSystem.getClip(); 
            song.open(audioInBuffer);
            gainControl = (FloatControl) song.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(Starting.soundVolume);
            
            song.stop(); 
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
        }  
        textures = ImageIO.read(url);  
        treasure = new Treasure(d.width / 72, d.height / 72);
        System.out.println("Done Adding Music to Main Game Screen.");
        endScreen = _endscreen;
        screenSize = d;
        init();
        timeCount = 0;
    }

    public void init() throws IOException { 
        System.out.println("Creating Textures...");
        int tileSize = (int) ((int) screenSize.height / 72.0);
        compassPosition = new Vector2D(50, 50);
        direction = new Vector2D (0,0);
        treasureDirection = new Vector2D(0, 0);
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
        
        background = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
        Graphics g_res = background.getGraphics();

        for (int i = 0; i < screenSize.width; i++) {
            for (int j = 0; j < screenSize.height; j++) {
                g_res.drawImage(sprites[0], i * width, j * height, endScreen);
            }
        }

        InputStream url = getClass().getResourceAsStream("/resources/background.png");

        ImageIO.write((RenderedImage) background, "png", new File(url.toString()));
        background = ImageIO.read(new File(url.toString()));
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        treasureDirection = new Vector2D(treasure.getTreasurePosition().getIntX() - treasure.getPlayerPosition().getX(),
                                        treasure.getTreasurePosition().getY() - treasure.getPlayerPosition().getY());

        
        compass = new Compass(treasure.getTreasureChest(), treasure.getPlayer());
        System.out.println("Done Creating Textures.");
        System.out.println("Creating Timers...");
        t = new Timer(compassUseTime,  (ActionEvent evt) -> { });
        t.setRepeats(false);
        
        
        time = new Timer(1000, (ActionEvent evt) -> {
            timeCount++; 
        });
        time.setRepeats(true);
        System.out.println("Done Creating Timers.");
        System.out.println("Done Creating Game Screen.");
    }

    public void restart(){
        treasure.reset();
        compass = new Compass(treasure.getTreasureChest(), treasure.getPlayer());
         
    }
    @Override
    public void paintComponent(Graphics g) {
        graphics = g;
        requestFocus();
        g.drawImage(background, 0, 0, endScreen);
        drawPlayer(g, treasure.getPlayerPosition().getIntX() * 72, treasure.getPlayerPosition().getIntY() * 72);
        //g.drawString("X: " + treasure.getPlayerPosition().getIntX() + ", Y:" + treasure.getPlayerPosition().getIntY(), 0, 40);
        
        compass.draw(g, treasure, compassPosition, direction);
        if(!t.isRunning()){
          compass.setShow(false);
        }
        
        if(treasure.getTreasureChest().getShow()){
            drawTreasureChest(g);
        } 
        if(treasure.didWin()){
            end = System.currentTimeMillis();
            this.setVisible(false);
            song.stop();
            System.out.println("Starting End Screen Song...");
            Starting.endScreen.setScore((int)(((end - begin) *100 *(4 - compass.getUses()))));
            Starting.endScreen.startSong();
            Starting.endScreen.setVisible(true);
        }  
        repaint();
    }
    
    private void drawPlayer(Graphics g, int _x, int _y) {
        g.drawImage(sprites[30], _x, _y, null);
        g.drawImage(sprites[31], _x, _y + sprites[2].getHeight(), null);
    }

    private void drawTreasureChest(Graphics g) {
        g.drawImage(sprites[1], treasure.getTreasurePosition().getIntX() * 72, treasure.getTreasurePosition().getIntY() * 72, endScreen);
        repaint();
    }
    
    public void updateVolume(){
        gainControl.setValue(Starting.soundVolume);
    } 
    
    @Override
    public void keyTyped(KeyEvent e) { }
 
    @Override
    public void keyPressed(KeyEvent e) { 
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            Starting.settings.setVisible(true);
            Starting.gameWindow.setVisible(false);
            Starting.settings.setReference(2);
        }else if(e.getKeyCode() == Starting.keyMap[0]){
            treasure.movePlayer("w");
        }else if(e.getKeyCode() == Starting.keyMap[1]){
            treasure.movePlayer("a");
        }else if(e.getKeyCode() == Starting.keyMap[2]){
            treasure.movePlayer("s");
        }else if(e.getKeyCode() == Starting.keyMap[3]){
            treasure.movePlayer("d");
        }else if(e.getKeyCode() == Starting.keyMap[4]){
             if(treasure.getPlayer().dig(treasure)){
                Starting.endScreen.setVisible(true);
                this.setVisible(false);
                stopSong(); 
                Starting.endScreen.startSong();
            } 
        }else if(e.getKeyCode() == Starting.keyMap[5]){
            if(compass.canUse() && !t.isRunning()){
                compass.use();
                compass.setShow(true);
                t.start();  
            }else{ 
                compass.setShow(false);
                repaint();
            } 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) {
        if( e.getButton() ==1){
            //System.out.println("Clicked!");
            if(treasure.getPlayer().dig(treasure)){
                System.out.println("User Won the Game.\nSwitching to End Screen.");
                treasure.setWin(true);
                Starting.endScreen.setScore(25000); 
            } 
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
    
    public void startSong(){
        song.start();
    }
    public void stopSong(){
        song.stop();
        song.setFramePosition(0);
    }
    public void startTimer(){
        begin = System.currentTimeMillis();
        time.start();
    }
}
