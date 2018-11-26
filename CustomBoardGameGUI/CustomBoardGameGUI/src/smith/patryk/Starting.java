/*
 */
package smith.patryk;
 
import javax.swing.JPanel;

/**
 *
 * @author Patryk Smith
 */
public class Starting extends javax.swing.JPanel {
    private final JPanel panel;
    
    /**
     * Creates new form StartingScreen
     * @param _panel
     */
    public Starting(JPanel _panel) {
        initComponents();
        panel = _panel;
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
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
