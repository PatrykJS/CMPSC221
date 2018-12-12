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

public final class TutorialScreen extends JPanel implements KeyListener, MouseListener {

    double SleepTime = 1000 / 30, lastRefresh = 0;
    private final Treasure treasure;
    private Starting mainScreen; 
    private BufferedImage[] sprites;
    private BufferedImage background;
    private Dimension screenSize;
    private static FloatControl gainControl;
    Vector2D compassPosition;
    Vector2D direction;
    Vector2D treasureDirection;
    
    private Compass compass; 
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
     * @param _mainScreen
     * @param d
     * @throws java.io.IOException
     */

    public TutorialScreen(Starting _mainScreen, Dimension d) throws IOException {
        super();
        addKeyListener(this);
        addMouseListener(this);
        
        InputStream url = getClass().getResourceAsStream("/resources/Textures.png");
        try {
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
        
        mainScreen = _mainScreen;
        screenSize = d;
        init();
        timeCount = 0;
    }

    public void init() { 
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
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        background = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        
        background = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
        Graphics g_res = background.getGraphics();

        for (int i = 0; i < screenSize.width; i++) {
            for (int j = 0; j < screenSize.height; j++) {
                g_res.drawImage(sprites[0], i * width, j * height, this);
            }
        }

        InputStream url = getClass().getResourceAsStream("/resources/background.png");

        try {
            ImageIO.write((RenderedImage) background, "png", new File(url.toString()));
        } catch (IOException ex) {
            Logger.getLogger(TutorialScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            background = ImageIO.read(new File(url.toString()));
        } catch (IOException ex) {
            Logger.getLogger(TutorialScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        treasureDirection = new Vector2D(treasure.getTreasurePosition().getIntX() - treasure.getPlayerPosition().getX(),
        treasure.getTreasurePosition().getY() - treasure.getPlayerPosition().getY());

        compass = new Compass(treasure.getTreasureChest(), treasure.getPlayer());
                
        t = new Timer(compassUseTime,  (ActionEvent evt) -> { });
        t.setRepeats(false);
        
         
        time = new Timer(1000, (ActionEvent evt) -> {
            timeCount++; 
        });
        time.setRepeats(true); 
    }

    @Override
    public void paintComponent(Graphics g) {    
        requestFocus();
        g.drawImage(background, 0, 0, this);
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
            this.setVisible(false); 
        } 
        
        drawInstructions(g, 0, 100);
        repaint();
    }
    
    private void drawPlayer(Graphics g, int _x, int _y) {
        g.drawImage(sprites[30], _x, _y, null);
        g.drawImage(sprites[31], _x, _y + sprites[2].getHeight(), null);
    }

    private void drawTreasureChest(Graphics g) {
        g.drawImage(sprites[1], treasure.getTreasurePosition().getIntX() * 72, treasure.getTreasurePosition().getIntY() * 72, mainScreen);
        repaint();
    }
    private void drawInstructions (Graphics g, int _x, int _y) {
       g.setColor(Color.darkGray);
       int size = 30;
       g.setFont(new Font("Forte", 3, size));
       g.drawString("To move around just use the "+KeyEvent.getKeyText(Starting.keyMap[0])
                                                    +KeyEvent.getKeyText(Starting.keyMap[1])
                                                    +KeyEvent.getKeyText(Starting.keyMap[2])
                                                    +KeyEvent.getKeyText(Starting.keyMap[3])
                                                    +" keys.", _x, _y+size);
       g.drawString("To dig for the treasure use the Right Mouse Button.",_x , _y+size*2);
       g.drawString("To use the compass use the SPACEBAR.", _x, _y+size*3);
       g.drawString("You have only 3 uses of the compass.", _x, _y+size*4);
       g.drawString("You will know when you find the treasure when the red line on the compass dissapears.", _x, _y+size*5);
    }

    public void restart(){
        treasure.reset();
        compass = new Compass(treasure.getTreasureChest(), treasure.getPlayer());
         
    }
    
    @Override
    public void keyTyped(KeyEvent e) {    }
    public void updateVolume(){
        gainControl.setValue(Starting.soundVolume);
    } 
    
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
            if(compass.canUse() && !t.isRunning()){
                compass.use();
                compass.setShow(true);
                t.start();  
            }else{ 
                compass.setShow(false);
                repaint();
            }
        }else if(e.getKeyCode() == Starting.keyMap[5]){
            if(treasure.getPlayer().dig(treasure)){
                Starting.endScreen.setVisible(true);
                this.setVisible(false);
                stopSong(); 
                Starting.endScreen.startSong();
            } 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) {
        if( e.getButton() == 1){ 
            if(treasure.getPlayer().dig(treasure)){
                mainScreen.setVisible(true);
                this.setVisible(false);
                stopSong(); 
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
    }
    public void startTimer(){
        time.start();
    }
}
