import java.util.Date;
import java.util.List;
import java.util.*;
import java.text.*;


public class Assignment implements SubmissionHistory {

	public TreeMap<String, Details> students;

	/**
	 * Constructor: Initialize the data structure
	 */
	public Assignment() {

		//Construct a BST map of which entry has a Key of "unikey" and a Value of "Grade Details"
		this.students = new TreeMap<String, Details>();
	}


	/**
	 * Handin class that implements the submission, which contains a unikey, a submission type, a grade
	 */
	public class Handin implements Submission{

		private String unikey;
		private Date timestamp;
		private Integer grade;

		/**
		 * Constructor, set all variable to null
		 */
		public Handin(){
			unikey = null;
			timestamp = null;
			grade = null;
		}

		/**
		 * Constructor, set all the varibale to the given values
		 */
		public Handin(String newUnikey, Date newDate, Integer newGrade){
			unikey = newUnikey;
			timestamp = newDate;
			grade = newGrade;
		}

		/**
		 * Getters and setters
		 */
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

	/**
	 *	The class Details contains all the useful imformation that "ONE STUDENT"(a unikey)(like Linhao Li: lili6423) would use
	 * 	Some variable are used for effiency like bestGrade and latestGrade
	 */
	private class Details{

		/**
		 * Constructor: Set all the variable to null
		 */
		public Details(){
			bestGrade = null;
			latestGrade = null;
			latest = null;
			sortedDates = null;
			sortedGrades = null;
		}


		//The grade of last submission
		public Integer latestGrade;
		//The best grade of all his/her Submissions
		public Integer bestGrade;
		//The last submission date
		public Date latest;

		//Initialize two Red and black trees (Self - balanced binary search trees)

		//The first one uses Dates as a Key and a submission as its value.
		public TreeMap<Date,  Handin> sortedDates;

		//The second one uses Grade as a Key and another TreeMap as its values.
		//The inner Tree takes the dates as its key and the coorisponding submission as value
		//So the second one is a nested tree. It sort the grade first, and then sort the dates that has the same grade
		public TreeMap<Integer, TreeMap<Date,Handin>> sortedGrades;
	}



	/**
	 * Find the highest grade of any submission by this student
	 *
	 * @param unikey
	 *            The student to filter on
	 * @return the best grade by this student, or null if they have made no
	 *         submissions
	 * @throws IllegalArgumentException
	 *             if the argument is null
	 */
	@Override
	public Integer getBestGrade(String unikey) throws IllegalArgumentException{
		//if the argument is null, throw an exception
		if(unikey == null){
			throw new IllegalArgumentException("The argument does not exist");
		}

		//Find the student by searching his/her unikey
		//This Operation takes O(log(n)) time.
		Details thisStudent = this.students.get(unikey);

		//if the unikey doesn't exist, the studentt didn't make any submission
		if(thisStudent == null){
			return null;
		}

		//If he doesn't have a best grade, obviousely he made no submissions
		//Getting the value for variable directly takes O(1)
		if(thisStudent.bestGrade == null){
			return null;
		}
		else{
			return thisStudent.bestGrade;
		}
		// This function takes O(log(n)) + O(1) = O(log(n)) time;
	}



	/**
	 * The most recent submission for a given student
	 *
	 * @param unikey
	 *            The student to filter on
	 * @return Submission made most recently by that student, or null if the
	 *         student has made no submissions
	 * @throws IllegalArgumentException
	 *             if the argument is null
	 */
	@Override
	public Submission getSubmissionFinal(String unikey) throws IllegalArgumentException{
		//if the argument is null, throw an exception
		if(unikey == null){
			throw new IllegalArgumentException("The argument does not exist");
		}

		Details thisStudent = this.students.get(unikey);
		//Find the student by searching his/her unikey
		//This Operation takes O(log(n)) time.
		if(thisStudent == null){
			return null;
		}

		//if the student does not have a last submission, he/she made no submission
		if(thisStudent.latestGrade == null){
			return null;
		}
		else{
			//Else, find the last submission by searching date in BST
			//the date is already stored in latest. so find it takes O(log(n))
			return thisStudent.sortedDates.get(thisStudent.latest);
		}
		// This function takes O(log(n)) time;
	}



