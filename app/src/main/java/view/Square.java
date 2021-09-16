package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TO BE IMPLEMENTED!
 * Used to handle the movements of the different pieces on the chessboard
 */
public class Square extends JButton implements ActionListener {

    private ImageIcon piece = new ImageIcon();
    private boolean pressed = false;

    Square() { }

    public void setPiece(ImageIcon image) {this.piece = image; }

    public ImageIcon getPiece() { return piece; }

    @Override
    /**
     * TO BE IMPLEMENTED!
     * Show/Remove a piece on this square if pressed
     */
    public void actionPerformed(ActionEvent e) {

    }
}
