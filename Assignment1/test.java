import java.util.*;
import java.text.*;

public class test{
	public static void main(String[] args){

		try{
			Assignment myass = new Assignment();

		String date_s = "2016/09/03 09:00:00";
		String date_d = "2016/09/03 16:00:00";
		String date_f = "2016/09/03 18:00:00";

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Date date1 = dt.parse(date_s);
		Date date2 = dt.parse(date_d);
		Date date3 = dt.parse(date_f);


			Submission submission = myass.add("cccc1234",date2,73);
			submission = myass.add("aaaa1234",date2,99);
			submission = myass.add("aaaa1234",date2,60);
			submission = myass.add("aaaa1234",date3,97);
			int ge = myass.getBestGrade("460124876");
			System.out.println(ge);
	}
	catch(Exception e){
		//stem.out//sdfadsf
		e.printStackTrace();
	}
	}
}
