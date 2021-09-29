package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
/**
 * TO BE IMPLEMENTED!
 * Used to handle the movements of the different pieces on the chessboard
 */
public class SquareButton extends JButton implements ActionListener {

    private ImageIcon pieceIcon = new ImageIcon();
    private Color buttonColor;

    SquareButton() { }

    public void setPiece(ImageIcon image) {this.pieceIcon = image; }

    public ImageIcon getPieceIcon() { return pieceIcon; }

    @Override
    /**
     * TO BE IMPLEMENTED!
     * Show/Remove a piece on this square if pressed
     */
    public void actionPerformed(ActionEvent e) {

    }

    public void setButtonColor(Color color){
        buttonColor=color;
    }

    public Color getButtonColor(){
        return buttonColor;
    }
}
