/*
 */
package smith.patryk;
 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image; 
import java.awt.Toolkit;
import java.io.BufferedInputStream; 
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException; 

/**
 *
 * @author Patryk Smith
 */
public class Starting extends javax.swing.JPanel{ 
    private InputStream audioInDirect;
    private AudioInputStream audioInBuffer;
    private Clip song;
    private Image background;
    private final String audioClip = "/resources/TitleScreen1.wav";
    private int x, y;
    private Dimension screenSize;
    public static Settings settings;
    
    private boolean mouse1Entered, mouse2Entered, mouse3Entered, mouse4Entered;
    int buttonL = 220, buttonH = 100;
    int centerX;
    int centerY;
    private int[] buttonY;
    private int buttonX;
    private String[] buttonText; 
    public static GameScreen gameWindow = null;
    public static EndScreen endScreen = null;
    public static TutorialScreen tutScreen = null;
    public static Starting mainScreen = null;
     
    /**
     * Creates new form StartingScreen
     * @param _screenSize
     */
    public Starting(Dimension _screenSize)  { 
        mouse1Entered = mouse2Entered = mouse3Entered = mouse4Entered = false;
        screenSize = _screenSize;
        mainScreen = this;
        System.out.println("Initializing Components...");
        initComponents(); 
        System.out.println("Getting Resources..."); 
        background = null;
        x = y = 0;
        try {
            System.out.println("Configuring Audio...");
            background =   Toolkit.getDefaultToolkit().createImage(getClass().getResource ("/resources/animatedBackground.gif"));
            audioInDirect = getClass().getResourceAsStream(audioClip);
            InputStream bufferedIn = new BufferedInputStream(audioInDirect);
            audioInBuffer = AudioSystem.getAudioInputStream(bufferedIn);
            song = AudioSystem.getClip();
            song.open(audioInBuffer);
            song.loop(100);
            song.stop();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        endScreen = new EndScreen(this, 0);
        endScreen.setSize(screenSize);
        
        settings = new Settings();
        settings.setSize(screenSize);
        settings.setVisible(false);
        
        buttonL = 220;
        buttonH = 100;
        
        System.out.println("Creating Game Window...");
        
        try {
            gameWindow = new GameScreen(endScreen, screenSize); 
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Creating Tutorial...");
        try {
            tutScreen = new TutorialScreen(this, screenSize);
        } catch (IOException ex) {
            Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(gameWindow != null){
            gameWindow.setSize(screenSize);
            gameWindow.stopSong();
            gameWindow.setVisible(false);
        } 
        endScreen.setVisible(false); 
        if( tutScreen != null){
            tutScreen.setVisible(false);
        }
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel3 = new javax.swing.JLabel();
        this.repaint();
        jButton1 = new javax.swing.JButton();
        jButton1.setBorder(null);         
        jButton1.setBorderPainted(false);         
        jButton1.setContentAreaFilled(false);        
        jButton1.setOpaque(false);
        jButton2 = new javax.swing.JButton();
        jButton2.setBorder(null);         
        jButton2.setBorderPainted(false);         
        jButton2.setContentAreaFilled(false);        
        jButton2.setOpaque(false);
        jButton3 = new javax.swing.JButton();
        jButton3.setBorder(null);         
        jButton3.setBorderPainted(false);         
        jButton3.setContentAreaFilled(false);        
        jButton3.setOpaque(false);
        jButton4 = new javax.swing.JButton();
        jButton4.setBorder(null);         
        jButton4.setBorderPainted(false);         
        jButton4.setContentAreaFilled(false);        
        jButton4.setOpaque(false);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Forte", 3, 48)); // NOI18N
        jLabel3.setText("Treasure");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        add(jLabel3, gridBagConstraints);

        jButton1.setFont(new java.awt.Font("Forte", 3, 18)); // NOI18N
        jButton1.setText("Start");
        jButton1.setOpaque(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 4;
        add(jButton1, gridBagConstraints);

        jButton2.setFont(new java.awt.Font("Forte", 3, 18)); // NOI18N
        jButton2.setText("Tutorial");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton2MouseExited(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 4;
        add(jButton2, gridBagConstraints);

        jButton3.setFont(new java.awt.Font("Forte", 3, 18)); // NOI18N
        jButton3.setText("Settings");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton3MouseExited(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 4;
        add(jButton3, gridBagConstraints);

        jButton4.setFont(new java.awt.Font("Forte", 3, 18)); // NOI18N
        jButton4.setText("Quit");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4MouseExited(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 4;
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
        tutScreen.restart();
        if(tutScreen != null){ 
            System.out.println("Switching Windows...");
            this.setVisible(false); 
            tutScreen.setVisible(true);
            stopSong();
            tutScreen.startSong();
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.out.println("User is changing settings...");
        settings.setVisible(true); 
        this.setVisible(false); 
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        if(mouse1Entered){
            System.out.println("Button 1 Clicked!");
        }
    }//GEN-LAST:event_formMouseClicked

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        jButton1.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        jButton1.setForeground(new Color(60,63,65));
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
        jButton2.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseExited
        jButton2.setForeground(new Color(60,63,65));
    }//GEN-LAST:event_jButton2MouseExited

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        jButton3.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton3MouseEntered

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseExited
        jButton3.setForeground(new Color(60,63,65));
    }//GEN-LAST:event_jButton3MouseExited

    private void jButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseExited
        jButton4.setForeground(new Color(60,63,65));
    }//GEN-LAST:event_jButton4MouseExited

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseEntered
        jButton4.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton4MouseEntered
    
      
    @Override
    public void paintComponent(Graphics g){ 
        g.drawImage(background, 0, 0, screenSize.width, screenSize.height, this);
         
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
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
