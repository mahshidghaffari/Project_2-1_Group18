package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DicePanel implements ActionListener {

    private ImageIcon image = new ImageIcon(getClass().getResource("/resources/GreenButton.png"));
    private JButton diceButton = new JButton();
    private JLabel resultLabel = new JLabel();
    private JPanel dicePanel = new JPanel();

    private Random rand = new Random();

    DicePanel() {initialize();}

    public void initialize() {
        dicePanel.setLayout(new GridLayout(1, 2));
        dicePanel.setBackground(Color.LIGHT_GRAY);

        diceButton.setIcon(image);
        diceButton.setBorderPainted(false);
        diceButton.addActionListener(this);

        resultLabel.setBorder(new EmptyBorder(2, 2, 2, 2));

        dicePanel.add(diceButton, 0, 0);
        dicePanel.add(resultLabel, 0,1);
    }

    public JPanel getDicePanel() { return dicePanel; }

    @Override
    /**
     * Dice
     * At the moment it only works for the white pieces
     */
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == diceButton) {
            int diceNumber = rand.nextInt(6);

            switch (diceNumber){

                case 0:
                    resultLabel.setIcon(new ImageIcon(getClass().getResource("/resources/wbishop.png")));
                    break;

                case 1:
                    resultLabel.setIcon(new ImageIcon(getClass().getResource("/resources/wking.png")));
                    break;

                case 2:
                    resultLabel.setIcon(new ImageIcon(getClass().getResource("/resources/wknight.png")));
                    break;

                case 3:
                    resultLabel.setIcon(new ImageIcon(getClass().getResource("/resources/wpawn.png")));
                    break;

                case 4:
                    resultLabel.setIcon(new ImageIcon(getClass().getResource("/resources/wqueen.png")));
                    break;

                case 5:
                    resultLabel.setIcon(new ImageIcon(getClass().getResource("/resources/wrook.png")));
                    break;
            }
        }
    }
}