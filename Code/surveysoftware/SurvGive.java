package surveysoftware;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SurvGive extends JFrame {
	
	
	String surveyName;
	survey_actions mySurvey;
	ArrayList allQuestions;
	private JScrollPane scroller;
	private JPanel panel;
	private JTextArea[] text;
	private JTextField[] answer;
	private JLabel survey;
	private JButton done, done2; 
	//int x = 0;
	int y = 20;
	
	public SurvGive(String s) {
	    surveyName = s;
		mySurvey = new survey_actions();
		initGUI();
	}
	
	public void initGUI()
	{	    
		int numQuestions = mySurvey.getNumberOfQuestions(mySurvey.getSurveyID(surveyName));
		String postText = "";
		//Get questions
		allQuestions = mySurvey.getSurveyQuestionsAnswers(surveyName);
		
		
		
		setBounds(0,0, 600, 700);
		panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(600, 750));
		setVisible(true);
		scroller = new JScrollPane(panel);
		text = new JTextArea[numQuestions];
		answer = new JTextField[numQuestions];
		survey = new JLabel(surveyName);
		survey.setBounds(135, 25, 138, 23);
		panel.add(survey);
		done = new JButton("Done");
		done.setBounds(254, 25, 138, 23);
		panel.add(done);
		
		for(int i = 0; i < numQuestions; i++){
			
			/*Get the Text to post */
			ArrayList <String> temp = (ArrayList) allQuestions.get(i);
			postText = temp.get(0) + "\n";
			for(int j = 1; j < temp.size(); j++){
				if (j%2 == 0)
					postText = postText + temp.get(j) + "\n";
				else
					postText = postText + "     " + temp.get(j) + "   ";
			}
			
			y+=40;
			text[i] = new JTextArea(postText);
			text[i].setLineWrap(true);
			text[i].setVisible(true);
			text[i].setBounds(10, y, 500, 100);
			answer[i] = new JTextField();
			y+=120;
			answer[i].setBounds(10, y , 30, 30);
			text[i].setLayout(new FlowLayout());
			answer[i].setLayout(new FlowLayout());
			text[i].setEditable(false);
			answer[i].setEditable(true);			
			text[i].setColumns(1);
			panel.add(text[i]);
			panel.add(answer[i]);
		}
		done2 = new JButton("Done");
		done2.setBounds(254, y + 30, 138, 23);
		panel.add(done2);
	    scroller = new JScrollPane(panel);
	    getContentPane().add(scroller, BorderLayout.CENTER);
	 
	}//END INITGUI

}

