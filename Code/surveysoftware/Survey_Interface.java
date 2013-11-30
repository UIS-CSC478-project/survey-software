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



public interface survey_interface {
	
	/* Collects the survey name and calls the database function to add it to the database. */
	void addNewSurvey(String surveyname);
	
	/* Collects the questions and answers and calls the database function to add them to the database. */
	void addNewQuestionWithAnswer(ArrayList <String> questionanswer, int numanswers);
	
	/* Not active yet at this time.  Will use to collect respondent information.*/
	void addNewRespondent();
	
	/* Collect results from users taking survey and send to the database function*/
	void addResults(int quesID, String answer, String other);
	
	/* Returns a list of all Survey questions for a given Survey id*/
	ArrayList getSurveyQuestionsAnswers(String surveyname);
	

	
	
	
	
	
}


