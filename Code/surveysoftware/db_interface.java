/*
 * survey_db
 * version 1.0.1
 * 11/28/2013
 * Copyright (c) 2013 Lizard League Software
 */

/**
 *  
   Interface that will hold the methods required by the class that 
   will access the survey database directly: survey_db.
	
	Requirements:  5.2.0
   
   
   */

package surveysoftware;

//import java.io.File;
//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
//import javax.swing.JOptionPane;

public interface db_interface {


	 /*
	  * Add a new question plus its possible answers to the tables SurveyQuestions
	 * and Answers.
	 * 
	 * questionanswer[1] = survey id
	 * questionanswer[2] = question text
	 * questionanswer [3] = correct question answer (can be null)
	 * questionanswer [4][6][8][10] = answer letter
	 * questionansswer [5][7][9][11] = answer text
	 */
	public void addNewQuestionWithAnswer(ArrayList questionanswer, int numanswers);
	
	/* Will add new respondent information to the respondent table 
	 * Not implemented in version 1
	 * */
	public void addNewRespondent();

	/* Add a new survey name to the Survey table. */
	public void addNewSurvey(String mysurveyname);
	
	/* Input results from survey_actions into database.
	 * This one is for one letter result with no additional text.
	 * */
	public void addResults(int quesID, String answer);
	
	/* Input results from survey_actions into database.
	 * This one is for a letter result with additional text.
	 * */
	public void addResults(int quesID, String answer, String other);	

	
	/* Check for the presence of an existing survey database: surveydatabase.db.*/
	public boolean check4Db();

	/* Returns a true if the survey name already exists in the database.*/
	public boolean checkUniqueSurveyName(String name);

	/* Create a new survey database with tables if the check4Db returns false: surveydatabase.db */
	public void createDb();
	
	/* Delete the current database: surveydatabase.db */
	public void deleteDB();

	/* Returns the id of the particular answer chosen.*/
	public int getAnswerID(int quesID, String answer);	

	/* Counts the number of questions that belong to a particular survey*/
	public int getNumberOfQuestions(int surveyID);

	/* Determine the number of rows that are in a Result Set*/
	public int getResultSetNumRows(ResultSet rs);

	/* Takes the name of the survey and returns the survey id from the database*/
	public int getSurveyID(String newsurvey);

	/* Get a list of all the surveys that have been created so far*/
	public ArrayList getSurveyNames();

	/* Returns an ArrayList of ArrayLists that holds the questions and possible answers*/
	public ArrayList getSurveyQuestionsAnswers(int surveyID);		
		
}
