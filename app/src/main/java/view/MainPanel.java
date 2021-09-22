package view;

import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Main panel holding the chessboard where the game is played
 */
public class MainPanel implements ActionListener  {

    private JPanel mainPanel = new JPanel();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ChessBoard cb;
    private Game game;
    private boolean clickedOnce =false;

    MainPanel(Game game) {
        this.game = game;
        initialize();

    }

    public void initialize() {
        //Game game = new Game();
        mainPanel.setLayout(new GridLayout(8, 8));
        mainPanel.setPreferredSize(new Dimension(screenSize.height, screenSize.height));
        mainPanel.setBounds(screenSize.height/2, 0, screenSize.height, screenSize.height);
        cb = game.getChessBoard();
        
        // Color the squares/buttons black or white
        for (int i = 0; i < 8 ; i++) {
            
            for (int j = 0; j < 8 ; j++) {

                SquareButton button = new SquareButton();
                if ((i + j) % 2 == 0) {
                    button.setButtonColor(Color.DARK_GRAY);
                    button.setBackground(button.getButtonColor());


                } else {
                    button.setButtonColor(Color.WHITE);
                    button.setBackground(button.getButtonColor());
                }

                button.setOpaque(true);
                button.setBorderPainted(false);
                button.addActionListener(this);
                
                //when mouse hovers then show legal moves for each move
                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        Square square = cb.getSquare(button);
                        Piece piece = square.getPieceOnSq();
                        if(piece!=null && piece.getLegalMoves(cb).size()>0){
                            for(Square sq : piece.getLegalMoves(cb)){
                                if(piece.isWhite())         { sq.getButtonOnSquare().setBackground(Color.GREEN);}//new Color(0f,1f,0f,.3f))
                                else if(!piece.isWhite())   { sq.getButtonOnSquare().setBackground(Color.BLUE); }

                            }
                        }
                    }
                    //when the mouse leaves the button, repaint everything back to the original black and white
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        if(button.getButtonColor().equals(Color.DARK_GRAY)) {
                            button.setBackground(Color.DARK_GRAY);
                        }
                        else if(button.getButtonColor().equals(Color.WHITE)) {
                            button.setBackground(Color.WHITE);
                        } 
                            Square square = cb.getSquare(button);
                            Piece piece = square.getPieceOnSq();
                            //color the legal moves back
                            if(piece!=null){
                                for(Square sq : piece.getLegalMoves(cb)){
                                    if(sq.getButtonOnSquare().getButtonColor().equals(Color.DARK_GRAY)){
                                        sq.getButtonOnSquare().setBackground(Color.DARK_GRAY);
                                    }
                                    else if (sq.getButtonOnSquare().getButtonColor().equals(Color.WHITE)){
                                        sq.getButtonOnSquare().setBackground(Color.WHITE);
                                    }
                                }
                            }
                        }
                    });
            

                cb.getBoard()[i][j].setButtonOnSquare(button);
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
        Square[][] board = cb.getBoard();

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

        for(Piece piece: cb.getLivePieces()){
            if(piece.isWhite()){
                if(piece.getPieceName().equals("King"))     piece.setImgIcon(wKing);
                if(piece.getPieceName().equals("Queen"))    piece.setImgIcon(wQueen);
                if(piece.getPieceName().equals("Bishop"))   piece.setImgIcon(wBishop);
                if(piece.getPieceName().equals("Pawn"))     piece.setImgIcon(wPawn);
                if(piece.getPieceName().equals("Rook"))     piece.setImgIcon(wRook);
                if(piece.getPieceName().equals("Knight"))   piece.setImgIcon(wKnight);
            }
            else{
                if(piece.getPieceName().equals("King"))     piece.setImgIcon(bKing);
                if(piece.getPieceName().equals("Queen"))    piece.setImgIcon(bQueen);
                if(piece.getPieceName().equals("Bishop"))   piece.setImgIcon(bBishop);
                if(piece.getPieceName().equals("Pawn"))     piece.setImgIcon(bPawn);
                if(piece.getPieceName().equals("Rook"))     piece.setImgIcon(bRook);
                if(piece.getPieceName().equals("Knight"))   piece.setImgIcon(bKnight);
            }
        }
       
    
     game.updateBoard();
    }

    @Override
    /**
     *  pressed square
     */
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                SquareButton b =  cb.getBoard()[i][j].getButtonOnSquare(); //get the clicked button
                Square square = cb.getSquare(b);                           //get the clicked corresponding square
                if (e.getSource() == b) {
                    if(game.isLegalChoice(clickedOnce, b)){   //if this click is relevant to the game/ either correct chosen piece to move or correct sqaure to move the piece to
                        clickedOnce = !clickedOnce; 
                        return; 
                    }                    
                    else{ break; } 
                }                
            }
        }
    }

    public JPanel getMainPanel() { return mainPanel; }
}
