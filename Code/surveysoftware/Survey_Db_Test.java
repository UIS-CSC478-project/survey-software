/**
 * 
 */
package surveysoftware.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import surveysoftware.survey_db;

public class Survey_Db_Test {

	Survey_Db db = new Survey_Db();
	String surveyname = "Test Survey";
	String questionName = "What is your name?";
	
	private Connection getConnection() throws Exception
	{
		Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");        
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#check4Db()}.
	 */
	@Test
	public final void testCheck4Db() {
		Assert.assertTrue(db.check4Db());
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#createDb()}.
	 */
	@Test
	public final void testCreateDb() {
		db.deleteDB();
		db.createDb();
		Assert.assertTrue(db.check4Db());
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#addNewSurvey(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testAddNewSurvey() throws Exception {
		
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			c = getConnection();
           
            //make sure the survey name is not in the database before this test is started.
            stmt = c.createStatement();
            stmt.executeUpdate("DELETE FROM SURVEY WHERE SURVEY_NAME = '" + surveyname + "'");
            db.addNewSurvey("Test Survey");
            
            rs = stmt.executeQuery("SELECT COUNT(*) FROM SURVEY WHERE SURVEY_NAME = '" + surveyname + "'");
            int result = rs.getInt(1);
            
            //Assert that our result contains only one record
            Assert.assertTrue(result == 1);
		}
		finally
		{
			if(rs != null)
				rs.close();
			
			if(stmt!=null)
				stmt.close();
			if(c!=null)
				c.close();
		}
		
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#addNewQuestionWithAnswer(java.util.ArrayList, int)}.
	 */
	@Test
	public final void testAddNewQuestionWithAnswer() throws Exception {
		//if(1==1) return;
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> qa = new ArrayList<String>();
		
		
		try
		{
			c = getConnection();
            
            stmt = c.createStatement();
			stmt.executeUpdate("DELETE FROM SURVEY_QUESTION");
			stmt.executeUpdate("DELETE FROM POSSIBLE_ANSWERS");
			rs = stmt.executeQuery("SELECT S_ID FROM SURVEY WHERE SURVEY_NAME = '" + surveyname + "'");
			String surveyid = rs.getString(1);
			
			//Need to close these because another function db.addNewQuestionWithAnswer 
			//will create a new connection while this connection is still open. That is not allowed.
			stmt.close();
			c.close();
			
			System.out.println("Survey id is " + surveyid);
			qa.add(surveyid); //Survey ID
			qa.add(questionName); //Question Text
			qa.add("c"); //Correct answer index
			
			qa.add("a");	//Answer 1 index
			qa.add("Java"); //Answer 1
			
			qa.add("b");	//Answer 2 index
			qa.add("C#");	//Answer 2
			
			qa.add("c");	//Answer 3 index
			qa.add("Python"); //Answer 3
			
			qa.add("d");	//Answer 4 index
			qa.add("C++");	//Answer 4
			
			
			int numanswers = 4;	//We have 4 answers for this question
			
			db.addNewQuestionWithAnswer(qa, numanswers);

			//get a fresh connection because it was closed previously
			c = getConnection();
            
            stmt = c.createStatement();
			//Check if the question was added
			rs = stmt.executeQuery("SELECT COUNT(*) FROM SURVEY_QUESTION WHERE FK_S_ID = '" + surveyid + "' AND QUESTION_TEXT = '" + qa.get(1) + "'");
			int output = rs.getInt(1);
			
			Assert.assertTrue(output == 1);
			
			//Check if there are 4 answers associated with this question of this survey			
			output = stmt.executeQuery(
						"select count(*) from possible_answers where fk_q_id = (select q_id from survey_question where question_text = '" + qa.get(1) + "' and fk_s_id = (select s_id from survey where survey_name = '" + surveyname +"'))").getInt(1);
			Assert.assertTrue(output == numanswers);
			
			
			//Check if the correct answer index is properly set for this question of this survey
			String correct_ans = stmt.executeQuery("select question_answer from survey_question where FK_S_ID = '" + surveyid + "' AND QUESTION_TEXT = '" + qa.get(1) + "'").getString(1);
			Assert.assertEquals(qa.get(2), correct_ans);
			
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		finally
		{
			if(stmt!=null)
				stmt.close();
			if (c!=null)
				c.close();
		}
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#addNewRespondent()}.
	 */
	@Test
	public final void testAddNewRespondent() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#addResults(int, java.lang.String)}.
	 */
	@Test
	public final void testAddResultsIntString() throws Exception {
		Connection c = null;
		
		try
		{
			c = getConnection();
			c.createStatement().executeUpdate("DELETE FROM ANSWERS");
			int quesID = c.createStatement().executeQuery("SELECT Q_ID FROM SURVEY_QUESTION WHERE QUESTION_TEXT = '" + questionName + "'").getInt(1);
			String answer = "a";
			c.close();
			
			db.addResults(quesID, answer);
			
			//See if the answer was added to the database
			c = getConnection();
			
			int output = c.createStatement().executeQuery("SELECT 1 FROM ANSWERS WHERE FK_Q_ID = " + Integer.toString(quesID) + 
					" AND FK_PA_ID = (SELECT PA_ID FROM POSSIBLE_ANSWERS WHERE LETTER = '" + answer + "' AND FK_Q_ID = " + Integer.toString(quesID) + ") ").getInt(1);
			
			Assert.assertEquals(1, output);
		}
		finally
		{
			if(c!=null)
				c.close();
		}
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#addResults(int, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testAddResultsIntStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#deleteDB()}.
	 * This test will first delete the database using deleteDB function
	 * and then verify that the file has been deleted.
	 */
	@Test
	public final void testDeleteDB() {
		db.deleteDB();
		Assert.assertTrue(!db.check4Db());
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#getSurveyNames()}.
	 * This test will count the number of items in the array list returned by the function GetSurveyNames()
	 * And compare that with COUNT(*) result. If they are same then we can be sure that the function returns
	 * all the surveys.
	 */
	@Test
	public final void testGetSurveyNames() throws Exception{
		Connection c = null;
		try
		{
			c = getConnection();
			int expectedsurveys = c.createStatement().executeQuery("SELECT COUNT(*) FROM SURVEY").getInt(1);
			c.close();
			
			ArrayList<String> surveys = db.getSurveyNames();
			int actualsurveys = surveys.size();
			
			Assert.assertEquals(expectedsurveys, actualsurveys);
			
			System.out.println("--GetSurveyNames() Output--");
			for(String survey: surveys)
			{
				System.out.println(survey);
			}
			System.out.println("--GetSurveyNames() Output--");
		}
		finally
		{
			if(c!=null)
				c.close();
		}
	}

	/**
	 * Test method for {@link surveysoftware.survey_db#getNumQuestions(int)}.
	 * This test will verify that the number of question are returned correctly by the 
	 * function GetNumQuestions
	 */
	@Test
	public final void testGetNumQuestions() throws Exception {
		Connection c = null;
		try
		{
			c = getConnection();
			int surveyid = c.createStatement().executeQuery("SELECT S_ID FROM SURVEY WHERE SURVEY_NAME = '" + surveyname + "'").getInt(1);
			int expectedquestions = c.createStatement().executeQuery("SELECT COUNT(*) FROM SURVEY_QUESTION WHERE FK_S_ID = " + Integer.toString(surveyid)).getInt(1);
			c.close();
			
			int actualquestions = db.getNumQuestions(surveyid);
			
			Assert.assertEquals(expectedquestions, actualquestions);
			
		}
		finally
		{
			if(c!=null)
				c.close();
		}
	}
	
	@Test
	public final void testgetSurveyQuestionsAnswers() throws Exception
	{
		Connection c = null;
		
		try
		{
			c = getConnection();
			int surveyID = c.createStatement().executeQuery("SELECT S_ID FROM SURVEY WHERE SURVEY_NAME = '" + surveyname + "'").getInt(1);
			System.out.println("--getSurveyQuestionsAnswers() Output--");
			System.out.println("Survey id is: " + Integer.toString(surveyID));
			ArrayList<ArrayList> qa = db.getSurveyQuestionsAnswers(surveyID);
			for(ArrayList<String> q: qa)
			{
				for(String s: q)
				{
					System.out.println(s);
				}
			}
			
			System.out.println("--getSurveyQuestionsAnswers() Output--");
		}
		finally
		{
			if(c!=null)
				c.close();
		}
	}

	
	@Test
	public final void testCheckUniqueSurveyName()
	{
		Assert.assertEquals(false, db.checkUniqueSurveyName(surveyname));
	}
	
	@Test
	public final void testgetSurveyID() throws Exception
	{
		Connection c = null;
		try
		{
			c = getConnection();
			Assert.assertEquals(c.createStatement().executeQuery("SELECT S_ID FROM SURVEY WHERE SURVEY_NAME = '" + surveyname + "'").getInt(1)
					, db.getSurveyID(surveyname));
		}
		finally
		{
			if (c!=null)
				c.close();
		}
	}
}
