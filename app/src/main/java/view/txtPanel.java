package view;

import controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class txtPanel implements ActionListener {

    JPanel txtPanel = new JPanel();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Game game;

    public txtPanel(Game game) { 
        this.game = game;
        initialize();
    }

    public void initialize() {
        txtPanel.setLayout(new GridLayout(1, 2));
        txtPanel.setBackground(Color.LIGHT_GRAY);
        txtPanel.setLayout(null);
        
        JLabel txtLabel = new JLabel("SIP");
        // txtPanel.add(txtLabel);

        // helpButton.setBounds((screenSize.height/2)/3, 0, (screenSize.height/2)/3, 50);
        // saveButton.setBounds((screenSize.height/2)/3, (screenSize.height/4)/4, (screenSize.height/2)/3, 50);
        // castlingButton.setBounds((screenSize.height/2)/3,  (((screenSize.height/4)/4)* 2), (screenSize.height/2)/3, 50);
        // promotionButton.setBounds((screenSize.height/2)/3,  (((screenSize.height/4)/4)*3),  (screenSize.height/2)/3, 50);

        // helpButton.addActionListener(this);
        // saveButton.addActionListener(this);
        // castlingButton.addActionListener(this);
        // promotionButton.addActionListener(this);

        // promotionButton.setVisible(true);
        // castlingButton.setVisible(false);

        // buttonPanel.add(helpButton);
        // buttonPanel.add(saveButton);
        // buttonPanel.add(castlingButton);
        // buttonPanel.add(promotionButton);

        // game.setButtonPanel(this);
    }

    @Override
    /**
     * Frames are opened when the buttons are pressed
     */
    public void actionPerformed(ActionEvent e) {

        // // if (e.getSource() == helpButton) {
        // //     HelpFrame frame1 = new HelpFrame();
        // //     frame1.setVisible(true);

        // // } if (e.getSource() == saveButton) {
        // //     SaveFrame frame2 = new SaveFrame();
        // //     frame2.setVisible(true);

        // // } else if (e.getSource() == castlingButton){
        // //     //game.getCastling()
        // }
    }

    public JPanel gettxtPanel() { return txtPanel; }
}
