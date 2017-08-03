package Othello;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Result {

	Font font;
	Result(int status)
	{
		String str="";
		if(status ==1)
			str="PLAYER WHITE WON";
		else if(status ==2)
			str="PLAYER BLACK WON";
		else if(status==3)
			str="DRAW";
		
		JFrame j=new JFrame("The End");
		j.setBounds(200,200,500,430);
		j.setResizable(false);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
		JLabel jl1=new JLabel("Game Status: "+str);
		JLabel jl=new JLabel("");
		
		jl.setBounds(210, 200,490,420);
	    font = new Font("Times new Roman", Font.BOLD, 20);
	    jl1.setFont(font);
	    
		Icon end=new ImageIcon(Board.class.getResource("/images/dancing gif2.gif"));
jl.setIcon(end);
j.add(jl1,BorderLayout.NORTH);
//j.add(Board.newGame,BorderLayout.SOUTH);
//Board.newGame.addMouseListener(new MyMouseEvent());
j.add(jl,BorderLayout.CENTER);
j.setVisible(true);
	}
}
