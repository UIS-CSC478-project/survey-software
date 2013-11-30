/*
 * survey_interface
 * version 1.0.1
 * 11/28/2013
 * Copyright (c) 2013 Lizard League Software
 */

/**
 *  
   Interface that will hold the methods required by the class that 
   will access the survey database class: survey_actions.
	
	Requirements:  5.3.0
   
   */

package surveysoftware;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;



public interface Survey_Interface {

	/* Collects the questions and answers and calls the database function to add them to the database.*/
	public void addNewQuestionWithAnswer(ArrayList <String> questionanswer, int numanswers);

	/* Collect respondent information. */
	public void addNewRespondent();

	
	/* Collects the survey name and calls the database function to add it to the database.  */	
	public void addNewSurvey(String surveyName);
		
	/* Collect results from users taking survey and send to the database function.
	 * This one is for one letter result with no additional text. */
	public void addResults(int quesID, String answer);
	
	/* Collect results from users taking survey and send to the database function.
	 * This one is for one letter result with additional text.  	 * */
	public void addResults(int quesID, String answer, String other);
	
	/* Return the number of questions present in a survey */
	public int getNumberOfQuestions(int surveyId);

	/* Return the survey ID */
	public int getSurveyId(String surveyName);
		
	/* Returns an arraylist of all the survey names that currently exist in the database */
	public String[] getSurveyNames();

	/* Returns an ArrayList of ArrayLists of all Survey questions plus answers for a given Survey id. */
	public ArrayList getSurveyQuestionsAnswers(String surveyName);
	
	/* Returns true if the survey name already exists  */
	public boolean survey_Exists(String surveyName);

	/* Returns true if the survey name already exists*/
//	public boolean checkUniquesurveyName(String name);
	
	

	
	
	
	
	
}


