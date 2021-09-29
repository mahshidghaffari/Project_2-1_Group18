package view;

import controller.ChessBoard;
import controller.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel implements ActionListener {

    JButton helpButton = new JButton("HELP");
    JButton saveButton = new JButton("RESET");
    JButton promotionButton = new JButton("PROMOTE");
    JButton castlingButton = new JButton("CASTLING");
    JPanel buttonPanel = new JPanel();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Game game;

    ButtonPanel(Game g) {initialize(g);}

    public void initialize(Game g) {
        this.game=g;
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setLayout(null);

        helpButton.setBounds((screenSize.height/2)/3, 0, (screenSize.height/2)/3, 50);
        saveButton.setBounds((screenSize.height/2)/3, (screenSize.height/4)/4, (screenSize.height/2)/3, 50);
        castlingButton.setBounds((screenSize.height/2)/3,  (((screenSize.height/4)/4)* 2), (screenSize.height/2)/3, 50);
        promotionButton.setBounds((screenSize.height/2)/3,  (((screenSize.height/4)/4)*3),  (screenSize.height/2)/3, 50);

        helpButton.addActionListener(this);
        saveButton.addActionListener(this);
        castlingButton.addActionListener(this);
        promotionButton.addActionListener(this);

        castlingButton.setVisible(false);
        promotionButton.setVisible(false);

        buttonPanel.add(helpButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(castlingButton);
        buttonPanel.add(promotionButton);
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
            System.out.println(game.whosPlaying());
            game.getFrame().dispose();
            SetupMenu sm= new SetupMenu();
            sm.startGamebaord();

        } else if (e.getSource() == castlingButton){

        }
    }

    public JPanel getButtonPanel() { return buttonPanel; }
}
