package view;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to start the visualization
 */
public class Launch {

    public static void main(String[] args) {

        JFrame f = new JFrame("Dice Chess");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.height + screenSize.height/2, screenSize.height - 50);
        f.setLayout(new BorderLayout());
        f.setBackground(Color.GREEN);

        f.add(new SidePanel().getPane(), BorderLayout.WEST);
        f.add(new MainPanel().getMainPanel(), BorderLayout.CENTER);

        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
