import java.util.Date;
import java.util.List;
import java.util.*;
import java.text.*;


public class Assignment implements SubmissionHistory {

	/**
	 * Default constructor
	 */
	public TreeMap<String, Details> students;

	public Assignment() {
		this.students = new TreeMap<String, Details>();
	}

	private class Handin implements Submission{
		private String unikey;
		private Date timestamp;
		private Integer grade;

		public Handin(){
			unikey = null;
			timestamp = null;
			grade = null;
		}

		public Handin(String newUnikey, Date newDate, Integer newGrade){
			unikey = newUnikey;
			timestamp = newDate;
			grade = newGrade;
		}

		public String getUnikey(){
			return unikey;
		}
		public Date getTime(){
			return timestamp;
		}
		public Integer getGrade(){
			return grade;
		}
		public void setUnikey(String newUnikey){
			unikey = newUnikey;
		}
		public void setTime(Date newDate){
			timestamp = newDate;
		}
		public void setGrade(Integer newGrade){
			grade = newGrade;
		}
	}


	private class Details{
		public Details(){
			bestGrade = -1;
			latestGrade = -1;
			latest = null;
			sortedDates = null;
			sortedGrades = null;
		}
		public Integer latestGrade;
		public Integer bestGrade;
		public Date latest;
		public TreeMap<Date,  Handin> sortedDates;
		public TreeMap<Integer, Handin> sortedGrades;
	}


	@Override
	public Integer getBestGrade(String unikey) throws IllegalArgumentException{
		if(unikey == null){
			throw new IllegalArgumentException("The argument does not exist");
		}
		if(this.students.get(unikey) == null){
			return null;
		}
		Details thisStudent = this.students.get(unikey);
		if(thisStudent.bestGrade == -1){
			return null;
		}
		else{
			return thisStudent.bestGrade;
		}
		// TODO Implement this, ideally in better than O(n)
	}

	@Override
	public Submission getSubmissionFinal(String unikey) throws IllegalArgumentException{
		if(unikey == null){
			throw new IllegalArgumentException("The argument does not exist");
		}
		if(this.students.get(unikey) == null){
			return null;
		}
		Details thisStudent = this.students.get(unikey);

		if(thisStudent.latestGrade == -1){
			return null;
		}
		else{
			return thisStudent.sortedDates.get(thisStudent.latest);
		}
		// TODO Implement this, ideally in better than O(n)
	}

	@Override
	public Submission getSubmissionBefore(String unikey, Date deadline) throws IllegalArgumentException{
		if(unikey == null||deadline == null){
			throw new IllegalArgumentException("The argument does not exist");
		}
		if(this.students.get(unikey) == null){
			return null;
		}
		Details thisStudent = this.students.get(unikey);

		if(thisStudent.latestGrade == -1){
			return null;
		}
		else{
			return thisStudent.sortedDates.get(thisStudent.sortedDates.floorKey(deadline));
		}
		// TODO Implement this, ideally in better than O(n)
	}

	@Override
	public Submission add(String unikey, Date timestamp, Integer grade) {
		// TODO Implement this, ideally in better than O(n)
		if(unikey == null || timestamp == null || grade == null){
			return null;
		}
		Handin submission = new Handin(unikey, timestamp, grade);

		if(this.students.get(unikey) == null){
			Details dts = new Details();
			dts.bestGrade = grade;
			dts.latestGrade = grade;
			dts.latest = timestamp;
			dts.sortedDates.put(timestamp, submission);
			dts.sortedGrades.put(grade, submission);
			this.students.put(unikey, dts);
		}
		else{
			Details thisStudent = this.students.get(unikey);
			if(thisStudent.bestGrade < grade){
				thisStudent.bestGrade = grade;
			}
			if(timestamp.compareTo(thisStudent.latest) > 0){
				thisStudent.latestGrade = grade;
				thisStudent.latest = timestamp;
			}
			thisStudent.sortedDates.put(timestamp, submission);
			thisStudent.sortedGrades.put(grade, submission);
		}
		return submission;
	}

	@Override
	public void remove(Submission submission) throws IllegalArgumentException{
		// TODO Implement this, ideally in better than O(n)
		if(submission == null){
			throw new IllegalArgumentException("The argument does not exist");
		}

		String removeUnikey = submission.getUnikey();
		Date removeTimestamp = submission.getTime();
		Integer removeGrade = submission.getGrade();





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
