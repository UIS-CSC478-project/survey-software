package surveysoftware;

import java.util.ArrayList;

public class survey_actions implements survey_interface{
	survey_db mydb;
	
	public survey_actions(){
		boolean dbExists = false;
		mydb = new survey_db();
		dbExists = mydb.check4Db();
		
		//DELETE THE NEXT 4 LINES BEFORE GOING LIVE
//		if (dbExists){
//			System.out.println(" delete the old DB");
//			mydb.deleteDB();
//		}
//		dbExists = mydb.check4Db();
		//////////////////////////////////////////
		
		if (!dbExists){
			System.out.println("make a new DB");
			mydb.createDb();
		}
	}
	
	/* Collects the survey name and calls the database function to add it to the database. */
	public void addNewSurvey(String surveyname){
		mydb.addNewSurvey(surveyname);	
	}
	
	/* Collects the questions and answers and calls the database function to add them to the database. */
	public void addNewQuestionWithAnswer(ArrayList questionanswer, int numanswers){
		mydb.addNewQuestionWithAnswer(questionanswer, numanswers);
	}
	
	/* Not active yet at this time.  Will use to collect respondent information.*/
	public void addNewRespondent(){
		
	}
	
	/* Collect results from users taking survey and send to the database function*/
	public void addResults(){
		
	}
	
	public int getSurveyID(String surveyname){
		return mydb.getSurveyID(surveyname);
		
	}
	
	/* Returns a list of all Survey questions for a given Survey id*/
	public ArrayList getSurveyQuestions(String surveyname){
		ArrayList test = mydb.getSurveyQuestions(mydb.getSurveyID(surveyname));
		return test;
	}	
	
	public boolean checkUniqueSurveyName(String name){
		return mydb.checkUniqueSurveyName(name);
	}
	
	public ArrayList getSurveyNames(){
		return mydb.getSurveyNames();
	}
	
}
