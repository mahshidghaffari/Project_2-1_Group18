package view;

import javax.swing.*;
import java.awt.*;

/**
 *  Frame that opens when helpButton is pressed
 */
public class HelpFrame {

    JFrame helpFrame = new JFrame();
    String text = "\n"
                + " This game is a variant of classic Chess, " + "\n"
                + " which adds a random factor to the strategy. " + "\n"
                + " The following list describes all differences of the rules: " + "\n"
                + " There is no check or checkmate, " + "\n"
                + " it is allowed to move the king to a square attacked by opponent's piece. " + "\n"
                + " The goal is to capture opponent's king. " + "\n"
                + " A die is rolled for every move. " + "\n"
                + " The die is displayed below the game board and the number " + "\n"
                + " determines which piece can be used to make the move. " + "\n" + "\n"
                + " 1 - pawn, 2 - knight, 3 - bishop, 4 - rook, 5 - queen, 6 - king. " + "\n"+ "\n"
                + " BrainKing automatically detects which pieces can be moved " + "\n"
                + " at the actual situation and does not roll a number of an immobile one. " + "\n"
                + " If a pawn is to be promoted (would advance to the last row), " + "\n"
                + " the player can move it even if the die does not show 1. " + "\n"
                + " However, he can only promote it to the piece chosen by the die roll."  + "\n"
                + " For example, if 3 is rolled, the pawn can be promoted to a bishop only. " + "\n"
                + " If 1 is rolled, the pawn can be promoted to any piece.";

    JTextArea rules = new JTextArea(text);

    HelpFrame() {
        this.helpFrame.setTitle("Help");
        this.helpFrame.setBackground(Color.YELLOW);
        this.helpFrame.setSize(new Dimension(500, 500));

        rules.setEditable(false);
        this.helpFrame.add(rules);

        this.helpFrame.setLocationRelativeTo(null);
        this.helpFrame.setVisible(false);
    }

    public JFrame getHelpFrame() { return this.helpFrame; }
}
