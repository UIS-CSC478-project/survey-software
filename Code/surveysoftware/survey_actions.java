package surveysoftware;

import java.util.ArrayList;

public class survey_actions implements survey_interface{
	survey_db mydb;
	
	public survey_actions(){
		boolean dbExists = false;
		mydb = new survey_db();
		dbExists = mydb.check4Db();
		
		//DELETE THE NEXT 4 LINES BEFORE GOING LIVE
		if (dbExists){
			mydb.deleteDB();
		}
		dbExists = mydb.check4Db();
		//////////////////////////////////////////
		
		if (!dbExists){
			mydb.createDb();
		}
	}
	
	/* Collects the survey name and calls the database function to add it to the database. */
	public void addNewSurvey(String surveyname){
		mydb.addNewSurvey(surveyname);	
	}
	
	/* Collects the questions and answers and calls the database function to add them to the database. */
	public void addNewQuestionWithAnswer(ArrayList questionanswer, int numanswers){
		addNewQuestionWithAnswer(questionanswer, numanswers);
		
	}
	
	/* Not active yet at this time.  Will use to collect respondent information.*/
	public void addNewRespondent(){
		
	}
	
	/* Collect results from users taking survey and send to the database function*/
	public void addResults(){
		
	}
	
	/* Returns a list of all Survey questions for a given Survey id*/
	public String getSurveyQuestions(){
		String temp = "temp";
		return temp;
	}	
}
