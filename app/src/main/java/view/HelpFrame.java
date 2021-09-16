package view;

import javax.swing.*;
import java.awt.*;

/**
 *  Frame that opens when helpButton is pressed
 */
public class HelpFrame extends JFrame {

    HelpFrame() {
        this.setTitle("Help");
        this.setBackground(Color.YELLOW);
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }
}
