package view;

import javax.swing.*;
import java.awt.Color;

public class SquareButton extends JButton {

    private ImageIcon pieceIcon = new ImageIcon();
    private Color buttonColor;

    SquareButton() { }

    public void setPieceIcon(ImageIcon image) {
        this.pieceIcon = image;
        this.setIcon(image);
    }

    public ImageIcon getPieceIcon() { return pieceIcon; }

    public void setButtonColor(Color color){
        buttonColor=color;
    }

    public Color getButtonColor(){
        return buttonColor;
    }
}
