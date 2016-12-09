public class DepthFirstAlgo implements Runnable {

	static MainFrame MF1;
	public int firstavailableI;
	public int firstavailableJ;
	public JButtonWID[][] B1;
static int Delay=0;

	DepthFirstAlgo(MainFrame F1) {
		MF1 = F1;
		Delay=MF1.DelayValue;
	}

	public void run() {
		long timeb4 = System.currentTimeMillis();
		MF1.btnStop.setVisible(true);
		MF1.btnSolve.setEnabled(false);
		if (solve(MF1.buttonINT))

		{
			long timeafter = System.currentTimeMillis();
			timeafter = timeafter - timeb4;
			System.out.printf(String.valueOf(timeafter));
			int count = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					MF1.button[i][j].setText(String
							.valueOf(MF1.buttonINT[count]));
					count++;
				}
			}
			
			long timeafterall=System.currentTimeMillis()-timeb4;
			
			MF1.statusLabel.setText("Sudoko Solving took: "+timeafter +" ms"+ " and With GUI: " + timeafterall +" ms");
			
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
		MF1.SWS.stop();
	}

	public static boolean solve(int[] puzzle) {
		int N = 3;
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
				puzzle[myRow * SIZE + myCol] = choice;
				if(MF1.chckbxStepByStep.isSelected())
				MF1.button[myRow][myCol].setText(String.valueOf(choice));
				if(MF1.chckbxDelaySolving.isSelected() && Delay!=0)
				{
					try {
				
					Thread.sleep(Delay);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}}
				boolean solved = solve(puzzle);
				if (solved)
					return true;
				else
				{	puzzle[myRow * SIZE + myCol] = 0;
				if(MF1.chckbxStepByStep.isSelected())
				MF1.button[myRow][myCol].setText("");
				}
			}
		}
		return false;
	}
}
