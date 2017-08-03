package Othello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Board {
	
	static JFrame jf;
	static JPanel[] row;
	static JButton[] button;
	static JButton statusButton;
    static JButton scoreBlack;
    static JButton scoreWhite; 
    static JButton newGame; 
    static JButton exit; 
    
	JLabel jl;
	JLabel whiteScoreLabel;
	JLabel blackScoreLabel;
	static Color green=new Color(0, 153, 0);
	JLabel bg = new JLabel("");
	static int filledCount=4;
	static int player1Count=2;
    static int player2Count=2;
	static boolean player1Turn=true;
	
	final static int PLAYER1WON = 1;
	final static int PLAYER2WON = 2;
	final static int DRAW = 3;
	final static int INCOMPLETE = 4;
	final static int COLORWHITE = 1;
	final static int COLORBLACK = 2;
	//1 means white;
	
	static HashMap<Integer,Color> isfilled =new HashMap<>();
    static HashMap<Integer, Boolean> isInvalid=new HashMap<>();
   
    static Icon emptyIcon=new ImageIcon(Board.class.getResource("/images/empty.png"));
	static Icon whiteIcon=new ImageIcon(Board.class.getResource("/images/whitecoin.png"));
	static Icon blackIcon=new ImageIcon(Board.class.getResource("/images/blackcoin.png"));
	static Icon grayIcon=new ImageIcon(Board.class.getResource("/images/grayicon.png"));
	static Icon darkGrayIcon=new ImageIcon(Board.class.getResource("/images/darkGrayicon.png"));
	static Icon theEnd=new ImageIcon(Board.class.getResource("/images/dancing gif.gif"));
	   
	int[] dimW = {250,50};
	int[] dimH = {35, 50};
	Dimension statusDimension = new Dimension(dimW[0], dimH[0]);
    Dimension regularDimension = new Dimension(dimW[1], dimH[1]);
	Dimension scoreDimension = new Dimension(dimW[1], dimH[0]);
	
	
	Board()
	{

		jf=new JFrame("Othello");
		jl=new JLabel("Game Status");
		newGame=new JButton("Restart");
		exit=new JButton("Quit");
		row=new JPanel[9];
		button=new JButton[64];
		scoreWhite=new JButton(); 
		scoreBlack=new JButton();
		statusButton=new JButton();
		whiteScoreLabel=new  JLabel("Player - White");
		blackScoreLabel=new JLabel("Player - Black");
		
	//	jf.setSize(610,500);
		jf.setBounds(100,100,700,530);
	//  jf.setBackground(Color.black);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(9,8);
		jf.setLayout(grid);


		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,2,2);

		for(int i = 0; i <=8; i++)
			row[i] = new JPanel();

		row[0].add(newGame);
		row[0].add(jl);
		statusButton.setBackground(Color.CYAN);
		scoreBlack.setBackground(Color.BLACK);
		scoreWhite.setBackground(Color.BLACK);
		statusButton.setPreferredSize(statusDimension);
		scoreBlack.setPreferredSize(scoreDimension);
		scoreWhite.setPreferredSize(scoreDimension);
		statusButton.setEnabled(false);
		scoreBlack.setEnabled(false);
		scoreWhite.setEnabled(false);
		row[0].add(statusButton);
		row[0].add(exit);
		jf.add(row[0]);

		for(int i = 1; i <=8; i++)
			row[i].setLayout(f2);

		for(int i = 0; i <64; i++) {
			button[i] = new JButton(); 
			button[i].setBackground(green);
			button[i].setIcon(emptyIcon);
			button[i].addMouseListener(new MyMouseEvent());
		}
		for(int i = 0; i <64; i++)
			button[i].setPreferredSize(regularDimension);
	
		newGame.addMouseListener(new MyMouseEvent());
		exit.addMouseListener(new MyMouseEvent());
		
		int buttonNum=0;
		for(int i = 1; i<=8; i++)
		{ 
			if(i==4)
				row[i].add(whiteScoreLabel);
			if(i==5)
				row[i].add(scoreWhite);
			for(int j=0;j<8;j++)
			{
				row[i].add(button[buttonNum++]);
				if(buttonNum-1==36||buttonNum-1==27)
					isfilled.put(buttonNum-1,Color.WHITE);
				else if(buttonNum-1==35||buttonNum-1==28)
					isfilled.put(buttonNum-1, Color.BLACK);
				else
					isfilled.put(buttonNum-1,green);
			}
			if(i==4)
				row[i].add(blackScoreLabel);
			if(i==5)
			    row[i].add(scoreBlack);
			jf.add(row[i]);
		}
        initialConfig();
		jf.setVisible(true);
	}

	public static void initialConfig()
	{

		for(int i = 0; i<64; i++)
		{ 
				if(i==36||i==27)
					{
					button[i].setBackground(Color.WHITE);
					button[i].setIcon(whiteIcon);
					isfilled.put(i,Color.WHITE);
					}
				else if(i==35||i==28)
					{
					button[i].setIcon(blackIcon);
					button[i].setBackground(Color.BLACK);
					isfilled.put(i, Color.BLACK);
					}
				else
					{
					button[i].setIcon(emptyIcon);
					button[i].setBackground(Color.GREEN);
					isfilled.put(i,green);
					}
			
		}
		filledCount=4;
		player1Count=2;
	    player2Count=2;
		player1Turn=true;
		scoreBlack.setText(player2Count+"");
		scoreWhite.setText(player1Count+"");
		Matrix.initialMatrix();
		statusButton.setText("Player White's Turn");
		
			jf.setVisible(true);
	}
   public static boolean isValid(int index, int color) {
	
		Pair p=getmatrixIndices(index);
		boolean b=Matrix.valid(color, p.x, p.y);
		//System.out.println(b);
		return b;
	}
	public static void showStatus(String str)
	{
		statusButton.setText(str);
		scoreBlack.setText(Integer.toString(player2Count));
		//scoreWhite.setText(player1Count+" ");
		scoreWhite.setText(Integer.toString(player1Count));
	}
   public static int getIndex(Object justClicked) {
		for(int i=0;i<64;i++)
		{
			if(justClicked==button[i])
				return i;
		}
		return -1;
	}

	public static int gameStatus()
	{
		
		if(filledCount>=64)
		{
			if(player1Count>player2Count)
				return PLAYER1WON;
		
			else if(player2Count>player1Count)
				return PLAYER2WON;
			else 
				return DRAW;
		}
		else 
			return INCOMPLETE;
		
		//testing  //return PLAYER1WON;
	}

	public static void update(int buttonIndex)
	{
		if(player1Turn)
		{
			button[buttonIndex].setBackground(Color.white);
			button[buttonIndex].setIcon(whiteIcon);
			isfilled.put(buttonIndex,Color.WHITE);
			player1Count++;
			player2Count--;
		}
		else
		{
			button[buttonIndex].setBackground(Color.black);
			button[buttonIndex].setIcon(blackIcon);
			isfilled.put(buttonIndex,Color.BLACK);
			player2Count++;
			player1Count--;
		}
	}
	
    public static Pair getmatrixIndices(int index) {
		Pair p=new Pair(0,0);
		int count=0;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{

				if(count==index)
				{
					p.x=i;
					p.y=j;
				}
				count++;
			}
		}

		return p;
	}

	
}
//
////fill  28,29,36,37 initial setup
//button[36].setBackground(Color.WHITE);
//button[27].setBackground(Color.WHITE);
//button[28].setBackground(Color.BLACK);
//button[35].setBackground(Color.BLACK);
//button[36].setIcon(whiteIcon);
//button[27].setIcon(whiteIcon);
//button[28].setIcon(blackIcon);
//button[35].setIcon(blackIcon);
////button[36].setEnabled(false);
////button[27].setEnabled(false);
////button[28].setEnabled(false);
////button[35].setEnabled(false);

//for(int i = 0; i <64; i++) {
//	button[i] = new JButton(); 
//	button[i].setBackground(green);
//	button[i].setIcon(emptyIcon);
//	button[i].addMouseListener(new MyMouseEvent());
//}
