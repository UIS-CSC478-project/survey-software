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
	public void addNewSurvey(String surveyName){
			mydb.addNewSurvey(surveyName);	
	}
	
	/* Collects the questions and answers and calls the database function to add them to the database. */
	public void addNewQuestionWithAnswer(ArrayList questionanswer, int numanswers){
		mydb.addNewQuestionWithAnswer(questionanswer, numanswers);
	}
	
	/* Not active yet at this time.  Will use to collect respondent information.*/
	public void addNewRespondent(){
		
	}
	
	/* Collect results from users taking survey and send to the database function.
	 * This one is for one letter result with no additional text
	 * */
	public void addResults(int surveyID, int quesID, String answer){
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		mydb.addResults(quesID, answer);		
	}
	
	/* Collect results from users taking survey and send to the database function.
	 * This one is for one letter result with additional text
	 * */
	public void addResults(int surveyID, int quesID, String answer, String other){
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		mydb.addResults(quesID, answer, other);		
	}
	
	public int getSurveyID(String surveyName){
		return mydb.getSurveyID(surveyName);
		
	}
	
	/* Returns a list of all Survey questions for a given Survey id*/
	public ArrayList getSurveyQuestions(String surveyName){
		ArrayList test = mydb.getSurveyQuestions(mydb.getSurveyID(surveyName));
		return test;
	}	
	
	public boolean checkUniquesurveyName(String name){
		return mydb.checkUniqueSurveyName(name);
	}
	
	public String[] getSurveyNames(){
		ArrayList surveyNames = mydb.getSurveyNames();
		String[] surveyNameArray = new String[surveyNames.size()];
		surveyNameArray = (String[]) surveyNames.toArray(surveyNameArray);
		return surveyNameArray;
	}
	
	public void addSurveyAnswers(){
		
		
	}
	
	public boolean survey_exists(String surveyName){
		System.out.println("in survey_exists");
		boolean match = false;
		ArrayList allNames = mydb.getSurveyNames();
		for(Object item : allNames ){
			System.out.println("item is: " + (String)item);
			if (surveyName.equals((String)item)){
				System.out.println("survey name in db: " + item);
				match = true;
			}
		}
		return match;
	}
	
}
