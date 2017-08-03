package Othello;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyMouseEvent implements MouseListener {


	public void mouseClicked(MouseEvent e) {

		//get index of button just clicked and assuming event e is referring to button that was clicked- yes it is :)
		//update function to set all those whose color change and update score too
        //Board.filledCount=64;//for testing
		Object justClicked=e.getSource();
		int index=Board.getIndex(justClicked);
		//System.out.println(index);
		if(justClicked==Board.exit)
		{
			
			System.exit(0);
		}
		else if(justClicked==Board.newGame)
		{
			Board.jf.setVisible(false);
			Othello.main(null);
//			Board.initialConfig();
		}
		else if(Board.isfilled.get(index)==Color.GRAY)
		{
			Board.button[index].setBackground(Color.WHITE);
			Board.button[index].setIcon(Board.whiteIcon);
			Board.isfilled.put(index,Color.WHITE);
			Board.player1Count++;
			Pair p=Board.getmatrixIndices(index);
			//System.out.println("call to move update "+p.x+" "+p.y);
			if(Matrix.move(Board.COLORWHITE, p.x, p.y))
				{
				Board.player1Turn=false;
				Board.isInvalid=new HashMap<>();
				Board.filledCount++;
				//button[index].setEnabled(false);
				}
		}
		else if(Board.isfilled.get(index)==Color.DARK_GRAY)
		{
			Board.button[index].setBackground(Color.BLACK);
			Board.button[index].setIcon(Board.blackIcon);
			Board.isfilled.put(index,Color.BLACK);
			Board.player2Count++;
			Pair p=Board.getmatrixIndices(index);
			if(Matrix.move(Board.COLORBLACK, p.x, p.y))
				{
				Board.player1Turn=true;
				Board.isInvalid=new HashMap<>();
				Board.filledCount++;
				//button[index].setEnabled(false);
				}
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

		JButton justHovered=(JButton)e.getSource();
		int index=Board.getIndex(justHovered);
		if(Board.isfilled.get(index)==Board.green)
		{
			if(Board.player1Turn){
				if(Board.isValid(index,Board.COLORWHITE)){
					Board.button[index].setBackground(Color.GRAY);
					Board.button[index].setIcon(Board.grayIcon);
					Board.isfilled.put(index,Color.GRAY);
					return;
				}
				//invalidButtons++;
				Board.isInvalid.put(index,true);
			}
			else{
				if(Board.isValid(index,Board.COLORBLACK)){
					Board.button[index].setBackground(Color.DARK_GRAY);
					Board.isfilled.put(index,Color.DARK_GRAY);
					Board.button[index].setIcon(Board.darkGrayIcon);
				return ;
				}
				Board.isInvalid.put(index,true);
			}
		if(Board.isInvalid.size()>=64-Board.filledCount)
			{//no more possible moves
			Board.filledCount=64;
	//		justHovered.doClick();
			}
		}

		if(Board.gameStatus()== Board.INCOMPLETE)
		{
			if(!Board.player1Turn){
				Board.showStatus("Player Black's Turn");
			}else{   
				Board.showStatus("Player White's Turn");
			}
		}
		else{
			if(Board.gameStatus() == Board.PLAYER1WON){
				Board.showStatus("Player White Won");
			}
			else if(Board.gameStatus() == Board.PLAYER2WON){
				Board.showStatus("Player Black Won");
			}
			else{
				Board.showStatus("draw");
			}
//		    Board.jf.setVisible(false);
//		//	Board.jf.dispose();
		    new Result(Board.gameStatus());

		    //			Board.jf.removeAll();
//
//			JLabel jl1=new JLabel("");
//			jl1.setBounds(150,150,400,300);
//			jl1.setIcon(Board.theEnd);
//			Board.jf.setLayout(null);
//			//JPanel jp=new JPanel();
//			//jp.setVisible(true);
//			//jp.add(jl1);
//			Board.jf.add(jl1);
//			//Board.jf.add(Board.statusButton);
//			Board.jf.setVisible(true);
//            //System.exit(0);
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object justHovered=e.getSource();
		int index=Board.getIndex(justHovered);
		if(Board.isfilled.get(index)==Color.GRAY||Board.isfilled.get(index)==Color.DARK_GRAY)
		{
			Board.button[index].setBackground(Board.green);
			Board.button[index].setIcon(Board.emptyIcon);
			Board.isfilled.put(index,Board.green);
		}	
	}
}
