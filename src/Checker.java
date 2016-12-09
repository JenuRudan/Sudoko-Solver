import java.awt.Button;
import java.awt.Color;
import java.io.Console;

import javax.swing.AbstractButton;
import javax.swing.JButton;

class Checker {

	public int blocksIs[][] = new int[9][9];
	public int blocksjs[][] = new int[9][9];

	int blockscount[]=new int[9];
	

	boolean CheckColRow(JButtonWID TheCheck, MainFrame F1) {
		//System.out.println(TheCheck.getText());
		if(!TheCheck.getText().equals(""))
		for (int i = 0; i < 9; i++) {
			//F1.button[i][TheCheck.j].setForeground(Color.green);
			if (TheCheck.getText().equals(F1.button[i][TheCheck.j].getText())
					&& TheCheck.ID != F1.button[i][TheCheck.j].ID && !TheCheck.getText().equals("0"))
				return true;
		}
		if(!TheCheck.getText().equals(""))
		for (int i = 0; i < 9; i++) {
			//F1.button[TheCheck.ID][i].setForeground(Color.green);
			if (TheCheck.getText().equals(F1.button[TheCheck.ID][i].getText())
					&& TheCheck.j != i && !TheCheck.getText().equals("0"))
				return true;
		}
		return false;
	}

	boolean CheckBlock(JButtonWID TheCheck, MainFrame F1) {
		if(!TheCheck.getText().equals(""))
		for (int i = 0; i < 9; i++) {
		//	F1.button[blocksIs[TheCheck.BlockID][i]][blocksjs[TheCheck.BlockID][i]].setForeground(Color.red);
			if(TheCheck.getText().equals(F1.button[blocksIs[TheCheck.BlockID][i]][blocksjs[TheCheck.BlockID][i]].getText()) && !TheCheck.getText().equals("0"))
			{
				if(TheCheck.ID!=F1.button[blocksIs[TheCheck.BlockID][i]][blocksjs[TheCheck.BlockID][i]].ID && TheCheck.j!=F1.button[blocksIs[TheCheck.BlockID][i]][blocksjs[TheCheck.BlockID][i]].j)
				return true;
			}
		
		}
		return false;
	}
	boolean CheckGoal(MainFrame F1)
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;i<9;j++)
			{
				if(F1.button[i][j].getText().equals(""))
					return false;
			}
		}
		return true;
	}
	boolean CheckSolvingGoal(int [][] B1 )
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;i<9;j++)
			{
				if( B1[i][j]==0)
					return false;
			}
		}
		return true;
	}
	boolean CheckSolvingBlock(JButtonWID TheCheck, JButtonWID [][] B1) {
		if(!TheCheck.getText().equals(""))
		for (int i = 0; i < 9; i++) {
		//	F1.button[blocksIs[TheCheck.BlockID][i]][blocksjs[TheCheck.BlockID][i]].setForeground(Color.red);
			if(TheCheck.getText().equals(B1[blocksIs[TheCheck.BlockID][i]][blocksjs[TheCheck.BlockID][i]].getText()) && !TheCheck.getText().equals("0"))
			{
				if(TheCheck.ID!=B1[blocksIs[TheCheck.BlockID][i]][blocksjs[TheCheck.BlockID][i]].ID && TheCheck.j!=B1[blocksIs[TheCheck.BlockID][i]][blocksjs[TheCheck.BlockID][i]].j)
				return true;
			}
		
		}
		return false;
	}
	boolean CheckSolvingColRow(JButtonWID TheCheck,JButtonWID [][] B1) {
		//System.out.println(TheCheck.getText());
		if(!TheCheck.getText().equals(""))
		for (int i = 0; i < 9; i++) {
			//F1.button[i][TheCheck.j].setForeground(Color.green);
			if (TheCheck.getText().equals(B1[i][TheCheck.j].getText())
					&& TheCheck.ID != B1[i][TheCheck.j].ID && !TheCheck.getText().equals("0"))
				return true;
		}
		if(!TheCheck.getText().equals(""))
		for (int i = 0; i < 9; i++) {
			//F1.button[TheCheck.ID][i].setForeground(Color.green);
			if (TheCheck.getText().equals(B1[TheCheck.ID][i].getText())
					&& TheCheck.j != i && !TheCheck.getText().equals("0"))
				return true;
		}
		return false;
	}
}
