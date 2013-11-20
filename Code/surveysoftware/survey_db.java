/*
 * survey_db
 * version 0.0.1
 * 10/8/2013
 * Copyright (c) 2013 Lizard League Software
 */

package surveysoftware;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
//import ____________;
//import ____________;

import javax.swing.JOptionPane;

/**
 *  
  	Class that implements the db_interface.db interface.  This class
    will manipulate the data that goes in and out of the database.
 *
 * @version      
         0.0.1 Oct 8 2013  
 * @author          
         Beth Kieler  */

public class survey_db implements db_interface{
	
	public survey_db(){
		
	}
	
	/* Check for the presence of an existing survey database.*/
	public boolean check4Db(){
		File f = new File("surveydatabase.db");
		return f.exists();	
	}
	
	/* Create a new survey database with tables if the check4Db returns false. */
	public void createDb(){
		System.out.println("in createDb");
		Connection c = null;
		Statement stmt = null;

		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
			stmt = c.createStatement();
		    
			//Survey
			String sql = "CREATE TABLE SURVEY " +
		                 "(S_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
		                 " SURVEY_NAME TEXT  UNIQUE  NOT NULL)"; 
		    stmt.executeUpdate(sql);
		    
		    //Survey_Question
		    sql = "CREATE TABLE SURVEY_QUESTION " +
            	"(Q_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            	" FK_S_ID INTEGER NOT NULL," +
            	" QUESTION_TEXT TEXT NOT NULL," +
            	" QUESTION_ANSWER TEXT," +
            	" FOREIGN KEY (FK_S_ID) REFERENCES SURVEY(S_ID))"; 
			stmt.executeUpdate(sql);
			
			//Possible_Answers
			sql = "CREATE TABLE POSSIBLE_ANSWERS " +
				"(PA_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				" FK_Q_ID INTEGER NOT NULL," +
				" LETTER TEXT NOT NULL," +
				" ANSWER_TEXT TEXT NOT NULL," +
				" FOREIGN KEY (FK_Q_ID) REFERENCES SURVEY_QUESTION(Q_ID))"; 
			stmt.executeUpdate(sql);
					    
			//Answers
			sql = "CREATE TABLE ANSWERS " +
				"(A_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				" FK_Q_ID INTEGER NOT NULL," +
				" FK_PA_ID INTEGER NOT NULL," +
				" OTHER_RESP TEXT," +
				" FOREIGN KEY (FK_Q_ID) REFERENCES SURVEY_QUESTION(Q_ID)," +
				" FOREIGN KEY (FK_PA_ID) REFERENCES POSSIBLE_ANSWERS(PA_ID))"; 
		stmt.executeUpdate(sql);
		    
		    stmt.close();
		    c.close();
		} catch (Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage() );
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		finally {
	    	try{
	  	      stmt.close();
		      c.close();
	    	} catch (Exception e) {
	    		JOptionPane.showMessageDialog(null, e.getMessage());
	    	}
	    }
	}
	
	/* Add a new survey name to the Survey table. */
	public void addNewSurvey(String mysurveyname){
		System.out.println("in addNewSurvey");
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      String sql = "INSERT INTO SURVEY (SURVEY_NAME) " +
	                   "VALUES ('" + mysurveyname + "')"; 
	      stmt.executeUpdate(sql);
	      c.commit();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, "Called from survey_db: Survey name is blank or " + e.getMessage() + ".");
	    } finally {
	    	try{
	  	      stmt.close();
		      c.close();
	    	} catch (Exception e) {
	    		JOptionPane.showMessageDialog(null, e.getMessage());
	    	}
	    }

	}
	
	 /*
	  * Add a new question plus its possible answers to the tables SurveyQuestions
	 * and Answers.
	 * 
	 * questionanswer[1] = survey id
	 * questionanswer[2] = question text
	 * questionanswer [3] = question number (used to sort questions later)
	 * questionanswer [4][6][8][10] = answer letter
	 * questionansswer [5][7][9][11] = answer text
	 */
	public void addNewQuestionWithAnswer(ArrayList questionanswer, int numanswers){
		System.out.println("in addNewQuestionWithAnswer");
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      
	      stmt = c.createStatement();
	      
	      String sql = "INSERT INTO SURVEY_QUESTION  " 
	          + "(FK_S_ID, QUESTION_TEXT, QUESTION_ANSWER) "
	          + "VALUES "
	          + "('" + questionanswer.get(0)   + "',"
	          + "'" + (String) questionanswer.get(1) + "',"
	          + "'" + (String) questionanswer.get(2) + "')";
	      stmt.executeUpdate(sql);
	      rs = stmt.executeQuery("SELECT Q_ID FROM SURVEY_QUESTION WHERE QUESTION_TEXT = '" + questionanswer.get(1) + "'");
	      int newqid = rs.getInt(1); 
	      
    	  sql = "INSERT INTO POSSIBLE_ANSWERS "
    	          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
    	          + "VALUES "
    	          + "('" + newqid   + "',"
    	          + "'" + (String) questionanswer.get(3) + "',"
    	          + "'" + (String) questionanswer.get(4) + "')";
    	  stmt.executeUpdate(sql);
    	  
    	  sql = "INSERT INTO POSSIBLE_ANSWERS "
	          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
	          + "VALUES "
	          + "('" + newqid   + "',"
	          + "'" + (String) questionanswer.get(5) + "',"
	          + "'" + (String) questionanswer.get(6) + "')";
    	  stmt.executeUpdate(sql);  
	      if(numanswers > 2){
	    	  sql = "INSERT INTO POSSIBLE_ANSWERS "
		          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
		          + "VALUES "
		          + "('" + newqid   + "',"
		          + "'" + (String) questionanswer.get(7) + "',"
		          + "'" + (String) questionanswer.get(8) + "')";
	    	  stmt.executeUpdate(sql);  
	      }
	      
	      if(numanswers >3){
	    	  sql = "INSERT INTO POSSIBLE_ANSWERS "
		          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
		          + "VALUES "
		          + "('" + newqid   + "',"
		          + "'" + (String) questionanswer.get(9) + "',"
		          + "'" + (String) questionanswer.get(10) + "')";
	    	  stmt.executeUpdate(sql);  
	      }
	      
	      c.commit();
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    } finally {
	    	try{
		  	      stmt.close();
			      c.close();
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    }
	}
	
	/* Will add new respondent information to the respondent table 
	 * Not implemented in version 1
	 * */
	public void addNewRespondent(){
		
	}
	
	/* Input results from survey_actions into database.
	 * This one is for one letter result with no additional text.
	 * */
	public void addResults(int quesID, String answer){
		System.out.println("in getAddResults");
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		int pAnswerID = getAnswerID(quesID, answer);
		
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      
	      System.out.println("answerID:  " + pAnswerID);
	      System.out.println("quesID:  " + quesID);
	      String sql = "INSERT INTO ANSWERS " 
	          + "(FK_Q_ID, FK_PA_ID) "
	          + "VALUES "
	          + "('" + quesID   + "',"
	          + "'" + pAnswerID + "')";
	      
	      stmt.executeUpdate(sql);
	      
	      c.commit();
	      
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    } finally {
	    	try{
		  	      stmt.close();
			      c.close();
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    }
	}
	
	
	/* Input results from survey_actions into database.
	 * This one is for a letter result with additional text.
	 * */
	public void addResults(int quesID, String answer, String other){
		System.out.println("in addResults with other text");
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		int pAnswerID = getAnswerID(quesID, answer);
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      
	      
	      String sql = "INSERT INTO ANSWERS " 
	          + "(FK_Q_ID, FK_PA_ID, OTHER_RESP) "
	          + "VALUES "
	          + "('" + quesID   + "',"
	          + "'" + pAnswerID + "',"
	          + "'" + other + "')";
	      
	      stmt.executeUpdate(sql);
	      
	      c.commit();
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    } finally {
	    	try{
		  	      stmt.close();
			      c.close();
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    }
	}	
	

	public void deleteDB(){
		File f = new File("surveydatabase.db");
		f.delete();
	}
	
	public ArrayList getSurveyNames(){
		System.out.println("in getSurveyNames");
		ArrayList surveyNames = new ArrayList();
		
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");;
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT SURVEY_NAME FROM SURVEY;" );

	      while(rs.next()){
	    	  
	    	   surveyNames.add(rs.getString("SURVEY_NAME"));
	    	}
	      System.out.println(surveyNames);
	      
	      rs.close();
	      stmt.close();
	      c.close();
	      
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    } finally {
	    	try{
	    		  rs.close();
		  	      stmt.close();
			      c.close();
			      
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    		
		    	}
		  }
		return surveyNames;
	}
	
	public int getNumQuestions(int surveyID){
		System.out.println("in getNumQuestions");
		int numquestions = 0;
		
		Connection c2 = null;
	    Statement stmt2 = null;
	    ResultSet rs2 = null;

	    try {
	      Class.forName("org.sqlite.JDBC");
	      c2 = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      stmt2 = c2.createStatement();
	      rs2 = stmt2.executeQuery( "SELECT COUNT(*) FROM SURVEY_QUESTION WHERE FK_S_ID = " + surveyID + ";" );
	      numquestions = rs2.getInt(1);
	      rs2.close();
	      stmt2.close();
	      c2.close();    
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    } finally {
	    	try{
	    		  rs2.close();
		  	      stmt2.close();
			      c2.close();
			      
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    		
		    	}
		  }
		return numquestions;		
	}
	
	
	/* Returns an ArrayList of ArrayLists that holds the questions and possible answers*/
	public ArrayList getSurveyQuestionsAnswers(int surveyID){
		System.out.println("in getSurveyQuestionsAnswers");
		//int numofquestions = this.getNumQuestions(surveyID);
		Connection c = null;
	    Statement stmtQuestions;
	    Statement stmtAnswers;
	    ResultSet rsQuestions = null;
	    ResultSet rsAnswers = null;
		ArrayList myLists = new ArrayList();
		
		String myAnswer = null;
		
	    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
		      stmtQuestions = c.createStatement();
		      stmtAnswers = c.createStatement();
		      rsQuestions = stmtQuestions.executeQuery( "SELECT Q_ID, QUESTION_TEXT FROM SURVEY_QUESTION WHERE SURVEY_QUESTION.FK_S_ID = '" + surveyID + "';");

		      int questionscounter = 0;
		      int id;
		      String idStr;
		      int counter = 0;
		      while (rsQuestions.next()) {
		    	 ArrayList tempArray = new ArrayList();
		         id = rsQuestions.getInt("Q_ID");
		         idStr = Integer.toString(id);
		         String questionStr = rsQuestions.getString("QUESTION_TEXT");
		         tempArray.add(questionStr);
		            
		         rsAnswers = stmtAnswers.executeQuery( "SELECT LETTER, ANSWER_TEXT FROM POSSIBLE_ANSWERS WHERE POSSIBLE_ANSWERS.FK_Q_ID = '" + id + "';");
				 while (rsAnswers.next()){
				  	String letter = rsAnswers.getString("LETTER");
				  	tempArray.add(letter);
				  	String answer = rsAnswers.getString("ANSWER_TEXT");
				 	tempArray.add(answer);
				 }
				 tempArray.add(idStr);   
		         questionscounter ++; 
		         myLists.add(tempArray);   
		      }
		            
		      stmtQuestions.close();
		      stmtAnswers.close();
		      c.close();   
		      
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
		    } finally {
		    	try{
		    		  stmtQuestions = c.createStatement();
		    		  stmtAnswers = c.createStatement();
		    		  stmtQuestions.close();
				      stmtAnswers.close();
				      c.close();
			    	} catch (Exception e) {
			    		JOptionPane.showMessageDialog(null, e.getMessage());
			    	}
			    }
		    return myLists;
			
		}
		
		

	
	/* Takes the name of the survey and returns the survey id from the database*/
	public int getSurveyID(String newsurvey){
		System.out.println("in getSurveyID");
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    int sid =0;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT S_ID FROM SURVEY WHERE SURVEY_NAME = '" + newsurvey + "'" );
	      sid = rs.getInt(1); 
	      rs.close();
	      stmt.close();
	      c.close();
	      
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    } finally {
	    	try{
		  	      stmt.close();
			      c.close();
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    }
	    
	    return sid;
	}
	
	/* Counts the number of questions that belong to a particular survey*/
	public int getNumberOfQuestions(int surveyID){
		
		System.out.println("in getNumberOfQuestions");
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    int countRow = 0;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT COUNT(Q_ID) FROM SURVEY_QUESTION WHERE FK_S_ID = '" + surveyID + "'" );
	      countRow = rs.getInt(1); 
	      rs.close();
	      stmt.close();
	      c.close();
	      
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    } finally {
	    	try{
		  	      stmt.close();
			      c.close();
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    }
	    
		return countRow;
		
	}
	
	
	public boolean checkUniqueSurveyName(String name){
		System.out.println("in checkUniqueSurveyName");
		boolean sameName = false;

		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    String dbName;
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
//	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT NAME FROM SURVEY" );
	      
	    //  dbName = rs.getInt(1); 
	      
	      
	      
	      rs.close();
	      stmt.close();
	      c.close();
	      
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    } finally {
	    	try{
		  	      stmt.close();
			      c.close();
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    }		
		return sameName;
	}
	
	/* Determine the number of rows that are in a Result Set*/
	public int getResultSetNumRows(ResultSet rs){
		System.out.println("in getResultSetNumrows");
		int count = 0;
		try {
			while ( rs.next() )  
			{  
			    // Process the row.  
			    count++;  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return count;
	}
	
	public int getAnswerID(int quesID, String answer){
		System.out.println("in getAnswerID");
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    int answerId = 0;
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT PA_ID FROM POSSIBLE_ANSWERS WHERE FK_Q_ID = '" + quesID + "' AND LETTER = '" + answer + "'" );
	      
	      answerId = rs.getInt(1); 
	      
	      rs.close();
	      stmt.close();
	      c.close();
	      
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    } finally {
	    	try{
	    		  rs.close();
	    		  stmt.close();
			      c.close();
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    }		
		return answerId;
	}
	
	
} // END SURVEY_DB
