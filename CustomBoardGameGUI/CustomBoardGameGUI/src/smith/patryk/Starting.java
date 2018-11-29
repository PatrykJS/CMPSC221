/*
 */
package smith.patryk;
 

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedInputStream; 
import java.io.IOException;
import java.io.InputStream;
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
    private final GameScreen panel;
    private InputStream audioInDirect;
    private AudioInputStream audioInBuffer;
    private Clip song;
    private Image background;
    private final String audioClip = "/resources/TitleScreen1.wav";
    private int x, y;
    /**
     * Creates new form StartingScreen
     * @param _panel
     */
    public Starting(GameScreen _panel)  {
        initComponents();
        panel = _panel;
        InputStream url = getClass().getResourceAsStream("/resources/TitleScreen.png");
        background = null;
        x = y = 0;
        try {
            background = ImageIO.read(url);
            //System.out.println(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/resources/TitleScreen1.wav")));
            audioInDirect = getClass().getResourceAsStream(audioClip);
            InputStream bufferedIn = new BufferedInputStream(audioInDirect);
            audioInBuffer = AudioSystem.getAudioInputStream(bufferedIn);
            song = AudioSystem.getClip();
            song.open(audioInBuffer);
            song.stop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
        }
        //song.loop(10);
        
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Forte", 3, 48)); // NOI18N
        jLabel3.setText("Treasure");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        add(jLabel3, gridBagConstraints);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(jButton1, gridBagConstraints);

        jLabel1.setText("Used WASD, SPACE, and RMB");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipady = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        add(jLabel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        panel.setVisible(true);
        stopSong();
        panel.startTimer();
        panel.startSong();
    }//GEN-LAST:event_jButton1ActionPerformed
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(background, 0, 0, this);
         
        repaint();
    }

    public void startSong(){
        song.start();
    }
    public void stopSong(){
        song.stop();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
