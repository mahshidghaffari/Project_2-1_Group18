package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.*;

import java.awt.*;

/**
 * Panel holding the Dice, Stopwatch and ButtonPanel
 */
public class SidePanel {

    private JPanel sidePanel = new JPanel();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Game game;

    SidePanel(Game game) {
        this.game = game;
        initialize();
    }

    public void initialize() {
        sidePanel.setBackground(Color.GRAY);
        sidePanel.setPreferredSize(new Dimension(screenSize.height/2, screenSize.height));
        sidePanel.setBounds(0, 0, screenSize.height/2, screenSize.height);
        sidePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        sidePanel.setLayout(new GridLayout(3, 1));
        sidePanel.add(new ButtonPanel(game).getButtonPanel(), 0, 0);
        sidePanel.add(new Stopwatch().getStopWatch(), 1,0);
        sidePanel.add(new DicePanel(game).getDicePanel(), 2, 0);
        //sidePanel.add(n)
    }

    public JPanel getPane() { return sidePanel; }
}
