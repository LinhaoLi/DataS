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

	public class Handin implements Submission{
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
			bestGrade = null;
			latestGrade = null;
			latest = null;
			sortedDates = null;
			sortedGrades = null;
		}
		public Integer latestGrade;
		public Integer bestGrade;
		public Date latest;
		public TreeMap<Date,  Handin> sortedDates;
		public TreeMap<Integer, TreeMap<Date,Handin>> sortedGrades;
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
		if(thisStudent.bestGrade == null){
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

		if(thisStudent.latestGrade == null){
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

		if(thisStudent.latestGrade == null){
			return null;
		}
		else{
			Date result = thisStudent.sortedDates.floorKey(deadline);
			if(result == null){
				return null;
			}else{
				return thisStudent.sortedDates.get(result);
			}
		}
		// TODO Implement this, ideally in better than O(n)
	}

	@Override
	public Submission add(String unikey, Date timestamp, Integer grade) throws IllegalArgumentException{
		// TODO Implement this, ideally in better than O(n)
		if(unikey == null || timestamp == null || grade == null){
			throw new IllegalArgumentException("The argument does not exist");
		}
		Handin submission = new Handin(unikey, timestamp, grade);

		if(this.students.get(unikey) == null){
			Details dts = new Details();
			dts.bestGrade = grade;
			dts.latestGrade = grade;
			dts.latest = timestamp;
			dts.sortedDates = new TreeMap<Date,  Handin>();
			dts.sortedGrades = new TreeMap<Integer, TreeMap<Date, Handin>>();
			TreeMap<Date,Handin> sameGrades = new TreeMap<Date,Handin>();
			Handin garbage = sameGrades.put(timestamp, submission);
			Handin oldSubmission1 = dts.sortedDates.put(timestamp, submission);
			TreeMap<Date,Handin> oldSubmission2 = dts.sortedGrades.put(grade, sameGrades);
			Details oldSubmission3 = this.students.put(unikey, dts);
		}
		else{
			Details thisStudent = this.students.get(unikey);
			if(thisStudent.bestGrade < grade){
				thisStudent.bestGrade = grade;
			}
			if(timestamp.compareTo(thisStudent.latest) >= 0){
				thisStudent.latestGrade = grade;
				thisStudent.latest = timestamp;
			}
			Handin oldSubmission1 = thisStudent.sortedDates.put(timestamp, submission);
			if(!thisStudent.sortedGrades.containsKey(grade)){
				TreeMap<Date,Handin> sameGrades = new TreeMap<Date,Handin>();
				Handin garbage = sameGrades.put(timestamp, submission);
				TreeMap<Date,Handin> oldSubmission2 = thisStudent.sortedGrades.put(grade, sameGrades);
			}
			else{
				TreeMap<Date,Handin> sameGrades = thisStudent.sortedGrades.get(grade);
				Handin oldSubmission2 = sameGrades.put(timestamp, submission);
			}

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

		Details thisStudent = this.students.get(removeUnikey);

		Handin removeSubmission = thisStudent.sortedDates.remove(removeTimestamp);
		thisStudent.latest =  thisStudent.sortedDates.lastKey();
		thisStudent.latestGrade = thisStudent.sortedDates.get(thisStudent.latest).getGrade();

		TreeMap<Date,Handin> sameGrades = thisStudent.sortedGrades.get(removeGrade);
		Handin removeChangeBG = sameGrades.remove(removeTimestamp);
		if(	removeChangeBG.getGrade() == thisStudent.bestGrade){
			if(sameGrades.size()==0){
				thisStudent.sortedGrades.remove(removeGrade);
			}
			thisStudent.bestGrade = thisStudent.sortedGrades.lastKey();
		}
	}

	@Override
	public List<String> listTopStudents() {
		// TODO Implement this, ideally in better than O(n)
		// (you may ignore the length of the list in the analysis)
		List<String> topStudents = new ArrayList<String>();
		int best = -1;
		for(Map.Entry<String, Details> entry: this.students.entrySet()){
			Details currentDetails = entry.getValue();
			if(currentDetails.bestGrade > best){
				best = currentDetails.bestGrade;
				topStudents.clear();
				topStudents.add(entry.getKey());
			}
			else if(currentDetails.bestGrade == best){
				topStudents.add(entry.getKey());
			}
		}
		return topStudents;
	}

	@Override
	public List<String> listRegressions() {
		// TODO Implement this, ideally in better than O(n^2)
		List<String> regressionStudents = new ArrayList<String>();
		for(Map.Entry<String, Details> entry: this.students.entrySet()){
			Details currentDetails = entry.getValue();
			if(currentDetails.bestGrade > currentDetails.latestGrade){

				regressionStudents.add(entry.getKey());
			}
		}
		return regressionStudents;
	}

}
