import java.util.*;
import java.text.*;

public class test{
	public static void main(String[] args){

		try{
			Assignment myass = new Assignment();

		String date_s = "2011-01-18 00:00:00";
		String date_d = "2011-01-18 00:00:03";
		String date_f = "2011-01-18 00:00:02";

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Date date1 = dt.parse(date_s);
		Date date2 = dt.parse(date_d);
		Date date3 = dt.parse(date_f);


			Submission submission = myass.add("460124876",date1,98);
			submission = myass.add("460124876",date2,99);
			submission = myass.add("460124875",date2,60);
			submission = myass.add("460124877",date3,97);
			int ge = myass.getBestGrade("460124876");
			System.out.println(ge);
	}
	catch(Exception e){
		//stem.out//sdfadsf
		e.printStackTrace();
	}
	}
}
