package zsk.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

public class PopupListener extends MouseAdapter{
	 JPopupMenu popup;
	 
     public PopupListener(JPopupMenu popupMenu) {
         this.popup = popupMenu;
     }

     public void mousePressed(MouseEvent e) {
         maybeShowPopup(e);
     }

     public void mouseReleased(MouseEvent e) {
         maybeShowPopup(e);
     }

     private void maybeShowPopup(MouseEvent e) {
         if (e.isPopupTrigger()) {
             this.popup.show(e.getComponent(), e.getX(), e.getY());
         }
     }
 }
	

