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
		System.out.println("F exists = " + f.exists());
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
				" LETTER CHAR NOT NULL," +
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
		    
			System.out.println("database created");
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
	          + "'" + questionanswer.get(1) + "',"
	          + "'" + questionanswer.get(2) + "')";
	      stmt.executeUpdate(sql);
	      rs = stmt.executeQuery("SELECT Q_ID FROM SURVEY_QUESTION WHERE QUESTION_TEXT = '" + questionanswer.get(1) + "'");
	      int newqid = rs.getInt(1); 
	      
    	  sql = "INSERT INTO POSSIBLE_ANSWERS "
    	          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
    	          + "VALUES "
    	          + "('" + newqid   + "',"
    	          + "'" + questionanswer.get(3) + "',"
    	          + "'" + questionanswer.get(4) + "')";
    	  stmt.executeUpdate(sql);
    	  
    	  sql = "INSERT INTO POSSIBLE_ANSWERS "
	          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
	          + "VALUES "
	          + "('" + newqid   + "',"
	          + "'" + questionanswer.get(5) + "',"
	          + "'" + questionanswer.get(6) + "')";
    	  stmt.executeUpdate(sql);  
	      if(numanswers > 2){
	    	  sql = "INSERT INTO POSSIBLE_ANSWERS "
		          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
		          + "VALUES "
		          + "('" + newqid   + "',"
		          + "'" + questionanswer.get(7) + "',"
		          + "'" + questionanswer.get(8) + "')";
	    	  stmt.executeUpdate(sql);  
	      }
	      
	      if(numanswers >3){
	    	  sql = "INSERT INTO POSSIBLE_ANSWERS "
		          + "(FK_Q_ID, LETTER, ANSWER_TEXT) "
		          + "VALUES "
		          + "('" + newqid   + "',"
		          + "'" + questionanswer.get(9) + "',"
		          + "'" + questionanswer.get(10) + "')";
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
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      
	      String sql = "INSERT INTO ANSWERS " 
	          + "(FK_Q_ID, FK_PA_ID) "
	          + "VALUES "
	          + "('" + quesID   + "',"
	          + "'" + answer + "')";
	      
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
	
	
	/* Input results from survey_actions into database.
	 * This one is for a letter result with additional text.
	 * */
	public void addResults(int quesID, String answer, String other){
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
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
	          + "'" + answer + "',"
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
	
	public ArrayList getSurveyQuestions(int surveyID){
		//int numofquestions = this.getNumQuestions(surveyID);
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rsQuestions = null;
	    ResultSet rsAnswers = null;
		ArrayList myLists = new ArrayList();
		
		String myAnswer = null;
		
	    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
		      stmt = c.createStatement();
		      System.out.println("about to get questions");
		      rsQuestions = stmt.executeQuery( "SELECT Q_ID, QUESTION_TEXT FROM SURVEY_QUESTION WHERE SURVEY_QUESTION.FK_S_ID = '1';");
		      
		      		      
		      int counter = 0;
		      while (rsQuestions.next()) {
		    	  	ArrayList tempArray = new ArrayList();
		            int id = rsQuestions.getInt("Q_ID");
		            String questionStr = rsQuestions.getString("QUESTION_TEXT");
		            System.out.println("quid: " + id + "\t text: " + questionStr);
		            
		            tempArray.add(id);
		            tempArray.add(questionStr);
		            
		            System.out.println("about to get answers");
		            rsAnswers = stmt.executeQuery( "SELECT ANSWER_TEXT FROM POSSIBLE_ANSWERS WHERE POSSIBLE_ANSWERS.FK_Q_ID = '" + id + "';");
				    while (rsAnswers.next()){
				    	String answer = rsAnswers.getString("ANSWER_TEXT");
				    	System.out.println("answer is: " + answer);
				    	tempArray.add(answer);
				    }
		            
		         myLists.add(tempArray);   
		      }
		            
		      stmt.close();
		      c.close();   
		      
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
		    } finally {
		    	try{
			  	      stmt.close();
				      c.close();
			    	} catch (Exception e) {
			    		JOptionPane.showMessageDialog(null, e.getMessage());
			    	}
			    }
	    
		    return myLists;
			
		}
		
		

	
	/* Takes the name of the survey and returns the survey id from the database*/
	public int getSurveyID(String newsurvey){
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

	
	public boolean checkUniqueSurveyName(String name){
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
	

} // END SURVEY_DB
