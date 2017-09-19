import java.util.*;
import java.text.*;

public class test{
	public static void main(String[] args){

		try{
			Assignment myass = new Assignment();

			SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

			Submission submission = myass.add("aaaa1111", new Date(2000000), 68);
			submission = myass.add("bbbb1111", new Date(600000), 57);
			submission = myass.add("aaaa1111", new Date(400000), 10);
			submission = myass.add("aaaa1111", new Date(1000000), 68);

			submission = myass.add("aaaa1111", new Date(200000), 56);
			submission = myass.add("bbbb1111", new Date(1600000), 23);
			submission = myass.add("aaaa1111", new Date(1200000), 80);
			submission = myass.add("aaaa1111", new Date(800000), 23);
			submission = myass.add("bbbb1111", new Date(1400000), 40);
			submission = myass.add("aaaa1111", new Date(1800000), 50);
			Submission sub = myass.getSubmissionBefore("aaaa1111", new Date(700000));
			TreeMap<Date, String> myTree = new TreeMap<Date, String>();
			myTree.put(sub.getTime(),"a");

			System.out.println(myTree);
	}
	catch(Exception e){
		//stem.out//sdfadsf
		e.printStackTrace();
	}
	}
}
