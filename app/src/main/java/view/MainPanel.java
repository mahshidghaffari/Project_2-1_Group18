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

    private JPanel chessboard = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JPanel xAxis = new JPanel();
    private JPanel yAxis = new JPanel();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ChessBoard cb;
    private Game game;
    private boolean clickedOnce =false;
    private FilePath FilePath = new FilePath();

    MainPanel(Game game) {
        this.game = game;
        initialize();
    }

    public void initialize() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(screenSize.height, screenSize.height));
        mainPanel.setBounds(screenSize.height/2, 0, screenSize.height, screenSize.height);

        xAxis.setLayout(new GridLayout(1,8));
        yAxis.setLayout(new GridLayout(8, 1));
        xAxis.setPreferredSize(new Dimension(screenSize.height, 20));
        yAxis.setPreferredSize(new Dimension(20, screenSize.height));

        chessboard.setLayout(new GridLayout(8, 8));
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
                                    if(game.getWhitePlayer().getIsMyTurn()){
                                        if(piece.isWhite())         { sq.getButtonOnSquare().setBackground(Color.GREEN);}//new Color(0f,1f,0f,.3f))
                                    }
                                    else if(game.getBlackPlayer().getIsMyTurn()){
                                        if(!piece.isWhite()){ sq.getButtonOnSquare().setBackground(Color.BLUE);}
                                    }
                                    else{
                                        return;
                                    }
                                        //else if(!piece.isWhite())   { sq.getButtonOnSquare().setBackground(Color.BLUE); }
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
                chessboard.add(button, i, j);
            }
        }
        initialPositions();

        // Add numbers to x and y axis
        for (int i = 0; i < 8; i++) {
            xAxis.add(new JLabel("" + i,  SwingConstants.CENTER));
            yAxis.add(new JLabel("" + i,  SwingConstants.CENTER));
            Font font = new Font("Times", Font.BOLD,17);
            xAxis.setFont(font);
            yAxis.setFont(font);
        }

        mainPanel.add(chessboard, BorderLayout.CENTER);
        mainPanel.add(xAxis, BorderLayout.NORTH);
        mainPanel.add(yAxis, BorderLayout.WEST);
    }

    /**
     * Initialize chessboard with the initial positions of the pieces
     * White at the bottom and Black on top
     */
    public void initialPositions() {
        ImageIcon bBishop = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bbishop.png")));
        ImageIcon bKing = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bking.png")));
        ImageIcon bKnight = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bknight.png")));
        ImageIcon bPawn = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bpawn.png")));
        ImageIcon bRook = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("brook.png")));
        ImageIcon bQueen = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("bqueen.png")));

        ImageIcon wBishop = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wbishop.png")));
        ImageIcon wKing = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wking.png")));
        ImageIcon wKnight = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wknight.png")));
        ImageIcon wPawn = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wpawn.png")));
        ImageIcon wQueen = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wqueen.png")));
        ImageIcon wRook = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("wrook.png")));

        ImageIcon rBishop = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("rbishop.png")));
        ImageIcon rKing = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("rking.png")));
        ImageIcon rKnight = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("rknight.png")));
        ImageIcon rPawn = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("rpawn.png")));
        ImageIcon rRook = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("rrook.png")));
        ImageIcon rQueen = new ImageIcon(ImageLoader.loadImage(FilePath.getFilePath("rqueen.png")));

        for(Piece piece: cb.getLivePieces()){
            if(piece.isWhite()){
                if(piece.getPieceName().equals("King")) {
                    piece.setImgIcon(wKing);
                    piece.setHighlightedImgIcon(rKing);
                }
                if(piece.getPieceName().equals("Queen")) {
                    piece.setImgIcon(wQueen);
                    piece.setHighlightedImgIcon(rQueen);
                }

                if(piece.getPieceName().equals("Bishop"))  {
                    piece.setImgIcon(wBishop);
                    piece.setHighlightedImgIcon(rBishop);
                }
                if(piece.getPieceName().equals("Pawn"))    {
                    piece.setImgIcon(wPawn);
                    piece.setHighlightedImgIcon(rPawn);
                }

                if(piece.getPieceName().equals("Rook")) {
                    piece.setImgIcon(wRook);
                    piece.setHighlightedImgIcon(rRook);
                }

                if(piece.getPieceName().equals("Knight"))  {
                    piece.setImgIcon(wKnight);
                    piece.setHighlightedImgIcon(rKnight);
                }
            }
            else{
                if(piece.getPieceName().equals("King")) {
                    piece.setImgIcon(bKing);
                    piece.setHighlightedImgIcon(rKing);
                }
                if(piece.getPieceName().equals("Queen")) {
                    piece.setImgIcon(bQueen);
                    piece.setHighlightedImgIcon(rQueen);
                }
                if(piece.getPieceName().equals("Bishop")) {
                    piece.setImgIcon(bBishop);
                    piece.setHighlightedImgIcon(rBishop);
                }
                if(piece.getPieceName().equals("Pawn")) {
                    piece.setImgIcon(bPawn);
                    piece.setHighlightedImgIcon(rPawn);
                }
                if(piece.getPieceName().equals("Rook"))  {
                    piece.setImgIcon(bRook);
                    piece.setHighlightedImgIcon(rRook);
                }
                if(piece.getPieceName().equals("Knight")) {
                    piece.setImgIcon(bKnight);
                    piece.setHighlightedImgIcon(rKnight);
                }
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
