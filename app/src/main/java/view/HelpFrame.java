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
                + " If 1 is rolled, the pawn can be promoted to any piece."
                + " \n"
                + " To Play: \n"
                + "P1, Press the Green Dice Button to generate a a dice roll. The piece shown on the Screeen will indicate \n"
                + "which of your Pieces you will be moving this turn.\n"
                + "If this this piece is movable, click the piece and then click the destination square. \n "
                + "If you need a hand remembering where each piece can move to, just hover with your mouse over the desired piece on your turn. \n"
                + "If the piece is not movable or not in the game, the turn goes to the next player and the next role will be the opposite color then the last \n "
                + "Change your mind?\n"
                + "If the dice rolled a Knight and youv'e clicked on one of your knights but then decided to move the other one, simply click again on the \n "
                + " On the held knight to release it and then click the desired piece \n "
                + "ENJOY!!! ";

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