	/**
	 * The most recent submission for a given student, prior to a given time
	 *
	 * @param unikey
	 *            The student to filter on
	 * @param deadline
	 *            The deadline after which no submissions are considered
	 * @return Submission made most recently by that student, or null if the
	 *         student has made no submissions
	 * @throws IllegalArgumentException
	 *             if the argument is null
	 */
	@Override
	public Submission getSubmissionBefore(String unikey, Date deadline) throws IllegalArgumentException{
		//if the argument is null, throw an exception
		if(unikey == null||deadline == null){
			throw new IllegalArgumentException("The argument does not exist");
		}

		//Find the student by searching his/her unikey
		//This Operation takes O(log(n)) time.
		Details thisStudent = this.students.get(unikey);
		if(thisStudent == null){
			return null;
		}

		if(thisStudent.latestGrade == null){
			return null;
		}
		else{
			//Find the last submission date before the given deadline
			//by searching the highest key equals or before the given date
			//This Operation takes O(log(n)) time.
			Date result = thisStudent.sortedDates.floorKey(deadline);
			if(result == null){
				return null;
			}else{
				//To return the submission, search the date in the BST
				//This Operation takes O(log(n)) time.
				return thisStudent.sortedDates.get(result);
			}
		}
		// This function takes (O((log(n) + log(n))*log(n)))= O(log(n)^2) time;
	}




	/**
	 * Add a new submission
	 *
	 * For simplicity, you may assume that all submissions have unique times
	 *
	 * @param unikey
	 * @param timestamp
	 * @param grade
	 * @return the Submission object that was created
	 * @throws IllegalArgumentException
	 *             if any argument is null
	 */
	@Override
	public Submission add(String unikey, Date timestamp, Integer grade) throws IllegalArgumentException{
		// TODO Implement this, ideally in better than O(n)
		//if the argument is null, throw an exception
		if(unikey == null || timestamp == null || grade == null){
			throw new IllegalArgumentException("The argument does not exist");
		}
		//Create a submission
		Handin submission = new Handin(unikey, timestamp, grade);

		//if the student has not made any submission before
		if(this.students.get(unikey) == null){

			//Create a Detail object for this student
			Details dts = new Details();

			//Initialize the variables
			dts.bestGrade = grade;
			dts.latestGrade = grade;
			dts.latest = timestamp;

			//Initialize the trees
			dts.sortedDates = new TreeMap<Date,  Handin>();
			dts.sortedGrades = new TreeMap<Integer, TreeMap<Date, Handin>>();

			//Put the data in the sortedDates, the returned oldSubPutIn is null;
			Handin oldSub = dts.sortedDates.put(timestamp, submission);

			//Create the inner tree for sortedGrades
			TreeMap<Date,Handin> sameGrades = new TreeMap<Date,Handin>();
			//Put the information of date submission in the inner tree
			Handin subPutIn = sameGrades.put(timestamp, submission);
			//Connect the tree to the outter tree sortedGrades
			TreeMap<Date,Handin> mapPutIn = dts.sortedGrades.put(grade, sameGrades);
			//Connect the tree to the otter-most tree.
			Details oldDetailPutIn = this.students.put(unikey, dts);

			//these Operation takes O(1) time.
		}
		else{
			//find the student(unikey)
			//This takes O(log(n));
			Details thisStudent = this.students.get(unikey);
			//Update the bestGrade
			if(thisStudent.bestGrade < grade){
				thisStudent.bestGrade = grade;
			}
			//Update the latest date
			if(timestamp.compareTo(thisStudent.latest) >= 0){
				thisStudent.latestGrade = grade;
				thisStudent.latest = timestamp;
			}
			//Add the new data in the tree sortedDates
			Handin oldSubPutIn = thisStudent.sortedDates.put(timestamp, submission);

			//Add the new data in the tree sortedGrades(nested tree);
			//if the grade doesn't exist
			if(!thisStudent.sortedGrades.containsKey(grade)){
				//check takes O(loh(n))
				//In the inner tree, if the key Date doesn't exist
				TreeMap<Date,Handin> sameGrades = new TreeMap<Date,Handin>();

				//Put the data in the inner tree
				Handin subPutIn = sameGrades.put(timestamp, submission);
				//Connect inner tree to the outter tree, return null
				TreeMap<Date,Handin> mapPutIn = thisStudent.sortedGrades.put(grade, sameGrades);
			}
			else{
				//if the grade exists, the corresponding inner tree must exists
				//find the grade
				//this takes O(log(n))
				TreeMap<Date,Handin> sameGrades = thisStudent.sortedGrades.get(grade);
				//"put" takes O(1)
				Handin mapPutIn = sameGrades.put(timestamp, submission);
			}

		}
		return submission;
		//the fuction uses O(log(n)^3);
	}


