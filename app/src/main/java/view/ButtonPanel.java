package view;

import controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel implements ActionListener {

    JButton helpButton = new JButton("HELP");
    JButton resetButton = new JButton("RESET");
    JLabel label = new JLabel();
    JPanel buttonPanel = new JPanel();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Game game;

    public ButtonPanel(Game g) { initialize(g); }

    public void initialize(Game g) {
        this.game=g;
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setLayout(null);

        helpButton.setBounds((screenSize.height/2)/3, 0, (screenSize.height/2)/3, 50);
        resetButton.setBounds((screenSize.height/2)/3, (screenSize.height/4)/4, (screenSize.height/2)/3, 50);
            
        label.setBounds((screenSize.height/2)/20,  (((screenSize.height/4)/4)* 2), (screenSize.height/2)/1, 50);

        helpButton.addActionListener(this);
        resetButton.addActionListener(this);

        buttonPanel.add(helpButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(label);
    }

    @Override
    /**
     * Frames are opened when the buttons are pressed
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == helpButton) {
            HelpFrame frame1 = new HelpFrame();
            frame1.getHelpFrame().setVisible(true);

        } if (e.getSource() == resetButton) {
            System.out.println(game.whosPlaying());
            game.getFrame().dispose();
            SetupMenu sm= new SetupMenu();
            sm.startGamebaord("PvP");

        }
    }


    public JPanel getButtonPanel() { return buttonPanel; }

    public void setText(String text){
        this.label.setText(text);
    }
}