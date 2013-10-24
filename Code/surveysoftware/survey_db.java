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
		final File f = new File("surveydatabase.db");
		//System.out.println("F exists = " + f.exists());
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
            	" QUESTION_NUMBER INT," +
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
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}
	
	/* Add a new survey name to the Survey table. */
	public void addNewSurvey(String mysurveyname){
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:surveydatabase.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "INSERT INTO SURVEY (SURVEY_NAME) " +
	                   "VALUES ('" + mysurveyname + "')"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Add new Survey Record created successfully");
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
	      System.out.println("Opened database successfully");

	      
	      stmt = c.createStatement();
	      
	      String sql = "INSERT INTO SURVEY_QUESTION  " 
	          + "(FK_S_ID, QUESTION_TEXT, QUESTION_NUMBER) "
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
	      
	      System.out.println("newqid = " + newqid);
	      c.commit();
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
		
	}
	
	/* Will add new respondent information to the respondent table 
	 * Not implemented in version 1
	 * */
	public void addNewRespondent(){
		
	}
	
	
	public void addResults(){
	}
	
	public void deleteDB(){
		final File f = new File("surveydatabase.db");
		f.delete();
	}
	
	public String getSurveyNames(){
		
		return "string";
	}
	
	public String getSurveyQuestions(){
		
		return "string";
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
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT S_ID FROM SURVEY WHERE SURVEY_NAME = '" + newsurvey + "'" );
	      sid = rs.getInt(1); 
	      rs.close();
	      stmt.close();
	      c.close();
	      
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    return sid;
	}

} // END SURVEY_DB
