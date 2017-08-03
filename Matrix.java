package Othello;

import java.util.ArrayList;

public class Matrix {

	static int [][]board={
			{-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,1,2,-1,-1,-1},
			{-1,-1,-1,2,1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1,-1,-1}};

	static int[] xDir = {-1,0,0,1,-1,-1,1,1};
	static int yDir[] = {0,-1,1,0,-1,1,1,-1}; 
    public static void initialMatrix()
    {
    	for(int i=0;i<8;i++)
    	{
    		for(int j =0;j<8;j++)
    		{
    			if(i==3&&j==3||i==4&&j==4)
    				board[i][j]=1;
    			else if (i==3&&j==4||i==4&&j==3)
    			    board[i][j]=2;
    			else
    				board[i][j]=-1;
    		}
    	}

    }
	public void setIndex(int index,int color){
		int count=0;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				if(index==count)
					board[i][j]=color;
				count++;
			}
	}

	public static boolean valid(int symbol,int x,int y) {
		boolean blackFound=false;
		for(int i = 0; i<xDir.length; i++){
			int stepX = xDir[i];
			int stepY = yDir[i];
			int currentX = x + stepX;
			int currentY = y + stepY;
			while(currentX<8&&currentX>=0&&currentY>=0&&currentY<8){
				if(board[currentX][currentY] == -1){
					break;
				}else if(board[currentX][currentY] == symbol){
					if(blackFound)return true;
					else break;
				}else{
					blackFound=true;
					currentX+= stepX;
					currentY+= stepY;
				}
			}
			blackFound=false;
		}
		return false;
	}

	public static  boolean move(int symbol, int x, int y){

		board[x][y]=symbol;
		ArrayList<Integer>maxPossible=new ArrayList<>();
		boolean foundOther=false;
		ArrayList<Integer>tobeChanged=new ArrayList<>();
		for(int i = 0; i<xDir.length; i++){
			int stepX = xDir[i];
			int stepY = yDir[i];
			int currentX = x + stepX;
			int currentY = y + stepY;
			while(currentX<8&&currentX>=0&&currentY>=0&&currentY<8){
				if(board[currentX][currentY] == -1){
					break;
				}else if(board[currentX][currentY] == symbol){
					if(foundOther){
						if(maxPossible.size()<tobeChanged.size())
							maxPossible=tobeChanged;
					}
					break;
				}else{
					foundOther=true;
					tobeChanged.add(8*currentX+currentY);
					currentX += stepX;
					currentY += stepY;
				}
			}
			tobeChanged=new ArrayList<>();	
		}
		if(maxPossible.size()==0)
			return false;
		else{
			for(int each:maxPossible){
				Pair p=Board.getmatrixIndices(each);
				board[p.x][p.y]=symbol;
		        Board.update(each);	
			}
			return true;
		}
	}
}
