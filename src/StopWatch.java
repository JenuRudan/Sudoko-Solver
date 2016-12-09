
public class StopWatch implements Runnable{

	MainFrame MF1;
	int minutes=0;
	int seconds=0;
	org.apache.commons.lang3.time.StopWatch S1= new org.apache.commons.lang3.time.StopWatch();
	StopWatch(MainFrame F1)
	{
		MF1=F1;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		S1.start();
		while(true)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if((S1.getTime()/1000)>60)
			{
				minutes=(int) ((S1.getTime()/1000)/60);
				seconds=(int) ((S1.getTime()/1000)-(minutes*60));
				MF1.lblTime.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
				System.out.println("Minutes "+String.valueOf(minutes)+" Seconds "+String.valueOf(seconds));
			}
			else
			{
				seconds=(int) (S1.getTime()/1000);
				MF1.lblTime.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
			}
		}
	}
	
}
