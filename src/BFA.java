import java.util.LinkedList;
import java.util.Queue;

public class BFA implements Runnable {
	static MainFrame MF1;
	static Queue<BFAstatus> Q = new LinkedList<BFAstatus>();
static int Delay=0;
	BFA(MainFrame F1) {
		MF1 = F1;
Delay=MF1.DelayValue;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long timeb4 = System.currentTimeMillis();
		MF1.btnSolve.setEnabled(false);
		MF1.btnStop.setVisible(true);
		if (!CheckSolved(MF1.buttonINT))
			if (SolveBFA(MF1.buttonINT)) {
				long timeafter = System.currentTimeMillis();
				timeafter = timeafter - timeb4;
				int count = 0;
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						MF1.button[i][j].setText(String
								.valueOf(MF1.buttonINT[count]));
						count++;
					}
				}
				
				long timeafterall = System.currentTimeMillis() - timeb4;
				MF1.statusLabel.setText("Sudoko Solving took: " + timeafter
						+ " ms" + " and With GUI: " + timeafterall + " ms");
				
			}
			else{
				MF1.statusLabel.setText("Sorry but this Sudoko can't be solved ;/");
			}
		MF1.btnBrowse.setEnabled(true);
		MF1.btnStop.setVisible(false);
		MF1.btnSolve.setEnabled(true);
		MF1.chckbxDelaySolving.setEnabled(true);
		MF1.chckbxStepByStep.setEnabled(true);
		MF1.textField_4.setEditable(true);
		Q.clear();
		MF1.SWS.stop();
	}

	public static boolean FillQueue(int[] puzzle) {
		int N = (int) Math.round(Math.pow(puzzle.length, 0.25d)); // length ^
		// 0.25
		int SIZE = N * N;
		int CELLS = SIZE * SIZE;
		boolean noEmptyCells = true;
		int myRow = 0, myCol = 0;
		for (int i = 0; i < CELLS; i++) {
			if (puzzle[i] == 0) {
				myRow = i / SIZE;
				myCol = i % SIZE;
				noEmptyCells = false;
				break;
			}
		}
		if (noEmptyCells)
			return true;

		for (int choice = 1; choice <= SIZE; choice++) {
			boolean isValid = true;
			int gridRow = myRow / N;
			int gridCol = myCol / N;
			// check grid for duplicates
			for (int row = N * gridRow; row < N * gridRow + N; row++)
				for (int col = N * gridCol; col < N * gridCol + N; col++)
					if (puzzle[row * SIZE + col] == choice)
						isValid = false;

			// row & column
			for (int j = 0; j < SIZE; j++)
				if (puzzle[SIZE * j + myCol] == choice
						|| puzzle[myRow * SIZE + j] == choice) {
					isValid = false;
					break;
				}

			if (isValid) {
				BFAstatus s = new BFAstatus();
				s.Value = choice;
				s.index = myRow * SIZE + myCol;
				Q.add(s);
			}
		}
		// if (Q.size() == 0)
		// return false;
		// int x = Q.size();
		// BFAstatus s = null;

		return false;
	}

	static boolean SolveBFA(int[] puzzle) {
		FillQueue(puzzle);
		while (Q.size() != 0) {
			BFAstatus temp = Q.remove();
			puzzle[temp.index] = temp.Value;
			int myRow = temp.index / 9;
			int myCol = temp.index % 9;
			if(MF1.chckbxDelaySolving.isSelected() && Delay!=0)
			{
				try {
			
				Thread.sleep(Delay);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}}
			if(MF1.chckbxStepByStep.isSelected())
			MF1.button[myRow][myCol].setText(String.valueOf(temp.Value));
			if (CheckSolved(puzzle))
				return true;
			else if (!temp.visited) {
				temp.visited = true;
				Q.add(temp);
				FillQueue(puzzle);
				puzzle[temp.index] = 0;
				if(MF1.chckbxStepByStep.isSelected())
				MF1.button[myRow][myCol].setText("");
			} else {
				Q.add(temp);
				// puzzle[temp.index]=0;
			}
			// else
			// {
			// puzzle[temp.index]=0;
			//
			// Q.add(temp);
			// }
		}

		return false;
	}

	static boolean CheckSolved(int[] puzzle) {
		for (int i = 0; i < 81; i++) {
			if (puzzle[i] == 0)
				return false;
		}
		return true;

	}
}
