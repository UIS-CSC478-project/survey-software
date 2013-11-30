/*
 * survey_actions
 * version 1.0.1
 * 11/28/2013
 * Copyright (c) 2013 Lizard League Software
 */         

/**
 *  
   Interface that will hold the methods required by the class that 
   will access the survey database.
	
	Requirements:  5.1.0
   
   
   */

package surveysoftware;

import java.util.ArrayList;

public class Survey_Actions implements Survey_Interface{
	Survey_Db myDB;
	
	public Survey_Actions(){
		/* If survey database doesn't exist, create a new one */
		boolean dbExists = false;
		myDB = new Survey_Db();
		dbExists = myDB.check4Db();
		
		//DELETE OR COMMENT OUT THE NEXT 4 LINES BEFORE GOING LIVE - FOR TESTING ONLY
//		if (dbExists){
//			System.out.println(" delete the old DB");
//			myDB.deleteDB();
//		}
//		dbExists = myDB.check4Db();
		//////////////////////////////////////////
		
		if (!dbExists){
			myDB.createDb();
		}
	}
	
	/* Collects the survey name and calls the database function to add it to the database. 
	 * 
	 * Requirements:  3.5.0
	 * */
	
	public void addNewSurvey(String surveyName){
			myDB.addNewSurvey(surveyName);	
	}
	
	/* Collects the questions and answers and calls the database function to add them to the database. 
	 * 
	 * Requirements: 3.0.0, 3.1.0, 3.4.0, 3.6.0
	 * */
	public void addNewQuestionWithAnswer(ArrayList <String> questionAnswer, int numAnswers){
		myDB.addNewQuestionWithAnswer(questionAnswer, numAnswers);
	}
	
	/* Not active yet at this time.  Will use to collect respondent information.
	 * 
	 * Requirements: 4.5.0
	 * */
	public void addNewRespondent(){
		
	}
	
	/* Collect results from users taking survey and send to the database function.
	 * This one is for one letter result with no additional text
	 * 
	 * */
	public void addResults(int quesId, String answer){
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		myDB.addResults(quesId, answer);		
	}
	
	/* Collect results from users taking survey and send to the database function.
	 * This one is for one letter result with additional text
	 * */
	public void addResults(int quesId, String answer, String other){
		//in the action performed will be a line like 
		// mysurvey.addNewSurvey(surveyName.getText());
		myDB.addResults(quesId, answer, other);		
	}
	
	/* Return the survey ID */
	public int getSurveyId(String surveyName){
		return myDB.getSurveyId(surveyName);
	}
	
	/* Return the number of questions present in a survey */
	public int getNumberOfQuestions(int surveyId){
		return myDB.getNumberOfQuestions(surveyId);		
	}
	
	/* Returns an ArrayList of ArrayLists of all Survey questions plus answers for a given Survey id
	 * 
	 * Requirements: 4.4.0
	 * */
	public ArrayList getSurveyQuestionsAnswers(String surveyName){
		ArrayList surveyQuestions = myDB.getSurveyQuestionsAnswers(myDB.getSurveyId(surveyName));
		return surveyQuestions;
	}	
	
	/* Returns true if the survey name already exists*/
//	public boolean checkUniquesurveyName(String name){
//		return myDB.checkUniqueSurveyName(name);
//	}
	
	public String[] getSurveyNames(){
		ArrayList surveyNames = myDB.getSurveyNames();
		String[] surveyNameArray = new String[surveyNames.size()];
		surveyNameArray = (String[]) surveyNames.toArray(surveyNameArray);
		return surveyNameArray;
	}
	
	
	/* Returns true if the survey name already exists  */
	public boolean survey_Exists(String surveyName){
		boolean match = false;
		
		ArrayList allNames = myDB.getSurveyNames();
		
		for(int i = 0; i < allNames.size(); i++){
			if (surveyName.equals(allNames.get(i))){
				match = true;
			}
		}
		return match;
	}
}
