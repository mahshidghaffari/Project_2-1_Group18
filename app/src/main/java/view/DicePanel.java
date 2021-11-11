package view;

import controller.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DicePanel implements ActionListener {

    private FilePath FilePath = new FilePath();
    private BufferedImage image = ImageLoader.loadImage(FilePath.getFilePath("GreenButton.png"));
    private JPanel finalPanel = new JPanel();
    private JButton diceButton = new JButton();
    private JLabel resultLabel = new JLabel();
    private JPanel dicePanel = new JPanel();
    private JLabel textLabel = new JLabel("WHITE PLAYER START");
    private JPanel textPanel = new JPanel();
    private Game game;
    private Random rand = new Random();
    private Dice dice;

    DicePanel(Game game) { 
        this.game = game;
        dice = new Dice();    
        initialize();
    }

    public void initialize() {
        dicePanel.setLayout(new GridLayout(1, 2));
        dicePanel.setBackground(Color.LIGHT_GRAY);
        textPanel.setBackground(Color.LIGHT_GRAY);
        finalPanel.setBackground(Color.LIGHT_GRAY);

        finalPanel.setLayout(new GridLayout(2, 1));

        diceButton.setIcon(new ImageIcon(image));
        diceButton.setBackground(Color.LIGHT_GRAY);
        diceButton.setBorderPainted(false);
        diceButton.addActionListener(this);

        resultLabel.setBorder(new EmptyBorder(2, 2, 2, 2));

        textLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 16));
        textLabel.setForeground(Color.RED);

        dicePanel.add(diceButton, 0, 0);
        dicePanel.add(resultLabel, 0,1);

        textPanel.add(textLabel);

        finalPanel.add(dicePanel);
        finalPanel.add(textPanel);
    }

    public JPanel getDicePanel() { return finalPanel; }

    @Override
    /**
     * Dice
     * At the moment it only works for the white pieces
     */

    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == diceButton) {
            game.updateBoard();
            if(!game.isNewTurn()) return; 
            else{

                // Set who's turn is it on the board
                if (game.getWhitePlayer().getIsMyTurn()) {
                    if (game.isNoMoves()) {
                        this.textLabel.setText("NO MOVES AVAILABLE, BLACK'S TURN!");
                    } else {
                        this.textLabel.setText("WHITE PLAYER'S TURN");
                    }

                } else {
                    if (game.isNoMoves()) {
                        this.textLabel.setText("NO MOVES AVAILABLE, WHITE'S TURN!");
                    } else {
                        this.textLabel.setText("BLACK PLAYER'S TURN");
                    }
                }
                System.out.println(game.isNoMoves());

                //game.newTurn();
                game.setDiceClicked(true);
                game.getDice().randomize();
                String name =  game.getDice().getRoleDice(); 
                switch (name){
                    case "Pawn":
                        if(game.getWhitePlayer().getIsMyTurn()){
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wpawn.png"))));
                            break;
                        }
                        else{
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bpawn.png"))));
                            break;
                        }

                    case "Rook":
                        if(game.getWhitePlayer().getIsMyTurn()){
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wrook.png"))));
                            break;
                        }
                    else{
                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("brook.png"))));
                        break;
                    }

                    case "Knight":
                        if(game.getWhitePlayer().getIsMyTurn()){
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wknight.png"))));
                            break;
                        }
                    else{
                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bknight.png"))));
                        break;
                    }

                    case "Bishop":
                        if(game.getWhitePlayer().getIsMyTurn()){
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wbishop.png"))));
                            break;
                        }
                        else{
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bbishop.png"))));
                            break;
                            }

                    case "Queen":
                    if(game.getWhitePlayer().getIsMyTurn()){
                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wqueen.png"))));
                        break;
                    }
                    else{
                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bqueen.png"))));
                        break;
                    }

                    case "King":
                        if(game.getWhitePlayer().getIsMyTurn()){
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wking.png"))));
                            break;
                        }
                        else{
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bking.png"))));
                            break;
                        }
                    }
                game.play();
            }
        }
    }
}