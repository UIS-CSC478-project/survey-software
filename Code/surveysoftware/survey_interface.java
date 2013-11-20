package surveysoftware;

import java.util.ArrayList;

/**
 *  
   Interface that will hold the methods required by the class that 
   will access the survey database.
 *
 * @version      
         0.0.1 Oct 20 2013  
 * @author          
         Beth Kieler  */

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


