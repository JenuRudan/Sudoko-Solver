import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.ParseConversionEvent;

public class InputParser {
	String sCurrentLine;
	int LineCount=0;
	Exception E=new Exception();
	public void ReadAndCheckEveryThing(String FileLocation,MainFrame M1,Checker C1) throws Exception {
		BufferedReader br = null;
		LineCount=0;
		try {
				for(int i=0;i<9;i++)
				{
					for(int j=0;j<9;j++)
					{
						M1.button[i][j].setText("");
					}
				}
			

			br = new BufferedReader(new FileReader(FileLocation));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				FillGridWithLine(M1,C1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	public void FillGridWithLine (MainFrame M1,Checker C1) throws Exception
	{
		if(sCurrentLine.length()==9)
		{
			for(int i=0;i<9;i++)
			{
				try{
				int x=Integer.parseInt(sCurrentLine.substring(i,i+1));
				if(x<=0 && x>9)
					throw E;
				}
				catch (Exception E)
				{
					throw E;
				}
				if(!sCurrentLine.substring(i,i+1).equals("0"))
				{M1.button[LineCount][i].setText(sCurrentLine.substring(i,i+1));
				M1.button[LineCount][i].setEnabled(false);
				}
				else
				{
					M1.button[LineCount][i].setText("");
					M1.button[LineCount][i].setEnabled(true);
				}
				if(C1.CheckColRow(M1.button[LineCount][i], M1)||C1.CheckBlock(M1.button[LineCount][i], M1))
					throw E;
				
				
			}	
		}
		LineCount++;
	}
}
