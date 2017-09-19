import java.util.Date;
import java.util.List;
import java.util.*;
import java.text.*;


public class Assignment implements SubmissionHistory {

	/**
	 * Default constructor
	 */
	TreeMap<String, Details> students = new TreeMap<String, Details>();

	public Assignment() {
		// TODO initialise your data structures
	}

	private class Handin implements Submission{
		private String unikey;
		private Date timestamp;
		private Integer grade;
		public String getUnikey(){
			return unikey;
		}
		public Date getTime(){
			return timestamp;
		}
		public Integer getGrade(){
			return grade;
		}
	}


	private class Details{
		public Integer bestGrade;
		public Integer latestGrade;
		public TreeMap<Date, String> myGrades;
	}


	@Override
	public Integer getBestGrade(String unikey) {
		// TODO Implement this, ideally in better than O(n)
		return null;
	}

	@Override
	public Submission getSubmissionFinal(String unikey) {
		// TODO Implement this, ideally in better than O(n)
		return null;
	}

	@Override
	public Submission getSubmissionBefore(String unikey, Date deadline) {
		// TODO Implement this, ideally in better than O(n)
		return null;
	}

	@Override
	public Submission add(String unikey, Date timestamp, Integer grade) {
		// TODO Implement this, ideally in better than O(n)
		if(unikey == null || timestamp == null || grade == null){
			return null;
		}

		SimpleDateFormat dt = new SimpleDateFormat("yymmddhhmmss");
		String s = dt.format(timestamp);

		if(students.get(unikey) == null){
			TreeMap<Integer, String> tm1 = new TreeMap<Integer, String>();
			tm1.put(grade, s);
			students.put(unikey, tm1);
		}
		else{
			students.get(unikey).put(grade, s);
		}
//idk
		return null;
	}

	@Override
	public void remove(Submission submission) {
		// TODO Implement this, ideally in better than O(n)



	}

	@Override
	public List<String> listTopStudents() {
		// TODO Implement this, ideally in better than O(n)
		// (you may ignore the length of the list in the analysis)
		return null;
	}

	@Override
	public List<String> listRegressions() {
		// TODO Implement this, ideally in better than O(n^2)
		return null;
	}

}
