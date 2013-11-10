package surveysoftware;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class SurvRev extends JFrame {
	
	String surveyName;
	survey_actions mySurvey;
	private JTextField[] questionsOutput;
	
	public SurvRev(String s) {
		surveyName = s;
		mySurvey = new survey_actions();
		//initGUI();
	}
	public void initGUI()
	{
		int x = 5;
		int y = 5;
		int moveDown = 0;
		setSize(684, 704);
		setResizable(true);
		getContentPane().setLayout(null);
		setVisible(true);
		
		//Get the number of questions that will be displayed
		int numQuestions = mySurvey.getNumberOfQuestions(mySurvey.getSurveyID(surveyName));
		
		//Get questions
		ArrayList allQuestions = mySurvey.getSurveyQuestions(surveyName);
		//Create a JTextFieldArray for the appropriate number of questions
		questionsOutput = new JTextField[numQuestions];
		
		for (int i = 0; i < numQuestions; i++){
			String outputQA;
			String[] aQuestionAnswer = new String[9];
			ArrayList temp = (ArrayList) allQuestions.get(i);
			aQuestionAnswer = (String[]) temp.toArray(aQuestionAnswer);
			
			outputQA = aQuestionAnswer[0]+ "\n" + aQuestionAnswer[1] + ")  " + 
			aQuestionAnswer[2] + "\n" + aQuestionAnswer[3] + ")  " + aQuestionAnswer[4] + 
			"\n" + aQuestionAnswer[5] + ")  " 	+ aQuestionAnswer[6] + "\n" + aQuestionAnswer[7] + 
			")  " + aQuestionAnswer[8] + "\n";
			
			questionsOutput[i] = new JTextField(outputQA, 100);
			
			
			questionsOutput[i].setBounds(5, 5 + moveDown, 660, 100);
			questionsOutput[i].setEditable(false);
			questionsOutput[i].setVisible(true);
			getContentPane().add(questionsOutput[i]);
			moveDown+=100;

		}
						
	}

}
