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

public class mytest{

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
	private SubmissionHistory buildExample() {
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


	//does getSubmissionsBefore work correctly if the submissions were not added in order
	@Test(timeout = 100)
	public void testGetBefore_AddUnsorted() {
		SubmissionHistory mySub = new Assignment();

		Submission b = testHelperAdd(mySub, "aaaa1111", new Date(400000), 10);
		Submission j = testHelperAdd(mySub, "aaaa1111", new Date(2000000), 68);
		Submission c = testHelperAdd(mySub, "bbbb1111", new Date(600000), 57);
		Submission e = testHelperAdd(mySub, "aaaa1111", new Date(1000000), 68);
		Submission a = testHelperAdd(mySub, "aaaa1111", new Date(200000), 56);
		Submission h = testHelperAdd(mySub, "bbbb1111", new Date(1600000), 23);
		Submission f = testHelperAdd(mySub, "aaaa1111", new Date(1200000), 80);
		Submission d = testHelperAdd(mySub, "aaaa1111", new Date(800000), 23);
		Submission g = testHelperAdd(mySub, "bbbb1111", new Date(1400000), 40);
		Submission i = testHelperAdd(mySub, "aaaa1111", new Date(1800000), 50);

		testHelperEquals(a, mySub.getSubmissionBefore("aaaa1111", new Date(300000)));
		assertNull(mySub.getSubmissionBefore("bbbb1111", new Date(300000)));

		testHelperEquals(b, mySub.getSubmissionBefore("aaaa1111", new Date(400000)));
		assertNull(mySub.getSubmissionBefore("bbbb1111", new Date(400000)));

		testHelperEquals(b, mySub.getSubmissionBefore("aaaa1111", new Date(700000)));
		testHelperEquals(c, mySub.getSubmissionBefore("bbbb1111", new Date(700000)));

		testHelperEquals(d, mySub.getSubmissionBefore("aaaa1111", new Date(900000)));
		testHelperEquals(c, mySub.getSubmissionBefore("bbbb1111", new Date(900000)));

		testHelperEquals(e, mySub.getSubmissionBefore("aaaa1111", new Date(1100000)));
		testHelperEquals(c, mySub.getSubmissionBefore("bbbb1111", new Date(1100000)));

		testHelperEquals(f, mySub.getSubmissionBefore("aaaa1111", new Date(1300000)));
		testHelperEquals(c, mySub.getSubmissionBefore("bbbb1111", new Date(1300000)));

		testHelperEquals(f, mySub.getSubmissionBefore("aaaa1111", new Date(1500000)));
		testHelperEquals(g, mySub.getSubmissionBefore("bbbb1111", new Date(1500000)));

		testHelperEquals(f, mySub.getSubmissionBefore("aaaa1111", new Date(1700000)));
		testHelperEquals(h, mySub.getSubmissionBefore("bbbb1111", new Date(1700000)));

		testHelperEquals(i, mySub.getSubmissionBefore("aaaa1111", new Date(1900000)));
		testHelperEquals(h, mySub.getSubmissionBefore("bbbb1111", new Date(1900000)));

		testHelperEquals(j, mySub.getSubmissionBefore("aaaa1111", new Date(2100000)));
		testHelperEquals(h, mySub.getSubmissionBefore("bbbb1111", new Date(2100000)));

	}

	@Test(timeout = 100)
	public void testGetFinal_AddUnsorted() {
		SubmissionHistory mySub = new Assignment();

		Submission b = testHelperAdd(mySub, "aaaa1111", new Date(400000), 10);
		testHelperEquals(b, mySub.getSubmissionFinal("aaaa1111"));
		assertNull(mySub.getSubmissionFinal("bbbb1111"));

		Submission j = testHelperAdd(mySub, "aaaa1111", new Date(2000000), 68);
		testHelperEquals(j, mySub.getSubmissionFinal("aaaa1111"));
		assertNull(mySub.getSubmissionFinal("bbbb1111"));

		Submission c = testHelperAdd(mySub, "bbbb1111", new Date(600000), 57);
		testHelperEquals(j, mySub.getSubmissionFinal("aaaa1111"));
		testHelperEquals(c, mySub.getSubmissionFinal("bbbb1111"));

		testHelperAdd(mySub, "aaaa1111", new Date(1000000), 68);
		testHelperEquals(j, mySub.getSubmissionFinal("aaaa1111"));
		testHelperEquals(c, mySub.getSubmissionFinal("bbbb1111"));

		testHelperAdd(mySub, "aaaa1111", new Date(200000), 56);
		testHelperEquals(j, mySub.getSubmissionFinal("aaaa1111"));
		testHelperEquals(c, mySub.getSubmissionFinal("bbbb1111"));

		Submission h = testHelperAdd(mySub, "bbbb1111", new Date(1600000), 23);
		testHelperEquals(j, mySub.getSubmissionFinal("aaaa1111"));
		testHelperEquals(h, mySub.getSubmissionFinal("bbbb1111"));

		testHelperAdd(mySub, "aaaa1111", new Date(1200000), 80);
		testHelperEquals(j, mySub.getSubmissionFinal("aaaa1111"));
		testHelperEquals(h, mySub.getSubmissionFinal("bbbb1111"));

		testHelperAdd(mySub, "aaaa1111", new Date(800000), 23);
		testHelperEquals(j, mySub.getSubmissionFinal("aaaa1111"));
		testHelperEquals(h, mySub.getSubmissionFinal("bbbb1111"));

		testHelperAdd(mySub, "bbbb1111", new Date(1400000), 40);
		testHelperEquals(j, mySub.getSubmissionFinal("aaaa1111"));
		testHelperEquals(h, mySub.getSubmissionFinal("bbbb1111"));

		testHelperAdd(mySub, "aaaa1111", new Date(1800000), 50);
		testHelperEquals(j, mySub.getSubmissionFinal("aaaa1111"));
		testHelperEquals(h, mySub.getSubmissionFinal("bbbb1111"));
	}

	//does getBestSubmission work correctly as we remove submissions
	@Test(timeout = 100)
	public void testGetBest_Remove() {
		SubmissionHistory mySub = new Assignment();
		Submission b = testHelperAdd(mySub, "aaaa1111", new Date(400000), 10);
		Submission j = testHelperAdd(mySub, "aaaa1111", new Date(2000000), 68);
		Submission c = testHelperAdd(mySub, "bbbb1111", new Date(600000), 68);
		Submission e = testHelperAdd(mySub, "aaaa1111", new Date(1000000), 68);
		Submission a = testHelperAdd(mySub, "aaaa1111", new Date(200000), 56);
		Submission h = testHelperAdd(mySub, "bbbb1111", new Date(1600000), 23);
		Submission f = testHelperAdd(mySub, "aaaa1111", new Date(1200000), 80);
		Submission d = testHelperAdd(mySub, "aaaa1111", new Date(800000), 23);
		Submission g = testHelperAdd(mySub, "bbbb1111", new Date(1400000), 40);
		Submission i = testHelperAdd(mySub, "aaaa1111", new Date(1800000), 50);

		assertEquals(new Integer(80), mySub.getBestGrade("aaaa1111"));
		assertEquals(new Integer(68), mySub.getBestGrade("bbbb1111"));

		mySub.remove(i);
		assertEquals(new Integer(80), mySub.getBestGrade("aaaa1111"));
		assertEquals(new Integer(68), mySub.getBestGrade("bbbb1111"));

		mySub.remove(f);
		assertEquals(new Integer(68), mySub.getBestGrade("aaaa1111"));
		assertEquals(new Integer(68), mySub.getBestGrade("bbbb1111"));

		mySub.remove(j);
		assertEquals(new Integer(68), mySub.getBestGrade("aaaa1111"));
		assertEquals(new Integer(68), mySub.getBestGrade("bbbb1111"));

		mySub.remove(e);
		assertEquals(new Integer(56), mySub.getBestGrade("aaaa1111"));
		assertEquals(new Integer(68), mySub.getBestGrade("bbbb1111"));
	}

	//testing a larger example with the regressions method
	@Test(timeout = 100)
	public void testMoreRegressions() {
		SubmissionHistory mySub = new Assignment();
		Submission a1 = testHelperAdd(mySub, "a", new Date(100000), 10);
		Submission b1 = testHelperAdd(mySub, "b", new Date(100000), 10);
		Submission c1 = testHelperAdd(mySub, "c", new Date(100000), 10);
		Submission d1 = testHelperAdd(mySub, "d", new Date(100000), 10);
		Submission e1 = testHelperAdd(mySub, "e", new Date(100000), 10);
		Submission f1 = testHelperAdd(mySub, "f", new Date(100000), 10);

		Submission a2 = testHelperAdd(mySub, "a", new Date(200000), 10);
		Submission b2 = testHelperAdd(mySub, "b", new Date(200000), 5); //regression
		Submission c2 = testHelperAdd(mySub, "c", new Date(200000), 5); //regression
		Submission d2 = testHelperAdd(mySub, "d", new Date(200000), 15);
		Submission e2 = testHelperAdd(mySub, "e", new Date(200000), 15);
		Submission f2 = testHelperAdd(mySub, "f", new Date(200000), 5); //regression

		List<String> studentsExpected = Arrays.asList("b","c","f");
		List<String> studentsActual = mySub.listRegressions();

		//sort both lists, to make it easier to compare them
		Collections.sort(studentsActual);
		Collections.sort(studentsActual);

		assertEquals(studentsExpected, studentsActual);

	}

	//testing a larger example with the regressions method
	@Test(timeout = 100)
	public void testMoreTopStudents() {
		SubmissionHistory mySub = new Assignment();
		Submission a1 = testHelperAdd(mySub, "a", new Date(100000), 10);
		Submission b1 = testHelperAdd(mySub, "b", new Date(100000), 10);
		Submission c1 = testHelperAdd(mySub, "c", new Date(100000), 10);
		Submission d1 = testHelperAdd(mySub, "d", new Date(100000), 10);
		Submission e1 = testHelperAdd(mySub, "e", new Date(100000), 10);
		Submission f1 = testHelperAdd(mySub, "f", new Date(100000), 15); //best
		Submission a2 = testHelperAdd(mySub, "a", new Date(200000), 10);
		Submission b2 = testHelperAdd(mySub, "b", new Date(200000), 5);
		Submission c2 = testHelperAdd(mySub, "c", new Date(200000), 5);
		Submission d2 = testHelperAdd(mySub, "d", new Date(200000), 15); //best
		Submission e2 = testHelperAdd(mySub, "e", new Date(200000), 15); //best
		Submission f2 = testHelperAdd(mySub, "f", new Date(200000), 5);

		//top grade is 15
		List<String> studentsExpected = Arrays.asList("d","e","f");
		List<String> studentsActual = mySub.listTopStudents();

		//sort both lists, to make it easier to compare them
		Collections.sort(studentsActual);
		Collections.sort(studentsActual);

		assertEquals(studentsExpected, studentsActual);
	}

	//testing an unusually high grade
	@Test(timeout = 100)
	public void testReallyHighGrade() {
		SubmissionHistory mySub = new Assignment();
		Submission a = testHelperAdd(mySub, "a", new Date(100000), 10);
		Submission b = testHelperAdd(mySub, "a", new Date(200000), 10000);
		Submission c = testHelperAdd(mySub, "a", new Date(300000), 50);

		assertEquals(new Integer(10000), mySub.getBestGrade("a"));
		testHelperEquals(c, mySub.getSubmissionFinal("a"));
		testHelperEquals(b, mySub.getSubmissionBefore("a", new Date(250000)));
	}


	/* ****************************************************************
	 * That's the end of the visible tests. There will be hidden tests,
	 * accounting for up to half the automarking grade!
	 *
	 * You will need to do more testing yourself.
	 *
	 * Some ideas:
	 * - test all the interface methods with the remove method
	 * - test all the interface methods with a mix of removes and adds
	 * - do (much) larger tests with more students and many more submissions
	 * - edge cases
	 * **************************************************************** */

}
