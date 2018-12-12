/*
 */
package smith.patryk;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
public class EndScreen extends javax.swing.JPanel {
    private int score;
    private BufferedImage background;
    private InputStream audioInDirect;
    private AudioInputStream audioInBuffer;
    private Clip song;
    private final String audioClip = "/resources/WinSong.wav";
    private Starting mainMenu;
    
    
    /**
     * Creates new form EndScreen
     * @param _mainMenu
     * @param _score
     */
    public EndScreen(Starting _mainMenu, int _score){
        super();
        mainMenu = _mainMenu;
        initComponents();
        score = _score;
        try {
            //System.out.println(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/resources/TitleScreen1.wav")));
            InputStream url = getClass().getResourceAsStream("/resources/background.png");
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
        //song.loop(1);
        
    }
   
    public void setScore(int _score){ 
        score = _score;
    }
    
    @Override
    public void paintComponents(Graphics g){ 
        g.drawImage(background, 0, 0, null);
        g.setFont(new Font("Forte", Font.ITALIC, 18));
        g.drawString(score+"", background.getHeight(), background.getWidth());
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Forte", 3, 48)); // NOI18N
        jLabel2.setText("You Win!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        add(jLabel2, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Forte", 3, 18)); // NOI18N
        jLabel1.setText("Score:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        add(jLabel4, gridBagConstraints);

        jButton1.setText("Main Menu");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        add(jButton1, gridBagConstraints);

        jButton2.setText("Quit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        add(jButton2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        System.out.println("Exiting Game...");
        exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        mainMenu.setVisible(true);
        this.setVisible(false);
        this.score = 0;
        stopSong();
        mainMenu.startSong();
        mainMenu.restart();
    }//GEN-LAST:event_jButton1ActionPerformed

    public void startSong(){
        song.start();
    }
    public void stopSong(){
        song.stop();
        song.setFramePosition(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
