import java.util.Collections;
import java.util.LinkedList;

public class aStarAlgo implements Runnable {

	static MainFrame MF1;
	static LinkedList<aStarObjectofList> Hn= new LinkedList<aStarObjectofList>();
	static int Delay=0;
	public aStarAlgo(MainFrame F1) {
		MF1 = F1;
		Delay=MF1.DelayValue;
	}

	public void run() {
		long timeb4 = System.currentTimeMillis();
		MF1.btnSolve.setEnabled(false);
		MF1.btnStop.setVisible(true);
//		if (solve(MF1.buttonINT));
		if(SolveAstar(MF1.buttonINT))

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
		MF1.chckbxDelaySolving.setEnabled(true);
		MF1.chckbxStepByStep.setEnabled(true);
		MF1.textField_4.setEditable(true);
		MF1.btnSolve.setEnabled(true);
		MF1.SWS.stop();
	}

	public static boolean FillList(int[] puzzle) {
		int N = (int) Math.round(Math.pow(puzzle.length, 0.25d)); // length ^
		int availablevalues=0;											// 0.25
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
		aStarObjectofList A= new aStarObjectofList();
		boolean FillFlag=false;
		A.index=myRow * SIZE + myCol;
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
				availablevalues++;
				A.count++;
				A.values[A.count-1]=choice;
				FillFlag=true;
				puzzle[myRow * SIZE + myCol]=-1;
			}
		}
		if(FillFlag)
			Hn.add(A);

		return false;
	}
	
	public static boolean SolveAstar(int[] puzzle)
	{
		for(int i=0;i<81;i++)
		{
		FillList(puzzle);
		}
		if(Hn.size()==0)
			return false;
		Collections.sort(Hn);
		aStarObjectofList index=Hn.getFirst();
//		if(index.index==0)
//		System.out.println(index.count);
		Hn.clear();
		for(int i=0;i<81;i++)
		{
			if(puzzle[i]==-1 && i!=index.index)
				puzzle[i]=0;
		}
		///solve ba2a
		int myRow = index.index / 9;
		int myCol = index.index % 9;
		for(int i=0;i<index.count;i++)
		{
			puzzle[index.index]=index.values[i];
		
			if(MF1.chckbxDelaySolving.isSelected() && Delay!=0)
			{
				try {
			
				Thread.sleep(Delay);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}}
			if(MF1.chckbxStepByStep.isSelected())
			MF1.button[myRow][myCol].setText(String.valueOf(index.values[i]));
			if(CheckSolved(puzzle))
				return true;
			else
				if(SolveAstar(puzzle))
					return true;
				else
					{puzzle[myRow * 9 + myCol]=0;
					if(MF1.chckbxStepByStep.isSelected())
					MF1.button[myRow][myCol].setText("");}
				
		}
		puzzle[index.index]=0;
		MF1.button[myRow][myCol].setText("");
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