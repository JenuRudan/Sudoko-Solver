
public class aStarObjectofList implements Comparable<aStarObjectofList>{
public int index=0;
public int count=0;
public int [] values= new int [9];
	@Override
	public int compareTo(aStarObjectofList o) {
		
		if(this.count>o.count)
			return 1;
		else if(this.count==o.count)
			return 0;
		else
			return -1;
		// TODO Auto-generated method stub
	}

}
