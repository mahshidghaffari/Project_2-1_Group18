package view;

import controller.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupMenu {
    private JFrame frame;
    private int WIDTH =750;
    private int HEIGHT=480;
    private String GameMode= "PvP";//default
    private int GameTime= 10;//default

    public SetupMenu(){

    }

    private void PrintGame(){
        System.out.println();
        System.out.println("GAME MODE: "+GameMode);
        System.out.println("GAME TIME: "+GameTime);
    }

    public void create() {

        frame = new JFrame();
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        //frame.setBounds(400, 100, WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        //MAIN PANELS

        JPanel PanelTop = new JPanel();
        PanelTop.setBackground(Color.DARK_GRAY);
        PanelTop.setPreferredSize(new Dimension(WIDTH,HEIGHT/10));
        frame.add(PanelTop,BorderLayout.NORTH);
        PanelTop.setLayout(null);

        JPanel PanelMiddle = new JPanel();
        PanelMiddle.setBackground(Color.LIGHT_GRAY);
        PanelMiddle.setPreferredSize(new Dimension(WIDTH,HEIGHT/10));
        frame.add(PanelMiddle,BorderLayout.CENTER);
        PanelMiddle.setLayout(null);

        JPanel PanelBottom = new JPanel();
        PanelBottom.setBackground(Color.DARK_GRAY);
        PanelBottom.setPreferredSize(new Dimension(WIDTH,HEIGHT/10));
        frame.add(PanelBottom,BorderLayout.SOUTH);
        PanelBottom.setLayout(null);

        //SUB PANELS

        JPanel PanelMidLeft = new JPanel();
        PanelMidLeft.setBackground(Color.WHITE);
        PanelMidLeft.setBounds(30,20,250,300);
        PanelMiddle.add(PanelMidLeft);
        PanelMidLeft.setLayout(new BorderLayout());

        JPanel PanelMidRight = new JPanel();
        PanelMidRight.setBackground(Color.WHITE);
        PanelMidRight.setBounds(WIDTH-265-30,20,250,300);
        PanelMiddle.add(PanelMidRight);
        PanelMidRight.setLayout(new BorderLayout());

        // SUB SUB PANELS
        JPanel PanelMidLeftUp = new JPanel();
        PanelMidLeftUp.setBackground(Color.WHITE);
        PanelMidLeftUp.setPreferredSize(new Dimension(WIDTH,HEIGHT/10));
        PanelMidLeft.add(PanelMidLeftUp,BorderLayout.NORTH);
        PanelMidLeftUp.setLayout(null);

        JPanel PanelMidLeftCenter = new JPanel();
        PanelMidLeftCenter.setBackground(Color.WHITE);
        PanelMidLeft.add(PanelMidLeftCenter,BorderLayout.CENTER);
        PanelMidLeftCenter.setLayout(null);

        JPanel PanelMidRighttUp = new JPanel();
        PanelMidRighttUp.setBackground(Color.WHITE);
        PanelMidRighttUp.setPreferredSize(new Dimension(WIDTH,HEIGHT/10));
        PanelMidRight.add(PanelMidRighttUp,BorderLayout.NORTH);
        PanelMidRighttUp.setLayout(null);

        JPanel PanelMidRightCenter = new JPanel();
        PanelMidRightCenter.setBackground(Color.WHITE);
        PanelMidRight.add(PanelMidRightCenter,BorderLayout.CENTER);
        PanelMidRightCenter.setLayout(null);

        //LABELS

        JLabel LabelTopTitle = new JLabel("WELCOME TO DICE CHESS");
        LabelTopTitle.setBounds(175, 0, WIDTH/2, HEIGHT/10);
        PanelTop.add(LabelTopTitle);
        LabelTopTitle.setForeground(Color.WHITE);
        LabelTopTitle.setBackground(new Color(240, 240, 240));
        LabelTopTitle.setHorizontalAlignment(SwingConstants.CENTER);
        LabelTopTitle.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel LabelBottomTitle = new JLabel("When Ready, Press here to play:");
        LabelBottomTitle.setBounds(250, 0, WIDTH/2, HEIGHT/10);
        PanelBottom.add(LabelBottomTitle);
        LabelBottomTitle.setForeground(Color.WHITE);
        LabelBottomTitle.setBackground(new Color(240, 240, 240));
        LabelBottomTitle.setHorizontalAlignment(SwingConstants.CENTER);
        LabelBottomTitle.setFont(new Font("SansSerif", Font.BOLD, 23));


        JLabel LabelTime = new JLabel("TIME");
        LabelTime.setForeground(Color.BLACK);
        LabelTime.setBounds(75, 3, 200, 50);
        LabelTime.setFont(new Font("Sitka Text", Font.BOLD|Font.ITALIC, 30));
        PanelMidLeftUp.add(LabelTime);

        JLabel LabelMode = new JLabel("MODE");
        LabelMode.setForeground(Color.BLACK);
        LabelMode.setBounds(75, 3, 200, 50);
        LabelMode.setFont(new Font("Sitka Text", Font.BOLD|Font.ITALIC, 30));
        PanelMidRighttUp.add(LabelMode);

        //BUTTONS: RIGHT------------------------------------------------------------------------------------------------
        //INITIALIZATION
        JButton ButtonPvP= new JButton("P vs P");
        JButton ButtonPvAI= new JButton("P vs AI");
        JButton ButtonAIvAI= new JButton("AI vs AI");

        //PvP
        ButtonPvP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ButtonPvP.setBackground(Color.LIGHT_GRAY);
                ButtonPvAI.setBackground(Color.WHITE);
                ButtonAIvAI.setBackground(Color.WHITE);
                GameMode="PvP";
                PrintGame();
            }
        });
        ButtonPvP.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 12));
        ButtonPvP.setForeground(Color.BLACK);
        ButtonPvP.setFocusable(false);
        ButtonPvP.setBackground(Color.WHITE);
        ButtonPvP.setBounds(25, 20, 200, 50);
        PanelMidRightCenter.add(ButtonPvP);

        //PvAI
        ButtonPvAI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ButtonPvP.setBackground(Color.WHITE);
                ButtonPvAI.setBackground(Color.LIGHT_GRAY);
                ButtonAIvAI.setBackground(Color.WHITE);
                GameMode="PvAI";
                PrintGame();
            }
        });
        ButtonPvAI.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 12));
        ButtonPvAI.setForeground(Color.BLACK);
        ButtonPvAI.setFocusable(false);
        ButtonPvAI.setBackground(Color.WHITE);
        ButtonPvAI.setBounds(25, 100, 200, 50);
        PanelMidRightCenter.add(ButtonPvAI);

        //AIvAI
        ButtonAIvAI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ButtonPvP.setBackground(Color.WHITE);
                ButtonPvAI.setBackground(Color.WHITE);
                ButtonAIvAI.setBackground(Color.LIGHT_GRAY);
                GameMode="AIvAI";
                PrintGame();
            }
        });
        ButtonAIvAI.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 12));
        ButtonAIvAI.setForeground(Color.BLACK);
        ButtonAIvAI.setFocusable(false);
        ButtonAIvAI.setBackground(Color.WHITE);
        ButtonAIvAI.setBounds(25, 180, 200, 50);
        PanelMidRightCenter.add(ButtonAIvAI);


        //BUTTONS: LEFT-------------------------------------------------------------------------------------------------
        //INITIALIZATION
        JButton Button10= new JButton("10min-10min");
        JButton Button5= new JButton("5min-5min");
        JButton Button20= new JButton("20min-20min");


        //TEN_TEN
        Button10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Button10.setBackground(Color.LIGHT_GRAY);
                Button5.setBackground(Color.WHITE);
                Button20.setBackground(Color.WHITE);
                GameTime=10;
                PrintGame();
            }
        });
        Button10.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 12));
        Button10.setForeground(Color.BLACK);
        Button10.setFocusable(false);
        Button10.setBackground(Color.WHITE);
        Button10.setBounds(25, 20, 200, 50);
        PanelMidLeftCenter.add(Button10);

        //FIVE_FIVE
        Button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Button10.setBackground(Color.WHITE);
                Button5.setBackground(Color.LIGHT_GRAY);
                Button20.setBackground(Color.WHITE);
                GameTime=5;
                PrintGame();
            }
        });
        Button5.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 12));
        Button5.setForeground(Color.BLACK);
        Button5.setFocusable(false);
        Button5.setBackground(Color.WHITE);
        Button5.setBounds(25, 100, 200, 50);
        PanelMidLeftCenter.add(Button5);

        //TWENTY_TWENTY
        Button20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Button10.setBackground(Color.WHITE);
                Button5.setBackground(Color.WHITE);
                Button20.setBackground(Color.LIGHT_GRAY);
                GameTime=20;
                PrintGame();
            }
        });
        Button20.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 12));
        Button20.setForeground(Color.BLACK);
        Button20.setFocusable(false);
        Button20.setBackground(Color.WHITE);
        Button20.setBounds(25, 180, 200, 50);
        PanelMidLeftCenter.add(Button20);

        //BUTTON PLAY
        JButton ButtonPlay= new JButton("PLAY");
        ButtonPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("\nLAUNCHING GAME.....");
                PrintGame();
                startGamebaord();
                frame.dispose();
            }
        });
        ButtonPlay.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 12));
        ButtonPlay.setForeground(Color.BLACK);
        ButtonPlay.setFocusable(false);
        ButtonPlay.setBackground(Color.WHITE);
        ButtonPlay.setBounds(650, 5, 70, 35);
        PanelBottom.add(ButtonPlay);

        frame.setBounds(400, 100, WIDTH-1, HEIGHT-1);
        frame.setBounds(400, 100, WIDTH, HEIGHT);
    }

    public void startGamebaord() {

        JFrame f = new JFrame("Dice Chess");
        Game game = new Game(f);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.height + screenSize.height/2, screenSize.height - 50);
        f.setLayout(new BorderLayout());
        f.setBackground(Color.GREEN);

        f.add(new SidePanel(game).getPane(), BorderLayout.WEST);
        f.add(new MainPanel(game).getMainPanel(), BorderLayout.CENTER);

        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

