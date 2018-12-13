/*
 */
package smith.patryk;
 
import java.awt.event.KeyEvent; 
import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter; 
import java.io.Writer;  
import java.util.Arrays; 
import java.util.List; 
import javax.swing.KeyStroke;

/**
 *
 * @author Patryk Smith
 */
public class Settings extends javax.swing.JPanel{

    
    
    private int reference;
    /**
     * Creates new form Settings
     */
    public Settings() {
        initComponents();
        reference = 0;
        loadSettings();
    }

    private void loadSettings(){
        List<String> data = null;  
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/settings.txt")));
             
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(!"".equals(line)){
                    data.add(line);
                }
            }
            reader.close();
            System.out.println("Sucessfully Read Settings.");
        } catch (IOException ex) {
            // Report
        }  
        
        if(data != null){
            System.out.println("Settings Loaded Successfully.");
            Starting.keyMap[0] = Integer.getInteger(data.get(0));
            Starting.keyMap[1] = Integer.getInteger(data.get(1));
            Starting.keyMap[2] = Integer.getInteger(data.get(2));
            Starting.keyMap[3] = Integer.getInteger(data.get(3));
            Starting.keyMap[4] = Integer.getInteger(data.get(4));
        }
    }
    private void saveSettings(){
        jTextField1.setText(KeyEvent.getKeyText(Starting.keyMap[0]));
        jTextField2.setText(KeyEvent.getKeyText(Starting.keyMap[1]));
        jTextField3.setText(KeyEvent.getKeyText(Starting.keyMap[2]));
        jTextField4.setText(KeyEvent.getKeyText(Starting.keyMap[3]));
        jTextField5.setText(KeyEvent.getKeyText(Starting.keyMap[4]));
        
         
        List<String> data;
        data = Arrays.asList(  Integer.toString(Starting.keyMap[0]),
            Integer.toString(Starting.keyMap[1]),
            Integer.toString(Starting.keyMap[2]),
            Integer.toString(Starting.keyMap[3]),
            Integer.toString(Starting.keyMap[4]),
            Integer.toString(jSlider1.getValue()));
          
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(getClass().getResourceAsStream("/resources/settings.txt").toString()), "utf-8")); 
            for(String s: data){
                writer.write(s);
            }
            writer.close();
        } catch (IOException ex) {
            // Report
        }  
    }
    public void setReference(int i){
        reference = i;
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

        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton2.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        jButton3 = new javax.swing.JButton();
        jButton3.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        jButton4 = new javax.swing.JButton();
        jButton4.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        jButton5 = new javax.swing.JButton();
        jButton5.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        jButton6 = new javax.swing.JButton();
        jButton6.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        jButton7 = new javax.swing.JButton();
        jButton7.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        jTextField5.setText("jTextField5");

        setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(jButton1, gridBagConstraints);

        jSlider1.setMaximum(1);
        jSlider1.setMinimum(-30);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setValue(-10);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jSlider1, gridBagConstraints);
        jSlider1.getAccessibleContext().setAccessibleName("volumeSlider");

        jLabel1.setText("Music Level :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(jLabel1, gridBagConstraints);

        jLabel2.setText("Forward :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(jLabel2, gridBagConstraints);

        jTextField1.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jTextField1, gridBagConstraints);

        jLabel3.setText("Backward:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(jLabel3, gridBagConstraints);

        jLabel4.setText("Right :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(jLabel4, gridBagConstraints);

        jLabel5.setText("Left :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(jLabel5, gridBagConstraints);

        jLabel6.setText("Compass :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(jLabel6, gridBagConstraints);

        jLabel7.setText("Dig :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(jLabel7, gridBagConstraints);

        jTextField2.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jTextField2, gridBagConstraints);

        jTextField3.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jTextField3, gridBagConstraints);

        jTextField4.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jTextField4, gridBagConstraints);

        jTextField6.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jTextField6, gridBagConstraints);

        jTextField7.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jTextField7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jLabel8, gridBagConstraints);

        jButton2.setText("Reassign");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        add(jButton2, gridBagConstraints);

        jButton3.setText("Reassign");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        add(jButton3, gridBagConstraints);

        jButton4.setText("Reassign");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton4KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        add(jButton4, gridBagConstraints);

        jButton5.setText("Reassign");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jButton5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton5KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        add(jButton5, gridBagConstraints);

        jButton6.setText("Reassign");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jButton6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton6KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        add(jButton6, gridBagConstraints);

        jButton7.setText("Reassign");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jButton7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        add(jButton7, gridBagConstraints);

        jButton8.setText("Save");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(jButton8, gridBagConstraints);

        jButton9.setText("Main Menu");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        add(jButton9, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        switch (reference) {
            case 0:
                Starting.mainScreen.setVisible(true);
                break;
            case 1:
                Starting.tutScreen.setVisible(true);
                break;
            case 2:
                Starting.gameWindow.setVisible(true);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        // TODO add your handling code here:
        Starting.setSoundVolume(jSlider1.getValue());
        Starting.tutScreen.updateVolume();
        Starting.gameWindow.updateVolume();
        Starting.endScreen.updateVolume();
        jLabel8.setText(jSlider1.getValue() + "");
    }//GEN-LAST:event_jSlider1StateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jButton2.setText("Listening..."); 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jButton3.setText("Listening...");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jButton4.setText("Listening...");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jButton5.setText("Listening...");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jButton6.setText("Listening...");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jButton7.setText("Listening..."); 
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyPressed
        Starting.keyMap[1] = evt.getKeyCode();
        jButton3.setText("Reassign"); 
        saveSettings();
    }//GEN-LAST:event_jButton3KeyPressed

    private void jButton4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton4KeyPressed
        Starting.keyMap[2] = evt.getKeyCode();
        jButton4.setText("Reassign"); 
        saveSettings();
    }//GEN-LAST:event_jButton4KeyPressed

    private void jButton5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton5KeyPressed
        Starting.keyMap[3] = evt.getKeyCode();
        jButton5.setText("Reassign"); 
        saveSettings();
    }//GEN-LAST:event_jButton5KeyPressed

    private void jButton6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton6KeyPressed
        Starting.keyMap[4] = evt.getKeyCode();
        jButton6.setText("Reassign"); 
        saveSettings();
    }//GEN-LAST:event_jButton6KeyPressed

    private void jButton7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton7KeyPressed
        Starting.keyMap[5] = evt.getKeyCode();
        jButton7.setText("Reassign"); 
        saveSettings();
    }//GEN-LAST:event_jButton7KeyPressed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        Starting.keyMap[0] = evt.getKeyCode(); 
        jButton2.setText("Reassign"); 
        saveSettings();
    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        saveSettings();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        this.setVisible(false);
        Starting.gameWindow.stopSong();
        Starting.mainScreen.startSong();
        Starting.mainScreen.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables

    
}
