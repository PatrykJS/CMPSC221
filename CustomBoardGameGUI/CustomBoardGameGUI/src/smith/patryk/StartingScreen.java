
package smith.patryk;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author Patryk Smith
 */
public final class StartingScreen extends javax.swing.JPanel {
    private JPanel panel;
    private GridBagConstraints c;
    /**
     * Creates new form StartingScreen
     * @param _panel
     */
    
    public StartingScreen(JPanel _panel) {
        super(new GridBagLayout());
        initComponents();
        panel = _panel;
        c = new GridBagConstraints();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        this.setVisible(false);
        panel.setVisible(true);
        
    }     
    public void initComponents(){
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        this.add(jButton1, c);
        this.add(jLabel1, c);
        this.add(jLabel2, c);
       
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton1ActionPerformed(evt);
        });
        
        jButton1.setText("Start");
        jLabel1.setText("Treasure");
        jLabel2.setText("Use the WASD Keys to move around! ");
        
    }
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
}

   