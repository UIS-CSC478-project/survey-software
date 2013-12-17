/*
 * Survey_Db
 * version 1.0.1
 * 11/28/2013
 * Copyright (c) 2013 Lizard League Software
 */

/**
 *  
  	Class that implements the db_interface.db interface.  This class
    will manipulate the data that goes in and out of the database.

 	Requirements:  2.0.0. 2.1.0, 2.1.1, 2.1.2, 2.1.3, 2.1.4, 2.1.5, 2.1.6, 5.0.0
*/


package surveysoftware;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Survey_Db implements Db_Interface{
	
	public Survey_Db(){
		
	}
	
	/* Check for the presence of an existing survey database.*/
	public boolean check4Db(){
		File f = new File("surveydatabase.db");
		return f.exists();	
	}
	
	/* Create a new survey database with tables if the check4Db returns false. */
	public void createDb(){
		Connection c = null;
		Statement stmt = null;

		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
			stmt = c.createStatement();
		    
			//Survey
			String sql = "CREATE TABLE Survey " +
		                 "(S_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
		                 " SURVEY_NAME TEXT  UNIQUE  NOT NULL)"; 
		    stmt.executeUpdate(sql);
		    
		    //Survey_Question
		    sql = "CREATE TABLE Survey_Question " +
            	"(Q_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            	" FK_S_ID INTEGER NOT NULL," +
            	" QUESTION_TEXT TEXT NOT NULL," +
            	" QUESTION_ANSWER TEXT," +
            	" FOREIGN KEY (FK_S_ID) REFERENCES Survey(S_ID))"; 
			stmt.executeUpdate(sql);
			
			//Possible_Answers
			sql = "CREATE TABLE Possible_Answers " +
				"(PA_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				" FK_Q_ID INTEGER NOT NULL," +
				" LETTER TEXT NOT NULL," +
				" ANSWER_TEXT TEXT NOT NULL," +
				" FOREIGN KEY (FK_Q_ID) REFERENCES Survey_Question(Q_ID))"; 
			stmt.executeUpdate(sql);
					    
			//Answers
			sql = "CREATE TABLE Answers " +
				"(A_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				" FK_Q_ID INTEGER NOT NULL," +
				" FK_PA_ID INTEGER NOT NULL," +
				" OTHER_RESP TEXT," +
				" FOREIGN KEY (FK_Q_ID) REFERENCES Survey_Question(Q_ID)," +
				" FOREIGN KEY (FK_PA_ID) REFERENCES Possible_Answers(PA_ID))"; 
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
	
	/* Add a new survey name to the Survey table. 
	 * 
	 * Requirements: 2.1.0, 2.1.1, 3.5.0
	 * */
	public void addNewSurvey(String mysurveyname){
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      String sql = "INSERT INTO Survey (SURVEY_NAME) " +
	                   "VALUES ('" + mysurveyname + "');"; 
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
	 * questionanswer [3] = correct question answer (can be null)
	 * questionanswer [4][6][8][10] = answer letter
	 * questionansswer [5][7][9][11] = answer text
	 * 
	 * Requirements: 2.1.2, 2.1.3, 2.1.4, 3.0.0, 3.1.0, 3.4.0, 3.6.0 
	 */
	public void addNewQuestionWithAnswer(ArrayList questionanswer, int numanswers){
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      
	      stmt = c.createStatement();
	      
	      String sql = "INSERT INTO Survey_Question  " 
	          + "(FK_S_ID, QUESTION_TEXT, QUESTION_ANSWER) "
	          + "VALUES "
	          + "('" + questionanswer.get(0)   + "',"
	          + "'" + (String) questionanswer.get(1) + "',"
	          + "'" + (String) questionanswer.get(2) + "');";
	      stmt.executeUpdate(sql);
	      rs = stmt.executeQuery("SELECT Q_ID FROM Survey_Question WHERE QUESTION_TEXT = '" + questionanswer.get(1) + "';");
	      int newqid = rs.getInt(1); 
	      
    	  sql = "INSERT INTO Possible_Answers "
    	          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
    	          + "VALUES "
    	          + "('" + newqid   + "',"
    	          + "'" + (String) questionanswer.get(3) + "',"
    	          + "'" + (String) questionanswer.get(4) + "')";
    	  stmt.executeUpdate(sql);
    	  
    	  sql = "INSERT INTO Possible_Answers "
	          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
	          + "VALUES "
	          + "('" + newqid   + "',"
	          + "'" + (String) questionanswer.get(5) + "',"
	          + "'" + (String) questionanswer.get(6) + "');";
    	  stmt.executeUpdate(sql);  
	      if(numanswers > 2){
	    	  sql = "INSERT INTO Possible_Answers "
		          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
		          + "VALUES "
		          + "('" + newqid   + "',"
		          + "'" + (String) questionanswer.get(7) + "',"
		          + "'" + (String) questionanswer.get(8) + "');";
	    	  stmt.executeUpdate(sql);  
	      }
	      
	      if(numanswers >3){
	    	  sql = "INSERT INTO Possible_Answers "
		          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
		          + "VALUES "
		          + "('" + newqid   + "',"
		          + "'" + (String) questionanswer.get(9) + "',"
		          + "'" + (String) questionanswer.get(10) + "');";
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
	 * 
	 * Requirements: 2.1.6
	 * */
	public void addNewRespondent(){
		
	}
	
	/* Input results from survey_actions into database.
	 * This one is for one letter result with no additional text.
	 * 
	 * Requirements: 2.1.5
	 * */
	public void addResults(int quesID, String answer){
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		int pAnswerID = getAnswerId(quesID, answer);
		
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      
	      String sql = "INSERT INTO Answers " 
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
	 * 
	 * Requirements: 2.1.5
	 * */
	public void addResults(int quesID, String answer, String other){
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		int pAnswerID = getAnswerId(quesID, answer);
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      
	      
	      String sql = "INSERT INTO Answers " 
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
	
	/* Delete the current database: surveydatabase.db */
	public void deleteDb(){
		File f = new File("surveydatabase.db");
		f.delete();
	}
	
	/* Get a list of all the surveys that have been created so far*/
	public ArrayList getSurveyNames(){
		ArrayList surveyNames = new ArrayList();
		
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");;
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT SURVEY_NAME FROM Survey;" );

	      while(rs.next()){
	    	  
	    	   surveyNames.add(rs.getString("SURVEY_NAME"));
	    	}
	      
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
	
	
	/* Returns an ArrayList of ArrayLists that holds the questions and possible answers
	 * 
	 * Requirements: 3.9.0, 4.1.0
	 * */
	
	public ArrayList getSurveyQuestionsAnswers(int surveyID){
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
		      rsQuestions = stmtQuestions.executeQuery( "SELECT Q_ID, QUESTION_TEXT FROM Survey_Question WHERE Survey_Question.FK_S_ID = '" + surveyID + "';");
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
		            
		         rsAnswers = stmtAnswers.executeQuery( "SELECT LETTER, ANSWER_TEXT FROM Possible_Answers WHERE Possible_Answers.FK_Q_ID = '" + id + "';");
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
	/* Returns an ArrayList of ArrayLists of all Survey questions plus the results for each answer.
	 * 
	 * Requirements: 4.4.0
	 * */		
	public ArrayList getSurveyQuestionsResults(int surveyID){
		Connection c = null;
	    Statement stmtQuestions , stmtAnswers, stmtCountAnswers, stmtTotalResponses;
	    ResultSet rsQuestions = null;
	    ResultSet rsAnswers = null;
	    ResultSet rsCountAnswers = null;
	    ResultSet rsTotalResponses = null;
		ArrayList myLists = new ArrayList();
		String myAnswer = null;
		int percent = 0;
		
	    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
		      stmtQuestions = c.createStatement();
		      stmtAnswers = c.createStatement();
		      stmtCountAnswers = c.createStatement();
		      stmtTotalResponses = c.createStatement();
		      
		      //Get the Question ID and Question text
		      rsQuestions = stmtQuestions.executeQuery( "SELECT Q_ID, QUESTION_TEXT FROM Survey_Question WHERE Survey_Question.FK_S_ID = '" + surveyID + "';");
		      int questionscounter = 0;
		      int questionId;
		      String idStr;
		      int counter = 0;
		      while (rsQuestions.next()) {
		    	 ArrayList tempArray = new ArrayList();
		         questionId = rsQuestions.getInt("Q_ID");
		         idStr = Integer.toString(questionId);
		         String questionStr = rsQuestions.getString("QUESTION_TEXT");
		         tempArray.add(questionStr);
		            
		         rsAnswers = stmtAnswers.executeQuery( "SELECT PA_ID, LETTER FROM Possible_Answers WHERE Possible_Answers.FK_Q_ID = '" + questionId + "';");
		         rsTotalResponses = stmtTotalResponses.executeQuery("SELECT COUNT(*) FROM Answers WHERE FK_Q_ID = '" + questionId + "';");
		         int numTotalResponses = rsTotalResponses.getInt(1);
  			  	 

		         
				 while (rsAnswers.next()){
				  	String letter = rsAnswers.getString("LETTER");
				  	tempArray.add(letter);
				  	int answerID = rsAnswers.getInt("PA_ID");
				  	
				  	
				  	rsCountAnswers = stmtCountAnswers.executeQuery("SELECT COUNT(*) FROM Answers WHERE Answers.FK_PA_ID = '" + answerID + "';");
				  					  	
				  	int answerCount = rsCountAnswers.getInt(1);
				  	int percentResults = (answerCount*100)/numTotalResponses;
				  	System.out.println("Int numTotalResponses: " + numTotalResponses);
				  	System.out.println("answerCount: " + answerCount);
				  	System.out.println("Int Percent Results: " + percentResults);
				  	
				  	String strAnswerCount = Integer.toString(answerCount);
				  	String strPercentResults = Integer.toString(percentResults);
				  	System.out.println("str Percent Results: " + strPercentResults);
				  	tempArray.add(strAnswerCount);
				  	tempArray.add(strPercentResults);
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
	public int getSurveyId(String newsurvey){
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    int sid =0;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT S_ID FROM Survey WHERE SURVEY_NAME = '" + newsurvey + "'" );
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
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    int countRow = 0;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT COUNT(Q_ID) FROM Survey_Question WHERE FK_S_ID = '" + surveyID + "'" );
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
	
	/* Returns a true if the survey name already exists in the database.*/
//	public boolean checkUniqueSurveyName(String name){
//	
//		boolean isUnique = true;
//
//		Connection c = null;
//	    Statement stmt = null;
//	    ResultSet rs = null;
//	    String dbName;
//	    
//	    try {
//	      Class.forName("org.sqlite.JDBC");
//	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
////	      c.setAutoCommit(false);
//	      stmt = c.createStatement();
//	      rs = stmt.executeQuery("SELECT 1 FROM SURVEY WHERE SURVEY_NAME = '" + name + "'");
//			if(rs.next())
//				isUnique = !(rs.getInt(1) == 1);
//	      
//	      
//	      
//	      rs.close();
//	      stmt.close();
//	      c.close();
//	      
//	      
//	    } catch ( Exception e ) {
//	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//	      JOptionPane.showMessageDialog(null, e.getMessage());
//	    } finally {
//	    	try{
//		  	      stmt.close();
//			      c.close();
//		    	} catch (Exception e) {
//		    		JOptionPane.showMessageDialog(null, e.getMessage());
//		    	}
//		    }		
//		return isUnique;
//	}
	
	/* Determine the number of rows that are in a Result Set*/
	public int getResultSetNumRows(ResultSet rs){
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
	
	/* Returns the id of the particular answer chosen.*/
	public int getAnswerId(int quesID, String answer){

		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    int answerId = 0;
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT PA_ID FROM Possible_Answers WHERE FK_Q_ID = '" + quesID + "' AND LETTER = '" + answer + "'" );
	      
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