	/**
	 * Remove a submission
	 *
	 * For simplicity, you may assume that all submissions have unique times
	 *
	 * @param submission
	 *            The Submission to remove
	 * @throws IllegalArgumentException
	 *             if the argument is null
	 */
	@Override
	public void remove(Submission submission) throws IllegalArgumentException{
		// TODO Implement this, ideally in better than O(n)
		if(submission == null){
			throw new IllegalArgumentException("The argument does not exist");
		}
		//get the values form the submission object
		String removeUnikey = submission.getUnikey();
		Date removeTimestamp = submission.getTime();
		Integer removeGrade = submission.getGrade();

		Details thisStudent = this.students.get(removeUnikey);

		//Remove the date object from the sortedDate tree
		Handin removeSubmission = thisStudent.sortedDates.remove(removeTimestamp);
		//Update the variables
		//This will take O(log(n))
		thisStudent.latest =  thisStudent.sortedDates.lastKey();
		//This will take O(log(n))
		thisStudent.latestGrade = thisStudent.sortedDates.get(thisStudent.latest).getGrade();

		//Remove the object from the sortedGrades tree
		TreeMap<Date,Handin> sameGrades = thisStudent.sortedGrades.get(removeGrade);
		Handin removeChangeBG = sameGrades.remove(removeTimestamp);
		//if this inner tree doesn't exist, we can delete it.
		if(sameGrades.size()==0){
			thisStudent.sortedGrades.remove(removeGrade);
		}
		//update the bestgrade;
		if(	removeChangeBG.getGrade() == thisStudent.bestGrade){
			//
			thisStudent.bestGrade = thisStudent.sortedGrades.lastKey();
		}
		// O(log(n)^3)
	}



	/**
	 * Get all the students who achieved the highest grade (in any of their
	 * submissions).
	 *
	 * For example, if the highest grade achieved by any student was 93, then
	 * this would return a list of all the students who have made a submission
	 * graded at 93.
	 *
	 * If no submissions have been made, then return an empty list.
	 *
	 * @return a list of unikeys
	 */
	@Override
	public List<String> listTopStudents() {

		// (you may ignore the length of the list in the analysis)
		//Inplement an ArrayList
		List<String> topStudents = new ArrayList<String>();

		//Set best to an impossible number
		int best = -1;

		//For each entry<Unikey, Details(includes bestGrade and latestGrade)>, compare each one with the best and put the good students in the ArrayList
		//This will take O(n) time beause it tranverse all the entry in the BST
		for(Map.Entry<String, Details> entry: this.students.entrySet()){

			//Get Details form each entry
			Details currentDetails = entry.getValue();

			//Do the check
			//Because it iterate through all the elements so it is O(n)
			if(currentDetails.bestGrade > best){

				//if the grade is better than the best grade, clear the ArrayList and update the "best" variable
				best = currentDetails.bestGrade;
				topStudents.clear();

				//add this student in the ArrayList
				topStudents.add(entry.getKey());
			}
			else if(currentDetails.bestGrade == best){
				//add the studnet in the ArrayList who acheieve the best score.
				topStudents.add(entry.getKey());
			}
		}
		return topStudents;
	}
	//This function takes O(n) time



	/**
	 * Get all the students whose most recent submissions have lower grades than
	 * their best submissions
	 *
	 * @return a list of unikeys
	 */
	@Override
	public List<String> listRegressions() {

		//Inplement an ArrayList
		List<String> regressionStudents = new ArrayList<String>();

		//For each entry<Unikey, Details(includes bestGrade and latestGrade)>, compare each one and put them in the ArrayList
		//This will take O(n) time beause it tranverse all the entry in the BST
		for(Map.Entry<String, Details> entry: this.students.entrySet()){
			//Get Details form each entry
			Details currentDetails = entry.getValue();
			//Do the check
			if(currentDetails.bestGrade > currentDetails.latestGrade){
				//Add it into the ArrayList
				regressionStudents.add(entry.getKey());
			}
		}
		return regressionStudents;
	}

}
