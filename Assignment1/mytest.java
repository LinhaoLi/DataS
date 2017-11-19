import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Mytest{

	// Set up JUnit to be able to check for expected exceptions
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// This will make it a bit easier for us to make Date objects
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	// This will make it a bit easier for us to make Date objects
	private static Date getDate(String s) {
		try {
			return df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			fail("The test case is broken, invalid SimpleDateFormat parse");
		}
		// unreachable
		return null;
	}

	// helper method to compare two Submissions using assertions

	// Helper method to build the trivial example submission mySub
	/*private SubmissionHistory buildExample() {
		SubmissionHistory mySub = new Assignment();
		// submission A:
		mySub.add("aaaa1234", getDate("1970/01/01 00:00:01"), 66);
		// submission B:
		mySub.add("bbbb1234", getDate("1970/01/02 00:00:01"), 86);
		// submission C:
		mySub.add("cccc1234", getDate("1970/01/01 00:00:05"), 73);
		// submission D:
		mySub.add("aaaa1234", getDate("1970/01/01 00:00:05"), 40);
		// submission D:
		mySub.add("cccc1234", getDate("1970/01/01 00:00:00"), 40);
		// submission D:
		mySub.add("aaaa1234", getDate("1970/01/08 18:00:00"), 40);
		// submission D:
		mySub.add("cccc1234", getDate("1970/05/02 18:00:00"), 73);
		// submission D:
		mySub.add("bbbb1234", getDate("1970/10/03 18:00:00"), 40);
		// submission D:
		mySub.add("cccc1234", getDate("1970/09/03 18:00:00"), 40);
		// submission D:
		mySub.add("bbbb1234", getDate("1970/03/03 18:00:00"), 40);
		return mySub;
	}
*/



	private static void testHelperEquals(Submission expected, Submission actual) {
		assertEquals(expected.getUnikey(), actual.getUnikey());
		assertEquals(expected.getTime(), actual.getTime());
		assertEquals(expected.getGrade(), actual.getGrade());
	}

	// helper method to compare two Submissions using assertions
	private static void testHelperEquals(String unikey, Date timestamp, Integer grade, Submission actual) {
		assertEquals(unikey, actual.getUnikey());
		assertEquals(timestamp, actual.getTime());
		assertEquals(grade, actual.getGrade());
	}

	// helper method that adds a new appointment AND checks the return value is correct
	private static Submission testHelperAdd(SubmissionHistory history, String unikey, Date timestamp, Integer grade) {
		Submission s = history.add(unikey, timestamp, grade);
		testHelperEquals(unikey, timestamp, grade, s);
		return s;
	}


	//does getSubmissionsBefore work correctly if the submissions were not added in order
	@Test(timeout = 100)
	public void testGetBefore_Remove() {
		SubmissionHistory mySub = new Assignment();

		Submission b = testHelperAdd(mySub, "aaaa1111", new Date(0), 10);
		Submission j = testHelperAdd(mySub, "aaaa1111", new Date(3), 68);
		Submission f = testHelperAdd(mySub, "aaaa1111", new Date(2), 0);
		Submission e = testHelperAdd(mySub, "aaaa1111", new Date(100000000), 68);

		Submission c = testHelperAdd(mySub, "bbbb1111", new Date(60000000), 57);
		Submission h = testHelperAdd(mySub, "bbbb1111", new Date(160000000), 23);

		mySub.remove(j);

		testHelperEquals(f, mySub.getSubmissionBefore("aaaa1111", new Date(2)));
		testHelperEquals(h, mySub.getSubmissionBefore("bbbb1111", new Date(160000001)));
		testHelperEquals(f, mySub.getSubmissionBefore("aaaa1111", new Date(1000000)));


	}


	//does getBestSubmission work correctly as we remove submissions
	@Test(timeout = 100)
	public void testGetBest_Remove() {
		SubmissionHistory mySub = new Assignment();
		Submission b = testHelperAdd(mySub, "aaaa1111", new Date(0), 10);
		Submission j = testHelperAdd(mySub, "aaaa1111", new Date(2), 68);
		Submission c = testHelperAdd(mySub, "bbbb1111", new Date(60000000), 57);
		Submission e = testHelperAdd(mySub, "aaaa1111", new Date(100000000), 68);
		Submission h = testHelperAdd(mySub, "bbbb1111", new Date(160000000), 23);
		Submission f = testHelperAdd(mySub, "aaaa1111", new Date(0), 0);


		mySub.remove(j);
		assertEquals(new Integer(68), mySub.getBestGrade("aaaa1111"));
		assertEquals(new Integer(57), mySub.getBestGrade("bbbb1111"));

		mySub.remove(e);
		assertEquals(new Integer(10), mySub.getBestGrade("aaaa1111"));
		assertEquals(new Integer(57), mySub.getBestGrade("bbbb1111"));
	}



	//testing a larger example with the regressions method
	@Test(timeout = 100)
	public void testNoRegressions() {
		SubmissionHistory mySub = new Assignment();
		Submission a1 = testHelperAdd(mySub, "a", new Date(100000), 10);
		Submission b1 = testHelperAdd(mySub, "b", new Date(100000), 10);
		Submission c1 = testHelperAdd(mySub, "c", new Date(100000), 10);
		Submission d1 = testHelperAdd(mySub, "d", new Date(100000), 10);
		Submission e1 = testHelperAdd(mySub, "e", new Date(100000), 10);
		Submission f1 = testHelperAdd(mySub, "f", new Date(100000), 10);

		Submission a2 = testHelperAdd(mySub, "a", new Date(200000), 10);
		Submission b2 = testHelperAdd(mySub, "b", new Date(200000), 10);
		Submission c2 = testHelperAdd(mySub, "c", new Date(200000), 10);
		Submission d2 = testHelperAdd(mySub, "d", new Date(200000), 10);
		Submission e2 = testHelperAdd(mySub, "e", new Date(200000), 10);
		Submission f2 = testHelperAdd(mySub, "f", new Date(200000), 10);
		List<String> studentsExpected = Arrays.asList();
		List<String> studentsActual = mySub.listRegressions();

		//sort both lists, to make it easier to compare them
		Collections.sort(studentsActual);
		Collections.sort(studentsActual);

		assertEquals(studentsExpected, studentsActual);

	}

	//testing a larger example with the regressions method
	@Test(timeout = 100)
	public void testAllTopStudents() {
		SubmissionHistory mySub = new Assignment();
		Submission a1 = testHelperAdd(mySub, "a", new Date(100000), 10);
		Submission b1 = testHelperAdd(mySub, "b", new Date(100000), 10);
		Submission c1 = testHelperAdd(mySub, "c", new Date(100000), 10);
		Submission d1 = testHelperAdd(mySub, "d", new Date(100000), 10);
		Submission e1 = testHelperAdd(mySub, "e", new Date(100000), 10);
		Submission f1 = testHelperAdd(mySub, "f", new Date(100000), 10);
		Submission a2 = testHelperAdd(mySub, "a", new Date(200000), 10);
		Submission b2 = testHelperAdd(mySub, "b", new Date(200000), 7);
		Submission c2 = testHelperAdd(mySub, "c", new Date(200000), 8);
		Submission d2 = testHelperAdd(mySub, "d", new Date(200000), 10);
		Submission e2 = testHelperAdd(mySub, "e", new Date(200000), 10);
		Submission f2 = testHelperAdd(mySub, "f", new Date(200000), 0);

		//top grade is 15
		List<String> studentsExpected = Arrays.asList("a","b","c","d","e","f");
		List<String> studentsActual = mySub.listTopStudents();

		//sort both lists, to make it easier to compare them
		Collections.sort(studentsActual);
		Collections.sort(studentsActual);

		assertEquals(studentsExpected, studentsActual);
	}

	//testing an unusually high grade
	@Test(timeout = 100)
	public void testBoundaryGrade() {
		SubmissionHistory mySub = new Assignment();
		Submission a = testHelperAdd(mySub, "a", new Date(100000), 0);
		assertEquals(new Integer(0), mySub.getBestGrade("a"));

		Submission b = testHelperAdd(mySub, "a", new Date(200000), 10000);
		Submission c = testHelperAdd(mySub, "a", new Date(300000), 50);

		assertEquals(new Integer(10000), mySub.getBestGrade("a"));

		testHelperEquals(c, mySub.getSubmissionFinal("a"));
		testHelperEquals(b, mySub.getSubmissionBefore("a", new Date(250000)));
	}


}
