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
			mydb.createDb();
		}
	}
	
	/* Collects the survey name and calls the database function to add it to the database. */
	public void addNewSurvey(String surveyName){
			mydb.addNewSurvey(surveyName);	
	}
	
	/* Collects the questions and answers and calls the database function to add them to the database. */
	public void addNewQuestionWithAnswer(ArrayList <String> questionanswer, int numanswers){
		mydb.addNewQuestionWithAnswer(questionanswer, numanswers);
	}
	
	/* Not active yet at this time.  Will use to collect respondent information.*/
	public void addNewRespondent(){
		
	}
	
	/* Collect results from users taking survey and send to the database function.
	 * This one is for one letter result with no additional text
	 * */
	public void addResults(int quesID, String answer){
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		mydb.addResults(quesID, answer);		
	}
	
	/* Collect results from users taking survey and send to the database function.
	 * This one is for one letter result with additional text
	 * */
	public void addResults(int quesID, String answer, String other){
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		mydb.addResults(quesID, answer, other);		
	}
	
	public int getSurveyID(String surveyName){
		return mydb.getSurveyID(surveyName);
		
	}
	
	public int getNumberOfQuestions(int surveyID){
		return mydb.getNumberOfQuestions(surveyID);		
	}
	
	/* Returns an ArrayList of ArrayLists of all Survey questions plus answers for a given Survey id*/
	public ArrayList getSurveyQuestionsAnswers(String surveyName){
		ArrayList surveyQuestions = mydb.getSurveyQuestionsAnswers(mydb.getSurveyID(surveyName));
		return surveyQuestions;
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
		boolean match = false;
		
		ArrayList allNames = mydb.getSurveyNames();
		
		for(int i = 0; i < allNames.size(); i++){
			if (surveyName.equals(allNames.get(i))){
				match = true;
			}
		}
		return match;
	}
	
}
