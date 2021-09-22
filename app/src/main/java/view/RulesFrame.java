package view;

import java.awt.*;

import javax.swing.JFrame;

/**
 *  TO BE IMPLEMENTED!
 *  Frame that opens when rulesButton is pressed
 */
public class RulesFrame extends JFrame {

    RulesFrame() {
        this.setTitle("Rules");
        this.setBackground(Color.BLUE);
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
