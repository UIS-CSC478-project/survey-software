/*
 * survey_db
 * version 0.0.1
 * 10/8/2013
 * Copyright (c) 2013 Lizard League Software
 */

package surveysoftware;

/**
 *  
   Interface that will hold the methods required by the class that 
   will access the survey database.
 *
 * @version      
         0.0.1 Oct 8 2013  
 * @author          
         Beth Kieler  */

public interface db_interface {

	/* Check for the presence of an existing survey database.*/
	boolean check4Db();
	
	/* Create a new survey database if the check4Db returns false. */
	void createDb();
	
	/* Add a new survey to the Survey table. */
	void addNewSurvey();
	
	/* Add a new question plus its possible answers to the tables SurveyQuestions
	 * and Answers.
	 */
	void addNewQuestionWithAnswer();
	
	void addNewRespondent();
	
	void addResults();
	
}
