package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main panel holding the chessboard where the game is played
 */
public class MainPanel implements ActionListener {

    private JPanel mainPanel = new JPanel();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Square[][] squares = new Square[8][8];
    private ImageIcon[] pieces = new ImageIcon[12];

    MainPanel() {initialize();}

    public void initialize() {
        mainPanel.setLayout(new GridLayout(8, 8));
        mainPanel.setPreferredSize(new Dimension(screenSize.height, screenSize.height));
        mainPanel.setBounds(screenSize.height/2, 0, screenSize.height, screenSize.height);

        // Color the squares/buttons black or white
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {

                Square button = new Square();
                if ((i + j) % 2 == 0) {
                    button.setBackground(Color.DARK_GRAY);

                } else {
                    button.setBackground(Color.WHITE);
                }

                button.setOpaque(true);
                button.setBorderPainted(false);
                button.addActionListener(this);

                squares[i][j] = button;
                mainPanel.add(button, i, j);
            }
        }
        initialPositions();
    }

    /**
     * Initialize chessboard with the initial positions of the pieces
     * White at the bottom and Black on top
     */
    public void initialPositions() {

        ImageIcon bBishop = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bbishop.png"));
        ImageIcon bKing = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bking.png"));
        ImageIcon bKnight = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bknight.png"));
        ImageIcon bPawn = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bpawn.png"));
        ImageIcon bRook = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/brook.png"));
        ImageIcon bQueen = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bqueen.png"));
        ImageIcon wBishop = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wbishop.png"));
        ImageIcon wKing = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wking.png"));
        ImageIcon wKnight = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wknight.png"));
        ImageIcon wPawn = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wpawn.png"));
        ImageIcon wQueen = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wqueen.png"));
        ImageIcon wRook = new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wrook.png"));

        // Initialize Pawns
        for (int i = 0; i < squares.length; i++) {
            squares[1][i].setIcon(wPawn);
            squares[6][i].setIcon(bPawn);
        }

        squares[0][0].setIcon(wRook);
        squares[0][1].setIcon(wKnight);
        squares[0][2].setIcon(wBishop);
        squares[0][3].setIcon(wQueen);
        squares[0][4].setIcon(wKing);
        squares[0][5].setIcon(wBishop);
        squares[0][6].setIcon(wKnight);
        squares[0][7].setIcon(wRook);
        squares[7][0].setIcon(bRook);
        squares[7][1].setIcon(bKnight);
        squares[7][2].setIcon(bBishop);
        squares[7][3].setIcon(bQueen);
        squares[7][4].setIcon(bKing);
        squares[7][5].setIcon(bBishop);
        squares[7][6].setIcon(bKnight);
        squares[7][7].setIcon(bRook);
    }

    @Override
    /**
     * Highlights the pressed square
     */
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if (e.getSource() == squares[i][j]) {
                    squares[i][j].setBackground(Color.GREEN);
                    break;
                }
            }
        }

    }

    public JPanel getMainPanel() { return mainPanel; }
}
