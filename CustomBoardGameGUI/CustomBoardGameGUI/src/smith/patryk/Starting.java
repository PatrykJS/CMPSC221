/*
 */
package smith.patryk;
 

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image; 
import java.io.BufferedInputStream; 
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException; 

/**
 *
 * @author Patryk Smith
 */
public class Starting extends javax.swing.JPanel { 
    private InputStream audioInDirect;
    private AudioInputStream audioInBuffer;
    private Clip song;
    private Image background;
    private final String audioClip = "/resources/TitleScreen1.wav";
    private int x, y;
    private Dimension screenSize;
    
    public static GameScreen gameWindow = null;
    public static EndScreen endScreen = null;
    public static TutorialScreen tutScreen = null;
    
     
    /**
     * Creates new form StartingScreen
     * @param _screenSize
     */
    public Starting(Dimension _screenSize)  { 
        screenSize = _screenSize;
        System.out.println("Initializing Components...");
        initComponents(); 
        System.out.println("Getting Resources...");
        InputStream url = getClass().getResourceAsStream("/resources/TitleScreen.png");
        background = null;
        x = y = 0;
        try {
            System.out.println("Configuring Audio...");
            background = ImageIO.read(url); 
            audioInDirect = getClass().getResourceAsStream(audioClip);
            InputStream bufferedIn = new BufferedInputStream(audioInDirect);
            audioInBuffer = AudioSystem.getAudioInputStream(bufferedIn);
            song = AudioSystem.getClip();
            song.open(audioInBuffer);
            song.stop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        endScreen = new EndScreen(this, 0);
        endScreen.setSize(screenSize);
        
        System.out.println("Creating Game Window...");
        try {
            gameWindow = new GameScreen(endScreen, screenSize); 
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(gameWindow != null){
            gameWindow.setSize(screenSize);
            gameWindow.stopSong();
            gameWindow.setVisible(false);
        } 
        endScreen.setVisible(false);
        if(tutScreen != null){
            tutScreen.setVisible(false);
        }
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Forte", 3, 48)); // NOI18N
        jLabel3.setText("Treasure");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        add(jLabel3, gridBagConstraints);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(jButton1, gridBagConstraints);

        jLabel1.setText("Used WASD, SPACE, and RMB");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jLabel1, gridBagConstraints);

        jButton2.setText("Tutorial");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(jButton2, gridBagConstraints);

        jButton3.setText("Settings");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        add(jButton3, gridBagConstraints);

        jButton4.setText("Quit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        add(jButton4, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.out.println("Staring Game...");
        System.out.println("Switching Windows...");
        this.setVisible(false);
        gameWindow.setVisible(true);
        stopSong();
        gameWindow.startTimer();
        gameWindow.startSong();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.out.println("Creaing Tutorial...");
        
        try {  
            tutScreen = new TutorialScreen(this, new Dimension(this.getWidth(), this.getHeight()));
        } catch (IOException ex) {
            Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
        } 
        if(tutScreen != null){ 
            System.out.println("Switching Windows...");
            this.setVisible(false); 
            tutScreen.setVisible(true); 
        }else{
            Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, "Internal Error. (0x41899");
        } 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        System.out.println("Closing Window...");
        System.out.println("Exiting Application...");
        exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed
    
    @Override
    public void paintComponents(Graphics g){
        g.drawImage(background, 0, 0, this); 
        repaint();
    }

    public void startSong(){
        System.out.println("Starting song...");
        song.start();
    }
    public void stopSong(){
        System.out.println("Stopping song...");
        song.setFramePosition(0);
        song.stop();
    }
    
    public final void restart(){
        System.out.println("Restarting Game ...");
        stopSong();
        endScreen.stopSong();
        
        double startResetTime = System.currentTimeMillis();
        System.out.println("Creating New Game...");
        gameWindow.restart();  
        if(gameWindow != null){
            System.out.println("Successfully Created New Game.");
            gameWindow.setSize(screenSize);
            gameWindow.stopSong();
            gameWindow.setVisible(false);
        } 
        endScreen.setVisible(false);
        if(tutScreen != null){
            tutScreen.setVisible(false);
        }
        double endResetTime = System.currentTimeMillis();
        System.out.println("Done Resetting Game. Took " + ((endResetTime-startResetTime)/1000) + "s");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
