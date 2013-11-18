/*
 * survey_db
 * version 0.0.1
 * 10/8/2013
 * Copyright (c) 2013 Lizard League Software
 */

package surveysoftware;

import java.util.ArrayList;

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
	
	/* Create a new survey database with tables if the check4Db returns false. */
	void createDb();
	
	/* Add a new survey name to the Survey table. */
	void addNewSurvey(String mysurveyname);
	
	/* Add a new question plus its possible answers to the tables SurveyQuestions
	 * and Answers.
	 * 
	 * numanswers is the number of possible answers that a user can choose from.
	 * 
	 * questionanswer arraylist is broken down as follows:
	 * questionanswer[1] = survey id
	 * questionanswer[2] = question text
	 * questionanswer [3] = question number (used to sort questions later)
	 * questionanswer [4][6][8][10] = answer letter
	 * questionansswer [5][7][9][11] = answer text
	 */
	void addNewQuestionWithAnswer(ArrayList <String> questionAnswer, int numAnswers);
	
	/* Not active yet at this time.  Will use to track respondents.*/
	void addNewRespondent();
	
	/* Add results from users taking survey to the Answers table*/
	void addResults(int quesID, String answer);
	
	/* Returns a list of all the Survey names created at the time it's called */
	ArrayList getSurveyNames();
	
	/* Returns a list of all Survey questions for a given Survey id*/
	ArrayList getSurveyQuestionsAnswers(int surveyID);
	
	int getNumQuestions(int surveyID);
	
}
