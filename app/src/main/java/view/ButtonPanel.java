package view;

import controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel implements ActionListener {

    JButton helpButton = new JButton("HELP");
    JButton saveButton = new JButton("SAVE");
    JButton promotionButton = new JButton("PROMOTE");
    JButton resetButton = new JButton("RESET");
    JPanel buttonPanel = new JPanel();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Game game;

    public ButtonPanel(Game game) { 
        this.game = game;
        initialize();
    }

    public void initialize() {
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setLayout(null);

        helpButton.setBounds((screenSize.height/2)/3, 0, (screenSize.height/2)/3, 50);
        saveButton.setBounds((screenSize.height/2)/3, (screenSize.height/4)/4, (screenSize.height/2)/3, 50);
        resetButton.setBounds((screenSize.height/2)/3,  (((screenSize.height/4)/4)* 2), (screenSize.height/2)/3, 50);
        promotionButton.setBounds((screenSize.height/2)/3,  (((screenSize.height/4)/4)*3),  (screenSize.height/2)/3, 50);

        helpButton.addActionListener(this);
        saveButton.addActionListener(this);
        resetButton.addActionListener(this);
        promotionButton.addActionListener(this);

        promotionButton.setVisible(true);

        buttonPanel.add(helpButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(promotionButton);

        game.setButtonPanel(this);
    }

    @Override
    /**
     * Frames are opened when the buttons are pressed
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == helpButton) {
            HelpFrame frame1 = new HelpFrame();
            frame1.setVisible(true);

        } if (e.getSource() == saveButton) {
            SaveFrame frame2 = new SaveFrame();
            frame2.setVisible(true);

        } 
    }



    public JPanel getButtonPanel() { return buttonPanel; }
}
