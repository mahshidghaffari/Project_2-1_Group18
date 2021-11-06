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
//    private BufferedImage image = ImageLoader.loadImage("app/src/main/java/view/resources/GreenButton.png");
//	private BufferedImage image= new BufferedImage(FilePath.getFilePath("name.png"));
    private JButton diceButton = new JButton();
    private JLabel resultLabel = new JLabel();
    private JPanel dicePanel = new JPanel();
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
        diceButton.setIcon(new ImageIcon(image));
        diceButton.setBackground(Color.LIGHT_GRAY);
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
            game.updateBoard();
            if(!game.isNewTurn()) return; 
            else{
                //game.newTurn();
                game.setDiceClicked(true);
                game.getDice().randomize();
                String name =  game.getDice().getRoleDice(); 
                switch (name){
                    case "Pawn":
                        if(game.getWhitePlayer().getIsMyTurn()){
//                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wpawn.png")));
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wpawn.png"))));
                            break;
                        }
                        else{
//                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bpawn.png")));
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bpawn.png"))));
                            break;
                        }

                    case "Rook":
                        if(game.getWhitePlayer().getIsMyTurn()){
//                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wrook.png")));
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wrook.png"))));
                            break;
                        }
                    else{
//                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/brook.png")));
                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("brook.png"))));
                        break;
                    }

                    case "Knight":
                        if(game.getWhitePlayer().getIsMyTurn()){
//                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wknight.png")));
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wknight.png"))));
                            break;
                        }
                    else{
//                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bknight.png")));
                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bknight.png"))));
                        break;
                    }

                    case "Bishop":
                        if(game.getWhitePlayer().getIsMyTurn()){
//                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wbishop.png")));
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wbishop.png"))));
                            break;
                        }
                        else{
//                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bbishop.png")));
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bbishop.png"))));
                            break;
                            }

                    case "Queen":
                    if(game.getWhitePlayer().getIsMyTurn()){
//                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wqueen.png")));
                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wqueen.png"))));
                        break;
                    }
                    else{
//                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bqueen.png")));
                        resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bqueen.png"))));
                        break;
                    }

                    case "King":
                        if(game.getWhitePlayer().getIsMyTurn()){
//                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wking.png")));
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wking.png"))));
                            break;
                        }
                        else{
//                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bking.png")));
                            resultLabel.setIcon(new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bking.png"))));
                            break;
                        }
                    }
                game.play();
            }
        }
    }
}