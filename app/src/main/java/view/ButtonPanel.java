package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel implements ActionListener {

    JButton helpButton = new JButton("HELP");
    JButton saveButton = new JButton("SAVE");
    JButton rulesButton = new JButton("RULES");
    JPanel buttonPanel = new JPanel();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    ButtonPanel() {initialize();}

    public void initialize() {
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setLayout(null);

        helpButton.setBounds((screenSize.height/2)/3, 0, (screenSize.height/2)/3, 70);
        saveButton.setBounds((screenSize.height/2)/3, (screenSize.height/3)/3, (screenSize.height/2)/3, 70);
        rulesButton.setBounds((screenSize.height/2)/3, ((screenSize.height/3)/3)*2, (screenSize.height/2)/3, 70);

        helpButton.addActionListener(this);
        saveButton.addActionListener(this);
        rulesButton.addActionListener(this);

        buttonPanel.add(helpButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(rulesButton);
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

        } else if (e.getSource() == rulesButton){
            RulesFrame frame3 = new RulesFrame();
            frame3.setVisible(true);

        }
    }

    public JPanel getButtonPanel() { return buttonPanel; }
}
