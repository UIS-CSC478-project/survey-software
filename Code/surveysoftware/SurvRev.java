package surveysoftware;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class SurvRev extends JFrame {
	
	String surveyName;
	survey_actions mySurvey;
	private JScrollPane scroller;
	private JPanel panel;
	private JTextArea[] text;
	
	public SurvRev(String s) {
	    surveyName = s;
		mySurvey = new survey_actions();
		initGUI();
	}
	
	public void initGUI()
	{
		int numQuestions = mySurvey.getNumberOfQuestions(mySurvey.getSurveyID(surveyName));
		String postText = "";
		setSize(600,700);
		panel = new JPanel();
		panel.setLayout(new GridLayout(32, 1,10,10));
		setVisible(true);
		scroller = new JScrollPane(panel);
		text = new JTextArea[numQuestions];
		getContentPane().add(scroller, BorderLayout.CENTER);
		
		//Get questions
		ArrayList allQuestions = mySurvey.getSurveyQuestionsAnswers(surveyName);
		panel.add(new JLabel(surveyName));
		for(int i = 0; i < numQuestions; i++){
			ArrayList <String> temp = (ArrayList) allQuestions.get(i);
			postText = temp.get(0) + "\n";
			for(int j = 1; j < temp.size(); j++){
				if (j%2 == 0)
					postText = postText + temp.get(j) + "\n";
				else
					postText = postText + "     " + temp.get(j) + "   ";
			}
			
			
			text[i] = new JTextArea(postText);
			
			text[i].setEditable(false);
			text[i].setColumns(1);
			panel.add(text[i]);
		}
	    scroller = new JScrollPane(panel);
	    getContentPane().add(scroller, BorderLayout.CENTER);

	}//END INITGUI

}
